package com.namp.vh.diag;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * LAUNCHER trung gian cua ban chan doan. Khong co loi cu -> mo thang MainActivity cua game.
 * Co loi -> hien bao cao + nut Luu vao Download / Chia se / Sao chep / Vao game.
 */
public final class LaunchActivity extends Activity {
    static final String MAIN = "com.com2us.inotia4.normal.freefull.google.global.android.common.MainActivity";
    private String report;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        CrashLog.install(this);
        try {
            report = CrashLog.pending(this);
        } catch (Throwable t) {
            report = null;
        }
        if (report == null) {
            go();
            return;
        }
        ui();
    }

    private void go() {
        CrashLog.markShown(this);
        Intent i = new Intent();
        i.setClassName(getPackageName(), MAIN);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

    private Button btn(LinearLayout row, String text, View.OnClickListener l) {
        Button b = new Button(this);
        b.setText(text);
        b.setAllCaps(false);
        b.setOnClickListener(l);
        row.addView(b, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        return b;
    }

    private void ui() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.BLACK);
        root.setPadding(16, 16, 16, 16);
        TextView title = new TextView(this);
        title.setTextColor(Color.YELLOW);
        title.setTextSize(16);
        title.setText("Lần chạy trước game bị thoát bất thường. Hãy bấm \"Lưu vào Download\" hoặc \"Chia sẻ\" "
            + "rồi gửi file/nội dung này để sửa lỗi.");
        root.addView(title);
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        btn(row, "Lưu vào Download", new View.OnClickListener() { public void onClick(View v) { save(); } });
        btn(row, "Chia sẻ", new View.OnClickListener() { public void onClick(View v) { share(); } });
        btn(row, "Sao chép", new View.OnClickListener() { public void onClick(View v) { copy(); } });
        btn(row, "Vào game", new View.OnClickListener() { public void onClick(View v) { go(); } });
        root.addView(row);
        ScrollView sv = new ScrollView(this);
        TextView tv = new TextView(this);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(10);
        tv.setTypeface(Typeface.MONOSPACE);
        tv.setTextIsSelectable(true);
        tv.setText(report);
        sv.addView(tv);
        root.addView(sv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));
        setContentView(root);
    }

    private void save() {
        String name = "inotia4_loi_" + System.currentTimeMillis() + ".txt";
        try {
            byte[] data = report.getBytes("UTF-8");
            if (Build.VERSION.SDK_INT >= 29) {
                ContentValues cv = new ContentValues();
                cv.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
                cv.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
                cv.put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/");
                Uri u = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
                OutputStream o = getContentResolver().openOutputStream(u);
                o.write(data);
                o.close();
                toast("Đã lưu Download/" + name);
            } else {
                File f = new File(getExternalFilesDir(null), name);
                FileOutputStream o = new FileOutputStream(f);
                o.write(data);
                o.close();
                toast("Đã lưu " + f);
            }
        } catch (Throwable t) {
            toast("Lưu lỗi: " + t);
        }
    }

    private String capped() {
        return report.length() > 90000 ? report.substring(0, 90000) + "\n...(cắt bớt)" : report;
    }

    private void share() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Inotia 4 Việt hoá - báo lỗi");
            i.putExtra(Intent.EXTRA_TEXT, capped());
            startActivity(Intent.createChooser(i, "Gửi báo lỗi"));
        } catch (Throwable t) {
            toast("Chia sẻ lỗi: " + t);
        }
    }

    private void copy() {
        try {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText("inotia4", capped()));
            toast("Đã sao chép");
        } catch (Throwable t) {
            toast("Sao chép lỗi: " + t);
        }
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
