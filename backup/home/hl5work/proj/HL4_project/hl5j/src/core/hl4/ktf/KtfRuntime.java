package hl4.ktf;

import hl4.core.Gfx16;
import hl4.core.Host;
import hl4.core.Runtime;
import hl4.cpu.Cpu;
import hl4.cpu.CpuException;
import java.util.Hashtable;

/**
 * KTF WIPI runtime: loads client.bin (AOT-compiled Java classes + native Clet code), provides the
 * Java bridge (a minimal JVM whose objects live in guest memory, KTF layout) and the KTF WIPI-C
 * interface tables.
 */
public class KtfRuntime extends Runtime {
    public static final int IMAGE_BASE = 0x100000;

    static final int CAT_INIT = 0x300;
    static final int CAT_JAVA = 0x302;   // host java method entries (index = method id)
    static final int CAT_WIPIC = 0x310;  // + table id

    public final KJvm jvm;
    int wipiExe, exeInterface, exeFunctions;
    int fnGetClass;
    int jvmContext, threadContext, threadContextHolder;
    int wipicInterfaces;

    public KtfRuntime(Host host) {
        super(host);
        jvm = new KJvm(this);
    }

    // ------------------------------------------------------------------
    // loading

    public void load(byte[] bin, int bssSize) {
        mem.map(IMAGE_BASE, bin.length + bssSize);
        mem.writeBytes(IMAGE_BASE, bin, 0, bin.length);
        wipiExe = cpu.call(IMAGE_BASE | 1, bssSize);
        log("KTF wipi_exe " + hex(wipiExe));
        exeInterface = mem.read32(wipiExe);
        exeFunctions = mem.read32(exeInterface);
        fnGetClass = mem.read32(exeFunctions + 16);

        int param0 = allocStruct(new int[]{0});
        int param3 = allocStruct(new int[]{0, 0, 0, 0, 'Z', 'C', 'F', 'D', 'B', 'S', 'I', 'J'});
        int[] p4 = new int[12];
        p4[0] = stub((CAT_INIT << 16) | 0);   // get_interface
        p4[1] = stub((CAT_INIT << 16) | 1);   // java_throw
        p4[4] = stub((CAT_INIT << 16) | 2);   // check_type
        p4[5] = stub((CAT_INIT << 16) | 3);   // java_new
        p4[6] = stub((CAT_INIT << 16) | 4);   // array_new
        p4[8] = stub((CAT_INIT << 16) | 5);   // class_load
        p4[11] = stub((CAT_INIT << 16) | 6);  // alloc
        int param4 = allocStruct(p4);
        jvmContext = alloc(12 + 128 * 4 + 16);
        mem.fill(jvmContext, 0, 12 + 128 * 4 + 16);
        threadContext = alloc(64);
        mem.fill(threadContext, 0, 64);
        threadContextHolder = allocStruct(new int[]{threadContext});
        int fnInit = mem.read32(exeFunctions + 8);
        int r = cpu.call(fnInit, new int[]{param0, threadContextHolder, jvmContext, param3, param4});
        if (r != 0) throw new CpuException("KTF exe init failed " + hex(r));
        int wipiInit = mem.read32(wipiExe + 20);
        r = cpu.call(wipiInit);
        if (r != 0) throw new CpuException("KTF wipi init failed " + hex(r));
        log("KTF init ok");
        // the game's bitwise DES (resource/save decryption): 16 rounds done on the host
        if (hleDes) hookThumb(DES_ROUNDS, HLE_DES);
    }

    public static boolean hleDes = true;

    static final int DES_ROUNDS = 0x108500, HLE_DES = 16;

    protected void hle(int idx) {
        if (idx == HLE_DES) {
            desRounds(cpu.arg(0), cpu.arg(1));
            return;
        }
        super.hle(idx);
    }

    private int desTable(int litAddr) {
        return mem.read32(mem.read32(mem.read32(GOT + mem.read32(litAddr)))) + 8;
    }

    private final int[] dL = new int[32], dR = new int[32], dE = new int[48], dS = new int[32];
    private byte[] dP, dEt, dSb;

    /** Exact host version of guest function 0x108500(L, R): 16 Feistel rounds on bit arrays (ints). */
    void desRounds(int lp, int rp) {
        int pt = desTable(0x1086a8), et = desTable(0x1086ac), st = desTable(0x1086b0), kt = desTable(0x1086b4);
        if (dP == null) {
            dP = new byte[32];
            dEt = new byte[48];
            dSb = new byte[512];
        }
        mem.readBytes(pt, dP, 0, 32);
        mem.readBytes(et, dEt, 0, 48);
        mem.readBytes(st, dSb, 0, 512);
        for (int i = 0; i < 32; i++) {
            dL[i] = mem.read32(lp + i * 4);
            dR[i] = mem.read32(rp + i * 4);
        }
        for (int round = 0; round < 16; round++) {
            int k = kt + round * 0x30;
            for (int i = 0; i < 48; i++) dE[i] = dR[(dEt[i] & 0xFF) - 1] ^ mem.read8(k + i);
            for (int j = 0; j < 8; j++) {
                int b = j * 6;
                int row = (dE[b] << 1) + dE[b + 5];
                int col = (((((dE[b + 1] << 1) + dE[b + 2]) << 1) + dE[b + 3]) << 1) + dE[b + 4];
                int v = dSb[(((j << 2) + row) << 4) + col] & 0xFF;
                dS[j * 4] = (v >> 3) & 1;
                dS[j * 4 + 1] = (v >> 2) & 1;
                dS[j * 4 + 2] = (v >> 1) & 1;
                dS[j * 4 + 3] = v & 1;
            }
            for (int i = 0; i < 32; i++) {
                int nr = dL[i] ^ dS[(dP[i] & 0xFF) - 1];
                dL[i] = dR[i];
                dR[i] = nr;
            }
        }
        for (int i = 0; i < 32; i++) {
            mem.write32(lp + i * 4, dL[i]);
            mem.write32(rp + i * 4, dR[i]);
        }
    }

