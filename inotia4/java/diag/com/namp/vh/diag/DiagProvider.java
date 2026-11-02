package com.namp.vh.diag;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/** Chi de duoc khoi tao som nhat (initOrder cao) va cai handler ghi loi. Khong cung cap du lieu gi. */
public final class DiagProvider extends ContentProvider {
    public boolean onCreate() {
        try {
            CrashLog.install(getContext());
        } catch (Throwable ignore) {
        }
        return true;
    }

    public Cursor query(Uri u, String[] p, String s, String[] a, String o) { return null; }

    public String getType(Uri u) { return null; }

    public Uri insert(Uri u, ContentValues v) { return null; }

    public int delete(Uri u, String s, String[] a) { return 0; }

    public int update(Uri u, ContentValues v, String s, String[] a) { return 0; }
}
