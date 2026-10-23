#!/usr/bin/env python3
"""Quet moi method Assembly-CSharp: chuoi literal + lenh goi (UI.Text...). Dung: scan_code.py so script.json csharp_methods.json out.json"""
import sys, json, re
sys.path.insert(0, __import__('os').path.dirname(__import__('os').path.abspath(__file__)))
import il2dis as D
so, sj, mj, outp = sys.argv[1:5]
d = D.Dis(so, sj)
ms = json.load(open(mj))
res = {}
for k, (cls, sig) in ms.items():
    a = int(k)
    try:
        lines = d.run(a, 100000)
    except Exception as e:
        continue
    strs = []; calls = []
    for l in lines:
        m = re.search(r'; -> 0x[0-9a-f]+ STR (".*")$', l)
        if m: strs.append(json.loads(m.group(1)) if m.group(1).endswith('"') else m.group(1))
        m = re.search(r'bl +#0x[0-9a-f]+ +; (.*)$', l)
        if m: calls.append(m.group(1))
    res[k] = dict(cls=cls, sig=sig, name=d.meth.get(a, ''), strs=strs, calls=calls)
json.dump(res, open(outp, 'w'), ensure_ascii=False)
print(len(res), sum(len(v['strs']) for v in res.values()))