    public int vtablesBase() {
        return jvmContext + 12;
    }

    /** Resolve an application class from client.bin (0 if not found). */
    public int nativeClass(String name) {
        byte[] b = ascii(name);
        int p = alloc(b.length + 1);
        mem.writeCString(p, b);
        int c = cpu.call(fnGetClass, p);
        heap.free(p);
        return c;
    }

    public void start(String mainClass) {
        jvm.startMain(mainClass);
    }

    protected String resourcePath(String name) {
        return name.startsWith("/") ? name.substring(1) : name;
    }

    protected byte[] resource(String name) {
        return host.loadResource(resourcePath(name));
    }

    protected void paint() {
        if (jvm.platform != null) jvm.platform.paint();
    }

    protected void dispatchEvent(int type, int p1, int p2) {
        KtfPlatform p = jvm.platform;
        if (p == null) return;
        if (type == CTRL_PAUSE) p.pause();
        else if (type == CTRL_RESUME) p.resume();
        else if (type == CTRL_DESTROY) p.destroy();
        else if (type >= EV_KEY_PRESS && type <= EV_KEY_REPEAT) p.key(type - 501, p1);
        else p.notifyEvent(type, p1, p2);
    }

    // ------------------------------------------------------------------
    // host calls

    static final int GOT = 0x189cb8; // sl of the PIC client code

    /** Install the Vietnamese text engine (call after load, before start). */
    public void installText(hl4.core.VnFont font, String[] dict) {
        text = new hl4.core.TextEngine(this, font, dict) {
            protected void applyColor(int color) {
                int fp = mem.read32(mem.read32(mem.read32(GOT + 0x64)) + 0x18);
                int gctx = mem.read32(GOT + 0x20) + 8;
                cpu.call(fp, new int[]{gctx, 1, color});
            }
        };
        text.fnDrawOrig = 0x13405c;
        text.fnColorCode = 0x133e2c;
        text.fnSetColor = 0x133dd0;
        text.fnCtx = 0x108a40;
        text.fnPutPixel = 0x133d24;
        text.addrEnabled = mem.read32(GOT + 0x270);
        text.addrCodes = mem.read32(GOT + 0x274);
        hookThumb(0x133f74, 0); // measure
        hookThumb(0x13495c, 1); // fit lines
        hookThumb(0x134840, 2); // draw (no colour)
        // the one direct call of the draw routine with a colour: route it through a near stub
        mem.map(0xF0000, 0x100);
        int near = cpu.makeStub(0xF0000, (CAT_HLE << 16) | 3);
        patchBL(0x12277c, near & ~1);
        ui = new hl4.core.UiOverlay(font);
        text.sink = ui;
    }

    public void svc(Cpu cpu, int id) {
        int cat = id >>> 16, idx = id & 0xFFFF;
        if (cat == CAT_HLE) {
            hle(idx);
        } else if (cat == CAT_JAVA) {
            jvm.hostMethod(idx);
        } else if (cat >= CAT_WIPIC && cat < CAT_WIPIC + 32) {
            wipic(cat - CAT_WIPIC, idx);
        } else if (cat == CAT_INIT) {
            init(idx);
        } else {
            throw new CpuException("unknown host call " + hex(id));
        }
    }

    void init(int idx) {
        switch (idx) {
            case 0: { // get_interface(name)
                String name = readString(cpu.arg(0));
                if (name.equals("WIPIC_knlInterface")) ret(kernelInterface());
                else if (name.equals("WIPI_JBInterface")) ret(jvm.jbInterface());
                else {
                    log("unknown interface " + name);
                    ret(0);
                }
                return;
            }
            case 1: jvm.javaThrow(); return;
            case 2: jvm.checkType(); return;
            case 3: ret(jvm.newObject(cpu.arg(0))); return;
            case 4: ret(jvm.newArrayRaw(cpu.arg(0), cpu.arg(1))); return;
            case 5: jvm.classLoad(); return;
            case 6: ret(alloc(cpu.arg(0))); return;
            default:
                if (idx >= 7 && idx <= 18) {
                    jvm.jb(idx);
                    return;
                }
                throw new CpuException("unknown KTF init call " + idx);
        }
    }

