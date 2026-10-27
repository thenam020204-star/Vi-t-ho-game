import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/*
 * Bytecode patches applied to the original classes of base/HL4_1.01.jar (session 2).
 * Every patch checks the exact instruction context it expects and aborts otherwise,
 * so a different input jar can never be patched silently in the wrong place.
 *
 *   hl4/gfx/Sprite.drawNpc  : Interp.sprBegin(...) before the cell loop,
 *                             Interp.cellX/cellY on the decoded cell offset (locals 35/36).
 *   hl4/game/TitleFrame     : frameCnt private -> package-private (saved around ghost frames).
 *   hl4/game/HeroDraw.particleProcess : ghost frames call Interp.ghostParticles(particleStart).
 *   hl4/game/Effects.draw   : ghost frames call Interp.drawEffect (stage-mixed damage numbers).
 *   hl4/game/Dialog.drawScriptWindow  : Interp.lp on window y/x/face y (locals 15/16/17).
 *   hl4/game/EventDraw      : Interp.lp on cinema bar height, subtitle and skip fade alpha.
 *   hl4/game/HudKtf/MainGameFrame.drawInfo : Interp.lp on the info box slide offset.
 *
 * usage: Patch <in.jar> <outdir>   (writes only the patched classes)
 */
public class Patch {
    static void fail(String m) { throw new IllegalStateException("patch: " + m); }

    public static void main(String[] a) throws Exception {
        Map<String, byte[]> in = new HashMap<>();
        try (ZipFile z = new ZipFile(a[0])) {
            for (Enumeration<? extends ZipEntry> e = z.entries(); e.hasMoreElements();) {
                ZipEntry ze = e.nextElement();
                if (ze.getName().endsWith(".class")) in.put(ze.getName(), z.getInputStream(ze).readAllBytes());
            }
        }
        write(a[1], "hl4/gfx/Sprite.class", sprite(in.get("hl4/gfx/Sprite.class")));
        write(a[1], "hl4/game/TitleFrame.class", title(in.get("hl4/game/TitleFrame.class")));
        write(a[1], "hl4/game/HeroDraw.class", heroDraw(in.get("hl4/game/HeroDraw.class")));
        write(a[1], "hl4/game/Effects.class", effects(in.get("hl4/game/Effects.class")));
        write(a[1], "hl4/game/Dialog.class", dialog(in.get("hl4/game/Dialog.class")));
        write(a[1], "hl4/game/EventDraw.class", eventDraw(in.get("hl4/game/EventDraw.class")));
        write(a[1], "hl4/game/HudKtf.class", info(in.get("hl4/game/HudKtf.class"), "drawInfo", "(Lhl4/game/Obj;)V"));
        write(a[1], "hl4/game/MainGameFrame.class", info(in.get("hl4/game/MainGameFrame.class"), "drawInfo", "()V"));
    }

    static void write(String dir, String name, byte[] b) throws IOException {
        Path p = Paths.get(dir, name);
        Files.createDirectories(p.getParent());
        Files.write(p, b);
        System.out.println("patched " + name + " (" + b.length + " bytes)");
    }

    static byte[] emit(ClassNode cn) {
        // COMPUTE_MAXS only: the input has no frames we keep (CLDC StackMap is regenerated
        // by the preverifier), and max stack/locals must cover the inserted calls.
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        return cw.toByteArray();
    }

    static ClassNode read(byte[] b) {
        ClassNode cn = new ClassNode();
        // SKIP_FRAMES drops the old CLDC StackMap attribute (unknown attribute, offsets would be stale)
        new ClassReader(b).accept(cn, ClassReader.SKIP_FRAMES);
        if (cn.attrs != null) cn.attrs.clear();
        return cn;
    }

    static boolean isVar(AbstractInsnNode n, int op, int var) {
        return n != null && n.getOpcode() == op && ((VarInsnNode) n).var == var;
    }

    static AbstractInsnNode next(AbstractInsnNode n) {
        n = n.getNext();
        while (n != null && n.getOpcode() < 0) n = n.getNext();
        return n;
    }

