package hl4.game;

import hl4.gfx.Blit;
import hl4.gfx.Cif;
import hl4.gfx.Frame;
import hl4.gfx.Fx;
import hl4.gfx.Gbm;
import hl4.gfx.Hf;
import hl4.gfx.Prim;
import hl4.gfx.Sprite;
import hl4.sys.Bytes;
import hl4.sys.Clock;
import hl4.sys.Rng;

/*
 * 60 fps display by interpolation: logic keeps the original tick rate, and between two ticks
 * the current frame is rendered again ("ghost" frames) with every visual quantity moved back
 * toward its value of the previous tick. The whole game state that render() touches is saved
 * before and restored after each ghost frame, so ghost frames never change gameplay.
 *
 * session 1 (movement 60 fps): object positions (pool objects x/y) and camera.
 * session 2 (60 fps everywhere): title / main menu screens, screen fades and UI slide
 * counters (SC_* table), and sprite animation tweening (cell positions between two frames
 * of the same animation, matched against the sprite draws of the previous tick).
 */
public final class Interp {
    public static boolean on = System.getProperty("interp.test") != null;
    public static boolean ghost;
    public static int alpha = 128;
    private static final int JUMP = 48;
    private static final int[] SPRPOOLS = new int[]{2, 4, 3, 1, 16};
    private static final int[] POOLS = new int[]{2, 4, 3, 1, 5, 6, 16, 9, 7, 14, 10};
    private static int[][] prevX;
    private static int[][] prevY;
    private static boolean[][] hasPrev;
    private static byte[][][] saved;
    private static byte[][] gSaved;
    private static final byte[][] GLOBALS = new byte[][]{G.game, G.map, G.event, G.npcUi, G.npcUiCursor, G.gameMenu};
    private static final int[] rngBuf = new int[34];
    private static final Sprite[] sp = new Sprite[256];
    private static final int[] spSaved = new int[2560];
    private static int spN;
    private static short[] backSaved;
    private static short[] scrSaved;
    private static Obj infoSaved;
    private static int statusFrame;
    private static int penFg;
    private static int penFont;
    private static int penMode;
    private static int prevCamX;
    private static int prevCamY;
    private static boolean camValid;
    public static Sink sink;
    private static Frame shot;

    // session 2: render-only counters saved around ghost frames (title blink counter)
    private static int titleCnt;
    private static int semicolon;

    // session 2: interpolated scalars. SC_ARR indexes GLOBALS, SC_W: 1 = u8, 2 = s16, 4 = s32.
    // A value is interpolated only when both guard bytes (-1 = none) are unchanged and
    // |delta| <= SC_MAXD; otherwise it is shown as is (same rule as JUMP for positions).
    private static final int GAME = 0;
    private static final int MAP = 1;
    private static final int EVENT = 2;
    // (counters that step by 1 per tick and are multiplied into pixels are tweened on the
    // drawn value instead, see lp())
    private static final int[] SC_ARR = new int[]{MAP, MAP, EVENT};
    private static final int[] SC_OFF = new int[]{842, 847, 1536};   // screen fade, flash, tint
    private static final int[] SC_W = new int[]{2, 1, 1};
    private static final int[] SC_G1 = new int[]{840, 846, 1516};
    private static final int[] SC_G2 = new int[]{-1, -1, -1};
    private static final int[] SC_G3 = new int[]{-1, -1, -1};
    private static final int[] SC_MAXD = new int[]{40, 96, 96};
    private static final int SCN = 3;
    private static final int[] scPrev = new int[SCN];
    private static final int[] scCur = new int[SCN];
    private static final int[] scGPrev = new int[SCN];
    private static final int[] scGCur = new int[SCN];
    private static boolean scValid;

    // session 2: sprite draw log of the last two real renders (double buffer, lgW = written now)
    private static final int LOGN = 320;
    private static final Sprite[][] lgSpr = new Sprite[2][LOGN];
    private static final int[][] lgKey = new int[2][LOGN];
    private static final int[][] lgX = new int[2][LOGN];
    private static final int[][] lgY = new int[2][LOGN];
    private static final int[] lgN = new int[2];
    private static final boolean[] lgUsed = new boolean[LOGN];
    private static int lgW;
    private static boolean sprAnim;
    private static final int MATCH = 64;
    private static final int CELLJUMP = 32;

