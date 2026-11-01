package com.namp.vh;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Mod menu (T8): he so sat thuong / phong thu / EXP (x1..x50) cho phe nguoi choi. Xem docs/mod_menu.md.
 *
 * Gan vao MainActivity bang MOT lenh smali (nhom `mod`, INSERT_AFTER): sau HiveActivity.onResume goi
 * ModMenu.onResume(this). Moi lan onResume: doc SharedPreferences, ghi lai cac he so vao libgame.so (ModMem; .bss ve 0 moi
 * lan mo app), them nut noi "MOD" (mot lan). Bam nut -> hop thoai noi (overlay trong cung cua so, khong tao Window moi nen
 * khong lam mat immersive/focus cua game) co 3 thanh keo.
 *
 * Moi phan deu boc try/catch Throwable: mod hong thi game van chay.
 *
 * Mo rong cho T9b ("Tam nhin", int thu 4 @0x72f78c): them mot Slot vao SLOTS (vd. new Slot("view", "Tầm nhìn", "%d%%", 100, 150, 5, 120,
 * "Khởi động lại game để áp dụng")). Chi so cua Slot = chi so int trong vung nho; ModMem da kiem 16 byte rw-p.
 * T8 KHONG ghi int thu 4.
 */
public final class ModMenu {
    private ModMenu() { }

    static final String TAG = "NampMod";
    static final String PREFS = "namp_mod";
    static final int ID_BUTTON = 0x4e4d0001;
    static final int ID_PANEL = 0x4e4d0002;
    static final int GOLD = 0xFFD9B55A;
    static final int TEXT = 0xFFEFEFEF;
    static final int BAD = 0xFFFF6B6B;
    static final int GOOD = 0xFF8FD18F;

    /** Mot thanh keo. Chi so trong SLOTS = chi so int32 trong vung nho native (0 = Mdmg, 1 = Mdef, 2 = Mexp). */
    static final class Slot {
        final String key, label, fmt, note;
        final int min, max, step, def;
        int value;

        Slot(String key, String label, String fmt, int min, int max, int step, int def, String note) {
            this.key = key;
            this.label = label;
            this.fmt = fmt;
            this.min = min;
            this.max = max;
            this.step = step;
            this.def = def;
            this.note = note;
            this.value = def;
        }

        int clamp(int v) {
            if (v < min) return min;
            if (v > max) return max;
            return v;
        }

        String show(int v) {
            return String.format(Locale.US, fmt, v);
        }
    }

    static final Slot[] SLOTS = {
        new Slot("dmg", "Sát thương gây ra", "x%d", 1, 50, 1, 1, null),
        new Slot("def", "Phòng thủ (giảm sát thương nhận)", "x%d", 1, 50, 1, 1, null),
        new Slot("exp", "EXP nhận", "x%d", 1, 50, 1, 1, null),
    };

    private static FloatButton button;
    private static boolean failToasted;
    private static String status = "";
    private static boolean statusOk = true;

    // ------------------------------------------------------------------ vong doi
    /** Goi tu MainActivity.onResume (UI thread). */
    public static void onResume(Activity a) {
        try {
            if (a == null || a.isFinishing()) return;
            loadPrefs(a);
            push(a, false);
            attachButton(a);
        } catch (Throwable t) {
            Log.w(TAG, "onResume loi", t);
        }
    }

    static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    static void loadPrefs(Context c) {
        SharedPreferences sp = prefs(c);
        for (int i = 0; i < SLOTS.length; i++) {
            Slot s = SLOTS[i];
            s.value = s.clamp(sp.getInt(s.key, s.def));
        }
    }

    static void savePrefs(Context c) {
        SharedPreferences.Editor ed = prefs(c).edit();
        for (int i = 0; i < SLOTS.length; i++) ed.putInt(SLOTS[i].key, SLOTS[i].value);
        ed.apply();
    }

