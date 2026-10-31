/*
 * twommod — menu mod chơi đơn cho This War of Mine 1.6.9 (arm64, libAndroidGame.so).
 * Hook bằng cách ghi đè mục GOT (R_AARCH64_JUMP_SLOT) của libAndroidGame.so: mọi lời gọi hàm export
 * bên trong thư viện đều đi qua PLT -> GOT, nên không cần sửa mã máy.
 *
 * Bằng chứng offset (disassembly libAndroidGame.so, xem mod/README.md):
 *   KosovoHPComponent:  +0x30 config (config+0x20 = HP tối đa), +0x38 host (+ -0x2d8 = entity), +0x44 HP hiện tại
 *   HPReducer:          +0x00 sát thương (float), +0x24 cờ "chết ngay"
 *   KosovoScene:        +0x108 số dweller, +0x110 mảng handle (handle -> entity)
 *   KosovoItemEntity:   +0x20d0 KosovoInventoryContainer (ba lô)
 *   InventoryContainer: +0x18 số phần tử, +0x20 mảng phần tử (0x40 byte), +0x70 SlotCount (<0 = không giới hạn)
 *   InventoryElement:   +0x18 config vật phẩm, +0x20 NameString, +0x28 số lượng
 *   gKosovoItemConfig:  +0x18 số mục, +0x20 mảng KosovoItemElementConfig (0x2f0 byte), +0x34 StackSize
 *   gKosovoMainParams:  +0x44 độ dài 1 ngày (giây thật, float) — FlowStateDay::OnTick, GetDayTimeRatio, GetHourDurationInSeconds
 *   gKosovoGlobalState: +0xe4 "ScavengeTime" (độ dài đêm lục lọi, float) — đặt ở InitScavengeScene, đọc ở FlowStateScavenge
 *   KosovoCraftingBaseComponent: +0x44 trạng thái (3 = người đang làm), +0x50 đã làm, +0x54 tổng thời gian;
 *                       OnTickCrafting(float) = vtable+0x130 (4 vtable: CraftingBase/Crafting/Construction/Upgrade)
 *   LUAConfigHelper::PS4IsPlusSubscriptionActive(): trên Android = "return false", chỉ được gọi từ wrapper tolua
 *                       (gConfigHelper trong Lua) -> dùng làm công tắc cho script NonInstantAction đã vá
 */
#include <jni.h>
#include <dlfcn.h>
#include <link.h>
#include <elf.h>
#include <string.h>
#include <stdint.h>
#include <sys/mman.h>
#include <unistd.h>
#include <android/log.h>

#define TAG "twommod"
#define LOG(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define P(p, off, T) (*(T *)((char *)(p) + (off)))

enum { OPT_GOD, OPT_INFBAG, OPT_STACK99, OPT_LOOTMUL, OPT_INSTANT, OPT_DAYSPEED, OPT_N };
static volatile int g_opt[OPT_N] = {0, 0, 0, 1, 0, 4};
/* OPT_DAYSPEED: chỉ số vào bảng tốc độ; 4 = x1 (gốc) */
#define NSPEED 9
static const float SPEEDS[NSPEED] = {0.25f, 1.f / 3.f, 0.5f, 0.75f, 1.f, 1.5f, 2.f, 3.f, 4.f};
static int g_ok[OPT_N];            /* tính năng đã kiểm tra an toàn */

/* ---------------- ký hiệu từ libAndroidGame.so ---------------- */
static void *g_lib;
static uintptr_t g_base;
static void **p_scene;             /* gKosovoScene (KosovoScene*) */
static char *p_itemcfg;            /* gKosovoItemConfig */
static void *p_delegate;           /* gKosovoGameDelegate */
static int (*f_GetDwellerIndex)(void *scene, void *ent);
static int (*f_IsScavenge)(void *delegate);
static int (*f_ContAdd)(void *cont, void *name, int n);
static int (*f_CanTake)(void *cont, void *name, int ignore);
static char *p_mainparams;         /* gKosovoMainParams */
static char *p_globalstate;        /* gKosovoGlobalState */

