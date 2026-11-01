#!/usr/bin/env python3
"""Chèn lời gọi Diag.* (hàm không đối số, không cần thanh ghi) vào smali của runner.
Dùng: patch_smali.py DIR_DX1 DIR_DX2   (DIR_DX1 = baksmali classes.dex, DIR_DX2 = baksmali classes2.dex)
Mỗi chuỗi cần khớp ĐÚNG 1 lần, nếu không thì báo lỗi (không vá bừa)."""
import sys, os
D = 'Lco/kr/eamobile/hl5/diag/Diag;'
def call(n): return '    invoke-static {}, %s->s_%s()V\n' % (D, n)
def callc(n): return '    invoke-static {p0}, %s->s_%s(Landroid/content/Context;)V\n' % (D, n)
def edit(path, rules):
    s = open(path, encoding='utf-8').read()
    for old, new in rules:
        if s.count(old) != 1: sys.exit('%s: khớp %d lần (cần 1): %r' % (os.path.basename(path), s.count(old), old[:70]))
        s = s.replace(old, new)
    open(path, 'w', encoding='utf-8').write(s)
dx1, dx2 = sys.argv[1:3]
R = 'co/kr/eamobile/hl5/runner/'
edit(f'{dx1}/{R}CompatibilityActivity.smali', [
  ('    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V\n',
   callc('launcher') + '    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V\n')])
M = 'Lco/kr/eamobile/hl5/runner/MainActivity;'
edit(f'{dx2}/{R}MainActivity.smali', [
  # <clinit>
  ('.method static constructor <clinit>()V\n    .registers 4\n\n    .line 15\n',
   '.method static constructor <clinit>()V\n    .registers 4\n\n' + call('clinit') + '\n    .line 15\n'),
  ('    invoke-static {v1}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V\n',
   call('loadLibBefore') + '    invoke-static {v1}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V\n' + call('loadLibAfter')),
  ('    :catch_13\n    move-exception v1\n',
   '    :catch_13\n    move-exception v1\n\n    invoke-static {v1}, %s->s_throwable(Ljava/lang/Throwable;)V\n' % D),
  ('    :goto_2e\n    return-void\n', '    :goto_2e\n' + call('clinitEnd') + '    return-void\n'),
  # onCreate
  ('    .registers 5\n\n    .line 61\n    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V\n',
   '    .registers 5\n\n' + callc('mainCreate') + '\n    .line 61\n    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V\n'),
  # thread chép asset
  ('    invoke-static {p0, v0}, Lco/kr/eamobile/hl5/runner/TargetAssets;->install(Landroid/content/Context;Ljava/io/File;)V\n',
   call('instBefore') + '    invoke-static {p0, v0}, Lco/kr/eamobile/hl5/runner/TargetAssets;->install(Landroid/content/Context;Ljava/io/File;)V\n' + call('instAfter')),
  ('    :catch_41\n    move-exception v0\n',
   '    :catch_41\n    move-exception v0\n\n    invoke-static {v0}, %s->s_throwable(Ljava/lang/Throwable;)V\n' % D),
  # GL
  ('.method private startGameView()V\n    .registers 12\n\n    .line 101\n',
   '.method private startGameView()V\n    .registers 12\n\n' + call('startGameView') + '\n    .line 101\n'),
  ('    .registers 3\n\n    .line 420\n', '    .registers 3\n\n' + call('surfCreatedJava') + '\n    .line 420\n'),
  ('    invoke-direct/range {v0 .. v5}, %s->nativeOnSurfaceCreated(Ljava/lang/String;Ljava/lang/String;IIZ)V\n' % M,
   call('nSurfBefore') + '    invoke-direct/range {v0 .. v5}, %s->nativeOnSurfaceCreated(Ljava/lang/String;Ljava/lang/String;IIZ)V\n' % M + call('nSurfAfter')),
  ('    invoke-direct {p0, v3, v4}, %s->nativeOnSurfaceChanged(II)V\n' % M,
   call('nChangedBefore') + '    invoke-direct {p0, v3, v4}, %s->nativeOnSurfaceChanged(II)V\n' % M + call('nChangedAfter')),
  ('    .line 433\n    invoke-direct {p0}, %s->nativeOnResume()V\n' % M,
   '    .line 433\n    invoke-direct {p0}, %s->nativeOnResume()V\n' % M + call('nResumeAfter')),
  ('    .registers 4\n\n    .line 438\n', '    .registers 4\n\n' + call('frame') + '\n    .line 438\n'),
  # vòng đời
  ('    .registers 3\n\n    .line 465\n', '    .registers 3\n\n' + call('pause') + '\n    .line 465\n'),
  ('    invoke-super {p0}, Landroid/app/Activity;->onResume()V\n', '    invoke-super {p0}, Landroid/app/Activity;->onResume()V\n' + call('resume')),
  ('    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V\n', '    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V\n' + call('destroy')),
])
print('smali đã chèn Diag')
