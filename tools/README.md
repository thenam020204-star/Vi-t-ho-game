# tools/ - lenh tai lap

Tat ca script Python chay bang `python3 -I`. `work/` va `out/` khong commit.

```
bash tools/setup_tools.sh          # pip (UnityPy fonttools freetype-py capstone pillow) + dotnet 8 runtime,
                                   # Il2CppDumper v6.7.46 (net6, chay tren dotnet 8 nho DOTNET_ROLL_FORWARD=Major),
                                   # Android build-tools r35 (zipalign/apksigner/aapt2), APKEditor 1.4.9, apktool 2.12.0
source tools/env.sh                # PATH + DOTNET_ROOT
python3 -I tools/unpack.py         # work/game.apks, work/base.apk, work/split_config.arm64_v8a.apk, work/ex/assets/
```

`unpack.py` noi z01..z06 + zip (ten that sau dau `-` cua file upload), bo marker 504b0708, doc local header,
inflate deflate-raw, kiem tra CRC 63ccbe18 + kich thuoc 170785160. Khong dung `zip -s 0`.

Kiem tra cong cu:
```
zipalign 2>&1 | head -2; apksigner --help | head -2; aapt2 version
dotnet work/tools/il2cppdumper/Il2CppDumper.dll          # in usage
java -jar work/tools/APKEditor-1.4.9.jar -h; java -jar work/tools/apktool_2.12.0.jar -version
```
Luu y: GitHub API (gh api / releases/latest) bi chan; chi tai duoc `releases/download/<tag>/<ten>` truc tiep.

## Build patch / APK (T8-T10)
```
python3 -I tools/build_font_f18px.py --px 75 --variants B          # work/font_px/JejuHallasan-f18B.ttf (D12)
python3 -I tools/text_inject.py                                    # work/build/text/eng_Dictionary_*.txt tu text/vi/vi.tsv
bash tools/build_apk.sh f18  work/font_px/JejuHallasan-f18B.ttf    # -> out/MagicSurvival_VI_f18.apks + _f18_merged.apk
bash tools/build_apk.sh jeju work/font/JejuHallasan-VI.ttf         # -> out/MagicSurvival_VI_jeju.apks + _jeju_merged.apk
python3 -I tools/bundle_diff.py work/ex/assets/bin/Data/data.unity3d <data.unity3d moi>   # dung 1 Font + 6 TextAsset + 18 UI.Text
python3 -I tools/apk_diff.py work/base.apk <base.apk moi>                                  # chi data.unity3d + chu ky
```
`build_apk.sh` goi `patch_bundle.py` (UnityPy, packer original, text/vi/scene.tsv cho UI.Text), thay entry trong base.apk (giu stored/deflate, thu tu),
`zipalign -f -p 4`, `apksigner` v2+v3 (khong v1, `--min-sdk-version 24`), ky lai split, dong `.apks` (stored), APKEditor `m` -> APK gop -> zipalign + ky.
Khoa ky THU NGHIEM (khong commit): `work/keys/ms-vi.jks`, alias `msvi`, mat khau store/key `msviviet`; tu tao neu chua co. Doi khoa so voi ban goc => phai go ban goc truoc khi cai.
Kiem tra: `apksigner verify --verbose --min-sdk-version 24 --print-certs X.apk`, `zipalign -c -p 4 X.apk`, `aapt2 dump badging X.apk`
(split khong co minSdk nen can `--min-sdk-version 24`, neu khong apksigner doi v1). `zipalign` in "header mismatch" khi xu ly APK do APKEditor tao: vo hai.

## Ban mod (T15)
```
python3 -I tools/patch_so.py && python3 -I tools/patch_so.py --check     # work/mod/libil2cpp.so
bash tools/build_mod.sh                                                  # work/mod/libmsmod.so, classes_mod.dex
java -jar work/tools/apktool_2.12.0.jar d -r -f work/base.apk -o ap      # sua smali_classes2/.../UnityPlayerActivity.smali: truoc return-void cua onCreate chen
                                                                         #   invoke-static {p0}, Lcom/msmod/Mod;->onCreate(Landroid/app/Activity;)V ; apktool b ; lay classes2.dex
source tools/env.sh; python3 -I tools/build_apk_mod.py --dex2 <classes2.dex>   # -> out/MagicSurvival_VI_f18_mod.apks + _mod_merged.apk
```
