import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Patch bytecode tinh cho "Tien Sung Ky Duyen" - Viet hoa (QD thiet ke 1-4, 2b trong PLAN.md).
 *   java -cp asm.jar:asm-tree.jar:. Patch <in_dir> <out_dir> <patch_log>
 * Chi ghi cac class co sua; class khac giu nguyen byte goc. Version class giu nguyen (45.3), COMPUTE_MAXS,
 * bo StackMap cu (ProGuard -microedition sinh lai). Moi patch phai khop DUNG so lan mong doi, sai -> exit 1.
 */
public class Patch {
    static final String G = "javax/microedition/lcdui/Graphics";
    static final String F = "javax/microedition/lcdui/Font";
    static final String VH = "VH";
    static final String STR = "Ljava/lang/String;";
    static PrintWriter log;
    static boolean failed = false;

    // ---- 5 loai call-site (QD 2): owner, name, desc, ten ham VH
    static final String[][] CALLS = {
        {G, "drawString", "(" + STR + "III)V", "drawString"},
        {F, "stringWidth", "(" + STR + ")I", "stringWidth"},
        {F, "charWidth", "(C)I", "charWidth"},
        {F, "substringWidth", "(" + STR + "II)I", "substringWidth"},
        {F, "getHeight", "()I", "getHeight"},
    };
    // ---- so call-site CON LAI sau khi da thay than method (docs/render.md muc 1-2): lop -> {drawString, stringWidth, charWidth, substringWidth, getHeight}
    static final Map<String, int[]> EXPECT = new LinkedHashMap<>();
    static {
        EXPECT.put("Main/a", new int[]{1, 0, 0, 0, 0});
        EXPECT.put("Main/e", new int[]{10, 8, 0, 0, 1});
        EXPECT.put("Main/f", new int[]{1, 2, 0, 0, 1});
        EXPECT.put("Main/g", new int[]{0, 0, 0, 0, 0});
        EXPECT.put("Main/j", new int[]{13, 12, 0, 0, 0});
        EXPECT.put("Main/k", new int[]{0, 1, 0, 0, 1});
        EXPECT.put("rf/b", new int[]{0, 1, 0, 0, 1});
        EXPECT.put("rf/c", new int[]{1, 0, 0, 0, 0});
        EXPECT.put("rf/e", new int[]{3, 0, 0, 0, 0});
    }
    // ---- so call-site TRUOC khi thay than method (de chung minh than method cu dung nhu phan tich)
    static final Map<String, int[]> BEFORE = new LinkedHashMap<>();
    static {
        BEFORE.put("Main/f", new int[]{11, 2, 0, 0, 1});
        BEFORE.put("Main/g", new int[]{1, 0, 2, 0, 0});
        BEFORE.put("rf/b", new int[]{10, 1, 0, 2, 1});
    }

    public static void main(String[] a) throws Exception {
        Path in = Paths.get(a[0]), out = Paths.get(a[1]);
        log = new PrintWriter(new FileWriter(a[2]));
        List<Path> files = new ArrayList<>();
        try (java.util.stream.Stream<Path> st = Files.walk(in)) {
            st.filter(p -> p.toString().endsWith(".class")).sorted().forEach(files::add);
        }
        int tot[] = new int[CALLS.length];
        int changedClasses = 0;
        for (Path f : files) {
            String name = in.relativize(f).toString().replace('\\', '/');
            name = name.substring(0, name.length() - 6);
            byte[] b = Files.readAllBytes(f);
            ClassNode cn = new ClassNode();
            new ClassReader(b).accept(cn, 0);
            boolean ch = patchClass(cn, name, tot);
            if (!ch) continue;
            stripStackMap(cn);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            Path o = out.resolve(name + ".class");
            Files.createDirectories(o.getParent());
            Files.write(o, cw.toByteArray());
            changedClasses++;
        }
        for (int i = 0; i < CALLS.length; i++) {
            int want = 0;
            for (int[] e : EXPECT.values()) want += e[i];
            expect("TONG " + CALLS[i][1], tot[i], want);
        }
        log.println("class da ghi: " + changedClasses);
        log.close();
        if (failed) {
            System.err.println("PATCH FAILED - xem log");
            System.exit(1);
        }
        System.out.println("PATCH OK (" + changedClasses + " class)");
    }

