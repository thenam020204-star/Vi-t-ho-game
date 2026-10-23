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