    /** Ghi cac he so vao libgame.so. Tra true neu OK. Toast "Mod khong chay duoc" toi da 1 lan/tien trinh. */
    static boolean push(Context c, boolean fromUser) {
        int[] v = new int[SLOTS.length];
        for (int i = 0; i < v.length; i++) v[i] = SLOTS[i].value;
        String err = ModMem.write(v);
        if (err == null) {
            status = "Đã ghi vào game (kênh " + ModMem.lastChannel + ")";
            statusOk = true;
            return true;
        }
        status = "Lỗi: " + err;
        statusOk = false;
        Log.w(TAG, "ghi that bai: " + err);
        if (!failToasted) {
            failToasted = true;
            try {
                Toast.makeText(c, "Mod không chạy được trên máy này", Toast.LENGTH_LONG).show();
            } catch (Throwable ignore) { }
        }
        return false;
    }

    // ------------------------------------------------------------------ tien ich UI
    static int dp(Context c, float v) {
        return (int) (v * c.getResources().getDisplayMetrics().density + 0.5f);
    }

    static GradientDrawable box(int fill, int stroke, int strokeDp, float radiusDp, Context c, boolean oval) {
        GradientDrawable g = new GradientDrawable();
        g.setShape(oval ? GradientDrawable.OVAL : GradientDrawable.RECTANGLE);
        g.setColor(fill);
        if (!oval) g.setCornerRadius(dp(c, radiusDp));
        g.setStroke(dp(c, strokeDp), stroke);
        return g;
    }

    // ------------------------------------------------------------------ nut noi
    static void attachButton(Activity a) {
        View old = a.findViewById(ID_BUTTON);
        if (old != null && old.getParent() != null) return;              // chong them 2 lan
        if (button != null && button.getParent() instanceof ViewGroup) {  // activity cu
            ((ViewGroup) button.getParent()).removeView(button);
        }
        FloatButton b = new FloatButton(a);
        button = b;
        a.addContentView(b, new FrameLayout.LayoutParams(b.size, b.size, Gravity.TOP | Gravity.START));
    }

    /** Nut tron "MOD" o le trai/phai, keo tha duoc; tha ra thi hut ve le gan nhat. Mac dinh: le phai, cao 30 % (trong vung trong cua HUD goc). */
    static final class FloatButton extends TextView implements View.OnLayoutChangeListener {
        final Activity act;
        final int size;
        final int margin;
        final int slop;
        int side;            // 0 = le trai, 1 = le phai
        float yFrac;         // 0..1 theo chieu cao cua cha
        float downX, downY, startTx, startTy;
        boolean dragging;

        FloatButton(Activity a) {
            super(a);
            act = a;
            size = dp(a, 40);
            margin = dp(a, 2);
            slop = ViewConfiguration.get(a).getScaledTouchSlop();
            SharedPreferences sp = prefs(a);
            side = sp.getInt("btn_side", 1) == 0 ? 0 : 1;
            float y = sp.getFloat("btn_y", 0.30f);
            yFrac = y < 0f ? 0f : (y > 1f ? 1f : y);
            setId(ID_BUTTON);
            setText("MOD");
            setGravity(Gravity.CENTER);
            setTextColor(GOLD);
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            setTypeface(Typeface.DEFAULT_BOLD);
            setBackground(box(0xCC1B1B1F, GOLD, 2, 0, a, true));
            setAlpha(0.88f);
            setClickable(true);
            setSingleLine(true);
            setPadding(0, 0, 0, 0);
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            try {
                View p = (View) getParent();
                if (p != null) p.addOnLayoutChangeListener(this);
                place();
            } catch (Throwable t) {
                Log.w(TAG, "attach", t);
            }
        }

        @Override
        protected void onDetachedFromWindow() {
            try {
                View p = (View) getParent();
                if (p != null) p.removeOnLayoutChangeListener(this);
            } catch (Throwable ignore) { }
            if (button == this) button = null;
            super.onDetachedFromWindow();
        }

        @Override
        public void onLayoutChange(View v, int l, int t, int r, int b, int ol, int ot, int or, int ob) {
            try {
                place();
            } catch (Throwable e) {
                Log.w(TAG, "layout", e);
            }
        }