    static void expect(String what, int got, int want) {
        String m = (got == want ? "OK   " : "FAIL ") + what + ": " + got + " (mong doi " + want + ")";
        log.println(m);
        if (got != want) {
            failed = true;
            System.err.println(m);
        }
    }

    static boolean patchClass(ClassNode cn, String name, int[] tot) {
        boolean changed = false;
        int[] want = EXPECT.get(name);
        int[] before = BEFORE.get(name);
        if (before != null) {
            for (int i = 0; i < CALLS.length; i++) expect(name + " [truoc] " + CALLS[i][1], count(cn, CALLS[i]), before[i]);
        }
        // QD 2b: thay than method
        if (name.equals("Main/g")) {
            redirectBody(cn, "a", "([C" + "L" + G + ";IIIII)I", "dialog");
            changed = true;
        } else if (name.equals("rf/b")) {
            wrapBody(cn, "a", "(" + STR + "I)V", "c", "e");
            wrapBody(cn, "b", "(" + STR + "I)V", "d", "f");
            redirectBody(cn, "a", "(L" + G + ";" + STR + "IIIII)V", "outline");
            changed = true;
        } else if (name.equals("Main/f")) {
            redirectBody(cn, "a", "(L" + G + ";" + STR + "IIIII)V", "outline");
            changed = true;
        }
        if (name.equals("Main/f")) {
            patchBoot(cn);
            changed = true;
        }
        // QD 2: thay call-site
        for (int i = 0; i < CALLS.length; i++) {
            int n = redirectCalls(cn, CALLS[i][0], CALLS[i][1], CALLS[i][2], CALLS[i][3]);
            tot[i] += n;
            if (n > 0) changed = true;
            int w = want == null ? 0 : want[i];
            if (n != w || n > 0) expect(name + " " + CALLS[i][1], n, w);
        }
        if (name.equals("Main/j")) {
            patchMainJ(cn);
            changed = true;
        } else if (name.equals("Main/e")) {
            patchShop(cn);
            changed = true;
        }
        if (name.equals("Main/j") || name.equals("Main/e") || name.equals("rf/b")) {
            int c = cacheConcat(cn);
            if (c > 0) changed = true;
        }
        return changed;
    }

    static int count(ClassNode cn, String[] c) {
        int n = 0;
        for (MethodNode m : cn.methods) {
            for (AbstractInsnNode x = m.instructions.getFirst(); x != null; x = x.getNext()) {
                if (isCall(x, Opcodes.INVOKEVIRTUAL, c[0], c[1], c[2])) n++;
            }
        }
        return n;
    }

    static void stripStackMap(ClassNode cn) {
        if (cn.attrs != null) cn.attrs.removeIf(x -> x.type.equals("StackMap"));
        for (MethodNode m : cn.methods) {
            if (m.attrs != null) m.attrs.removeIf(x -> x.type.equals("StackMap") || x.type.equals("StackMapTable"));
        }
    }

    static MethodNode method(ClassNode cn, String n, String d) {
        for (MethodNode m : cn.methods) if (m.name.equals(n) && m.desc.equals(d)) return m;
        throw new RuntimeException("khong thay " + cn.name + "." + n + d);
    }

    static void clearBody(MethodNode m) {
        m.instructions.clear();
        m.tryCatchBlocks.clear();
        m.localVariables = null;
        m.visibleLocalVariableAnnotations = null;
        m.invisibleLocalVariableAnnotations = null;
    }

