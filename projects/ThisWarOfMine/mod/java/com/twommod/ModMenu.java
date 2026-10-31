package com.twommod;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

/** Menu mod chơi đơn: nút nổi "MOD" + bảng tuỳ chọn. Gọi ModMenu.attach(activity) sau setContentView. */
public final class ModMenu {
    static final int OPT_GOD = 0, OPT_INFBAG = 1, OPT_STACK99 = 2, OPT_LOOTMUL = 3;
    static native void nSet(int id, int v);
    static native int nAvail(int id);

    private static boolean loaded;
    private static SharedPreferences prefs;

    public static void attach(final Activity a) {
        try {
            if (!loaded) { System.loadLibrary("twommod"); loaded = true; }
        } catch (Throwable t) {
            Log.w("twommod", "không nạp được libtwommod.so", t);
            return;
        }
        prefs = a.getSharedPreferences("twommod", 0);
        nSet(OPT_GOD, prefs.getBoolean("god", false) ? 1 : 0);
        nSet(OPT_INFBAG, prefs.getBoolean("bag", false) ? 1 : 0);
        nSet(OPT_STACK99, prefs.getBoolean("stack", false) ? 1 : 0);
        nSet(OPT_LOOTMUL, prefs.getInt("mul", 1));
        a.addContentView(build(a), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private static int dp(Activity a, float v) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, a.getResources().getDisplayMetrics());
    }

    private static GradientDrawable bg(int color, float r) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color); g.setCornerRadius(r); g.setStroke(2, 0xFF8A7A5A);
        return g;
    }

    private static View build(final Activity a) {
        final FrameLayout root = new FrameLayout(a);

        final LinearLayout panel = new LinearLayout(a);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setBackground(bg(0xE6201C18, dp(a, 8)));
        int p = dp(a, 12);
        panel.setPadding(p, p, p, p);
        panel.setVisibility(View.GONE);
        panel.setClickable(true);

        TextView title = new TextView(a);
        title.setText("MOD — chơi đơn");
        title.setTextColor(0xFFE8D9B0);
        title.setTextSize(16);
        panel.addView(title);

        panel.addView(sw(a, "Bất tử khi lục lọi (máu không về 0)", "god", OPT_GOD));
        panel.addView(sw(a, "Ba lô không giới hạn", "bag", OPT_INFBAG));
        panel.addView(sw(a, "Mỗi ô chứa 99", "stack", OPT_STACK99));

        final TextView mulLabel = new TextView(a);
        mulLabel.setTextColor(Color.WHITE);
        mulLabel.setTextSize(14);
        final SeekBar mul = new SeekBar(a);
        mul.setMax(5);
        int m = prefs.getInt("mul", 1);
        mul.setProgress(m - 1);
        mulLabel.setText("Hệ số nhặt đồ: x" + m);
        boolean lootOk = nAvail(OPT_LOOTMUL) != 0;
        mul.setEnabled(lootOk);
        if (!lootOk) mulLabel.setText("Hệ số nhặt đồ: không khả dụng");
        mul.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar s, int v, boolean u) {
                int k = v + 1;
                mulLabel.setText("Hệ số nhặt đồ: x" + k);
                nSet(OPT_LOOTMUL, k);
                prefs.edit().putInt("mul", k).apply();
            }
            public void onStartTrackingTouch(SeekBar s) {}
            public void onStopTrackingTouch(SeekBar s) {}
        });
        panel.addView(mulLabel);
        panel.addView(mul, new LinearLayout.LayoutParams(dp(a, 260), ViewGroup.LayoutParams.WRAP_CONTENT));

        final TextView btn = new TextView(a);
        btn.setText("MOD");
        btn.setTextColor(0xFFE8D9B0);
        btn.setTextSize(13);
        btn.setGravity(Gravity.CENTER);
        btn.setBackground(bg(0xCC201C18, dp(a, 20)));
        FrameLayout.LayoutParams blp = new FrameLayout.LayoutParams(dp(a, 48), dp(a, 40));
        blp.leftMargin = dp(a, 8); blp.topMargin = dp(a, 60);
        root.addView(btn, blp);

        FrameLayout.LayoutParams plp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plp.leftMargin = dp(a, 64); plp.topMargin = dp(a, 40);
        root.addView(panel, plp);

        // chạm để mở/đóng, kéo để di chuyển nút
        btn.setOnTouchListener(new View.OnTouchListener() {
            float dx, dy, sx, sy; boolean moved;
            public boolean onTouch(View v, MotionEvent e) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dx = e.getRawX() - lp.leftMargin; dy = e.getRawY() - lp.topMargin;
                        sx = e.getRawX(); sy = e.getRawY(); moved = false; return true;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(e.getRawX() - sx) + Math.abs(e.getRawY() - sy) > 12) moved = true;
                        if (moved) {
                            lp.leftMargin = (int) (e.getRawX() - dx); lp.topMargin = (int) (e.getRawY() - dy);
                            v.setLayoutParams(lp);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (!moved) {
                            boolean show = panel.getVisibility() != View.VISIBLE;
                            if (show) refresh(panel);
                            FrameLayout.LayoutParams pl = (FrameLayout.LayoutParams) panel.getLayoutParams();
                            pl.leftMargin = lp.leftMargin + v.getWidth() + dp(a, 8); pl.topMargin = Math.max(0, lp.topMargin - dp(a, 20));
                            panel.setLayoutParams(pl);
                            panel.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                        return true;
                }
                return false;
            }
        });
        return root;
    }

    /** cập nhật trạng thái khả dụng (bảng vật phẩm chỉ kiểm được khi đã vào game) */
    private static void refresh(LinearLayout panel) {
        for (int i = 0; i < panel.getChildCount(); i++) {
            View v = panel.getChildAt(i);
            if (v instanceof Switch && v.getTag() instanceof Integer) {
                boolean ok = nAvail((Integer) v.getTag()) != 0;
                v.setEnabled(ok);
                v.setAlpha(ok ? 1f : 0.4f);
            }
        }
    }

    private static Switch sw(Activity a, String text, final String key, final int id) {
        Switch s = new Switch(a);
        s.setText(text);
        s.setTextColor(Color.WHITE);
        s.setTextSize(14);
        s.setTag(id);
        s.setChecked(prefs.getBoolean(key, false));
        int p = dp(a, 6);
        s.setPadding(0, p, 0, p);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton b, boolean on) {
                nSet(id, on ? 1 : 0);
                prefs.edit().putBoolean(key, on).apply();
            }
        });
        return s;
    }
}