/* hàm gốc */
static void (*o_ReduceHP)(void *, void *);
static void (*o_SceneOnTick)(void *);
static void (*o_AddSlotCount)(void *, int);
static int (*o_Steal)(void *, void *, void *, int);
static int (*o_StealWE)(void *, void *, void *, int);
static int (*o_StealSpec)(void *, void *, void *, int, int);
static int (*o_StealSpecWE)(void *, void *, void *, int, int);
static void (*o_GiveTo)(void *, void *, unsigned, int);
static void (*o_GiveToSib)(void *, int, int);
static void (*o_TickCraft)(void *, float);
static uintptr_t (*o_InitScav)(void *, void *, float, void *, void *);
static int (*o_PS4Plus)(void *);

/* vùng mã của KosovoUIPanelInventory::* (bảng thùng đồ / kho — không gồm bảng giao dịch) */
#define MAXR 128
static uintptr_t g_r0[MAXR], g_r1[MAXR];
static int g_nr;
static __thread int t_ui;

static int in_panel(uintptr_t lr) {
    for (int i = 0; i < g_nr; i++) if (lr >= g_r0[i] && lr < g_r1[i]) return 1;
    return 0;
}

/* ---------------- đọc bảng động của thư viện trong bộ nhớ ---------------- */
static int find_cb(struct dl_phdr_info *info, size_t sz, void *data) {
    (void)sz; (void)data;
    if (info->dlpi_name && strstr(info->dlpi_name, "libAndroidGame.so")) { g_base = info->dlpi_addr; return 1; }
    return 0;
}

static ElfW(Dyn) *dyn_section(void) {
    ElfW(Ehdr) *eh = (ElfW(Ehdr) *)g_base;
    ElfW(Phdr) *ph = (ElfW(Phdr) *)(g_base + eh->e_phoff);
    for (int i = 0; i < eh->e_phnum; i++)
        if (ph[i].p_type == PT_DYNAMIC) return (ElfW(Dyn) *)(g_base + ph[i].p_vaddr);
    return 0;
}

static ElfW(Sym) *g_symtab; static const char *g_strtab; static ElfW(Rela) *g_jmprel; static size_t g_njmp; static size_t g_nsym;

static int read_dynamic(void) {
    ElfW(Dyn) *d = dyn_section();
    if (!d) return 0;
    uint32_t *hash = 0;
    for (; d->d_tag != DT_NULL; d++) {
        uintptr_t v = d->d_un.d_ptr; if (v < g_base) v += g_base;
        switch (d->d_tag) {
        case DT_SYMTAB: g_symtab = (ElfW(Sym) *)v; break;
        case DT_STRTAB: g_strtab = (const char *)v; break;
        case DT_JMPREL: g_jmprel = (ElfW(Rela) *)v; break;
        case DT_PLTRELSZ: g_njmp = d->d_un.d_val / sizeof(ElfW(Rela)); break;
        case DT_HASH: hash = (uint32_t *)v; break;
        }
    }
    if (hash) g_nsym = hash[1];
    return g_symtab && g_strtab && g_jmprel && g_njmp;
}

/* thay mục GOT của hàm tên `name`; trả về số mục đã thay */
static int got_hook(const char *name, void *hook, void **orig) {
    int n = 0;
    for (size_t i = 0; i < g_njmp; i++) {
        ElfW(Rela) *r = &g_jmprel[i];
        if (ELF64_R_TYPE(r->r_info) != R_AARCH64_JUMP_SLOT) continue;
        if (strcmp(g_strtab + g_symtab[ELF64_R_SYM(r->r_info)].st_name, name)) continue;
        void **slot = (void **)(g_base + r->r_offset);
        uintptr_t pg = (uintptr_t)slot & ~(uintptr_t)(getpagesize() - 1);
        if (mprotect((void *)pg, getpagesize(), PROT_READ | PROT_WRITE)) { LOG("mprotect lỗi %s", name); return 0; }
        if (orig && !*orig) *orig = *slot;
        *slot = hook; n++;
    }
    if (!n) LOG("không thấy GOT: %s", name);
    return n;
}

