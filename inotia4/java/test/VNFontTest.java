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