    // ------------------------------------------------------------------
    // WIPI-C interface tables

    static final int T_KERNEL = 0, T_UTIL = 1, T_MISC = 2, T_GRAPHICS = 3, T_IF3 = 4, T_IF4 = 5, T_IF5 = 6,
            T_DATABASE = 7, T_IF7 = 8, T_UIC = 9, T_MEDIA = 10, T_NET = 11, T_IF11 = 12, T_IF12 = 13,
            T_IF13 = 14, T_IF14 = 15, T_IF15 = 16, T_IF16 = 17;

    int table(int t, int count) {
        int a = alloc(count * 4);
        for (int i = 0; i < count; i++) mem.write32(a + i * 4, stub(((CAT_WIPIC + t) << 16) | i));
        return a;
    }

    int kernelInterface() {
        return table(T_KERNEL, 65);
    }

    int wipicInterfaces() {
        if (wipicInterfaces != 0) return wipicInterfaces;
        int[] t = new int[17];
        t[0] = table(T_UTIL, 7);
        t[1] = table(T_MISC, 5);
        t[2] = table(T_GRAPHICS, 60);
        t[3] = table(T_IF3, 5);
        t[4] = table(T_IF4, 64);
        t[5] = table(T_IF5, 64);
        t[6] = table(T_DATABASE, 17);
        t[7] = table(T_IF7, 64);
        t[8] = table(T_UIC, 45);
        t[9] = table(T_MEDIA, 27);
        t[10] = table(T_NET, 30);
        t[11] = table(T_IF11, 64);
        t[12] = table(T_IF12, 3);
        t[13] = table(T_IF13, 64);
        t[14] = table(T_IF14, 64);
        t[15] = table(T_IF15, 64);
        t[16] = table(T_IF16, 64);
        wipicInterfaces = allocStruct(t);
        return wipicInterfaces;
    }

    public int[] svcStats = new int[32 * 128];

    void wipic(int t, int idx) {
        if (t * 128 + idx < svcStats.length) svcStats[t * 128 + idx]++;
        switch (t) {
            case T_KERNEL: kernel(idx); return;
            case T_GRAPHICS: graphics(idx); return;
            case T_DATABASE: database(idx); return;
            case T_MEDIA: media(idx); return;
            case T_MISC:
                if (idx == 0) {
                    ret(0);
                    return;
                }
                break;
            case T_UTIL:
                if (idx == 1) { // htons
                    int v = cpu.arg(0);
                    ret(((v & 0xFF) << 8) | ((v >> 8) & 0xFF));
                    return;
                }
                break;
            case T_NET:
                net(idx);
                return;
            case T_IF11: // KTF's network table is longer than 30 entries: it continues here
                net(30 + idx);
                return;
            case T_IF3:
            case T_IF12:
                debug("stub unk" + t + "-" + idx);
                ret(0);
                return;
            case T_UIC:
                debug("uic " + idx);
                ret(0);
                return;
        }
        throw new CpuException("unimplemented KTF WIPI-C table " + t + " function " + idx);
    }

    // ---- network: offline fake server (no real network) ----
    public FakeServer server = new FakeServer();
    int netReadCb, netReadParam, netWriteCb, netWriteParam;
    byte[] netIn = new byte[0];

    void netDeliver() {
        byte[] r = server.poll();
        if (r != null && r.length > 0) {
            byte[] n = new byte[netIn.length + r.length];
            System.arraycopy(netIn, 0, n, 0, netIn.length);
            System.arraycopy(r, 0, n, netIn.length, r.length);
            netIn = n;
        }
        if ((netIn.length > 0 || server.dropped) && netReadCb != 0) scheduleCall(netReadCb, new int[]{1, 0, netReadParam}, 5);
    }

