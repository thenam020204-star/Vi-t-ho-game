package com.namp.vh;

/**
 * Kiem Slot cua ModMenu (T9b): 4 thanh; thanh "Tầm nhìn" 100..150 buoc 5 mac dinh 120, dinh dang "%d%%"; clamp/snap; nut "Đặt lại x1" chi dung 3 thanh dau.
 * Chay tren JVM may tinh voi android.jar trong classpath (chi nap lop, khong goi API Android):
 *   javac -cp android.jar -d cls java/src/com/namp/vh/ModMem.java java/src/com/namp/vh/ModMenu.java java/test/ModMenuSlotTest.java
 *   java -cp cls:android.jar com.namp.vh.ModMenuSlotTest
 */
public final class ModMenuSlotTest {
    static int fail;

    static void check(String what, boolean ok) {
        System.out.println((ok ? "DAT  " : "LOI  ") + what);
        if (!ok) fail++;
    }

    public static void main(String[] a) {
        ModMenu.Slot[] s = ModMenu.SLOTS;
        check("4 thanh keo", s.length == 4);
        check("MULT_COUNT = 3", ModMenu.MULT_COUNT == 3);
        ModMenu.Slot v = s[3];
        check("thanh 4 la tam nhin", v.key.equals("view") && v.label.equals("Tầm nhìn"));
        check("min 100, max 150, buoc 5, mac dinh 120", v.min == 100 && v.max == 150 && v.step == 5 && v.def == 120 && v.value == 120);
        check("dinh dang %d%%: 120 -> \"120%\"", v.show(120).equals("120%"));
        check("ghi chu khoi dong lai", "Khởi động lại game để áp dụng".equals(v.note));
        check("so nac = 10 (max-min)/step", (v.max - v.min) / v.step == 10);
        check("clamp 0 -> 100, 99 -> 100, 151 -> 150, -5 -> 100", v.clamp(0) == 100 && v.clamp(99) == 100 && v.clamp(151) == 150 && v.clamp(-5) == 100);
        check("snap 123 -> 125, 122 -> 120, 107 -> 105, 118 -> 120", v.clamp(123) == 125 && v.clamp(122) == 120 && v.clamp(107) == 105 && v.clamp(118) == 120);
        boolean all = true;
        for (int p = 100; p <= 150; p += 5) all &= v.clamp(p) == p;
        check("11 nac 100..150 giu nguyen", all);
        for (int i = 0; i < 3; i++) {
            ModMenu.Slot m = s[i];
            check("thanh " + i + " (" + m.key + ") x1..x50 buoc 1 mac dinh 1, clamp(25) = 25, clamp(51) = 50", m.min == 1 && m.max == 50 && m.step == 1 && m.def == 1 && m.clamp(25) == 25 && m.clamp(51) == 50 && m.show(7).equals("x7"));
        }
        System.out.println(fail == 0 ? "TONG KET: TAT CA DAT" : "TONG KET: " + fail + " LOI");
        System.exit(fail == 0 ? 0 : 1);
    }
}
