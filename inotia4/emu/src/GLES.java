import com.github.unidbg.Emulator;
import com.github.unidbg.arm.Arm64Svc;
import com.github.unidbg.arm.context.RegisterContext;
import com.github.unidbg.memory.SvcMemory;
import com.github.unidbg.pointer.UnidbgPointer;
import com.github.unidbg.virtualmodule.VirtualModule;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**
 * libGLESv1_CM.so ao cho unidbg: rasterizer phan mem toi gian du cho game 2D cua Com2uS (SGL).
 * Ho tro: ma tran (fixed/float), viewport, scissor, clear, blend, alpha test, texture 2D
 * (RGBA/RGB/ALPHA/LUMINANCE[_ALPHA], 8888/565/4444/5551), mau dinh, client array,
 * DrawArrays/DrawElements (TRIANGLES/STRIP/FAN). Lay mau texture NEAREST.
 * Muc dich: chup man hinh de soat chu/bo cuc, KHONG phai GPU chinh xac.
 */
public class GLES extends VirtualModule<Void> {

    // ---- hang so GL can dung
    static final int GL_TEXTURE_2D = 0x0DE1, GL_BLEND = 0x0BE2, GL_ALPHA_TEST = 0x0BC0, GL_SCISSOR_TEST = 0x0C11;
    static final int GL_VERTEX_ARRAY = 0x8074, GL_COLOR_ARRAY = 0x8076, GL_TEXTURE_COORD_ARRAY = 0x8078;
    static final int GL_MODELVIEW = 0x1700, GL_PROJECTION = 0x1701, GL_TEXTURE = 0x1702;
    static final int GL_BYTE = 0x1400, GL_UNSIGNED_BYTE = 0x1401, GL_SHORT = 0x1402, GL_UNSIGNED_SHORT = 0x1403, GL_FLOAT = 0x1406, GL_FIXED = 0x140C;
    static final int GL_ALPHA = 0x1906, GL_RGB = 0x1907, GL_RGBA = 0x1908, GL_LUMINANCE = 0x1909, GL_LUMINANCE_ALPHA = 0x190A;
    static final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033, GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034, GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
    static final int GL_TRIANGLES = 4, GL_TRIANGLE_STRIP = 5, GL_TRIANGLE_FAN = 6;
    static final int GL_ZERO = 0, GL_ONE = 1, GL_SRC_COLOR = 0x300, GL_ONE_MINUS_SRC_COLOR = 0x301, GL_SRC_ALPHA = 0x302,
            GL_ONE_MINUS_SRC_ALPHA = 0x303, GL_DST_ALPHA = 0x304, GL_ONE_MINUS_DST_ALPHA = 0x305, GL_DST_COLOR = 0x306, GL_ONE_MINUS_DST_COLOR = 0x307;
    static final int GL_TEXTURE_ENV_MODE = 0x2200, GL_MODULATE = 0x2100, GL_REPLACE = 0x1E01, GL_DECAL = 0x2101, GL_ADD = 0x0104;

    final Map<String, Integer> calls = new TreeMap<>();
    final Set<String> unhandled = new TreeSet<>();
    Emulator<?> emu;

    // ---- trang thai
    int fbW = 1280, fbH = 720;
    int[] fb = new int[fbW * fbH];
    int vpX, vpY, vpW = fbW, vpH = fbH;
    int scX, scY, scW = fbW, scH = fbH;
    float[] clear = {0, 0, 0, 1};
    final Set<Integer> enabled = new HashSet<>();
    final Set<Integer> clientOn = new HashSet<>();
    int blendS = GL_ONE, blendD = GL_ZERO;
    int alphaFunc = 0x0207; float alphaRef = 0;
    float[] color = {1, 1, 1, 1};
    int texEnv = GL_MODULATE;
    int matMode = GL_MODELVIEW;
    final Deque<float[]> mv = new ArrayDeque<>(), pj = new ArrayDeque<>(), tx = new ArrayDeque<>();
    static class Arr { int size, type, stride; long ptr; }
    final Arr vArr = new Arr(), cArr = new Arr(), tArr = new Arr();
    static class Tex { int w, h; int[] px; }
    final Map<Integer, Tex> tex = new HashMap<>();
    int nextTex = 1, boundTex = 0;
    int drawCalls, frameNo;