static void collect_panel_ranges(void) {
    const char *pre = "_ZN22KosovoUIPanelInventory";
    for (size_t i = 0; i < g_nsym && g_nr < MAXR; i++) {
        ElfW(Sym) *s = &g_symtab[i];
        if (!s->st_value || !s->st_size || ELF64_ST_TYPE(s->st_info) != STT_FUNC) continue;
        if (strncmp(g_strtab + s->st_name, pre, strlen(pre))) continue;
        g_r0[g_nr] = g_base + s->st_value; g_r1[g_nr] = g_r0[g_nr] + s->st_size; g_nr++;
    }
}

/* ---------------- tiện ích game ---------------- */
static void *scene(void) { return p_scene ? *p_scene : 0; }

static int dweller_count(void *sc) { return P(sc, 0x108, int); }
static char *dweller_at(void *sc, int i) {
    void **h = P(sc, 0x110, void **);
    void **e = h ? (void **)h[i] : 0;
    return e ? (char *)*e : 0;
}
static int is_dweller_bag(void *cont) {
    void *sc = scene(); if (!sc || !cont) return 0;
    int n = dweller_count(sc); if (n < 0 || n > 64) return 0;
    for (int i = 0; i < n; i++) { char *e = dweller_at(sc, i); if (e && e + 0x20d0 == (char *)cont) return 1; }
    return 0;
}
static int scavenging(void) { return p_delegate && f_IsScavenge && f_IsScavenge(p_delegate); }

/* ---------------- 1. bất tử khi lục lọi ---------------- */
static void h_ReduceHP(void *self, void *red) {
    if (g_opt[OPT_GOD] && g_ok[OPT_GOD] && scavenging()) {
        char *host = P(self, 0x38, char *);
        void *sc = scene();
        if (host && sc && f_GetDwellerIndex(sc, host - 0x2d8) >= 0) {
            float hp = P(self, 0x44, float);
            void *cfg = P(self, 0x30, void *);
            float maxhp = cfg ? P(cfg, 0x20, float) : 0.f;
            float floor_ = maxhp > 0.f ? maxhp * 0.02f : 0.01f;     /* máu không xuống dưới 2% */
            float allow = hp - floor_; if (allow < 0.f) allow = 0.f;
            P(red, 0x24, uint8_t) = 0;                              /* bỏ cờ chết ngay */
            if (P(red, 0, float) > allow) P(red, 0, float) = allow;
        }
    }
    o_ReduceHP(self, red);
}

/* ---------------- 2. ba lô không giới hạn ---------------- */
#define MAXB 16
static void *g_bag[MAXB]; static int g_bagslots[MAXB]; static int g_nbag;

static int bag_idx(void *c) { for (int i = 0; i < g_nbag; i++) if (g_bag[i] == c) return i; return -1; }

static void h_AddSlotCount(void *cont, int n) {
    int i = bag_idx(cont);
    if (i >= 0 && P(cont, 0x70, int) < 0) { g_bagslots[i] += n; if (g_bagslots[i] < 0) g_bagslots[i] = 0; return; }
    o_AddSlotCount(cont, n);
}

static void tick_bags(void) {
    void *sc = scene(); if (!sc) return;
    int n = dweller_count(sc); if (n < 0 || n > 64) return;
    if (g_opt[OPT_INFBAG] && g_ok[OPT_INFBAG]) {
        for (int k = 0; k < n; k++) {
            char *e = dweller_at(sc, k); if (!e) continue;
            void *c = e + 0x20d0; int s = P(c, 0x70, int);
            if (s < 0 || bag_idx(c) >= 0 || g_nbag >= MAXB) continue;
            g_bag[g_nbag] = c; g_bagslots[g_nbag++] = s; P(c, 0x70, int) = -1;
        }
    } else if (g_nbag) {               /* trả số ô gốc — chỉ cho ba lô vẫn còn thuộc dweller */
        for (int i = 0; i < g_nbag; i++)
            if (is_dweller_bag(g_bag[i]) && P(g_bag[i], 0x70, int) < 0) P(g_bag[i], 0x70, int) = g_bagslots[i];
        g_nbag = 0;
    }
    /* dweller chết/rời đi: bỏ khỏi danh sách để không ghi vào vùng nhớ đã giải phóng */
    for (int i = 0; i < g_nbag; i++)
        if (!is_dweller_bag(g_bag[i])) { g_bag[i] = g_bag[--g_nbag]; g_bagslots[i] = g_bagslots[g_nbag]; i--; }
}