    // session 2: tween state of the sprite frame drawn right now (one drawNpc at a time)
    private static final int MAXCELL = 64;
    private static boolean tw;
    private static int twN;
    private static final boolean[] twOk = new boolean[MAXCELL];
    private static final int[] twX = new int[MAXCELL];
    private static final int[] twY = new int[MAXCELL];

    // session 2: drawn values (window position, bar height, fade alpha) of the last real render,
    // tweened in ghost frames by lp(); lpSer = serial of the real render that wrote the value
    public static final int LP_DIALOG = 0;      // + slot * 3 + {0: y, 1: x, 2: face y}
    public static final int LP_BARS = 12;       // cinema bars height (EventDraw.draw)
    public static final int LP_INFO = 13;       // monster info box slide (MainGameFrame/HudKtf.drawInfo)
    public static final int LP_SUBTITLE = 14;   // subtitle backdrop alpha (EventDraw.subTitle)
    public static final int LP_SKIP = 15;       // skip fade alpha (EventDraw.skipFade)
    private static final int LPN = 16;
    private static final int LPJUMP = 96;
    private static final int[] lpVal = new int[LPN];
    private static final int[] lpSer = new int[LPN];
    private static int serial = 1;

    // session 2: particles are simulated inside render() (time based); ghost frames simulate
    // the time elapsed since the last real render on the live arrays, restored afterwards
    private static byte[] pSaved;
    private static byte[] pArr;
    private static int pCount;
    private static int pMax;

    // session 2: damage number stages (scaleX, scaleY, alpha, y offset) of the previous and current counter
    private static final int[] effA = new int[4];
    private static final int[] effB = new int[4];

    private Interp() {
    }

    private static void alloc() {
        prevX = new int[POOLS.length][];
        prevY = new int[POOLS.length][];
        hasPrev = new boolean[POOLS.length][];
        saved = new byte[POOLS.length][][];
        int i = 0;
        while (i < POOLS.length) {
            Obj[] objs = Pool.pools[POOLS[i]];
            prevX[i] = new int[objs.length];
            prevY[i] = new int[objs.length];
            hasPrev[i] = new boolean[objs.length];
            saved[i] = new byte[objs.length][];
            int j = 0;
            while (j < objs.length) {
                saved[i][j] = new byte[objs[j].m.length];
                ++j;
            }
            ++i;
        }
        backSaved = new short[Gfx.back.px.length];
        scrSaved = new short[Gfx.scr.px.length];
        gSaved = new byte[GLOBALS.length][];
        i = 0;
        while (i < GLOBALS.length) {
            gSaved[i] = new byte[GLOBALS[i].length];
            ++i;
        }
    }

    public static void beforeTick() {
        if (!on) {
            return;
        }
        if (prevX == null) {
            Interp.alloc();
        }
        int i = 0;
        while (i < POOLS.length) {
            Obj[] objs = Pool.pools[POOLS[i]];
            int j = 0;
            while (j < objs.length) {
                byte[] m = objs[j].m;
                prevX[i][j] = Bytes.s32(m, 8);
                prevY[i][j] = Bytes.s32(m, 12);
                hasPrev[i][j] = true;
                ++j;
            }
            ++i;
        }
        prevCamX = Bytes.s32(G.map, 156);
        prevCamY = Bytes.s32(G.map, 160);
        camValid = true;
    }

    private static void addSpr(Sprite s) {
        if (s == null) {
            return;
        }
        int i = 0;
        while (i < spN) {
            if (sp[i] == s) {
                return;
            }
            ++i;
        }
        if (spN < sp.length) {
            sp[spN++] = s;
        }
    }