    static byte[] sprite(byte[] b) {
        ClassNode cn = read(b);
        MethodNode m = null;
        for (MethodNode x : cn.methods)
            if (x.name.equals("drawNpc") && x.desc.equals("(Lhl4/gfx/SpriteOut;Lhl4/gfx/Frame;IIIIIIIIII)I")) m = x;
        if (m == null) fail("Sprite.drawNpc not found");
        for (MethodNode x : cn.methods) if (x.attrs != null) x.attrs.clear();
        for (MethodNode x : cn.methods) for (AbstractInsnNode n : x.instructions)
            if (n.getOpcode() == Opcodes.INVOKESTATIC && ((MethodInsnNode) n).owner.equals("hl4/game/Interp")) fail("Sprite already patched");

        // 1. frame select "aload 21; iload 7; invokestatic s8; iaload; istore 22" (n17 = row[s8(n5)]):
        //    local 7 = Interp.sprBegin(this, anim, row, frame, x, y, n8, n4, n3)
        AbstractInsnNode sel = null;
        for (AbstractInsnNode n : m.instructions) {
            if (isVar(n, Opcodes.ALOAD, 21) && isVar(next(n), Opcodes.ILOAD, 7)) {
                AbstractInsnNode c = next(next(n));
                if (c.getOpcode() == Opcodes.INVOKESTATIC && ((MethodInsnNode) c).name.equals("s8")
                        && next(c).getOpcode() == Opcodes.IALOAD && isVar(next(next(c)), Opcodes.ISTORE, 22)) { sel = n; break; }
            }
        }
        if (sel == null) fail("frame select not found");
        InsnList begin = new InsnList();
        begin.add(new VarInsnNode(Opcodes.ALOAD, 0));
        begin.add(new VarInsnNode(Opcodes.ILOAD, 3));   // anim
        begin.add(new VarInsnNode(Opcodes.ILOAD, 20));  // direction row
        begin.add(new VarInsnNode(Opcodes.ILOAD, 7));   // frame (validated)
        begin.add(new VarInsnNode(Opcodes.ILOAD, 8));   // x
        begin.add(new VarInsnNode(Opcodes.ILOAD, 9));   // y
        begin.add(new VarInsnNode(Opcodes.ILOAD, 10));  // n8 (flip when 1)
        begin.add(new VarInsnNode(Opcodes.ILOAD, 6));   // n4 (flip when == n3)
        begin.add(new VarInsnNode(Opcodes.ILOAD, 5));   // n3
        begin.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "hl4/game/Interp", "sprBegin", "(Lhl4/gfx/Sprite;IIIIIIII)I", false));
        begin.add(new VarInsnNode(Opcodes.ISTORE, 7));
        m.instructions.insertBefore(sel, begin);

        // 2. first "iload 25; iload 28; iadd; istore 25" after the first "istore 36"
        AbstractInsnNode s36 = null;
        for (AbstractInsnNode n : m.instructions)
            if (isVar(n, Opcodes.ISTORE, 36)) { s36 = n; break; }
        if (s36 == null) fail("istore 36 not found");
        AbstractInsnNode at = null;
        for (AbstractInsnNode n = s36; n != null; n = n.getNext()) {
            if (isVar(n, Opcodes.ILOAD, 25) && isVar(next(n), Opcodes.ILOAD, 28)
                    && next(next(n)).getOpcode() == Opcodes.IADD && isVar(next(next(next(n))), Opcodes.ISTORE, 25)) { at = n; break; }
        }
        if (at == null) fail("cell advance not found");
        // the position decode of both modes must end here: the label before it is the goto target
        AbstractInsnNode lab = at.getPrevious();
        while (lab != null && !(lab instanceof LabelNode)) lab = lab.getPrevious();
        boolean target = false;
        for (AbstractInsnNode n : m.instructions)
            if (n instanceof JumpInsnNode && ((JumpInsnNode) n).label == lab && n.getOpcode() == Opcodes.GOTO) target = true;
        if (!target) fail("cell advance is not the merge point of the position decode");
        InsnList cell = new InsnList();
        cell.add(new VarInsnNode(Opcodes.ILOAD, 30));
        cell.add(new VarInsnNode(Opcodes.ILOAD, 35));
        cell.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "hl4/game/Interp", "cellX", "(II)I", false));
        cell.add(new VarInsnNode(Opcodes.ISTORE, 35));
        cell.add(new VarInsnNode(Opcodes.ILOAD, 30));
        cell.add(new VarInsnNode(Opcodes.ILOAD, 36));
        cell.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "hl4/game/Interp", "cellY", "(II)I", false));
        cell.add(new VarInsnNode(Opcodes.ISTORE, 36));
        m.instructions.insertBefore(at, cell);
        return emit(cn);
    }

    static MethodNode method(ClassNode cn, String name, String desc) {
        MethodNode m = null;
        for (MethodNode x : cn.methods) {
            if (x.attrs != null) x.attrs.clear();
            if (x.name.equals(name) && x.desc.equals(desc)) m = x;
        }
        if (m == null) fail(cn.name + "." + name + desc + " not found");
        for (AbstractInsnNode n : m.instructions)
            if (n instanceof MethodInsnNode && ((MethodInsnNode) n).owner.equals("hl4/game/Interp")
                    && !((MethodInsnNode) n).name.equals("sprBegin")) fail(cn.name + "." + name + " already patched");
        return m;
    }

    static MethodInsnNode interp(String name, String desc) {
        return new MethodInsnNode(Opcodes.INVOKESTATIC, "hl4/game/Interp", name, desc, false);
    }

    // "v = Interp.lp(id, v)" for an int on top of the stack
    static InsnList lpTop(int id) {
        InsnList l = new InsnList();
        l.add(new IntInsnNode(Opcodes.SIPUSH, id));
        l.add(new InsnNode(Opcodes.SWAP));
        l.add(interp("lp", "(II)I"));
        return l;
    }

    // "local = Interp.lp(id, local)"
    static InsnList lpVar(int id, int var) {
        InsnList l = new InsnList();
        l.add(new IntInsnNode(Opcodes.SIPUSH, id));
        l.add(new VarInsnNode(Opcodes.ILOAD, var));
        l.add(interp("lp", "(II)I"));
        l.add(new VarInsnNode(Opcodes.ISTORE, var));
        return l;
    }

    static boolean isInt(AbstractInsnNode n, int v) {
        if (n == null) return false;
        int op = n.getOpcode();
        if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) return op - Opcodes.ICONST_0 == v;
        if (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH) return ((IntInsnNode) n).operand == v;
        return false;
    }

    // the instruction computing the alpha argument of "Gfx.fillRectAlpha(.., alpha, 11, 11, 11)"
    static List<AbstractInsnNode> alphaArgs(MethodNode m) {
        List<AbstractInsnNode> r = new ArrayList<>();
        for (AbstractInsnNode n : m.instructions) {
            if (n instanceof MethodInsnNode && ((MethodInsnNode) n).name.equals("fillRectAlpha")
                    && ((MethodInsnNode) n).owner.equals("hl4/game/Gfx")) {
                AbstractInsnNode c = n.getPrevious(), b = c.getPrevious(), a = b.getPrevious();
                if (isInt(a, 11) && isInt(b, 11) && isInt(c, 11)) r.add(a.getPrevious());
            }
        }
        return r;
    }

    static byte[] heroDraw(byte[] b) {
        ClassNode cn = read(b);
        MethodNode m = method(cn, "particleProcess", "()V");
        AbstractInsnNode g = m.instructions.getFirst();
        while (g.getOpcode() < 0) g = g.getNext();
        if (!(g instanceof FieldInsnNode) || !((FieldInsnNode) g).name.equals("ghost")) fail("particleProcess ghost test not first");
        AbstractInsnNode j = next(g), r = next(j);
        if (j.getOpcode() != Opcodes.IFEQ || r.getOpcode() != Opcodes.RETURN) fail("particleProcess ghost return not found");
        InsnList l = new InsnList();
        l.add(new FieldInsnNode(Opcodes.GETSTATIC, "hl4/game/HeroDraw", "particleStart", "J"));
        l.add(interp("ghostParticles", "(J)V"));
        m.instructions.insertBefore(r, l);
        return emit(cn);
    }

    static byte[] effects(byte[] b) {
        ClassNode cn = read(b);
        MethodNode m = method(cn, "draw", "(Lhl4/game/Obj;II)V");
        LabelNode go = new LabelNode();
        InsnList l = new InsnList();
        l.add(new FieldInsnNode(Opcodes.GETSTATIC, "hl4/game/Interp", "ghost", "Z"));
        l.add(new JumpInsnNode(Opcodes.IFEQ, go));
        l.add(new VarInsnNode(Opcodes.ALOAD, 0));
        l.add(new VarInsnNode(Opcodes.ILOAD, 1));
        l.add(new VarInsnNode(Opcodes.ILOAD, 2));
        l.add(interp("drawEffect", "(Lhl4/game/Obj;II)V"));
        l.add(new InsnNode(Opcodes.RETURN));
        l.add(go);
        m.instructions.insert(l);
        return emit(cn);
    }

    static byte[] dialog(byte[] b) {
        ClassNode cn = read(b);
        MethodNode m = method(cn, "drawScriptWindow", "(Lhl4/gfx/Frame;IZ)V");
        // merge point of the window position chain: the label targeted by the most gotos,
        // followed by "iload_1; iconst_1; if_icmpgt" ((n <= 1 ? 9 : 20) + n14)
        Map<LabelNode, Integer> cnt = new HashMap<>();
        for (AbstractInsnNode n : m.instructions)
            if (n.getOpcode() == Opcodes.GOTO) cnt.merge(((JumpInsnNode) n).label, 1, Integer::sum);
        LabelNode best = null;
        for (Map.Entry<LabelNode, Integer> e : cnt.entrySet()) if (best == null || e.getValue() > cnt.get(best)) best = e.getKey();
        if (best == null || cnt.get(best) < 10) fail("dialog merge point not found");
        AbstractInsnNode at = next(best);
        if (!isVar(at, Opcodes.ILOAD, 1) || !isInt(next(at), 1) || next(next(at)).getOpcode() != Opcodes.IF_ICMPGT) fail("dialog merge context");
        InsnList l = new InsnList();
        for (int k = 0; k < 3; k++) {
            l.add(new VarInsnNode(Opcodes.ILOAD, 1));   // id = LP_DIALOG + slot * 3 + k
            l.add(new InsnNode(Opcodes.ICONST_3));
            l.add(new InsnNode(Opcodes.IMUL));
            l.add(new IntInsnNode(Opcodes.BIPUSH, k));
            l.add(new InsnNode(Opcodes.IADD));
            l.add(new VarInsnNode(Opcodes.ILOAD, 15 + k));
            l.add(interp("lp", "(II)I"));
            l.add(new VarInsnNode(Opcodes.ISTORE, 15 + k));
        }
        m.instructions.insertBefore(at, l);
        return emit(cn);
    }

    static byte[] eventDraw(byte[] b) {
        ClassNode cn = read(b);
        MethodNode d = method(cn, "draw", "(Lhl4/gfx/Frame;)V");
        AbstractInsnNode s4 = null;
        for (AbstractInsnNode n : d.instructions) if (isVar(n, Opcodes.ISTORE, 4)) { s4 = n; break; }
        AbstractInsnNode mul = s4 == null ? null : s4.getPrevious();
        if (mul == null || mul.getOpcode() != Opcodes.IMUL || !isInt(mul.getPrevious(), 6)) fail("bars height store not found");
        d.instructions.insert(s4, lpVar(12, 4));
        MethodNode st = method(cn, "subTitle", "(Lhl4/gfx/Frame;)V");
        List<AbstractInsnNode> a = alphaArgs(st);
        if (a.size() != 1 || a.get(0).getOpcode() != Opcodes.IMUL) fail("subtitle alpha not found");
        st.instructions.insert(a.get(0), lpTop(14));
        MethodNode sk = method(cn, "skipFade", "(Lhl4/gfx/Frame;)V");
        a = alphaArgs(sk);
        if (a.size() != 1 || a.get(0).getOpcode() != Opcodes.ILOAD) fail("skip fade alpha not found");
        sk.instructions.insert(a.get(0), lpTop(15));
        return emit(cn);
    }

    // "n4 = G.game[70] * 3 - 15" then n4 = Interp.lp(13, n4)
    static byte[] info(byte[] b, String name, String desc) {
        ClassNode cn = read(b);
        MethodNode m = method(cn, name, desc);
        AbstractInsnNode at = null;
        for (AbstractInsnNode n : m.instructions) {
            if (isInt(n, 70) && next(n).getOpcode() == Opcodes.BALOAD && isInt(next(next(n)), 3)
                    && next(next(next(n))).getOpcode() == Opcodes.IMUL && isInt(next(next(next(next(n)))), 15)) {
                AbstractInsnNode sub = next(next(next(next(next(n)))));
                AbstractInsnNode st = next(sub);
                if (sub.getOpcode() == Opcodes.ISUB && st.getOpcode() == Opcodes.ISTORE) { at = st; break; }
            }
        }
        if (at == null) fail(cn.name + "." + name + " slide offset not found");
        m.instructions.insert(at, lpVar(13, ((VarInsnNode) at).var));
        return emit(cn);
    }

    static byte[] title(byte[] b) {
        ClassNode cn = read(b);
        for (MethodNode x : cn.methods) if (x.attrs != null) x.attrs.clear();
        FieldNode f = null;
        for (FieldNode x : cn.fields) if (x.name.equals("frameCnt") && x.desc.equals("I")) f = x;
        if (f == null || f.access != (Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC)) fail("TitleFrame.frameCnt not private static int");
        f.access = Opcodes.ACC_STATIC;
        return emit(cn);
    }
}
