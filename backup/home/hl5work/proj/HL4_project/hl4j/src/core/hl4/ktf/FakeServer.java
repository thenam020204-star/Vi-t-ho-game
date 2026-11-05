package hl4.ktf;

/**
 * Offline replacement of the game's network server (shop / network warehouse).
 * Receives the bytes the game writes to its socket and produces the reply bytes.
 */
public class FakeServer {
    private byte[] in = new byte[0];
    private byte[] out;

    public void reset() {
        in = new byte[0];
        out = null;
        dropped = false;
    }

    public static boolean ECHO = false;

    public void receive(byte[] d) {
        byte[] n = new byte[in.length + d.length];
        System.arraycopy(in, 0, n, 0, in.length);
        System.arraycopy(d, 0, n, in.length, d.length);
        in = n;
        if (ECHO) {
            out = d;
            return;
        }
        while (in.length >= 6) {
            int len = (in[0] & 0xFF) | ((in[1] & 0xFF) << 8) | ((in[2] & 0xFF) << 16) | ((in[3] & 0xFF) << 24);
            if (len < 6 || len > 0x10000) { // garbage: drop
                in = new byte[0];
                return;
            }
            if (in.length < len) return;
            byte[] pl = new byte[len - 6];
            System.arraycopy(in, 6, pl, 0, pl.length);
            int major = in[4] & 0xFF, minor = in[5] & 0xFF;
            byte[] rest = new byte[in.length - len];
            System.arraycopy(in, len, rest, 0, rest.length);
            in = rest;
            handle(major, minor, pl);
        }
    }

    /** Text encoder of the Vietnamese build (null: plain ASCII). */
    public hl4.core.TextEngine text;

    protected byte[] str(String v) {
        byte[] b;
        if (text != null) b = text.encode(v);
        else {
            b = new byte[v.length()];
            for (int i = 0; i < b.length; i++) b[i] = (byte) (v.charAt(i) < 0x80 ? v.charAt(i) : '?');
        }
        byte[] z = new byte[b.length + 1];
        System.arraycopy(b, 0, z, 0, b.length);
        return z;
    }

    protected static byte[] cat(byte[] a, byte[] b) {
        byte[] n = new byte[a.length + b.length];
        System.arraycopy(a, 0, n, 0, a.length);
        System.arraycopy(b, 0, n, a.length, b.length);
        return n;
    }

    /** Set when the server drops the connection (the game then reports a lost connection). */
    public boolean dropped;

    static String prop(String k) {
        try {
            String v = System.getProperty(k);
            return v == null ? "" : v;
        } catch (Throwable t) {
            return "";
        }
    }

    /** Offline cash shop: {category, index, count} of the items for sale (all free). */
    static final int[][] CATALOG = {
        {8, 15, 5},   // Elixir
        {8, 16, 3},   // Sach hoi sinh
        {8, 24, 5},   // Da tinh luyen cao cap
        {8, 21, 3},   // Bua thanh cong
        {8, 22, 3},   // Bua hoan nguyen
        {8, 23, 1},   // Them o kham
        {8, 19, 1},   // Tui hanh trang
        {8, 20, 1},   // Tu nhat do
        {8, 17, 1},   // Dat lai ky nang
        {8, 18, 1},   // Dat lai chi so
        {8, 29, 1},   // Bo dat lai
        {8, 25, 1},   // Ruong bau nho
        {8, 26, 1},   // Ruong bau
        {8, 27, 1},   // Ruong bau lon
        {8, 28, 1},   // Ruong bau co
    };
    private final java.util.Random rnd = new java.util.Random();

