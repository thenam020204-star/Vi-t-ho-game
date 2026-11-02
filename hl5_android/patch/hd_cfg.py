"""Hằng số HD dùng chung (T10) cho bridge (mk_bridge_patches.py) và guest (engine/build.sh -> t10.c).
Đổi MỘT chỗ này rồi dựng lại (make_apk.sh) là đủ."""
DEFAULT_K = 2      # k mặc định (÷k) khi chưa có hd_scale.txt: 1..4. Bridge và menu Cài đặt cùng dùng giá trị này.
HD_MIN_H = 480     # logic cao >= 480 thì bật chữ 18 px (vn_state.hd = 1)
HD_LINE = 18       # BFont[69] (chiều cao dòng; khoảng dòng = [69]+2) khi HD
