package com.msmod;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu mod Magic Survival: nut noi "MOD" keo duoc + bang Bat tu / Len cap.
 * Goi tu UnityPlayerActivity.onCreate: Mod.onCreate(this). Khong can quyen overlay (addContentView).
 * Native: libmsmod.so (mods/native/msmod.c), ten ham JNI Java_com_msmod_Mod_*.
 */
public final class Mod {
    private static final String TAG = "msmod";
    private static boolean nativeOk;
    private static boolean god; // nho trang thai de ap lai khi Activity duoc tao lai

    // ---- JNI (khop msmod.c) ----
    static native void setGodMode(boolean on);
    static native void requestLevelUp(int n);
    static native boolean isInBattle();
    static native int getPendingLevelUps();
    static native boolean isReady();

    private Mod() {}

    /** Diem vao duy nhat; an toan khi goi nhieu lan. Thieu libmsmod.so (ban chan doan "vá-only") van hien menu chi co "Luu log". */
    public static void onCreate(final Activity activity) {
        android.util.Log.i(TAG, "Mod.onCreate (java) pid=" + android.os.Process.myPid());
        try {
            System.loadLibrary("msmod");
            nativeOk = true;
            android.util.Log.i(TAG, "System.loadLibrary(msmod) OK");
        } catch (Throwable t) {
            nativeOk = false;
            android.util.Log.e(TAG, "khong nap duoc libmsmod.so (menu chi con Luu log)", t);
        }
        if (nativeOk) { try { setGodMode(god); } catch (Throwable ignored) { } }
        activity.runOnUiThread(new Runnable() {
            @Override public void run() {
                try {
                    new Ui(activity).attach();
                    android.util.Log.i(TAG, "menu da gan");
                } catch (Throwable t) {
                    android.util.Log.e(TAG, "loi tao menu", t);
                }
            }
        });
    }

    // ---- luu log (T17): logcat cua chinh app, khong can quyen ----
    static final int MAX_LINES = 2000;