    /** One request packet from the game. */
    protected void handle(int major, int minor, byte[] pl) {
        System.out.println("[FakeServer] req " + major + "/" + minor + " " + hexdump(pl));
        if (major == 0) return; // keep alive
        if (major == 1) {
            switch (minor) {
                case 1: reply(1, 1, new byte[]{0}); return;                       // hello
                case 0x3d: reply(1, 0x3d, new byte[]{0}); return;                 // login
                case 0x3e: reply(1, 0x3e, cat(new byte[]{0, 0}, str("Cửa hàng offline: mọi vật phẩm đều miễn phí."))); return;
                case 0x50: reply(1, 0x50, cat(new byte[]{1, 0}, str("Đã lưu lựa chọn."))); return; // SMS consent
            }
        }
        if (major == 5) {
            switch (minor) {
                case 63: { // shop catalog: flags, u16 count, 37-byte records
                    int n = CATALOG.length;
                    byte[] p = new byte[4 + 37 * n];
                    p[2] = (byte) n;
                    p[3] = (byte) (n >> 8);
                    for (int i = 0; i < n; i++) {
                        int o = 4 + 37 * i;
                        p[o + 1] = (byte) (i + 1);           // item id (8 bytes, echoed by purchases)
                        p[o + 9] = (byte) CATALOG[i][0];     // product id (8 bytes, echoed by billing)
                        p[o + 10] = (byte) CATALOG[i][1];
                        p[o + 17] = (byte) CATALOG[i][0];    // item category
                        p[o + 18] = (byte) CATALOG[i][1];    // item index
                        p[o + 19] = (byte) CATALOG[i][2];    // count
                        p[o + 20] = 1;
                        // price (4 bytes at o + 21) = 0
                    }
                    reply(5, 63, p);
                    return;
                }
                case 66: reply(5, 66, cat(new byte[]{1, 0}, str("..."))); return;   // purchase accepted -> billing
                case 64:                                                               // phone billing
                case 107: {                                                            // KOIN billing
                    byte[] m = str("Đã mua (miễn phí).");
                    byte[] p = new byte[2 + m.length + 1];
                    p[0] = 1;
                    p[1] = (byte) m.length;
                    System.arraycopy(m, 0, p, 2, m.length);
                    p[2 + m.length] = (byte) boxCode(pl);
                    reply(5, minor, p);
                    return;
                }
                case 106: { // KOIN balance: huge balance, price 0
                    byte[] m = str("KOIN");
                    byte[] p = new byte[2 + m.length + 8];
                    p[0] = 1;
                    p[1] = (byte) m.length;
                    System.arraycopy(m, 0, p, 2, m.length);
                    int o = 2 + m.length;
                    p[o] = (byte) 0x40; p[o + 1] = 0x42; p[o + 2] = 0x0f; // 1,000,000
                    reply(5, 106, p);
                    return;
                }
                case 110:                                                               // revive purchase
                case 111: reply(5, minor, cat(new byte[]{1, 0}, str("Hồi sinh (miễn phí)."))); return;
            }
        }
        // everything else (network warehouse, character restore, ...) is not available offline
        System.out.println("[FakeServer] unhandled " + major + "/" + minor + " -> drop");
        dropped = true;
    }

    /** Billing result code: 0xff = give the bought item; for treasure chests a random content set. */
    private int boxCode(byte[] pl) {
        int cat = pl.length > 0 ? pl[0] & 0xFF : 0, idx = pl.length > 1 ? pl[1] & 0xFF : 0;
        if (cat != 8 || idx < 25 || idx > 28) return 0xff;
        String mode = prop("hl4.fs.box");
        if (mode.length() > 0) return Integer.parseInt(mode);
        switch (idx) {
            case 25: return rnd.nextInt(2);
            case 26: return 2 + rnd.nextInt(2);
            case 27: return 4 + rnd.nextInt(2);
            default: return 6;
        }
    }

    protected void reply(int major, int minor, byte[] pl) {
        int len = pl.length + 6;
        byte[] p = new byte[len];
        p[0] = (byte) len;
        p[1] = (byte) (len >> 8);
        p[2] = (byte) (len >> 16);
        p[3] = (byte) (len >> 24);
        p[4] = (byte) major;
        p[5] = (byte) minor;
        System.arraycopy(pl, 0, p, 6, pl.length);
        if (out == null) out = p;
        else {
            byte[] n = new byte[out.length + len];
            System.arraycopy(out, 0, n, 0, out.length);
            System.arraycopy(p, 0, n, out.length, len);
            out = n;
        }
    }

    /** Reply bytes that became available (null when none). */
    public byte[] poll() {
        byte[] o = out;
        out = null;
        return o;
    }

    public static String hexdump(byte[] d) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < d.length && i < 512; i++) {
            int b = d[i] & 0xFF;
            sb.append("0123456789abcdef".charAt(b >> 4)).append("0123456789abcdef".charAt(b & 15));
        }
        return sb.toString();
    }
}
