"""Vá script Lua trong common.dat cho tính năng "công việc xong tức thì" của menu mod.
Khối a0d11dd5 = NonInstantAction (lớp gốc mọi hành động có thanh tiến độ: lục đồ, dọn, chặt, dỡ chướng ngại, mở khoá...).
Chỉ thêm một nhánh được bật bằng biến toàn cục TWOMMOD_INSTANT (libtwommod.so đặt qua LuaWrapper::Execute).
Khi biến chưa được đặt (không có menu) hành vi giống hệt bản gốc.
  python3 patch_lua.py <thư mục khối gốc (datpack unpack)> <thư mục khối thay>"""
import sys, os

BLOCK = 'a0d11dd5'

OLD = b"""function NonInstantAction:OnUpdateTime(dt)
\tif self.MultiplierName and self.User then
\t\tdt = dt * self.User:GetParameterValueAsMultiplier(self.MultiplierName);
\tend;
\tself.Timer = self.Timer + dt;"""

NEW = b"""-- twommod: hanh dong "cong viec" cua nguoi trong nhom xong ngay khi bat menu (TWOMMOD_INSTANT)
local TWOMMOD_WORK = {"ScavengeAction", "ClearAction", "RemoveBlockadeAction", "ChopAction", "LockAction", "UnlockAction"};
local function TWOMMOD_IsWork(a)
\tlocal u = a.OnUpdate;
\tfor i = 1, #TWOMMOD_WORK do
\t\tlocal c = _G[TWOMMOD_WORK[i]];
\t\tif c and u == c.OnUpdate then return true; end;
\tend;
\treturn false;
end;

function NonInstantAction:OnUpdateTime(dt)
\tif self.MultiplierName and self.User then
\t\tdt = dt * self.User:GetParameterValueAsMultiplier(self.MultiplierName);
\tend;
\tif TWOMMOD_INSTANT and self.User and self.Duration and self.Duration > 0 and self.User:HasTag("Dweller") and TWOMMOD_IsWork(self) then
\t\tlocal need = self.Duration - self.Timer;
\t\tif need > dt then dt = need; end;
\tend;
\tself.Timer = self.Timer + dt;"""


def patch(src):
    nl = b'\r\n' if b'\r\n' in src else b'\n'          # file gốc dùng CRLF
    old, new = OLD.replace(b'\n', nl), NEW.replace(b'\n', nl)
    assert src.count(old) == 1, 'NonInstantAction:OnUpdateTime không khớp bản gốc'
    return src.replace(old, new)


if __name__ == '__main__':
    ex, rep = sys.argv[1], sys.argv[2]
    p = os.path.join(rep, BLOCK + '.bin')
    src = open(p if os.path.exists(p) else os.path.join(ex, BLOCK + '.bin'), 'rb').read()
    os.makedirs(rep, exist_ok=True)
    open(p, 'wb').write(patch(src))
    print('lua: đã vá', BLOCK)