    public GLES(Emulator<?> emulator) {
        super(emulator, "libGLESv1_CM.so");
        this.emu = emulator;
        mv.push(ident()); pj.push(ident()); tx.push(ident());
    }

    static float[] ident() { return new float[]{1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1}; }
    Deque<float[]> stack() { return matMode == GL_PROJECTION ? pj : matMode == GL_TEXTURE ? tx : mv; }
    float[] cur() { return stack().peek(); }
    static float[] mul(float[] a, float[] b) { // column-major a*b
        float[] r = new float[16];
        for (int c = 0; c < 4; c++) for (int rr = 0; rr < 4; rr++) {
            float s = 0;
            for (int k = 0; k < 4; k++) s += a[k * 4 + rr] * b[c * 4 + k];
            r[c * 4 + rr] = s;
        }
        return r;
    }
    void multCur(float[] m) { float[] c = stack().pop(); stack().push(mul(c, m)); }
    static float fx(int v) { return v / 65536f; }

    public void resize(int w, int h) {
        fbW = w; fbH = h; fb = new int[w * h];
        vpW = w; vpH = h; scW = w; scH = h;
    }

    @Override
    protected void onInitialize(Emulator<?> emulator, Void extra, Map<String, UnidbgPointer> symbols) {
        this.emu = emulator;
        SvcMemory svc = emulator.getSvcMemory();
        String[] names = ("glActiveTexture glAlphaFunc glBindTexture glBlendFunc glClear glClearColor glClearColorx glClientActiveTexture "
                + "glColor4x glColorPointer glCompressedTexImage2D glCopyTexImage2D glCopyTexSubImage2D glDeleteTextures glDepthMask glDisable "
                + "glDisableClientState glDrawArrays glDrawElements glEnable glEnableClientState glFinish glFrustumx glGenTextures glGetError "
                + "glGetFixedv glGetIntegerv glGetPointerv glIsEnabled glLightxv glLineWidthx glLoadIdentity glLoadMatrixf glLoadMatrixx "
                + "glMatrixMode glMultMatrixx glNormalPointer glOrthox glPointSizex glPopMatrix glPushMatrix glRotatex glScalex glScissor "
                + "glShadeModel glStencilMask glTexCoordPointer glTexEnvf glTexEnvx glTexEnvxv glTexImage2D glTexParameterf glTexParameteri "
                + "glTexParameterx glTexSubImage2D glTranslatex glVertexPointer glViewport glColor4f glColor4ub glOrthof glTranslatef glScalef "
                + "glRotatef glFlush glHint glPixelStorei glDepthFunc glCullFace glFrontFace glReadPixels glTexEnvi").split(" ");
        for (final String n : names) {
            symbols.put(n, svc.registerSvc(new Arm64Svc() {
                @Override
                public long handle(Emulator<?> e) {
                    Integer c = calls.get(n);
                    calls.put(n, c == null ? 1 : c + 1);
                    try {
                        return call(n, e.getContext());
                    } catch (RuntimeException ex) {
                        System.out.println("[GL!] " + n + ": " + ex);
                        return 0;
                    }
                }
            }));
        }
    }

    float farg(RegisterContext c, int i) {
        // doi so float nam o s0..s7 (AAPCS64)
        byte[] v = emu.getBackend().reg_read_vector(unicorn.Arm64Const.UC_ARM64_REG_Q0 + i);
        return Float.intBitsToFloat((v[0] & 0xff) | (v[1] & 0xff) << 8 | (v[2] & 0xff) << 16 | (v[3] & 0xff) << 24);
    }

