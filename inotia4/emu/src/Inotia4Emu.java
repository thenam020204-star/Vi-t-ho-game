import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.Emulator;
import com.github.unidbg.arm.backend.Unicorn2Factory;
import com.github.unidbg.linux.android.AndroidEmulatorBuilder;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.*;
import com.github.unidbg.linux.android.dvm.array.ByteArray;
import com.github.unidbg.linux.android.dvm.array.IntArray;
import com.github.unidbg.memory.Memory;
import com.github.unidbg.memory.SvcMemory;
import com.github.unidbg.arm.ArmSvc;

import java.io.File;
import java.util.*;

/**
 * Chay libgame.so (Inotia 4, ARM64) trong unidbg - khong can KVM/Android.
 * Mo phong vong doi Java cua com.com2us.wrapper (CWrapperActivity/CWrapperKernel),
 * log moi loi goi JNI/GL ma harness chua xu ly.
 *
 *   java Inotia4Emu <libgame.so> <apk> <out dir> [so frame]
 */
public class Inotia4Emu extends AbstractJni {

    static final String PKG = "com.com2us.inotia4.normal.freefull.google.global.android.common";
    final AndroidEmulator emulator;
    final VM vm;
    final DalvikModule dm;
    final Map<String, Integer> unknown = new TreeMap<>();
    final GLES gl;
    final File res, out;
    int origW, origH;
    static final int FONT_W = 1024, FONT_H = 512;
    static final int DISP_W = 960, DISP_H = 640;
    IntArray fontBuf;

    Inotia4Emu(File so, File apk, File out) {
        this.res = apk;
        this.out = out;
        File root = new File(out, "rootfs");
        File apkDst = new File(root, APK_PATH.substring(1));
        apkDst.getParentFile().mkdirs();
        new File(root, "data/data/" + PKG + "/files").mkdirs();
        try { java.nio.file.Files.copy(apk.toPath(), apkDst.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING); }
        catch (java.io.IOException ex) { throw new RuntimeException(ex); }
        emulator = AndroidEmulatorBuilder.for64Bit()
                .setRootDir(root)
                .setProcessName(PKG)
                .addBackendFactory(new Unicorn2Factory(true))
                .build();
        Memory memory = emulator.getMemory();
        memory.setLibraryResolver(new AndroidResolver(23));
        gl = new GLES(emulator);
        gl.resize(DISP_W, DISP_H);
        gl.register(memory);
        vm = emulator.createDalvikVM();
        vm.setJni(this);
        vm.setVerbose(Boolean.getBoolean("emu.verbose"));
        dm = vm.loadLibrary(so, true);
        fontBuf = new IntArray(vm, new int[FONT_W * FONT_H]);
        // Stub CHI trong emulator (moi truong unidbg khong co ps/system): ten=gia_tri_tra_ve
        String stubs = System.getProperty("emu.stub", "_ZN10Protection19isRunningMemoryHackEv=0,_ZN10Protection8isRootedEv=0");
        if (!stubs.isEmpty()) {
            com.github.unidbg.debugger.Debugger dbg0 = emulator.attach();
            for (String kv : stubs.split(",")) {
                final String name = kv.split("=")[0];
                final long ret = Long.decode(kv.split("=")[1]);
                com.github.unidbg.Symbol sym = dm.getModule().findSymbolByName(name, false);
                if (sym == null) { System.out.println("[stub] khong co " + name); continue; }
                dbg0.addBreakPoint(sym.getAddress(), (emu, addr) -> {
                    com.github.unidbg.arm.backend.Backend be = emu.getBackend();
                    be.reg_write(unicorn.Arm64Const.UC_ARM64_REG_X0, ret);
                    be.reg_write(unicorn.Arm64Const.UC_ARM64_REG_PC, be.reg_read(unicorn.Arm64Const.UC_ARM64_REG_LR).longValue());
                    return true;
                });
            }
        }
        String tf = System.getProperty("emu.tracefn", "");
        if (!tf.isEmpty()) {
            com.github.unidbg.debugger.Debugger dbg = emulator.attach();
            for (final String name : tf.split(",")) {
                com.github.unidbg.Symbol sym = dm.getModule().findSymbolByName(name, false);
                if (sym == null) { System.out.println("[trace] khong co symbol " + name); continue; }
                dbg.addBreakPoint(sym.getAddress(), (emu, addr) -> {
                    System.out.println("[fn] " + name + " x0=0x" + Long.toHexString(emu.getContext().getLongArg(0))
                            + " x1=0x" + Long.toHexString(emu.getContext().getLongArg(1)));
                    return true;
                });
            }
        }
    }

