package com.github.unidbg.linux.android.dvm;

import com.github.unidbg.Emulator;

/** Goi method native khai bao o lop cha (vd CWrapper.nativeInitialize) tren doi tuong lop con. */
public final class EmuCall {
    private EmuCall() { }

    public static Number call(Emulator<?> emu, VM vm, DvmClass declaring, DvmObject<?> self, String method, Object... args) {
        try {
            return DvmObject.callJniMethod(emu, vm, declaring, self, method, args);
        } finally {
            ((BaseVM) vm).deleteLocalRefs();
        }
    }
}
