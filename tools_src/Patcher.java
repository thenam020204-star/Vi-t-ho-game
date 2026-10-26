// Patcher bytecode (chay tren JDK may tinh, dung ASM 9). Sua tinh class game truoc khi preverify.
//   java -cp asm.jar:asm-tree.jar:. Patcher <in_class_dir> <out_dir> [n73|e62]
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Patcher {
    static final String G = "javax/microedition/lcdui/Graphics";
    static final String S = "java/lang/String";
    static int total = 0;
    static String[] F;

    public static void main(String[] a) throws Exception {
        String in = a[0], out = a[1];
        String v = a.length > 2 ? a[2] : "n73";
        // ten field khung thoai (obfuscator dat ten khac nhau moi ban): da, eu(String), ey(tu), ev(so sign),
        // ex(mau), et(ae), ez(den) -- doi chieu tung lenh getstatic/putstatic cua n.a(Graphics,short[],int,int[],int)
        F = v.equals("e62") ? new String[]{"cY", "er", "ev", "es", "eu", "eq", "ew"}
                            : new String[]{"da", "eu", "ey", "ev", "ex", "et", "ez"};
        System.out.println("bien the " + v + ": field khung thoai " + Arrays.toString(F));
        new File(out).mkdirs();
        patch(in, out, "z", cn -> {
            delegate(cn, "a", "(Ljava/lang/String;Ljavax/microedition/lcdui/Font;I)[Ljava/lang/String;", "a");
            delegate(cn, "a", "(Ljava/lang/String;I)[Ljava/lang/String;", "a");
            delegate(cn, "a", "(L" + G + ";Ljava/lang/String;I[SII)I", "a");
            drawString(cn); stringWidth(cn);
        });
        patch(in, out, "cb", cn -> {
            delegate(cn, "a", "(L" + G + ";Ljava/lang/String;IIIIIIIII)I", "b");
            delegate(cn, "a", "(L" + G + ";Ljava/lang/String;IIIIIIII)I", "c");
            delegate(cn, "a", "(L" + G + ";Ljava/lang/String;Lbn;II)V", "m");
        });
        patch(in, out, "n", cn -> {
            dialogue(cn); drawString(cn); stringWidth(cn); lenMul(cn, 14);
        });
        patch(in, out, "al", cn -> drawString(cn));
        patch(in, out, "bb", cn -> { drawString(cn); lenMul(cn, 12); });
        patch(in, out, "ag", cn -> lenMul(cn, 14));
        System.out.println("TONG thay doi: " + total);
        if (total != 33) throw new RuntimeException("so cho va " + total + " != 33 (khac ban N73) -> kiem tra lai");
    }

    interface Fn { void run(ClassNode cn) throws Exception; }

    static void patch(String in, String out, String name, Fn f) throws Exception {
        byte[] b = Files.readAllBytes(Paths.get(in, name + ".class"));
        ClassReader cr = new ClassReader(b);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);
        f.run(cn);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        Files.write(Paths.get(out, name + ".class"), cw.toByteArray());
    }

    static MethodNode find(ClassNode cn, String n, String d) {
        for (MethodNode m : cn.methods) if (m.name.equals(n) && m.desc.equals(d)) return m;
        throw new RuntimeException("khong thay " + cn.name + "." + n + d);
    }

    static void clear(MethodNode m) {
        m.instructions.clear();
        m.tryCatchBlocks.clear();
        if (m.localVariables != null) m.localVariables.clear();
    }

    /* than method static -> return vh.<to>(cung tham so) */
    static void delegate(ClassNode cn, String n, String d, String to) {
        MethodNode m = find(cn, n, d);
        if ((m.access & Opcodes.ACC_STATIC) == 0) throw new RuntimeException("can static");
        clear(m);
        InsnList L = m.instructions;
        int slot = 0;
        for (Type t : Type.getArgumentTypes(d)) { L.add(new VarInsnNode(t.getOpcode(Opcodes.ILOAD), slot)); slot += t.getSize(); }
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "vh", to, d, false));
        L.add(new InsnNode(Type.getReturnType(d).getOpcode(Opcodes.IRETURN)));
        System.out.println(cn.name + "." + n + d + " -> vh." + to);
        total++;
    }

    /* n.a(Graphics, short[], int, int[], int) -> vh.d(..., eu, ey, ev, ex, et); ez = vh.z; ex = vh.x */
    static void dialogue(ClassNode cn) {
        String d = "(L" + G + ";[SI[II)V";
        MethodNode m = find(cn, "a", d);
        String[] want = {"I", "Ljava/lang/String;", "I", "I", "I", "Lae;", "I"};
        for (int i = 0; i < F.length; i++) {
            boolean ok = false;
            for (FieldNode f : cn.fields)
                if (f.name.equals(F[i]) && f.desc.equals(want[i]) && (f.access & Opcodes.ACC_STATIC) != 0) ok = true;
            if (!ok) throw new RuntimeException("n khong co field static " + F[i] + " " + want[i]);
        }
        clear(m);
        InsnList L = m.instructions;
        L.add(new VarInsnNode(Opcodes.ALOAD, 0));
        L.add(new VarInsnNode(Opcodes.ALOAD, 1));
        L.add(new VarInsnNode(Opcodes.ILOAD, 2));
        L.add(new VarInsnNode(Opcodes.ALOAD, 3));
        L.add(new VarInsnNode(Opcodes.ILOAD, 4));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "n", F[1], "Ljava/lang/String;"));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "n", F[2], "I"));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "n", F[3], "I"));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "n", F[4], "I"));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "n", F[5], "Lae;"));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "n", F[0], "I"));
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "vh", "d",
                "(L" + G + ";[SI[IILjava/lang/String;IIILae;I)V", false));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "vh", "z", "I"));
        L.add(new FieldInsnNode(Opcodes.PUTSTATIC, "n", F[6], "I"));
        L.add(new FieldInsnNode(Opcodes.GETSTATIC, "vh", "x", "I"));
        L.add(new FieldInsnNode(Opcodes.PUTSTATIC, "n", F[4], "I"));
        L.add(new InsnNode(Opcodes.RETURN));
        System.out.println("n.a" + d + " -> vh.d (khung thoai)");
        total++;
    }

    /* Graphics.drawString(String,int,int,int) -> vh.ds(Graphics,String,int,int,int) */
    static void drawString(ClassNode cn) {
        int c = 0;
        for (MethodNode m : cn.methods)
            for (AbstractInsnNode i : m.instructions.toArray())
                if (i instanceof MethodInsnNode) {
                    MethodInsnNode mi = (MethodInsnNode) i;
                    if (mi.getOpcode() == Opcodes.INVOKEVIRTUAL && mi.owner.equals(G) && mi.name.equals("drawString")
                            && mi.desc.equals("(Ljava/lang/String;III)V")) {
                        m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, "vh", "ds",
                                "(L" + G + ";Ljava/lang/String;III)V", false));
                        c++;
                    }
                }
        System.out.println(cn.name + ": drawString -> vh.ds x" + c); total += c;
    }

    /* Font.stringWidth(String) -> vh.sw(Font,String) */
    static void stringWidth(ClassNode cn) {
        int c = 0;
        for (MethodNode m : cn.methods)
            for (AbstractInsnNode i : m.instructions.toArray())
                if (i instanceof MethodInsnNode) {
                    MethodInsnNode mi = (MethodInsnNode) i;
                    if (mi.getOpcode() == Opcodes.INVOKEVIRTUAL && mi.owner.equals("javax/microedition/lcdui/Font")
                            && mi.name.equals("stringWidth") && mi.desc.equals("(Ljava/lang/String;)I")) {
                        m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, "vh", "sw",
                                "(Ljavax/microedition/lcdui/Font;Ljava/lang/String;)I", false));
                        c++;
                    }
                }
        System.out.println(cn.name + ": Font.stringWidth -> vh.sw x" + c); total += c;
    }

    /* String.length() ; bipush k ; imul  ->  bs.a(String) (do rong px that) */
    static void lenMul(ClassNode cn, int k) {
        int c = 0;
        for (MethodNode m : cn.methods)
            for (AbstractInsnNode i : m.instructions.toArray()) {
                if (!(i instanceof MethodInsnNode)) continue;
                MethodInsnNode mi = (MethodInsnNode) i;
                if (!(mi.owner.equals(S) && mi.name.equals("length") && mi.desc.equals("()I"))) continue;
                AbstractInsnNode p = mi.getNext(), q = p == null ? null : p.getNext();
                if (p instanceof IntInsnNode && p.getOpcode() == Opcodes.BIPUSH && ((IntInsnNode) p).operand == k
                        && q != null && q.getOpcode() == Opcodes.IMUL) {
                    m.instructions.remove(p); m.instructions.remove(q);
                    m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, "bs", "a", "(Ljava/lang/String;)I", false));
                    c++;
                }
            }
        System.out.println(cn.name + ": String.length()*" + k + " -> bs.a x" + c); total += c;
    }
}
