package hl4.lgt;

/**
 * Offline replacement of the Heroes Lore 5 game server (LGT). Receives the bytes the game writes to
 * its socket and produces the reply bytes. Protocol handlers are added as they are reverse
 * engineered; unknown requests are logged and answered with nothing.
 */
public class Hl5Server {
    private byte[] in = new byte[0];
    private byte[] out = new byte[0];
    public boolean dropped;

    /** Ports the offline server answers (server save service); other connections fail at once. */
    public static boolean accepts(int port) {
        if (port == 11532 || port == 18182 || port == 9093) return true;
        String x = System.getProperty("hl5.ports"); // debug: accept more ports (harness)
        return x != null && x.indexOf(String.valueOf(port)) >= 0;
    }
    public static boolean ECHO = false;
    LgtRuntime rt;

    Hl5Server(LgtRuntime rt) {
        this.rt = rt;
    }

    public void reset() {
        in = new byte[0];
        out = new byte[0];
        dropped = false;
    }

    /** Bytes the server has ready for the game (consumed). */
    public byte[] poll() {
        byte[] r = out;
        // game-server packets are delivered one per read (the game checks len == bytes received)
        if (r.length >= 20 && r[4] == 'G') {
            int len = be32(r, 0);
            if (len > 0 && len < r.length) {
                byte[] a = new byte[len], b = new byte[r.length - len];
                System.arraycopy(r, 0, a, 0, len);
                System.arraycopy(r, len, b, 0, b.length);
                out = b;
                return a;
            }
        }
        out = new byte[0];
        return r;
    }

    protected void send(byte[] d) {
        out = cat(out, d);
    }

    public void receive(byte[] d) {
        in = cat(in, d);
        if (ECHO) {
            send(d);
            in = new byte[0];
            return;
        }
        handle();
    }

    /** Parse complete requests from {@link #in}. */
    protected void handle() {
        rt.log("[Hl5Server] req " + hexdump(in));
        // game server (18182/9093): binary packets [u32 BE len]["G1000157"][u32 major][u32 minor][fields]
        if (in.length >= 20 && in[4] == 'G') {
            while (in.length >= 4) {
                int len = be32(in, 0);
                if (len < 20 || in.length < len) return;
                byte[] pk = new byte[len];
                System.arraycopy(in, 0, pk, 0, len);
                byte[] rest = new byte[in.length - len];
                System.arraycopy(in, len, rest, 0, rest.length);
                in = rest;
                game(be32(pk, 12), be32(pk, 16));
            }
            return;
        }
        String text = ascii(in);
        in = new byte[0];
        // server save backup service (port 11532): text commands "CMD <phone> <game id>"
        if (text.startsWith("IS_SAVEDATA_EXIST")) {
            // reply type 2: [type][result][strlen][str...][flag][u32 size]; flag 0 = no save on the server
            send(new byte[]{2, 0, 0, 0, 0, 0, 0, 0});
            return;
        }
        if (text.startsWith("FINISH_SAVEDATA")) { // reply type 7: [7][result][strlen]
            send(new byte[]{7, 0, 0});
            return;
        }
    }

    /** Offline message shown for server-only services (set by the runtime; EUC-KR or Vietnamese codes). */
    public byte[] offlineMsg;

    /**
     * Game server. Only the SMS-consent request (op 1, sent from the consent screen at every start-up) is
     * answered with success: login (1,1) -> member registration (1,5) -> consent (1,2). Every other
     * service (ranking, network warehouse, trade...) gets a login error with the offline message.
     */
    void game(int major, int minor) {
        int ctx = rt.mem.read32(0x150bf80);
        int op = ctx == 0 ? -1 : rt.mem.read32(ctx + 0x344);
        rt.log("[Hl5Server] game packet " + major + "," + minor + " op " + op);
        if (major == 0) return; // client notices (0,2 = bye): no reply
        java.io.ByteArrayOutputStream b = new java.io.ByteArrayOutputStream();
        if (major == 1 && minor == 1) {
            if (op == 1) {
                put32(b, 0); put32(b, 0); put32(b, (int) (rt.host.currentTimeMillis() / 1000)); // game clock (deterministic in the harness)
            } else {
                byte[] m = offlineMsg != null ? offlineMsg : new byte[0];
                put32(b, 1); put32(b, m.length); b.write(m, 0, m.length); put32(b, 0);
            }
        } else if (major == 1 && minor == 5) { // member registration: two 64-bit ids
            put32(b, 0); put32(b, 0); put32(b, 0); put32(b, 1); put32(b, 0); put32(b, 1);
        } else if (major == 1 && minor == 2) { // SMS consent stored
            put32(b, 0); put32(b, 0);
        } else {
            byte[] m = offlineMsg != null ? offlineMsg : new byte[0];
            put32(b, 1); put32(b, m.length); b.write(m, 0, m.length); put32(b, 0); put32(b, 0);
        }
        byte[] body = b.toByteArray();
        java.io.ByteArrayOutputStream o = new java.io.ByteArrayOutputStream();
        put32(o, 20 + body.length);
        byte[] id = {'G', '1', '0', '0', '0', '1', '5', '7'};
        o.write(id, 0, 8);
        put32(o, major); put32(o, minor);
        o.write(body, 0, body.length);
        send(o.toByteArray());
    }

    static int be32(byte[] d, int o) {
        return ((d[o] & 0xFF) << 24) | ((d[o + 1] & 0xFF) << 16) | ((d[o + 2] & 0xFF) << 8) | (d[o + 3] & 0xFF);
    }

    static void put32(java.io.ByteArrayOutputStream o, int v) {
        o.write(v >>> 24); o.write(v >>> 16); o.write(v >>> 8); o.write(v);
    }

    static String ascii(byte[] d) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < d.length; i++) sb.append((char) (d[i] & 0xFF));
        return sb.toString();
    }

    static byte[] cat(byte[] a, byte[] b) {
        byte[] n = new byte[a.length + b.length];
        System.arraycopy(a, 0, n, 0, a.length);
        System.arraycopy(b, 0, n, a.length, b.length);
        return n;
    }

    public static String hexdump(byte[] d) {
        StringBuffer sb = new StringBuffer();
        String h = "0123456789abcdef";
        for (int i = 0; i < d.length && i < 512; i++) {
            sb.append(h.charAt((d[i] >> 4) & 15)).append(h.charAt(d[i] & 15));
            if ((i & 3) == 3) sb.append(' ');
        }
        if (d.length > 512) sb.append("...");
        sb.append(" |");
        for (int i = 0; i < d.length && i < 512; i++) {
            int c = d[i] & 0xFF;
            sb.append(c >= 32 && c < 127 ? (char) c : '.');
        }
        return sb.toString();
    }
}
