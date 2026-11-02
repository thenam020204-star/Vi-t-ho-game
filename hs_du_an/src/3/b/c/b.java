/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package b.c;

import b.c.c;
import javax.microedition.lcdui.Image;

public final class b {
    public c a = null;
    public String b = null;
    public Image c = null;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 0;
    public int h = 0;
    public String i = "";
    public long j = 0L;
    public byte[] k = null;
    public int l = 0;

    public b(c c2) {
        this.a = c2;
    }

    public final byte a(byte by) {
        if (this.k == null || this.k.length < 3) {
            return 0;
        }
        for (int i2 = 0; i2 < this.k.length; i2 += 3) {
            if (this.k[i2] != by) continue;
            return this.k[i2 + 2];
        }
        return 0;
    }

    public final String toString() {
        String string = ((b)((Object)string)).b + ", plname: " + ((b)((Object)string)).i + ", trans: " + ((b)((Object)string)).g;
        return string;
    }
}