    /** Thay toan bo than method: nap tham so (khong gom this) -> invokestatic VH.target(desc) -> return. */
    static void redirectBody(ClassNode cn, String n, String d, String target) {
        MethodNode m = method(cn, n, d);
        clearBody(m);
        InsnList L = new InsnList();
        int slot = (m.access & Opcodes.ACC_STATIC) != 0 ? 0 : 1;
        for (Type t : Type.getArgumentTypes(d)) {
            L.add(new VarInsnNode(t.getOpcode(Opcodes.ILOAD), slot));
            slot += t.getSize();
        }
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, target, d, false));
        L.add(new InsnNode(Type.getReturnType(d).getOpcode(Opcodes.IRETURN)));
        m.instructions = L;
        log.println("BODY " + cn.name + "." + n + d + " -> VH." + target);
    }

    /** rf.b.a/b(String,int) (static): arr = <cn>.<arrField>; <cn>.<cntField> = VH.wrap(s, w, arr). */
    static void wrapBody(ClassNode cn, String n, String d, String arrField, String cntField) {
        MethodNode m = method(cn, n, d);
        clearBody(m);
        InsnList L = new InsnList();
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new VarInsnNode(Opcodes.ILOAD, 1));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, cn.name, arrField, "[" + STR));
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "wrap", "(" + STR + "I[" + STR + ")I", false));
        L.add(new FieldInsnNode(Opcodes.PUTSTATIC, cn.name, cntField, "I"));
        L.add(new InsnNode(Opcodes.RETURN));
        m.instructions = L;
        log.println("BODY " + cn.name + "." + n + d + " -> VH.wrap(" + arrField + "," + cntField + ")");
    }

    /** invokevirtual owner.name(desc) -> invokestatic VH.target(Lowner;desc). */
    static int redirectCalls(ClassNode cn, String owner, String name, String desc, String target) {
        String nd = "(L" + owner + ";" + desc.substring(1);
        int n = 0;
        for (MethodNode m : cn.methods) {
            AbstractInsnNode x = m.instructions.getFirst();
            while (x != null) {
                AbstractInsnNode nx = x.getNext();
                if (isCall(x, Opcodes.INVOKEVIRTUAL, owner, name, desc)) {
                    m.instructions.set(x, new MethodInsnNode(Opcodes.INVOKESTATIC, VH, target, nd, false));
                    n++;
                }
                x = nx;
            }
        }
        return n;
    }

    static AbstractInsnNode next(AbstractInsnNode x) {
        x = x.getNext();
        while (x != null && (x instanceof LabelNode || x instanceof LineNumberNode || x instanceof FrameNode)) x = x.getNext();
        return x;
    }

    static AbstractInsnNode prev(AbstractInsnNode x) {
        x = x.getPrevious();
        while (x != null && (x instanceof LabelNode || x instanceof LineNumberNode || x instanceof FrameNode)) x = x.getPrevious();
        return x;
    }

    static boolean isField(AbstractInsnNode x, int op, String owner, String name) {
        return x != null && x.getOpcode() == op && ((FieldInsnNode) x).owner.equals(owner) && ((FieldInsnNode) x).name.equals(name);
    }

    static boolean isCall(AbstractInsnNode x, int op, String owner, String name, String desc) {
        if (x == null || x.getOpcode() != op) return false;
        MethodInsnNode m = (MethodInsnNode) x;
        return m.owner.equals(owner) && m.name.equals(name) && m.desc.equals(desc);
    }

    static int intValue(AbstractInsnNode x) {
        int op = x.getOpcode();
        if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) return op - Opcodes.ICONST_0;
        if (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH) return ((IntInsnNode) x).operand;
        if (op == Opcodes.LDC && ((LdcInsnNode) x).cst instanceof Integer) return (Integer) ((LdcInsnNode) x).cst;
        return Integer.MIN_VALUE;
    }

    // ===================================================================== T7: bo cuc
    static MethodInsnNode vhCall(String name, String desc) {
        return new MethodInsnNode(Opcodes.INVOKESTATIC, VH, name, desc, false);
    }

    /** Doi cac call VH.drawString trong method (name, (Graphics)V) thanh VH.<to>; phai dung `want` lan. */
    static void retarget(ClassNode cn, String mname, String to, int want) {
        MethodNode m = method(cn, mname, "(L" + G + ";)V");
        int n = 0;
        for (AbstractInsnNode x = m.instructions.getFirst(); x != null; x = x.getNext()) {
            if (x instanceof MethodInsnNode && x.getOpcode() == Opcodes.INVOKESTATIC) {
                MethodInsnNode mi = (MethodInsnNode) x;
                if (mi.owner.equals(VH) && mi.name.equals("drawString")) {
                    mi.name = to;
                    n++;
                }
            }
        }
        expect(cn.name + "." + mname + " drawString -> VH." + to, n, want);
    }

    static void patchMainJ(ClassNode cn) {
        // phim mem phai ("Thoat"/"Quay lai", x = t - k.w*2 - 1) va "Tam dung" (x = t/2 - k.w): can theo do rong that
        retarget(cn, "y", "drawRight", 1);
        retarget(cn, "z", "drawRight", 1);
        retarget(cn, "i", "drawMid", 1);

        // menu chinh f(Graphics): (1) bo muc 2 ("Game khac", platformRequest quang cao), (2) mui ten theo nhan dang chon
        MethodNode f = method(cn, "f", "(L" + G + ";)V");
        InsnList pre = new InsnList();
        pre.add(new VarInsnNode(Opcodes.ALOAD, 0));
        pre.add(new VarInsnNode(Opcodes.ALOAD, 0));
        pre.add(new FieldInsnNode(Opcodes.GETFIELD, "Main/j", "cR", "I"));
        String[] busy = {"bS", "bX", "bC", "bk", "bs", "bL"};
        for (int i = 0; i < busy.length; i++) {
            pre.add(new FieldInsnNode(Opcodes.GETSTATIC, "Main/j", busy[i], "Z"));
            if (i > 0) pre.add(new InsnNode(Opcodes.IOR));
        }
        pre.add(vhCall("menuSkip", "(II)I"));
        pre.add(new FieldInsnNode(Opcodes.PUTFIELD, "Main/j", "cR", "I"));
        f.instructions.insert(pre);
        int arrows = 0;
        for (AbstractInsnNode x = f.instructions.getFirst(); x != null; x = x.getNext()) {
            if (x.getOpcode() == Opcodes.ALOAD && ((VarInsnNode) x).var == 2) {
                AbstractInsnNode a1 = x.getNext();
                AbstractInsnNode a2 = a1 == null ? null : a1.getNext();
                AbstractInsnNode a3 = a2 == null ? null : a2.getNext();
                if (a1 != null && a1.getOpcode() == Opcodes.ICONST_1 && a2 != null && a2.getOpcode() == Opcodes.AALOAD
                        && a3 != null && a3.getOpcode() == Opcodes.INVOKESTATIC && ((MethodInsnNode) a3).name.equals("stringWidth")) {
                    InsnList r = new InsnList();
                    r.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    r.add(new FieldInsnNode(Opcodes.GETFIELD, "Main/j", "cR", "I"));
                    f.instructions.insert(a1, r);
                    f.instructions.remove(a1);
                    arrows++;
                }
            }
        }
        expect("Main/j.f: mui ten menu theo nhan dang chon", arrows, 2);

        // o mo ta cuon doc a(G,String,IIIII)V: cuon theo trang (hang + thay trang), khong cat nua dong
        MethodNode a = method(cn, "a", "(L" + G + ";" + STR + "IIIII)V");
        clearBody(a);
        InsnList L = new InsnList();
        LabelNode skip = new LabelNode();
        // g=1 s=2 w=3 h=4 x=5 y=6 color=7
        L.add(new VarInsnNode(Opcodes.ALOAD, 1));
        L.add(new VarInsnNode(Opcodes.ILOAD, 5));
        L.add(new VarInsnNode(Opcodes.ILOAD, 6));
        L.add(new VarInsnNode(Opcodes.ILOAD, 3));
        L.add(new VarInsnNode(Opcodes.ILOAD, 4));
        L.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, G, "setClip", "(IIII)V", false));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "Main/j", "bE", "Z"));
        L.add(new JumpInsnNode(Opcodes.IFEQ, skip));
        L.add(new VarInsnNode(Opcodes.ALOAD, 2));
        L.add(new VarInsnNode(Opcodes.ILOAD, 3));
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "rf/b", "a", "(" + STR + "I)V", false));
        L.add(new InsnNode(Opcodes.ICONST_0));
        L.add(new FieldInsnNode(Opcodes.PUTSTATIC, "Main/j", "bE", "Z"));
        L.add(new InsnNode(Opcodes.ICONST_0));
        L.add(new FieldInsnNode(Opcodes.PUTSTATIC, "Main/j", "cW", "I"));
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new InsnNode(Opcodes.ICONST_0));
        L.add(new FieldInsnNode(Opcodes.PUTFIELD, "Main/j", "X", "I"));
        L.add(skip);
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new VarInsnNode(Opcodes.ALOAD, 1));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "rf/b", "c", "[" + STR));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "rf/b", "e", "I"));
        L.add(new VarInsnNode(Opcodes.ILOAD, 5));
        L.add(new VarInsnNode(Opcodes.ILOAD, 6));
        L.add(new VarInsnNode(Opcodes.ILOAD, 3));
        L.add(new VarInsnNode(Opcodes.ILOAD, 4));
        L.add(new VarInsnNode(Opcodes.ILOAD, 7));
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new FieldInsnNode(Opcodes.GETFIELD, "Main/j", "X", "I"));
        L.add(vhCall("scroll", "(L" + G + ";[" + STR + "IIIIIII)I"));
        L.add(new FieldInsnNode(Opcodes.PUTFIELD, "Main/j", "X", "I"));
        L.add(new InsnNode(Opcodes.RETURN));
        a.instructions = L;
        log.println("BODY Main/j.a(G,String,IIIII) -> VH.scroll (cuon theo trang)");
    }

    /** Cua hang Main/e.a(G,String[][],...): cot so luong/gia dat sau ten DAI NHAT (khong phai ten hang dau). */
    static void patchShop(ClassNode cn) {
        MethodNode m = method(cn, "a", "(L" + G + ";[[" + STR + "IIIIIIZI)I");
        int n = 0;
        for (AbstractInsnNode x = m.instructions.getFirst(); x != null; x = x.getNext()) {
            if (x.getOpcode() == Opcodes.ALOAD && ((VarInsnNode) x).var == 2) {
                AbstractInsnNode a1 = x.getNext(), a2 = a1 == null ? null : a1.getNext(), a3 = a2 == null ? null : a2.getNext(),
                        a4 = a3 == null ? null : a3.getNext(), a5 = a4 == null ? null : a4.getNext();
                if (a1 != null && a1.getOpcode() == Opcodes.ICONST_0 && a2 != null && a2.getOpcode() == Opcodes.AALOAD
                        && a3 != null && a3.getOpcode() == Opcodes.ICONST_0 && a4 != null && a4.getOpcode() == Opcodes.AALOAD
                        && a5 != null && a5.getOpcode() == Opcodes.INVOKESTATIC && ((MethodInsnNode) a5).name.equals("stringWidth")) {
                    InsnList r = new InsnList();
                    r.add(new InsnNode(Opcodes.ICONST_0));
                    r.add(vhCall("widest", "([[" + STR + "I)" + STR));
                    m.instructions.insert(a4, r);
                    m.instructions.remove(a1);
                    m.instructions.remove(a2);
                    m.instructions.remove(a3);
                    m.instructions.remove(a4);
                    n++;
                }
            }
        }
        expect("Main/e.a(String[][]): cot theo ten dai nhat", n, 1);
    }

    /**
     * Noi chuoi toan String ("a" + b + "^") moi khung hinh: thay chuoi new StringBuffer().append(S)...toString() bang
     * VH.catN(a,b,..,slot): neu cac phan tu giu nguyen (so sanh tham chieu) thi tra lai ket qua da noi (khong cap phat).
     * Chi xu ly chuoi chi co append(String) voi doi so la 1 lenh don (LDC/ALOAD/GETSTATIC/this.field).
     */
    static int catSlots = 0;

    static int cacheConcat(ClassNode cn) {
        int total = 0;
        String sb = "java/lang/StringBuffer";
        for (MethodNode m : cn.methods) {
            AbstractInsnNode x = m.instructions.getFirst();
            while (x != null) {
                AbstractInsnNode nx = x.getNext();
                if (x.getOpcode() == Opcodes.NEW && ((TypeInsnNode) x).desc.equals(sb)) {
                    AbstractInsnNode d = next(x), ini = d == null ? null : next(d);
                    if (d != null && d.getOpcode() == Opcodes.DUP && ini != null && isCall(ini, Opcodes.INVOKESPECIAL, sb, "<init>", "()V")) {
                        List<InsnList> args = new ArrayList<>();
                        List<AbstractInsnNode> all = new ArrayList<>();
                        AbstractInsnNode c = next(ini);
                        boolean ok = true;
                        AbstractInsnNode end = null;
                        while (ok) {
                            // doi so: 1 lenh (LDC String / ALOAD / GETSTATIC) hoac ALOAD0+GETFIELD
                            List<AbstractInsnNode> g = new ArrayList<>();
                            AbstractInsnNode a = c;
                            if (a == null) { ok = false; break; }
                            int op = a.getOpcode();
                            if (op == Opcodes.LDC && ((LdcInsnNode) a).cst instanceof String) { g.add(a); c = next(a); }
                            else if (op == Opcodes.ALOAD && ((VarInsnNode) a).var != 0) { g.add(a); c = next(a); }
                            else if (op == Opcodes.GETSTATIC && ((FieldInsnNode) a).desc.equals(STR)) { g.add(a); c = next(a); }
                            else if (op == Opcodes.ALOAD && ((VarInsnNode) a).var == 0 && next(a) != null && next(a).getOpcode() == Opcodes.GETFIELD
                                    && ((FieldInsnNode) next(a)).desc.equals(STR)) { g.add(a); g.add(next(a)); c = next(next(a)); }
                            else { ok = false; break; }
                            if (c == null || !isCall(c, Opcodes.INVOKEVIRTUAL, sb, "append", "(" + STR + ")L" + sb + ";")) { ok = false; break; }
                            InsnList il = new InsnList();
                            for (AbstractInsnNode q : g) all.add(q);
                            args.add(null);
                            all.add(c);
                            c = next(c);
                            if (c != null && isCall(c, Opcodes.INVOKEVIRTUAL, sb, "toString", "()" + STR)) { end = c; break; }
                        }
                        if (ok && end != null && args.size() >= 2 && args.size() <= 4) {
                            // gom lenh doi so (bo cac append)
                            List<AbstractInsnNode> keep = new ArrayList<>();
                            for (AbstractInsnNode q : all) if (!(q instanceof MethodInsnNode)) keep.add(q);
                            InsnList rep = new InsnList();
                            // doi so duoc giu nguyen thu tu; dem cho du 4 doi so
                            for (AbstractInsnNode q : keep) {
                                m.instructions.remove(q);
                                rep.add(q);
                            }
                            for (int i = args.size(); i < 4; i++) rep.add(new InsnNode(Opcodes.ACONST_NULL));
                            rep.add(new LdcInsnNode(catSlots++));
                            rep.add(vhCall("cat", "(" + STR + STR + STR + STR + "I)" + STR));
                            m.instructions.insertBefore(x, rep);
                            // xoa new/dup/init + cac append + toString
                            for (AbstractInsnNode q : all) if (q instanceof MethodInsnNode) m.instructions.remove(q);
                            m.instructions.remove(end);
                            m.instructions.remove(ini);
                            m.instructions.remove(d);
                            nx = x.getNext();
                            m.instructions.remove(x);
                            total++;
                        }
                    }
                }
                x = nx;
            }
        }
        log.println("cacheConcat " + cn.name + ": " + total);
        return total;
    }

    // ===================================================================== Main/f: khoi dong (QD 9)
    /**
     * - c(): chi nap /Image/namp.png (VH.logo()) vao this.h, bo bg/logo0-5/logo51/52/menu thu nho.
     * - paint pha u: ve h giua man hinh (y = s/2, khong lech -40).
     * - run(): sau 2s logo, TRUOC khi u=false: g=true; Main.j.bY=true; w=false; v=false; this.e() (bat nhac) -> bo qua hoi nhac + hoat hinh logo,
     *   di thang vao khoi finally goc (chuyen canvas, a.d().a(2), start thread).
     */
    static void patchBoot(ClassNode cn) {
        MethodNode c = method(cn, "c", "()V");
        clearBody(c);
        InsnList L = new InsnList();
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "logo", "()Ljavax/microedition/lcdui/Image;", false));
        L.add(new FieldInsnNode(Opcodes.PUTFIELD, "Main/f", "h", "Ljavax/microedition/lcdui/Image;"));
        L.add(new InsnNode(Opcodes.RETURN));
        c.instructions = L;
        log.println("BODY Main/f.c() -> chi nap namp.png");

        MethodNode p = method(cn, "paint", "(L" + G + ";)V");
        int n40 = 0, shifted = 0;
        for (AbstractInsnNode x = p.instructions.getFirst(); x != null; x = x.getNext()) {
            if (x.getOpcode() == Opcodes.BIPUSH && ((IntInsnNode) x).operand == 40) {
                n40++;
                if (n40 <= 2) {
                    p.instructions.set(x, x = new InsnNode(Opcodes.ICONST_0));
                    shifted++;
                }
            }
        }
        expect("Main/f.paint: bipush 40 (2 dau la pha u) -> 0", n40 * 10 + shifted, 42);

        MethodNode r = method(cn, "run", "()V");
        int ins = 0;
        for (AbstractInsnNode x = r.instructions.getFirst(); x != null; x = x.getNext()) {
            if (isField(x, Opcodes.PUTSTATIC, "Main/f", "u")) {
                AbstractInsnNode pv = prev(x);
                if (pv != null && pv.getOpcode() == Opcodes.ICONST_0) {
                    InsnList a = new InsnList();
                    a.add(new InsnNode(Opcodes.ICONST_1));
                    a.add(new FieldInsnNode(Opcodes.PUTSTATIC, "Main/f", "g", "Z"));
                    a.add(new InsnNode(Opcodes.ICONST_1));
                    a.add(new FieldInsnNode(Opcodes.PUTSTATIC, "Main/j", "bY", "Z"));
                    a.add(new InsnNode(Opcodes.ICONST_0));
                    a.add(new FieldInsnNode(Opcodes.PUTSTATIC, "Main/f", "w", "Z"));
                    a.add(new InsnNode(Opcodes.ICONST_0));
                    a.add(new FieldInsnNode(Opcodes.PUTSTATIC, "Main/f", "v", "Z"));
                    a.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    a.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "Main/f", "e", "()V", false));
                    r.instructions.insertBefore(pv, a);
                    ins++;
                }
            }
        }
        expect("Main/f.run: chen bat nhac + bo hoi nhac/logo truoc u=false", ins, 1);
    }
}