    void note(String what) {
        Integer n = unknown.get(what);
        unknown.put(what, n == null ? 1 : n + 1);
        if (n == null) System.out.println("[JNI?] " + what);
    }

    static final String APK_PATH = "/data/app/" + PKG + "-1/base.apk";

    void trace(String s) { if (Boolean.getBoolean("emu.trace")) System.out.println("[jni] " + s); }

    // ------------------------------------------------------- JNI: mot bo xu ly chung
    /** Java "gia" ma native goi. Tra ve Integer/Long/Boolean/DvmObject/null. Chua biet -> note + gia tri rong. */
    Object java(DvmObject<?> self, String sig, VarArg va) {
        trace(sig);
        Object r = javaImpl(self, sig, va);
        if (r == UNKNOWN) { note(sig); return null; }
        return r;
    }
    static final Object UNKNOWN = new Object();
    static final Object VOID = new Object();

    Object javaImpl(DvmObject<?> self, String sig, VarArg va) {
        String F = "com/com2us/wrapper/function/CFunction->";
        if (sig.startsWith(F)) {
            String m = sig.substring(F.length());
            if (m.startsWith("getUserDir(")) return str("/data/data/");
            if (m.startsWith("getPackageName(")) return str(PKG);
            if (m.startsWith("getApkFilePath(")) return str(APK_PATH);
            if (m.startsWith("getLanguage(") || m.startsWith("getInitialLanguage(")) return str("en");
            if (m.startsWith("getCountry(") || m.startsWith("getInitialCountry(")) return str("VN");
            if (m.startsWith("getDeviceModel(")) return str("unidbg");
            if (m.startsWith("getSystemVersion(")) return str("9");
            if (m.startsWith("getDeviceId(") || m.startsWith("getAndroidId(") || m.startsWith("getCom2usUniqueId(")) return str("0123456789abcdef");
            if (m.startsWith("getPhoneNumber(") || m.startsWith("getMIN(")) return str("");
            if (m.startsWith("getSystemVersionCode(")) return 28;
            if (m.startsWith("getNativeAvailableMemory(")) return 512L << 20;
        }
        switch (sig) {
            case "com/com2us/wrapper/function/CResource->getFileDescriptorFromAsset(Ljava/lang/String;[I)Ljava/io/FileDescriptor;": {
                String name = (String) va.getObjectArg(0).getValue();
                int[] out = (int[]) va.getObjectArg(1).getValue();
                // CResource.getFullPathName: <thu muc da ngon ngu>/<ten> + ".jpg" (assetFileNameAppended)
                ZipIndex.Entry e = null;
                for (String dir : new String[]{"assets/en/game_res/", "assets/common/game_res/", "assets/"}) {
                    e = zip.map.get(dir + name + ".jpg");
                    if (e == null) e = zip.map.get(dir + name);
                    if (e != null) break;
                }
                if (e == null || e.method != 0) {
                    System.out.println("[asset] KHONG mo duoc (thieu hoac bi nen): " + name + (e != null ? " method=" + e.method : ""));
                    out[0] = -1; out[1] = -1; return null;
                }
                out[0] = (int) e.dataOffset; out[1] = (int) e.usize;
                trace("asset " + name + " @" + e.dataOffset + " len=" + e.usize);
                assetsOpened.add(name);
                return vm.resolveClass("java/io/FileDescriptor").newObject(apkFd);
            }
            case "com/com2us/wrapper/WrapperUserDefined->getMaxMemory()J": return 512L << 20;
            case "com/com2us/wrapper/WrapperUserDefined->getTotalMemory()J": return 128L << 20;
            case "com/com2us/wrapper/function/CResource->closeFileDescriptor(Ljava/lang/String;)V":
            case "com/com2us/module/activeuser/ActiveUser->setLogged(Z)V":
            case "com/com2us/module/activeuser/ActiveUser->setModuleOrientation(I)V":
                return VOID;
            case "com/com2us/wrapper/kernel/CWrapperData->setDataFromNative(Ljava/lang/String;Ljava/lang/String;IIZZZIIILjava/lang/String;Ljava/lang/String;ZIIZI)V":
                origW = va.getIntArg(8);
                origH = va.getIntArg(9);
                System.out.println("[setDataFromNative] asset=" + va.getObjectArg(0) + " suffix=" + va.getObjectArg(1)
                        + " render=" + va.getIntArg(2) + " useSGL=" + va.getIntArg(5) + " orig=" + origW + "x" + origH
                        + " enc=" + va.getObjectArg(10) + " market=" + va.getObjectArg(11));
                return VOID;
        }
        return UNKNOWN;
    }

