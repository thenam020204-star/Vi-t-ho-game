# Sao lưu môi trường và thư mục làm việc HL5 (05/11/2026)

- `home/hl5work/`: thư mục làm việc chính (`~/hl5work`): APK gốc, .so đã giải, VFS Việt (vi_android), dự án J2ME/ảnh (proj, lgt5, namp), bản dựng (build_apk, test*_build), bản gốc so sánh `orig_v02hd`.
- `home/hl5work_*/`: thư mục làm việc của từng công nhân (harness, ảnh, log). Symlink trỏ về `/root/hl5work/...`.
- `opt/asdk/`: Android build-tools (zipalign, apksigner, aapt) đã cài ở `/opt/asdk`.

Khôi phục trên container mới:
```
cp -a backup/home/hl5work* ~/
sudo cp -a backup/opt/asdk /opt/
```
Sau đó các lệnh trong hl5_android/PLAN.md (make_apk.sh, harness) chạy như cũ với WORK=~/hl5work.
