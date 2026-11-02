/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package e.a;

import javax.microedition.lcdui.Graphics;

public final class a {
    public static void a(Graphics graphics, int n2, String string, int n3, int n4, int n5) {
        graphics.setColor(0);
        graphics.drawString(string, n3 - 1, n4, n5);
        graphics.drawString(string, n3 + 1, n4, n5);
        graphics.drawString(string, n3, n4 + 1, n5);
        graphics.drawString(string, n3, n4 - 1, n5);
        graphics.setColor(0xFFFFFF);
        graphics.drawString(string, n3, n4, n5);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = n2 >> 16 & 0xFF;
        int n9 = n2 >> 8 & 0xFF;
        int n10 = n2 & 0xFF;
        int n11 = n3 >> 16 & 0xFF;
        int n12 = n3 >> 8 & 0xFF;
        int n13 = n3 & 0xFF;
        int n14 = (n11 - n8) / n7;
        if (n14 == 0) {
            int n15 = n14 = n11 > n8 ? 1 : -1;
        }
        if ((n11 = (n12 - n9) / n7) == 0) {
            int n16 = n11 = n12 > n9 ? 1 : -1;
        }
        if ((n12 = (n13 - n10) / n7) == 0) {
            n12 = n13 > n10 ? 1 : -1;
        }
        n13 = 0;
        int n17 = 0;
        int n18 = 0;
        for (int i2 = 0; i2 <= n7; ++i2) {
            graphics.setColor(n2);
            graphics.drawLine(n4, n5 + i2, n4 + n6, n5 + i2);
            if (n2 == n3) continue;
            n13 = n8 + i2 * n14;
            if (n13 > 255) {
                n13 = 255;
            } else if (n13 < 0) {
                n13 = 0;
            }
            n17 = n9 + i2 * n11;
            if (n17 > 255) {
                n17 = 255;
            } else if (n17 < 0) {
                n17 = 0;
            }
            n18 = n10 + i2 * n12;
            if (n18 > 255) {
                n18 = 255;
            } else if (n18 < 0) {
                n18 = 0;
            }
            n2 = n13 << 16 | n17 << 8 | n18;
        }
    }
}