/* ---------------- 3. mỗi ô 99 ---------------- */
#define MAXI 1024
static uint32_t g_stack0[MAXI]; static int g_nstack; static int g_stack_on;

static void tick_stack(void) {
    if (!p_itemcfg || !g_ok[OPT_STACK99]) return;
    int n = P(p_itemcfg, 0x18, int); char *arr = P(p_itemcfg, 0x20, char *);
    if (!arr || n <= 0 || n > MAXI) return;
    int want = g_opt[OPT_STACK99];
    if (want && (!g_stack_on || n != g_nstack)) {
        for (int i = 0; i < n; i++) {
            uint32_t *s = (uint32_t *)(arr + i * 0x2f0 + 0x34);
            if (!g_stack_on || i >= g_nstack) g_stack0[i] = *s;
            if (g_stack0[i] < 99) *s = 99;
        }
        g_nstack = n; g_stack_on = 1;
    } else if (!want && g_stack_on) {
        for (int i = 0; i < n && i < g_nstack; i++) *(uint32_t *)(arr + i * 0x2f0 + 0x34) = g_stack0[i];
        g_stack_on = 0;
    }
}

static int check_stack_layout(void) {     /* mọi StackSize phải là số dương hợp lý */
    int n = P(p_itemcfg, 0x18, int); char *arr = P(p_itemcfg, 0x20, char *);
    if (!arr || n <= 0 || n > MAXI) return -1;            /* chưa nạp: kiểm lại sau */
    for (int i = 0; i < n; i++) { uint32_t s = *(uint32_t *)(arr + i * 0x2f0 + 0x34); if (s == 0 || s > 10000) return 0; }
    return 1;
}

/* ---------------- 4. hệ số nhặt đồ ---------------- */
#define MAXE 256
typedef struct { uintptr_t name; int cnt; } snap_t;

static int snapshot(void *c, snap_t *s) {
    int n = P(c, 0x18, int); char *a = P(c, 0x20, char *);
    if (n < 0 || n > MAXE || (n && !a)) return -1;
    for (int i = 0; i < n; i++) { s[i].name = P(a + i * 0x40, 0x20, uintptr_t); s[i].cnt = P(a + i * 0x40, 0x28, int); }
    return n;
}

static void apply_mult(void *dst, snap_t *before, int nb) {
    snap_t after[MAXE]; int na = snapshot(dst, after); if (na < 0) return;
    int mul = g_opt[OPT_LOOTMUL];
    snap_t add[MAXE]; int nadd = 0;
    for (int i = 0; i < na; i++) {
        int old = 0;
        for (int j = 0; j < nb; j++) if (before[j].name == after[i].name) { old = before[j].cnt; break; }
        int d = after[i].cnt - old;
        if (d > 0) { add[nadd].name = after[i].name; add[nadd++].cnt = d * (mul - 1); }
    }
    for (int k = 0; k < nadd; k++) {      /* tìm lại phần tử mỗi lần vì Add có thể cấp phát lại mảng */
        int n = P(dst, 0x18, int); char *a = P(dst, 0x20, char *);
        for (int i = 0; i < n; i++) {
            void *nm = a + i * 0x40 + 0x20;
            if (*(uintptr_t *)nm != add[k].name) continue;
            int extra = add[k].cnt;
            if (P(dst, 0x70, int) >= 0) { int room = f_CanTake(dst, nm, 0); if (extra > room) extra = room; }
            if (extra > 0) f_ContAdd(dst, nm, extra);
            break;
        }
    }
}

static int loot_ctx(void *dst, void *src, uintptr_t lr) {
    return g_ok[OPT_LOOTMUL] && g_opt[OPT_LOOTMUL] > 1 && (t_ui > 0 || in_panel(lr)) && scavenging()
        && is_dweller_bag(dst) && !is_dweller_bag(src);
}