    private static void saveSprites() {
        spN = 0;
        Interp.addSpr(TEffects.commonSpr);
        Interp.addSpr(Imgs.imo);
        int i = 0;
        while (i < Imgs.mmenuChar.length) {
            Interp.addSpr(Imgs.mmenuChar[i]);
            ++i;
        }
        i = 0;
        while (i < Imgs.evtEffect.length) {
            Interp.addSpr(Imgs.evtEffect[i]);
            ++i;
        }
        i = 0;
        while (i < Faces.spr.length) {
            Interp.addSpr(Faces.spr[i]);
            ++i;
        }
        i = 0;
        while (i < Particles.sprites.length) {
            Interp.addSpr(Particles.sprites[i]);
            ++i;
        }
        Obj[] te = Pool.pools[6];
        i = 0;
        while (i < te.length) {
            if (te[i] instanceof TEff) {
                Interp.addSpr(((TEff) te[i]).spr);
            }
            ++i;
        }
        i = 0;
        while (i < SPRPOOLS.length) {
            Obj[] objs = Pool.pools[SPRPOOLS[i]];
            int j = 0;
            while (j < objs.length) {
                if (objs[j] instanceof Char) {
                    Interp.addSpr(((Char) objs[j]).spr);
                    Interp.addSpr(((Char) objs[j]).spr2);
                } else if (objs[j] instanceof SObj) {
                    Interp.addSpr(((SObj) objs[j]).spr);
                }
                ++j;
            }
            ++i;
        }
        i = 0;
        while (i < spN) {
            Sprite s = sp[i];
            int o = i * 10;
            spSaved[o] = s.f68;
            spSaved[o + 1] = s.f6c;
            spSaved[o + 2] = s.f6d;
            spSaved[o + 3] = s.f6e;
            spSaved[o + 4] = s.f6f;
            spSaved[o + 5] = s.c60;
            spSaved[o + 6] = s.c62;
            spSaved[o + 7] = s.c64;
            spSaved[o + 8] = s.c66;
            spSaved[o + 9] = s.c67;
            ++i;
        }
    }

    private static void restoreSprites() {
        int i = 0;
        while (i < spN) {
            Sprite s = sp[i];
            int o = i * 10;
            s.f68 = spSaved[o];
            s.f6c = spSaved[o + 1];
            s.f6d = spSaved[o + 2];
            s.f6e = spSaved[o + 3];
            s.f6f = spSaved[o + 4];
            s.c60 = spSaved[o + 5];
            s.c62 = spSaved[o + 6];
            s.c64 = spSaved[o + 7];
            s.c66 = spSaved[o + 8];
            s.c67 = spSaved[o + 9];
            ++i;
        }
    }

    private static int lerp(int from, int to) {
        int d = to - from;
        if (d > JUMP || d < -JUMP) {
            return to;
        }
        return from + (d * alpha >> 8);
    }

    private static boolean moved() {
        if (Bytes.s32(G.map, 156) != prevCamX || Bytes.s32(G.map, 160) != prevCamY) {
            return true;
        }
        int i = 0;
        while (i < POOLS.length) {
            Obj[] objs = Pool.pools[POOLS[i]];
            int j = 0;
            while (j < objs.length) {
                byte[] m = objs[j].m;
                if (Bytes.s32(m, 8) != prevX[i][j] || Bytes.s32(m, 12) != prevY[i][j]) {
                    return true;
                }
                ++j;
            }
            ++i;
        }
        return false;
    }

    private static void saveAll() {
        int i = 0;
        while (i < POOLS.length) {
            Obj[] objs = Pool.pools[POOLS[i]];
            int j = 0;
            while (j < objs.length) {
                System.arraycopy(objs[j].m, 0, saved[i][j], 0, objs[j].m.length);
                ++j;
            }
            ++i;
        }
        i = 0;
        while (i < GLOBALS.length) {
            System.arraycopy(GLOBALS[i], 0, gSaved[i], 0, GLOBALS[i].length);
            ++i;
        }
        System.arraycopy(Gfx.back.px, 0, backSaved, 0, backSaved.length);
        System.arraycopy(Gfx.scr.px, 0, scrSaved, 0, scrSaved.length);
        Interp.saveSprites();
        infoSaved = MainGameFrame.infoMonster;
        statusFrame = GMenuStatus.drawFrame;
        penFg = Prim.fg;
        penFont = Hf.fontColor;
        penMode = Blit.dMode;
        titleCnt = TitleFrame.frameCnt;
        semicolon = Hf.semicolon;
        pArr = Particles.particles;
        if (pArr != null) {
            if (pSaved == null || pSaved.length != pArr.length) {
                pSaved = new byte[pArr.length];
            }
            System.arraycopy(pArr, 0, pSaved, 0, pArr.length);
        }
        pCount = Particles.curCount;
        pMax = Particles.maxCount;
        Rng.saveState(rngBuf);
    }

