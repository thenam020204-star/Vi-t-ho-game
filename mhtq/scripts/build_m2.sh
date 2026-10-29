#!/usr/bin/env bash
# Dựng lại Mốc 2 (bản dịch đầy đủ + ngắt dòng/khung thoại 3 dòng). Chạy từ bất kỳ đâu:
#   bash mhtq/scripts/build_m2.sh
# t06_fill -> text_pack -> patch_font --overlay -> patch_wrap -> manifest/jad ASCII -> preverify (trong các patch) -> jar_check
set -e
R="$(cd "$(dirname "$0")/.." && pwd)"
cd "$R"
source work/tools/env.sh
SK=/root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa
B=work/build
mkdir -p $B
python3 -I scripts/t06_fill.py
rm -rf $B/m2_text
python3 -I scripts/text_pack.py --tsv work/text_da_dich.tsv --out $B/m2_text
python3 -I scripts/patch_font.py --overlay $B/m2_text --out-jar $B/MHTQ_K790_font.jar
python3 -I scripts/patch_wrap.py --in-jar $B/MHTQ_K790_font.jar --out-jar $B/MHTQ_K790_m2.jar
python3 -I scripts/set_manifest.py --in-jar $B/MHTQ_K790_m2.jar --out-jar $B/MHTQ_K790_moc2.jar --name "Mong Hoi Tam Quoc"
python3 -I $SK/scripts/jar_check.py goc/MHTQ_K790.jar $B/MHTQ_K790_moc2.jar | tee logs/jar_check_m2.txt
grep -q "Jar-Size: $(stat -c %s $B/MHTQ_K790_moc2.jar)" $B/MHTQ_K790_moc2.jad && echo "JAD size khop"
