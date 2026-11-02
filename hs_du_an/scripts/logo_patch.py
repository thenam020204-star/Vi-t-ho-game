"""Rút màn logo đầu game còn 1 logo (logo2.png).

  python3 logo_patch.py <in.class> <out.class>

Class logo của cả 5 phần (P1 g/i, P2 d/j, P3 d/l, P4 d/l, P5 c/i) có <clinit> giống hệt:
    static byte[] X = {2, 0, 1};   -> lần lượt /logo2.png, /logo0.png, /logo1.png, mỗi ảnh 2000 ms
Bytecode gốc (19 byte):
    iconst_3 newarray byte | dup iconst_0 iconst_2 bastore | dup iconst_1 iconst_0 bastore |
    dup iconst_2 iconst_1 bastore | putstatic #k | return
Vá thành mảng 1 phần tử {2}: iconst_1, và thay 8 byte của hai lệnh gán sau bằng nop.
Cùng độ dài code, không đổi constant pool, descriptor, max_stack; <clinit> không có nhánh nên
không cần StackMap (vẫn preverify lại cả jar).
"""
import sys

OLD = bytes([0x06, 0xBC, 0x08, 0x59, 0x03, 0x05, 0x54, 0x59, 0x04, 0x03, 0x54, 0x59, 0x05, 0x04, 0x54, 0xB3])
NEW = bytes([0x04, 0xBC, 0x08, 0x59, 0x03, 0x05, 0x54]) + bytes(8) + bytes([0xB3])


def main():
    src, dst = sys.argv[1:3]
    data = open(src, 'rb').read()
    n = data.count(OLD)
    if n != 1:
        sys.exit('không thấy đúng 1 mẫu <clinit> {2,0,1} (thấy %d)' % n)
    i = data.index(OLD)
    if data[i + 18] != 0xB1:  # putstatic u2 rồi return
        sys.exit('sau putstatic không phải return')
    open(dst, 'wb').write(data[:i] + NEW + data[i + len(NEW):])
    print('đã vá', src, '@', i)


if __name__ == '__main__':
    main()