    private static void restoreAll() {
        int i = 0;
        while (i < POOLS.length) {
            Obj[] objs = Pool.pools[POOLS[i]];
            int j = 0;
            while (j < objs.length) {
                System.arraycopy(saved[i][j], 0, objs[j].m, 0, objs[j].m.length);
                ++j;
            }
            ++i;
        }
        i = 0;
        while (i < GLOBALS.length) {
            System.arraycopy(gSaved[i], 0, GLOBALS[i], 0, GLOBALS[i].length);
            ++i;
        }
        Rng.loadState(rngBuf);
        Interp.restoreSprites();
        MainGameFrame.infoMonster = infoSaved;
        GMenuStatus.drawFrame = statusFrame;
        Prim.fg = penFg;
        Hf.fontColor = penFont;
        Blit.dMode = penMode;
        TitleFrame.frameCnt = titleCnt;
        Hf.semicolon = semicolon;
        Particles.particles = pArr;
        if (pArr != null) {
            System.arraycopy(pSaved, 0, pArr, 0, pArr.length);
        }
        Particles.curCount = pCount;
        Particles.maxCount = pMax;
        System.arraycopy(backSaved, 0, Gfx.back.px, 0, backSaved.length);
        System.arraycopy(scrSaved, 0, Gfx.scr.px, 0, scrSaved.length);
    }

    private static int scRead(int k) {
        byte[] a = GLOBALS[SC_ARR[k]];
        int o = SC_OFF[k];
        switch (SC_W[k]) {
            case 1:
                return a[o] & 0xFF;
            case 2:
                return Bytes.s16(a, o);
            default:
                return Bytes.s32(a, o);
        }
    }

    private static void scWrite(int k, int v) {
        byte[] a = GLOBALS[SC_ARR[k]];
        int o = SC_OFF[k];
        switch (SC_W[k]) {
            case 1:
                a[o] = (byte) v;
                break;
            case 2:
                Bytes.w16(a, o, v);
                break;
            default:
                Bytes.w32(a, o, v);
        }
    }

    private static int scGuard(int k) {
        byte[] a = GLOBALS[SC_ARR[k]];
        int g = 0;
        if (SC_G1[k] >= 0) {
            g = a[SC_G1[k]] & 0xFF;
        }
        if (SC_G2[k] >= 0) {
            g = g << 8 | a[SC_G2[k]] & 0xFF;
        }
        if (SC_G3[k] >= 0) {
            g = g << 8 | a[SC_G3[k]] & 0xFF;
        }
        return g;
    }

    // capture the values the coming real render will show; true if any of them will be tweened
    private static boolean scCapture() {
        boolean any = false;
        int k = 0;
        while (k < SCN) {
            scCur[k] = Interp.scRead(k);
            scGCur[k] = Interp.scGuard(k);
            if (scValid && Interp.scLerpable(k)) {
                any = true;
            }
            ++k;
        }
        return any;
    }

    private static boolean scLerpable(int k) {
        int d = scCur[k] - scPrev[k];
        return d != 0 && scGCur[k] == scGPrev[k] && d <= SC_MAXD[k] && d >= -SC_MAXD[k];
    }

    private static void scApply() {
        int k = 0;
        while (k < SCN) {
            if (Interp.scLerpable(k)) {
                Interp.scWrite(k, Interp.mix(scPrev[k], scCur[k]));
            }
            ++k;
        }
    }

