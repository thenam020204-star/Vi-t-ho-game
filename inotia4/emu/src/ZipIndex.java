import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/** Doc central directory zip: ten -> {offset du lieu, kich thuoc nen, kich thuoc that, kieu nen}. */
public class ZipIndex {
    public static class Entry { public long dataOffset, csize, usize; public int method; }
    public final Map<String, Entry> map = new HashMap<>();

    public ZipIndex(String path) throws Exception {
        try (RandomAccessFile f = new RandomAccessFile(path, "r")) {
            long len = f.length();
            int scan = (int) Math.min(len, 65557);
            byte[] tail = new byte[scan];
            f.seek(len - scan); f.readFully(tail);
            int eocd = -1;
            for (int i = scan - 22; i >= 0; i--) if (u32(tail, i) == 0x06054b50L) { eocd = i; break; }
            if (eocd < 0) throw new IllegalStateException("khong thay EOCD");
            int n = u16(tail, eocd + 10);
            long cdOff = u32(tail, eocd + 16), cdSize = u32(tail, eocd + 12);
            byte[] cd = new byte[(int) cdSize];
            f.seek(cdOff); f.readFully(cd);
            int p = 0;
            byte[] lh = new byte[30];
            for (int k = 0; k < n; k++) {
                int method = u16(cd, p + 10);
                long csize = u32(cd, p + 20), usize = u32(cd, p + 24);
                int nl = u16(cd, p + 28), el = u16(cd, p + 30), cl = u16(cd, p + 32);
                long lho = u32(cd, p + 42);
                String name = new String(cd, p + 46, nl, StandardCharsets.UTF_8);
                f.seek(lho); f.readFully(lh);
                Entry e = new Entry();
                e.dataOffset = lho + 30 + u16(lh, 26) + u16(lh, 28);
                e.csize = csize; e.usize = usize; e.method = method;
                map.put(name, e);
                p += 46 + nl + el + cl;
            }
        }
    }

    static int u16(byte[] b, int i) { return (b[i] & 0xff) | (b[i + 1] & 0xff) << 8; }
    static long u32(byte[] b, int i) { return (u16(b, i) | (long) u16(b, i + 2) << 16) & 0xffffffffL; }
}
