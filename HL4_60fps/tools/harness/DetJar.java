import org.objectweb.asm.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
/* test only: copy of a jar where hl4/gfx/Vi's System.currentTimeMillis() becomes hl4.sys.Clock.now() */
public class DetJar {
    public static void main(String[] a) throws Exception {
        try (ZipFile z = new ZipFile(a[0]); ZipOutputStream o = new ZipOutputStream(new FileOutputStream(a[1]))) {
            for (Enumeration<? extends ZipEntry> e = z.entries(); e.hasMoreElements();) {
                ZipEntry ze = e.nextElement(); byte[] b = z.getInputStream(ze).readAllBytes();
                if (ze.getName().equals("hl4/gfx/Vi.class")) {
                    ClassReader cr = new ClassReader(b); ClassWriter cw = new ClassWriter(cr, 0); int[] n = {0};
                    cr.accept(new ClassVisitor(Opcodes.ASM9, cw) { public MethodVisitor visitMethod(int ac, String nm, String d, String s, String[] ex) {
                        return new MethodVisitor(Opcodes.ASM9, super.visitMethod(ac, nm, d, s, ex)) { public void visitMethodInsn(int op, String ow, String name, String desc, boolean itf) {
                            if (ow.equals("java/lang/System") && name.equals("currentTimeMillis")) { n[0]++; super.visitMethodInsn(op, "hl4/sys/Clock", "now", desc, false); } else super.visitMethodInsn(op, ow, name, desc, itf); } }; } }, 0);
                    b = cw.toByteArray(); System.out.println("replaced " + n[0]);
                }
                o.putNextEntry(new ZipEntry(ze.getName())); o.write(b); o.closeEntry();
            }
        }
    }
}
