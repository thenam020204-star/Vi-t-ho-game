/* libmsmod.so - mod noi bo Magic Survival (arm64): bat tu + len cap ngay trong tran.
 * Hook bang Dobby (inline) vao libil2cpp.so. Khong cap phat / khoa trong hook moi frame: chi bien atomic.
 * Java (com.msmod.Mod) goi: setGodMode(boolean), requestLevelUp(int), isInBattle(), + getPendingLevelUps(), isReady().
 */
#include <android/log.h>
#include <dobby.h>
#include <jni.h>
#include <link.h>
#include <pthread.h>
#include <stdatomic.h>
#include <stdbool.h>
#include <stdint.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

#include "offsets.h"

#define TAG "msmod"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define EXPORT __attribute__((visibility("default")))

/* ---- trang thai chia se (UI thread <-> luong Unity) ------------------------------------------------------ */
static atomic_int g_god;                 /* 1 = bat tu */
static atomic_int g_pending;             /* so cap con phai len */
static atomic_llong g_last_update_ns;    /* thoi diem Update gan nhat (CLOCK_MONOTONIC) */
static atomic_int g_live;                /* Unit_IsLive thay o Update gan nhat */
static atomic_int g_ready;               /* 1 = da hook xong */
static _Atomic(void *) g_mana_this;      /* V_Ability_Manager* (lay tu hook ManaGain) */
static uintptr_t g_base;                 /* base libil2cpp.so */

/* ---- ban goc (trampoline cua Dobby) ----------------------------------------------------------------------- */
static void (*orig_Unit_Damage)(void *, float, float, int32_t, const void *);
static void (*orig_Unit_Death)(void *, const void *);
static void (*orig_Update)(void *, const void *);
static void (*orig_Start)(void *, const void *);
static void (*orig_ManaGain)(void *, float, const void *);
static float (*fn_get_timeScale)(const void *);

/* log mot lan cho moi diem (chan doan T17): tra true o lan dau */
static bool once(atomic_int *f) { return atomic_exchange_explicit(f, 1, memory_order_relaxed) == 0; }
static atomic_int o_dmg, o_death, o_start, o_mana, o_upd, o_live;

static inline long long now_ns(void) {
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return (long long)ts.tv_sec * 1000000000LL + ts.tv_nsec;
}

/* Il2CppClass* / static_fields chi hop le khi >= 2^32 (truoc do slot chua ma hoa metadata-usage), can 8. */
static inline bool ptr_ok(uintptr_t p) { return p >= 0x100000000ULL && (p & 7) == 0; }

static uint8_t *statics_of(uint32_t slot_rva) {
    uintptr_t klass = *(volatile uintptr_t *)(g_base + slot_rva);
    if (!ptr_ok(klass)) return NULL;
    uintptr_t st = *(volatile uintptr_t *)(klass + IL2CPP_CLASS_STATIC_FIELDS);
    if (!ptr_ok(st)) return NULL;
    return (uint8_t *)st;
}

/* ---- hook ------------------------------------------------------------------------------------------------- */
static void hook_Unit_Damage(void *self, float dmg, float special, int32_t type, const void *mi) {
    if (once(&o_dmg)) LOGI("hook_Unit_Damage lan dau (god=%d)", atomic_load(&g_god));
    if (atomic_load_explicit(&g_god, memory_order_relaxed)) return;
    orig_Unit_Damage(self, dmg, special, type, mi);
}

static void hook_Unit_Death(void *self, const void *mi) {
    if (once(&o_death)) LOGI("hook_Unit_Death lan dau (god=%d)", atomic_load(&g_god));
    if (atomic_load_explicit(&g_god, memory_order_relaxed)) return;
    orig_Unit_Death(self, mi);
}

static void hook_Start(void *self, const void *mi) {
    /* Vao tran moi: V_Ability_Manager cu da bi huy => bo con tro cu, bo yeu cau len cap con lai. */
    if (once(&o_start)) LOGI("hook C_Battle_Manager.Start lan dau");
    atomic_store(&g_mana_this, NULL);
    atomic_store(&g_pending, 0);
    orig_Start(self, mi);
}

static void hook_ManaGain(void *self, float mana, const void *mi) {
    if (once(&o_mana)) LOGI("hook ManaGain lan dau");
    atomic_store_explicit(&g_mana_this, self, memory_order_relaxed);
    orig_ManaGain(self, mana, mi);
}

