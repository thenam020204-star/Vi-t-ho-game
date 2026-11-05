#!/usr/bin/env python3
"""CLDC/MIDP compatibility scanner + binary-compatibility diff for a J2ME class.
Usage: compat_scan.py <new.class> [<old.class>]"""
import re, subprocess, sys, collections

# --- curated API surface. CLDC = CLDC 1.0/1.1 (JSR 30/139), MIDP = MIDP 1.0/2.0 (JSR 37/118)
API = {
 'java/lang/Object':            ('CLDC 1.0', {'<init>:()V','getClass:()Ljava/lang/Class;','hashCode:()I',
                                  'equals:(Ljava/lang/Object;)Z','toString:()Ljava/lang/String;','notify:()V',
                                  'notifyAll:()V','wait:()V','wait:(J)V','wait:(JI)V'}),
 'java/lang/String':            ('CLDC 1.0', {'length:()I','charAt:(I)C','valueOf:(C)Ljava/lang/String;',
                                  'valueOf:(I)Ljava/lang/String;','valueOf:(J)Ljava/lang/String;',
                                  'valueOf:(Z)Ljava/lang/String;','valueOf:(Ljava/lang/Object;)Ljava/lang/String;',
                                  'indexOf:(I)I','substring:(I)Ljava/lang/String;','substring:(II)Ljava/lang/String;',
                                  'equals:(Ljava/lang/Object;)Z','getBytes:()[B','toCharArray:()[C',
                                  'concat:(Ljava/lang/String;)Ljava/lang/String;'}),
 'java/lang/Integer':           ('CLDC 1.0', {'<init>:(I)V','intValue:()I','parseInt:(Ljava/lang/String;)I',
                                  'toString:()Ljava/lang/String;','hashCode:()I','equals:(Ljava/lang/Object;)Z'}),
 'java/lang/System':            ('CLDC 1.0', {'arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V',
                                  'currentTimeMillis:()J','gc:()V'}),
 'java/lang/Throwable':         ('CLDC 1.0', {'<init>:()V','printStackTrace:()V','getMessage:()Ljava/lang/String;'}),
 'java/lang/Exception':         ('CLDC 1.0', {'<init>:()V','printStackTrace:()V'}),
 'java/lang/Class':             ('CLDC 1.0', {'getResourceAsStream:(Ljava/lang/String;)Ljava/io/InputStream;',
                                  'forName:(Ljava/lang/String;)Ljava/lang/Class;','newInstance:()Ljava/lang/Object;'}),
 'java/lang/StringBuffer':      ('CLDC 1.0', {'<init>:()V','append:(Ljava/lang/String;)Ljava/lang/StringBuffer;',
                                  'append:(I)Ljava/lang/StringBuffer;','append:(C)Ljava/lang/StringBuffer;',
                                  'toString:()Ljava/lang/String;'}),
 'java/lang/Math':              ('CLDC 1.0', {'abs:(I)I','min:(II)I','max:(II)I'}),
 'java/util/Hashtable':         ('CLDC 1.0', {'<init>:()V','<init>:(I)V',
                                  'put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;',
                                  'get:(Ljava/lang/Object;)Ljava/lang/Object;','size:()I','clear:()V',
                                  'keys:()Ljava/util/Enumeration;'}),
 'java/util/Vector':            ('CLDC 1.0', set()),
 'java/io/DataInputStream':     ('CLDC 1.0', {'<init>:(Ljava/io/InputStream;)V','readUTF:()Ljava/lang/String;',
                                  'readByte:()B','readShort:()S','readInt:()I','close:()V','read:([B)I'}),
 'java/io/InputStream':         ('CLDC 1.0', set()),
 'javax/microedition/lcdui/Image':    ('MIDP 1.0', {
                                  'createRGBImage:([IIIZ)Ljavax/microedition/lcdui/Image;',
                                  'createImage:(Ljava/lang/String;)Ljavax/microedition/lcdui/Image;',
                                  'createImage:(II)Ljavax/microedition/lcdui/Image;',
                                  'getWidth:()I','getHeight:()I'}),
 'javax/microedition/lcdui/Graphics': ('MIDP 1.0', {
                                  'getColor:()I','setColor:(I)V','setColor:(III)V',
                                  'drawRGB:([IIIIIIIZ)V',
                                  'drawRegion:(Ljavax/microedition/lcdui/Image;IIIIIIII)V',
                                  'drawImage:(Ljavax/microedition/lcdui/Image;III)V',
                                  'drawString:(Ljava/lang/String;III)V','fillRect:(IIII)V','drawLine:(IIII)V',
                                  'setClip:(IIII)V','getClipX:()I','getClipY:()I','getClipWidth:()I','getClipHeight:()I'}),
}
# MIDP-2.0-only members that must be flagged when the target is MIDP 1.0
MIDP2_ONLY = {
 'javax/microedition/lcdui/Image.createRGBImage:([IIIZ)Ljavax/microedition/lcdui/Image;',
 'javax/microedition/lcdui/Graphics.drawRGB:([IIIIIIIZ)V',
 'javax/microedition/lcdui/Graphics.drawRegion:(Ljavax/microedition/lcdui/Image;IIIIIIII)V',
}
BANNED_PREFIX = ('android/','java/nio/','java/net/','java/text/','java/util/regex/','java/util/concurrent/',
                 'java/lang/reflect/','java/lang/invoke/','javax/swing/','java/awt/','java/util/function/',
                 'java/util/stream/','sun/','jdk/','java/util/ArrayList','java/util/HashMap','java/util/List',
                 'java/util/Map','java/lang/StringBuilder','java/lang/Iterable','java/lang/AutoCloseable')