        void place() {
            View p = (View) getParent();
            if (p == null) return;
            int pw = p.getWidth(), ph = p.getHeight();
            if (pw <= 0 || ph <= 0) return;
            int w = getWidth() > 0 ? getWidth() : size;
            int h = getHeight() > 0 ? getHeight() : size;
            float tx = side == 0 ? margin : pw - w - margin;
            float ty = yFrac * (ph - h);
            if (ty < 0f) ty = 0f;
            if (ty > ph - h) ty = ph - h;
            setTranslationX(tx);
            setTranslationY(ty);
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            try {
                View p = (View) getParent();
                if (p == null) return false;
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = e.getRawX();
                        downY = e.getRawY();
                        startTx = getTranslationX();
                        startTy = getTranslationY();
                        dragging = false;
                        getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    case MotionEvent.ACTION_MOVE: {
                        float dx = e.getRawX() - downX, dy = e.getRawY() - downY;
                        if (!dragging && (Math.abs(dx) > slop || Math.abs(dy) > slop)) dragging = true;
                        if (dragging) {
                            float tx = startTx + dx, ty = startTy + dy;
                            float maxX = p.getWidth() - getWidth(), maxY = p.getHeight() - getHeight();
                            setTranslationX(tx < 0f ? 0f : (tx > maxX ? maxX : tx));
                            setTranslationY(ty < 0f ? 0f : (ty > maxY ? maxY : ty));
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                        if (dragging) {
                            snap(p);
                        } else {
                            performClick();
                            openPanel(act);
                        }
                        dragging = false;
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        if (dragging) snap(p);
                        dragging = false;
                        return true;
                    default:
                        return true;
                }
            } catch (Throwable t) {
                Log.w(TAG, "touch", t);
                return true;
            }
        }

        /** Hut ve le gan nhat, nho vi tri. */
        void snap(View p) {
            int pw = p.getWidth(), ph = p.getHeight();
            if (pw <= 0 || ph <= getHeight()) return;
            side = (getTranslationX() + getWidth() / 2f < pw / 2f) ? 0 : 1;
            yFrac = getTranslationY() / (ph - getHeight());
            place();
            prefs(act).edit().putInt("btn_side", side).putFloat("btn_y", yFrac).apply();
        }
    }

    // ------------------------------------------------------------------ hop thoai noi
    static void closePanel(Activity a, View scrim) {
        try {
            if (scrim.getParent() instanceof ViewGroup) ((ViewGroup) scrim.getParent()).removeView(scrim);
        } catch (Throwable t) {
            Log.w(TAG, "closePanel", t);
        }
        if (button != null) button.setVisibility(View.VISIBLE);
    }

    static TextView text(Context c, String s, float sp, int color, boolean bold) {
        TextView t = new TextView(c);
        t.setText(s);
        t.setTextColor(color);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        if (bold) t.setTypeface(Typeface.DEFAULT_BOLD);
        return t;
    }

    static Button flatButton(Context c, String s) {
        Button b = new Button(c);
        b.setText(s);
        b.setAllCaps(false);
        b.setTextColor(GOLD);
        b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        b.setBackground(box(0xFF2B2B31, GOLD, 1, 6, c, false));
        b.setPadding(dp(c, 14), dp(c, 6), dp(c, 14), dp(c, 6));
        return b;
    }

    static void openPanel(final Activity a) {
        try {
            if (a.findViewById(ID_PANEL) != null) return;
            final FrameLayout scrim = new FrameLayout(a);
            scrim.setId(ID_PANEL);
            scrim.setBackgroundColor(0xB0000000);
            scrim.setClickable(true);                                    // nuot cham: game khong nhan khi hop thoai mo
            scrim.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) { closePanel(a, scrim); }
            });

