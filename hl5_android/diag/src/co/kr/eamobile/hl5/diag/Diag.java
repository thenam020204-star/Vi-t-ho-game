package co.kr.eamobile.hl5.diag;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Lop chan doan HL5-VI. MOI ham public bao quanh try/catch(Throwable): lop nay khong duoc phep lam crash app.
 * Ham khong doi so (s_*) de chen vao smali khong can thanh ghi.
 */
public final class Diag {
    private static final String TAG = "HL5Diag";
    private static final Object LOCK = new Object();
    private static boolean started;
    private static File dir;
    private static long t0 = System.currentTimeMillis();
    private static int frames;
    private static String variant = "?";

    private Diag() {}

    // ---------- diem vao ----------
    private static Context appCtx;
    public static void s_launcher(Context c) { remember(c); onLaunch(null); mark("CompatibilityActivity.onCreate"); }
    private static void remember(Context c) {
        try { if (c != null && appCtx == null) { Context a = c.getApplicationContext(); appCtx = a != null ? a : c; } } catch (Throwable t) { }
    }
    public static void s_clinit() { onLaunch(null); mark("MainActivity.<clinit> bat dau"); }
    public static void s_loadLibBefore() { mark("TRUOC System.loadLibrary(hl5_arm64_bridge)"); }
    public static void s_loadLibAfter() { mark("SAU System.loadLibrary OK (JNI_OnLoad da chay)"); }
    public static void s_clinitEnd() { mark("MainActivity.<clinit> xong"); }
    public static void s_mainCreate(Context c) { remember(c); onLaunch(null); mark("MainActivity.onCreate"); }
    public static void s_instBefore() { mark("TRUOC TargetAssets.install (kiem sha256 + copy asset)"); }
    public static void s_instAfter() { mark("SAU TargetAssets.install OK"); }
    public static void s_startGameView() { mark("startGameView"); }
    public static void s_surfCreatedJava() { mark("onSurfaceCreated (Java)"); }
    public static void s_nSurfBefore() { mark("TRUOC nativeOnSurfaceCreated (nap guest .so + khoi tao)"); }
    public static void s_nSurfAfter() { mark("SAU nativeOnSurfaceCreated OK"); }
    public static void s_nChangedBefore() { mark("TRUOC nativeOnSurfaceChanged"); }
    public static void s_nChangedAfter() { mark("SAU nativeOnSurfaceChanged OK"); }
    public static void s_nResumeAfter() { mark("SAU nativeOnResume OK"); }
    public static void s_frame() {
        try {
            frames++;
            if (frames <= 5 || frames == 30 || frames == 120 || frames % 600 == 0) mark("khung hinh #" + frames + " (truoc nativeOnDrawFrame)");
        } catch (Throwable t) { }
    }
    public static void s_pause() { mark("onPause"); }
    public static void s_resume() { mark("onResume"); }
    public static void s_destroy() { mark("onDestroy"); }
    public static void s_throwable(Throwable t) {
        try { mark("NGOAI LE: " + stack(t)); } catch (Throwable x) { }
    }
    public static void mark(String s) {
        try {
            long dt = System.currentTimeMillis() - t0;
            String line = "+" + dt + "ms [" + Thread.currentThread().getName() + "] " + s;
            Log.i(TAG, line);
            File d = ensureDir();
            if (d == null) return;
            synchronized (LOCK) {
                FileOutputStream o = new FileOutputStream(new File(d, "steps.txt"), true);
                try { o.write((line + "\n").getBytes("UTF-8")); o.flush(); } finally { o.close(); }
            }
        } catch (Throwable t) { }
    }

    // ---------- khoi dong ----------
    private static Context ctx() {
        if (appCtx != null) return appCtx;
        try {
            Class<?> c = Class.forName("android.app.ActivityThread");
            Object app = c.getMethod("currentApplication").invoke(null);
            return (Context) app;
        } catch (Throwable t) { return null; }
    }

    private static File ensureDir() {
        if (dir != null) return dir;
        try {
            Context c = ctx();
            if (c == null) return null;
            File d = null;
            try { d = c.getExternalFilesDir("diag"); } catch (Throwable t) { }
            if (d == null) d = new File(c.getFilesDir(), "diag");
            d.mkdirs();
            dir = d;
        } catch (Throwable t) { }
        return dir;
    }

