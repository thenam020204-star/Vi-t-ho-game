#!/usr/bin/env python3
"""HL5: catalog5.json -> list of unique strings to translate (TSV + XLSX).

ID = K<5 digits>, stable for a given catalog (order: UI tables, items, skills, quests, help, story scn).
"""
import json, os, sys, collections

W = os.path.dirname(os.path.abspath(__file__))

GROUPS = [  # (prefix of file name, group label)
    ('csv/menu_text', 'Giao diện - menu'), ('csv/common_text', 'Giao diện - chung'),
    ('csv/ingame_text', 'Giao diện - trong game'), ('csv/tips', 'Mẹo'), ('csv/name', 'Tên nhân vật'),
    ('csv/class', 'Lớp nhân vật'), ('csv/buildup', 'Đặc tính'), ('csv/option', 'Tuỳ chọn'),
    ('csv/set_option', 'Bộ trang bị'), ('csv/item', 'Vật phẩm'), ('csv/cash', 'Cửa hàng cash'),
    ('csv/smith', 'Rèn/chế tạo'), ('csv/skill', 'Kỹ năng'), ('csv/mission', 'Thành tựu/danh hiệu'),
    ('csv/enemy', 'Quái vật'), ('csv/npc', 'NPC'), ('csv/quest', 'Nhiệm vụ'), ('csv2/help', 'Hướng dẫn'),
    ('csv/', 'Bảng khác'), ('map/', 'Cốt truyện (hội thoại)'), ('bin', 'Trong binary'),
]


def group(f):
    f = f.replace('res/c/', '')
    for i, (p, g) in enumerate(GROUPS):
        if f.startswith(p): return i, g
    return len(GROUPS), 'Khác'


def ko_width(s):
    """pixel width of the widest line in the game font (Hangul 10 px, ASCII 6 px, colour codes 0)"""
    best = 0
    for line in s.split(';'):
        w = 0
        for c in line:
            if c in '&|${}\'"`^<[': continue
            w += 6 if ord(c) < 0x80 else 10
        best = max(best, w)
    return best


def main():
    cat = json.load(open(os.path.join(W, 'catalog5.json')))
    first = collections.OrderedDict()
    info = {}
    for e in sorted(cat, key=lambda e: (group(e['file'])[0], e['file'], e.get('rec', e.get('idx', 0)), e.get('off', 0))):
        s = e['src']
        if s not in first:
            first[s] = e
            info[s] = {'n': 0, 'cap': e['cap'], 'files': []}
        info[s]['n'] += 1
        info[s]['cap'] = min(info[s]['cap'], e['cap'])
        fn = os.path.basename(e['file'])
        if fn not in info[s]['files']: info[s]['files'].append(fn)
    rows = []
    for i, (s, e) in enumerate(first.items()):
        g = group(e['file'])[1]
        rows.append(['K%05d' % (i + 1), g, ', '.join(info[s]['files'][:3]) + (' …' if len(info[s]['files']) > 3 else ''),
                     s, '', info[s]['n'], ko_width(s), info[s]['cap']])
    ids = {r[3]: r[0] for r in rows}
    json.dump(ids, open(os.path.join(W, 'ids5.json'), 'w'), ensure_ascii=False, indent=0)
    out = sys.argv[1] if len(sys.argv) > 1 else os.path.join(W, 'out')
    os.makedirs(out, exist_ok=True)
    head = ['ID', 'Nhom', 'File', 'Tieng Han', 'Tieng Viet', 'So lan', 'Rong goc px', 'Gioi han byte']
    with open(os.path.join(out, 'HL5_dich.tsv'), 'w', encoding='utf-8') as f:
        f.write('\t'.join(head) + '\n')
        for r in rows: f.write('\t'.join(str(x).replace('\t', ' ').replace('\n', '\\n') for x in r) + '\n')
    try:
        import openpyxl
        from openpyxl.styles import Font, Alignment, PatternFill
        wb = openpyxl.Workbook(); ws = wb.active; ws.title = 'Dich'
        ws.append(['ID', 'Nhóm', 'File', 'Tiếng Hàn (gốc)', 'Tiếng Việt (điền vào đây)', 'Số lần', 'Rộng dòng gốc (px)', 'Giới hạn byte'])
        for c in ws[1]: c.font = Font(bold=True); c.fill = PatternFill('solid', fgColor='DDEBF7')
        for r in rows: ws.append(r)
        widths = [9, 22, 22, 60, 60, 7, 8, 8]
        for i, w in enumerate(widths): ws.column_dimensions[chr(65 + i)].width = w
        for row in ws.iter_rows(min_row=2):
            row[3].alignment = Alignment(wrap_text=True, vertical='top')
            row[4].alignment = Alignment(wrap_text=True, vertical='top')
            row[4].fill = PatternFill('solid', fgColor='FFF2CC')
        ws.freeze_panes = 'E2'
        ws.auto_filter.ref = 'A1:H%d' % (len(rows) + 1)
        wb.save(os.path.join(out, 'HL5_dich.xlsx'))
    except ImportError:
        print('openpyxl missing: xlsx skipped')
    cnt = collections.Counter(r[1] for r in rows)
    print(len(rows), 'unique strings;', ', '.join('%s %d' % kv for kv in cnt.items()))


if __name__ == '__main__': main()