#define LR() ((uintptr_t)__builtin_return_address(0))
#define WRAP_STEAL(CALL)                                                          \
    snap_t b[MAXE]; int nb = snapshot(dst, b);                                    \
    int r = CALL; if (nb >= 0) apply_mult(dst, b, nb); return r;

static int h_Steal(void *dst, void *src, void *nm, int c) {
    if (!loot_ctx(dst, src, LR())) return o_Steal(dst, src, nm, c);
    WRAP_STEAL(o_Steal(dst, src, nm, c))
}
static int h_StealWE(void *dst, void *src, void *nm, int c) {
    if (!loot_ctx(dst, src, LR())) return o_StealWE(dst, src, nm, c);
    WRAP_STEAL(o_StealWE(dst, src, nm, c))
}
static int h_StealSpec(void *dst, void *src, void *el, int a, int c) {
    if (!loot_ctx(dst, src, LR())) return o_StealSpec(dst, src, el, a, c);
    WRAP_STEAL(o_StealSpec(dst, src, el, a, c))
}
static int h_StealSpecWE(void *dst, void *src, void *el, int a, int c) {
    if (!loot_ctx(dst, src, LR())) return o_StealSpecWE(dst, src, el, a, c);
    WRAP_STEAL(o_StealSpecWE(dst, src, el, a, c))
}
static void h_GiveTo(void *self, void *to, unsigned n, int b) {
    int ui = in_panel(LR()); t_ui += ui; o_GiveTo(self, to, n, b); t_ui -= ui;
}
static void h_GiveToSib(void *self, int a, int b) {
    int ui = in_panel(LR()); t_ui += ui; o_GiveToSib(self, a, b); t_ui -= ui;
}

/* ---------------- 5. công việc xong tức thì ---------------- */
/* chế tạo / xây / nâng cấp (native): cho đúng phần thời gian còn lại của món đang làm, phần còn lại do mã gốc xử lý */
static void h_TickCraft(void *c, float dt) {
    if (g_opt[OPT_INSTANT] && P(c, 0x44, int) == 3) {
        float done = P(c, 0x50, float), total = P(c, 0x54, float);
        if (total > 0.f && total > done) { float need = (total - done) * 1.0001f + 1e-4f; if (dt < need) dt = need; }
    }
    o_TickCraft(c, dt);
}

/* hành động Lua (lục đồ, dọn, chặt, dỡ chướng ngại, mở khoá): NonInstantAction:OnUpdateTime trong common.dat
   đã vá (mod/lua/patch_lua.py) hỏi gConfigHelper:PS4IsPlusSubscriptionActive(). Lua tự gọi xuống, native không
   bao giờ gọi vào Lua (tránh đụng Lua state khi game tạm dừng / nạp lại). */
static int h_PS4Plus(void *self) { (void)self; return g_opt[OPT_INSTANT] != 0; }

/* ---------------- 6. tốc độ ngày / đêm ---------------- */
static float speed(void) { int i = g_opt[OPT_DAYSPEED]; return (i >= 0 && i < NSPEED) ? SPEEDS[i] : 1.f; }

/* ban ngày: đổi độ dài ngày (MainParams không nằm trong save). Mọi thứ tính theo giờ trong game
   (chế tạo, đói, mệt, nhiên liệu, lò sưởi) đi qua giá trị này nên giãn/co cùng nhau. */
static float g_day0, g_dayset;
static void tick_day(void) {
    float *p = (float *)(p_mainparams + 0x44), cur = *p, sp = speed();
    if (g_dayset == 0.f || cur != g_dayset) {          /* chưa đặt, hoặc game nạp lại cấu hình */
        if (!(cur > 1.f && cur < 1e6f)) return;
        g_day0 = cur; g_dayset = 0.f;
    }
    if (sp == 1.f) { if (g_dayset != 0.f) { *p = g_day0; g_dayset = 0.f; } return; }
    float want = g_day0 / sp;
    if (cur != want) *p = want;
    g_dayset = want;
}

