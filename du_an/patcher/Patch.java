import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Patch bytecode tinh cho "Di The Ta Quan" (RPG22) - Viet hoa NAM.P.
 *   java -cp asm.jar:asm-tree.jar:. Patch <in_dir> <out_dir> <patch_log>
 * Chi doc/ghi cac class can sua; class khac giu nguyen byte goc.
 * Version class giu nguyen (45.3), COMPUTE_MAXS, bo attribute StackMap cu -> ProGuard -microedition sinh lai.
 * Moi patch phai khop dung so lan mong doi, sai -> dung han (exit 1).
 */
public class Patch {
    static final String G = "javax/microedition/lcdui/Graphics";
    static final String F = "javax/microedition/lcdui/Font";
    static final String VH = "VH";
    static PrintWriter log;
    static boolean failed = false;

    public static void main(String[] a) throws Exception {
        Path in = Paths.get(a[0]), out = Paths.get(a[1]);
        log = new PrintWriter(new FileWriter(a[2]));
        patchClass(in, out, "c");
        patchClass(in, out, "i");
        log.close();
        if (failed) {
            System.err.println("PATCH FAILED - xem log");
            System.exit(1);
        }
        System.out.println("PATCH OK");
    }

    static void expect(String what, int got, int want) {
        String m = (got == want ? "OK   " : "FAIL ") + what + ": " + got + " (mong doi " + want + ")";
        log.println(m);
        if (got != want) {
            failed = true;
            System.err.println(m);
        }
    }

    static void patchClass(Path in, Path out, String name) throws Exception {
        byte[] b = Files.readAllBytes(in.resolve(name + ".class"));
        ClassReader cr = new ClassReader(b);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);
        if (name.equals("c")) patchC(cn);
        if (name.equals("i")) patchI(cn);
        stripStackMap(cn);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        Path o = out.resolve(name + ".class");
        Files.createDirectories(o.getParent());
        Files.write(o, cw.toByteArray());
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

