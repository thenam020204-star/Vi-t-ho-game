package com.namp.vh;

import com.com2us.module.activeuser.ActiveUser;
import com.com2us.peppermint.HubBridge;
import com.com2us.wrapper.function.CFunction;

/**
 * Ban offline: thay cac lop goi mang (Hive ActiveUser / Peppermint Hub) bang ket qua cuc bo.
 * Moi ket qua duoc tra ve libgame.so tren luong GL (CFunction.runOnGlThread), giong SDK goc
 * (SDK tra ket qua bat dong bo). Gia tri JSON da kiem chung bang emulator unidbg (emu/).
 */
public final class Offline {
    private Offline() { }

    /** ActiveUser.start(SERVER_STATE): Hive Auth init -> coi nhu thanh cong (DID co san). */
    public static void activeUserStart(final ActiveUser au) {
        CFunction.runOnGlThread(new Runnable() {
            public void run() { ActiveUser.namp_notify(au, 0); }
        });
    }

    /** ActiveUser.jniGetAgeRange(j): khong co du lieu tuoi (userStatus 4), nhu khi ageRange == null. */
    public static void ageRange(final ActiveUser au, final long j) {
        CFunction.runOnGlThread(new Runnable() {
            public void run() { ActiveUser.namp_ageRange(au, j, 1, 4, -1, -1, "", ""); }
        });
    }

    /** JSON cuc bo cho tung request Hub. */
    static String json(String name) {
        if ("device/registered".equals(name))
            // error_code 0 + is_registered false -> native: "chua dang nhap Hub", danh dau xong (hubCallbackRegisteredDevice)
            return "{\"type\":\"device/registered\",\"error_code\":0,\"error_msg\":\"\",\"is_registered\":false}";
        // auth va moi request khac: loi "offline" (error_code != 0 va != 2001) -> native khong dang nhap,
        // khong hien popup dang nhap (2001), khong goi HttpConnectPage.
        return "{\"type\":\"" + name + "\",\"error_code\":1,\"error_msg\":\"offline\"}";
    }

    /** HubBridge.asyncRequest/auth: tra JSON qua hubCallbackWithJSON. Tra 0 = da nhan request. */
    public static int hubAsync(final String name, final long j, final long j2) {
        final String js = json(name == null ? "" : name);
        CFunction.runOnGlThread(new Runnable() {
            public void run() { if (j != 0) HubBridge.namp_cb(js, j, j2); }
        });
        return 0;
    }
}
