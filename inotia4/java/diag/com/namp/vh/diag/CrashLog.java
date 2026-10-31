package com.namp.vh.diag;

import android.app.ActivityManager;
import android.app.ApplicationExitInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Ban CHAN DOAN (khong vao ban phat hanh): ghi lai nguyen nhan app bi thoat bat thuong de lan mo sau hien ra.
 * - Loi Java: UncaughtExceptionHandler ghi stack trace vao filesDir/namp_crash_java.txt (roi chuyen tiep handler cu).
 * - Moi kieu thoat (Java, native SIGSEGV/abort, tu exit()/kill(), ANR, het RAM): ApplicationExitInfo (Android 11+).
 * - Logcat cua chinh app (Android cho app doc log cung uid, khong can quyen).
 */
public final class CrashLog {
    static final String TAG = "NAMP-DIAG";
    private static final String PREF = "namp_diag";
    private static final String KEY_SHOWN = "shown_ts";
    private static boolean installed;

    private CrashLog() { }

    static File javaFile(Context c) {
        return new File(c.getFilesDir(), "namp_crash_java.txt");
    }

    /** Goi som nhat co the (DiagProvider.onCreate, initOrder cao nhat). */
    public static synchronized void install(final Context ctx) {
        if (installed) return;
        installed = true;
        final Context c = ctx.getApplicationContext() != null ? ctx.getApplicationContext() : ctx;
        final Thread.UncaughtExceptionHandler prev = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                try {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    pw.println("time=" + System.currentTimeMillis() + " (" + fmt(System.currentTimeMillis()) + ")");
                    pw.println("thread=" + (t == null ? "?" : t.getName()));
                    if (e != null) e.printStackTrace(pw);
                    pw.flush();
                    FileOutputStream f = new FileOutputStream(javaFile(c));
                    f.write(sw.toString().getBytes("UTF-8"));
                    f.close();
                    Log.e(TAG, "uncaught: " + sw);
                } catch (Throwable ignore) {
                }
                if (prev != null) {
                    prev.uncaughtException(t, e);
                } else {
                    Process.killProcess(Process.myPid());
                    System.exit(10);
                }
            }
        });
        Log.i(TAG, "da cai handler, sdk=" + Build.VERSION.SDK_INT);
    }

    static String fmt(long ts) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(ts));
    }

    private static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public static void markShown(Context c) {
        prefs(c).edit().putLong(KEY_SHOWN, System.currentTimeMillis()).commit();
        javaFile(c).delete();
    }

    /** Bao cao neu lan chay truoc thoat bat thuong (chua hien lan nao), nguoc lai null. */
    public static String pending(Context c) {
        long shown = prefs(c).getLong(KEY_SHOWN, 0);
        StringBuilder sb = new StringBuilder();
        boolean bad = false;
        File jf = javaFile(c);
        if (jf.exists() && jf.length() > 0) {
            bad = true;
            sb.append("===== LOI JAVA (UncaughtException) =====\n").append(readFile(jf, 64 * 1024)).append('\n');
        }
        if (Build.VERSION.SDK_INT >= 30) {
            try {
                bad |= exitInfo(c, shown, sb);
            } catch (Throwable t) {
                sb.append("exitInfo loi: ").append(t).append('\n');
            }
        }
        if (!bad) return null;
        StringBuilder head = new StringBuilder();
        head.append("Inotia 4 Viet hoa - BAO CAO CHAN DOAN\n");
        head.append("Thoi diem: ").append(fmt(System.currentTimeMillis())).append('\n');
        head.append("May: ").append(Build.MANUFACTURER).append(' ').append(Build.MODEL)
            .append(" | Android ").append(Build.VERSION.RELEASE).append(" (API ").append(Build.VERSION.SDK_INT).append(")\n");
        head.append("ABI: ").append(join(Build.SUPPORTED_ABIS)).append(" | fingerprint: ").append(Build.FINGERPRINT).append('\n');
        try {
            head.append("pagesize: ").append(android.system.Os.sysconf(android.system.OsConstants._SC_PAGESIZE)).append('\n');
        } catch (Throwable ignore) {
        }
        try {
            head.append("ban app: ").append(c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName).append('\n');
        } catch (Throwable ignore) {
        }
        head.append('\n');
        sb.insert(0, head);
        sb.append("\n===== LOGCAT CUA APP (moi nhat o cuoi) =====\n").append(logcat());
        return sb.toString();
    }

    private static String join(String[] a) {
        if (a == null) return "?";
        StringBuilder s = new StringBuilder();
        for (String x : a) { if (s.length() > 0) s.append(','); s.append(x); }
        return s.toString();
    }

    @android.annotation.TargetApi(30)
    private static boolean exitInfo(Context c, long shown, StringBuilder sb) throws Exception {
        ActivityManager am = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        List<ApplicationExitInfo> list = am.getHistoricalProcessExitReasons(c.getPackageName(), 0, 8);
        boolean bad = false;
        sb.append("===== ApplicationExitInfo (moi nhat truoc) =====\n");
        for (ApplicationExitInfo e : list) {
            int r = e.getReason();
            boolean abnormal = r == ApplicationExitInfo.REASON_CRASH || r == ApplicationExitInfo.REASON_CRASH_NATIVE
                || r == ApplicationExitInfo.REASON_ANR || r == ApplicationExitInfo.REASON_SIGNALED
                || r == ApplicationExitInfo.REASON_EXIT_SELF || r == ApplicationExitInfo.REASON_INITIALIZATION_FAILURE
                || r == ApplicationExitInfo.REASON_DEPENDENCY_DIED || r == ApplicationExitInfo.REASON_EXCESSIVE_RESOURCE_USAGE
                || r == ApplicationExitInfo.REASON_LOW_MEMORY || r == ApplicationExitInfo.REASON_UNKNOWN;
            boolean isNew = e.getTimestamp() > shown;
            if (abnormal && isNew) bad = true;
            sb.append(isNew ? "[MOI] " : "[cu]  ").append(fmt(e.getTimestamp()))
                .append(" pid=").append(e.getPid()).append(" ly_do=").append(reason(r))
                .append(" status=").append(e.getStatus()).append(" importance=").append(e.getImportance())
                .append(" pss=").append(e.getPss()).append("KB rss=").append(e.getRss()).append("KB\n");
            if (e.getDescription() != null) sb.append("   mo ta: ").append(e.getDescription()).append('\n');
            if (isNew && (r == ApplicationExitInfo.REASON_CRASH_NATIVE || r == ApplicationExitInfo.REASON_ANR)) {
                InputStream in = null;
                try {
                    in = e.getTraceInputStream();
                } catch (Throwable ignore) {
                }
                if (in != null) {
                    byte[] b = readAll(in, 512 * 1024);
                    // native: tombstone protobuf (Android 12+) -> lay cac chuoi in duoc (ten .so, ham, abort message)
                    sb.append("   --- trace ---\n").append(r == ApplicationExitInfo.REASON_ANR
                        ? new String(b, 0, Math.min(b.length, 48 * 1024), "UTF-8") : printable(b, 24 * 1024)).append('\n');
                }
            }
        }
        return bad;
    }

    private static String reason(int r) {
        switch (r) {
            case 1: return "EXIT_SELF(app tu goi exit)";
            case 2: return "SIGNALED(bi kill bang tin hieu)";
            case 3: return "LOW_MEMORY";
            case 4: return "CRASH(Java)";
            case 5: return "CRASH_NATIVE";
            case 6: return "ANR";
            case 7: return "INITIALIZATION_FAILURE";
            case 8: return "PERMISSION_CHANGE";
            case 9: return "EXCESSIVE_RESOURCE_USAGE";
            case 10: return "USER_REQUESTED";
            case 11: return "USER_STOPPED";
            case 12: return "DEPENDENCY_DIED";
            case 13: return "OTHER";
            case 14: return "FREEZER";
            case 15: return "PACKAGE_STATE_CHANGE";
            case 16: return "PACKAGE_UPDATED";
            default: return "UNKNOWN(" + r + ")";
        }
    }

    private static String printable(byte[] b, int max) {
        StringBuilder s = new StringBuilder();
        int start = -1;
        for (int i = 0; i <= b.length && s.length() < max; i++) {
            int v = i < b.length ? (b[i] & 0xff) : 0;
            boolean p = v >= 0x20 && v < 0x7f;
            if (p && start < 0) start = i;
            if (!p && start >= 0) {
                if (i - start >= 4) s.append(new String(b, start, i - start)).append('\n');
                start = -1;
            }
        }
        return s.toString();
    }

    private static byte[] readAll(InputStream in, int max) throws Exception {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while (o.size() < max && (n = in.read(buf)) > 0) o.write(buf, 0, n);
        in.close();
        return o.toByteArray();
    }

    private static String readFile(File f, int max) {
        try {
            return new String(readAll(new FileInputStream(f), max), "UTF-8");
        } catch (Throwable t) {
            return "(khong doc duoc " + f + ": " + t + ")";
        }
    }

    private static String logcat() {
        try {
            java.lang.Process p = Runtime.getRuntime().exec(new String[]{
                "logcat", "-d", "-v", "threadtime", "-b", "main,system,crash", "-t", "600"});
            byte[] b = readAll(p.getInputStream(), 256 * 1024);
            p.destroy();
            return new String(b, "UTF-8");
        } catch (Throwable t) {
            return "(khong doc duoc logcat: " + t + ")";
        }
    }
}
