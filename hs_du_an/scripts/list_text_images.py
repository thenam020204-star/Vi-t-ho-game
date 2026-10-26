"""Lập danh sách ảnh menu có chữ cần vẽ lại + chép riêng ra anh_menu/can_ve/pN/.
Nội dung chữ được quản đốc đọc bằng mắt từ _xem_nhanh.png (bảng TEXT dưới đây)."""
import os, shutil, sys
from PIL import Image

A = sys.argv[1]  # .../anh_menu
# tên file (trong pak/goc_jar) -> (chữ gốc, gợi ý nghĩa tiếng Việt, ghi chú)
TEXT = {
    'goc_jar/tt.png': ('起始 读取 社区 选项 帮助 关于 退出 (P3+: 下载其它章节)', 'Bắt đầu · Tải game · Cộng đồng · Tuỳ chọn · Trợ giúp · Giới thiệu · Thoát (· Tải chương khác)', 'Menu chính, chữ dọc 2 hàng, mỗi mục một ô cố định — giữ đúng kích thước & vị trí từng ô'),
    'c.pak/mmtext.png': ('奇术 物品 装备 人物 (任务) 天书', 'Kỳ thuật · Vật phẩm · Trang bị · Nhân vật · (Nhiệm vụ) · Thiên thư', 'Menu trong game, mỗi mục một ô cố định — giữ kích thước & vị trí từng ô'),
    'mm.pak/title.png': ('轩辕剑 天之痕 + dấu chương', 'Hiên Viên Kiếm – Thiên Chi Ngân', 'Ảnh tiêu đề 240x320 (thư pháp), tuỳ chọn'),
    'mm.pak/cr.png': ('©2008 拉阔游戏', '©2008 Lakoo Games', 'Dòng bản quyền'),
    'c.pak/help.png': ('帮助', 'Trợ giúp', ''),
    'c.pak/load.png': ('读取', 'Tải', ''),
    'c.pak/s0.png': ('购买', 'Mua', 'Cửa hàng'),
    'c.pak/s1.png': ('贩卖', 'Bán', 'Cửa hàng'),
    'c.pak/save.png': ('存档', 'Lưu', ''),
    'c.pak/set.png': ('设置', 'Cài đặt', ''),
    'c.pak/pt0.png': ('攻击 属性 力量 耐力 智慧 敏捷', 'ATK · Thuộc tính · Lực · Bền · Trí · Nhanh', 'Cột nhãn chỉ số, 6 dòng'),
    'c.pak/pt1.png': ('防御 金 木 水 火 土', 'DEF · Kim · Mộc · Thủy · Hỏa · Thổ', 'Cột nhãn, 6 dòng'),
    'b.pak/a.png': ('攻', 'ATK / Công', 'Biểu tượng chiến đấu 13x13'),
    'b.pak/d.png': ('防', 'DEF / Thủ', 'Biểu tượng chiến đấu 13x13'),
    'b.pak/s.png': ('敏', 'SPD / Mẫn', 'Biểu tượng chiến đấu 13x13'),
    'b.pak/angry.png': ('怒', 'Nộ', 'Biểu tượng 20x20'),
    'b.pak/cri.png': ('爆击', 'Chí mạng', 'Chữ nổi khi đánh'),
    'b.pak/miss.png': ('失误', 'Trượt', 'Chữ nổi khi đánh'),
    'ic.pak/elements.png': ('金木水火土', 'Kim Mộc Thủy Hỏa Thổ', 'Ngũ hành, 5 ô bằng nhau'),
    'ic.pak/i0.png': ('全部', 'Tất cả', 'Tab túi đồ'),
    'ic.pak/i1.png': ('恢复', 'Hồi phục', 'Tab túi đồ'),
    'ic.pak/i2.png': ('辅助', 'Hỗ trợ', 'Tab túi đồ'),
    'ic.pak/i3.png': ('法宝', 'Pháp bảo', 'Tab túi đồ'),
    'ic.pak/i4.png': ('装备', 'Trang bị', 'Tab túi đồ'),
    'ic.pak/i5.png': ('活物', 'Vật sống', 'Tab túi đồ'),
    'ic.pak/i6.png': ('其它', 'Khác', 'Tab túi đồ'),
    'ic.pak/n0.png': ('靖仇', 'Tĩnh Cừu', 'Tên nhân vật'),
    'ic.pak/n1.png': ('如雪 / 小雪', 'Như Tuyết / Tiểu Tuyết', 'Tên nhân vật (P1 如雪, P2 小雪)'),
    'ic.pak/n2.png': ('玉儿', 'Ngọc Nhi', 'Tên nhân vật'),
    'ic.pak/n3.png': ('张烈', 'Trương Liệt', 'Tên nhân vật'),
    'ic.pak/sign.png': ('/ % 级 时 分', '/ % Cấp Giờ Phút', 'Dải ký hiệu, ô bằng nhau'),
    'ic.pak/text.png': ('命 灵 体', 'HP · MP · Thể', 'Cột 3 chữ'),
    'ic.pak/atk.png': ('攻击', 'ATK', 'Nhãn chỉ số'),
    'ic.pak/def.png': ('防御', 'DEF', 'Nhãn chỉ số'),
    'ic.pak/int.png': ('智慧', 'Trí', 'Nhãn chỉ số'),
    'ic.pak/sta.png': ('耐力', 'Bền', 'Nhãn chỉ số'),
    'ic.pak/str.png': ('力量', 'Lực', 'Nhãn chỉ số'),
    'ic.pak/spd.png': ('敏捷', 'SPD', 'Nhãn chỉ số'),
    'ic.pak/phase.png': ('属性', 'Thuộc tính', 'Nhãn'),
    'ic.pak/ph0.png': ('金', 'Kim', 'Ngũ hành'),
    'ic.pak/ph1.png': ('木', 'Mộc', 'Ngũ hành'),
    'ic.pak/ph2.png': ('水', 'Thủy', 'Ngũ hành'),
    'ic.pak/ph3.png': ('火', 'Hỏa', 'Ngũ hành'),
    'ic.pak/ph4.png': ('土', 'Thổ', 'Ngũ hành'),
    'goc_jar/about.png': ('关于', 'Giới thiệu', ''),
    'goc_jar/logo0.png': ('拉阔游戏 (logo)', 'Logo nhà phát hành', 'Tuỳ chọn, thường giữ nguyên'),
    'goc_jar/logo2.png': ('Powered by Lakoo / logo', 'Logo', 'Tuỳ chọn, thường giữ nguyên'),
    'goc_jar/sns_logo.png': ('拉阔', 'Lakoo', 'Logo, tuỳ chọn'),
    'goc_jar/icon.png': ('轩 + số chương', 'Icon game', 'Tuỳ chọn'),
}
rows = ['phan\tfile\tkich_thuoc\tchu_goc\tgoi_y_viet\tghi_chu']
for n in range(1, 6):
    pd = os.path.join(A, 'p%d' % n)
    for rel, (zh, vi, note) in sorted(TEXT.items()):
        src = os.path.join(pd, rel)
        if not os.path.exists(src):
            continue
        w, h = Image.open(src).size
        dst = os.path.join(A, 'can_ve', 'p%d' % n, rel)
        os.makedirs(os.path.dirname(dst), exist_ok=True)
        shutil.copy2(src, dst)
        rows.append('%d\t%s\t%dx%d\t%s\t%s\t%s' % (n, rel, w, h, zh, vi, note))
open(os.path.join(A, 'DANH_SACH_CO_CHU.tsv'), 'w', encoding='utf-8').write('\n'.join(rows) + '\n')
print(len(rows) - 1, 'ảnh có chữ')
