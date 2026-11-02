/*
 * @(#) CPBG.java
 *
 * Copyright (C) <2006> Beijing Animal Studio. All rights reserved.
 * Beijing Animal Studio PROPRIETARY/CONFIDENTIAL.
 */

import javax.microedition.lcdui.*;
import java.io.*;

/**
 * The <code>CPBG</code> class represents all PBG format images. All
 * PBG format images in this project, such as <code>ct_t</code>, are
 * implemented as instances of this class. Now in RainbowSix4, the PBG images are
 * packaged by modules, so this class buffer some modules which are recently used.
 *
 * This class includes <code>{@link #initSize}, {@link #load}, {@link #draw2RGBBuffer} ,
 * {@link #changePalColor}, {@link #enablePrecalcImage},{@link #resetAll} and {@link #draw}</code>methods.
 * <p>
 * <p>
 * Note that the draw methods of this class use drawRegion, so it must be supportted by <i><B>MIDP2.0</B></i> SDK.
 * So this class can not be used on <i><B>MIDP1.0</B></i> right now.
 *
 * @author Tacit.W
 * @see #enablePrecalcImage(int,int)
 * @see #draw(Graphics,int,int,int,int)
 * @see  Graphics#drawRegion(Image,int,int,int,int,int,int,int,int)

 */