    long call(String n, RegisterContext c) {
        switch (n) {
            case "glGenTextures": {
                int cnt = c.getIntArg(0); UnidbgPointer p = c.getPointerArg(1);
                for (int i = 0; i < cnt; i++) { tex.put(nextTex, new Tex()); p.setInt(i * 4L, nextTex++); }
                return 0;
            }
            case "glDeleteTextures": {
                int cnt = c.getIntArg(0); UnidbgPointer p = c.getPointerArg(1);
                for (int i = 0; i < cnt; i++) tex.remove(p.getInt(i * 4L));
                return 0;
            }
            case "glBindTexture": boundTex = c.getIntArg(1); if (!tex.containsKey(boundTex)) tex.put(boundTex, new Tex()); return 0;
            case "glTexImage2D": texImage(c, false); return 0;
            case "glTexSubImage2D": texImage(c, true); return 0;
            case "glEnable": enabled.add(c.getIntArg(0)); return 0;
            case "glDisable": enabled.remove(c.getIntArg(0)); return 0;
            case "glIsEnabled": return enabled.contains(c.getIntArg(0)) ? 1 : 0;
            case "glEnableClientState": clientOn.add(c.getIntArg(0)); return 0;
            case "glDisableClientState": clientOn.remove(c.getIntArg(0)); return 0;
            case "glBlendFunc": blendS = c.getIntArg(0); blendD = c.getIntArg(1); return 0;
            case "glAlphaFunc": alphaFunc = c.getIntArg(0); alphaRef = farg(c, 0); return 0;
            case "glColor4x": for (int i = 0; i < 4; i++) color[i] = fx(c.getIntArg(i)); return 0;
            case "glColor4f": for (int i = 0; i < 4; i++) color[i] = farg(c, i); return 0;
            case "glColor4ub": for (int i = 0; i < 4; i++) color[i] = (c.getIntArg(i) & 0xff) / 255f; return 0;
            case "glClearColorx": for (int i = 0; i < 4; i++) clear[i] = fx(c.getIntArg(i)); return 0;
            case "glClearColor": for (int i = 0; i < 4; i++) clear[i] = farg(c, i); return 0;
            case "glClear": doClear(); return 0;
            case "glViewport": vpX = c.getIntArg(0); vpY = c.getIntArg(1); vpW = c.getIntArg(2); vpH = c.getIntArg(3); return 0;
            case "glScissor": scX = c.getIntArg(0); scY = c.getIntArg(1); scW = c.getIntArg(2); scH = c.getIntArg(3); return 0;
            case "glMatrixMode": matMode = c.getIntArg(0); return 0;
            case "glLoadIdentity": stack().pop(); stack().push(ident()); return 0;
            case "glPushMatrix": stack().push(cur().clone()); return 0;
            case "glPopMatrix": if (stack().size() > 1) stack().pop(); return 0;
            case "glLoadMatrixx": { UnidbgPointer p = c.getPointerArg(0); float[] m = new float[16]; for (int i = 0; i < 16; i++) m[i] = fx(p.getInt(i * 4L)); stack().pop(); stack().push(m); return 0; }
            case "glLoadMatrixf": { UnidbgPointer p = c.getPointerArg(0); float[] m = new float[16]; for (int i = 0; i < 16; i++) m[i] = p.getFloat(i * 4L); stack().pop(); stack().push(m); return 0; }
            case "glMultMatrixx": { UnidbgPointer p = c.getPointerArg(0); float[] m = new float[16]; for (int i = 0; i < 16; i++) m[i] = fx(p.getInt(i * 4L)); multCur(m); return 0; }
            case "glOrthox": ortho(fx(c.getIntArg(0)), fx(c.getIntArg(1)), fx(c.getIntArg(2)), fx(c.getIntArg(3)), fx(c.getIntArg(4)), fx(c.getIntArg(5))); return 0;
            case "glOrthof": ortho(farg(c, 0), farg(c, 1), farg(c, 2), farg(c, 3), farg(c, 4), farg(c, 5)); return 0;
            case "glTranslatex": translate(fx(c.getIntArg(0)), fx(c.getIntArg(1)), fx(c.getIntArg(2))); return 0;
            case "glTranslatef": translate(farg(c, 0), farg(c, 1), farg(c, 2)); return 0;
            case "glScalex": scale(fx(c.getIntArg(0)), fx(c.getIntArg(1)), fx(c.getIntArg(2))); return 0;
            case "glScalef": scale(farg(c, 0), farg(c, 1), farg(c, 2)); return 0;
            case "glRotatex": rotate(fx(c.getIntArg(0)), fx(c.getIntArg(1)), fx(c.getIntArg(2)), fx(c.getIntArg(3))); return 0;
            case "glRotatef": rotate(farg(c, 0), farg(c, 1), farg(c, 2), farg(c, 3)); return 0;
            case "glVertexPointer": setArr(vArr, c); return 0;
            case "glColorPointer": setArr(cArr, c); return 0;
            case "glTexCoordPointer": setArr(tArr, c); return 0;
            case "glTexEnvx": case "glTexEnvi": if (c.getIntArg(1) == GL_TEXTURE_ENV_MODE) texEnv = c.getIntArg(2); return 0;
            case "glTexEnvf": if (c.getIntArg(1) == GL_TEXTURE_ENV_MODE) texEnv = (int) farg(c, 0); return 0;
            case "glDrawArrays": draw(c.getIntArg(0), c.getIntArg(1), c.getIntArg(2), 0, 0); return 0;
            case "glDrawElements": draw(c.getIntArg(0), 0, c.getIntArg(1), c.getIntArg(2), c.getPointerArg(3) == null ? 0 : c.getPointerArg(3).peer); return 0;
            case "glGetError": return 0;
            case "glGetIntegerv": {
                int pn = c.getIntArg(0); UnidbgPointer p = c.getPointerArg(1);
                int v = pn == 0x0D33 ? 2048 : pn == 0x0BA2 ? 0 : pn == 0x8069 ? boundTex : 0; // MAX_TEXTURE_SIZE, VIEWPORT
                if (pn == 0x0BA2) { p.setInt(0, vpX); p.setInt(4, vpY); p.setInt(8, vpW); p.setInt(12, vpH); }
                else p.setInt(0, v);
                return 0;
            }
            default:
                unhandled.add(n);
                return 0;
        }
    }