    void net(int idx) {
        switch (idx) {
            case 0: { // MC_netConnect(cb, param)
                // only the flows the offline server emulates may connect: SMS consent (5), cash shop
                // (0 with the shop flag), revive purchase (6). Warehouse/restore fail like "no network".
                int g = mem.read32(0x189e44);
                int st = g != 0 ? (byte) mem.read8(g + 0xdb) : -1, shop = g != 0 ? mem.read8(g + 0xd4) : 0;
                if (!(st == 5 || st == 6 || (st == 0 && shop != 0))) {
                    log("MC_netConnect state=" + st + " -> offline, fail");
                    scheduleCall(cpu.arg(0), new int[]{-1, cpu.arg(1)}, 1);
                    ret(0);
                    return;
                }
                log("MC_netConnect state=" + st + " -> offline server");
                server.text = text;
                scheduleCall(cpu.arg(0), new int[]{0, cpu.arg(1)}, 5);
                ret(0);
                return;
            }
            case 1: log("MC_netClose"); server.reset(); netIn = new byte[0]; return;
            case 2: log("MC_netSocket(" + cpu.arg(0) + "," + cpu.arg(1) + ") -> 1"); ret(1); return;
            case 3: { // MC_netSocketConnect(fd, type, addr, port, cb, param)
                int fd = cpu.arg(0), addr = cpu.arg(2), port = cpu.arg(3) & 0xFFFF;
                log("MC_netSocketConnect(" + fd + ", " + hex(addr) + ", " + port + ")");
                server.reset();
                netIn = new byte[0];
                scheduleCall(cpu.arg(4), new int[]{fd, 0, cpu.arg(5)}, 5);
                ret(0);
                return;
            }
            case 4: { // MC_netSocketWrite(fd, buf, len)
                int len = cpu.arg(2);
                byte[] d = mem.readBytes(cpu.arg(1), len);
                server.receive(d);
                log("MC_netSocketWrite " + len + ": " + FakeServer.hexdump(d));
                if (netWriteCb != 0) scheduleCall(netWriteCb, new int[]{cpu.arg(0), 0, netWriteParam}, 1);
                netDeliver();
                ret(len);
                return;
            }
            case 5: { // MC_netSocketRead(fd, buf, len)
                int len = Math.min(cpu.arg(2), netIn.length);
                mem.writeBytes(cpu.arg(1), netIn, 0, len);
                byte[] rest = new byte[netIn.length - len];
                System.arraycopy(netIn, len, rest, 0, rest.length);
                netIn = rest;
                log("MC_netSocketRead(" + cpu.arg(2) + ") -> " + len);
                ret(len);
                return;
            }
            case 6: log("MC_netSocketClose(" + cpu.arg(0) + ")"); ret(0); return;
            case 8: ret(1500); return;
            case 11: { // MC_netGetHostAddr(name, addr*, cb, param)
                log("MC_netGetHostAddr " + cString(cpu.arg(0)));
                mem.write32(cpu.arg(1), 0x1C12DED2);
                ret(0);
                return;
            }
            case 13: // MC_netSetReadCB(fd, cb, param)
                netReadCb = cpu.arg(1);
                netReadParam = cpu.arg(2);
                log("MC_netSetReadCB " + hex(netReadCb));
                if (netIn.length > 0 || server.dropped) netDeliver();
                ret(0);
                return;
            case 14:
                netWriteCb = cpu.arg(1);
                netWriteParam = cpu.arg(2);
                log("MC_netSetWriteCB " + hex(netWriteCb));
                ret(0);
                return;
        }
        if (idx == 31) { // write(fd, buf, len)
            int len = cpu.arg(2);
            byte[] d = mem.readBytes(cpu.arg(1), len);
            log("net write " + len + ": " + FakeServer.hexdump(d));
            server.receive(d);
            netDeliver();
            ret(len);
            return;
        }
        if (idx == 32) { // read(fd, buf, len) -> n / M_E_WOULDBLOCK
            netDeliver();
            int len = Math.min(cpu.arg(2), netIn.length);
            if (len == 0 && server.dropped) {
                log("net read -> connection closed by server");
                ret(-1);
                return;
            }
            if (len == 0) {
                ret(-19);
                return;
            }
            mem.writeBytes(cpu.arg(1), netIn, 0, len);
            byte[] rest = new byte[netIn.length - len];
            System.arraycopy(netIn, len, rest, 0, rest.length);
            netIn = rest;
            log("net read " + len);
            ret(len);
            return;
        }
        if (idx == 33) { // close(fd)
            log("net close(" + cpu.arg(0) + ")");
            ret(0);
            return;
        }
        if (idx == 30) { // KTF socket connect (fd, addr, port, ?, cb, param) -> M_E_WOULDBLOCK, cb(fd, 0, param)
            int fd = cpu.arg(0);
            log("MC_netSocketConnect30(" + fd + ", " + hex(cpu.arg(1)) + ", " + hex(cpu.arg(2)) + ", " + hex(cpu.arg(3)) + ", cb=" + hex(cpu.arg(4)) + ", " + hex(cpu.arg(5)) + ")");
            server.reset();
            netIn = new byte[0];
            scheduleCall(cpu.arg(4), new int[]{fd, 0, cpu.arg(5)}, 5);
            ret(-19);
            return;
        }
        log("MC_net function " + idx + " (unsupported) " + hex(cpu.arg(0)) + "," + hex(cpu.arg(1)) + "," + hex(cpu.arg(2)) + "," + hex(cpu.arg(3)) + " lr=" + hex(cpu.r[14]));
        ret(-1);
    }

    String cString(int p) {
        StringBuffer sb = new StringBuffer();
        for (int c; p != 0 && (c = mem.read8(p)) != 0 && sb.length() < 256; p++) sb.append((char) c);
        return sb.toString();
    }

    // ---- memory handles (KTF: every allocation has an indirect pointer) ----
    // handle -> [handle+4] ; [handle+4] = size ; data at [handle] + 8

    public int allocHandle(int size) {
        int p = alloc(size + 12);
        mem.write32(p, p + 4);
        mem.write32(p + 4, size);
        return p;
    }

    public int dataPtr(int handle) {
        return mem.read32(handle) + 8;
    }

    public void freeHandle(int handle) {
        heap.free(handle);
    }