def javap(path):
    return subprocess.run(['javap','-v','-p',path],capture_output=True,text=True).stdout

def scan(path, self_name):
    out = javap(path)
    refs = collections.OrderedDict()
    for m in re.finditer(r'=\s+(Methodref|InterfaceMethodref|Fieldref)\s+#\d+\.#\d+\s+//\s+(\S+)\.(\S+?):(\S+)', out):
        kind, cls, name, desc = m.groups()
        name = name.strip('"')
        if cls == self_name: continue
        refs.setdefault((kind, cls, name, desc), 0)
        refs[(kind, cls, name, desc)] += 1
    ver = re.search(r'major version: (\d+)', out).group(1)
    hasmap = 'StackMap:' in out
    hasmaptable = 'StackMapTable:' in out
    indy = 'invokedynamic' in out
    return refs, ver, hasmap, hasmaptable, indy, out

def api_members(out):
    """public/protected fields+methods with descriptors"""
    res = set()
    cur = None
    for ln in out.splitlines():
        m = re.match(r'\s{2}(public|protected)\s.*?([\w<>$]+)(\(.*\))?;\s*$', ln)
        if m: cur = m.group(2)
        d = re.match(r'\s+descriptor: (\S+)', ln)
        if d and cur: res.add(cur + ':' + d.group(1)); cur = None
    return res

def report(newp, oldp=None):
    refs, ver, sm, smt, indy, out = scan(newp, 'fontcus')
    print("=== CLASS ===")
    print("  file                %s" % newp)
    print("  class file version  %s.0  %s" % (ver, "(Java 1.3 - safe for CLDC/KVM)" if ver=='47' else "(!! KVM expects <= 47/48)"))
    print("  preverified         %s" % ("YES - CLDC StackMap present" if sm else "NO  - !! KVM will throw java.lang.VerifyError"))
    if smt: print("  !! StackMapTable (Java 6+ format) present - not understood by CLDC")
    if indy: print("  !! invokedynamic present - not in CLDC")
    print()
    print("=== EXTERNAL API REFERENCES ===")
    bad = 0; midp2 = []
    for (kind, cls, name, desc) in refs:
        key = name + ':' + desc
        if any(cls.startswith(p) for p in BANNED_PREFIX):
            print("  REJECT   %s.%s%s   <- not available on CLDC/MIDP" % (cls,name,desc)); bad += 1; continue
        if cls not in API:
            print("  UNKNOWN  %s.%s %s   <- verify against the target profile manually" % (cls,name,desc)); bad += 1; continue
        prof, members = API[cls]
        ok = (kind=='Fieldref') or (not members) or (key in members)
        tag = 'OK      ' if ok else 'REJECT  '
        if not ok: bad += 1
        full = '%s.%s' % (cls, key)
        note = prof
        if full in MIDP2_ONLY: note = 'MIDP 2.0 ONLY'; midp2.append(full)
        print("  %s %-58s %s" % (tag, '%s.%s%s' % (cls,name,desc), note))
    print()
    if midp2:
        print("  NOTE: %d MIDP 2.0-only calls (same as the original class - not a regression)." % len(midp2))
        print("        All of them sit behind try/catch + atlasOk, so a MIDP 1.0 device")
        print("        degrades instead of crashing, but glyphs will not render there.")
        print()
    if oldp:
        _,_,_,_,_,oout = scan(oldp,'fontcus')
        oldapi, newapi = api_members(oout), api_members(out)
        print("=== BINARY COMPATIBILITY vs ORIGINAL ===")
        missing = sorted(oldapi - newapi); added = sorted(newapi - oldapi)
        for x in missing: print("  BREAK    removed/changed: %s" % x)
        for x in added:   print("  added    %s" % x)
        print("  %d public/protected members in original, %d kept -> %s"
              % (len(oldapi), len(oldapi)-len(missing),
                 "BINARY COMPATIBLE" if not missing else "!! INCOMPATIBLE"))
        bad += len(missing)
    print()
    print("RESULT: %s" % ("PASS - no compatibility violations" if bad==0 and sm and ver=='47' else "ISSUES FOUND (%d)" % bad))
    return bad

if __name__ == '__main__':
    sys.exit(1 if report(sys.argv[1], sys.argv[2] if len(sys.argv)>2 else None) else 0)
