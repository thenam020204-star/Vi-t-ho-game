import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;
import java.util.*;
import java.util.zip.*;

/* For a root method, walks the call graph inside the app jar and lists every
 * PUTSTATIC / PUTFIELD target and array-store site reached (render side effects). */
public class RenderEffects {
    static Map<String, ClassNode> app = new HashMap<>();
    public static void main(String[] a) throws Exception {
        try (ZipFile z = new ZipFile(a[0])) {
            for (Enumeration<? extends ZipEntry> e = z.entries(); e.hasMoreElements();) {
                ZipEntry ze = e.nextElement(); if (!ze.getName().endsWith(".class")) continue;
                ClassNode cn = new ClassNode(); new ClassReader(z.getInputStream(ze).readAllBytes()).accept(cn, 0); app.put(cn.name, cn);
            }
        }
        for (int i = 1; i < a.length; i++) run(a[i]);
    }
    static MethodNode find(String owner, String name, String desc) {
        for (ClassNode c = app.get(owner); c != null; c = app.get(c.superName))
            for (MethodNode m : c.methods) if (m.name.equals(name) && (desc == null || m.desc.equals(desc))) return m;
        return null;
    }
    static String ownerOf(String owner, String name, String desc) {
        for (ClassNode c = app.get(owner); c != null; c = app.get(c.superName))
            for (MethodNode m : c.methods) if (m.name.equals(name) && m.desc.equals(desc)) return c.name;
        return owner;
    }
    static Map<MethodNode, Frame<org.objectweb.asm.tree.analysis.SourceValue>[]> frames = new HashMap<>();
    static String arraySrc(String owner, MethodNode m, AbstractInsnNode in, int op) {
        try {
            Frame<org.objectweb.asm.tree.analysis.SourceValue>[] f = frames.get(m);
            if (f == null) { f = new Analyzer<>(new org.objectweb.asm.tree.analysis.SourceInterpreter()).analyze(owner, m); frames.put(m, f); }
            Frame<org.objectweb.asm.tree.analysis.SourceValue> fr = f[m.instructions.indexOf(in)];
            if (fr == null) return "?dead";
            int depth = (op == Opcodes.LASTORE || op == Opcodes.DASTORE) ? 4 : 3;
            org.objectweb.asm.tree.analysis.SourceValue v = fr.getStack(fr.getStackSize() - depth);
            StringBuilder sb = new StringBuilder();
            for (AbstractInsnNode p : v.insns) sb.append(desc(owner, m, f, p, 0)).append("|");
            return sb.toString();
        } catch (Exception e) { return "?err " + e; }
    }
    static String desc(String owner, MethodNode m, Frame<org.objectweb.asm.tree.analysis.SourceValue>[] f, AbstractInsnNode p, int d) {
        if (p instanceof FieldInsnNode) { FieldInsnNode fi = (FieldInsnNode) p; return (p.getOpcode() == Opcodes.GETSTATIC ? "S " : "F ") + fi.owner + "." + fi.name; }
        if (p instanceof VarInsnNode) {
            int var = ((VarInsnNode) p).var;
            // find stores to this local
            StringBuilder sb = new StringBuilder();
            if (d < 3) for (AbstractInsnNode q : m.instructions) if (q instanceof VarInsnNode && q.getOpcode() == Opcodes.ASTORE && ((VarInsnNode) q).var == var) {
                Frame<org.objectweb.asm.tree.analysis.SourceValue> fr = f[m.instructions.indexOf(q)];
                if (fr != null) for (AbstractInsnNode r : fr.getStack(fr.getStackSize() - 1).insns) sb.append(desc(owner, m, f, r, d + 1)).append(",");
            }
            return sb.length() > 0 ? sb.toString() : "local" + var + "(param?)";
        }
        if (p.getOpcode() == Opcodes.AALOAD && d < 3) {
            Frame<org.objectweb.asm.tree.analysis.SourceValue> fr = f[m.instructions.indexOf(p)];
            StringBuilder sb = new StringBuilder("elem(");
            for (AbstractInsnNode r : fr.getStack(fr.getStackSize() - 2).insns) sb.append(desc(owner, m, f, r, d + 1));
            return sb.append(")").toString();
        }
        if (p instanceof MethodInsnNode) return "ret " + ((MethodInsnNode) p).owner + "." + ((MethodInsnNode) p).name;
        if (p instanceof TypeInsnNode || p instanceof IntInsnNode) return "new";
        return "op" + p.getOpcode();
    }
    static void run(String root) {
        String[] p = root.split("\\.");
        Deque<String> q = new ArrayDeque<>(); Set<String> seen = new LinkedHashSet<>(); Map<String,String> par = new HashMap<>();
        q.add(p[0] + "." + p[1] + "." + find(p[0], p[1], null).desc);
        TreeMap<String, Set<String>> puts = new TreeMap<>();
        while (!q.isEmpty()) {
            String k = q.poll(); if (!seen.add(k)) continue;
            String[] s = k.split("\\.", 3); MethodNode m = find(s[0], s[1], s[2]); if (m == null) continue;
            for (AbstractInsnNode in : m.instructions) {
                int op = in.getOpcode();
                if (in instanceof MethodInsnNode) {
                    MethodInsnNode mi = (MethodInsnNode) in;
                    if (!app.containsKey(mi.owner)) continue;
                    // interface/virtual: add all implementors
                    for (ClassNode c : app.values()) {
                        boolean sub = false; for (ClassNode x = c; x != null; x = app.get(x.superName)) { if (x.name.equals(mi.owner) || x.interfaces.contains(mi.owner)) { sub = true; break; } }
                        if (sub && (op != Opcodes.INVOKESTATIC && op != Opcodes.INVOKESPECIAL || c.name.equals(mi.owner))) {
                            if (find(c.name, mi.name, mi.desc) != null) { String nk = ownerOf(c.name, mi.name, mi.desc) + "." + mi.name + "." + mi.desc; par.putIfAbsent(nk, k); q.add(nk); }
                        }
                    }
                } else if (op >= Opcodes.IASTORE && op <= Opcodes.SASTORE) {
                    String src = arraySrc(s[0], m, in, op);
                    puts.computeIfAbsent("A " + src, x -> new TreeSet<>()).add(s[0] + "." + s[1]);
                } else if (op == Opcodes.PUTSTATIC || op == Opcodes.PUTFIELD) {
                    FieldInsnNode fi = (FieldInsnNode) in;
                    puts.computeIfAbsent((op == Opcodes.PUTSTATIC ? "S " : "F ") + fi.owner + "." + fi.name + ":" + fi.desc, x -> new TreeSet<>()).add(s[0] + "." + s[1]);
                }
            }
        }
        System.out.println("=== " + root + " methods=" + seen.size());
        String tgt = System.getProperty("path");
        if (tgt != null) for (String k : seen) if (k.startsWith(tgt)) { StringBuilder sb = new StringBuilder(); for (String x = k; x != null; x = par.get(x)) sb.append(x.replaceAll("\\(.*", "")).append(" <- "); System.out.println("PATH " + sb); }
        for (Map.Entry<String, Set<String>> e : puts.entrySet()) System.out.println("  " + e.getKey() + "  <- " + e.getValue());
    }
}