    void kernel(int idx) {
        switch (idx) {
            case 0: knlPrintk(); return;
            case 1: knlSprintk(); return;
            case 7: knlExit(); return;
            case 9: ret(1); return;           // current program id
            case 14: knlGetProgramName(); return;
            case 20: { // alloc
                int size = cpu.arg(0);
                ret(size == 0 ? 0 : allocHandle(size));
                return;
            }
            case 21: { // calloc
                int size = cpu.arg(0);
                if (size == 0) {
                    ret(0);
                    return;
                }
                int h = allocHandle(size);
                mem.fill(dataPtr(h), 0, size);
                ret(h);
                return;
            }
            case 22: { // free
                int h = cpu.arg(0);
                if (h != 0) freeHandle(h);
                ret(h);
                return;
            }
            case 23: knlGetTotalMemory(); return;
            case 24: knlGetFreeMemory(); return;
            case 25: knlDefTimer(); return;
            case 26: knlSetTimer(); return;
            case 27: knlUnsetTimer(); return;
            case 28: knlCurrentTime(); return;
            case 29: knlGetSystemProperty(); return;
            case 30: knlSetSystemProperty(); return;
            case 31: knlGetResourceId(); return;
            case 32: { // get resource (id, indirect buf, size)
                int id = cpu.arg(0), buf = cpu.arg(1), size = cpu.arg(2);
                if (id <= 0) {
                    ret(-9);
                    return;
                }
                byte[] data = resource(readString(id));
                if (data == null) {
                    ret(-12);
                    return;
                }
                if (data.length > size) {
                    ret(-1);
                    return;
                }
                mem.writeBytes(dataPtr(buf), data, 0, data.length);
                ret(0);
                return;
            }
            case 33: ret(wipicInterfaces()); return;
            default:
                throw new CpuException("unimplemented KTF kernel function " + idx);
        }
    }

    // ------------------------------------------------------------------
    // graphics (framebuffer = handle -> {w, h, bpl, bpp, buf(handle)})

    int screenFb;
    int[] frame;
    public int flushCount;

    int fbStruct(int handle) {
        return dataPtr(handle);
    }

    int newFramebuffer(int w, int h) {
        int bpl = w * 2;
        int pix = allocHandle(bpl * h);
        mem.fill(dataPtr(pix), 0, bpl * h);
        int fb = allocHandle(20);
        int s = dataPtr(fb);
        mem.write32(s, w);
        mem.write32(s + 4, h);
        mem.write32(s + 8, bpl);
        mem.write32(s + 12, 16);
        mem.write32(s + 16, pix);
        return fb;
    }

    Gfx16.Surface surface(int fb, int ctx, boolean useClip) {
        int s = fbStruct(fb);
        Gfx16.Surface sf = new Gfx16.Surface();
        sf.width = mem.read32(s);
        sf.height = mem.read32(s + 4);
        sf.bpl = mem.read32(s + 8);
        sf.buf = dataPtr(mem.read32(s + 16));
        sf.clipX1 = 0;
        sf.clipY1 = 0;
        sf.clipX2 = sf.width;
        sf.clipY2 = sf.height;
        return sf;
    }

    int fgPixel(int ctx) {
        return ctx == 0 ? 0 : mem.read32(ctx + 12) & 0xFFFF;
    }

