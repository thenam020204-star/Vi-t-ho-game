#!/usr/bin/env bash
# Dựng lại cả 5 jar tiếng Việt từ jar gốc.
#   bash rebuild_all.sh [phần...]        (mặc định 1 2 3 4 5)
# Cần: WS (thư mục làm việc có x/N giải nén từ goc/hsN.jar, work/tools, work/lib), source work/tools/env.sh.
# Thứ tự mỗi phần:
#   1) repack text resource   (pN/text_da_dich.tsv, nếu chưa có thì text gốc)
#   2) ảnh: ảnh đã vẽ trong anh_menu/can_ve/pN đè lên anh_menu/pN → đóng gói lại .pak / chép PNG gốc jar
#   3) build_part.sh: vá chuỗi class (pN/class_da_dich.tsv) → chuyển hướng font → preverify → jar_check
#   4) chạy thử FreeJ2ME, chụp màn hình vào $WS/shots/rb_N
set -e
P=${P:-$(cd "$(dirname "$0")/.." && pwd)}; export P
: "${WS:?đặt WS=thư mục làm việc}"; export WS
PARTS=${*:-1 2 3 4 5}
mkdir -p "$P/out"
for N in $PARTS; do
  echo "=== Phần $N"
  [ -d "$WS/x/$N" ] || { mkdir -p "$WS/x/$N"; (cd "$WS/x/$N" && unzip -q "$P/goc/hs$N.jar"); }
  R=$WS/res$N; rm -rf "$R"
  TSV=$P/p$N/text_da_dich.tsv; [ -f "$TSV" ] || TSV=$P/p$N/text_can_dich.tsv
  python3 -I "$P/scripts/repack.py" "$WS/x/$N" "$TSV" "$R" --part "$N"
  # ảnh
  IMG=$WS/img$N; rm -rf "$IMG"; cp -r "$P/anh_menu/p$N" "$IMG"
  if [ -d "$P/anh_menu/can_ve/p$N" ]; then
    (cd "$P/anh_menu/can_ve/p$N" && find . -name '*.png' ! -name '_xem_nhanh.png') | while read -r f; do
      cp "$P/anh_menu/can_ve/p$N/$f" "$IMG/$f"; done
  fi
  for d in "$IMG"/*.pak; do
    [ -d "$d" ] || continue
    python3 -I "$P/scripts/pakimg.py" pack "$d" "$R/$(basename "$d")"
  done
  cp "$IMG"/goc_jar/*.png "$R/"
  # chuỗi class: bản dịch + ghi đè của quản đốc (dòng sau thắng)
  CT=$WS/class_p$N.tsv; printf 'id\tfile\toffset\tgoc\tdich\tghi_chu\tgioi_han\n' > "$CT"
  for f in "$P/p$N/class_da_dich.tsv" "$P/p$N/class_override.tsv"; do [ -f "$f" ] && tail -n +2 "$f" >> "$CT"; done
  bash "$P/scripts/build_part.sh" "$N" "$R" "$P/out/hs${N}_vi.jar" $CT
  # JAD đi kèm
  SZ=$(stat -c%s "$P/out/hs${N}_vi.jar")
  unzip -p "$P/out/hs${N}_vi.jar" META-INF/MANIFEST.MF | python3 -I -c "import sys; t=sys.stdin.read().replace('\r','').replace('\n ',''); sys.stdout.write(''.join(l+'\n' for l in t.split('\n') if l.strip() and not l.startswith(('Manifest-Version','Created-By','Ant-Version'))))" > "$P/out/hs${N}_vi.jad"
  printf 'MIDlet-Jar-URL: hs%s_vi.jar\nMIDlet-Jar-Size: %s\n' "$N" "$SZ" >> "$P/out/hs${N}_vi.jad"
done
echo "Xong: $P/out"
