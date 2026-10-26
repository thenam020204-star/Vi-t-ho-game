/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package b.e;

import javax.microedition.lcdui.Graphics;

public final class c {
    public static void a(Graphics graphics, int n2, String string, int n3, int n4, int n5) {
        if (string == null) {
            return;
        }
        graphics.setColor(0);
        graphics.drawString(string, n3 - 1, n4, n5);
        graphics.drawString(string, n3 + 1, n4, n5);
        graphics.drawString(string, n3, n4 + 1, n5);
        graphics.drawString(string, n3, n4 - 1, n5);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(string, n3, n4, n5);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n7 == 0) {
            return;
        }
        int n8 = n2 >> 16 & 0xFF;
        int n9 = n2 >> 8 & 0xFF;
        int n10 = n2 & 0xFF;
        int n11 = n3 >> 16 & 0xFF;
        int n12 = n3 >> 8 & 0xFF;
        int n13 = n3 & 0xFF;
        n11 = (n11 - n8) * 100 / n7;
        n12 = (n12 - n9) * 100 / n7;
        n13 = (n13 - n10) * 100 / n7;
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        for (int i2 = 0; i2 <= n7; ++i2) {
            graphics.setColor(n2);
            graphics.drawLine(n4, n5 + i2, n4 + n6, n5 + i2);
            if (n2 == n3) continue;
            n14 = n8 + i2 * n11 / 100;
            if (n14 > 255) {
                n14 = 255;
            } else if (n14 < 0) {
                n14 = 0;
            }
            n15 = n9 + i2 * n12 / 100;
            if (n15 > 255) {
                n15 = 255;
            } else if (n15 < 0) {
                n15 = 0;
            }
            n16 = n10 + i2 * n13 / 100;
            if (n16 > 255) {
                n16 = 255;
            } else if (n16 < 0) {
                n16 = 0;
            }
            n2 = n14 << 16 | n15 << 8 | n16;
        }
    }
}