    void graphics(int idx) {
        switch (idx) {
            case 2: { // get screen framebuffer
                if (screenFb == 0) screenFb = newFramebuffer(screenWidth, screenHeight);
                ret(screenFb);
                return;
            }
            case 3: { // destroy offscreen
                int fb = cpu.arg(0);
                if (fb != 0) {
                    freeHandle(mem.read32(fbStruct(fb) + 16));
                    freeHandle(fb);
                }
                return;
            }
            case 4: ret(newFramebuffer(cpu.arg(0), cpu.arg(1))); return;
            case 5: { // init context: WIPICGraphicsContext (packed(4)), 52 bytes
                int p = cpu.arg(0);
                mem.fill(p, 0, 52);
                return;
            }
            case 6: { // set context
                int p = cpu.arg(0), op = cpu.arg(1), pv = cpu.arg(2);
                switch (op) {
                    case 0: // clip: 4 ints -> 4 u16 at +4
                        for (int i = 0; i < 4; i++) mem.write16(p + 4 + i * 2, mem.read32(pv + i * 4));
                        break;
                    case 1: mem.write32(p + 12, pv); break;
                    case 2: mem.write32(p + 16, pv); break;
                    case 3: mem.write32(p + 20, pv); break;
                    case 4: mem.write32(p + 24, pv); break;
                    case 5: mem.write32(p + 32, pv); break;
                    case 6: mem.write32(p + 36, pv); break;
                    case 7: mem.write32(p + 44, pv); break;
                    case 8: mem.write32(p + 48, pv); break;
                    case 10: // offset: 2 ints -> 2 u16 at +28
                        mem.write16(p + 28, mem.read32(pv));
                        mem.write16(p + 30, mem.read32(pv + 4));
                        break;
                }
                return;
            }
            case 8: { // put pixel
                Gfx16.Surface s = surface(cpu.arg(0), 0, false);
                Gfx16.putPixel(mem, s, cpu.arg(1), cpu.arg(2), fgPixel(cpu.arg(3)));
                return;
            }
            case 9: { // draw line
                Gfx16.Surface s = surface(cpu.arg(0), 0, false);
                Gfx16.drawLine(mem, s, cpu.arg(1), cpu.arg(2), cpu.arg(3), cpu.arg(4), fgPixel(cpu.arg(5)));
                return;
            }
            case 10: { // draw rect
                int w = cpu.arg(3), h = cpu.arg(4);
                if (w <= 0 || h <= 0) return;
                Gfx16.Surface s = surface(cpu.arg(0), 0, false);
                Gfx16.drawRect(mem, s, cpu.arg(1), cpu.arg(2), w - 1, h - 1, fgPixel(cpu.arg(5)));
                return;
            }
            case 11: { // fill rect
                int w = cpu.arg(3), h = cpu.arg(4);
                if (w <= 0 || h <= 0) return;
                Gfx16.Surface s = surface(cpu.arg(0), 0, false);
                Gfx16.fillRect(mem, s, cpu.arg(1), cpu.arg(2), w, h, fgPixel(cpu.arg(5)));
                return;
            }
            case 12: { // copy framebuffer (dst, x, y, w, h, src, sx, sy, ctx)
                int w = cpu.arg(3), h = cpu.arg(4);
                if (w <= 0 || h <= 0) return;
                Gfx16.Surface d = surface(cpu.arg(0), 0, false);
                Gfx16.Surface s = surface(cpu.arg(5), 0, false);
                Gfx16.copy(mem, d, cpu.arg(1), cpu.arg(2), w, h, s, cpu.arg(6), cpu.arg(7));
                return;
            }
            case 14: { // copy area (dst, x, y, w, h, sx, sy, ctx)
                int w = cpu.arg(3), h = cpu.arg(4);
                if (w <= 0 || h <= 0) return;
                Gfx16.Surface d = surface(cpu.arg(0), 0, false);
                Gfx16.copy(mem, d, cpu.arg(1), cpu.arg(2), w, h, d, cpu.arg(5), cpu.arg(6));
                return;
            }
            case 21: { // flush lcd
                int fb = cpu.arg(1);
                int s = fbStruct(fb);
                int w = mem.read32(s), h = mem.read32(s + 4);
                if (frame == null || frame.length != screenWidth * screenHeight) frame = new int[screenWidth * screenHeight];
                Gfx16.toArgb(mem, dataPtr(mem.read32(s + 16)), mem.read32(s + 8), 0, 0, Math.min(w, screenWidth), Math.min(h, screenHeight), frame, 0, screenWidth);
                flushCount++;
                if (ui != null) ui.compose(frame, screenWidth, screenHeight);
                host.present(frame, screenWidth, screenHeight);
                return;
            }
            case 22: ret(Gfx16.toRgb565(cpu.arg(0), cpu.arg(1), cpu.arg(2))); return;
            case 23: {
                int argb = Gfx16.table()[cpu.arg(0) & 0xFFFF];
                mem.write32(cpu.arg(1), (argb >> 16) & 0xFF);
                mem.write32(cpu.arg(2), (argb >> 8) & 0xFF);
                mem.write32(cpu.arg(3), argb & 0xFF);
                ret(cpu.arg(0));
                return;
            }
            case 24: { // display info
                int out = cpu.arg(1);
                int[] v = {16, 16, screenWidth, screenHeight, screenWidth * 2, 1, 0xf800, 0x001f, 0x07e0};
                for (int i = 0; i < v.length; i++) mem.write32(out + i * 4, v[i]);
                ret(0);
                return;
            }
            case 25: requestRepaint(); return;
            case 26: ret(0); return; // get font
            case 27: ret(12); return;
            case 28: ret(10); return;
            case 29: ret(2); return;
            default:
                throw new CpuException("unimplemented KTF graphics function " + idx);
        }
    }

    // ------------------------------------------------------------------
    // database (KTF stream-style, see wie database.rs)

    /** Current content of a storage file, shared by every handle opened on it. */
    static final class FileBuf {
        String name;
        byte[] data = new byte[64];
        int len;
        boolean dirty;
    }

    static final class DbFile {
        FileBuf f;
        int rpos, wpos, mode;
    }

    final Hashtable dbs = new Hashtable();
    final Hashtable files = new Hashtable();   // name -> FileBuf (null content = does not exist)
    int dbNext = 0x100;

    private boolean recovered;

    /**
     * Builds before ui7 had no delete/rename (db 6/7). When the save outgrew its slot the game
     * rebuilt it into "chicken" and renamed that to "kickass", which silently failed: every later
     * load got the old "kickass". A leftover "chicken" therefore holds the newest save: use it.
     */
    private void recoverRebuiltSave() {
        if (recovered) return;
        recovered = true;
        byte[] c = host.storageRead("chicken");
        if (c == null || c.length < 200) return;
        log("KTF: recovering save from unrenamed chicken (" + c.length + " bytes)");
        host.storageWrite("kickass", c);
        host.storageDelete("chicken");
        if (ui != null) ui.note("KHOI PHUC SAVE MOI NHAT " + c.length + "B");
    }

