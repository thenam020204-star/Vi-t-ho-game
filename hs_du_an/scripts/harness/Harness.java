// Chạy: env -u JAVA_TOOL_OPTIONS java -Dfile.encoding=ISO_8859_1 -Djava.awt.headless=true -Dharness.stub=d.a:q:512 -cp <dir class> Harness <jar> <freej2me.jar> [file ra] < in.txt
// (file.encoding PHẢI là ISO_8859_1, nếu không MobilePlatform gọi System.exit(0) im lặng -> không in gì.)
// Công cụ kiểm chứng trên desktop (KHÔNG đóng vào jar game).
// Gọi chính parser của game trên từng đoạn byte, kiểm parser đọc hết đúng đoạn,
// rồi gom mọi String trong cây object trả về để so với bộ quét utfscan.py.
// Dòng vào (stdin): file \t start \t end \t class \t method
// Dòng ra: OK|ERR \t file \t start \t consumed/len \t chuỗi1 \x1f chuỗi2 ... \t offset-các-lần-readUTF (phân tách bằng ,)
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

public class Harness {
    static ClassLoader gl;
    static int errTrace = 0;
    public static void main(String[] a) throws Exception {
        // Giữ luồng ra gốc trước khi init FreeJ2ME (nó có thể thay System.out/System.in/exit).
        // Tham số thứ 3 (tuỳ chọn): file ghi kết quả; mặc định ghi thẳng vào fd 1.
        final PrintStream out = new PrintStream(a.length > 2 ? new FileOutputStream(a[2])
            : new FileOutputStream(FileDescriptor.out), true, "UTF-8");
        final InputStream in0 = new FileInputStream(FileDescriptor.in);
        try {  // ưu tiên MIDletLoader của FreeJ2ME (vá getResourceAsStream, đọc resource từ jar): cần FreeJ2ME trong -cp
            Class<?> ml = Class.forName("org.recompile.mobile.MIDletLoader");
            URL ju = new File(a[0]).toURI().toURL();
            gl = (ClassLoader) ml.getConstructor(URL.class, Map.class).newInstance(ju, new HashMap<String, String>());
            try {  // getMIDletResourceAsStream cần mainClass (lớp MIDlet chính) để định vị resource
                String mcn = (String) ml.getMethod("findMainClassInJar", URL.class).invoke(null, ju);
                Field mf = ml.getDeclaredField("mainClass"); mf.setAccessible(true);
                mf.set(gl, Class.forName(mcn, false, gl));
            } catch (Throwable t) { System.err.println("mainClass: " + t); }
        } catch (Throwable t) {
            System.err.println("MIDletLoader không dùng được, quay về URLClassLoader: " + t);
        gl = new URLClassLoader(new URL[]{new File(a[0]).toURI().toURL(), new File(a[1]).toURI().toURL()}, null);
        }
        try {  // dựng platform FreeJ2ME để <clinit> dùng Font/Image của game chạy được
            Class<?> mp = Class.forName("org.recompile.mobile.MobilePlatform", true, gl);
            Object plat = mp.getConstructor(int.class, int.class).newInstance(240, 320);
            try { Field lf = mp.getField("loader"); lf.set(plat, gl); } catch (Throwable t) { System.err.println("platform.loader: " + t); }
            Class<?> mob = Class.forName("org.recompile.mobile.Mobile", true, gl);
            for (Method x : mob.getMethods()) if (x.getName().equals("setPlatform")) { if (x.getParameterTypes().length == 1) x.invoke(null, plat); else x.invoke(null, plat, (Runnable) () -> {}); }
        } catch (Throwable t) { System.err.println("platform init: " + t); }
        // -Dharness.stub=lớp:trường:cỡ,... : gán mảng String[cỡ] toàn "" cho static String[] mà game nạp lúc chạy
        // (vd P1: d.a:q:512 vì <clinit> của d.g đọc d.a.q[..]). Không ảnh hưởng chuỗi thu được (chỉ lấy field instance).
        String stub = System.getProperty("harness.stub");
        if (stub != null) for (String sp : stub.split(",")) {
            String[] q = sp.split(":");
            Field f = Class.forName(q[0], true, gl).getDeclaredField(q[1]);
            f.setAccessible(true);
            Object arr;
            if (q[2].equals("new")) {  // đối tượng rỗng (không chạy constructor) cho static mà game nạp lúc chạy
                Field uf = Class.forName("sun.misc.Unsafe").getDeclaredField("theUnsafe"); uf.setAccessible(true);
                Object un = uf.get(null);
                arr = un.getClass().getMethod("allocateInstance", Class.class).invoke(un, f.getType());
            } else arr = mkArr(f.getType(), Integer.parseInt(q[2]));
            f.set(null, arr);
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(in0, "UTF-8"));
        Map<String, byte[]> cache = new HashMap<>();
        String line;
        while ((line = r.readLine()) != null) {
            String[] p = line.split("\t");
            if (p[0].equals("LOAD")) { load(p, out); continue; }
            byte[] all = cache.get(p[0]);
            if (all == null) { all = java.nio.file.Files.readAllBytes(new File(p[0]).toPath()); cache.put(p[0], all); }
            int s = Integer.parseInt(p[1]), e = Integer.parseInt(p[2]);
            final int base = s; final byte[] allb = all; final StringBuilder reads = new StringBuilder();
            // ghi lại mọi lần game đọc readUTF: u2 độ dài vừa đọc rồi read(buf, 0, độ dài) ngay sau đó
            ByteArrayInputStream bi = new ByteArrayInputStream(all, s, e - s) {
                public synchronized int read(byte[] b, int off, int len) {
                    int q = pos;
                    if (len >= 1 && q - 2 >= base) {
                        int L = ((allb[q - 2] & 0xff) << 8) | (allb[q - 1] & 0xff);
                        if (L == len) { if (reads.length() > 0) reads.append(','); reads.append(q - 2); }
                    }
                    return super.read(b, off, len);
                }
            };
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
                for (String x : strs) { if (sb.length() > 0) sb.append('\u001f'); sb.append(x.replace("\\", "\\\\").replace("\n", "\\n")); }
                out.println((consumed == e - s ? "OK" : "SHORT") + "\t" + p[0] + "\t" + s + "\t" + consumed + "/" + (e - s) + "\t" + sb + "\t" + reads);
            } catch (Throwable t) {
                Throwable c = t instanceof InvocationTargetException ? t.getCause() : t;
                out.println("ERR\t" + p[0] + "\t" + s + "\t" + c);
                if (errTrace++ < 3) c.printStackTrace();  // chỉ 3 lỗi đầu, để chẩn đoán
            }
        }
    }
    // Đối số số có thể kèm hậu tố s/i/b để chọn đúng quá tải short/int/byte.
    // Chế độ nạp: LOAD \t lớp \t phương thức static \t [đối số...]  (String/số theo kiểu tham số).
    // Gọi loader của game (đọc resource qua classpath hệ thống: đặt thư mục/jar dữ liệu vào -cp), rồi gom mọi String
    // từ giá trị trả về và từ các static của lớp đó. Ra: OK|ERR \t LOAD \t lớp.pt(đối số) \t - \t chuỗi...
    static void load(String[] p, PrintStream out) {
        String id = p[1] + "." + p[2] + (p.length > 3 ? "(" + p[3] + ")" : "()");
        try {
            Class<?> c = Class.forName(p[1].replace('/', '.'), true, gl);
            Method m = null;
            for (Method x : c.getDeclaredMethods()) {  // trùng tên + số tham số: đối số toàn số -> ưu tiên tham số số, ngược lại String
                if (!(x.getName().equals(p[2]) && Modifier.isStatic(x.getModifiers()) && x.getParameterTypes().length == p.length - 3)) continue;
                boolean okm = true;
                for (int i = 0; i < x.getParameterTypes().length; i++) {
                    Class<?> t = x.getParameterTypes()[i]; String ar = p[3 + i];
                    boolean num = ar.matches("-?\\d+[sib]?");
                    if (t == String.class) okm &= !num;
                    else if (t == short.class) okm &= num && !ar.matches(".*[ib]$");
                    else if (t == int.class) okm &= num && !ar.matches(".*[sb]$");
                    else if (t == byte.class) okm &= num && !ar.matches(".*[si]$");
                    else okm = false;
                }
                if (okm) { m = x; break; }
            }
            m.setAccessible(true);
            Object[] args = new Object[p.length - 3];
            for (int i = 0; i < args.length; i++) {
                Class<?> t = m.getParameterTypes()[i];
                if (t == String.class) args[i] = p[3 + i];
                else if (t == short.class) args[i] = Short.valueOf(p[3 + i].replaceAll("[sib]$", ""));
                else if (t == int.class) args[i] = Integer.valueOf(p[3 + i].replaceAll("[sib]$", ""));
                else if (t == byte.class) args[i] = Byte.valueOf(p[3 + i].replaceAll("[sib]$", ""));
                else throw new IllegalArgumentException("kiểu " + t);
            }
            Object res = m.invoke(null, args);
            if (System.getProperty("harness.debug") != null) System.err.println("LOAD " + id + " [" + m + "] -> " + (res == null ? "null" : res.getClass()));
            List<String> strs = new ArrayList<>();
            IdentityHashMap<Object, Boolean> seen = new IdentityHashMap<>();
            collect(res, strs, seen);
            for (Field f : c.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) || f.getType().isPrimitive()) continue;
                f.setAccessible(true);
                Object fv = f.get(null);
                if (System.getProperty("harness.debug") != null && fv != null && fv.getClass().isArray()) System.err.println("  static " + f.getName() + " len=" + Array.getLength(fv));
                collect(fv, strs, seen);
            }
            StringBuilder sb = new StringBuilder();
            for (String x : strs) { if (sb.length() > 0) sb.append('\u001f'); sb.append(x.replace("\\", "\\\\").replace("\n", "\\n")); }
            out.println("OK\tLOAD\t" + id + "\t-\t" + sb + "\t");
        } catch (Throwable t) {
            Throwable c = t instanceof InvocationTargetException ? t.getCause() : t;
            out.println("ERR\tLOAD\t" + id + "\t" + c);
        }
    }

    // mảng (nhiều chiều) cỡ n; String[] điền "", mảng con được tạo đủ cỡ
    static Object mkArr(Class<?> t, int n) {
        Class<?> ct = t.getComponentType();
        Object a = Array.newInstance(ct, n);
        if (ct == String.class) Arrays.fill((Object[]) a, "");
        else if (ct.isArray()) for (int i = 0; i < n; i++) Array.set(a, i, mkArr(ct, n));
        return a;
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