    void setArr(Arr a, RegisterContext c) {
        a.size = c.getIntArg(0); a.type = c.getIntArg(1); a.stride = c.getIntArg(2);
        UnidbgPointer p = c.getPointerArg(3); a.ptr = p == null ? 0 : p.peer;
    }

    void ortho(float l, float r, float b, float t, float n, float f) {
        float[] m = new float[16];
        m[0] = 2 / (r - l); m[5] = 2 / (t - b); m[10] = -2 / (f - n);
        m[12] = -(r + l) / (r - l); m[13] = -(t + b) / (t - b); m[14] = -(f + n) / (f - n); m[15] = 1;
        multCur(m);
    }
    void translate(float x, float y, float z) { float[] m = ident(); m[12] = x; m[13] = y; m[14] = z; multCur(m); }
    void scale(float x, float y, float z) { float[] m = ident(); m[0] = x; m[5] = y; m[10] = z; multCur(m); }
    void rotate(float a, float x, float y, float z) {
        double r = Math.toRadians(a); float cs = (float) Math.cos(r), sn = (float) Math.sin(r);
        float l = (float) Math.sqrt(x * x + y * y + z * z); if (l == 0) return; x /= l; y /= l; z /= l;
        float[] m = ident(); float ic = 1 - cs;
        m[0] = x * x * ic + cs; m[4] = x * y * ic - z * sn; m[8] = x * z * ic + y * sn;
        m[1] = y * x * ic + z * sn; m[5] = y * y * ic + cs; m[9] = y * z * ic - x * sn;
        m[2] = x * z * ic - y * sn; m[6] = y * z * ic + x * sn; m[10] = z * z * ic + cs;
        multCur(m);
    }

    void doClear() {
        int v = argb(clear[0], clear[1], clear[2], clear[3]);
        if (enabled.contains(GL_SCISSOR_TEST)) {
            for (int y = Math.max(0, scY); y < Math.min(fbH, scY + scH); y++)
                for (int x = Math.max(0, scX); x < Math.min(fbW, scX + scW); x++) fb[(fbH - 1 - y) * fbW + x] = v;
        } else Arrays.fill(fb, v);
    }

    static int argb(float r, float g, float b, float a) {
        return (cl(a) << 24) | (cl(r) << 16) | (cl(g) << 8) | cl(b);
    }
    static int cl(float v) { int i = Math.round(v * 255); return i < 0 ? 0 : i > 255 ? 255 : i; }

