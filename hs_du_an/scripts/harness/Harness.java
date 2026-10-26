// Công cụ kiểm chứng trên desktop (KHÔNG đóng vào jar game).
// Gọi chính parser của game trên từng đoạn byte, kiểm parser đọc hết đúng đoạn,
// rồi gom mọi String trong cây object trả về để so với bộ quét utfscan.py.
// Dòng vào (stdin): file \t start \t end \t class \t method
// Dòng ra: OK|ERR \t file \t start \t consumed/len \t chuỗi1 \x1f chuỗi2 ...
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

public class Harness {
    static ClassLoader gl;
    public static void main(String[] a) throws Exception {
        gl = new URLClassLoader(new URL[]{new File(a[0]).toURI().toURL(), new File(a[1]).toURI().toURL()}, null);
        try {  // dựng platform FreeJ2ME để <clinit> dùng Font/Image của game chạy được
            Class<?> mp = Class.forName("org.recompile.mobile.MobilePlatform", true, gl);
            Object plat = mp.getConstructor(int.class, int.class).newInstance(240, 320);
            Class<?> mob = Class.forName("org.recompile.mobile.Mobile", true, gl);
            for (Method x : mob.getMethods()) if (x.getName().equals("setPlatform") && x.getParameterTypes().length == 1) x.invoke(null, plat);
        } catch (Throwable t) { System.err.println("platform init: " + t); }
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        Map<String, byte[]> cache = new HashMap<>();
        String line;
        while ((line = r.readLine()) != null) {
            String[] p = line.split("\t");
            byte[] all = cache.get(p[0]);
            if (all == null) { all = java.nio.file.Files.readAllBytes(new File(p[0]).toPath()); cache.put(p[0], all); }
            int s = Integer.parseInt(p[1]), e = Integer.parseInt(p[2]);
            ByteArrayInputStream bi = new ByteArrayInputStream(all, s, e - s);
            DataInputStream di = new DataInputStream(bi);
            try {
                Class<?> c = Class.forName(p[3].replace('/', '.'), false, gl);
                Method m = null;
                for (Method x : c.getDeclaredMethods())
                    if (x.getName().equals(p[4]) && Modifier.isStatic(x.getModifiers()) && x.getParameterTypes().length >= 1
                        && x.getParameterTypes()[0] == DataInputStream.class
                        && (p.length < 6 || x.getParameterTypes().length == Integer.parseInt(p[5]))) { m = x; break; }
                m.setAccessible(true);
                Object[] args = new Object[m.getParameterTypes().length];
                args[0] = di;
                for (int i = 1; i < args.length; i++) {
                    Class<?> t = m.getParameterTypes()[i];
                    if (t == int.class) args[i] = 0; else if (t == short.class) args[i] = (short) 0;
                    else if (t == byte.class) args[i] = (byte) 0; else if (t == boolean.class) args[i] = false;
                }
                Object res = m.invoke(null, args);
                int consumed = (e - s) - bi.available();
                List<String> strs = new ArrayList<>();
                collect(res, strs, new IdentityHashMap<>());
                StringBuilder sb = new StringBuilder();
                for (String x : strs) { if (sb.length() > 0) sb.append('\u001f'); sb.append(x.replace("\n", "\\n")); }
                out.println((consumed == e - s ? "OK" : "SHORT") + "\t" + p[0] + "\t" + s + "\t" + consumed + "/" + (e - s) + "\t" + sb);
            } catch (Throwable t) {
                Throwable c = t instanceof InvocationTargetException ? t.getCause() : t;
                out.println("ERR\t" + p[0] + "\t" + s + "\t" + c);
            }
        }
    }
    static void collect(Object o, List<String> out, IdentityHashMap<Object, Boolean> seen) throws Exception {
        if (o == null || seen.containsKey(o)) return;
        seen.put(o, true);
        if (o instanceof String) { out.add((String) o); return; }
        Class<?> c = o.getClass();
        if (c.isArray()) {
            if (c.getComponentType().isPrimitive()) return;
            for (int i = 0; i < Array.getLength(o); i++) collect(Array.get(o, i), out, seen);
            return;
        }
        if (c.getClassLoader() != gl) return;
        for (Class<?> k = c; k != null && k.getClassLoader() == gl; k = k.getSuperclass())
            for (Field f : k.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers()) || f.getType().isPrimitive()) continue;
                f.setAccessible(true);
                collect(f.get(o), out, seen);
            }
    }
}