    StringObject str(String s) { return new StringObject(vm, s); }
    static int i(Object o) { return o instanceof Number ? ((Number) o).intValue() : o instanceof Boolean ? ((Boolean) o ? 1 : 0) : 0; }
    static long l(Object o) { return o instanceof Number ? ((Number) o).longValue() : 0; }
    static boolean z(Object o) { return o instanceof Boolean ? (Boolean) o : o instanceof Number && ((Number) o).intValue() != 0; }
    static DvmObject<?> obj(Object o) { return o instanceof DvmObject ? (DvmObject<?>) o : null; }

    @Override public void callStaticVoidMethodV(BaseVM vm, DvmClass c, String sig, VaList va) { java(null, sig, va); }
    @Override public void callStaticVoidMethod(BaseVM vm, DvmClass c, String sig, VarArg va) { java(null, sig, va); }
    @Override public int callStaticIntMethodV(BaseVM vm, DvmClass c, String sig, VaList va) { return i(java(null, sig, va)); }
    @Override public int callStaticIntMethod(BaseVM vm, DvmClass c, String sig, VarArg va) { return i(java(null, sig, va)); }
    @Override public boolean callStaticBooleanMethodV(BaseVM vm, DvmClass c, String sig, VaList va) { return z(java(null, sig, va)); }
    @Override public boolean callStaticBooleanMethod(BaseVM vm, DvmClass c, String sig, VarArg va) { return z(java(null, sig, va)); }
    @Override public long callStaticLongMethodV(BaseVM vm, DvmClass c, String sig, VaList va) { return l(java(null, sig, va)); }
    @Override public long callStaticLongMethod(BaseVM vm, DvmClass c, String sig, VarArg va) { return l(java(null, sig, va)); }
    @Override public DvmObject<?> callStaticObjectMethodV(BaseVM vm, DvmClass c, String sig, VaList va) { return obj(java(null, sig, va)); }
    @Override public DvmObject<?> callStaticObjectMethod(BaseVM vm, DvmClass c, String sig, VarArg va) { return obj(java(null, sig, va)); }
    @Override public void callVoidMethodV(BaseVM vm, DvmObject<?> o, String sig, VaList va) { java(o, sig, va); }
    @Override public void callVoidMethod(BaseVM vm, DvmObject<?> o, String sig, VarArg va) { java(o, sig, va); }
    @Override public int callIntMethodV(BaseVM vm, DvmObject<?> o, String sig, VaList va) { return i(java(o, sig, va)); }
    @Override public int callIntMethod(BaseVM vm, DvmObject<?> o, String sig, VarArg va) { return i(java(o, sig, va)); }
    @Override public boolean callBooleanMethodV(BaseVM vm, DvmObject<?> o, String sig, VaList va) { return z(java(o, sig, va)); }
    @Override public boolean callBooleanMethod(BaseVM vm, DvmObject<?> o, String sig, VarArg va) { return z(java(o, sig, va)); }
    @Override public long callLongMethodV(BaseVM vm, DvmObject<?> o, String sig, VaList va) { return l(java(o, sig, va)); }
    @Override public long callLongMethod(BaseVM vm, DvmObject<?> o, String sig, VarArg va) { return l(java(o, sig, va)); }
    @Override public DvmObject<?> callObjectMethodV(BaseVM vm, DvmObject<?> o, String sig, VaList va) { return obj(java(o, sig, va)); }
    @Override public DvmObject<?> callObjectMethod(BaseVM vm, DvmObject<?> o, String sig, VarArg va) { return obj(java(o, sig, va)); }
    @Override public DvmObject<?> newObjectV(BaseVM vm, DvmClass c, String sig, VaList va) { note("NEW " + sig); return c.newObject(null); }
    @Override public DvmObject<?> newObject(BaseVM vm, DvmClass c, String sig, VarArg va) { note("NEW " + sig); return c.newObject(null); }
    @Override public DvmObject<?> getStaticObjectField(BaseVM vm, DvmClass c, String sig) { note("GSF " + sig); return null; }
    @Override public int getStaticIntField(BaseVM vm, DvmClass c, String sig) { note("GSI " + sig); return 0; }
    @Override public DvmObject<?> getObjectField(BaseVM vm, DvmObject<?> o, String sig) { note("GF " + sig); return null; }
    @Override public int getIntField(BaseVM vm, DvmObject<?> o, String sig) {
        if (sig.equals("java/io/FileDescriptor->descriptor:I")) return (Integer) o.getValue();
        note("GI " + sig); return 0;
    }
    ZipIndex zip;
    int apkFd = -1;
    final Set<String> assetsOpened = new TreeSet<>();