/* ban đêm: độ dài đêm lục lọi được đặt mỗi đêm ở InitScavengeScene (100000 = không giới hạn, giữ nguyên) */
#define SCAV_MAX 50000.f
static float g_sc0, g_scset;
static uintptr_t h_InitScav(void *gs, void *name, float t, void *g1, void *g2) {
    float sp = speed(), t2 = (t > 0.f && t < SCAV_MAX && sp != 1.f) ? t / sp : t;
    uintptr_t r = o_InitScav(gs, name, t2, g1, g2);
    g_sc0 = t; g_scset = P(gs, 0xe4, float);
    return r;
}
static void tick_night(void) {
    if (!scavenging()) return;
    float *p = (float *)(p_globalstate + 0xe4), cur = *p, sp = speed();
    if (g_scset == 0.f || cur != g_scset) {            /* giá trị không do mod đặt (vd. vừa nạp save) */
        if (!(cur > 0.f && cur < SCAV_MAX)) return;
        g_sc0 = cur; g_scset = cur;
    }
    float want = g_sc0 / sp;
    if (cur != want) *p = want;
    g_scset = want;
}

/* ---------------- nhịp mỗi khung hình ---------------- */
static void h_SceneOnTick(void *sc) {
    o_SceneOnTick(sc);
    if (p_itemcfg && !g_ok[OPT_STACK99]) { int c = check_stack_layout(); if (c == 1) g_ok[OPT_STACK99] = 1; }
    tick_stack();
    tick_bags();
    if (g_ok[OPT_DAYSPEED]) { tick_day(); tick_night(); }
}

/* ---------------- khởi tạo ---------------- */
#define SYM(var, name) do { *(void **)&(var) = dlsym(g_lib, name); if (!(var)) LOG("thiếu %s", name); } while (0)

/* thay một ô vtable, chỉ khi ô đó đang trỏ đúng hàm mong đợi */
static int vt_hook(const char *vt, size_t off, void *expect, void *hook) {
    char *v = dlsym(g_lib, vt);
    if (!v || !expect) { LOG("thiếu vtable %s", vt); return 0; }
    void **slot = (void **)(v + off);
    if (*slot != expect && *slot != hook) { LOG("vtable %s+%#zx không khớp", vt, off); return 0; }
    uintptr_t pg = (uintptr_t)slot & ~(uintptr_t)(getpagesize() - 1);
    if (mprotect((void *)pg, getpagesize(), PROT_READ | PROT_WRITE)) { LOG("mprotect lỗi %s", vt); return 0; }
    *slot = hook;
    mprotect((void *)pg, getpagesize(), PROT_READ);
    return 1;
}