    private static void onLaunch(Context unused) {
        try {
            synchronized (LOCK) {
                if (started) return;
                started = true;
            }
            File d = ensureDir();
            Context c = ctx();
            if (d != null) {
                File cur = new File(d, "steps.txt"), prev = new File(d, "steps_prev.txt");
                if (cur.exists()) { prev.delete(); cur.renameTo(prev); }
            }
            t0 = System.currentTimeMillis();
            mark("=== lan chay moi, pid=" + android.os.Process.myPid() + ", " + new Date() + " ===");
            installHandler();
            if (c != null) { try { variant = readAsset(c, "diag-variant.txt").trim(); } catch (Throwable t) { } }
            mark("variant=" + variant);
            final Context cc = c;
            // bao cao lan chay truoc: lam dong bo (nhanh, <~1s) de chac chan xong truoc khi game co the crash
            writeReport(cc);
        } catch (Throwable t) { }
    }

    private static void installHandler() {
        try {
            final Thread.UncaughtExceptionHandler old = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread th, Throwable ex) {
                    try {
                        String s = "Thread " + th.getName() + " pid=" + android.os.Process.myPid() + " " + new Date() + "\n" + stack(ex);
                        mark("UNCAUGHT JAVA EXCEPTION: " + s);
                        File d = ensureDir();
                        if (d != null) writeFile(new File(d, "crash_java.txt"), s.getBytes("UTF-8"));
                    } catch (Throwable t) { }
                    try { if (old != null) old.uncaughtException(th, ex); } catch (Throwable t) { }
                }
            });
        } catch (Throwable t) { }
    }

    // ---------- bao cao ----------
    private static void writeReport(Context c) {
        try {
            StringBuilder sb = new StringBuilder(64 * 1024);
            sb.append("HL5-VI nhat ky chan doan\n");
            sb.append("Tao luc: ").append(new Date()).append("\nvariant: ").append(variant).append("\n\n");
            try { device(sb, c); } catch (Throwable t) { sb.append("[device loi] ").append(stack(t)).append('\n'); }
            try { files(sb, c); } catch (Throwable t) { sb.append("[files loi] ").append(stack(t)).append('\n'); }
            File d = ensureDir();
            if (d != null) {
                sb.append("\n===== steps_prev.txt (cac moc cua LAN CHAY TRUOC) =====\n");
                appendFile(sb, new File(d, "steps_prev.txt"), 60000);
                sb.append("\n===== crash_java.txt (ngoai le Java lan truoc, neu co) =====\n");
                appendFile(sb, new File(d, "crash_java.txt"), 60000);
            }
            try { exitReasons(sb, c); } catch (Throwable t) { sb.append("[exit reasons loi] ").append(stack(t)).append('\n'); }
            try { logcat(sb); } catch (Throwable t) { sb.append("[logcat loi] ").append(stack(t)).append('\n'); }
            byte[] data = sb.toString().getBytes("UTF-8");
            String name = "HL5_diag_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".txt";
            boolean ok = false;
            if (d != null) { try { writeFile(new File(d, name), data); ok = true; } catch (Throwable t) { mark("ghi ban sao that bai: " + t); } }
            boolean dl = false;
            try { dl = saveToDownloads(c, name, data); } catch (Throwable t) { mark("MediaStore that bai: " + t); }
            mark("bao cao: " + name + " (Downloads=" + dl + ", extFiles=" + ok + ", " + data.length + " byte)");
            // sau khi da luu, xoa crash_java cu de lan sau khong lap lai
            if (d != null) new File(d, "crash_java.txt").delete();
            if (dl || ok) toast(c, dl ? "Đã lưu nhật ký chẩn đoán vào Downloads" : "Đã lưu nhật ký chẩn đoán (Android/data/co.kr.eamobile.hl5.runner/files/diag)");
        } catch (Throwable t) { Log.w(TAG, "writeReport: " + t); }
    }

    private static boolean saveToDownloads(Context c, String name, byte[] data) throws Exception {
        if (c == null || Build.VERSION.SDK_INT < 29) return false;
        ContentResolver r = c.getContentResolver();
        Uri base = Uri.parse("content://media/external/downloads");
        ContentValues v = new ContentValues();
        v.put("_display_name", name);
        v.put("mime_type", "text/plain");
        v.put("relative_path", "Download/");
        v.put("is_pending", 1);
        Uri u = r.insert(base, v);
        if (u == null) return false;
        OutputStream o = r.openOutputStream(u);
        if (o == null) return false;
        try { o.write(data); o.flush(); } finally { o.close(); }
        ContentValues v2 = new ContentValues();
        v2.put("is_pending", 0);
        r.update(u, v2, null, null);
        return true;
    }

    private static void toast(final Context c, final String s) {
        try {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() { try { Toast.makeText(c, s, Toast.LENGTH_LONG).show(); } catch (Throwable t) { } }
            });
        } catch (Throwable t) { }
    }

    private static void device(StringBuilder sb, Context c) throws Exception {
        sb.append("===== may =====\n");
        sb.append("Model: ").append(Build.MANUFACTURER).append(' ').append(Build.MODEL).append(" (").append(Build.DEVICE).append(")\n");
        sb.append("Android: ").append(Build.VERSION.RELEASE).append(" SDK ").append(Build.VERSION.SDK_INT).append(" patch ").append(sget("android.os.Build$VERSION", "SECURITY_PATCH")).append('\n');
        sb.append("Build: ").append(Build.FINGERPRINT).append('\n');
        sb.append("CPU_ABI: ").append(Build.CPU_ABI).append(" / ").append(Build.CPU_ABI2).append('\n');
        Object abis = sget("android.os.Build", "SUPPORTED_ABIS");
        if (abis instanceof String[]) { sb.append("SUPPORTED_ABIS:"); for (String a : (String[]) abis) sb.append(' ').append(a); sb.append('\n'); }
        sb.append("Hardware: ").append(Build.HARDWARE).append(" board=").append(Build.BOARD).append(" soc=").append(sget("android.os.Build", "SOC_MODEL")).append('\n');
        sb.append("os.arch: ").append(System.getProperty("os.arch")).append(" vm=").append(System.getProperty("java.vm.version")).append('\n');
        try {
            Class<?> os = Class.forName("android.system.Os"), oc = Class.forName("android.system.OsConstants");
            int pg = oc.getField("_SC_PAGESIZE").getInt(null);
            sb.append("Kich thuoc trang bo nho: ").append(os.getMethod("sysconf", int.class).invoke(null, pg)).append('\n');
        } catch (Throwable t) { sb.append("Kich thuoc trang: ?\n"); }
        if (c != null) {
            DisplayMetrics m = c.getResources().getDisplayMetrics();
            sb.append("Man hinh: ").append(m.widthPixels).append('x').append(m.heightPixels).append(" density=").append(m.density).append(" dpi=").append(m.densityDpi).append('\n');
            ActivityManager am = (ActivityManager) c.getSystemService("activity");
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            sb.append("RAM trong: ").append(mi.availMem >> 20).append(" MB / ").append(mi.totalMem >> 20).append(" MB, lowMemory=").append(mi.lowMemory).append('\n');
            sb.append("Goi: ").append(c.getPackageName()).append(" nativeLibraryDir=").append(c.getApplicationInfo().nativeLibraryDir).append('\n');
            sb.append("sourceDir: ").append(c.getApplicationInfo().sourceDir).append('\n');
            try {
                android.content.pm.PackageInfo pi = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
                sb.append("versionCode=").append(pi.versionCode).append(" versionName=").append(pi.versionName).append(" lastUpdate=").append(new Date(pi.lastUpdateTime)).append('\n');
            } catch (Throwable t) { }
        }
    }

    private static void files(StringBuilder sb, Context c) {
        if (c == null) return;
        sb.append("\n===== tep =====\n");
        listDir(sb, new File(c.getApplicationInfo().nativeLibraryDir), "nativeLibraryDir");
        listDir(sb, c.getFilesDir(), "filesDir");
        File v = new File(c.getFilesDir(), "runner-assets-verified.json");
        if (v.exists()) { sb.append("runner-assets-verified.json:\n"); appendFile(sb, v, 4000); }
        File ex = null;
        try { ex = c.getExternalFilesDir(null); } catch (Throwable t) { }
        if (ex != null) listDir(sb, ex, "externalFilesDir");
        try { sb.append("assets/target-assets.json: ").append(readAsset(c, "target-assets.json")).append('\n'); } catch (Throwable t) { sb.append("doc target-assets.json loi: ").append(t).append('\n'); }
    }

    private static void listDir(StringBuilder sb, File d, String label) {
        sb.append(label).append(" = ").append(d).append('\n');
        File[] fs = d.listFiles();
        if (fs == null) { sb.append("  (khong liet ke duoc)\n"); return; }
        for (File f : fs) sb.append("  ").append(f.getName()).append(f.isDirectory() ? "/" : "").append("  ").append(f.length()).append(" B  ").append(new Date(f.lastModified())).append('\n');
    }

    private static void exitReasons(StringBuilder sb, Context c) throws Exception {
        sb.append("\n===== ApplicationExitInfo (API 30+) =====\n");
        if (c == null || Build.VERSION.SDK_INT < 30) { sb.append("(khong co tren Android < 11)\n"); return; }
        ActivityManager am = (ActivityManager) c.getSystemService("activity");
        Method m = ActivityManager.class.getMethod("getHistoricalProcessExitReasons", String.class, int.class, int.class);
        List<?> l = (List<?>) m.invoke(am, c.getPackageName(), 0, 5);
        sb.append("so muc: ").append(l == null ? 0 : l.size()).append('\n');
        if (l == null) return;
        int i = 0;
        for (Object e : l) {
            sb.append("--- muc ").append(i++).append(" ---\n").append(e).append('\n');
            try {
                Method gt = e.getClass().getMethod("getTraceInputStream");
                InputStream in = (InputStream) gt.invoke(e);
                if (in != null) {
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    byte[] b = new byte[8192]; int n;
                    try { while ((n = in.read(b)) > 0 && bo.size() < 262144) bo.write(b, 0, n); } finally { in.close(); }
                    sb.append("trace/tombstone (").append(bo.size()).append(" byte, chi giu doan chu doc duoc):\n");
                    strings(sb, bo.toByteArray(), 40000);
                }
            } catch (Throwable t) { sb.append("(doc trace loi: ").append(t).append(")\n"); }
        }
    }

    private static void strings(StringBuilder sb, byte[] b, int cap) {
        int start = -1, out = 0;
        for (int i = 0; i <= b.length && out < cap; i++) {
            boolean p = i < b.length && ((b[i] >= 32 && b[i] < 127) || b[i] == '\n' || b[i] == '\t');
            if (p) { if (start < 0) start = i; }
            else if (start >= 0) {
                if (i - start >= 4) { String s = new String(b, start, i - start); sb.append(s).append('\n'); out += s.length() + 1; }
                start = -1;
            }
        }
    }

    private static void logcat(StringBuilder sb) throws Exception {
        sb.append("\n===== logcat -d -v threadtime (chi log cua chinh app) =====\n");
        final Process p = Runtime.getRuntime().exec(new String[]{"logcat", "-d", "-v", "threadtime", "-t", "5000"});
        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
        Thread rd = new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream in = p.getInputStream();
                    byte[] b = new byte[8192]; int n;
                    while ((n = in.read(b)) > 0) { if (bo.size() < 900000) bo.write(b, 0, n); }
                } catch (Throwable t) { }
            }
        }, "diag-logcat");
        rd.setDaemon(true);
        rd.start();
        rd.join(6000);
        try { p.destroy(); } catch (Throwable t) { }
        sb.append(new String(bo.toByteArray(), "UTF-8")).append("\n(het logcat, ").append(bo.size()).append(" byte)\n");
    }

    // ---------- tien ich ----------
    private static Object sget(String cls, String field) {
        try { return Class.forName(cls).getField(field).get(null); } catch (Throwable t) { return "?"; }
    }

    private static String readAsset(Context c, String n) throws Exception {
        InputStream in = c.getAssets().open(n);
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            byte[] b = new byte[4096]; int k;
            while ((k = in.read(b)) > 0) bo.write(b, 0, k);
            return new String(bo.toByteArray(), "UTF-8");
        } finally { in.close(); }
    }

    private static void appendFile(StringBuilder sb, File f, int cap) {
        try {
            if (!f.exists()) { sb.append("(khong co)\n"); return; }
            FileInputStream in = new FileInputStream(f);
            try {
                byte[] b = new byte[(int) Math.min(f.length(), cap)];
                int off = 0, n;
                while (off < b.length && (n = in.read(b, off, b.length - off)) > 0) off += n;
                sb.append(new String(b, 0, off, "UTF-8"));
                if (sb.charAt(sb.length() - 1) != '\n') sb.append('\n');
            } finally { in.close(); }
        } catch (Throwable t) { sb.append("(doc loi: ").append(t).append(")\n"); }
    }

    private static void writeFile(File f, byte[] data) throws Exception {
        FileOutputStream o = new FileOutputStream(f);
        try { o.write(data); o.flush(); } finally { o.close(); }
    }

    private static String stack(Throwable t) {
        StringWriter w = new StringWriter();
        t.printStackTrace(new PrintWriter(w));
        return w.toString();
    }
}
