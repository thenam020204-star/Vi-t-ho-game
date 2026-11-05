import org.objectweb.asm.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/** Downgrade a javac-8 class to CLDC-friendly major 47 (Java 1.3):
 *  - class file version -> 47.0
 *  - drop StackMapTable (ProGuard -microedition then writes the CLDC StackMap)
 *  - reject any post-1.3 construct that a KVM cannot verify/execute
 *  - optionally strip debug attributes
 */
public class J2meFix {
    static final List<String> problems = new ArrayList<String>();

    public static void main(String[] a) throws Exception {
        byte[] in = Files.readAllBytes(Paths.get(a[0]));
        boolean stripDebug = a.length > 2 && "strip".equals(a[2]);
        ClassReader cr = new ClassReader(in);
        ClassWriter cw = new ClassWriter(0);
        int flags = ClassReader.SKIP_FRAMES | (stripDebug ? ClassReader.SKIP_DEBUG : 0);
        cr.accept(new ClassVisitor(Opcodes.ASM9, cw) {
            String cls;
            public void visit(int v, int acc, String n, String sig, String sup, String[] itf) {
                cls = n;
                if ((v & 0xFFFF) > Opcodes.V1_8) problems.add("source class newer than 1.8");
                super.visit(Opcodes.V1_3, acc, n, sig, sup, itf);   // 47.0
            }
            public MethodVisitor visitMethod(int acc, String n, String d, String s, String[] ex) {
                final String where = cls + "." + n + d;
                MethodVisitor mv = super.visitMethod(acc, n, d, s, ex);
                if (mv == null) return null;
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    public void visitInvokeDynamicInsn(String n2, String d2, Handle h, Object... b) {
                        problems.add(where + ": invokedynamic (not in CLDC)");
                        super.visitInvokeDynamicInsn(n2, d2, h, b);
                    }
                    public void visitLdcInsn(Object c) {
                        if (c instanceof Type) problems.add(where + ": ldc of class/method constant (Java 5+)");
                        if (c instanceof Handle) problems.add(where + ": ldc of MethodHandle (Java 7+)");
                        if (c instanceof ConstantDynamic) problems.add(where + ": condy (Java 11+)");
                        super.visitLdcInsn(c);
                    }
                    public void visitMethodInsn(int op, String o, String n2, String d2, boolean itf) {
                        if (op == Opcodes.INVOKESTATIC && itf) problems.add(where + ": invokestatic on interface (Java 8+)");
                        if (op == Opcodes.INVOKESPECIAL && itf) problems.add(where + ": default-method super call (Java 8+)");
                        super.visitMethodInsn(op, o, n2, d2, itf);
                    }
                };
            }
        }, flags);
        Files.write(Paths.get(a[1]), cw.toByteArray());
        if (problems.isEmpty()) System.out.println("DOWNGRADE OK -> 47.0  (" + a[1] + ")");
        else { for (String p : problems) System.out.println("REJECT: " + p); System.exit(1); }
    }
}