    // ------------------------------------------------------- vong doi
    final Map<String, DvmObject<?>> objs = new HashMap<>();
    DvmClass cwrapper() { return vm.resolveClass("com/com2us/wrapper/kernel/CWrapper"); }

    /** Mot doi tuong duy nhat cho moi class (giong singleton trong game), lop cha = CWrapper. */
    DvmObject<?> obj(String cls) {
        DvmObject<?> o = objs.get(cls);
        if (o == null) {
            DvmClass c = cls.equals("com/com2us/wrapper/font/CSGLFont")
                    ? vm.resolveClass(cls, vm.resolveClass("com/com2us/wrapper/font/CFont", cwrapper()))
                    : cls.startsWith("com/com2us/wrapper") || cls.endsWith("CUserDefined") ? vm.resolveClass(cls, cwrapper()) : vm.resolveClass(cls);
            o = c.newObject(null);
            objs.put(cls, o);
        }
        return o;
    }

    DirectBuffer direct(int size) {
        com.github.unidbg.pointer.UnidbgPointer p = emulator.getMemory().malloc(size, true).getPointer();
        p.write(0, new byte[size], 0, size);
        return new DirectBuffer(vm, p, size);
    }

    void step(String name, Runnable r) {
        System.out.println("== " + name);
        try { r.run(); } catch (Throwable t) { System.out.println("!! " + name + ": " + t); }
    }

    void wrapperInit(final String cls) {
        step("CWrapper.nativeInitialize " + cls, () -> EmuCall.call(emulator, vm, cwrapper(), obj(cls),
                "nativeInitialize(Ljava/lang/String;)V", new StringObject(vm, cls.replace('/', '.'))));
    }

