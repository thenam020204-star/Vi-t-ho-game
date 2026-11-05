package javax.microedition.lcdui;
/** Desktop test stub with MIDP2.0-exact signatures. NOT shipped. */
public class Image {
    public int[] argb; public int w, h;
    private Image(int[] a,int w,int h){this.argb=a;this.w=w;this.h=h;}
    public static Image createRGBImage(int[] rgb,int width,int height,boolean processAlpha){
        int[] c=new int[width*height]; System.arraycopy(rgb,0,c,0,width*height);
        return new Image(c,width,height);
    }
    public int getWidth(){return w;} public int getHeight(){return h;}
}