    void texImage(RegisterContext c, boolean sub) {
        int level = c.getIntArg(1);
        if (level != 0) return;
        Tex t = tex.computeIfAbsent(boundTex, k -> new Tex());
        int x0, y0, w, h, fmt, type; UnidbgPointer p;
        p = UnidbgPointer.pointer(emu, emu.getContext().getStackPointer().getLong(0)); // doi so thu 9 (pixels) tren stack
        if (sub) { x0 = c.getIntArg(2); y0 = c.getIntArg(3); w = c.getIntArg(4); h = c.getIntArg(5); fmt = c.getIntArg(6); type = c.getIntArg(7); }
        else {
            x0 = 0; y0 = 0; w = c.getIntArg(3); h = c.getIntArg(4); fmt = c.getIntArg(6); type = c.getIntArg(7);
            t.w = w; t.h = h; t.px = new int[w * h];
        }
        if (p == null || t.px == null) return;
        int bpp = type == GL_UNSIGNED_BYTE ? (fmt == GL_RGBA ? 4 : fmt == GL_RGB ? 3 : fmt == GL_LUMINANCE_ALPHA ? 2 : 1) : 2;
        byte[] data = p.getByteArray(0, w * h * bpp);
        for (int y = 0; y < h; y++) for (int x = 0; x < w; x++) {
            int i = (y * w + x) * bpp, v;
            if (type == GL_UNSIGNED_BYTE) {
                switch (fmt) {
                    case GL_RGBA: v = (data[i + 3] & 0xff) << 24 | (data[i] & 0xff) << 16 | (data[i + 1] & 0xff) << 8 | (data[i + 2] & 0xff); break;
                    case GL_RGB: v = 0xff000000 | (data[i] & 0xff) << 16 | (data[i + 1] & 0xff) << 8 | (data[i + 2] & 0xff); break;
                    case GL_ALPHA: v = (data[i] & 0xff) << 24 | 0xffffff; break;
                    case GL_LUMINANCE: { int l = data[i] & 0xff; v = 0xff000000 | l << 16 | l << 8 | l; break; }
                    default: { int l = data[i] & 0xff; v = (data[i + 1] & 0xff) << 24 | l << 16 | l << 8 | l; }
                }
            } else {
                int s = (data[i] & 0xff) | (data[i + 1] & 0xff) << 8;
                if (type == GL_UNSIGNED_SHORT_5_6_5) v = 0xff000000 | ((s >> 11) * 255 / 31) << 16 | (((s >> 5) & 63) * 255 / 63) << 8 | ((s & 31) * 255 / 31);
                else if (type == GL_UNSIGNED_SHORT_4_4_4_4) v = ((s & 15) * 17) << 24 | ((s >> 12) * 17) << 16 | (((s >> 8) & 15) * 17) << 8 | ((s >> 4) & 15) * 17;
                else v = ((s & 1) * 255) << 24 | ((s >> 11) * 255 / 31) << 16 | (((s >> 6) & 31) * 255 / 31) << 8 | (((s >> 1) & 31) * 255 / 31);
            }
            int tx2 = x0 + x, ty2 = y0 + y;
            if (tx2 < t.w && ty2 < t.h) t.px[ty2 * t.w + tx2] = v;
        }
    }

    float[] fetch(Arr a, int i, int def) {
        float[] r = new float[4]; r[3] = 1;
        int es = a.type == GL_FLOAT || a.type == GL_FIXED ? 4 : a.type == GL_SHORT || a.type == GL_UNSIGNED_SHORT ? 2 : 1;
        int stride = a.stride != 0 ? a.stride : es * a.size;
        UnidbgPointer p = UnidbgPointer.pointer(emu, a.ptr + (long) i * stride);
        for (int k = 0; k < a.size; k++) {
            long o = (long) k * es;
            switch (a.type) {
                case GL_FLOAT: r[k] = p.getFloat(o); break;
                case GL_FIXED: r[k] = fx(p.getInt(o)); break;
                case GL_SHORT: r[k] = p.getShort(o); break;
                case GL_UNSIGNED_SHORT: r[k] = p.getShort(o) & 0xffff; break;
                case GL_BYTE: r[k] = p.getByte(o); break;
                default: r[k] = (p.getByte(o) & 0xff) / (a == cArr ? 255f : 1f);
            }
        }
        return r;
    }