            final ScrollView sv = new ScrollView(a);
            sv.setBackground(box(0xF01B1B1F, 0xFF8A7440, 2, 10, a, false));
            sv.setClickable(true);                                       // bam trong the khong dong
            LinearLayout col = new LinearLayout(a);
            col.setOrientation(LinearLayout.VERTICAL);
            int pad = dp(a, 16);
            col.setPadding(pad, dp(a, 12), pad, dp(a, 12));
            sv.addView(col, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            col.addView(text(a, "MOD · Hệ số sức mạnh", 17, GOLD, true));
            TextView hint = text(a, "Chỉ tác động phe người chơi. x1 = như game gốc.", 12, 0xFFB8B8C0, false);
            col.addView(hint);

            final SeekBar[] bars = new SeekBar[SLOTS.length];
            final TextView[] vals = new TextView[SLOTS.length];
            final TextView statusView = text(a, status, 12, statusOk ? GOOD : BAD, false);
            for (int i = 0; i < SLOTS.length; i++) {
                final Slot s = SLOTS[i];
                LinearLayout row = new LinearLayout(a);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, dp(a, 10), 0, 0);
                TextView name = text(a, s.label, 15, TEXT, false);
                row.addView(name, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                final TextView val = text(a, s.show(s.value), 15, GOLD, true);
                row.addView(val);
                col.addView(row);
                vals[i] = val;

                SeekBar sb = new SeekBar(a);
                sb.setMax((s.max - s.min) / s.step);
                sb.setProgress((s.value - s.min) / s.step);
                ColorStateList gold = ColorStateList.valueOf(GOLD);
                sb.setProgressTintList(gold);
                sb.setThumbTintList(gold);
                sb.setProgressBackgroundTintList(ColorStateList.valueOf(0xFF55555C));
                sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                        if (!fromUser) return;
                        try {
                            s.value = s.clamp(s.min + progress * s.step);
                            val.setText(s.show(s.value));
                            savePrefs(a);
                            push(a, true);
                            statusView.setText(status);
                            statusView.setTextColor(statusOk ? GOOD : BAD);
                        } catch (Throwable t) {
                            Log.w(TAG, "seekbar", t);
                        }
                    }

                    public void onStartTrackingTouch(SeekBar bar) { }

                    public void onStopTrackingTouch(SeekBar bar) { }
                });
                col.addView(sb, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(a, 36)));
                bars[i] = sb;
                if (s.note != null) col.addView(text(a, s.note, 11, 0xFFB8B8C0, false));
            }
            LinearLayout.LayoutParams sl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            sl.topMargin = dp(a, 8);
            col.addView(statusView, sl);

            LinearLayout btns = new LinearLayout(a);
            btns.setOrientation(LinearLayout.HORIZONTAL);
            btns.setGravity(Gravity.END);
            Button reset = flatButton(a, "Đặt lại x1");
            reset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        for (int i = 0; i < SLOTS.length; i++) {
                            Slot s = SLOTS[i];
                            s.value = s.def;
                            bars[i].setProgress((s.def - s.min) / s.step);
                            vals[i].setText(s.show(s.value));
                        }
                        savePrefs(a);
                        push(a, true);
                        statusView.setText(status);
                        statusView.setTextColor(statusOk ? GOOD : BAD);
                    } catch (Throwable t) {
                        Log.w(TAG, "reset", t);
                    }
                }
            });
            Button close = flatButton(a, "Đóng");
            close.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) { closePanel(a, scrim); }
            });
            btns.addView(reset);
            LinearLayout.LayoutParams cl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cl.leftMargin = dp(a, 10);
            btns.addView(close, cl);
            LinearLayout.LayoutParams bl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            bl.topMargin = dp(a, 12);
            col.addView(btns, bl);

            int sw = a.getResources().getDisplayMetrics().widthPixels;
            int w = Math.min(dp(a, 420), (int) (sw * 0.92f));
            scrim.addView(sv, new FrameLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            if (button != null) button.setVisibility(View.INVISIBLE);
            a.addContentView(scrim, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } catch (Throwable t) {
            Log.w(TAG, "openPanel loi", t);
            if (button != null) button.setVisibility(View.VISIBLE);
        }
    }
}