static int install(void) {
    g_lib = dlopen("libAndroidGame.so", RTLD_NOW | RTLD_NOLOAD);
    dl_iterate_phdr(find_cb, 0);
    if (!g_lib || !g_base || !read_dynamic()) { LOG("không mở được libAndroidGame.so"); return 0; }
    SYM(p_scene, "gKosovoScene");
    SYM(p_itemcfg, "gKosovoItemConfig");
    SYM(p_delegate, "gKosovoGameDelegate");
    SYM(f_GetDwellerIndex, "_ZN11KosovoScene15GetDwellerIndexEP16KosovoGameEntity");
    SYM(f_IsScavenge, "_ZNK18KosovoGameDelegate10IsScavengeEv");
    SYM(f_ContAdd, "_ZN24KosovoInventoryContainer3AddERK10NameStringi");
    SYM(f_CanTake, "_ZN24KosovoInventoryContainer7CanTakeERK10NameStringb");
    SYM(p_mainparams, "gKosovoMainParams");
    SYM(p_globalstate, "gKosovoGlobalState");
    SYM(o_TickCraft, "_ZN27KosovoCraftingBaseComponent14OnTickCraftingEf");
    collect_panel_ranges();
    int tick = got_hook("_ZN11KosovoScene6OnTickEv", h_SceneOnTick, (void **)&o_SceneOnTick);

    g_ok[OPT_GOD] = tick && p_scene && f_GetDwellerIndex && f_IsScavenge && p_delegate
        && got_hook("_ZN17KosovoHPComponent8ReduceHPEP9HPReducer", h_ReduceHP, (void **)&o_ReduceHP);
    g_ok[OPT_INFBAG] = tick && p_scene
        && got_hook("_ZN24KosovoInventoryContainer12AddSlotCountEi", h_AddSlotCount, (void **)&o_AddSlotCount);
    g_ok[OPT_STACK99] = 0;   /* bật sau khi kiểm bảng vật phẩm (check_stack_layout) */
    if (p_scene && f_IsScavenge && f_ContAdd && f_CanTake && g_nr > 0) {
        int a = got_hook("_ZN24KosovoInventoryContainer5StealERS_RK10NameStringi", h_Steal, (void **)&o_Steal);
        int b = got_hook("_ZN24KosovoInventoryContainer20StealWithoutEquippedERS_RK10NameStringi", h_StealWE, (void **)&o_StealWE);
        int c = got_hook("_ZN24KosovoInventoryContainer13StealSpecificERS_PK22KosovoInventoryElementii", h_StealSpec, (void **)&o_StealSpec);
        int d = got_hook("_ZN24KosovoInventoryContainer28StealSpecificWithoutEquippedERS_PK22KosovoInventoryElementii", h_StealSpecWE, (void **)&o_StealSpecWE);
        int e = got_hook("_ZN22KosovoUIItemsPresenter14GiveSelectedToEPS_jb", h_GiveTo, (void **)&o_GiveTo);
        int f = got_hook("_ZN22KosovoUIItemsPresenter22GiveSelectedToSiblingsEbb", h_GiveToSib, (void **)&o_GiveToSib);
        g_ok[OPT_LOOTMUL] = a && b && c && d && e && f;
    }
    int vt = 0;
    if (o_TickCraft) {
        static const char *VT[] = {"_ZTV27KosovoCraftingBaseComponent", "_ZTV23KosovoCraftingComponent",
                                   "_ZTV27KosovoConstructionComponent", "_ZTV22KosovoUpgradeComponent"};
        for (int i = 0; i < 4; i++) vt += vt_hook(VT[i], 0x130, (void *)o_TickCraft, (void *)h_TickCraft);
    }
    int lua = got_hook("_ZNK15LUAConfigHelper27PS4IsPlusSubscriptionActiveEv", (void *)h_PS4Plus, (void **)&o_PS4Plus);
    g_ok[OPT_INSTANT] = lua || vt;
    g_ok[OPT_DAYSPEED] = tick && p_mainparams && p_globalstate && p_delegate && f_IsScavenge
        && got_hook("_ZN17KosovoGlobalState17InitScavengeSceneERK10NameStringfRK10SimpleGUIDS5_", h_InitScav, (void **)&o_InitScav);
    LOG("cài xong: god=%d bag=%d loot=%d instant=%d (vtable %d/4, lua %d) dayspeed=%d panelRanges=%d",
        g_ok[0], g_ok[1], g_ok[3], g_ok[4], vt, lua, g_ok[5], g_nr);
    return 1;
}

/* ---------------- JNI ---------------- */
JNIEXPORT void JNICALL Java_com_twommod_ModMenu_nSet(JNIEnv *env, jclass cls, jint id, jint v) {
    (void)env; (void)cls;
    if (id < 0 || id >= OPT_N) return;
    if (id == OPT_LOOTMUL) { if (v < 1) v = 1; if (v > 6) v = 6; }
    if (id == OPT_DAYSPEED) { if (v < 0) v = 0; if (v >= NSPEED) v = NSPEED - 1; }
    g_opt[id] = v;
}
JNIEXPORT jint JNICALL Java_com_twommod_ModMenu_nAvail(JNIEnv *env, jclass cls, jint id) {
    (void)env; (void)cls;
    if (id == OPT_STACK99) return p_itemcfg != 0;   /* bảng vật phẩm được kiểm khi vào game */
    return id >= 0 && id < OPT_N ? g_ok[id] : 0;
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *r) {
    (void)vm; (void)r;
    install();
    return JNI_VERSION_1_6;
}
