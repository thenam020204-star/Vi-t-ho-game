# Prompt khởi đầu (dán vào phiên mới)

```
Tiếp tục dự án HL5 Android (Heroes Lore 5: Việt hoá, logic HD, cash shop miễn phí, menu cảm ứng mới T17, UI combat T14) trong repo thenam020204-star/vi-t-ho-game, nhánh ccr-706054f3-ih6lfy, thư mục hl5_android.

0. BẮT BUỘC TRƯỚC TIÊN: làm việc trên nhánh ccr-706054f3-ih6lfy, KHÔNG phải main (main và các nhánh khác là bản cũ ~02/11, kế hoạch cũ T15->T16->T14->T7->T11 — KHÔNG làm theo). Chạy: git fetch origin ccr-706054f3-ih6lfy && git checkout -B ccr-706054f3-ih6lfy origin/ccr-706054f3-ih6lfy. Kiểm: grep -c 'VIỆC CÒN LẠI' hl5_android/PLAN.md phải >= 1; nếu = 0 là sai nhánh -> dừng và báo tôi. KHÔNG chạy setup_env.sh cũ; môi trường lấy từ backup/ (bước 1).
1. Khôi phục môi trường (container mới):
   cp -a backup/home/hl5work* ~/ && cp -a backup/opt/asdk /opt/
   (chi tiết: backup/README.md; gốc so sánh harness = ~/hl5work/orig_v02hd, chép lại vào ~/hl5work/build_apk sau mỗi make_apk.sh)
2. Đọc hl5_android/PLAN.md: các mục Môi trường, Quyết định thiết kế, Quy tắc thay công nhân, và mục "VIỆC CÒN LẠI" (nguồn sự thật). Đọc báo cáo liên quan khi giao việc: BAO-CAO-T14.md, BAO-CAO-L1.md, BAO-CAO-L2.md, BAO-CAO-T16.md, BAO-CAO-RE.md.
3. Làm theo skill opus-plan-sonnet-build: Opus làm quản đốc (lập kế hoạch, giao việc, nghiệm thu, commit/push, dựng APK), công nhân là Sonnet (model "sonnet"), được chạy song song.
   Luật công nhân: dừng ở ~300k token (khi lượt tuần thấp: ~200k) và ghi "CÒN LẠI" vào báo cáo; không bao giờ vượt 400k; KHÔNG resume công nhân đã dừng — commit WIP, ghi trạng thái, giao công nhân MỚI. Công nhân: file chung chỉ thêm/sửa dòng của mình, không inject_menu --update-repo, không make_apk.sh, không sửa PLAN.md/BAO-CAO-RE.md, không commit, không ghi đè shots/T17c*/T16; mỗi công nhân một WORK riêng (~/hl5work_<tên>, symlink như ~/hl5work_t14).
4. Chế độ J2ME/RE nghiêm ngặt: mã máy/bytecode thật > decompiler; không đoán; giữ hành vi gốc (chạm mới = cùng trạng thái như phím gốc); không cấp phát trong paint; kiểm 3 cỡ 400x240, 600x270, 1200x540 (new/orig/off) + hồi quy trước khi nghiệm thu.
5. Dựng APK thử: quy trình ghép vtable (xoá khối "# >>> T17c menu-vtable" trong patch/so_patches.tsv -> WORK=~/hl5work ./make_apk.sh VER -> apply_so.py .so gốc -> pre.so -> inject_menu.py pre.so ~/hl5work/build_apk/menu --update-repo -> dựng lại, .so phải trùng byte) rồi hậu kỳ: patch/mk_vi_images.py -> tools/swap_bgm.py IN.apk env/inputs/bgm OUT.apk --replace OVL -> zipalign -p 4 -> apksigner (sign/README.md) -> verify; chạy harness tới cảnh chơi; trả build_apk về orig_v02hd.
6. Commit: kết thúc message bằng các dòng attribution của phiên; không ghi tên model; không tạo PR khi chưa được yêu cầu; push: git push -u origin ccr-706054f3-ih6lfy (thử lại khi lỗi mạng).
7. Bắt đầu: đọc dòng Nhật ký cuối PLAN.md (T14c dừng giữa chừng). Việc đầu tiên = T14c: HUD combat gây lag ở 1/1 + nút nhoè (WIP đã commit trong hud.c/gen_hud_art.py; dựng, đo perf_hud.py trước/sau, kiểm check_t14/dpad/swallow/leak, xem ảnh shots/T14/net_*), rồi đóng v0.3-test6. Sau đó hỏi tôi phản hồi chơi thử rồi theo thứ tự mục VIỆC CÒN LẠI.
```
