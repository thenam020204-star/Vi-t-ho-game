import com.namp.vh.VNFont;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/** Test JVM: goi dung API ma CFont/CSGLFont se goi, xuat PNG de soat bang mat. */
public class VNFontTest {
    static int[] buf = new int[2048 * 2048];

    static void png(String name, int w, int h, int scale) throws Exception {
        BufferedImage im = new BufferedImage(w * scale, h * scale, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < h; y++) for (int x = 0; x < w; x++) {
            int v = buf[y * w + x] != 0 ? 0xFFFFFF : 0x203040;
            for (int a = 0; a < scale; a++) for (int c = 0; c < scale; c++) im.setRGB(x * scale + c, y * scale + a, v);
        }
        ImageIO.write(im, "png", new File(name));
    }

    static int lit(int n) {
        int c = 0;
        for (int i = 0; i < n; i++) if (buf[i] != 0) c++;
        return c;
    }

    public static void main(String[] a) throws Exception {
        String out = a[0];
        String s = "Xin chào! Hắc Kỵ Sĩ: Ấy, Ữ, ỹ - _ = ~ ’ ★ 1234567890";
        byte[] b = s.getBytes("UTF-8");
        int[] sizes = {14, 20, 24, 28, 38};
        for (int sz : sizes) {
            int w = VNFont.getStringWidth(b, sz, 0);
            int w2 = VNFont.drawText(buf, b, sz, 0);
            if (w != w2) throw new RuntimeException("width mismatch " + sz);
            png(out + "/line_" + sz + ".png", w, (int) (sz * 1.3), 2);
        }
        // size >= 24 (F24): drawText khong bi cat hang. So diem sang drawText (cao size*1.3) == drawInRect mot dong (cao lineH)
        String tall = "Ẫ Ấ Ậ Ể Ỗ Ữ Ự Ỹ ầ ẫ ẩ ấ ậ ể ỗ ữ ự ỹ ỡ ợ Q g p y ặ ị";
        byte[] tb = tall.getBytes("UTF-8");
        for (int sz : new int[] {24, 28, 38}) {
            int[] o2 = new int[2];
            int w = VNFont.drawText(buf, tb, sz, 0);
            int rows = (int) (sz * 1.3d);
            int lit1 = lit(w * rows);
            int ww = 1000;
            VNFont.measureInRect(tb, sz, 0, ww, 1, o2);
            VNFont.drawInRect(buf, tb, sz, 0, 0, ww, 1);
            int lit2 = lit(ww * o2[1]);
            System.out.println("size " + sz + ": drawText " + w + "x" + rows + " lit=" + lit1 + ", drawInRect 1 dong " + ww + "x" + o2[1] + " lit=" + lit2);
            if (lit1 != lit2) throw new RuntimeException("drawText bi cat o size " + sz);
        }
        String d = "Thật ra, ngươi có thể giúp ta ngay lúc này. Hãy đi tìm pháp sư Grumman. Ông ta hơi lạc đường, nhưng ngươi sẽ tìm thấy ông ấy ở phía đông khu rừng.\nDòng mới sau ký tự xuống dòng. Từdàiquámứckhôngcódấucáchnàocảđểthửcắtkýtự.";
        b = d.getBytes("UTF-8");
        int[] o = new int[2];
        for (int al = 0; al < 3; al++) {
            VNFont.measureInRect(b, 20, 0, 300, 1, o);
            VNFont.drawInRect(buf, b, 20, 0, al, 300, 1);
            png(out + "/rect_al" + al + ".png", 300, o[1], 2);
            System.out.println("rect align " + al + " -> " + o[0] + "x" + o[1]);
        }
        // doan dai qua maxW
        int[] pos = {0};
        int parts = 0;
        while (pos[0] >= 0 && parts < 50) { VNFont.drawTextPart(buf, 120, b, 20, 0, pos); parts++; }
        System.out.println("parts " + parts);
        System.out.println("OK");
    }
}
