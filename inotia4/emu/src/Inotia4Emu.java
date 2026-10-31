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
    static final long EPOCH_MS = 1793404800000L; // 2026-10-31 00:00 UTC
    long virtMs = 0, virtTick = 0;
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
                .addBackendFactory("dynarmic".equals(System.getProperty("emu.backend"))
                        ? new com.github.unidbg.arm.backend.DynarmicFactory(true) : new Unicorn2Factory(true))
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
        // Dong ho ao: gettimeofday/time cua libc tra ve epoch co dinh + virtMs (16ms moi frame) -> chay tat dinh
        if (!Boolean.getBoolean("emu.realtime")) {
            com.github.unidbg.Module libc = emulator.getMemory().findModule("libc.so");
            com.github.unidbg.debugger.Debugger dbg1 = emulator.attach();
            dbg1.addBreakPoint(libc.findSymbolByName("gettimeofday", false).getAddress(), (emu, addr) -> {
                com.github.unidbg.arm.backend.Backend be = emu.getBackend();
                long tv = be.reg_read(unicorn.Arm64Const.UC_ARM64_REG_X0).longValue();
                long us = EPOCH_MS * 1000 + virtMs * 1000 + (virtTick++ % 1000);
                if (tv != 0) {
                    com.github.unidbg.pointer.UnidbgPointer p = com.github.unidbg.pointer.UnidbgPointer.pointer(emu, tv);
                    p.setLong(0, us / 1000000); p.setLong(8, us % 1000000);
                }
                be.reg_write(unicorn.Arm64Const.UC_ARM64_REG_X0, 0);
                be.reg_write(unicorn.Arm64Const.UC_ARM64_REG_PC, be.reg_read(unicorn.Arm64Const.UC_ARM64_REG_LR).longValue());
                return true;
            });
            dbg1.addBreakPoint(libc.findSymbolByName("time", false).getAddress(), (emu, addr) -> {
                com.github.unidbg.arm.backend.Backend be = emu.getBackend();
                long t = (EPOCH_MS + virtMs) / 1000;
                long p0 = be.reg_read(unicorn.Arm64Const.UC_ARM64_REG_X0).longValue();
                if (p0 != 0) com.github.unidbg.pointer.UnidbgPointer.pointer(emu, p0).setLong(0, t);
                be.reg_write(unicorn.Arm64Const.UC_ARM64_REG_X0, t);
                be.reg_write(unicorn.Arm64Const.UC_ARM64_REG_PC, be.reg_read(unicorn.Arm64Const.UC_ARM64_REG_LR).longValue());
                return true;
            });
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
        if (sig.startsWith("com/com2us/wrapper/font/C")) {
            String m = sig.substring(sig.indexOf("->") + 2);
            int[] buf = fontBuf.getValue();
            switch (m) {
                case "getStringWidth([BII)I":
                    return com.namp.vh.VNFont.getStringWidth(bytes(va, 0), va.getIntArg(1), va.getIntArg(2));
                case "drawText([BII)I": {
                    int size = va.getIntArg(1);
                    int w = com.namp.vh.VNFont.drawText(buf, bytes(va, 0), size, va.getIntArg(2));
                    syncFont(w * (int) (size * 1.3d));
                    logText(va, 0, "drawText size=" + size);
                    return w;
                }
                case "drawText([BII[I)I": {
                    int size = va.getIntArg(1);
                    int w = com.namp.vh.VNFont.drawTextPart(buf, FONT_W, bytes(va, 0), size, va.getIntArg(2), (int[]) va.getObjectArg(3).getValue());
                    syncFont(w * (int) (size * 1.3d));
                    return w;
                }
                case "getStringWidthHeightInRect([BIIII[I)I":
                    return com.namp.vh.VNFont.measureInRect(bytes(va, 0), va.getIntArg(1), va.getIntArg(2), va.getIntArg(3), va.getIntArg(4), (int[]) va.getObjectArg(5).getValue());
                case "drawTextInRect([BIIIII)I": {
                    byte[] b = bytes(va, 0);
                    int size = va.getIntArg(1), style = va.getIntArg(2), align = va.getIntArg(3), maxW = va.getIntArg(4), mode = va.getIntArg(5);
                    int[] o = new int[2];
                    com.namp.vh.VNFont.measureInRect(b, size, style, maxW, mode, o);
                    int r = com.namp.vh.VNFont.drawInRect(buf, b, size, style, align, maxW, mode);
                    syncFont(maxW * o[1]);
                    logText(va, 0, "drawTextInRect size=" + size + " w=" + maxW);
                    return r;
                }
            }
        }
        String C = "com/com2us/wrapper/common/CCommonAPI->";
        try {
            if (sig.equals(C + "generateHash([BLjava/lang/String;)[B"))
                return new com.github.unidbg.linux.android.dvm.array.ByteArray(vm, java.security.MessageDigest.getInstance(
                        (String) va.getObjectArg(1).getValue()).digest(bytes(va, 0)));
            if (sig.equals(C + "generateHashtoHexString([BLjava/lang/String;)Ljava/lang/String;")) {
                byte[] h = java.security.MessageDigest.getInstance((String) va.getObjectArg(1).getValue()).digest(bytes(va, 0));
                StringBuilder sb = new StringBuilder(); for (byte x : h) sb.append(String.format("%02x", x)); return str(sb.toString());
            }
        } catch (java.security.NoSuchAlgorithmException ex) { throw new RuntimeException(ex); }
        if (sig.equals(C + "generateCRC32([B)J")) { java.util.zip.CRC32 c = new java.util.zip.CRC32(); c.update(bytes(va, 0)); return c.getValue(); }
        if (sig.equals(C + "encodeBase64([B)[B")) return new com.github.unidbg.linux.android.dvm.array.ByteArray(vm, Base64.getEncoder().encode(bytes(va, 0)));
        if (sig.equals(C + "decodeBase64([B)[B")) return new com.github.unidbg.linux.android.dvm.array.ByteArray(vm, Base64.getMimeDecoder().decode(bytes(va, 0)));
        if (sig.equals(C + "getActiveNetwork()I")) return NET_TYPE;
        if (sig.equals("com/com2us/wrapper/utility/CUtility->getAppProperty([B)[B")) {
            byte[] v = appProps.get(new String(bytes(va, 0), java.nio.charset.StandardCharsets.UTF_8));
            return v == null ? null : new com.github.unidbg.linux.android.dvm.array.ByteArray(vm, v);
        }
        if (sig.equals("com/com2us/wrapper/utility/CUtility->setAppProperty([B[B)V")) {
            String k = new String(bytes(va, 0), java.nio.charset.StandardCharsets.UTF_8);
            appProps.put(k, bytes(va, 1)); System.out.println("[prop] set " + k + " = " + new String(bytes(va, 1), java.nio.charset.StandardCharsets.UTF_8));
            return VOID;
        }
        if (sig.equals("com/com2us/wrapper/utility/CUtility->removeAppProperty([B)V")) { appProps.remove(new String(bytes(va, 0), java.nio.charset.StandardCharsets.UTF_8)); return VOID; }
        if (sig.startsWith("com/com2us/wrapper/media/CSoundManager->")) {
            String m = sig.substring(sig.indexOf("->") + 2);
            if (m.startsWith("soundCreate")) return ++soundId;
            if (m.endsWith(")Z")) return true;
            if (m.endsWith(")I")) return 0;
            return VOID;
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
            // ---- ActiveUser offline: luu con tro notifier, start() -> bao thanh cong o frame sau
            case "com/com2us/module/activeuser/ActiveUser->setJNIActiveUserNotifier(J)V": auNotifier = va.getLongArg(0); return VOID;
            case "com/com2us/module/activeuser/ActiveUser->setJNIUserAgreeNotifier(J)V": auAgree = va.getLongArg(0); return VOID;
            case "com/com2us/module/activeuser/ActiveUser->start(Lcom/com2us/module/manager/ModuleManager$SERVER_STATE;)V":
                later(() -> obj("com/com2us/module/activeuser/ActiveUser").callJniMethod(emulator, "jniActiveUserNotifier(JI)V", auNotifier, 0), "ActiveUser -> jniActiveUserNotifier(0)");
                return VOID;
            case "com/com2us/module/activeuser/ActiveUser->jniGetAgeRange(J)V": {
                final long j = va.getLongArg(0);
                later(() -> obj("com/com2us/module/activeuser/ActiveUser").callJniMethod(emulator,
                        "jniGetAgeRangeNotifier(JIIIILjava/lang/String;Ljava/lang/String;)V", j, 1, 4, -1, -1, str(""), str("")), "AgeRange(1,4)");
                return VOID;
            }
            // ---- HubBridge offline (Peppermint): moi request tra JSON cuc bo
            case "com/com2us/peppermint/HubBridge->initializeEx(Ljava/lang/String;I)I": return 0;
            case "com/com2us/peppermint/HubBridge->auth(JJ)I":
                hubReply("auth", "auth", va.getLongArg(0), va.getLongArg(1));
                return 0;
            case "com/com2us/peppermint/HubBridge->asyncRequest(Ljava/lang/String;Ljava/lang/String;JJ)I": {
                String name = (String) va.getObjectArg(0).getValue();
                Object pr = va.getObjectArg(1) == null ? null : va.getObjectArg(1).getValue();
                hubReply(name, "asyncRequest " + name + " params=" + pr, va.getLongArg(2), va.getLongArg(3));
                return 0;
            }
            case "com/com2us/wrapper/WrapperUserDefined->GetStringFromXmlEx(Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/Object;": {
                String file = (String) va.getObjectArg(0).getValue(), key = (String) va.getObjectArg(1).getValue();
                int idx = va.getIntArg(2);
                List<String> arr = plist(file).get(key);
                String v = arr != null && idx < arr.size() ? arr.get(idx) : null;
                trace("xml " + file + "/" + key + "[" + idx + "] = " + v);
                if (v == null) { System.out.println("[xml] thieu " + file + "/" + key + "[" + idx + "]"); return null; }
                return new com.github.unidbg.linux.android.dvm.array.ByteArray(vm, v.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            }
            case "com/com2us/wrapper/function/CResource->closeFileDescriptor(Ljava/lang/String;)V":
            case "com/com2us/module/activeuser/ActiveUser->setServerId(Ljava/lang/String;)V":
            case "com/com2us/wrapper/WrapperUserDefined->ActivityIndicatorOpen()V":
            case "com/com2us/wrapper/WrapperUserDefined->ActivityIndicatorClose()V":
            case "com/com2us/wrapper/game/CGameManager->requestRender()V":
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
    @Override public DvmObject<?> getStaticObjectField(BaseVM vm, DvmClass c, String sig) {
        if (sig.startsWith("com/com2us/module/manager/ModuleManager$SERVER_STATE->")) return c.newObject(sig.substring(sig.indexOf("->") + 2, sig.indexOf(':')));
        note("GSF " + sig); return null;
    }
    long auNotifier, auAgree;
    final Map<String, byte[]> appProps = new TreeMap<>();
    int soundId;
    /** CCommonAPI.getActiveNetwork: 1 = khong ho tro/khong mang, 2 = WWAN, 3 = WiFi. Ban offline: 1. */
    static final int NET_TYPE = Integer.getInteger("emu.net", 1);

    static byte[] bytes(VarArg va, int i) { DvmObject<?> o = va.getObjectArg(i); return o == null ? new byte[0] : (byte[]) o.getValue(); }

    /** Chep n pixel dau cua mPixelbuffer vao con tro ma native giu tu GetIntArrayElements (global @0x32e8c8). */
    void syncFont(int n) {
        long base = dm.getModule().base;
        long ptr = com.github.unidbg.pointer.UnidbgPointer.pointer(emulator, base + 0x32e8c8).getLong(0);
        if (ptr == 0 || n <= 0) return;
        n = Math.min(n, FONT_W * FONT_H);
        int[] buf = fontBuf.getValue();
        java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(n * 4).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        for (int k = 0; k < n; k++) bb.putInt(buf[k]);
        com.github.unidbg.pointer.UnidbgPointer.pointer(emulator, ptr).write(0, bb.array(), 0, n * 4);
    }

    final Set<String> textsSeen = new LinkedHashSet<>();
    void logText(VarArg va, int i, String what) {
        String t = new String(bytes(va, i), java.nio.charset.StandardCharsets.UTF_8);
        if (textsSeen.add(t)) System.out.println("[text] " + what + " : " + t.replace("\n", "\\n"));
    }
    final Map<String, Map<String, List<String>>> plists = new HashMap<>();

    /** res/xml/<file>.xml dang plist: key -> mang string (chi so = ngon ngu, 0 = EN). */
    Map<String, List<String>> plist(String file) {
        return plists.computeIfAbsent(file, f -> {
            Map<String, List<String>> m = new LinkedHashMap<>();
            File x = new File(System.getProperty("emu.resxml", "../work/dec/res/xml"), f + ".xml");
            try {
                String t = new String(java.nio.file.Files.readAllBytes(x.toPath()), java.nio.charset.StandardCharsets.UTF_8);
                java.util.regex.Matcher mm = java.util.regex.Pattern.compile("<key>(.*?)</key>\\s*<array>(.*?)</array>", java.util.regex.Pattern.DOTALL).matcher(t);
                while (mm.find()) {
                    List<String> arr = new ArrayList<>();
                    java.util.regex.Matcher sm = java.util.regex.Pattern.compile("<string>(.*?)</string>|<string/>", java.util.regex.Pattern.DOTALL).matcher(mm.group(2));
                    while (sm.find()) arr.add(unxml(sm.group(1) == null ? "" : sm.group(1)));
                    m.put(mm.group(1), arr);
                }
            } catch (java.io.IOException ex) { System.out.println("[xml] khong doc duoc " + x); }
            return m;
        });
    }
    static String unxml(String s) {
        return s.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("&apos;", "'").replace("\\'", "'").replace("\\\"", "\"").replace("&amp;", "&");
    }

    /** JSON offline cho tung request Hub (giong lop Java se dua vao APK). */
    static String offlineHubJson(String name) {
        switch (name) {
            case "auth": return "{\"type\":\"auth\",\"error_code\":1,\"error_msg\":\"offline\"}";
            case "device/registered": return "{\"type\":\"device/registered\",\"error_code\":0,\"error_msg\":\"\",\"is_registered\":false}";
            default: return "{\"type\":\"" + name + "\",\"error_code\":0,\"error_msg\":\"\"}";
        }
    }

    void hubReply(String name, String what, final long j, final long j2) {
        final String json = offlineHubJson(name);
        System.out.println("[hub] " + what + " -> " + json);
        later(() -> vm.resolveClass("com/com2us/peppermint/HubBridge").callStaticJniMethod(emulator,
                "hubCallbackWithJSON(Ljava/lang/String;JJ)V", str(json), j, j2), "hubCallback " + name);
    }
    final List<Object[]> laterQ = new ArrayList<>();
    /** Goi lai native o cuoi frame (giong viewEx.queueEvent / runOnGlThread). */
    void later(Runnable r, String what) { laterQ.add(new Object[]{r, what}); }
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
        evManage = direct(16); evQueue = direct(2048);
        step("CEventHandler.nativeEventHandlerInitialize", () -> obj("com/com2us/wrapper/kernel/CEventHandler").callJniMethod(emulator,
                "nativeEventHandlerInitialize(Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;II)V", evManage, evQueue, 8, 256));
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
            catch (Throwable t) {
                System.out.println("!! nativeProcess #" + f + ": " + t);
                try { emulator.getUnwinder().unwind(); } catch (Throwable t2) { System.out.println("unwind loi: " + t2); }
                break;
            }
            onFrameEnd(f);
            virtMs += 16;
            if (Boolean.getBoolean("emu.realtime")) {
                long due = t0 + (f + 1) * 16L - System.currentTimeMillis();
                if (due > 0) try { Thread.sleep(due); } catch (InterruptedException ignored) { }
            }
            if (f % 50 == 0) System.out.println("== frame " + f + " t=" + (System.currentTimeMillis() - t0) + "ms gl=" + gl.calls.size());
        }
    }

    DirectBuffer evManage, evQueue;

    /** CEventHandler.onEvent: hang doi vong 256 muc x 8 byte (big-endian), chi so ghi/doc la long LE. */
    boolean addEvent(int type, int a, int b, int c, int d) {
        com.github.unidbg.pointer.UnidbgPointer m = evManage.getValue(), q = evQueue.getValue();
        long head = m.getLong(0), tail = m.getLong(8);
        long j = head + 1;
        if (j % 256 == tail) return false;
        long off = (head % 256) * 8;
        byte[] e = {(byte) (type >> 8), (byte) type, (byte) (a >> 8), (byte) a, (byte) (b >> 8), (byte) b, (byte) c, (byte) d};
        q.write(off, e, 0, 8);
        m.setLong(0, j % 256);
        return true;
    }

    /** Kich ban: "frame:lenh:thamso;..."  lenh = tap x,y (toa do 480x320) | shot ten | key ma */
    final Map<Integer, List<String[]>> script = new HashMap<>();
    void loadScript(String sc) {
        if (sc == null) return;
        for (String part : sc.split(";")) {
            String[] t = part.trim().split(":", 3);
            if (t.length < 2) continue;
            script.computeIfAbsent(Integer.parseInt(t[0]), k -> new ArrayList<>()).add(new String[]{t[1], t.length > 2 ? t[2] : ""});
        }
    }
    final Map<Integer, int[]> pendingUp = new HashMap<>();

    void runScript(int f) {
        int[] up = pendingUp.remove(f);
        if (up != null) addEvent(3, up[0], up[1], 1, 1);
        List<String[]> cmds = script.get(f);
        if (cmds == null) return;
        for (String[] c : cmds) {
            switch (c[0]) {
                case "tap": {
                    String[] xy = c[1].split(",");
                    int x = Integer.parseInt(xy[0]), y = Integer.parseInt(xy[1]);
                    addEvent(1, x, y, 1, 1);
                    pendingUp.put(f + 4, new int[]{x, y});
                    System.out.println("== frame " + f + " tap " + x + "," + y);
                    break;
                }
                case "shot": gl.screenshot(new File(out, c[1] + ".png")); System.out.println("== frame " + f + " shot " + c[1]); break;
                case "key": addEvent(4096, Integer.parseInt(c[1]), -1, -1, -1); addEvent(4097, Integer.parseInt(c[1]), -1, -1, -1); break;
            }
        }
    }

    void onFrameEnd(int f) {
        runScript(f);
        while (!laterQ.isEmpty()) {
            Object[] t = laterQ.remove(0);
            System.out.println("== frame " + f + " callback " + t[1]);
            try { ((Runnable) t[0]).run(); } catch (Throwable ex) { System.out.println("!! callback " + t[1] + ": " + ex); }
        }
        if (shotEvery > 0 && f % shotEvery == 0 && gl.drawCalls > 0) gl.screenshot(new File(out, String.format("f%05d.png", f)));
    }
    int shotEvery = Integer.getInteger("emu.shot", 0);

    public static void main(String[] a) throws Exception {
        File out = new File(a[2]);
        out.mkdirs();
        Inotia4Emu e = new Inotia4Emu(new File(a[0]), new File(a[1]), out);
        System.out.println("== JNI_OnLoad");
        e.dm.callJNI_OnLoad(e.emulator);
        e.loadScript(System.getProperty("emu.script"));
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
