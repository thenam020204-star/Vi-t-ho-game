/* Offsets/RVA cho libil2cpp.so cua Magic Survival 0.9941 (Unity 2022.3.62f3, arm64).
 * Nguon: docs/mod_analysis.md (T12/T13), work/il2/out/dump.cs (Il2CppDumper 6.7.46), disasm bang tools/il2dis.py.
 * Build ID libil2cpp.so goc: 53be159e4d707729239f52a7e504849a4989752a (sha256 61546e52...e9b326).
 * Moi RVA la dia chi ao trong libil2cpp.so (first PT_LOAD vaddr = 0) => dia chi that = base + RVA, base = dlpi_addr.
 */
#ifndef MSMOD_OFFSETS_H
#define MSMOD_OFFSETS_H

/* ---- Ham (RVA) --------------------------------------------------------------------------------------------
 * Chu ky IL2CPP: tham so cuoi la `const MethodInfo*` (luon NULL khi ta tu goi; ham khong generic khong dung).
 * Float di thanh ghi s0.., int di w1.. (AAPCS64) nen prototype C duoi day khop ABI.
 */
/* void C_Battle_AllyUnit::Unit_Damage(C_Battle_AllyUnit* this, float dmg, float special, int32 dmgType, MethodInfo*)
 * dump.cs: "// RVA: 0x1048858 ... public void Unit_Damage(float EUnitApply_Damage, float EUnitApply_SpecialVar, int EIsDamageType)"
 * 16 byte dau: ea0f1afc e923016d fe6702a9 f85f03a9 (str d10; stp d9,d8; stp x30,x25; stp x24,x23) - khong PC-relative. */
#define RVA_UNIT_DAMAGE        0x1048858u
/* void C_Battle_AllyUnit::Unit_Death(C_Battle_AllyUnit* this, MethodInfo*)  - dump.cs "public void Unit_Death()"
 * 16 byte dau: fe57bea9 f44f01a9 75b30090 54a60090 (stp x30,x21; stp x20,x19; adrp x21; adrp x20)
 * => co 2 lenh adrp: Dobby (InstructionRelocationARM64.cc, inst_is_adrp) dich thanh `Mov Xd, <trang tuyet doi>` nen relocate dung;
 *    khong co nhanh nao trong ham nhay vao 16 byte dau (tools: kiem tra o docs/mod_analysis.md muc T14). */
#define RVA_UNIT_DEATH         0x10449F0u
/* void C_Battle_Manager::Update(C_Battle_Manager* this, MethodInfo*)  - private void Update(); chi co o canh tran (level2).
 * 16 byte dau: e80f1afc fe6f01a9 fa6702a9 f85f03a9 (str d8; stp x30,x27; stp x26,x25; stp x24,x23) - khong PC-relative. */
#define RVA_BATTLE_UPDATE      0x109CBF8u
/* void C_Battle_Manager::Start(C_Battle_Manager* this, MethodInfo*) - dung de xoa con tro V_Ability_Manager cu khi vao tran moi.
 * 16 byte dau: ffc306d1 ef3b116d ed33126d eb2b136d (sub sp; stp d15,d14; stp d13,d12; stp d11,d10) - khong PC-relative. */
#define RVA_BATTLE_START       0x109E444u
/* void V_Ability_Manager::ManaGain(V_Ability_Manager* this, float mana, MethodInfo*)  "EXP" trong game goi la Mana.
 * 16 byte dau: e923bb6d fe0b00f9 f85f02a9 f65703a9 (stp d9,d8; str x30; stp x24,x23; stp x22,x21) - khong PC-relative. */
#define RVA_MANA_GAIN          0x10FC64Cu
/* float UnityEngine.Time::get_timeScale(MethodInfo*) - chi GOI (khong hook). Static icall wrapper cua IL2CPP. */
#define RVA_GET_TIMESCALE      0x219AB34u

/* ---- Truong static ---------------------------------------------------------------------------------------
 * Cach game doc static: x0 = *(slot) (Il2CppClass*), roi `ldr x8,[x0,#0xb8]` (Il2CppClass::static_fields) -> + offset truong.
 * `slot` = dia chi bien global `<Class>_TypeInfo` trong .data/.bss (RVA ben duoi, lay tu script.json ScriptMetadata).
 * LUU Y: truoc khi game chay lan dau mot ham dung lop do, slot con chua ma hoa metadata-usage (gia tri nho < 2^32,
 * vd 0x200047ED), CHUA phai con tro. Chi coi la con tro Il2CppClass* khi gia tri >= 2^32 va can 8.
 * static_fields cung kiem tuong tu (co the NULL neu lop chua init).
 */
#define IL2CPP_CLASS_STATIC_FIELDS 0xB8u    /* Il2CppClass::static_fields (game dung `ldr x8,[x0,#0xb8]`) */

/* C_Battle_AllyUnit_TypeInfo: slot RVA 0x259EE58 (script.json "C_Battle_AllyUnit_TypeInfo"; ManaGain 0x10FC66C nap `ldr x22,[x22,#0x740]` -> 0x259ee58) */
#define SLOT_ALLY_UNIT         0x259EE58u
#define ALLY_UNIT_IS_LIVE      0x10u   /* public static bool Unit_IsLive;            dump.cs // 0x10 */
#define ALLY_APPLY_LEVEL_MAX   0x18u   /* public static int ApplyLevelMax;           // 0x18 */
#define ALLY_APPLY_LEVEL_ADDMAX 0x1Cu  /* public static int ApplyLevelAddMax;        // 0x1C */
#define ALLY_HP_MAX            0x30u   /* public static float UnitApply_HpMax;       // 0x30 */
#define ALLY_HP                0x38u   /* public static float Unit_Hp;               // 0x38 (Unit_Damage ghi `stp s0,s2,[x8,#0x38]` tai 0x10490B0) */

/* Z_Database_TypeInfo: slot RVA 0x25A4B48 (script.json; Show_CloudResult 0x115BDEC -> 0x25a4b48) */
#define SLOT_Z_DATABASE        0x25A4B48u
#define ZDB_LEVEL              0x9D8u  /* public static int AllyUnitGlobal_Level;       // 0x9D8 (V_Ability_Manager.ManaGain 0x10FC724 ldr w20,[x9,#0x9d8]) */
#define ZDB_LEVEL_ADD          0x9DCu  /* public static int AllyUnitGlobal_LevelAdd;    // 0x9DC */
#define ZDB_MANA_COST          0x9E4u  /* public static float AllyUnitGlobal_ManaCost;  // 0x9E4 (EXP can cho cap ke) */
#define ZDB_MANA_STOCK         0x9E8u  /* public static float AllyUnitGlobal_ManaStock; // 0x9E8 (EXP hien co) */

/* Byte goc 16 dau moi ham hook (little-endian word) de kiem truoc khi hook (khong khop => khong hook, ghi log). */
#endif
