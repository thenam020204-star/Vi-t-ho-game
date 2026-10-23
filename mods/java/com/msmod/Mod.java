package com.msmod;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.Switch;
import android.widget.TextView;

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

    /** Diem vao duy nhat; an toan khi goi nhieu lan / khi thieu libmsmod.so (khi do khong them menu). */
    public static void onCreate(final Activity activity) {
        try {
            System.loadLibrary("msmod");
            nativeOk = true;
        } catch (Throwable t) {
            android.util.Log.e(TAG, "khong nap duoc libmsmod.so", t);
            return;
        }
        try { setGodMode(god); } catch (Throwable ignored) { }
        activity.runOnUiThread(new Runnable() {
            @Override public void run() {
                try {
                    new Ui(activity).attach();
                } catch (Throwable t) {
                    android.util.Log.e(TAG, "loi tao menu", t);
                }
            }
        });
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
        private TextView status;

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
            if (!ready) s = "Đang cài hook...";
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
