import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.io.*;
import java.nio.file.*;

/** Redirect Class.getResourceAsStream(String) to rc.g(Class,String) in the given class files. */
public class Patch {
    public static void main(String[] a) throws Exception {
        for (String f : a) {
            ClassNode cn = new ClassNode();
            new ClassReader(Files.readAllBytes(Paths.get(f))).accept(cn, 0);
            int n = 0;
            for (MethodNode m : cn.methods)
                for (AbstractInsnNode in : m.instructions)
                    if (in.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        MethodInsnNode mi = (MethodInsnNode) in;
                        if (mi.owner.equals("java/lang/Class") && mi.name.equals("getResourceAsStream") && mi.desc.equals("(Ljava/lang/String;)Ljava/io/InputStream;")) {
                            m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, "rc", "g", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/io/InputStream;", false));
                            n++;
                        }
                    }
            ClassWriter cw = new ClassWriter(0);
            cn.accept(cw);
            Files.write(Paths.get(f), cw.toByteArray());
            System.out.println("patch " + f + ": " + n + " call(s)");
        }
    }
}