    /** Thay toan bo than method bang: nap tham so -> invokestatic VH.target(desc) -> return. */
    static void redirectBody(ClassNode cn, String n, String d, String target) {
        MethodNode m = method(cn, n, d);
        m.instructions.clear();
        m.tryCatchBlocks.clear();
        m.localVariables = null;
        m.visibleParameterAnnotations = null;
        InsnList L = new InsnList();
        Type[] args = Type.getArgumentTypes(d);
        int slot = (m.access & Opcodes.ACC_STATIC) != 0 ? 0 : 1;
        for (Type t : args) {
            L.add(new VarInsnNode(t.getOpcode(Opcodes.ILOAD), slot));
            slot += t.getSize();
        }
        L.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, target, d, false));
        L.add(new InsnNode(Type.getReturnType(d).getOpcode(Opcodes.IRETURN)));
        m.instructions = L;
        log.println("BODY " + cn.name + "." + n + d + " -> VH." + target);
    }

    /** Doi moi invokevirtual owner.name(desc) trong class thanh invokestatic VH.target(owner;desc). */
    static int redirectCalls(ClassNode cn, String owner, String name, String desc, String target) {
        String nd = "(L" + owner + ";" + desc.substring(1);
        int n = 0;
        for (MethodNode m : cn.methods) {
            for (AbstractInsnNode x = m.instructions.getFirst(); x != null; x = x.getNext()) {
                if (x.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                    MethodInsnNode mi = (MethodInsnNode) x;
                    if (mi.owner.equals(owner) && mi.name.equals(name) && mi.desc.equals(desc)) {
                        m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, VH, target, nd, false));
                        n++;
                        x = m.instructions.getFirst(); // danh sach da doi -> quet lai
                    }
                }
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

    // ===================================================================== c (CGame)
    static void patchC(ClassNode cn) {
        // 1) Kiem tra xuong dong cua chu chay hoi thoai: getstatic i.Q; getstatic c.eo; getstatic c.ep; caload; Font.charWidth
        //    -> getstatic i.Q; getstatic c.eo; getstatic c.ep; VH.nw(Font,char[],int)
        int nwCount = 0;
        MethodNode typer = method(cn, "a", "(L" + G + ";II)V");
        for (AbstractInsnNode x = typer.instructions.getFirst(); x != null; x = x.getNext()) {
            if (isCall(x, Opcodes.INVOKEVIRTUAL, F, "charWidth", "(C)I")) {
                AbstractInsnNode p1 = prev(x), p2 = prev(p1), p3 = prev(p2), p4 = prev(p3);
                if (p1.getOpcode() == Opcodes.CALOAD && isField(p2, Opcodes.GETSTATIC, "c", "ep") && isField(p3, Opcodes.GETSTATIC, "c", "eo")
                        && isField(p4, Opcodes.GETSTATIC, "i", "Q")) {
                    // lan thu 2 la buoc kiem tra (n5 = charWidth(eo[ep]) sau ++ep); lan 1 la tien x sau khi ve
                    AbstractInsnNode st = next(x);
                    if (st != null && st.getOpcode() == Opcodes.ISTORE) {
                        typer.instructions.remove(p1);
                        MethodInsnNode nw = new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "nw", "(L" + F + ";[CI)I", false);
                        typer.instructions.set(x, nw);
                        x = nw;
                        nwCount++;
                    }
                }
            }
        }
        expect("c: hoi thoai charWidth(eo[ep]) -> VH.nw", nwCount, 1);

        // 2) Ten nguoi noi: dR = c.c(n7).length() -> VH.nlen(String)
        MethodNode nameM = method(cn, "b", "(IIIIII)V");
        int nl = 0;
        for (AbstractInsnNode x = nameM.instructions.getFirst(); x != null; x = x.getNext()) {
            if (isCall(x, Opcodes.INVOKEVIRTUAL, "java/lang/String", "length", "()I")) {
                MethodInsnNode r = new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "nlen", "(Ljava/lang/String;)I", false);
                nameM.instructions.set(x, r);
                x = r;
                nl++;
            }
        }
        expect("c.b(IIIIII): String.length -> VH.nlen", nl, 1);

        // 3) Hop thoai 2 dong -> 3 dong: c.h(0, 2, k) -> c.h(0, 3, k)
        int dlg = 0;
        for (MethodNode m : cn.methods) {
            for (AbstractInsnNode x = m.instructions.getFirst(); x != null; x = x.getNext()) {
                if (isCall(x, Opcodes.INVOKESTATIC, "c", "h", "(III)V")) {
                    AbstractInsnNode a3 = prev(x), a2 = prev(a3), a1 = prev(a2);
                    if (intValue(a1) == 0 && intValue(a2) == 2 && (intValue(a3) == 0 || intValue(a3) == 3)) {
                        m.instructions.set(a2, new InsnNode(Opcodes.ICONST_3));
                        dlg++;
                    }
                }
            }
        }
        expect("c: h(0,2,x) -> h(0,3,x) (hop thoai 3 dong)", dlg, 2);

        // 4) Ham ngat theo so ky tu -> VH (ngat theo px)
        redirectBody(cn, "a", "(L" + G + ";Ljava/lang/String;IIIIIIII)V", "dl");
        redirectBody(cn, "a", "(Ljava/lang/String;II)I", "calcPos");
        redirectBody(cn, "b", "(Ljava/lang/String;II)I", "calcPage");

        patchBoot(cn);
        patchMenu(cn);
        patchStrings(cn);

        // 5) Moi lenh ve/do chu con lai
        expect("c: Graphics.drawString", redirectCalls(cn, G, "drawString", "(Ljava/lang/String;III)V", "drawString"), 5);
        expect("c: Graphics.drawSubstring", redirectCalls(cn, G, "drawSubstring", "(Ljava/lang/String;IIIII)V", "drawSubstring"), 0);
        expect("c: Graphics.drawChar", redirectCalls(cn, G, "drawChar", "(CIII)V", "drawChar"), 1);
        expect("c: Font.charWidth", redirectCalls(cn, F, "charWidth", "(C)I", "cw"), 3);
        expect("c: Font.stringWidth", redirectCalls(cn, F, "stringWidth", "(Ljava/lang/String;)I", "sw"), 1);
    }

    // ===================================================================== go nha mang
    /**
     * Khoi dong: state 18 (GS_PREVIEW: anh bia + logo 移动圈圈 + hoi "是否开启音效") -> chi hien /p0 (logo NAMP) toan man
     * ~2.5s roi tu bat am thanh (bU = true, k |= 1) va vao thang state 0 (GS_INIT), bo qua state 19 (may quay so
     * logo China Mobile/Sohu + khoi tao SDK 弱联网) va state 21 (hoi ket noi mang).
     */
    static void patchBoot(ClassNode cn) {
        MethodNode k = method(cn, "k", "()V");
        int loop = 0, logo = 0, snd = 0, t = 0, key = 0, st = 0;
        for (AbstractInsnNode x = k.instructions.getFirst(); x != null; x = x.getNext()) {
            int op = x.getOpcode();
            if (op == Opcodes.ICONST_4 && loop == 0 && next(x).getOpcode() == Opcodes.IF_ICMPGE) {
                k.instructions.set(x, x = new InsnNode(Opcodes.ICONST_1));       // chi nap /p0
                loop++;
            } else if (op == Opcodes.LDC && "/logo_G".equals(((LdcInsnNode) x).cst)) {
                AbstractInsnNode a = next(x), b = next(a);
                if (isCall(a, Opcodes.INVOKESTATIC, "javax/microedition/lcdui/Image", "createImage", "(Ljava/lang/String;)Ljavax/microedition/lcdui/Image;")
                        && isField(b, Opcodes.PUTSTATIC, "c", "db")) {
                    AbstractInsnNode after = next(b);
                    k.instructions.remove(x); k.instructions.remove(a); k.instructions.remove(b);
                    x = after.getPrevious();
                    logo++;
                }
            } else if (op == Opcodes.NEW && ((TypeInsnNode) x).desc.equals("java/io/DataInputStream")) {
                // bo doan nap /sound.mid (nhac hieu nha mang o man quay so): new DataInputStream ... close()
                AbstractInsnNode y = x;
                List<AbstractInsnNode> del = new ArrayList<>();
                boolean ok = false;
                while (y != null) {
                    del.add(y);
                    if (isCall(y, Opcodes.INVOKEVIRTUAL, "java/io/DataInputStream", "close", "()V")) { ok = true; break; }
                    y = y.getNext();
                }
                if (ok) {
                    AbstractInsnNode after = y.getNext();
                    for (AbstractInsnNode d : del) if (!(d instanceof LabelNode)) k.instructions.remove(d);
                    x = after.getPrevious();
                    snd++;
                }
            } else if (op == Opcodes.LDC && Long.valueOf(20L).equals(((LdcInsnNode) x).cst)) {
                k.instructions.set(x, x = new LdcInsnNode(Long.valueOf(50L)));     // ~2.5s (50ms/khung)
                t++;
            } else if (op == Opcodes.LDC && Integer.valueOf(264224).equals(((LdcInsnNode) x).cst) && key == 0) {
                AbstractInsnNode a = next(x);
                if (isCall(a, Opcodes.INVOKESTATIC, "c", "b", "(I)Z")) {
                    k.instructions.remove(a);
                    k.instructions.set(x, x = new InsnNode(Opcodes.ICONST_1));     // "Co" luon dung
                    key++;
                }
            } else if (op == Opcodes.BIPUSH && ((IntInsnNode) x).operand == 19 && st == 0) {
                k.instructions.set(x, x = new InsnNode(Opcodes.ICONST_0));        // -> GS_INIT
                st++;
            }
        }
        expect("k(): vong nap /p0..p3 -> chi /p0", loop, 1);
        expect("k(): bo nap /logo_G (移动圈圈)", logo, 1);
        expect("k(): bo nap /sound.mid", snd, 1);
        expect("k(): cho 20 -> 50 khung", t, 1);
        expect("k(): tu dong chon bat am thanh", key, 1);
        expect("k(): state 19 -> 0", st, 1);

        // Ve state 18: drawImage(cZ[0],120,30,17) -> (0,0,20); bo drawImage(db,120,170,3); khong ve hoi am thanh (15L -> 1000000L)
        MethodNode q = method(cn, "q", "()V");
        int img = 0, db = 0, tx = 0;
        for (AbstractInsnNode x = q.instructions.getFirst(); x != null; x = x.getNext()) {
            if (isCall(x, Opcodes.INVOKEVIRTUAL, G, "drawImage", "(Ljavax/microedition/lcdui/Image;III)V")) {
                AbstractInsnNode an = prev(x), y = prev(an), xx = prev(y), im = prev(xx);
                if (intValue(an) == 17 && intValue(y) == 30 && intValue(xx) == 120 && im.getOpcode() == Opcodes.AALOAD) {
                    q.instructions.set(xx, new InsnNode(Opcodes.ICONST_0));
                    q.instructions.set(y, new InsnNode(Opcodes.ICONST_0));
                    q.instructions.set(an, new IntInsnNode(Opcodes.BIPUSH, 20));
                    img++;
                } else if (intValue(an) == 3 && intValue(y) == 170 && intValue(xx) == 120 && isField(im, Opcodes.GETSTATIC, "c", "db")) {
                    InsnList r = new InsnList();
                    r.add(new InsnNode(Opcodes.POP2));
                    r.add(new InsnNode(Opcodes.POP2));
                    AbstractInsnNode last = new InsnNode(Opcodes.POP);
                    r.add(last);
                    q.instructions.insert(x, r);
                    q.instructions.remove(x);
                    x = last;
                    db++;
                }
            } else if (x.getOpcode() == Opcodes.LDC && Long.valueOf(15L).equals(((LdcInsnNode) x).cst)) {
                AbstractInsnNode p = prev(x);
                if (isField(p, Opcodes.GETSTATIC, "c", "bF")) {
                    q.instructions.set(x, x = new LdcInsnNode(Long.valueOf(1000000L)));
                    tx++;
                }
            }
        }
        expect("q(): logo NAMP toan man (0,0,TOP|LEFT)", img, 1);
        expect("q(): bo ve logo 移动圈圈", db, 1);
        expect("q(): bo chu hoi am thanh + nut 是/否", tx, 1);

        // Diem/上传成绩 (state 20, SDK) -> ve thang menu (state 6), nhu khi chon "否"
        int up = 0, link = 0;
        for (MethodNode m : cn.methods) {
            for (AbstractInsnNode x = m.instructions.getFirst(); x != null; x = x.getNext()) {
                if (x.getOpcode() == Opcodes.BIPUSH && isCall(next(x), Opcodes.INVOKESTATIC, "c", "b", "(B)V")) {
                    int v = ((IntInsnNode) x).operand;
                    if (v == 20) { m.instructions.set(x, x = new IntInsnNode(Opcodes.BIPUSH, 6)); up++; }
                    else if (v == 15) { m.instructions.set(x, x = new IntInsnNode(Opcodes.BIPUSH, 17)); link++; }
                }
            }
        }
        expect("SetGameState(20 上传成绩) -> 6 (menu)", up, 4);
        expect("SetGameState(15 mo link 3g.tysis.cn) -> 17 (thoat)", link, 2);
    }

    /** Menu chinh: nhay qua muc mang (VH.mainMenu); thoat -> GS_QUIT thay vi man "更多精彩游戏". */
    static void patchMenu(ClassNode cn) {
        MethodNode j = method(cn, "J", "(Lh;)V");
        InsnList a = new InsnList();
        a.add(new VarInsnNode(Opcodes.ALOAD, 0));
        a.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "mainMenu", "(Lh;)V", false));
        j.instructions.insert(a);
        MethodNode g = method(cn, "G", "(L" + G + ";Lh;)V");
        InsnList b = new InsnList();
        b.add(new VarInsnNode(Opcodes.ALOAD, 1));
        b.add(new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "mainMenu", "(Lh;)V", false));
        g.instructions.insert(b);
        int ex = 0;
        for (AbstractInsnNode x = j.instructions.getFirst(); x != null; x = x.getNext()) {
            if (isCall(x, Opcodes.INVOKEVIRTUAL, "h", "c", "()V")) {
                AbstractInsnNode p = prev(x);
                if (p.getOpcode() == Opcodes.ALOAD && ((VarInsnNode) p).var == 3) {
                    AbstractInsnNode n = new MethodInsnNode(Opcodes.INVOKESTATIC, "c", "b", "(B)V", false);
                    j.instructions.set(p, new IntInsnNode(Opcodes.BIPUSH, 17));
                    j.instructions.set(x, n);
                    x = n;
                    ex++;
                }
            }
        }
        expect("J(): xac nhan thoat -> GS_QUIT (bo man link)", ex, 1);
        log.println("J()/G(): chen VH.mainMenu(h)");
    }

    // ===================================================================== chuoi hien thi trong c.class
    static final String[][] STR = {
        {"\u6b63\u5728\u8fdb\u5165\u6e38\u620f...", "\u0110ang v\u00e0o game..."},           // 正在进入游戏...
        {"\u6e38\u620f\u5df2\u6682\u505c...", "T\u1ea1m d\u1eebng..."},                          // 游戏已暂停...
        {"\u8fd4\u56de", "Tr\u1edf v\u1ec1"},                                                       // 返回
        {"\u6b63\u5728\u52a0\u8f7d\u8d44\u6e90...", "\u0110ang t\u1ea3i..."},                   // 正在加载资源...
        {"\u662f\u5426\u5f00\u542f\u97f3\u6548", "B\u1eadt \u00e2m thanh?"},                    // 是否开启音效
        {"\u60a8\u5f53\u524d\u7684\u79ef\u5206\uff1a", "\u0110i\u1ec3m hi\u1ec7n t\u1ea1i:"}, // 您当前的积分：
        {"\u5206", " \u0111i\u1ec3m"},                                                               // 分
        {"\u662f\u5426\u63d0\u4ea4\u6210\u7ee9\uff1f", "G\u1eedi \u0111i\u1ec3m?"},           // 是否提交成绩？
        {"\u662f", "C\u00f3"},                                                                        // 是
        {"\u5426", "Kh\u00f4ng"},                                                                     // 否
        {"\u63d0\u4ea4\u4e2d\u2026\u2026", "\u0110ang g\u1eedi..."},                              // 提交中……
        {"\u9700\u8981\u8054\u7f51\u624d\u80fd\u4f7f\u7528\u4e92\u52a8\u529f\u80fd\uff0c\u662f\u5426\u7acb\u523b\u8054\u7f51\uff1f",
         "T\u00ednh n\u0103ng m\u1ea1ng \u0111\u00e3 \u0111\u01b0\u1ee3c g\u1ee1 b\u1ecf."},  // 需要联网...
        {"\u83b7\u5f97", "Nh\u1eadn"},                                                               // 获得
        {"\u91d1\u94b1 ", " ti\u1ec1n "},                                                            // 金钱_
        {" \u6d77\u661fX", " Sao bi\u1ec3n X"},                                                      // _海星X
        {"\u66f4\u591a\u7cbe\u5f69\u6e38\u620f", "C\u1ea3m \u01a1n \u0111\u00e3 ch\u01a1i!"}, // 更多精彩游戏
        {"\u5c3d\u5728\u6e38\u620f\u9891\u9053", "D\u1ecb Th\u1ebf T\u00e0 Qu\u00e2n"},       // 尽在游戏频道
        {"3g.tysis.cn", "Vi\u1ec7t ho\u00e1: NAM.P"},
        {"\u786e\u8ba4", "Tho\u00e1t"},                                                              // 确认
        {"\u9000\u51fa", "Tho\u00e1t"},                                                              // 退出
        {"\u8fc7\u957f ", "Qu\u00e1 d\u00e0i "},                                                    // 过长_
        {"http://3g.tysis.cn", ""},
    };

    static void patchStrings(ClassNode cn) {
        Map<String, String> m = new HashMap<>();
        for (String[] p : STR) m.put(p[0], p[1]);
        int n = 0;
        Set<String> used = new HashSet<>();
        for (MethodNode mn : cn.methods) {
            for (AbstractInsnNode x = mn.instructions.getFirst(); x != null; x = x.getNext()) {
                if (x.getOpcode() == Opcodes.LDC && ((LdcInsnNode) x).cst instanceof String) {
                    String v = m.get((String) ((LdcInsnNode) x).cst);
                    if (v != null) {
                        used.add((String) ((LdcInsnNode) x).cst);
                        ((LdcInsnNode) x).cst = v;
                        n++;
                    }
                }
            }
        }
        for (String[] p : STR) if (!used.contains(p[0])) log.println("CHUA DUNG: " + p[1]);
        expect("c: chuoi hien thi trong class da Viet hoa (so cho LDC)", n, 30);
    }

    // ===================================================================== i (Const)
    static void patchI(ClassNode cn) {
        MethodNode cl = method(cn, "<clinit>", "()V");
        int h = 0, w = 0, d = 0;
        for (AbstractInsnNode x = cl.instructions.getFirst(); x != null; x = x.getNext()) {
            if (isCall(x, Opcodes.INVOKEVIRTUAL, F, "getHeight", "()I")) {
                MethodInsnNode r = new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "fh", "(L" + F + ";)I", false);
                cl.instructions.set(x, r);
                x = r;
                h++;
            } else if (isCall(x, Opcodes.INVOKEVIRTUAL, F, "stringWidth", "(Ljava/lang/String;)I")) {
                // FONT_WIDTH = Q.stringWidth("宽") -> VH.sw0 = 12 (giu bo cuc goc tinh theo o chu Han)
                MethodInsnNode r = new MethodInsnNode(Opcodes.INVOKESTATIC, VH, "sw0", "(L" + F + ";Ljava/lang/String;)I", false);
                cl.instructions.set(x, r);
                x = r;
                w++;
            } else if (isField(x, Opcodes.PUTSTATIC, "i", "aE")) {
                // UI_DIALOG_HEIGHT = 2 * R -> 3 * R
                AbstractInsnNode mul = prev(x);
                if (mul.getOpcode() == Opcodes.DUP) mul = prev(mul);
                AbstractInsnNode r = prev(mul), two = prev(r);
                if (mul.getOpcode() == Opcodes.IMUL && isField(r, Opcodes.GETSTATIC, "i", "R") && intValue(two) == 2) {
                    cl.instructions.set(two, new InsnNode(Opcodes.ICONST_3));
                    d++;
                }
            }
        }
        expect("i.<clinit>: Font.getHeight -> VH.fh", h, 1);
        expect("i.<clinit>: Font.stringWidth -> VH.sw0", w, 1);
        expect("i.<clinit>: aE = 2*R -> 3*R", d, 1);
    }
}