    private static void scShift() {
        int k = 0;
        while (k < SCN) {
            scPrev[k] = scCur[k];
            scGPrev[k] = scGCur[k];
            ++k;
        }
        scValid = true;
    }

    private static void ghostFrame(FrameHandler fh, int a) {
        alpha = a;
        int i = 0;
        while (i < POOLS.length) {
            Obj[] objs = Pool.pools[POOLS[i]];
            int j = 0;
            while (j < objs.length) {
                byte[] m = objs[j].m;
                int x = Bytes.s32(m, 8);
                int y = Bytes.s32(m, 12);
                Bytes.w32(m, 8, Interp.lerp(prevX[i][j], x));
                Bytes.w32(m, 12, Interp.lerp(prevY[i][j], y));
                ++j;
            }
            ++i;
        }
        int cx = Bytes.s32(G.map, 156);
        int cy = Bytes.s32(G.map, 160);
        Bytes.w32(G.map, 156, Interp.lerp(prevCamX, cx));
        Bytes.w32(G.map, 160, Interp.lerp(prevCamY, cy));
        Interp.scApply();
        int n = lgN[lgW];
        i = 0;
        while (i < n) {
            lgUsed[i] = false;
            ++i;
        }
        ghost = true;
        Fx.ghostBegin();
        try {
            fh.render();
        } finally {
            ghost = false;
            tw = false;
            Fx.ghostEnd();
        }
    }

    // session 2: screens whose render() only draws (or whose side effects saveAll covers)
    private static boolean eligible(FrameHandler fh) {
        if (fh instanceof MainGameFrame || fh instanceof TitleFrame) {
            return true;
        }
        if (fh instanceof MainMenuFrame) {
            return MainMenuFrame.mode == 0 || MainMenuFrame.mode == 1;
        }
        return false;
    }

    public static void renderBetween(FrameHandler fh) {
        if (!on) {
            return;
        }
        boolean anim = sprAnim;
        boolean sc = Interp.scCapture();
        try {
            if (prevX == null || !camValid || !Interp.eligible(fh)) {
                return;
            }
            Sink s = sink;
            int n = s == null ? 2 : s.slots();
            if (n < 2) {
                return;
            }
            if (s != null && !anim && !sc && !Interp.moved()) {
                return;
            }
            if (shot == null) {
                shot = new Frame(Gfx.scr.w, Gfx.scr.h);
            }
            Interp.saveAll();
            try {
                int i = 1;
                while (i < n) {
                    if (s == null || !s.late(i)) {
                        Interp.ghostFrame(fh, 256 * i / n);
                        if (s != null) {
                            System.arraycopy(Gfx.scr.px, 0, shot.px, 0, shot.px.length);
                        }
                        Interp.restoreAll();
                        if (s != null) {
                            s.show(shot, i);
                        }
                    }
                    ++i;
                }
            } finally {
                Interp.restoreAll();
            }
        } finally {
            Interp.scShift();
            ++serial;
            lgW ^= 1;
            lgN[lgW] = 0;
            sprAnim = false;
        }
    }