    private FileBuf file(String name, boolean create) {
        recoverRebuiltSave();
        FileBuf fb = (FileBuf) files.get(name);
        if (fb == null) {
            byte[] ex = host.storageRead(name);
            if (ex == null && !create) return null;
            fb = new FileBuf();
            fb.name = name;
            if (ex != null) {
                fb.data = new byte[Math.max(64, ex.length)];
                System.arraycopy(ex, 0, fb.data, 0, ex.length);
                fb.len = ex.length;
            } else {
                fb.dirty = true;
            }
            files.put(name, fb);
        }
        return fb;
    }

    private void persist(FileBuf fb) {
        if (!fb.dirty) return;
        byte[] snap = new byte[fb.len];
        System.arraycopy(fb.data, 0, snap, 0, fb.len);
        host.storageWrite(fb.name, snap);
        fb.dirty = false;
        if (saveDebug && ui != null) {
            saveCount++;
            ui.note("GHI#" + saveCount + " " + fb.name + " " + fb.len + "B G:" + tag(fb, "Hero4GameSave") + " S:" + tag(fb, "Hero4SlotSave"));
        }
        debug("KTF db persist " + fb.name + " " + fb.len + " bytes");
    }

    /** Save diagnostics banner (manifest HL4-SAVEDBG: 1). */
    public boolean saveDebug;
    int saveCount;

