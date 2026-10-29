import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Patch bytecode tinh cho "Tinh Van Truyen Thuyet" - Viet hoa.
 *   java -cp asm.jar:asm-tree.jar:. Patch <ban: 240|320|360> <in_dir> <out_dir> <patch_log>
 * in_dir = thu muc goc jar (da repack), chua src/*.class. Chi ghi class co sua; class khac giu nguyen byte.
 * Giu version class, ten/descriptor/access flag; COMPUTE_MAXS; bo StackMap cu (ProGuard -microedition sinh lai).
 * Moi patch phai khop DUNG so lan mong doi, sai -> exit 1.
 */
public class Patch {
    static final String G = "javax/microedition/lcdui/Graphics";
    static final String STR = "Ljava/lang/String;";
    static final String VH = "src/VH";
    static final String GD = "L" + G + ";";
    static PrintWriter log;
    static boolean failed = false;

    /** Cau hinh tung ban: class ve chu, ten field Graphics/gradient/bang mau, tam man hinh, so drawString. */
    static class Cfg {
        String cls, fG, fGrad, fCtab, boot, midlet;
        int cx, drawStrings;
        String mDrawG, mDrawC, mLines, mLinesTW, mLinesFade, mWidth, mWidthRaw, mTw, mWrap;
    }

    static Cfg cfg(String v) {
        Cfg c = new Cfg();
        if (v.equals("240") || v.equals("320")) {
            c.cls = "src/b"; c.fG = "a"; c.fGrad = "g"; c.fCtab = "a";
            c.cx = v.equals("240") ? 120 : 160;
            c.drawStrings = 11;
            c.boot = "src/q"; c.midlet = "src/GameMidlet";
            c.mDrawG = c.mDrawC = c.mLines = c.mLinesTW = c.mWidth = c.mWrap = "a";
            c.mLinesFade = c.mTw = "b";
            c.mWidthRaw = "c";
            return c;
        }
        throw new IllegalArgumentException("ban chua ho tro: " + v);
    }

    public static void main(String[] a) throws Exception {
        Cfg c = cfg(a[0]);
        Path in = Paths.get(a[1]), out = Paths.get(a[2]);
        log = new PrintWriter(new FileWriter(a[3]));
        log.println("ban " + a[0] + " class " + c.cls);
        write(in, out, c.cls, 1, c);
        write(in, out, c.boot, 2, c);
        log.close();
        if (failed) {
            System.err.println("PATCH FAILED - xem log");
            System.exit(1);
        }
        System.out.println("PATCH OK");
    }

    static void write(Path in, Path out, String cls, int kind, Cfg c) throws IOException {
        ClassNode cn = new ClassNode();
        new ClassReader(Files.readAllBytes(in.resolve(cls + ".class"))).accept(cn, 0);
        if (kind == 1) patchText(cn, c); else patchBoot(cn, c);
        stripStackMap(cn);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        Path o = out.resolve(cls + ".class");
        Files.createDirectories(o.getParent());
        Files.write(o, cw.toByteArray());
    }

