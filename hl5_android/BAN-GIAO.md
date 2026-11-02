# BÀN GIAO — Việt hoá + HD Heroes Lore 5 (Android)

File này đủ để một phiên mới (người hoặc Claude) làm tiếp mà không cần lịch sử chat.
Nguồn sự thật chi tiết: `PLAN.md` (checklist, quyết định thiết kế, Nhật ký, Vướng mắc) và `BAO-CAO-RE.md` (phân tích mã máy).

## 0. Cập nhật mới nhất
- **v0.2-hd** (`dist/HL5-VI-v0.2-hd.apk`, diag `dist/diag/HL5-diag-v0.2-hd.apk`): người dùng xác nhận **chạy ổn trên máy**. Có: chọn "Phân giải" 1/1–1/4 trong Cài đặt (mặc định 1/2, file `hd_scale.txt`), chữ 18 px khi khung cao ≥ 480, giới hạn texture 4096.
- Thứ tự còn lại: **T15** (phóng to tab/menu trong game; T15a thiết kế có thể đang dở → xem BAO-CAO §12 nếu có) → **T16** (khung hội thoại mới, mockup `ui_new/dialog/mockup_dialog_hd.jpg`) → T14a/T14b (nút cảm ứng mới + vẽ DASH) → T7 (menu offline, cash shop miễn phí, rút gọn nhãn menu chính bị tràn ở 18 px) → T11.
- Tồn đọng đã biết: nhãn menu chính tràn ô ở 18 px; mô tả lớp nhân vật tràn khung (lỗi sẵn có); ÷2 tốn CPU ~2.7× v0.1.1.

## 1. Trạng thái (bàn giao lần 1)
- **Bản ổn định:** `dist/HL5-VI-v0.1.1.apk` (sha256 `119e0f2d7bc44ebf857c175fe5fe343f4039b5570bffc23590eee8bf20b84261`), **người dùng đã chạy tốt trên máy thật** (arm64, Android 10+).
  Có: chữ Việt (14282/14284 chuỗi), offline khởi động, logic = màn ÷ k tự động (k = floor(cao/240)), NEAREST, viewport số nguyên, chạm đúng.
- Bản chẩn đoán đi kèm: `dist/diag/HL5-diag-v0.1.1.apk` (ghi log vào Downloads; xem `PHAT-HANH-diag.md`).
- **Đang làm:** T9 (guest ở khung lớn 1200x540…) — có thể chưa xong khi bàn giao; xem Nhật ký cuối `PLAN.md`.
- **Thứ tự còn lại (người dùng chốt):** T9 → T10 (÷k trong Cài đặt, mặc định ÷2, chữ 18 px) → T14a (kiểm kê UI cảm ứng) → T14b (thay UI bằng nút mới + **vẽ thêm nút DASH**) → T7 (menu offline, cash shop miễn phí, hồi sinh — đã phân tích, có địa chỉ trong Nhật ký T7) → T11 (vẽ lại ảnh chữ Anh).

## 2. UI mới (T14)
- Ảnh người dùng gửi: `ui_new/01..07*.png`; ảnh tham chiếu trong game v0.1.1: `ui_new/ref_ingame_v011.jpg`.
- Ánh xạ đã chốt: 01 emblem = tấn công (thường), 02 emblem sáng = tấn công (nhấn), 03 khung rỗng = nền nút kỹ năng, 04 ‖ = tạm dừng/menu, 05 khay = lưu game, 06 "Cửa hàng" = cash shop, 07 hai mũi tên = đổi vũ khí/bộ kỹ năng.
- Trên màn hiện có: chữ "bản đồ / cửa hàng / lưu" ở trên (thay bằng nút 05/06, "bản đồ" dùng khung 03 + biểu tượng tự vẽ), cột kỹ năng phải, nút DASH (chữ, **không có ảnh mới → tự vẽ cùng phong cách viền cam/đen**), nút tấn công tròn cam, nút đổi `#`, ô vật phẩm.
- Ở HD nên vẽ nút theo độ phân giải thật (T14a xác định ai vẽ: guest `touch.mgr` hay bridge).

