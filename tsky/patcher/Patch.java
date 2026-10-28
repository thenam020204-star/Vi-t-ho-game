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
        // QD 2: thay call-site
        for (int i = 0; i < CALLS.length; i++) {
            int n = redirectCalls(cn, CALLS[i][0], CALLS[i][1], CALLS[i][2], CALLS[i][3]);
            tot[i] += n;
            if (n > 0) changed = true;
            int w = want == null ? 0 : want[i];
            if (n != w || n > 0) expect(name + " " + CALLS[i][1], n, w);
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
}