    /*
     * session 2: hook in Sprite.drawNpc right before the frame data is selected (anim, direction
     * row, validated frame index, screen position, flip inputs); returns the frame to draw.
     * Real render: logs the draw, returns the frame unchanged. Ghost render: looks up the same
     * sprite/animation/row drawn last tick nearest to this position; when the frame advanced by
     * one (or wrapped to 0) and both frames have the same cell layout, cell offsets are tweened
     * (cellX/cellY); any other frame change is shown at the nearest tick (old frame for
     * alpha < 1/2), like the original 1-tick step but never ahead of time.
     */
    public static int sprBegin(Sprite s, int anim, int dir, int frame, int x, int y, int n8, int n4, int n3) {
        tw = false;
        if (!on) {
            return frame;
        }
        int f = (byte) frame;
        int key = (n8 == 1 || n4 == n3 ? 0x1000000 : 0) | (anim & 0xFF) << 16 | (dir & 0xFF) << 8;
        if (!ghost) {
            int w = lgW;
            int n = lgN[w];
            if (n < LOGN) {
                lgSpr[w][n] = s;
                lgKey[w][n] = key | f & 0xFF;
                lgX[w][n] = x;
                lgY[w][n] = y;
                lgN[w] = n + 1;
            }
            if (!sprAnim && f >= 0) {
                int r = w ^ 1;
                int m = lgN[r];
                int i = 0;
                while (i < m) {
                    if (lgSpr[r][i] == s && (lgKey[r][i] & ~0xFF) == key && (lgKey[r][i] & 0xFF) != (f & 0xFF)) {
                        sprAnim = true;
                        break;
                    }
                    ++i;
                }
            }
            return frame;
        }
        int r = lgW;
        int m = lgN[r];
        int best = -1;
        int bestD = MATCH + 1;
        int i = 0;
        while (i < m) {
            if (!lgUsed[i] && lgSpr[r][i] == s && (lgKey[r][i] & ~0xFF) == key) {
                int dx = lgX[r][i] - x;
                int dy = lgY[r][i] - y;
                int d = (dx < 0 ? -dx : dx) + (dy < 0 ? -dy : dy);
                if (d < bestD) {
                    bestD = d;
                    best = i;
                }
            }
            ++i;
        }
        if (best < 0) {
            return frame;
        }
        lgUsed[best] = true;
        int pf = lgKey[r][best] & 0xFF;
        if (f < 0 || pf == f) {
            return frame;
        }
        Cif c = s.cif;
        if (anim < 0 || anim >= c.frameStart.length) {
            return frame;
        }
        int[][] rows = c.frameStart[anim];
        if (dir < 0 || dir >= rows.length) {
            return frame;
        }
        int[] fs = rows[dir];
        if (f >= fs.length || pf >= fs.length) {
            return frame;
        }
        if ((pf + 1 == f || f == 0 && pf == fs.length - 1 && pf > 0) && Interp.tweenSetup(c, fs[pf], fs[f])) {
            return frame;
        }
        return alpha < 128 ? pf : frame;
    }

    private static boolean tweenSetup(Cif c, int po, int co) {
        byte[] d = c.data;
        int n = d[po];
        if (n <= 0 || n != d[co] || n > MAXCELL) {
            return false;
        }
        int step = c.mode == 2 ? 5 : 4;
        int p = po + 1;
        int q = co + 1;
        int i = 0;
        while (i < n) {
            int pb = d[p] & 0xFF;
            int qb = d[q] & 0xFF;
            twOk[i] = pb != 127 && qb != 127 && (pb & 0x9F) == (qb & 0x9F);
            if (twOk[i]) {
                if (c.mode == 2) {
                    int b2 = d[p + 2] & 0xFF;
                    int b3 = d[p + 3] & 0xFF;
                    int b4 = d[p + 4] & 0xFF;
                    twX[i] = (short) ((b2 << 4 | b3 >> 4 | ((b2 & 0x80) != 0 ? 61440 : 0)) & 0xFFFF);
                    twY[i] = (short) ((b4 | (b3 & 0xF) << 8 | ((b3 & 8) != 0 ? 61440 : 0)) & 0xFFFF);
                } else {
                    twX[i] = d[p + 2];
                    twY[i] = d[p + 3];
                }
            }
            p += pb == 127 ? step : ((pb & 0x80) != 0 ? step + 5 : step);
            q += qb == 127 ? step : ((qb & 0x80) != 0 ? step + 5 : step);
            ++i;
        }
        twN = n;
        tw = true;
        return true;
    }

    // round to nearest, symmetric for both signs (1 px steps switch at the half tick)
    private static int mix(int from, int to) {
        return from + ((to - from) * alpha + 128 >> 8);
    }

    // session 2: hooks after Sprite.drawNpc decoded the offset of cell i (before flipping)
    public static int cellX(int i, int v) {
        if (!tw || i >= twN || !twOk[i]) {
            return v;
        }
        int d = v - twX[i];
        if (d > CELLJUMP || d < -CELLJUMP) {
            return v;
        }
        return Interp.mix(twX[i], v);
    }