    /** Offset of a section of the game's save file (200 byte directory: 10 x {name[16], s16 alloc, s16 used}). */
    static int section(FileBuf fb, String prefix) {
        if (fb.len < 200) return -1;
        int off = 200;
        for (int e = 0; e < 10; e++) {
            int p = e * 20;
            if (fb.data[p] == 0) continue;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 16 && fb.data[p + i] != 0; i++) sb.append((char) (fb.data[p + i] & 0xFF));
            int alloc = (short) ((fb.data[p + 16] & 0xFF) | (fb.data[p + 17] << 8));
            if (sb.toString().startsWith(prefix)) return off;
            if (alloc > 0) off += alloc;
        }
        return -1;
    }

    static String tag(FileBuf fb, String prefix) {
        int off = section(fb, prefix);
        return off < 0 ? "-" : tag(fb, off);
    }

    static String tag(FileBuf fb, int off) {
        if (off < 0 || fb.len < off + 4) return "-";
        String h = "0123456789ABCDEF";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            int b = fb.data[off + i] & 0xFF;
            sb.append(h.charAt(b >> 4)).append(h.charAt(b & 15));
        }
        return sb.toString();
    }

    /** Write every modified file to the host storage (app pause / exit). */
    public void flushStorage() {
        synchronized (files) {
            java.util.Enumeration e = files.elements();
            while (e.hasMoreElements()) persist((FileBuf) e.nextElement());
        }
    }

    void database(int idx) {
        synchronized (files) {
            databaseLocked(idx);
        }
    }

    void databaseLocked(int idx) {
        switch (idx) {
            case 0: { // open(name, mode, type): 1 = read, 2 = create, 4 = truncate, 8 = read/write
                String name = readString(cpu.arg(0));
                int mode = cpu.arg(1);
                FileBuf fb = file(name, mode != 1);
                log("KTF dbOpen(" + name + ", mode=" + mode + ") exists=" + (fb != null && !(fb.dirty && fb.len == 0)));
                if (fb == null) {
                    ret(-12);
                    return;
                }
                if (mode == 4 || mode == 2) { // create / truncate: start empty
                    fb.len = 0;
                    fb.dirty = true;
                }
                if (fb.dirty) persist(fb); // new / truncated files exist from now on
                DbFile f = new DbFile();
                f.f = fb;
                f.mode = mode;
                int h = dbNext++;
                dbs.put(new Integer(h), f);
                debug("KTF dbOpen -> h=" + hex(h) + " open handles " + dbs.size());
                ret(h);
                return;
            }
            case 1: { // stream read (h, buf, len)
                DbFile f = (DbFile) dbs.get(new Integer(cpu.arg(0)));
                if (f == null) {
                    ret(-25);
                    return;
                }
                FileBuf fb = f.f;
                if (f.rpos >= fb.len) {
                    ret(-23);
                    return;
                }
                int take = Math.min(cpu.arg(2), fb.len - f.rpos);
                mem.writeBytes(cpu.arg(1), fb.data, f.rpos, take);
                f.rpos += take;
                ret(take);
                return;
            }
            case 2: { // stream write
                DbFile f = (DbFile) dbs.get(new Integer(cpu.arg(0)));
                if (f == null) {
                    ret(-25);
                    return;
                }
                FileBuf fb = f.f;
                int len = cpu.arg(2);
                int end = f.wpos + len;
                if (end > fb.data.length) {
                    byte[] nd = new byte[Math.max(end, fb.data.length * 2)];
                    System.arraycopy(fb.data, 0, nd, 0, fb.len);
                    fb.data = nd;
                }
                mem.readBytes(cpu.arg(1), fb.data, f.wpos, len);
                debug("KTF dbWrite h=" + hex(cpu.arg(0)) + " " + fb.name + " @" + f.wpos + " len " + len);
                f.wpos = end;
                if (end > fb.len) fb.len = end;
                fb.dirty = true;
                ret(len);
                return;
            }
            case 3: { // close: the file is written to storage as one piece
                DbFile f = (DbFile) dbs.remove(new Integer(cpu.arg(0)));
                debug("KTF dbClose h=" + hex(cpu.arg(0)));
                if (f == null) {
                    ret(-25);
                    return;
                }
                persist(f.f);
                ret(0);
                return;
            }
            case 4: { // select record = seek
                DbFile f = (DbFile) dbs.get(new Integer(cpu.arg(0)));
                if (f == null) {
                    ret(-25);
                    return;
                }
                int off = cpu.arg(1);
                if (off < 0) {
                    ret(-22);
                    return;
                }
                f.rpos = off;
                f.wpos = off;
                if (saveDebug && ui != null && f.mode == 1 && off == section(f.f, "Hero4GameSave")) ui.note("DOC G:" + tag(f.f, off) + " " + f.f.len + "B");
                debug("KTF dbSeek h=" + hex(cpu.arg(0)) + " " + f.f.name + " @" + off);
                ret(0);
                return;
            }
            case 5: { // stat by name (name, int[3] out, mode) -> 0 and {0, 0, size}
                String name = readString(cpu.arg(0));
                FileBuf fb = file(name, false);
                if (fb == null) {
                    ret(-22);
                    return;
                }
                int out = cpu.arg(1);
                if (out != 0) {
                    mem.write32(out, 0);
                    mem.write32(out + 4, 0);
                    mem.write32(out + 8, fb.len);
                }
                ret(0);
                return;
            }
            case 6: { // delete: (handle, rec) = record delete (no-op) or (name, flag) = delete the file
                if (dbs.get(new Integer(cpu.arg(0))) != null) {
                    ret(0);
                    return;
                }
                String name = readString(cpu.arg(0));
                FileBuf fb = file(name, false);
                log("KTF dbDelete(" + name + ") exists=" + (fb != null));
                if (fb == null) {
                    ret(-12);
                    return;
                }
                files.remove(name);
                host.storageDelete(name);
                if (saveDebug && ui != null) ui.note("XOA " + name);
                ret(0);
                return;
            }
            case 7: { // rename(old, new, flag): the game rebuilds a grown save into "chicken" then renames it
                String from = readString(cpu.arg(0)), to = readString(cpu.arg(1));
                FileBuf fb = file(from, false);
                log("KTF dbRename(" + from + " -> " + to + ") exists=" + (fb != null));
                if (fb == null) {
                    ret(-12);
                    return;
                }
                files.remove(from);
                fb.name = to;
                fb.dirty = true;
                files.put(to, fb);
                persist(fb);
                host.storageDelete(from);
                if (saveDebug && ui != null) ui.note("DOI TEN " + from + " -> " + to + " " + fb.len + "B");
                ret(0);
                return;
            }
            case 12: { // available storage
                ret(1024 * 1024);
                return;
            }
            case 16: { // exists(name)
                String name = readString(cpu.arg(0));
                ret(file(name, false) != null ? 1 : 0);
                return;
            }
            default:
                log("KTF db function " + idx + " args " + hex(cpu.arg(0)) + " " + hex(cpu.arg(1)) + " " + hex(cpu.arg(2)));
                ret(-1);
        }
    }

    // ------------------------------------------------------------------
    // media (clip = raw struct, sound handle kept host side)
    final Hashtable clips = new Hashtable();

    void media(int idx) {
        switch (idx) {
            case 0: {
                int p = alloc(180);
                mem.fill(p, 0, 180);
                ret(p);
                return;
            }
            case 3: {
                int p = cpu.arg(0);
                if (p != 0) {
                    Object s = clips.remove(new Integer(p));
                    if (s != null) host.soundFree(s);
                    heap.free(p);
                }
                ret(0);
                return;
            }
            case 4: {
                int p = cpu.arg(0), buf = cpu.arg(1), size = cpu.arg(2);
                if (p == 0) {
                    ret(-1);
                    return;
                }
                Object old = clips.remove(new Integer(p));
                if (old != null) host.soundFree(old);
                Object s = host.soundCreate(mem.readBytes(buf, size), 0);
                if (s != null) clips.put(new Integer(p), s);
                ret(size);
                return;
            }
            case 8: {
                Object s = clips.get(new Integer(cpu.arg(0)));
                if (s != null) host.soundPlay(s, cpu.arg(1) != 0);
                ret(0);
                return;
            }
            case 11: {
                Object s = clips.get(new Integer(cpu.arg(0)));
                if (s != null) host.soundStop(s);
                ret(0);
                return;
            }
            case 16:
                host.vibrate(cpu.arg(1), cpu.arg(0));
                ret(0);
                return;
            case 26: {
                Object s = clips.get(new Integer(cpu.arg(0)));
                if (s != null) host.soundVolume(s, cpu.arg(1));
                ret(0);
                return;
            }
            default:
                debug("KTF media stub " + idx);
                ret(0);
        }
    }
}