## 3. Dựng lại môi trường (1 lệnh, ~40 giây, KHÔNG cần upload lại)
**Bước đầu tiên của phiên mới:**
```
hl5_android/env/setup_env.sh --build      # WORK mặc định ~/hl5work (đặt WORK=... để đổi)
```
Script tự: kiểm sha256 đầu vào (`env/SHA256SUMS`), cài gói Python thiếu, cài/giải nén Android build-tools 34 từ `env/sdk/` vào `/opt/asdk/android-14`, chép jar smali vào `$WORK/tools_dl`, giải nén 3 đầu vào gốc từ `env/inputs/` (APK gốc, `HL_project*.zip`, `Tool_Viet_Hoa_NAM_P.zip`), build vi5 LGT, disasm `so.dis`/`br.dis`, rồi (`--build`) dựng APK và so với `dist/HL5-VI-v0.2-hd.apk` đã commit — đã thử từ thư mục trống: **trùng từng byte**.
1. Các script đều đọc `WORK` từ biến môi trường (mặc định `~/hl5work`); không còn đường dẫn scratchpad cứng.
2. Build: `hl5_android/make_apk.sh [VERSION]` (mặc định `0.2-hd`; `--diag A|B|C|C1` cho bản chẩn đoán; `ORIG_APK` mặc định `env/inputs/heroes-lore-5-v1.0.5.apk`).
3. Kiểm guest headless: `python3 -I hl5_android/harness/hl5run.py --so <so> --vfs <vfs> --out <dir> --w 600 --h 270 --frames N --keys ...`
4. Công cụ: clang-18/ld.lld, keystone/capstone/unicorn, pyelftools, Android build-tools 34 (`/opt/asdk/android-14`).
5. Khoá ký: `sign/hl5vi.keystore`, mật khẩu trong `sign/README.md`. Người dùng phải gỡ bản gốc trước khi cài.

## 4. Bản đồ file
| Đường dẫn | Nội dung |
|---|---|
| `vi/` | trích (`extract_android.py`), ghép (`align_android.py`, `review_T2d.tsv`), dịch câu mới `A_dich.tsv` (người dùng soát), build dữ liệu (`build_android_vi.py`), kiểm (`check_A.py`) |
| `engine/` | engine chữ Việt trong guest (`vn.c`, `inject.py`, `mk_vibin.py`, `test_vn.py`) |
| `patch/` | `so_patches.tsv` (guest), `bridge_patches.tsv` (bridge arm64), `bridge_review.md`, script áp dụng (kiểm byte gốc) |
| `diag/` | lớp chẩn đoán Java + script chèn smali |
| `harness/` | chạy guest ARM32 bằng Unicorn |
| `tools/` | VFS (`vfs_pack.py`), scn, disasm, xref |
| `shots/` | ảnh nghiệm thu từng task |
| `dist/` | APK phát hành |

## 5. Quy trình làm việc (người dùng chốt)
- Mô hình quản đốc (Opus) – công nhân (Sonnet 5.5), skill `opus-plan-sonnet-build`: tối đa 1 công nhân sửa file; thay công nhân khi ngữ cảnh > 400k.
- Mọi patch: kiểm byte gốc, diff disasm, **lần luồng sau điểm vá tới hết hàm bao** (bài học crash v0.1: `uc_hook_add` trả 0 = thành công).
- Mỗi bản phát hành kèm bản diag. Không có thiết bị thật trong container: bridge chỉ kiểm tĩnh/giả lập → luôn nhờ người dùng thử.
- Git: nhánh `ccr-0532c7e4-lrr5j9`, PR nháp #8; không commit APK diag phụ (xem `.gitignore`).

## 6. Rủi ro / việc còn mở
- Đoạn `nativeOnSurfaceCreated` sau 0xc6e68 chưa giả lập được.
- Bridge armeabi-v7a chưa vá (chỉ arm64).
- 2 chuỗi chưa dịch (`(A)END`, nhãn `EVENT_`); nhãn menu chính tràn ô (sửa ở T7).
