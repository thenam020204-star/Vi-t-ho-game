"""T23: mô phỏng PHÍA HARNESS (Python) các bản vá đề xuất, để đo "sau" mà không sửa mã game/engine.
Mỗi hàm enable_*(c) gắn UC_HOOK_CODE ở đầu hàm guest; nếu đối số rơi vào đường nhanh (tương đương từng byte với vòng gốc) thì ghi bộ nhớ bằng host
rồi đặt PC=LR (bỏ qua vòng guest); nếu không thì để hàm gốc chạy. Số khối guest bị bỏ qua là lợi ước tính (thực tế host memcpy/memset rẻ ~ như T20)."""
import struct
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
import hl5run
B = hl5run.BASE
stats = {}
def _s(k, n=1): stats[k] = stats.get(k, 0) + n

def enable_fillrect(c):
    """MC_grpFillRect 0x1456a4 (fb, x, y, w, h, ctx): đường nhanh khi ctx.alpha(+0x20) > 254 (có dấu), ctx.cb(+0x2c) == 0, độ sâu 16/32."""
    uc = c.g.uc
    def rd(a, n=4): return struct.unpack('<i', bytes(uc.mem_read(a, 4)))[0]
    def hook(uc_, a, s, u):
        fb = uc.reg_read(UC_ARM_REG_R0); x = rd_r(UC_ARM_REG_R1); y = rd_r(UC_ARM_REG_R2); w = rd_r(UC_ARM_REG_R3)
        sp = uc.reg_read(UC_ARM_REG_SP); h = rd(sp); ctx = rd(sp + 4) & 0xffffffff; fb &= 0xffffffff
        FW, FH, stride, depth, data = rd(fb), rd(fb + 4), rd(fb + 8), rd(fb + 0xc), rd(fb + 0x10) & 0xffffffff
        alpha = rd(ctx + 0x20); cb = rd(ctx + 0x2c)
        if alpha <= 254 or cb != 0 or depth not in (16, 32):
            _s('fill_orig'); return
        if w < 0: x += w; w = -w
        if h < 0: y += h; h = -h
        if x < 0: w += x; x = 0
        if y < 0: h += y; y = 0
        xe = min(x + w, FW); ye = min(y + h, FH)
        cx0, cy0, cx1, cy1 = rd(ctx + 4), rd(ctx + 8), rd(ctx + 0xc), rd(ctx + 0x10)
        x0 = max(x, cx0); y0 = max(y, cy0); x1 = min(xe, cx1); y1 = min(ye, cy1)
        _s('fill_fast'); _s('fill_px', max(0, x1 - x0) * max(0, y1 - y0))
        if x0 < x1 and y0 < y1:
            col = rd(ctx + 0x14) & 0xffffffff; es = depth >> 3
            px = struct.pack('<H', col & 0xffff) if es == 2 else struct.pack('<I', col)
            row = px * (x1 - x0)
            for yy in range(y0, y1): uc.mem_write(data + yy * stride + x0 * es, row)
        uc.reg_write(UC_ARM_REG_PC, uc.reg_read(UC_ARM_REG_LR) & ~1)
    def rd_r(r): v = uc.reg_read(r); return v - (1 << 32) if v & 0x80000000 else v
    uc.hook_add(UC_HOOK_CODE, hook, begin=B + 0x1456a4, end=B + 0x1456a4); uc.ctl_flush_tb()
