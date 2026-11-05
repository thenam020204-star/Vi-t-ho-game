import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import javax.microedition.lcdui.*;

/** Loads the ORIGINAL fontcus (resource-based) and the NEW fontcus (self-contained)
 *  in isolated class loaders and proves they are observationally identical. */
public class Compare {

    static class Iso extends URLClassLoader {
        Iso(File dir) throws Exception { super(new URL[]{dir.toURI().toURL()}, Compare.class.getClassLoader()); }
        protected Class<?> loadClass(String n, boolean r) throws ClassNotFoundException {
            if (n.equals("fontcus")) {
                Class<?> c = findLoadedClass(n);
                if (c == null) c = findClass(n);
                if (r) resolveClass(c);
                return c;
            }
            return super.loadClass(n, r);
        }
    }

    static int fails = 0;
    static void check(String what, boolean ok) {
        System.out.println((ok ? "  PASS  " : "  FAIL  ") + what);
        if (!ok) fails++;
    }
    static Object f(Class<?> c, String n) throws Exception { Field x=c.getField(n); x.setAccessible(true); return x.get(null); }
    static void setf(Class<?> c, String n, Object v) throws Exception { Field x=c.getField(n); x.setAccessible(true); x.set(null,v); }

    public static void main(String[] args) throws Exception {
        Class<?> A = new Iso(new File("refA")).loadClass("fontcus");
        Class<?> B = new Iso(new File("refB")).loadClass("fontcus");

        System.out.println("== static state ==");
        check("fontHeight  " + f(A,"fontHeight"), f(A,"fontHeight").equals(f(B,"fontHeight")));
        check("atlasW      " + f(A,"atlasW"),     f(A,"atlasW").equals(f(B,"atlasW")));
        check("atlasOk     " + f(A,"atlasOk"),    f(A,"atlasOk").equals(f(B,"atlasOk")));
        check("unusedNhungW " + f(A,"unusedNhungW"), f(A,"unusedNhungW").equals(f(B,"unusedNhungW")));
        check("glyphW[]",   Arrays.equals((int[])f(A,"glyphW"),   (int[])f(B,"glyphW")));
        check("glyphOff[]", Arrays.equals((int[])f(A,"glyphOff"), (int[])f(B,"glyphOff")));
        check("charMap[8448]", Arrays.equals((short[])f(A,"charMap"), (short[])f(B,"charMap")));
        byte[][] ga=(byte[][])f(A,"glyphBits"), gb=(byte[][])f(B,"glyphBits");
        boolean bits = ga.length==gb.length;
        for (int i=0; bits && i<ga.length; i++) bits = Arrays.equals(ga[i], gb[i]);
        check("glyphBits[" + ga.length + "][" + ga[0].length + "]", bits);
        Hashtable ha=(Hashtable)f(A,"charIndex"), hb=(Hashtable)f(B,"charIndex");
        boolean hs = ha.size()==hb.size();
        for (Object k : Collections.list(ha.keys())) hs &= ha.get(k).equals(hb.get(k));
        check("charIndex (" + ha.size() + " entries)", hs);

        System.out.println("== metrics over U+0000..U+20FF ==");
        Method awA=A.getMethod("charWidth",char.class),   awB=B.getMethod("charWidth",char.class);
        Method dcA=A.getMethod("doChu",int.class,int.class), dcB=B.getMethod("doChu",int.class,int.class);
        boolean m1=true, m2=true;
        for (int c=0;c<0x2100;c++) {
            m1 &= awA.invoke(null,(char)c).equals(awB.invoke(null,(char)c));
            m2 &= dcA.invoke(null,7,c).equals(dcB.invoke(null,7,c));
        }
        check("charWidth(c) for all 8448 code points", m1);
        check("doChu(7,c)  for all 8448 code points", m2);

        String[] samples = {
            "Tiếng Việt có dấu",
            "Đại chiến Tam Quốc 2024!",
            "Lượng máu: 1234/5678 (99%)",
            "AaBbCc 0123456789 .,;:!@/\\*()[]{}|#$%^&<>?'\"+-",
            "ỮỮỢỠỬ ỸỴỶ …‚",
            "không có glyph: 中文 Ж =_~`",
            "", " ", "nhung"
        };
        Method swA=A.getMethod("strWidth",String.class), swB=B.getMethod("strWidth",String.class);
        boolean sw=true;
        for (String s : samples) sw &= swA.invoke(null,s).equals(swB.invoke(null,s));
        check("strWidth on " + samples.length + " sample strings", sw);

        Method xd1A=A.getMethod("XuongDong",int.class,int.class,int.class,int.class);
        Method xd1B=B.getMethod("XuongDong",int.class,int.class,int.class,int.class);
        boolean x1=true;
        for (int a=0;a<70;a+=3) for (int b=0;b<70;b+=5) for (int c=0;c<70;c+=7) for (int ch : new int[]{32,65,97,0})
            x1 &= xd1A.invoke(null,ch,a,b,c).equals(xd1B.invoke(null,ch,a,b,c));
        check("XuongDong over 4x24x14x10 grid", x1);

        Method xd2A=A.getMethod("XuongDong2",String.class,int.class,int.class,int.class,int.class);
        Method xd2B=B.getMethod("XuongDong2",String.class,int.class,int.class,int.class,int.class);
        boolean x2=true;
        for (String s : samples) for (int i=-1;i<=s.length();i++) for (int p=0;p<60;p+=7) for (int sl=0;sl<60;sl+=13) for (int hl=0;hl<80;hl+=17)
            x2 &= xd2A.invoke(null,s,i,p,sl,hl).equals(xd2B.invoke(null,s,i,p,sl,hl));
        x2 &= xd2A.invoke(null,null,0,0,0,0).equals(xd2B.invoke(null,null,0,0,0,0));
        check("XuongDong2 exhaustive over samples", x2);

        System.out.println("== pixel-exact rendering ==");
        int[] colors = {0x000000, 0xFFFFFF, 0xFF0000, 0x3C7A2B, 0x123456, 0xFFFFFF, 0xFF0000};
        boolean px = true, pxFallback = true;
        for (int pass=0; pass<2; pass++) {
            if (pass==1) { setf(A,"atlasOk",Boolean.FALSE); setf(B,"atlasOk",Boolean.FALSE);
                           setf(A,"cacheN",0); setf(B,"cacheN",0); }
            Method dsA=A.getMethod("drawString",Graphics.class,String.class,int.class,int.class);
            Method dsB=B.getMethod("drawString",Graphics.class,String.class,int.class,int.class);
            Method dchA=A.getMethod("drawChar",Graphics.class,char.class,int.class,int.class);
            Method dchB=B.getMethod("drawChar",Graphics.class,char.class,int.class,int.class);
            Method duA=A.getMethod("drawUniversal",Graphics.class,String.class,int.class,int.class,int.class);
            Method duB=B.getMethod("drawUniversal",Graphics.class,String.class,int.class,int.class,int.class);
            for (int ci=0; ci<colors.length; ci++) {
                Graphics ga2=new Graphics(400,40), gb2=new Graphics(400,40);
                ga2.setColor(colors[ci]); gb2.setColor(colors[ci]);
                for (String s : samples) { dsA.invoke(null,ga2,s,3,20); dsB.invoke(null,gb2,s,3,20); }
                for (int anc : new int[]{20,17,24,2,32,9,3}) { duA.invoke(null,ga2,samples[0],200,20,anc); duB.invoke(null,gb2,samples[0],200,20,anc); }
                boolean eq = Arrays.equals(ga2.buf, gb2.buf);
                if (pass==0) px &= eq; else pxFallback &= eq;
            }
            // every glyph, one by one
            Graphics ga3=new Graphics(4000,30), gb3=new Graphics(4000,30);
            ga3.setColor(0x2288FF); gb3.setColor(0x2288FF);
            int xa=0, xb=0;
            for (int c=0;c<0x2100;c++) {
                Object ra=dchA.invoke(null,ga3,(char)c,xa%3900,20);
                Object rb=dchB.invoke(null,gb3,(char)c,xb%3900,20);
                if (!ra.equals(rb)) { if(pass==0) px=false; else pxFallback=false; }
                xa += ((Integer)ra)+1; xb += ((Integer)rb)+1;
            }
            boolean eq2 = Arrays.equals(ga3.buf, gb3.buf);
            if (pass==0) px &= eq2; else pxFallback &= eq2;
        }
        check("drawString/drawChar/drawUniversal, atlas path, 7 colours", px);
        check("same, drawRGB fallback path (atlasOk=false)", pxFallback);

        System.out.println(fails==0 ? "\nALL CHECKS PASSED" : "\n" + fails + " CHECK(S) FAILED");
        System.exit(fails==0?0:1);
    }
}
