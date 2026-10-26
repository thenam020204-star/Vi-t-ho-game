import java.io.IOException;
import java.io.InputStream;

/**
 * Decrypting resource stream. Every resource in the JAR (except the MIDlet
 * icon) has each byte replaced through a 256-entry permutation derived from
 * KEY (Fisher-Yates driven by xorshift32); this class applies the inverse
 * table while the game reads. A byte substitution keeps the files as
 * compressible as the plain ones, so the JAR does not grow.
 * Replaces the Class.getResourceAsStream calls in w and av (same stack shape:
 * Class, String -> InputStream). CLDC 1.0 safe: no float, the table is built
 * once, one object per opened resource.
 */
public final class rc extends InputStream {
    private static final int KEY = 56958410;
    private static final byte[] DEC = new byte[256];

    static {
        int[] enc = new int[256];
        for (int i = 0; i < 256; i++) {
            enc[i] = i;
        }
        int s = KEY;
        for (int i = 255; i > 0; i--) {
            s ^= s << 13;
            s ^= s >>> 17;
            s ^= s << 5;
            int j = (s >>> 1) % (i + 1);
            int t = enc[i];
            enc[i] = enc[j];
            enc[j] = t;
        }
        for (int i = 0; i < 256; i++) {
            DEC[enc[i]] = (byte) i;
        }
    }

    private final InputStream in;

    private rc(InputStream in) {
        this.in = in;
    }

    public static InputStream g(Class c, String name) {
        InputStream is = c.getResourceAsStream(name);
        return is == null ? null : new rc(is);
    }

    public int read() throws IOException {
        int b = in.read();
        if (b < 0) {
            return b;
        }
        return DEC[b] & 0xFF;
    }

    // Single delegate read, like the original stream: w.a(String,boolean)
    // relies on available() + one read(byte[]), keep that behaviour.
    public int read(byte[] b, int off, int len) throws IOException {
        int n = in.read(b, off, len);
        byte[] d = DEC;
        for (int i = off, end = off + n; i < end; i++) {
            b[i] = d[b[i] & 0xFF];
        }
        return n;
    }

    public long skip(long n) throws IOException {
        return in.skip(n);
    }

    public int available() throws IOException {
        return in.available();
    }

    public void close() throws IOException {
        in.close();
    }
}
