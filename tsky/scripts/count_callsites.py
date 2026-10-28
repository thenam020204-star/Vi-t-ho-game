#!/usr/bin/env python3
"""Dem call-site ve chu theo bytecode tu javap -c -p (thu muc chua *.txt). In bang class.method:offset."""
import re, sys, glob, os, collections
PAT = re.compile(r'invoke(virtual|static|special|interface)\s+#\d+\s+//\s+(?:Method|InterfaceMethod)\s+(javax/microedition/lcdui/(?:Graphics|Font|Canvas)\.(?:drawString|drawChar|drawChars|drawSubstring|stringWidth|charWidth|charsWidth|substringWidth|getHeight|getBaselinePosition|setFont|getFont|getFace|getStyle|getSize|getDefaultFont|getFont)):?(\([^)]*\)\S*)?|(javax/microedition/lcdui/Font\.(?:getFont|getDefaultFont)):(\([^)]*\)\S*)')
def scan(d):
    rows = []
    for f in sorted(glob.glob(d + '/*.txt')):
        cls = os.path.basename(f)[:-4]; meth = None
        for ln in open(f, encoding='utf-8', errors='replace'):
            if ln.startswith('  static {}'): meth = '<clinit>'; continue
            m = re.match(r'  (?!  )(?:[\w ]*? )?([\w<>$.]+)\((.*)\)(?: throws .*)?;$', ln.rstrip('\n'))
            if m:
                ps = ','.join(x.split('.')[-1].replace('javax.microedition.lcdui.', '') for x in m.group(2).split(', ') if x)
                meth = m.group(1).split('.')[-1] + '(' + ps + ')'; continue
            m = re.match(r'\s+(\d+): (invoke\w+)\s+#\d+\s+// (?:Interface)?Method (javax/microedition/lcdui/(?:Graphics|Font)\.\S+)', ln)
            if m and re.search(r'drawString|drawChar|drawChars|drawSubstring|stringWidth|charWidth|charsWidth|substringWidth|getHeight|getBaselinePosition|setFont|getFont|getDefaultFont', m.group(3)):
                rows.append((cls, meth, int(m.group(1)), m.group(3)))
    return rows
if __name__ == '__main__':
    rows = scan(sys.argv[1])
    if '--list' in sys.argv:
        for r in rows: print('%s.%s:%d %s' % r)
    else:
        c = collections.Counter((r[0], r[3].split(':')[0].replace('javax/microedition/lcdui/', '')) for r in rows)
        for k, v in sorted(c.items()): print(k, v)