    /** Chay logcat -d cho pid cua minh; doc stream tren luong phu. Tra ve toi da MAX_LINES dong cuoi. */
    static List<String> collectLog() {
        final int pid = android.os.Process.myPid();
        String[][] tries = {
            {"logcat", "-d", "-v", "time", "-b", "main,crash", "--pid=" + pid},
            {"logcat", "-d", "-v", "time", "--pid=" + pid},
            {"logcat", "-d", "-v", "time"},
        };
        List<String> best = new ArrayList<String>();
        for (String[] cmd : tries) {
            final List<String> lines = new ArrayList<String>();
            try {
                final Process p = Runtime.getRuntime().exec(cmd);
                Thread rd = new Thread(new Runnable() {
                    @Override public void run() {
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
                            String l;
                            while ((l = br.readLine()) != null) {
                                synchronized (lines) { lines.add(l); if (lines.size() > MAX_LINES * 2) lines.subList(0, MAX_LINES).clear(); }
                            }
                        } catch (Throwable ignored) { }
                    }
                });
                rd.start();
                // doc loi (stderr) de khong bi nghen pipe
                Thread er = new Thread(new Runnable() {
                    @Override public void run() {
                        try { BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream())); while (br.readLine() != null) { } } catch (Throwable ignored) { }
                    }
                });
                er.start();
                rd.join(15000);
                try { p.destroy(); } catch (Throwable ignored) { }
            } catch (Throwable t) {
                synchronized (lines) { lines.add("[msmod] exec logcat loi: " + t); }
            }
            synchronized (lines) {
                if (lines.size() > MAX_LINES) lines.subList(0, lines.size() - MAX_LINES).clear();
                if (lines.size() > best.size()) best = new ArrayList<String>(lines);
            }
            if (best.size() > 3) break;
        }
        return best;
    }

    /** Ghi msmod_log.txt vao getExternalFilesDir(null) (fallback getFilesDir). Tra ve file, hoac null. */
    static File saveLog(Activity a, List<String> lines) {
        File dir = null;
        try { dir = a.getExternalFilesDir(null); } catch (Throwable ignored) { }
        if (dir == null) dir = a.getFilesDir();
        try {
            dir.mkdirs();
            File f = new File(dir, "msmod_log.txt");
            FileOutputStream o = new FileOutputStream(f);
            StringBuilder sb = new StringBuilder();
            sb.append("# msmod log pid=").append(android.os.Process.myPid()).append(" android=").append(android.os.Build.VERSION.RELEASE)
              .append(" sdk=").append(android.os.Build.VERSION.SDK_INT).append(" ").append(android.os.Build.MANUFACTURER).append(" ")
              .append(android.os.Build.MODEL).append(" abi=").append(android.os.Build.SUPPORTED_ABIS[0]).append(" nativeOk=").append(nativeOk).append("\n");
            for (String l : lines) sb.append(l).append('\n');
            o.write(sb.toString().getBytes("UTF-8"));
            o.close();
            return f;
        } catch (Throwable t) {
            android.util.Log.e(TAG, "ghi log loi", t);
            return null;
        }
    }

    // ------------------------------------------------------------------------------------------------
    private static final class Ui {
        private static final int POLL_MS = 500;
        private final Activity act;
        private final Handler handler = new Handler(Looper.getMainLooper());
        private final float dp;
        private FrameLayout root;
        private TextView fab;
        private LinearLayout panel;
        private Switch swGod;
        private Button btn1, btn5;
        private TextView status, logView;
        private List<String> lastLog = new ArrayList<String>();
        private File lastFile;

        Ui(Activity a) {
            act = a;
            dp = a.getResources().getDisplayMetrics().density;
        }

        private int px(float v) { return (int) (v * dp + 0.5f); }

        private GradientDrawable bg(int color, float radiusDp, int strokeColor) {
            GradientDrawable d = new GradientDrawable();
            d.setColor(color);
            d.setCornerRadius(px(radiusDp));
            if (strokeColor != 0) d.setStroke(px(1), strokeColor);
            return d;
        }

        void attach() {
            root = new FrameLayout(act);
            root.setClipChildren(false);
            // Container chi bao quanh cac view con => chi chan cham o vung nut/bang; phan con lai xuong Unity.
            fab = new TextView(act);
            fab.setText("MOD");
            fab.setTextColor(Color.WHITE);
            fab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            fab.setGravity(Gravity.CENTER);
            fab.setBackground(bg(0xB0303060, 22, 0xFFFFFFFF));
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(px(44), px(44), Gravity.TOP | Gravity.START);
            flp.leftMargin = px(8);
            flp.topMargin = px(80);
            fab.setLayoutParams(flp);
            fab.setOnTouchListener(new DragListener());

            panel = buildPanel();
            panel.setVisibility(View.GONE);
            FrameLayout.LayoutParams plp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            root.addView(panel, plp);
            root.addView(fab);

            // root phu toan man hinh nhung khong nhan cham (trong suot, khong clickable) => Unity van nhan cham o noi khac.
            act.addContentView(root, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        private LinearLayout buildPanel() {
            LinearLayout p = new LinearLayout(act);
            p.setOrientation(LinearLayout.VERTICAL);
            p.setPadding(px(16), px(12), px(16), px(12));
            p.setBackground(bg(0xEE181830, 12, 0xFF8080C0));
            p.setClickable(true); // chan cham xuyen xuong game khi bang dang mo

            TextView title = new TextView(act);
            title.setText("Menu MOD");
            title.setTextColor(Color.WHITE);
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            title.setGravity(Gravity.CENTER);
            p.addView(title, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            swGod = new Switch(act);
            swGod.setText("Bất tử");
            swGod.setTextColor(Color.WHITE);
            swGod.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            swGod.setChecked(god);
            swGod.setMinWidth(px(200));
            swGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton b, boolean on) {
                    god = on;
                    try { setGodMode(on); } catch (Throwable t) { android.util.Log.e(TAG, "setGodMode", t); }
                }
            });
            LinearLayout.LayoutParams sl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, px(44));
            sl.topMargin = px(8);
            p.addView(swGod, sl);

            btn1 = actionButton("Lên 1 cấp", 1);
            btn5 = actionButton("Lên 5 cấp", 5);
            p.addView(btn1, rowLp());
            p.addView(btn5, rowLp());

            status = new TextView(act);
            status.setTextColor(0xFFC0C0E0);
            status.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            status.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams stl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            stl.topMargin = px(6);
            p.addView(status, stl);

            Button save = new Button(act);
            save.setText("Lưu log");
            save.setAllCaps(false);
            save.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) { doSaveLog(); }
            });
            p.addView(save, rowLp());
            Button share = new Button(act);
            share.setText("Gửi log (chia sẻ)");
            share.setAllCaps(false);
            share.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) { doShareLog(); }
            });
            p.addView(share, rowLp());
            logView = new TextView(act);
            logView.setTextColor(0xFFA0FFA0);
            logView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            logView.setTypeface(android.graphics.Typeface.MONOSPACE);
            logView.setText("(chưa lưu log)");
            ScrollView sv = new ScrollView(act);
            sv.addView(logView);
            p.addView(sv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, px(150)));

            Button close = new Button(act);
            close.setText("Đóng");
            close.setAllCaps(false);
            close.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) { showPanel(false); }
            });
            p.addView(close, rowLp());
            return p;
        }

        private LinearLayout.LayoutParams rowLp() {
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, px(46));
            l.topMargin = px(6);
            return l;
        }

        private Button actionButton(String text, final int n) {
            Button b = new Button(act);
            b.setText(text);
            b.setAllCaps(false);
            b.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    try {
                        if (isInBattle()) requestLevelUp(n);
                    } catch (Throwable t) { android.util.Log.e(TAG, "requestLevelUp", t); }
                    refresh();
                }
            });
            return b;
        }

        private void doSaveLog() {
            logView.setText("Đang lấy log...");
            new Thread(new Runnable() {
                @Override public void run() {
                    final List<String> lines = collectLog();
                    final File f = saveLog(act, lines);
                    act.runOnUiThread(new Runnable() {
                        @Override public void run() {
                            lastLog = lines; lastFile = f;
                            StringBuilder sb = new StringBuilder();
                            sb.append(f != null ? "Đã lưu: " + f.getAbsolutePath() : "KHÔNG ghi được file").append("\n")
                              .append(lines.size()).append(" dòng. 30 dòng cuối:\n");
                            for (int i = Math.max(0, lines.size() - 30); i < lines.size(); i++) sb.append(lines.get(i)).append('\n');
                            logView.setText(sb.toString());
                        }
                    });
                }
            }).start();
        }

        private void doShareLog() {
            if (lastLog.isEmpty()) { Toast.makeText(act, "Bấm Lưu log trước", Toast.LENGTH_SHORT).show(); return; }
            StringBuilder sb = new StringBuilder();
            for (String l : lastLog) sb.append(l).append('\n');
            String t = sb.toString();
            if (t.length() > 200000) t = t.substring(t.length() - 200000);
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "msmod log");
                i.putExtra(Intent.EXTRA_TEXT, t);
                act.startActivity(Intent.createChooser(i, "Gửi log"));
            } catch (Throwable e) { android.util.Log.e(TAG, "share", e); }
        }

        private void showPanel(boolean show) {
            panel.setVisibility(show ? View.VISIBLE : View.GONE);
            handler.removeCallbacks(poll);
            if (show) {
                refresh();
                handler.postDelayed(poll, POLL_MS);
            }
        }

        private final Runnable poll = new Runnable() {
            @Override public void run() {
                if (act.isFinishing() || panel.getVisibility() != View.VISIBLE) return;
                refresh();
                handler.postDelayed(this, POLL_MS);
            }
        };

        /** Cap nhat do mo cua nut theo trang thai tran dau + dong trang thai. */
        private void refresh() {
            boolean battle = false;
            boolean ready = false;
            int pending = 0;
            try {
                ready = isReady();
                battle = ready && isInBattle();
                pending = getPendingLevelUps();
            } catch (Throwable ignored) { }
            float a = battle ? 1f : 0.4f;
            btn1.setAlpha(a);
            btn5.setAlpha(a);
            btn1.setEnabled(battle);
            btn5.setEnabled(battle);
            String s;
            if (!nativeOk) s = "Bản chẩn đoán: không có libmsmod";
            else if (!ready) s = "Đang cài hook...";
            else if (!battle) s = "Chỉ dùng được khi đang trong trận";
            else if (pending > 0) s = "Đang chờ lên cấp: " + pending;
            else s = "Sẵn sàng";
            status.setText(s);
        }

        /** Keo nut; cham nhe (khong keo) thi mo/dong bang. */
        private final class DragListener implements View.OnTouchListener {
            private float downX, downY, startLeft, startTop;
            private boolean moved;
            private final int slop = ViewConfiguration.get(act).getScaledTouchSlop();

            @Override public boolean onTouch(View v, MotionEvent e) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = e.getRawX(); downY = e.getRawY();
                        startLeft = lp.leftMargin; startTop = lp.topMargin;
                        moved = false;
                        return true;
                    case MotionEvent.ACTION_MOVE: {
                        float dx = e.getRawX() - downX, dy = e.getRawY() - downY;
                        if (!moved && (Math.abs(dx) > slop || Math.abs(dy) > slop)) moved = true;
                        if (moved) {
                            int maxX = Math.max(0, root.getWidth() - v.getWidth());
                            int maxY = Math.max(0, root.getHeight() - v.getHeight());
                            lp.leftMargin = (int) Math.min(maxX, Math.max(0, startLeft + dx));
                            lp.topMargin = (int) Math.min(maxY, Math.max(0, startTop + dy));
                            v.setLayoutParams(lp);
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                        if (!moved) showPanel(panel.getVisibility() != View.VISIBLE);
                        return true;
                    default:
                        return true;
                }
            }
        }
    }
}
