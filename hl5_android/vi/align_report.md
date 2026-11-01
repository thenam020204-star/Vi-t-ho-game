# Báo cáo ghép chuỗi Android <-> LGT (T2)

Sinh bởi `align_android.py`. Ngưỡng: NEW nếu điểm < 0.55 hoặc không có câu Hàn tương ứng; "điểm cao" >= 0.70.

## Tổng kết

| nhóm | tổng câu | ghép được (K#####) | NEW | trong đó điểm cao |
|---|---:|---:|---:|---:|
| scn | 7163 | 6848 | 315 | 6819 |
| dat | 7135 | 6915 | 220 | 6817 |
| tất cả | 14298 | 13763 | 535 | 13636 |

## scn cùng số câu (ghép theo chỉ số)

- Trong 214 file scn có ở cả hai bản: **189** cùng số câu bảng chuỗi, 25 khác số câu (kể cả file không có câu chữ).
- Số file scn Android có câu chữ = 203; trong đó cùng số câu bảng chuỗi với LGT = **176** file (5851 câu Android).
- Cặp điểm cao (>= 0.70): **5819 / 5851 = 99.5%**; ghép được (>= 0.55): 5839 (99.8%); NEW: 12.
- File cùng số câu nhưng phải dùng DP (ghép theo chỉ số cho điểm thấp): 0 []

## scn khác số câu (DP)

| file | bảng Android | bảng LGT | câu Android có chữ | ghép | NEW |
|---|---:|---:|---:|---:|---:|
| c/map/00000.scn | 19 | 41 | 18 | 18 | 0 |
| c/map/00019.scn | 20 | 7 | 19 | 6 | 13 |
| c/map/00030.scn | 43 | 30 | 43 | 30 | 13 |
| c/map/00068.scn | 111 | 110 | 111 | 110 | 1 |
| c/map/00087.scn | 99 | 96 | 99 | 96 | 3 |
| c/map/00107.scn | 22 | 24 | 22 | 22 | 0 |
| c/map/00113.scn | 29 | 28 | 29 | 24 | 5 |
| c/map/00136.scn | 45 | 34 | 45 | 31 | 14 |
| c/map/00144.scn | 66 | 67 | 66 | 65 | 1 |
| c/map/00150.scn | 13 | 12 | 13 | 12 | 1 |
| c/map/00151.scn | 188 | 185 | 188 | 185 | 3 |
| c/map/00170.scn | 149 | 103 | 148 | 103 | 45 |
| c/map/00174.scn | 218 | 181 | 218 | 181 | 37 |
| c/map/00175.scn | 17 | 1 | 17 | 1 | 16 |
| c/map/00186.scn | 37 | 18 | 36 | 1 | 35 |
| c/map/00198.scn | 52 | 44 | 52 | 44 | 8 |
| c/map/00200.scn | 19 | 16 | 19 | 16 | 3 |
| c/map/00201.scn | 40 | 35 | 40 | 35 | 5 |
| c/map/00202.scn | 23 | 20 | 23 | 20 | 3 |
| c/map/00207.scn | None | None | 16 | 0 | 16 |
| c/map/00208.scn | 1 | 16 | 1 | 1 | 0 |
| c/map/00209.scn | 14 | 1 | 14 | 1 | 13 |
| c/map/00210.scn | None | None | 1 | 0 | 1 |
| c/map/00211.scn | 1 | 14 | 1 | 1 | 0 |
| c/map/00213.scn | 4 | 1 | 4 | 1 | 3 |
| c/map/00214.scn | 17 | 1 | 16 | 1 | 15 |
| c/map/00216.scn | 54 | 4 | 53 | 4 | 49 |

## .dat: kết luận về trường khoá

Không có trường ID duy nhất đầu bản ghi dùng chung cho mọi file (nhiều file bản ghi bắt đầu bằng số loại/kiểu lặp lại hoặc chỉ chứa chuỗi). Khoá thực dụng: **bản ghi LGT và Android giữ nguyên thứ tự tương đối; Android chỉ chèn thêm/bớt bản ghi**. Do đó ghép bản ghi bằng DP tuần tự theo (a) khung xương = bản ghi bỏ các chuỗi (giống nhau từng byte ở phần lớn bản ghi), (b) độ giống chuỗi. Cột `diag` = số bản ghi ghép vẫn cùng chỉ số; `ident` = số cặp khung xương giống hệt; `shifts` = độ lệch chỉ số phổ biến (K - A).

| file Android | file LGT | bản ghi A | bản ghi K | ghép | diag | khung giống hệt | độ lệch phổ biến | câu A | NEW |
|---|---|---:|---:|---:|---:|---:|---|---:|---:|
| c/csv/buildup.dat | c/csv/buildup.dat | 111 | 111 | 111 | 111 | 110 | +0:111 | 111 | 1 |
| c/csv/cash_expert.dat | c/csv/cash_expert.dat | 9 | 9 | 9 | 9 | 7 | +0:9 | 19 | 1 |
| c/csv/cash_network.dat | c/csv/cash_network.dat | 34 | 34 | 34 | 34 | 34 | +0:34 | 68 | 0 |
| vfs:0x831ffd41.dat | c/csv/cash_single.dat | 30 | 29 | 24 | 1 | 0 | +5:17 +1:3 +0:1 +2:1 | 60 | 12 |
| c/csv/cash_single.dat | c/csv/cash_single.dat | 29 | 29 | 29 | 29 | 29 | +0:29 | 58 | 0 |
| vfs:0x2b3c2959.dat | c/csv/cash_single.dat | 30 | 29 | 24 | 1 | 0 | +5:17 +1:3 +0:1 +2:1 | 60 | 12 |
| c/csv/class.dat | c/csv/class.dat | 5 | 5 | 5 | 5 | 5 | +0:5 | 5 | 0 |
| c/csv/common_text.dat | c/csv/common_text.dat | 351 | 333 | 303 | 302 | 303 | +0:302 -1:1 | 346 | 45 |
| c/csv/enemy_0.dat | c/csv/enemy_0.dat | 166 | 166 | 166 | 166 | 0 | +0:166 | 166 | 0 |
| c/csv/enemy_1.dat | c/csv/enemy_1.dat | 166 | 166 | 166 | 166 | 0 | +0:166 | 166 | 0 |
| c/csv/enemy_2.dat | c/csv/enemy_2.dat | 166 | 166 | 166 | 166 | 0 | +0:166 | 166 | 0 |
| c/csv/enemy_expert_0.dat | c/csv/enemy_expert_0.dat | 142 | 142 | 142 | 142 | 0 | +0:142 | 147 | 5 |
| c/csv/enemy_expert_1.dat | c/csv/enemy_expert_1.dat | 142 | 142 | 142 | 142 | 0 | +0:142 | 142 | 0 |
| c/csv/enemy_expert_2.dat | c/csv/enemy_expert_2.dat | 142 | 142 | 142 | 142 | 0 | +0:142 | 142 | 0 |
| c/csv/ingame_text.dat | c/csv/ingame_text.dat | 357 | 355 | 347 | 340 | 347 | +0:340 +1:6 +2:1 | 356 | 10 |
| c/csv/item_00.dat | c/csv/item_00.dat | 86 | 81 | 81 | 81 | 81 | +0:81 | 172 | 10 |
| c/csv/item_01.dat | c/csv/item_01.dat | 86 | 81 | 81 | 81 | 81 | +0:81 | 172 | 10 |
| c/csv/item_02.dat | c/csv/item_02.dat | 86 | 81 | 81 | 81 | 81 | +0:81 | 172 | 10 |
| c/csv/item_03.dat | c/csv/item_03.dat | 86 | 81 | 81 | 81 | 81 | +0:81 | 172 | 10 |
| c/csv/item_04.dat | c/csv/item_04.dat | 1 | 1 | 1 | 1 | 1 | +0:1 | 2 | 0 |
| c/csv/item_05.dat | c/csv/item_05.dat | 90 | 90 | 90 | 90 | 90 | +0:90 | 90 | 0 |
| c/csv/item_06.dat | c/csv/item_06.dat | 93 | 93 | 93 | 93 | 93 | +0:93 | 93 | 0 |
| c/csv/item_07.dat | c/csv/item_07.dat | 81 | 81 | 81 | 81 | 81 | +0:81 | 81 | 0 |
| c/csv/item_08.dat | c/csv/item_08.dat | 81 | 81 | 81 | 81 | 81 | +0:81 | 81 | 0 |
| c/csv/item_09.dat | c/csv/item_09.dat | 81 | 81 | 81 | 81 | 81 | +0:81 | 81 | 0 |
| c/csv/item_10.dat | c/csv/item_10.dat | 18 | 17 | 17 | 17 | 10 | +0:17 | 19 | 1 |
| c/csv/item_11.dat | c/csv/item_11.dat | 16 | 16 | 16 | 16 | 15 | +0:16 | 32 | 1 |
| c/csv/item_12.dat | c/csv/item_12.dat | 53 | 53 | 53 | 53 | 14 | +0:53 | 106 | 0 |
| c/csv/item_13.dat | c/csv/item_13.dat | 86 | 86 | 86 | 86 | 86 | +0:86 | 172 | 0 |
| c/csv/item_14.dat | c/csv/item_14.dat | 58 | 58 | 58 | 58 | 58 | +0:58 | 116 | 0 |
| c/csv/item_15.dat | c/csv/item_15.dat | 116 | 116 | 116 | 116 | 114 | +0:116 | 232 | 0 |
| c/csv/item_16.dat | c/csv/item_16.dat | 95 | 95 | 95 | 95 | 95 | +0:95 | 190 | 0 |
| c/csv/item_17.dat | c/csv/item_17.dat | 98 | 98 | 98 | 98 | 98 | +0:98 | 196 | 0 |
| c/csv/item_18.dat | c/csv/item_18.dat | 49 | 43 | 43 | 43 | 43 | +0:43 | 98 | 12 |
| c/csv/menu_text.dat | c/csv/menu_text.dat | 210 | 205 | 195 | 189 | 195 | +0:189 -1:5 -6:1 | 193 | 8 |
| c/csv/mission_list.dat | c/csv/mission_list.dat | 105 | 105 | 105 | 105 | 100 | +0:105 | 104 | 1 |
| c/csv/mission_text.dat | c/csv/mission_text.dat | 105 | 105 | 104 | 104 | 101 | +0:104 | 105 | 1 |
| c/csv/name.dat | c/csv/name.dat | 63 | 63 | 63 | 63 | 63 | +0:63 | 62 | 0 |
| c/csv/quest_0.dat | c/csv/quest_0.dat | 151 | 152 | 151 | 151 | 103 | +0:151 | 400 | 1 |
| c/csv/quest_1.dat | c/csv/quest_1.dat | 151 | 152 | 151 | 151 | 95 | +0:151 | 400 | 1 |
| c/csv/quest_2.dat | c/csv/quest_2.dat | 151 | 152 | 151 | 151 | 95 | +0:151 | 400 | 1 |
| c/csv/set_option.dat | c/csv/set_option.dat | 14 | 14 | 14 | 14 | 14 | +0:14 | 14 | 0 |
| c/csv/skill_00.dat | c/csv/skill_00.dat | 43 | 43 | 43 | 43 | 37 | +0:43 | 87 | 2 |
| c/csv/skill_01.dat | c/csv/skill_01.dat | 43 | 43 | 43 | 43 | 40 | +0:43 | 87 | 1 |
| c/csv/skill_02.dat | c/csv/skill_02.dat | 43 | 43 | 43 | 43 | 37 | +0:43 | 90 | 4 |
| c/csv/skill_03.dat | c/csv/skill_03.dat | 43 | 43 | 43 | 43 | 41 | +0:43 | 86 | 0 |
| c/csv/skill_05.dat | c/csv/skill_05.dat | 16 | 16 | 16 | 16 | 16 | +0:16 | 32 | 0 |
| c/csv/smith_0.dat | c/csv/smith_0.dat | 96 | 96 | 96 | 96 | 69 | +0:96 | 165 | 27 |
| c/csv/smith_1.dat | c/csv/smith_1.dat | 96 | 96 | 96 | 96 | 79 | +0:96 | 179 | 17 |
| c/csv/smith_2.dat | c/csv/smith_2.dat | 96 | 96 | 96 | 96 | 80 | +0:96 | 178 | 16 |
| c/csv/tips.dat | c/csv/tips.dat | 26 | 26 | 26 | 26 | 26 | +0:26 | 26 | 0 |
| vfs:0x92eafcd8.dat | c/csv2/help_skt.dat | 20 | 21 | 20 | 19 | 20 | +0:19 +1:1 | 39 | 0 |
| vfs:0xc28f807a.dat | c/csv2/help_lgt.dat | 20 | 20 | 20 | 20 | 20 | +0:20 | 39 | 0 |
| vfs:0xe6bbcc45.dat | c/csv2/help_skt.dat | 21 | 21 | 21 | 21 | 21 | +0:21 | 41 | 0 |
| c/csv2/help_ktf.dat | c/csv2/help_ktf.dat | 20 | 20 | 20 | 20 | 20 | +0:20 | 39 | 0 |
| c/csv2/help_lgt.dat | c/csv2/help_lgt.dat | 20 | 20 | 20 | 20 | 20 | +0:20 | 39 | 0 |
| c/csv2/help_skt.dat | c/csv2/help_skt.dat | 21 | 21 | 21 | 21 | 21 | +0:21 | 41 | 0 |

## Tên riêng học được (Hàn -> token Anh), 78 mục

9번가의 성녀=street, 감시자=watcher, 강림자의 교단=order, 강림자의 방주=ark/descendant, 강화=enhance, 결합=fuse/orb, 고급=high/refine, 고위사도=apostles/high/radiance, 공격력=atk, 공화국=republic, 광산 협회=mining, 광산조합=mining, 교리의 관=dogma/hall, 그라함=graham, 기원의 관=hall, 네베드=neved, 네오 그라함 호=graham/neo, 누아다=nuada, 로건=logan, 론베르크=ronberg, 루레인=lurain, 루퍼스=rufus, 마르크=marc, 모이투라=moytura, 반군=tyr, 방어력=def, 방주=ark, 베쓰무라=bethmura, 베인 가=bane/house, 브리안=brian, 비명길=frozen/screaming/sea, 살리실리아=salicilia, 설녀=soulstone/yuki, 성지=sanctuary, 성지전쟁=sanctuary, 센트럴=central, 센트럴 타워=central/tower, 소켓=items, 솔티안=soltian, 수호자=guardian, 슈르츠=schultz, 슈우=schu, 신비조사회=arcane/society, 실버힐=hill/silver, 아미노페나=aminofena, 아일린=eileen, 악몽의 주인=lord/nightmares, 알테나=altena, 알프레드=alfred, 액티브=active/skill, 얼어붙은 바다=frozen/sea, 에블린=evelyn, 엘렌=ellen, 엘바니움=elbanium, 영혼가루=soul, 영혼석=soulstone, 오르비스=archipelago/orbis, 오브=fuse/orb, 웨이드 가=house/wade, 위그드라실=yggdrasil, 이자벨=isabel, 이터니움=eternium, 일반=normal/refine, 제련=refine, 제이크=jake, 카스틀러=castler, 케프네스=kefnes, 크래드=krad, 크리스=chris, 테오=theo/theodore, 테오도어=theodore, 티르=tyr, 티아나=tiana, 판매=items/sell, 패시브=passive/skill, 황혼섬=isle/twilight, 황혼의 다리=bridge/twilight, 흑의병=black/guard

## Phân bố điểm (cặp được ghép)

| khoảng | số cặp |
|---|---:|
| 0.0-0.1 | 0 |
| 0.1-0.2 | 0 |
| 0.2-0.3 | 3 |
| 0.3-0.4 | 1 |
| 0.4-0.5 | 5 |
| 0.5-0.6 | 18 |
| 0.6-0.7 | 112 |
| 0.7-0.8 | 568 |
| 0.8-0.9 | 1551 |
| 0.9-1.0 | 11517 |

## 50 cặp điểm thấp nhất (có câu Hàn đề xuất) để quản đốc soát

| # | file | vị trí | điểm | kết quả | Anh | Hàn |
|---:|---|---|---:|---|---|---|
| 1 | c/map/00215.scn | idx 3 | 0.21 | NEW | What is it this time... | \|(이니셜 L이 새겨진 도구가 놓여 있다. 심하게 손상된 것 같다.){ |
| 2 | c/map/00108.scn | idx 51 | 0.26 | NEW | If more people show up, I might not be able to handle it all alone... | \|(돌아갈 곳이라...){ |
| 3 | c/map/00215.scn | idx 2 | 0.28 | NEW | Commencing elimination protocol. | (아무래도 론베르크의 마법기를 분해 해석한 결과를 설명하는 문서인 듯 하다.){ |
| 4 | c/map/00108.scn | idx 50 | 0.34 | NEW | \|(A place to return to...){ | 물론 네가 어디 정착해서 돌아갈 곳이 있으면 그게 제일 좋겠지만. |
| 5 | c/map/00215.scn | idx 1 | 0.40 | NEW | Intruder detected. | (설계 도면 같은 것이 놓여 있다.){ |
| 6 | c/map/00136.scn | idx 39 | 0.46 | NEW | There are plenty of monsters now, so mercenaries make a living, but wh | 5년전의 성지전쟁은 인류 최악의 실수였어. 이렇게 많은 피해자들을 낳다니.. |
| 7 | c/csv/quest_0.dat | rec 136@10 | 0.50 | NEW | Graham asked for;materials to repair his;power suit. | 여분 |
| 8 | c/csv/quest_1.dat | rec 136@10 | 0.50 | NEW | Graham asked for;materials to repair his;power suit. | 여분 |
| 9 | c/csv/quest_2.dat | rec 136@10 | 0.50 | NEW | Graham asked for;materials to repair his;power suit. | 여분 |
| 10 | c/map/00108.scn | idx 47 | 0.50 | NEW | I do not know what kind of work you are involved in, but... | 그렇군. |
| 11 | c/csv/item_11.dat | rec 12@23 | 0.53 | NEW | Expert Mode Only{;Restores 100 HP 5;times, for 500 HP;total. Cannot be | ^익스퍼트 모드 전용{;100씩 5회에 걸쳐 총 500의 HP를 회복합니다;스토리 모드에서는 사용할 수 없습니다. |
| 12 | c/csv/skill_00.dat | rec 42@54 | 0.55 | NEW | T+rBuff Skill. Cooldown:;}#09s\|. For }#08s\|, Max;HP drops b | 버프 스킬.;재사용대기 }#09초\|.;}#08초\| 동안 HP최대치가;}30%\| 감소하는 대신;공격속도가 증가하고;크리티컬 |
| 13 | c/csv/skill_02.dat | rec 19@55 | 0.55 | K03242 | TDABuff Skill. Maximizes;attack speed of;continuous fire fo | 버프 스킬.;}#08초\|동안;연속 사격의 공격속도를;최대로 증가시킨다. |
| 14 | c/map/00038.scn | idx 64 | 0.56 | K04977 | Failures happen too, locking the item from more refining. A Critical F | 물론 실패해서 더는 제련할 수 없는 경우도 있고. 재수가 없어서 대실패를 하면 장비가 망가질 때도 있지만 |
| 15 | c/map/00060.scn | idx 125 | 0.56 | K06337 | Basically, a ten-year installment plan! Some whine it is just tenant f | 간단히 말해서 10년 할부!랄까. 소작농이랑 뭐가 다르냐는 녀석도 있지만, 어쨌든 버티면 자기 땅이 되는 거잖아? |
| 16 | c/map/00094.scn | idx 22 | 0.56 | K06951 | Unless you keep your gear up to par, you will not survive against the  | 우리 손으로 직접 벼린 무기들이라면, 센트럴의 공장에서 찍어내는 무기들 따위 무섭지 않다고! |
| 17 | c/map/00144.scn | idx 54 | 0.56 | K06716 | Looking at this used to bring me peace of mind... | 정말 기적이라고 밖에 할 수 없는 빛이에요. 네베드들의 기술인 걸까요? |
| 18 | c/map/00174.scn | idx 38 | 0.56 | K09489 | ...If four remain, does that mean their power... is about a quarter of | .. 4명이 남았다면, 그 정도의 힘이.. 완성체의 1/4 수준의 힘이라는 건가요.. |
| 19 | c/map/00136.scn | idx 36 | 0.57 | K06460 | I will edit this a bit, get it properly published, and then place a co | 아직도 가슴에 }노예의 낙인{이 찍힌 채로 살아가는 사람들이 있지.. |
| 20 | c/csv/quest_0.dat | rec 126@13 | 0.58 | K03820 | An ancient relic might;help investigate the;ruins below Hall of;Origin | 여분 |
| 21 | c/csv/quest_1.dat | rec 126@13 | 0.58 | K03820 | An ancient relic might;help investigate the;ruins below Hall of;Origin | 여분 |
| 22 | c/csv/quest_2.dat | rec 126@13 | 0.58 | K03820 | An ancient relic might;help investigate the;ruins below Hall of;Origin | 여분 |
| 23 | c/csv/quest_0.dat | rec 135@15 | 0.58 | K03820 | Destroy the reviving;ancient god vessel in;Sanctuary again. It;will on | 여분 |
| 24 | c/csv/quest_1.dat | rec 135@15 | 0.58 | K03820 | Destroy the reviving;ancient god vessel in;Sanctuary again. It;will on | 여분 |
| 25 | c/csv/quest_2.dat | rec 135@15 | 0.58 | K03820 | Destroy the reviving;ancient god vessel in;Sanctuary again. It;will on | 여분 |
| 26 | c/csv/skill_02.dat | rec 38@55 | 0.59 | K03277 | D\Set off a flashbang.;For }#08s\|, nearby;enemies lose }3 | 제자리에 섬광탄을 터뜨려;}#08초\|동안 인접한 적들의;명중능력을 }30\| 감소시키고;}시야를 }1\|로 감소시킨다. |
| 27 | c/csv/ingame_text.dat | rec 328@0 | 0.60 | K00733 | Failed! | 에서;실패 하였습니다. |
| 28 | c/csv/skill_01.dat | rec 31@122 | 0.60 | K03179 | cost of;all skills by }50%\| for; | 버프 스킬.;}SP #07\| 소모.;재사용대기 }#09초\|.;}#08초\|동안;모든 스킬의 SP소모량을;}50%\| 감소시 |
| 29 | c/map/00136.scn | idx 34 | 0.61 | K06458 | Regardless, for that reason... | 온 섬이 불타는 데도, 웨이드 가에서 이 도서관만큼은 지켜주었어. 감사한 일이지. |
| 30 | c/csv/item_16.dat | rec 0@25 | 0.62 | K02326 | Warrior Skillbook;Req. Level: Lv.1;Attacks enemies up to;3 times in me | 워리어 스킬북;제한레벨 LV1 |
| 31 | c/csv/item_16.dat | rec 1@25 | 0.62 | K02328 | Warrior Skillbook;Req. Level: Lv.4;Attacks enemies up to;3 times in me | 워리어 스킬북;제한레벨 LV4 |
| 32 | c/csv/item_16.dat | rec 2@23 | 0.62 | K02330 | Warrior Skillbook;Req. Level: Lv.22;Attack enemies up to;3 times with  | 워리어 스킬북;제한레벨 LV22 |
| 33 | c/csv/item_16.dat | rec 7@15 | 0.62 | K02339 | Warrior Skillbook;Req. Level: Lv.14;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV14 |
| 34 | c/csv/item_16.dat | rec 8@15 | 0.62 | K02341 | Warrior Skillbook;Req. Level: Lv.26;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV26 |
| 35 | c/csv/item_16.dat | rec 9@15 | 0.62 | K02343 | Warrior Skillbook;Req. Level: Lv.38;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV38 |
| 36 | c/csv/item_16.dat | rec 10@15 | 0.62 | K02345 | Warrior Skillbook;Req. Level: Lv.46;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV46 |
| 37 | c/csv/item_16.dat | rec 11@15 | 0.62 | K02347 | Warrior Skillbook;Req. Level: Lv.58;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV58 |
| 38 | c/csv/item_16.dat | rec 12@15 | 0.62 | K02349 | Warrior Skillbook;Req. Level: Lv.68;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV68 |
| 39 | c/csv/item_16.dat | rec 13@15 | 0.62 | K02351 | Warrior Skillbook;Req. Level: Lv.80;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV80 |
| 40 | c/csv/item_16.dat | rec 18@23 | 0.62 | K02328 | Warrior Skillbook;Req. Level: Lv.4;Slash upward to deal;melee damage.  | 워리어 스킬북;제한레벨 LV4 |
| 41 | c/csv/item_16.dat | rec 19@23 | 0.62 | K02362 | Warrior Skillbook;Req. Level: Lv.16;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV16 |
| 42 | c/csv/item_16.dat | rec 20@23 | 0.62 | K02364 | Warrior Skillbook;Req. Level: Lv.34;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV34 |
| 43 | c/csv/item_16.dat | rec 21@23 | 0.62 | K02366 | Warrior Skillbook;Req. Level: Lv.50;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV50 |
| 44 | c/csv/item_16.dat | rec 22@23 | 0.62 | K02368 | Warrior Skillbook;Req. Level: Lv.64;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV64 |
| 45 | c/csv/item_16.dat | rec 23@23 | 0.62 | K02370 | Warrior Skillbook;Req. Level: Lv.82;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV82 |
| 46 | c/csv/item_16.dat | rec 24@21 | 0.62 | K02372 | Warrior Skillbook;Req. Level: Lv.20;Spin and strike all;nearby enemies | 워리어 스킬북;제한레벨 LV20 |
| 47 | c/csv/item_16.dat | rec 25@21 | 0.62 | K02374 | Warrior Skillbook;Req. Level: Lv.36;Spin and strike all;nearby enemies | 워리어 스킬북;제한레벨 LV36 |
| 48 | c/csv/item_16.dat | rec 26@21 | 0.62 | K02376 | Warrior Skillbook;Req. Level: Lv.52;Spin and strike all;nearby enemies | 워리어 스킬북;제한레벨 LV52 |
| 49 | c/csv/item_16.dat | rec 48@17 | 0.62 | K02420 | Rogue Skill Book Req;Lv1 Strikes up to 4;times with Dagger/1H;Sword in | 로그 스킬북;제한레벨 LV1 |
| 50 | c/csv/item_16.dat | rec 49@17 | 0.62 | K02422 | Rogue Skill Book Req;Lv4 Strikes up to 4;times with Dagger/1H;Sword in | 로그 스킬북;제한레벨 LV4 |

## Thống kê NEW theo file (20 file nhiều nhất)

| file | NEW |
|---|---:|
| c/map/00216.scn | 49 |
| c/csv/common_text.dat | 45 |
| c/map/00170.scn | 45 |
| c/map/00174.scn | 37 |
| c/map/00186.scn | 35 |
| c/csv/smith_0.dat | 27 |
| c/csv/smith_1.dat | 17 |
| c/csv/smith_2.dat | 16 |
| c/map/00175.scn | 16 |
| c/map/00207.scn | 16 |
| c/map/00214.scn | 15 |
| c/map/00136.scn | 14 |
| c/map/00019.scn | 13 |
| c/map/00030.scn | 13 |
| c/map/00209.scn | 13 |
| vfs:0x831ffd41.dat | 12 |
| vfs:0x2b3c2959.dat | 12 |
| c/csv/item_18.dat | 12 |
| c/csv/ingame_text.dat | 10 |
| c/csv/item_00.dat | 10 |

## Ghi chú kỹ thuật

- `tools/scn.py:parse` chọn `hits[-1]` nên sai ở các file có nhiều điểm khớp EOF (vd Android c/map/00154.scn, và vài file LGT như 00026/00034 trả về 0 chuỗi). `extract_android.parse_scn` ưu tiên điểm bắt đầu s=15 (header chuẩn mọi file) rồi mới chọn hit nhiều chuỗi nhất; T4 phải dùng `extract_android.parse_scn/build_scn`, không dùng `scn.build`.
- 2 câu Android có chữ `\u0000` dính cuối (c/map/00041.scn idx 54, c/map/00042.scn idx 24): lỗi của bản Anh hoá, bỏ khi chấm điểm; T4 nên bỏ đoạn này khi ghi.
- Dấu nháy sở hữu trong .dat tiếng Anh ("Marc's") là chữ thường, nhưng `'` là mã điều khiển của engine; chấm điểm bỏ qua dấu nháy giữa 2 chữ cái. Cần T4/T3 kiểm tra cách engine vẽ.
- Ví dụ nhiễu còn sót trong android_catalog (chuỗi .dat 2 ký tự trùng bố cục số): `HX` (enemy_expert_*), `oAl`: ghi nhận, sẽ ra NEW và khi dịch có thể bỏ qua.
- Câu NEW của .dat thuộc bản ghi mới của Android (item_00..03/18 thêm 5-6 bản ghi, common_text +18, v.v.), của file chỉ Android, hoặc bản ghi LGT có chữ giữ chỗ `여분`.

NEW chia theo lý do: {'bản ghi LGT không có chuỗi Hàn': 59, 'chuỗi thừa trong bản ghi': 15, 'bản ghi mới (không có bản ghi LGT tương ứng)': 141, 'điểm thấp': 9, 'K không có chữ Hàn ở chỉ số này': 6, 'không ghép được': 285, 'nghi lệch -1': 2, 'nghi lệch +1': 1, 'LGT không có file này': 17}