    void boot(int frames) {
        try { zip = new ZipIndex(res.getPath()); } catch (Exception ex) { throw new RuntimeException(ex); }
        apkFd = emulator.getMemory().findModule("libc.so").callFunction(emulator, "open", APK_PATH, 0).intValue();
        System.out.println("== mo APK trong emulator: fd=" + apkFd + ", " + zip.map.size() + " muc zip");
        final String K = "com/com2us/wrapper/kernel/CWrapperKernel";
        step("CWrapper.nativeSetClass", () -> vm.resolveClass("com/com2us/wrapper/kernel/CWrapper").callStaticJniMethod(emulator, "nativeSetClass()V"));
        step("CWrapperData.nativeInitialize", () -> obj("com/com2us/wrapper/kernel/CWrapperData").callJniMethod(emulator, "nativeInitialize(I)V", 7 * 3600 * 1000));
        // MainActivity.onCreate: setActiveUser, setMercury, setPeppermint, setInAppV4
        step("ActiveUser.jniInitialize", () -> obj("com/com2us/module/activeuser/ActiveUser").callJniMethod(emulator,
                "jniInitialize(Lcom/com2us/module/activeuser/useragree/UserAgreeManager;)V", obj("com/com2us/module/activeuser/useragree/UserAgreeManager")));
        step("Mercury.nativeMercuryInitialize", () -> obj("com/com2us/module/mercury/Mercury").callJniMethod(emulator, "nativeMercuryInitialize()V"));
        step("HubBridge.callCSHubInitialize", () -> vm.resolveClass("com/com2us/peppermint/HubBridge").callStaticJniMethod(emulator, "callCSHubInitialize()V"));
        step("InAppV4.jniInAppV4Initialize", () -> vm.resolveClass("com/com2us/module/inappv4/InAppV4").callStaticJniMethod(emulator,
                "jniInAppV4Initialize(Lcom/com2us/module/inappv4/InAppV4;)V", obj("com/com2us/module/inappv4/InAppV4")));
        step("CCommonAPI.nativeInitialize", () -> vm.resolveClass("com/com2us/wrapper/common/CCommonAPI").callStaticJniMethod(emulator, "nativeInitialize()V"));
        wrapperInit("com/com2us/wrapper/function/CFunctionTaskRunner");
        wrapperInit("com/com2us/wrapper/kernel/CEventHandler");
        step("CEventHandler.nativeEventHandlerInitialize", () -> obj("com/com2us/wrapper/kernel/CEventHandler").callJniMethod(emulator,
                "nativeEventHandlerInitialize(Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;II)V", direct(16), direct(2048), 8, 256));
        wrapperInit("com/com2us/wrapper/game/CGameManager");
        wrapperInit("com/com2us/wrapper/kernel/CWrapperTimer");
        wrapperInit("com/com2us/wrapper/sensor/CSensor");
        wrapperInit("com/com2us/wrapper/ui/CUserInput");
        wrapperInit("com/com2us/wrapper/ui/CTextInput");
        wrapperInit("com/com2us/wrapper/font/CSGLFont");
        step("CFont.nativeFontInitialize", () -> EmuCall.call(emulator, vm, vm.resolveClass("com/com2us/wrapper/font/CFont"),
                obj("com/com2us/wrapper/font/CSGLFont"), "nativeFontInitialize([IIII)V", fontBuf, FONT_W, FONT_H, 1));
        wrapperInit("com/com2us/wrapper/media/CSoundManager");
        wrapperInit("com/com2us/wrapper/network/CHttpManager");
        wrapperInit("com/com2us/wrapper/utility/CUtility");
        wrapperInit("com/com2us/wrapper/WrapperUserDefined");
        wrapperInit(PKG.replace('.', '/') + "/CUserDefined");
        step("nativeSetScreenSize", () -> obj("com/com2us/wrapper/kernel/CWrapperData").callJniMethod(emulator,
                "nativeSetScreenSize(IIIIII)V", origW, origH, DISP_W, DISP_H, DISP_W, DISP_H));
        step("nativeStart", () -> vm.resolveClass(K).callStaticJniMethod(emulator, "nativeStart()V"));
        step("nativeStartClet", () -> vm.resolveClass(K).callStaticJniMethod(emulator, "nativeStartClet()V"));
        long t0 = System.currentTimeMillis();
        for (int f = 0; f < frames; f++) {
            final int ff = f;
            try { vm.resolveClass(K).callStaticJniMethod(emulator, "nativeProcess()V"); }
            catch (Throwable t) { System.out.println("!! nativeProcess #" + f + ": " + t); break; }
            onFrameEnd(f);
            long due = t0 + (f + 1) * 16L - System.currentTimeMillis();
            if (due > 0) try { Thread.sleep(due); } catch (InterruptedException ignored) { }
            if (f % 50 == 0) System.out.println("== frame " + f + " t=" + (System.currentTimeMillis() - t0) + "ms gl=" + gl.calls.size());
        }
    }

    void onFrameEnd(int f) {
        if (shotEvery > 0 && f % shotEvery == 0 && gl.drawCalls > 0) gl.screenshot(new File(out, String.format("f%05d.png", f)));
    }
    int shotEvery = Integer.getInteger("emu.shot", 0);

    public static void main(String[] a) throws Exception {
        File out = new File(a[2]);
        out.mkdirs();
        Inotia4Emu e = new Inotia4Emu(new File(a[0]), new File(a[1]), out);
        System.out.println("== JNI_OnLoad");
        e.dm.callJNI_OnLoad(e.emulator);
        e.boot(a.length > 3 ? Integer.parseInt(a[3]) : 3);
        System.out.println("== JNI chua xu ly:");
        for (Map.Entry<String, Integer> en : e.unknown.entrySet()) System.out.println("  " + en.getValue() + "x " + en.getKey());
        System.out.println("asset da mo: " + e.assetsOpened.size() + " " + e.assetsOpened);
        System.out.println("GL da goi: " + e.gl.calls);
        System.out.println("GL chua xu ly: " + e.gl.unhandled + " drawCalls=" + e.gl.drawCalls);
        e.gl.screenshot(new File(out, "last.png"));
        e.emulator.close();
    }
}
