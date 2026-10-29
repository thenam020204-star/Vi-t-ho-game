/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
package src;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.microedition.lcdui.Image;

public class Util {
    static Random random = new Random();
    static short[] mapinfo;
    static int backColor;
    static short[][] modulesdata;
    static byte[][][] maptype;
    static short[][][][] mapdata;
    static short[][] collidedata;
    static short[][] flushenemyrect;
    static byte[] tempData;
    static int[] position;
    static String[] talksdata;
    static String[] npcname;
    static short[][] npcdata;
    static short[][] chestdata;
    static short[][] doordata;
    static short[][][] eventdata;
    static short[][] modules;
    static short[][] att;
    static short[][] bea;
    static short[][][] frames;
    static byte[][][] actions;
    static byte[][][][] effDatas;
    static byte[][][][] attDatas;
    static final int EFFNUM = 13;
    static final int ATTNUM = 17;
    static String[][] xls2DUTFdata;
    static byte[][] xls2DBYTEdata;
    static short[][] xls2DSHORTdata;
    static int[][] xls2DINTdata;

    public static void clearmapdata() {
        mapinfo = null;
        modulesdata = null;
        maptype = null;
        mapdata = null;
        collidedata = null;
        flushenemyrect = null;
    }

    public static void readMapdata(String path) {
        try {
            int k;
            int j;
            int i;
            int i2;
            DataInputStream dis = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            mapinfo = new short[dis.readShort()];
            for (i2 = 0; i2 < mapinfo.length; ++i2) {
                Util.mapinfo[i2] = dis.readShort();
            }
            backColor = dis.readInt();
            modulesdata = new short[dis.readShort()][];
            for (i2 = 0; i2 < modulesdata.length; ++i2) {
                Util.modulesdata[i2] = new short[5];
                for (int j2 = 0; j2 < modulesdata[i2].length; ++j2) {
                    Util.modulesdata[i2][j2] = dis.readShort();
                }
            }
            maptype = new byte[dis.readShort()][][];
            byte temptype = 0;
            for (i = 0; i < maptype.length; ++i) {
                Util.maptype[i] = new byte[dis.readShort()][];
                block18: for (j = 0; j < maptype[i].length; ++j) {
                    temptype = dis.readByte();
                    switch (temptype) {
                        case 0: {
                            Util.maptype[i][j] = new byte[1];
                            Util.maptype[i][j][0] = temptype;
                            continue block18;
                        }
                        case 1: {
                            Util.maptype[i][j] = new byte[3];
                            Util.maptype[i][j][0] = temptype;
                            Util.maptype[i][j][1] = 0;
                            Util.maptype[i][j][2] = 0;
                            continue block18;
                        }
                        case 2: {
                            Util.maptype[i][j] = new byte[5];
                            Util.maptype[i][j][0] = temptype;
                            for (k = 1; k < maptype[i][j].length; ++k) {
                                Util.maptype[i][j][k] = dis.readByte();
                            }
                            continue block18;
                        }
                        case 3: {
                            Util.maptype[i][j] = new byte[7];
                            Util.maptype[i][j][0] = temptype;
                            for (k = 1; k < maptype[i][j].length; ++k) {
                                Util.maptype[i][j][k] = dis.readByte();
                            }
                            continue block18;
                        }
                    }
                }
            }
            mapdata = new short[maptype.length][][][];
            for (i = 0; i < mapdata.length; ++i) {
                Util.mapdata[i] = new short[maptype[i].length][][];
                for (j = 0; j < mapdata[i].length; ++j) {
                    Util.mapdata[i][j] = new short[dis.readShort()][];
                    block23: for (k = 0; k < mapdata[i][j].length; ++k) {
                        switch (maptype[i][j][0]) {
                            case 0: {
                                int l;
                                Util.mapdata[i][j][k] = new short[4];
                                for (l = 0; l < mapdata[i][j][k].length; ++l) {
                                    Util.mapdata[i][j][k][l] = dis.readShort();
                                }
                                continue block23;
                            }
                            case 1: {
                                int l;
                                Util.mapdata[i][j][k] = new short[5];
                                for (l = 0; l < mapdata[i][j][k].length; ++l) {
                                    Util.mapdata[i][j][k][l] = dis.readShort();
                                }
                                continue block23;
                            }
                            case 2: {
                                int l;
                                Util.mapdata[i][j][k] = new short[4];
                                for (l = 0; l < mapdata[i][j][k].length; ++l) {
                                    Util.mapdata[i][j][k][l] = dis.readShort();
                                }
                                continue block23;
                            }
                            case 3: {
                                int l;
                                Util.mapdata[i][j][k] = new short[6];
                                for (l = 0; l < 4; ++l) {
                                    Util.mapdata[i][j][k][l] = dis.readShort();
                                }
                                Util.mapdata[i][j][k][4] = mapdata[i][j][k][2];
                                Util.mapdata[i][j][k][5] = mapdata[i][j][k][3];
                            }
                        }
                    }
                }
            }
            collidedata = new short[dis.readShort()][4];
            for (i = 0; i < collidedata.length; ++i) {
                for (j = 0; j < collidedata[i].length; ++j) {
                    Util.collidedata[i][j] = dis.readShort();
                }
            }
            flushenemyrect = new short[dis.readShort()][4];
            for (i = 0; i < flushenemyrect.length; ++i) {
                for (j = 0; j < flushenemyrect[i].length; ++j) {
                    Util.flushenemyrect[i][j] = dis.readShort();
                }
            }
            dis.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void readEventData(String path) {
        try {
            int j;
            int i;
            DataInputStream dis = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            talksdata = new String[dis.readShort()];
            for (i = 0; i < talksdata.length; ++i) {
                Util.talksdata[i] = dis.readUTF();
            }
            npcdata = new short[dis.readShort()][];
            for (i = 0; i < npcdata.length; ++i) {
                Util.npcdata[i] = new short[dis.readShort()];
                for (j = 0; j < npcdata[i].length; ++j) {
                    Util.npcdata[i][j] = dis.readShort();
                }
            }
            chestdata = new short[dis.readShort()][];
            for (i = 0; i < chestdata.length; ++i) {
                Util.chestdata[i] = new short[dis.readShort()];
                for (j = 0; j < chestdata[i].length; ++j) {
                    Util.chestdata[i][j] = dis.readShort();
                }
            }
            doordata = new short[dis.readShort()][];
            for (i = 0; i < doordata.length; ++i) {
                Util.doordata[i] = new short[dis.readShort()];
                for (j = 0; j < doordata[i].length; ++j) {
                    Util.doordata[i][j] = dis.readShort();
                }
            }
            eventdata = new short[dis.readShort()][][];
            for (i = 0; i < eventdata.length; ++i) {
                Util.eventdata[i] = new short[dis.readShort()][];
                for (j = 0; j < eventdata[i].length; ++j) {
                    Util.eventdata[i][j] = new short[dis.readShort()];
                    for (int k = 0; k < eventdata[i][j].length; ++k) {
                        Util.eventdata[i][j][k] = dis.readShort();
                    }
                }
            }
            dis.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void clearEventdata() {
        talksdata = null;
        npcdata = null;
        chestdata = null;
        doordata = null;
        eventdata = null;
    }

    public static void clearActdata() {
        modules = null;
        att = null;
        bea = null;
        frames = null;
        actions = null;
    }

    public static Image createImage(String path) {
        try {
            Image image = Image.createImage((String)path);
            return image;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.print(path);
            return null;
        }
    }

    public static Image createImage_1(String path) {
        try {
            byte[] roledata = Util.getPngStream(path);
            return Image.createImage((byte[])roledata, (int)0, (int)roledata.length);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.print(path);
            return null;
        }
    }

    private static byte[] getPngStream(String path) {
        byte[] data = null;
        try {
            DataInputStream dataInput = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            byte[] len = new byte[4];
            for (int i = 0; i < 4; ++i) {
                len[i] = dataInput.readByte();
            }
            int data_length = (len[3] & 0xFF) << 24 | (len[2] & 0xFF) << 16 | (len[1] & 0xFF) << 8 | len[0] & 0xFF;
            data = new byte[data_length];
            dataInput.read(data);
            dataInput.close();
        }
        catch (Exception ex) {
            System.out.print("getStream err! path = " + path);
            ex.printStackTrace();
        }
        return data;
    }

    private static byte[] getColorData(String path) {
        try {
            DataInputStream dataInput = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            byte[] len = new byte[4];
            for (int i = 0; i < 4; ++i) {
                len[i] = dataInput.readByte();
            }
            int data_length = (len[0] & 0xFF) << 24 | (len[1] & 0xFF) << 16 | (len[2] & 0xFF) << 8 | len[3] & 0xFF;
            byte[] color = new byte[data_length - 4];
            dataInput.read(color);
            dataInput.close();
            return color;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.print("getColorData err!");
            return null;
        }
    }

    public static Image changeColor(String pngPath, String dataPath) {
        byte[] source = Util.getPngStream(pngPath);
        byte[] color = Util.getColorData(dataPath);
        for (int i = 0; i < color.length; ++i) {
            source[41 + i] = color[i];
        }
        return Image.createImage((byte[])source, (int)0, (int)source.length);
    }

    public static int getARandomInt(int n) {
        return (random.nextInt() & Integer.MAX_VALUE) % n;
    }

    public static int getARandomInt(int n, int m) {
        return (random.nextInt() & Integer.MAX_VALUE) % (n - m) + n;
    }

    public static int getBRandomInt(int n) {
        return random.nextInt() % n;
    }

    public static void clearRoleData() {
        effDatas = null;
        attDatas = null;
    }

    public static void readRoleData(String path) {
        try {
            int l;
            int k;
            int j;
            int i;
            DataInputStream dis = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            effDatas = new byte[dis.readShort()][][][];
            for (i = 0; i < effDatas.length; ++i) {
                Util.effDatas[i] = new byte[dis.readShort()][][];
                for (j = 0; j < effDatas[i].length; ++j) {
                    Util.effDatas[i][j] = new byte[dis.readShort()][];
                    for (k = 0; k < effDatas[i][j].length; ++k) {
                        Util.effDatas[i][j][k] = new byte[13];
                        for (l = 0; l < effDatas[i][j][k].length; ++l) {
                            Util.effDatas[i][j][k][l] = dis.readByte();
                        }
                    }
                }
            }
            attDatas = new byte[effDatas.length][][][];
            for (i = 0; i < attDatas.length; ++i) {
                Util.attDatas[i] = new byte[dis.readShort()][][];
                for (j = 0; j < attDatas[i].length; ++j) {
                    Util.attDatas[i][j] = new byte[dis.readShort()][];
                    for (k = 0; k < attDatas[i][j].length; ++k) {
                        Util.attDatas[i][j][k] = new byte[17];
                        for (l = 0; l < attDatas[i][j][k].length; ++l) {
                            Util.attDatas[i][j][k][l] = dis.readByte();
                        }
                    }
                }
            }
            dis.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void getStream(byte[] obj, String name, int skip) {
        try {
            InputStream input = new Object().getClass().getResourceAsStream(name);
            DataInputStream dinput = new DataInputStream(input);
            dinput.skip(skip);
            dinput.read(obj, 0, obj.length);
            dinput.close();
        }
        catch (Exception e) {
            System.out.print("name = " + name);
            System.out.print("GameInf err at getStream()  :  " + e);
            e.printStackTrace();
        }
    }

    public static short[] loadInfShort(byte[] inf, short[] des, int[] position) {
        short length1 = Util.loadInfShort(inf, position);
        short singleDataLength = Util.loadInfShort(inf, position);
        des = new short[length1 * singleDataLength];
        for (int i = 0; i < des.length; ++i) {
            byte a0 = inf[position[0]];
            position[0] = position[0] + 1;
            byte a1 = inf[position[0]];
            position[0] = position[0] + 1;
            des[i] = (short)(a0 << 8 | a1 & 0xFF);
        }
        return des;
    }

    public static short[][] loadInfShort(byte[] inf, short[][] type, int[] position) {
        int length1 = Util.loadInfShort(inf, position);
        short singleDataLength = Util.loadInfShort(inf, position);
        type = new short[length1][];
        for (int i = 0; i < length1; ++i) {
            short length2 = Util.loadInfShort(inf, position);
            type[i] = new short[length2 * singleDataLength];
            for (int j = 0; j < type[i].length; ++j) {
                byte a0 = inf[position[0]];
                position[0] = position[0] + 1;
                byte a1 = inf[position[0]];
                position[0] = position[0] + 1;
                type[i][j] = (short)(a0 << 8 | a1 & 0xFF);
            }
        }
        return type;
    }

    public static short loadInfShort(byte[] inf, int[] position) {
        int n = position[0];
        position[0] = n + 1;
        int n2 = position[0];
        position[0] = n2 + 1;
        return (short)((inf[n] & 0xFF) << 8 | inf[n2] & 0xFF);
    }

    public static int[] effect_negative(Image image) {
        int imageW = image.getWidth();
        int imageH = image.getHeight();
        int[] imagePixels = Util.getPixels(image);
        image = null;
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 0;
        for (int i = 0; i < imageH; ++i) {
            for (int j = 0; j < imageW; ++j) {
                int argb = imagePixels[i * imageW + j];
                a = argb & 0xFF000000;
                r = 0xFF0000 - (argb & 0xFF0000);
                g = 65280 - (argb & 0xFF00);
                b = 255 - (argb & 0xFF);
                imagePixels[i * imageW + j] = a | r | g | b;
            }
        }
        return imagePixels;
    }

    public static Image createPixels(int[] pixels, int w, int h) {
        Image image = Image.createRGBImage((int[])pixels, (int)w, (int)h, (boolean)true);
        pixels = null;
        return image;
    }

    public static int[] getPixels(Image src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int[] pixels = new int[w * h];
        src.getRGB(pixels, 0, w, 0, 0, w, h);
        return pixels;
    }

    public static byte[] readHZK(String path) {
        byte[] data = null;
        try {
            InputStream in = new Object().getClass().getResourceAsStream(path);
            data = new byte[in.available()];
            in.read(data, 0, data.length);
            in.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public static void clearXls2DUTF() {
        xls2DUTFdata = null;
    }

    public static void readXls2DUTF(String path) {
        try {
            DataInputStream dis = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            xls2DUTFdata = new String[dis.readShort()][dis.readShort()];
            for (int i = 0; i < xls2DUTFdata.length; ++i) {
                for (int j = 0; j < xls2DUTFdata[i].length; ++j) {
                    Util.xls2DUTFdata[i][j] = dis.readUTF();
                }
            }
            dis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearXls2DBYTE() {
        xls2DBYTEdata = null;
    }

    public static void readXls2DBYTE(String path) {
        try {
            DataInputStream dis = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            xls2DBYTEdata = new byte[dis.readShort()][dis.readShort()];
            for (int i = 0; i < xls2DBYTEdata.length; ++i) {
                for (int j = 0; j < xls2DBYTEdata[i].length; ++j) {
                    Util.xls2DBYTEdata[i][j] = dis.readByte();
                }
            }
            dis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearXls2DSHORT() {
        xls2DSHORTdata = null;
    }

    public static void readXls2DSHORT(String path) {
        try {
            DataInputStream dis = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            xls2DSHORTdata = new short[dis.readShort()][dis.readShort()];
            for (int i = 0; i < xls2DSHORTdata.length; ++i) {
                for (int j = 0; j < xls2DSHORTdata[i].length; ++j) {
                    Util.xls2DSHORTdata[i][j] = dis.readShort();
                }
            }
            dis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearXls2DINT() {
        xls2DINTdata = null;
    }

    public static void readXls2DINT(String path) {
        try {
            DataInputStream dis = new DataInputStream(new Object().getClass().getResourceAsStream(path));
            xls2DINTdata = new int[dis.readShort()][dis.readShort()];
            for (int i = 0; i < xls2DINTdata.length; ++i) {
                for (int j = 0; j < xls2DINTdata[i].length; ++j) {
                    Util.xls2DINTdata[i][j] = dis.readInt();
                }
            }
            dis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        position = new int[]{0};
    }
}

