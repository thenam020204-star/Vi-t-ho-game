package javax.microedition.lcdui;
/** Desktop test stub with MIDP2.0-exact signatures. NOT shipped. */
public class Graphics {
    public int[] buf; public int bw, bh; private int color;
    public Graphics(int bw,int bh){this.bw=bw;this.bh=bh;buf=new int[bw*bh];}
    public int getColor(){return color;}
    public void setColor(int c){color=c&0xFFFFFF;}
    private void blend(int px,int x,int y){
        if(x<0||y<0||x>=bw||y>=bh) return;
        if((px>>>24)==0) return;
        buf[y*bw+x]=px;
    }
    public void drawRGB(int[] rgbData,int offset,int scanlength,int x,int y,int width,int height,boolean processAlpha){
        for(int j=0;j<height;j++) for(int i=0;i<width;i++) blend(rgbData[offset+j*scanlength+i],x+i,y+j);
    }
    public void drawRegion(Image src,int x_src,int y_src,int width,int height,int transform,int x_dest,int y_dest,int anchor){
        if(transform!=0) throw new IllegalArgumentException("stub supports TRANS_NONE only");
        if((anchor&20)!=20) throw new IllegalArgumentException("stub supports TOP|LEFT only, got "+anchor);
        for(int j=0;j<height;j++) for(int i=0;i<width;i++) blend(src.argb[(y_src+j)*src.w+(x_src+i)],x_dest+i,y_dest+j);
    }
}
