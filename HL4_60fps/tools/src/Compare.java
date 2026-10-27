import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.nio.file.*;

/* Compares every method of classes in jar A with the same class files in dir/jar B after
 * normalising labels, line numbers, frames and constant-pool order. Prints per class: same/diff. */
public class Compare {
    static Map<String, byte[]> load(String p) throws Exception {
        Map<String, byte[]> m = new TreeMap<>();
        if (p.endsWith(".jar")) { try (ZipFile z = new ZipFile(p)) { for (Enumeration<? extends ZipEntry> e = z.entries(); e.hasMoreElements();) { ZipEntry ze = e.nextElement(); if (ze.getName().endsWith(".class")) m.put(ze.getName(), z.getInputStream(ze).readAllBytes()); } } }
        else Files.walk(Paths.get(p)).filter(x -> x.toString().endsWith(".class")).forEach(x -> { try { m.put(Paths.get(p).relativize(x).toString(), Files.readAllBytes(x)); } catch (IOException e) { throw new UncheckedIOException(e); } });
        return m;
    }
    static Map<String, String> norm(byte[] b) {
        ClassNode cn = new ClassNode(); new ClassReader(b).accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        Map<String, String> r = new TreeMap<>();
        StringBuilder hdr = new StringBuilder(cn.access + " " + cn.name + " " + cn.superName + " " + cn.interfaces);
        List<String> fl = new ArrayList<>(); for (FieldNode f : cn.fields) fl.add(f.access + " " + f.name + f.desc + "=" + f.value); Collections.sort(fl);
        r.put("<header>", hdr + " " + fl);
        for (MethodNode m : cn.methods) {
            Textifier t = new Textifier(); TraceMethodVisitor v = new TraceMethodVisitor(t);
            m.accept(v);
            StringWriter sw = new StringWriter(); t.print(new PrintWriter(sw));
            String s = sw.toString().replaceAll("(?m)^\\s*MAXSTACK.*$", "").replaceAll("(?m)^\\s*MAXLOCALS.*$", "");
            r.put(m.access + " " + m.name + m.desc, s);
        }
        return r;
    }
    public static void main(String[] a) throws Exception {
        Map<String, byte[]> A = load(a[0]), B = load(a[1]);
        boolean verbose = a.length > 2;
        int same = 0, diff = 0;
        for (String k : A.keySet()) {
            if (!B.containsKey(k)) continue;
            Map<String, String> x = norm(A.get(k)), y = norm(B.get(k));
            List<String> bad = new ArrayList<>();
            Set<String> keys = new TreeSet<>(x.keySet()); keys.addAll(y.keySet());
            for (String mk : keys) if (!Objects.equals(x.get(mk), y.get(mk))) bad.add(mk);
            if (bad.isEmpty()) same++; else { diff++; System.out.println("DIFF " + k + " " + bad.size() + "/" + keys.size() + " " + (verbose ? bad : "")); }
        }
        System.out.println("same=" + same + " diff=" + diff);
    }
}