static void hook_Update(void *self, const void *mi) {
    if (once(&o_upd)) LOGI("hook C_Battle_Manager.Update lan dau (tran dau bat dau)");
    orig_Update(self, mi);
    atomic_store_explicit(&g_last_update_ns, now_ns(), memory_order_relaxed);

    uint8_t *ally = statics_of(SLOT_ALLY_UNIT);
    if (!ally) { atomic_store_explicit(&g_live, 0, memory_order_relaxed); return; }
    int live = ally[ALLY_UNIT_IS_LIVE] != 0;
    atomic_store_explicit(&g_live, live, memory_order_relaxed);
    if (live && once(&o_live)) LOGI("Unit_IsLive = 1 lan dau");
    if (!live) return;

    /* Bat tu: giu HP day moi frame (phong ky nang tu tru mau khong qua Unit_Damage). */
    if (atomic_load_explicit(&g_god, memory_order_relaxed)) {
        float mx = *(volatile float *)(ally + ALLY_HP_MAX);
        volatile float *hp = (volatile float *)(ally + ALLY_HP);
        if (mx > 0.0f && *hp < mx) *hp = mx;
    }

    /* Len cap (T19): ManaGain KHONG ghi Level/LevelAdd (chi doc) - Level tang sau khi nguoi choi chon ky nang.
     * Coi 1 lan goi la "da len cap" khi ManaCost doi, hoac ManaStock bi tru (< stock+need), hoac timeScale<=0.01 (bang da mo).
     * Thanh cong -> giam pending ngay, roi cho timeScale>0.01 tro lai + ~10 frame truoc khi goi tiep. */
    static int wait_resume, settle, fails, blocked;
    int pend = atomic_load_explicit(&g_pending, memory_order_relaxed);
    if (pend <= 0) { wait_resume = 0; settle = 0; fails = 0; blocked = 0; return; }
    void *mgr = atomic_load_explicit(&g_mana_this, memory_order_relaxed);
    if (!mgr) return;                          /* chua nhat vien EXP nao => chua co this; giu yeu cau */
    if (fn_get_timeScale(NULL) <= 0.01f) return; /* bang chon ky nang / tam dung dang mo */
    if (wait_resume) {                         /* bang vua dong: cho game xu ly xong lua chon */
        if (++settle < 10) return;
        wait_resume = 0;
    }
    uint8_t *zdb = statics_of(SLOT_Z_DATABASE);
    if (!zdb) return;

    int lv = *(volatile int *)(zdb + ZDB_LEVEL);
    int la = *(volatile int *)(zdb + ZDB_LEVEL_ADD);
    int lmax = *(volatile int *)(ally + ALLY_APPLY_LEVEL_MAX);
    int lamax = *(volatile int *)(ally + ALLY_APPLY_LEVEL_ADDMAX);
    if (!(lv < lmax || la < lamax)) { atomic_store(&g_pending, 0); return; } /* da dat cap toi da */

    float cost = *(volatile float *)(zdb + ZDB_MANA_COST);
    float stock = *(volatile float *)(zdb + ZDB_MANA_STOCK);
    if (stock >= cost) {                       /* game dang tu xu ly EXP tran (vd bang artifact mo): khong chen vao */
        if (++blocked >= 600) { blocked = 0; atomic_store(&g_pending, 0); }
        return;
    }
    blocked = 0;
    float need = cost - stock;
    orig_ManaGain(mgr, need + 0.01f, NULL);

    float cost2 = *(volatile float *)(zdb + ZDB_MANA_COST);
    float stock2 = *(volatile float *)(zdb + ZDB_MANA_STOCK);
    int lv2 = *(volatile int *)(zdb + ZDB_LEVEL), la2 = *(volatile int *)(zdb + ZDB_LEVEL_ADD);
    bool ok = (cost2 != cost) || (stock2 < stock + need - 0.005f) || (fn_get_timeScale(NULL) <= 0.01f) || (lv2 + la2 > lv + la);
    LOGI("levelup: ok=%d cost %.1f->%.1f stock %.1f->%.1f lv %d+%d->%d+%d pend=%d", ok, cost, cost2, stock, stock2, lv, la, lv2, la2, pend);
    if (ok) {
        fails = 0; settle = 0; wait_resume = 1;
        atomic_fetch_sub(&g_pending, 1);
    } else if (++fails >= 30) {
        fails = 0;
        atomic_store(&g_pending, 0);
    }
}

/* ---- cai hook ---------------------------------------------------------------------------------------------- */
static int find_il2cpp_cb(struct dl_phdr_info *info, size_t sz, void *data) {
    (void)sz;
    const char *n = info->dlpi_name;
    size_t l = n ? strlen(n) : 0;
    static const char suf[] = "/libil2cpp.so";
    if (l >= sizeof(suf) - 1 && strcmp(n + l - (sizeof(suf) - 1), suf) == 0) {
        *(uintptr_t *)data = (uintptr_t)info->dlpi_addr;
        return 1;
    }
    return 0;
}

static bool check_prologue(uint32_t rva, const uint32_t want[4], const char *name) {
    const uint32_t *p = (const uint32_t *)(g_base + rva);
    for (int i = 0; i < 4; i++) {
        if (p[i] != want[i]) {
            LOGE("%s: byte dau khong khop (word %d: %08x != %08x) - khong hook", name, i, p[i], want[i]);
            return false;
        }
    }
    return true;
}