    public static int cellY(int i, int v) {
        if (!tw || i >= twN || !twOk[i]) {
            return v;
        }
        int d = v - twY[i];
        if (d > CELLJUMP || d < -CELLJUMP) {
            return v;
        }
        return Interp.mix(twY[i], v);
    }

    // session 2: hook on a drawn value; real render records it, ghost render mixes it with the
    // value of the previous real render (only if that render drew it and the step is small)
    public static int lp(int id, int v) {
        if (!on) {
            return v;
        }
        if (!ghost) {
            lpVal[id] = v;
            lpSer[id] = serial;
            return v;
        }
        if (lpSer[id] != serial) {
            return v;
        }
        int p = lpVal[id];
        int d = v - p;
        if (d > LPJUMP || d < -LPJUMP) {
            return v;
        }
        return Interp.mix(p, v);
    }

    // session 2: HeroDraw.particleProcess in a ghost frame (particleStart = time of the last real step)
    public static void ghostParticles(long start) {
        if (Particles.sets == null) {
            return;
        }
        long l = Clock.now() - start;
        if (l < 0L) {
            l = 0L;
        }
        Particles.onIdle(l);
    }

    // Effects.draw stage table (same values as the original), result in o
    private static void effStage(int type, int c, int[] o) {
        int s = c > 5 ? 100 : 200;
        int a;
        if (c == 6) {
            s = 160;
            a = 255;
        } else if (c == 7) {
            s = 120;
            a = 204;
        } else if (c == 8) {
            a = 153;
        } else if (c == 9) {
            a = 102;
        } else if (c == 10) {
            a = 51;
        } else {
            a = c == 11 ? 0 : 255;
        }
        if (type == 4 || type == 0) {
            s = 100;
        }
        o[0] = s;
        o[1] = s;
        o[2] = a;
        o[3] = c > 5 ? c - 4 : 0;
    }

    /*
     * session 2: Effects.draw in a ghost frame. The counter m[34] advances once per real render
     * (after drawing), so the previous real render drew m[34] - 1: scale, alpha and drift are
     * mixed between the two stages, the digit reveal switches at the half tick. Same drawing
     * code as Effects.drawNumber otherwise.
     */
    public static void drawEffect(Obj obj, int x, int y) {
        byte[] m = obj.m;
        int c = m[34];
        int type = m[32];
        if (type != 0 && type != 1 && type != 4 && type != 5) {
            return;
        }
        if (Bytes.s32(m, 36) < 0) {
            return;
        }
        Interp.effStage(type, c, effB);
        int by = c;
        if (c > 0) {
            Interp.effStage(type, c - 1, effA);
            if (alpha < 128) {
                by = c - 1;
            }
        } else {
            Interp.effStage(type, c, effA);
        }
        int sx = Interp.mix(effA[0], effB[0]);
        int sy = Interp.mix(effA[1], effB[1]);
        int a = Interp.mix(effA[2], effB[2]);
        int off = Interp.mix(effA[3], effB[3]);
        int yy = y - (type == 4 || type == 0 ? 28 : 30);
        boolean hero = type == 4 || type == 5;
        Gbm g9 = Icons.damageNumber[9];
        int step = ((short) g9.w - 3) * sx / 100;
        int n = m[54];
        int xx = x - n * step / 2;
        int first = 0;
        if (by < n) {
            first = n - (by + 1);
        }
        int base = xx + step / 2;
        int lift = 0;
        Frame f = Gfx.scr;
        int i = n - 1;
        while (i >= first) {
            if (by < n && i == first) {
                lift = -1;
            }
            Gbm g = (hero ? Icons.heroDamageNumber : Icons.damageNumber)[m[44 + i]];
            Fx.drawImgRotateEx(f, base + step * (n - 1 - i), yy + lift + off, (short) g.aux, (short) g.h, g, 0, 0, 0, 0, sx, sy, 0, a, 17);
            Fx.restorePalette(g);
            --i;
        }
    }

    public static interface Sink {
        public int slots();

        public boolean late(int n);

        public void show(Frame f, int n);
    }
}