    void draw(int mode, int first, int count, int idxType, long idxPtr) {
        if (!clientOn.contains(GL_VERTEX_ARRAY) || vArr.ptr == 0 || count <= 0) return;
        drawCalls++;
        float[] mvp = mul(pj.peek(), mv.peek());
        int n = count;
        float[][] sx = new float[n][]; // x,y, u,v, r,g,b,a
        for (int k = 0; k < n; k++) {
            int i = first + k;
            if (idxPtr != 0) {
                UnidbgPointer ip = UnidbgPointer.pointer(emu, idxPtr);
                i = idxType == GL_UNSIGNED_SHORT ? ip.getShort(k * 2L) & 0xffff : ip.getByte(k) & 0xff;
            }
            float[] v = fetch(vArr, i, 0);
            if (vArr.size < 3) v[2] = 0;
            float cx = mvp[0] * v[0] + mvp[4] * v[1] + mvp[8] * v[2] + mvp[12];
            float cy = mvp[1] * v[0] + mvp[5] * v[1] + mvp[9] * v[2] + mvp[13];
            float cw = mvp[3] * v[0] + mvp[7] * v[1] + mvp[11] * v[2] + mvp[15];
            if (cw == 0) cw = 1;
            float wx = vpX + (cx / cw + 1) * vpW / 2f, wy = vpY + (cy / cw + 1) * vpH / 2f;
            float[] o = new float[8];
            o[0] = wx; o[1] = fbH - wy;
            if (clientOn.contains(GL_TEXTURE_COORD_ARRAY) && tArr.ptr != 0) {
                float[] t = fetch(tArr, i, 0);
                float[] tm = tx.peek();
                o[2] = tm[0] * t[0] + tm[4] * t[1] + tm[12]; o[3] = tm[1] * t[0] + tm[5] * t[1] + tm[13];
            }
            if (clientOn.contains(GL_COLOR_ARRAY) && cArr.ptr != 0) { float[] cc = fetch(cArr, i, 0); System.arraycopy(cc, 0, o, 4, 4); }
            else System.arraycopy(color, 0, o, 4, 4);
            sx[k] = o;
        }
        if (mode == GL_TRIANGLES) for (int k = 0; k + 2 < n; k += 3) tri(sx[k], sx[k + 1], sx[k + 2]);
        else if (mode == GL_TRIANGLE_STRIP) for (int k = 0; k + 2 < n; k++) tri(sx[k], sx[k + 1], sx[k + 2]);
        else if (mode == GL_TRIANGLE_FAN) for (int k = 1; k + 1 < n; k++) tri(sx[0], sx[k], sx[k + 1]);
    }