    /**
     * Khoi dong NAMP: splash cu (logo nhom upload, hoi am thanh, logo nha phat hanh) -> logo NAMP 2 giay.
     * run(): VH.boot(this, this.player); <am luong static int> = 4 (= chon "Co"); this.midlet.startGame(1)
     * paint(G): VH.bootPaint(g, this)     a()V (nap anh splash cu): rong
     */
    static void patchBoot(ClassNode cn, Cfg c) {
        String PL = "Ljavax/microedition/media/Player;", CV = "Ljavax/microedition/lcdui/Canvas;";
        expect("field Player", findField(cn, "a", PL) == null ? 0 : 1, 1);
        expect("field midlet", findField(cn, "a", "L" + c.midlet + ";") == null ? 0 : 1, 1);
        FieldNode vol = findField(cn, "a", "I");
        expect("static int am luong", vol != null && (vol.access & Opcodes.ACC_STATIC) != 0 ? 1 : 0, 1);
        MethodNode run = method(cn, "run", "()V"), paint = method(cn, "paint", "(" + GD + ")V"), img = method(cn, "a", "()V");
        expect("q.run/paint/a()", (run != null ? 1 : 0) + (paint != null ? 1 : 0) + (img != null ? 1 : 0), 3);
        if (run == null || paint == null || img == null) return;
        InsnList L = new InsnList();
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new FieldInsnNode(Opcodes.GETFIELD, cn.name, "a", PL));
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "boot", "(" + CV + PL + ")V", false));
        L.add(new InsnNode(Opcodes.ICONST_4));
        L.add(new FieldInsnNode(Opcodes.PUTSTATIC, cn.name, "a", "I"));
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new FieldInsnNode(Opcodes.GETFIELD, cn.name, "a", "L" + c.midlet + ";"));
        L.add(new InsnNode(Opcodes.ICONST_1));
        L.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, c.midlet, "startGame", "(B)V", false));
        L.add(new InsnNode(Opcodes.RETURN));
        setBody(run, L);
        L = new InsnList();
        L.add(new VarInsnNode(Opcodes.ALOAD, 1));
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "bootPaint", "(" + GD + CV + ")V", false));
        L.add(new InsnNode(Opcodes.RETURN));
        setBody(paint, L);
        L = new InsnList();
        L.add(new InsnNode(Opcodes.RETURN));
        setBody(img, L);
        log.println("BODY " + cn.name + ".run/paint/a() -> logo NAMP, am thanh bat (4)");
    }

    static void setBody(MethodNode m, InsnList L) {
        m.instructions = L;
        m.tryCatchBlocks = new ArrayList<>();
        m.localVariables = null;
    }

    static void expect(String what, int got, int want) {
        String m = (got == want ? "OK   " : "FAIL ") + what + ": " + got + " (mong doi " + want + ")";
        log.println(m);
        if (got != want) {
            failed = true;
            System.err.println(m);
        }
    }

    static void patchText(ClassNode cn, Cfg c) {
        String gF = "F:" + c.fG + ":" + GD, gr = "F:" + c.fGrad + ":[I", ct = "F:" + c.fCtab + ":[[I";
        body(cn, c.mDrawG, "(" + STR + "III[B)I", "drawG", "(" + GD + "[I[[I" + STR + "III)I",
                gF, gr, ct, "A1", "I2", "I3", "I4");
        body(cn, c.mDrawC, "(" + STR + "III[BI)V", "drawC", "(" + GD + STR + "IIII)V",
                gF, "A1", "I2", "I3", "I4", "I6");
        body(cn, c.mLines, "([" + STR + "IIIIII)V", "lines", "(" + GD + "[I[[I[" + STR + "IIIII)V",
                gF, gr, ct, "A1", "I2", "I3", "I4", "I5", "I6");
        body(cn, c.mLinesTW, "([" + STR + "IIIIIIB)V", "linesTW", "(" + GD + "[I[[I[" + STR + "IIIII)V",
                gF, gr, ct, "A1", "I2", "I3", "I5", "I6", "I8");
        body(cn, c.mLinesFade, "([" + STR + "IIIIII)V", "linesFade", "(" + GD + "[I[[I[" + STR + "IIIII)V",
                gF, gr, ct, "A1", "K" + c.cx, "I3", "I5", "I6", "I7");
        body(cn, c.mWidth, "(" + STR + ")I", "width", "(" + STR + ")I", "A1");
        body(cn, c.mWidthRaw, "(" + STR + ")I", "widthRaw", "(" + STR + ")I", "A1");
        body(cn, c.mTw, "(" + STR + ")I", "twUnits", "(" + STR + ")I", "A0");
        body(cn, c.mWrap, "(" + STR + "II)[" + STR, "wrap", "(" + STR + "I)[" + STR, "A1", "I2");
        int n = redirectCalls(cn, G, "drawString", "(" + STR + "III)V", "drawString", "(" + GD + STR + "III)V");
        expect(c.cls + " Graphics.drawString -> VH.drawString", n, c.drawStrings);
    }

    static MethodNode method(ClassNode cn, String n, String d) {
        MethodNode r = null;
        for (MethodNode m : cn.methods) if (m.name.equals(n) && m.desc.equals(d)) r = m;
        return r;
    }

    /** Thay toan bo than method: nap cac gia tri theo spec -> invokestatic VH.target -> return dung kieu. */
    static void body(ClassNode cn, String n, String d, String target, String tdesc, String... spec) {
        MethodNode m = method(cn, n, d);
        expect("than " + cn.name + "." + n + d + " -> VH." + target, m == null ? 0 : 1, 1);
        if (m == null) return;
        boolean isStatic = (m.access & Opcodes.ACC_STATIC) != 0;
        InsnList L = new InsnList();
        for (String s : spec) {
            char k = s.charAt(0);
            if (k == 'F') {
                String[] p = s.split(":", 3);
                if (isStatic) throw new IllegalStateException("field tren method static");
                if (findField(cn, p[1], p[2]) == null) expect("field " + p[1] + p[2], 0, 1);
                L.add(new VarInsnNode(Opcodes.ALOAD, 0));
                L.add(new FieldInsnNode(Opcodes.GETFIELD, cn.name, p[1], p[2]));
            } else if (k == 'A') {
                L.add(new VarInsnNode(Opcodes.ALOAD, Integer.parseInt(s.substring(1))));
            } else if (k == 'I') {
                L.add(new VarInsnNode(Opcodes.ILOAD, Integer.parseInt(s.substring(1))));
            } else if (k == 'K') {
                int v = Integer.parseInt(s.substring(1));
                L.add(v >= -32768 && v <= 32767 ? new IntInsnNode(Opcodes.SIPUSH, v) : new LdcInsnNode(v));
            }
        }
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, target, tdesc, false));
        Type rt = Type.getReturnType(d);
        if (!rt.equals(Type.getReturnType(tdesc))) throw new IllegalStateException("kieu tra ve lech " + n + d);
        L.add(new InsnNode(rt.getOpcode(Opcodes.IRETURN)));
        m.instructions = L;
        m.tryCatchBlocks = new ArrayList<>();
        m.localVariables = null;
        m.visibleLocalVariableAnnotations = null;
        m.invisibleLocalVariableAnnotations = null;
    }

    static FieldNode findField(ClassNode cn, String n, String d) {
        for (FieldNode f : cn.fields) if (f.name.equals(n) && f.desc.equals(d)) return f;
        return null;
    }

    /** invokevirtual owner.name(desc) -> invokestatic VH.target(tdesc), cung hinh dang stack. */
    static int redirectCalls(ClassNode cn, String owner, String name, String desc, String target, String tdesc) {
        int k = 0;
        for (MethodNode m : cn.methods) {
            List<MethodInsnNode> hit = new ArrayList<>();
            for (AbstractInsnNode x = m.instructions.getFirst(); x != null; x = x.getNext()) {
                if (x instanceof MethodInsnNode) {
                    MethodInsnNode mi = (MethodInsnNode) x;
                    if (mi.getOpcode() == Opcodes.INVOKEVIRTUAL && mi.owner.equals(owner) && mi.name.equals(name) && mi.desc.equals(desc)) hit.add(mi);
                }
            }
            for (MethodInsnNode mi : hit) m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, VH, target, tdesc, false));
            k += hit.size();
        }
        return k;
    }

    static void stripStackMap(ClassNode cn) {
        for (MethodNode m : cn.methods) {
            if (m.instructions == null) continue;
            for (AbstractInsnNode x = m.instructions.getFirst(); x != null; ) {
                AbstractInsnNode nx = x.getNext();
                if (x instanceof FrameNode) m.instructions.remove(x);
                x = nx;
            }
        }
        if (cn.attrs != null) cn.attrs.clear();
    }
}