public class CPBG
        implements Const
{
//@#if USE_PBG_RES
//@    /** the max buffered module number of each PBG.*/
//@//    private final static int PRECALC_IMAGES_MAX = 11;
//@    //-=Variables=-
//@    //final static int FLAG_16_COLORS                = 0x1;        // (1 << 0)
//@    //final static int FLAG_8_COLORS                = 0x2;        // (1 << 1)
//@    //final static int FLAG_RLE                    = 0x4;        // (1 << 2)
//@    //final static int FLAG_RLE_8BITS                = 0x8;        // (1 << 3)
//@    //final static int FLAG_RLE_8BITS_FRAME        = 0x10;        // (1 << 4)
//@    //final static int FLAG_MULTIPALETTE            = 0x100;    // (1 << 8)
//@    //final static int FLAG_REBUILD_FILE            = 0x200;    // (1 << 9)
//@    final static int FLAG_REBUILD_SIZE = 0x400; // (1 << 10)
//@    //final static int FLAG_INCLUDE                = 0x800;    // (1 << 11)
//@    //final static int FLAG_OPTIM_PAL                = 0x1000;    // (1 << 12)
//@    //final static int FLAG_EXACT_PAL                = 0x2000;    // (1 << 13)
//@    //final static int FLAG_4444_FORMAT            = 0x4000;    // (1 << 14)
//@    //final static int FLAG_1555_FORMAT            = 0x8000;    // (1 << 15)
//@    //final static int FLAG_TRANSPARENT            = 0x10000;    // (1 << 16)
//@    //final static int FLAG_ALPHA                    = 0x20000;    // (1 << 17)
//@    final static int FLAG_REBUILD_SPRITE = 0x40000; // (1 << 18)
//@    final static int FLAG_REBUILD_MODULE = 0x80000; // (1 << 19)
//@    final static int FLAG_16BIT_4_LEN = 0x200000; // (1 << 21)
//@    /**Data size of each module. 2 for PBG on module, include <code>width</code> and <code>height</code>.
//@     * 4 for PBG on frame, include  <code>width</code>, <code>height</code>, <code>x position</code> and
//@     * <code>y position</code>*/
//@    final static int sf_module_size = 2;
//@
//@    /**Image data of PBG*/
//@    byte[] m_data;
//@    /**data offset of each module in {@link #_data}*/
//@    int[] _data_offset;
//@
//@    /**the pallette of currently used*/
//@    int _pal_cur = -1;
//@
//@    /**All module images of PBG. The number is <code>pallette number * module number</code>.
//@     * But in RainbowSix4, it only saved some stack modules of PBG. The max number of stack module is
//@     * {@link #_precalc_stack_max} */
//@    Image[] _precalc_images;
//@    /**pallette of this PBG. First dimension is the pal number of this PBG,
//@     * second dimension is value of each pallette */
//@    int[][] _pal;
//@    /**Return object of {@link #getImage}, now it is no use in RainbowSix4*/
//@    static Image _image_temp;
//@
//@    static int[] s_bufB = new int[STATIC_BUFFER_SIZE];
//@
//@    /**Contain the index of stacked module in {@link #_precalc_images}. Array lenght is determained by
//@     * {@link #_precalc_stack_max}*/
//@    int _precalc_stack[];
//@    //int             _precalc_stack_ratio[];
//@    /**The stack index of currently used in {@link #draw} method.*/
//@    int _precalc_stack_index;
//@    /**The max stack number of stacked module of each PBG. It also is the array length of {@link #_precalc_stack}*/
//@    int _precalc_stack_max;
//@
//@    /**If use percalc pallette, in {@link #setPalette} method, it will precalculate compressed palettes
//@     * and save the result in {@link #_pal_ext}  */
//@    private final static boolean USE_PRECALC_PAL = true;
//@
//@    /**The precalculated extend palette value. If the {@link #USE_PRECALC_PAL} is true, in
//@     * {@link #setPalette} method, it will extend all palette from the compressed palettes data.  */
//@    int[][][] _pal_ext;
//@    /**The extended palette data of currently used. It was precalculated in {@link #_pal_ext}.  */
//@    int[][] _pal_ext_current;
//@    /**The flag of REBUILD SPRITE or REBUILD MODULE.*/
//@
//@    int _flags;
//@    /**The width of rebuilded module. */
//@    int _rebuild_width;
//@    /**The height of rebuilded module. */
//@    int _rebuild_height;
//@    /**The size of image data in PBG. */
//@    int _data_size;
//@
//@    /**The bit count represents pallette index of each byte in image data block of PBG. For more details,
//@     * plz see PBG document writed by Cao Heng*/
//@    int _count;
//@    /**Bit mask of the pallette index data of each byte.*/
//@    int _mask;
//@
//@// *** frames
//@    /**The module or frame number of PBG.*/
//@    int m_modules_amount;
//@    /**The module/frame data of PBG.*/
//@    byte[] m_modules;
//@    /**The width of each module/frame.*/
//@    int m_width;
//@    /**The height of each module/frame.*/
//@    int m_height;
//@    /**The left position of each frame.*/
//@    int m_left;
//@    /**The top position of each frame.*/
//@    int m_top;
//@
//@    CPBG m_refPBG = null;
//@
//@    /**
//@     * Load PBG data, include module/frame number, module/frame data, data offset. And set the current pallette
//@     * to <code>0</code>.
//@     * @param name The name of PBG file.
//@     */
//@    public void load(String name)
//@    {
//@        try
//@        {
//@            DataInputStream dis = new DataInputStream(name.getClass().getResourceAsStream(name));
//@            load(dis);
//@            dis.close();
//@            dis = null;
//@        }
//@        catch (Exception e)
//@        {
//@            System.out.println(": Error while loading " + name);
//@            e.printStackTrace();
//@        }
//@
//@    }
//@
//@    public void load(DataInputStream dis)
//@    {
//@        try
//@        {
//@            _flags = dis.readInt();
//@            LoadImage(dis);
//@            if ((_flags & FLAG_REBUILD_MODULE) != 0)
//@            {
//@                // load animation info
//@                m_modules_amount = dis.readUnsignedShort();
//@                m_modules = new byte[m_modules_amount * sf_module_size];
//@                dis.read(m_modules);
//@            }
//@            _data_offset = new int[m_modules_amount];
//@            int offset = 0;
//@            for (int i = 0; i < m_modules_amount; ++i)
//@            {
//@                _data_offset[i] = offset;
//@                if ((_flags & FLAG_REBUILD_SIZE) != 0)
//@                {
//@                    offset += 16 * 16;
//@                }
//@                else
//@                {
//@                    if (0 == (_flags & FLAG_16BIT_4_LEN))
//@                    {
//@                        offset += dis.read();
//@                    }
//@                    else
//@                    {
//@                        offset += dis.readShort();
//@                    }
//@                }
//@            }
//@            setPalette(0);
//@        }
//@        catch (Exception e)
//@        {
//@            {
//@                e.printStackTrace();
//@            }
//@        }
//@        System.gc();
//@    }
//@
//@    /**
//@     * Load module/frame data and all pallette of PBG. Set {@link #_rebuild_width},{@link #_rebuild_height}
//@     * {@link #m_nb_frames}, {@link #_count} and read pallette and {@link #_data} from PBG file.
//@     * @param dis DataInputStream from {@link #load},
//@     */
//@    private void LoadImage(DataInputStream dis)
//@    {
//@        try
//@        {
//@            int i, j, temp, pal;
//@            //System.out.println();
//@            // image info
//@            //if ((_flags & (FLAG_REBUILD_FILE | FLAG_INCLUDE | FLAG_REBUILD_SPRITE)) == 0)
//@            if ((_flags & FLAG_REBUILD_SIZE) != 0)
//@            {
//@                _rebuild_width = dis.read() & 0xFF;
//@                _rebuild_height = dis.read() & 0xFF;
//@
//@                m_modules_amount = dis.readShort() & 0xFFFF; // map tile count can be 256
//@            }
//@            else
//@            {
//@                _count = dis.read() & 0xFF;
//@                _mask = 0xFF >> (8 - _count);
//@            }
//@
//@            _data_size = dis.readInt();
//@            _pal = new int[dis.read() & 0xFF][];
//@            temp = dis.read() & 0xFF;
//@
//@            int len = _pal.length;
//@
//@            for (i = 0; i < len; ++i)
//@            {
//@                _pal[i] = new int[temp];
//@
//@                for (j = 0; j < temp; ++j)
//@                {
//@                    pal = dis.readUnsignedShort();
//@
//@                    // MIDP2 // if alpha blending isn't supported
//@                    _pal[i][j] = (((((pal & 0xF000) >>> 12) * (17 << 24)) & 0xFF000000)
//@                                  | ((((pal & 0x0F00) >>> 8) * (17 << 16)) & 0x00FF0000)
//@                                  | ((((pal & 0x00F0) >>> 4) * (17 << 8)) & 0x0000FF00) | ((((pal & 0x000F) * 17))));
//@                    if (_pal[i][j] == 0xffff00ff)
//@                    {
//@                        _pal[i][j] = 0x00ff00ff;
//@                    }
//@                }
//@            }
//@
//@            if (USE_PRECALC_PAL)
//@            {
//@                _pal_ext = new int[len][][];
//@            }
//@
//@            m_data = new byte[_data_size];
//@            dis.read(m_data);
//@        }
//@        catch (Exception e)
//@        {
//@            System.out.println(e + ": Error while loading image");
//@        }
//@        System.gc();
//@    }
//@
//@    /**
//@     * Set currently used palette. If {@link #USE_PRECALC_PAL} is true, precalculete the extended palette from compressed pal data. When
//@     * all pal data was been extend, then it will not precalculate pallette any more.
//@     * @param palette the pallette index
//@     * @return false if palette index exceed the pal length, or palette is currently used now.
//@     */
//@    public boolean setPalette(int palette)
//@    {
//@        if (_pal_cur == palette || palette >= _pal.length)
//@        {
//@            return false;
//@        }
//@
//@        _pal_cur = palette;
//@
//@        if (USE_PRECALC_PAL)
//@        {
//@            if (_pal_ext[palette] == null)
//@            {
//@                int i, j;
//@
//@                _pal_ext[palette] = new int[_pal[palette].length][];
//@
//@                for (i = 0; i < _pal_ext[palette].length; ++i)
//@                {
//@                    _pal_ext[palette][i] = new int[(0xFF >> _count) + 1];
//@
//@                    for (j = 0; j <= (0xFF >> _count); ++j)
//@                    {
//@                        _pal_ext[palette][i][j] = _pal[palette][i];
//@                    }
//@                }
//@            }
//@            _pal_ext_current = _pal_ext[palette];
//@        }
//@        return true;
//@    }
//@
//@    public void delPalette(int palette)
//@    {
//@        if (_pal_cur == palette)
//@            _pal_cur = -1;
//@
//@        if (palette >= _pal.length)
//@            return;
//@        _pal[palette] = null;
//@
//@        if (USE_PRECALC_PAL)
//@            _pal_ext[palette] = null;
//@
//@        if (_precalc_images != null)
//@        {
//@            int i = m_modules_amount * palette;
//@            int end = i + m_modules_amount;
//@            for (; i < end; i++)
//@            {
//@                _precalc_images[i] = null;
//@            }
//@        }
//@        System.gc();
//@    }
//@
//@    /**
//@     * Change a color of the <code>index</code> pallette, and then set the new pallette to currently use.
//@     * Release the buffered module image which used old pallette.
//@     *
//@     * @param pal The index of pal
//@     * @param index The index color of one pal
//@     * @param color New color value of the index color.
//@     */
//@
//@    public void changePalColor(int pal, int index, int color)
//@    {
//@        _pal_ext[_pal_cur] = null;
//@
//@        _pal[pal][index] = color;
//@
//@        _pal_cur = -1;
//@        setPalette(pal);
//@
//@        _precalc_images[0] = null;
//@    }
//@
//@    /**
//@     * Set pre cached module/frame image.
//@     * For example:
//@     * <p>
//@     * If cache == <code>10</code>, 10 modules/frames will be cacahed in {@link #_precalc_images}
//@     * <p>
//@     * Plz note that:
//@     * If cache == <code>0</code>, it will initialize all module/frame(all module will be cached).
//@     * @param palette 要使用调色板的编号
//@     * @param cache 能存多少个 module
//@     */
//@    public void EnablePrecalcImage(int palette, int cache)
//@    {
//@        if (_precalc_images == null)
//@        {
//@            _precalc_images = new Image[_pal.length * m_modules_amount];
//@        }
//@        _pal_cur = -1;
//@        setPalette(palette);
//@        if (cache == 0)
//@        {
//@            for (int i = 0; i < m_modules_amount; i++)
//@            {
//@                int frame = i; //+palette*m_nb_frames;
//@                initSize(frame);
//@                if (m_width > 0 && m_height > 0)
//@                {
//@                    buildRLE8bfrm(frame, m_width * m_height);
//@                    frame = i + palette * m_modules_amount;
//@                    for (int ii = 0; ii < s_bufB.length; ii++)
//@                    {
//@                        if ((s_bufB[ii] & 0x00ffffff) == 0xff00ff)
//@                        {
//@                            s_bufB[ii] = 0x00000000;
//@                        }
//@                    }
//@                    _precalc_images[frame] = Image.createRGBImage(s_bufB, m_width, m_height, true);
//@                }
//@            }
//@#if COMPACT_PBG_CACHE
//@            RemoveData();
//@#endif
//@        }
//@        else
//@        {
//@            _precalc_stack_max = cache;
//@            _precalc_stack = new int[cache];
//@        }
//@    }
//@
//@    public void ReplaceColor(int oriColor, int newColor)
//@    {
//@        for (int i = 0; i < _pal_ext.length; i++)
//@        {
//@            for (int j = 0; j < _pal_ext[i].length; j++)
//@            {
//@                for (int k = 0; k < _pal_ext[i][j].length; k++)
//@                {
//@                    if (_pal_ext[i][j][k] == oriColor)
//@                    {
//@                        _pal_ext[i][j][k] = newColor;
//@                    }
//@                }
//@            }
//@
//@        }
//@    }
//@
//@    public int[] getModulePixel(int moduleID, int palette)
//@    {
//@        _pal_cur = -1;
//@        setPalette(palette);
//@        int frame = moduleID;
//@        initSize(frame);
//@        if (m_width > 0 && m_height > 0)
//@        {
//@            buildRLE8bfrm(frame, m_width * m_height);
//@        }
//@        else
//@        {
//@            return null;
//@        }
//@        return s_bufB;
//@    }
//@
//@    public void modifyCache(Image img, int cacheID, int x, int y)
//@    {
//@    }
//@
//@    /**
//@     * Delete all pre cacahed modules/frames images. None parameter and none retrun value. This function will set
//@     * {@link #_precalc_images} and {@link #_precalc_stack} to <code>null</code>.
//@     */
//@    public void removePrecalcImgPals()
//@    {
//@        int i;
//@        if (_precalc_images != null)
//@        {
//@            for (i = 0; i < _precalc_images.length; ++i)
//@            {
//@                _precalc_images[i] = null;
//@            }
//@            _precalc_images = null;
//@        }
//@        _precalc_stack = null;
//@
//@        _image_temp = null;
//@
//@    }
//@
//@    /*
//@     * release pre cached image and its info
//@     */
//@    public void releaseCachedImgs()
//@    {
//@        int i;
//@        if (_precalc_images != null)
//@        {
//@            for (i = 0; i < _precalc_images.length; ++i)
//@            {
//@                _precalc_images[i] = null;
//@            }
//@        }
//@
//@        if (_precalc_stack != null)
//@        {
//@            for (i = 0; i < _precalc_stack.length; ++i)
//@            {
//@                _precalc_stack[i] = 0;
//@            }
//@        }
//@    }
//@
//@    public void RemoveData()
//@    {
//@        m_data = null;
//@        _data_offset = null;
//@        _pal = null;
//@
//@        if (USE_PRECALC_PAL)
//@        {
//@            _pal_ext = null;
//@            _pal_ext_current = null;
//@        }
//@        System.gc();
//@    }
//@
//@    /**
//@     * Reset all PBG member variables. All data and pallettes of this PBG will be released. And the pre cacahed
//@     * modules/frames will be delete.
//@     */
//@    public void resetAll()
//@    {
//@        m_data = null;
//@        _data_offset = null;
//@        _pal = null;
//@        _pal_cur = -1;
//@
//@        if (USE_PRECALC_PAL)
//@        {
//@            _pal_ext = null;
//@            _pal_ext_current = null;
//@        }
//@
//@        removePrecalcImgPals();
//@        m_modules = null;
//@    }
//@
//@    /**
//@     * Get a certain module/frame of PBG. This function used to create certain module/frame of PBG
//@     * instead of release all part of it.
//@     * @param palette The pallette index of module/frame.
//@     * @param frame The module/frame index which to be created.
//@     * @return Created RGB Image.
//@     */
//@
//@    public Image getImage(int palette, int frame)
//@    {
//@        if (frame >= m_modules_amount)
//@            return null;
//@
//@        _image_temp = null;
//@        setPalette(palette);
//@        initSize(frame);
//@        if (m_width * m_height <= 0)
//@            return null;
//@        buildRLE8bfrm(frame, m_width * m_height);
//@        _image_temp = Image.createRGBImage(s_bufB, m_width, m_height, true);
//@//        s_bufB = null;
//@        return _image_temp;
//@    }
//@
//@    /**
//@     * Initialize the frame index ,width and height of indicated module/frame.
//@     * @param frame Module/frame index
//@     */
//@    public void initSize(int frame)
//@    {
//@        if ((_flags & FLAG_REBUILD_SIZE) != 0)
//@        {
//@            m_width = _rebuild_width;
//@            m_height = _rebuild_height;
//@            m_left = 0;
//@            m_top = 0;
//@        }
//@        else
//@        {
//@            frame *= sf_module_size;
//@            m_width = m_modules[frame] & 0xFF;
//@            m_height = m_modules[frame + 1] & 0xFF;
//@        }
//@    }
//@
//@    public int getModuleWidth(int module)
//@    {
//@        module *= sf_module_size;
//@        return m_modules[module] & 0xFF;
//@    }
//@
//@    public int getModuleHeight(int module)
//@    {
//@        module *= sf_module_size;
//@        return m_modules[module + 1] & 0xFF;
//@    }
//@
//@    private void buildRLE8bfrm(int offset, int size)
//@    {
//@        int count, pos = 0;
//@        int _pal_current[] = _pal[_pal_cur];
//@        offset = _data_offset[offset];
//@
//@        while (pos < size)
//@        {
//@            if ((_flags & FLAG_REBUILD_SIZE) != 0)
//@            {
//@                s_bufB[pos++] = _pal_current[m_data[offset++] & 0xFF];
//@            }
//@            else
//@            {
//@                count = (m_data[offset] & 0xFF) >> _count;
//@                if (count == 0)
//@                {
//@                    s_bufB[pos++] = _pal_current[m_data[offset++] & _mask];
//@                }
//@                else
//@                {
//@                    // USE_PRECALC_PALETTE
//@                    if (USE_PRECALC_PAL)
//@                    {
//@                        System.arraycopy(_pal_ext_current[m_data[offset++] & _mask], 0, s_bufB, pos, ++count);
//@                        pos += count;
//@                    }
//@                    else
//@                    {
//@                        int c = _pal_current[m_data[offset++] & _mask];
//@                        ++count;
//@                        for (int i = 0; i < count; ++i)
//@                        {
//@                            s_bufB[pos++] = c;
//@                        }
//@                    }
//@                }
//@            }
//@        }
//@    }
//@
//@    public void draw(Graphics g, int frame, int x, int y, int flag)
//@    {
//@        if (frame >= m_modules_amount || _pal_cur < 0)
//@        {
//@            return;
//@        }
//@        // init frame data
//@        initSize(frame);
//@
//@        if (m_width <= 0 || m_height <= 0)
//@        {
//@            return;
//@        }
//@
//@        frame += m_modules_amount * _pal_cur;
//@
//@        if (_precalc_images[frame] == null)
//@        {
//@            if (_precalc_stack != null)
//@            {
//@                if (_precalc_stack[_precalc_stack_index] >= 0)
//@                {
//@                    _precalc_images[_precalc_stack[_precalc_stack_index] >> 16] = null;
//@                }
//@                _precalc_stack[_precalc_stack_index] = frame << 16;
//@                _precalc_stack[_precalc_stack_index] += (1 << 8);
//@                _precalc_stack_index = (_precalc_stack_index + 1) % _precalc_stack_max;
//@                buildRLE8bfrm(frame % m_modules_amount, m_width * m_height);
//@                _precalc_images[frame] = Image.createRGBImage(s_bufB, m_width, m_height, true);
//@            }
//@            else
//@            {
//@                addRef(frame);
//@            }
//@        }
//@        g.drawRegion(_precalc_images[frame], 0, 0, m_width, m_height, flag, x, y, ALIGN_LEFT_TOP);
//@//        s_bufB = null;
//@    }
//@
//@    public void draw2RGBBuffer(int[] buffer, int frame, int x, int y, int scanLine)
//@    {
//@        if (frame >= m_modules_amount || _pal_cur < 0)
//@            return;
//@
//@        // init frame data
//@        initSize(frame);
//@
//@        if (m_width <= 0 || m_height <= 0)
//@        {
//@            if (DEBUG_PRINT_OUT)
//@                System.out.println("ERROR: sprite with a width or height null.");
//@            return;
//@        }
//@
//@        buildRLE8bfrm(frame, m_width * m_height);
//@        int pos0 = 0;
//@        int pos1 = y * scanLine + x;
//@        for (int i = 0; i < m_height; i++)
//@        {
//@            System.arraycopy(s_bufB, pos0, buffer, pos1, m_width);
//@            pos0 += m_width;
//@            pos1 += scanLine;
//@        }
//@//        s_bufB = null;
//@    }
//@
//@    static final int MIN_RATIO_2_RELEASE = 5;
//@
//@    public void releaseLTNU()
//@    {
//@        for (int i = 0; i < _precalc_stack_max; ++i)
//@        {
//@            if (_precalc_stack[i] != 0)
//@            {
//@                if ((_precalc_stack[i] & 0xFF) / ((_precalc_stack[i] >> 8) & 0xFF) > MIN_RATIO_2_RELEASE)
//@                {
//@
//@                    _precalc_images[(_precalc_stack[i] >> 16)] = null;
//@
//@                    if (_precalc_stack != null)
//@                        _precalc_stack[i] = 0;
//@                }
//@                else
//@                {
//@                    if (_precalc_stack != null)
//@                        _precalc_stack[i] = (_precalc_stack[i] & 0xFFFF0000) + 0x101;
//@                }
//@            }
//@        }
//@    }
//@
//@    public void increaseTime()
//@    {
//@        for (int i = 0; i < _precalc_stack_max; ++i)
//@        {
//@            if (_precalc_stack != null)
//@                if (0 != _precalc_stack[i])
//@                {
//@                    ++_precalc_stack[i];
//@                }
//@        }
//@    }
//@
//@    public void addRef(int frame)
//@    {
//@        for (int i = 0; i < _precalc_stack_max; ++i)
//@        {
//@            if (_precalc_stack != null)
//@            {
//@                if ((_precalc_stack[i] >> 16) == frame)
//@                {
//@
//@                    if (0xFF00 == (_precalc_stack[i] & 0xFF00))
//@                    {
//@                        _precalc_stack[i] = (_precalc_stack[i] & 0xFFFF0000) + 0x101;
//@                    }
//@                    else
//@                    {
//@                        _precalc_stack[i] += (1 << 8);
//@                    }
//@                    break;
//@                }
//@            }
//@        }
//@    }
//@#endif
}