    void tri(float[] a, float[] b, float[] c) {
        float area = (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]);
        if (Math.abs(area) < 1e-6) return;
        int minX = (int) Math.floor(Math.min(a[0], Math.min(b[0], c[0]))), maxX = (int) Math.ceil(Math.max(a[0], Math.max(b[0], c[0])));
        int minY = (int) Math.floor(Math.min(a[1], Math.min(b[1], c[1]))), maxY = (int) Math.ceil(Math.max(a[1], Math.max(b[1], c[1])));
        int cx0 = 0, cy0 = 0, cx1 = fbW, cy1 = fbH;
        if (enabled.contains(GL_SCISSOR_TEST)) { cx0 = scX; cx1 = scX + scW; cy0 = fbH - (scY + scH); cy1 = fbH - scY; }
        minX = Math.max(minX, cx0); minY = Math.max(minY, cy0); maxX = Math.min(maxX, cx1); maxY = Math.min(maxY, cy1);
        Tex t = enabled.contains(GL_TEXTURE_2D) ? tex.get(boundTex) : null;
        if (t != null && t.px == null) t = null;
        boolean blend = enabled.contains(GL_BLEND), atest = enabled.contains(GL_ALPHA_TEST);
        for (int y = minY; y < maxY; y++) {
            float py = y + 0.5f;
            for (int x = minX; x < maxX; x++) {
                float px = x + 0.5f;
                float w0 = ((b[0] - px) * (c[1] - py) - (b[1] - py) * (c[0] - px)) / area;
                float w1 = ((c[0] - px) * (a[1] - py) - (c[1] - py) * (a[0] - px)) / area;
                float w2 = 1 - w0 - w1;
                if (w0 < -1e-4 || w1 < -1e-4 || w2 < -1e-4) continue;
                float r = w0 * a[4] + w1 * b[4] + w2 * c[4], g = w0 * a[5] + w1 * b[5] + w2 * c[5];
                float bl = w0 * a[6] + w1 * b[6] + w2 * c[6], al = w0 * a[7] + w1 * b[7] + w2 * c[7];
                if (t != null) {
                    float u = w0 * a[2] + w1 * b[2] + w2 * c[2], v = w0 * a[3] + w1 * b[3] + w2 * c[3];
                    int tu = (int) Math.floor(u * t.w), tv = (int) Math.floor(v * t.h);
                    tu = Math.max(0, Math.min(t.w - 1, tu)); tv = Math.max(0, Math.min(t.h - 1, tv));
                    int s = t.px[tv * t.w + tu];
                    float sa = (s >>> 24) / 255f, sr = ((s >> 16) & 255) / 255f, sg = ((s >> 8) & 255) / 255f, sb = (s & 255) / 255f;
                    if (texEnv == GL_REPLACE) { r = sr; g = sg; bl = sb; al = sa; }
                    else if (texEnv == GL_ADD) { r = Math.min(1, r + sr); g = Math.min(1, g + sg); bl = Math.min(1, bl + sb); al *= sa; }
                    else { r *= sr; g *= sg; bl *= sb; al *= sa; }
                }
                if (atest && al <= alphaRef) continue;
                int di = y * fbW + x;
                if (blend) {
                    int d = fb[di];
                    float dr = ((d >> 16) & 255) / 255f, dg = ((d >> 8) & 255) / 255f, db = (d & 255) / 255f, da = (d >>> 24) / 255f;
                    float[] sf = fac(blendS, r, g, bl, al, dr, dg, db, da), df = fac(blendD, r, g, bl, al, dr, dg, db, da);
                    r = r * sf[0] + dr * df[0]; g = g * sf[1] + dg * df[1]; bl = bl * sf[2] + db * df[2]; al = al * sf[3] + da * df[3];
                }
                fb[di] = argb(r, g, bl, al);
            }
        }
    }

    static float[] fac(int f, float r, float g, float b, float a, float dr, float dg, float db, float da) {
        switch (f) {
            case GL_ZERO: return new float[]{0, 0, 0, 0};
            case GL_ONE: return new float[]{1, 1, 1, 1};
            case GL_SRC_ALPHA: return new float[]{a, a, a, a};
            case GL_ONE_MINUS_SRC_ALPHA: return new float[]{1 - a, 1 - a, 1 - a, 1 - a};
            case GL_DST_ALPHA: return new float[]{da, da, da, da};
            case GL_ONE_MINUS_DST_ALPHA: return new float[]{1 - da, 1 - da, 1 - da, 1 - da};
            case GL_SRC_COLOR: return new float[]{r, g, b, a};
            case GL_ONE_MINUS_SRC_COLOR: return new float[]{1 - r, 1 - g, 1 - b, 1 - a};
            case GL_DST_COLOR: return new float[]{dr, dg, db, da};
            case GL_ONE_MINUS_DST_COLOR: return new float[]{1 - dr, 1 - dg, 1 - db, 1 - da};
            default: return new float[]{1, 1, 1, 1};
        }
    }

    public void screenshot(File f) {
        try {
            BufferedImage im = new BufferedImage(fbW, fbH, BufferedImage.TYPE_INT_RGB);
            im.setRGB(0, 0, fbW, fbH, fb, 0, fbW);
            javax.imageio.ImageIO.write(im, "png", f);
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