static bool hook_one(uint32_t rva, const uint32_t want[4], void *repl, void **orig, const char *name) {
    const uint32_t *pp = (const uint32_t *)(g_base + rva);
    LOGI("hook %s rva=0x%x addr=%p bytes=%08x %08x %08x %08x", name, rva, (const void *)pp, pp[0], pp[1], pp[2], pp[3]);
    if (!check_prologue(rva, want, name)) return false;
    int r = DobbyHook((void *)(g_base + rva), repl, orig);
    if (r != 0) { LOGE("DobbyHook %s FAIL (%d)", name, r); return false; }
    LOGI("hooked OK %s @ %p (orig tramp=%p)", name, (void *)(g_base + rva), *orig);
    return true;
}

static void *installer(void *arg) {
    (void)arg;
    uintptr_t base = 0;
    for (int i = 0; i < 6000 && !base; i++) { /* ~120 s, 20 ms/lan */
        dl_iterate_phdr(find_il2cpp_cb, &base);
        if (!base) usleep(20000);
    }
    LOGI("installer: tim libil2cpp.so %s", base ? "thay" : "KHONG thay");
    if (!base) { LOGE("khong thay libil2cpp.so"); return NULL; }
    g_base = base;
    LOGI("libil2cpp.so base=%p", (void *)base);

    /* 16 byte dau (4 word, little-endian) - xem offsets.h */
    static const uint32_t w_damage[4] = {0xfc1a0fea, 0x6d0123e9, 0xa90267fe, 0xa9035ff8};
    static const uint32_t w_death[4]  = {0xa9be57fe, 0xa9014ff4, 0x9000b375, 0x9000a654};
    static const uint32_t w_update[4] = {0xfc1a0fe8, 0xa9016ffe, 0xa90267fa, 0xa9035ff8};
    static const uint32_t w_start[4]  = {0xd106c3ff, 0x6d113bef, 0x6d1233ed, 0x6d132beb};
    static const uint32_t w_mana[4]   = {0x6dbb23e9, 0xf9000bfe, 0xa9025ff8, 0xa90357f6};

    fn_get_timeScale = (float (*)(const void *))(g_base + RVA_GET_TIMESCALE);
    bool ok = true;
    ok &= hook_one(RVA_UNIT_DAMAGE, w_damage, (void *)hook_Unit_Damage, (void **)&orig_Unit_Damage, "Unit_Damage");
    ok &= hook_one(RVA_UNIT_DEATH, w_death, (void *)hook_Unit_Death, (void **)&orig_Unit_Death, "Unit_Death");
    ok &= hook_one(RVA_MANA_GAIN, w_mana, (void *)hook_ManaGain, (void **)&orig_ManaGain, "ManaGain");
    ok &= hook_one(RVA_BATTLE_START, w_start, (void *)hook_Start, (void **)&orig_Start, "C_Battle_Manager.Start");
    ok &= hook_one(RVA_BATTLE_UPDATE, w_update, (void *)hook_Update, (void **)&orig_Update, "C_Battle_Manager.Update");
    atomic_store(&g_ready, ok ? 1 : 0);
    LOGI("hook xong: %s", ok ? "OK" : "CO LOI");
    return NULL;
}

__attribute__((constructor)) static void msmod_init(void) {
    pthread_t t;
    LOGI("constructor: libmsmod.so nap xong, pid=%d", (int)getpid());
    if (pthread_create(&t, NULL, installer, NULL) == 0) pthread_detach(t);
}

/* ---- JNI: lop com.msmod.Mod ------------------------------------------------------------------------------- */
EXPORT JNIEXPORT void JNICALL Java_com_msmod_Mod_setGodMode(JNIEnv *env, jclass cls, jboolean on) {
    (void)env; (void)cls;
    atomic_store(&g_god, on ? 1 : 0);
}

EXPORT JNIEXPORT void JNICALL Java_com_msmod_Mod_requestLevelUp(JNIEnv *env, jclass cls, jint n) {
    (void)env; (void)cls;
    if (n <= 0) return;
    if (n > 99) n = 99;
    int cur = atomic_load(&g_pending);
    int nv = cur + n;
    if (nv > 99) nv = 99;
    atomic_store(&g_pending, nv);
}

EXPORT JNIEXPORT jboolean JNICALL Java_com_msmod_Mod_isInBattle(JNIEnv *env, jclass cls) {
    (void)env; (void)cls;
    if (!atomic_load(&g_ready) || !atomic_load(&g_live)) return JNI_FALSE;
    long long last = atomic_load(&g_last_update_ns);
    return (last != 0 && now_ns() - last < 1000000000LL) ? JNI_TRUE : JNI_FALSE;
}

EXPORT JNIEXPORT jint JNICALL Java_com_msmod_Mod_getPendingLevelUps(JNIEnv *env, jclass cls) {
    (void)env; (void)cls;
    return atomic_load(&g_pending);
}

EXPORT JNIEXPORT jboolean JNICALL Java_com_msmod_Mod_isReady(JNIEnv *env, jclass cls) {
    (void)env; (void)cls;
    return atomic_load(&g_ready) ? JNI_TRUE : JNI_FALSE;
}
