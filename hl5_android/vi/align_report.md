# Báo cáo ghép chuỗi Android <-> LGT (T2)

Sinh bởi `align_android.py`. Ngưỡng: NEW nếu điểm < 0.70, không có câu Hàn tương ứng, câu Hàn là chỗ giữ chỗ, hoặc câu Anh dài/nhiều đoạn hơn hẳn (xem ko_hint); "điểm cao" >= 0.70.

## Tổng kết

| nhóm | tổng câu | ghép được (K#####) | NEW | trong đó điểm cao |
|---|---:|---:|---:|---:|
| scn | 7163 | 6760 | 403 | 6760 |
| dat | 7121 | 6471 | 650 | 6471 |
| tất cả | 14284 | 13231 | 1053 | 13231 |

## scn cùng số câu (ghép theo chỉ số)

- Trong 214 file scn có ở cả hai bản: **189** cùng số câu bảng chuỗi, 25 khác số câu (kể cả file không có câu chữ).
- Số file scn Android có câu chữ = 203; trong đó cùng số câu bảng chuỗi với LGT = **182** file (5888 câu Android).
- Soát nghĩa thủ công T2d (`review_T2d.tsv`, SAI/LỆCH -> NEW): 66 câu {'dat/rec': 1, 'scn/dp': 64, 'scn/idx': 1}.
- Cặp điểm cao (>= 0.70): **5844 / 5888 = 99.3%**; ghép được (>= 0.70): 5844 (99.3%); NEW: 44.
- File cùng số câu nhưng phải dùng DP (ghép theo chỉ số cho điểm thấp): 0 []

## Bảng ghép FILE scn Android -> LGT (theo nội dung, T2c)

Điểm file = 0.6*bytecode (Dice 4-gram của khối script) + 0.4*độ giống câu (+0.02 nếu cùng số file); gán 1-1 tham lam; < 0.50 thì không ghép (toàn bộ câu NEW).

### File đổi ghép so với ghép theo số file

| file Android | LGT theo số | LGT theo nội dung | điểm | bytecode | câu |
|---|---|---|---:|---:|---:|
| c/map/00019.scn | c/map/00019.scn | (không ghép: NEW) | - | - | - |
| c/map/00175.scn | c/map/00175.scn | (không ghép: NEW) | - | - | - |
| c/map/00186.scn | c/map/00186.scn | (không ghép: NEW) | - | - | - |
| c/map/00207.scn | c/map/00207.scn | c/map/00208.scn | 0.98 | 1.00 | 0.95 |
| c/map/00208.scn | c/map/00208.scn | c/map/00209.scn | 0.93 | 0.88 | 1.00 |
| c/map/00209.scn | c/map/00209.scn | c/map/00211.scn | 0.96 | 0.94 | 0.98 |
| c/map/00210.scn | c/map/00210.scn | c/map/00212.scn | 1.00 | 1.00 | 1.00 |
| c/map/00211.scn | c/map/00211.scn | c/map/00213.scn | 1.00 | 1.00 | 1.00 |
| c/map/00212.scn | c/map/00212.scn | c/map/00214.scn | 1.00 | 1.00 | 1.00 |
| c/map/00213.scn | c/map/00213.scn | c/map/00215.scn | 0.91 | 0.88 | 0.94 |
| c/map/00214.scn | c/map/00214.scn | (không ghép: NEW) | - | - | - |
| c/map/00215.scn | c/map/00215.scn | c/map/00216.scn | 0.97 | 1.00 | 0.91 |
| c/map/00216.scn | c/map/00216.scn | (không ghép: NEW) | - | - | - |

Tổng số file đổi ghép: **13** (trong 203 file scn có chữ).

### Toàn bộ bảng ghép file

| file Android | LGT | điểm file | bytecode | câu |
|---|---|---:|---:|---:|
| c/map/00000.scn | c/map/00000.scn | 0.71 | 0.85 | 0.44 |
| c/map/00001.scn | c/map/00001.scn | 0.99 | 1.00 | 0.93 |
| c/map/00002.scn | c/map/00002.scn | 1.01 | 1.00 | 0.97 |
| c/map/00003.scn | c/map/00003.scn | 0.98 | 0.99 | 0.92 |
| c/map/00004.scn | c/map/00004.scn | 1.02 | 1.00 | 1.00 |
| c/map/00005.scn | c/map/00005.scn | 0.98 | 0.97 | 0.95 |
| c/map/00006.scn | c/map/00006.scn | 1.00 | 1.00 | 0.94 |
| c/map/00007.scn | c/map/00007.scn | 0.93 | 1.00 | 0.77 |
| c/map/00008.scn | c/map/00008.scn | 1.00 | 1.00 | 0.94 |
| c/map/00009.scn | c/map/00009.scn | 1.01 | 1.00 | 0.97 |
| c/map/00010.scn | c/map/00010.scn | 1.00 | 1.00 | 0.96 |
| c/map/00011.scn | c/map/00011.scn | 1.02 | 1.00 | 0.99 |
| c/map/00012.scn | c/map/00012.scn | 1.01 | 1.00 | 0.97 |
| c/map/00013.scn | c/map/00013.scn | 0.99 | 1.00 | 0.92 |
| c/map/00014.scn | c/map/00014.scn | 0.99 | 1.00 | 0.93 |
| c/map/00015.scn | c/map/00015.scn | 1.00 | 1.00 | 0.96 |
| c/map/00019.scn | - | - | - | - |
| c/map/00020.scn | c/map/00020.scn | 1.01 | 1.00 | 0.99 |
| c/map/00021.scn | c/map/00021.scn | 1.01 | 1.00 | 0.97 |
| c/map/00023.scn | c/map/00023.scn | 1.00 | 1.00 | 0.95 |
| c/map/00024.scn | c/map/00024.scn | 0.88 | 0.93 | 0.75 |
| c/map/00025.scn | c/map/00025.scn | 1.01 | 1.00 | 0.98 |
| c/map/00026.scn | c/map/00026.scn | 1.00 | 1.00 | 0.95 |
| c/map/00028.scn | c/map/00028.scn | 0.88 | 0.84 | 0.89 |
| c/map/00029.scn | c/map/00029.scn | 0.99 | 0.99 | 0.94 |
| c/map/00030.scn | c/map/00030.scn | 0.73 | 0.75 | 0.66 |
| c/map/00031.scn | c/map/00031.scn | 1.00 | 0.99 | 0.97 |
| c/map/00033.scn | c/map/00033.scn | 1.02 | 1.00 | 1.00 |
| c/map/00034.scn | c/map/00034.scn | 1.01 | 1.00 | 0.97 |
| c/map/00035.scn | c/map/00035.scn | 1.01 | 1.00 | 0.97 |
| c/map/00036.scn | c/map/00036.scn | 0.99 | 1.00 | 0.93 |
| c/map/00037.scn | c/map/00037.scn | 1.01 | 1.00 | 0.97 |
| c/map/00038.scn | c/map/00038.scn | 1.00 | 0.99 | 0.97 |
| c/map/00039.scn | c/map/00039.scn | 1.02 | 1.00 | 1.00 |
| c/map/00040.scn | c/map/00040.scn | 1.01 | 1.00 | 0.97 |
| c/map/00041.scn | c/map/00041.scn | 1.00 | 1.00 | 0.96 |
| c/map/00042.scn | c/map/00042.scn | 1.00 | 1.00 | 0.96 |
| c/map/00043.scn | c/map/00043.scn | 1.00 | 1.00 | 0.96 |
| c/map/00044.scn | c/map/00044.scn | 1.01 | 1.00 | 0.98 |
| c/map/00045.scn | c/map/00045.scn | 0.99 | 1.00 | 0.92 |
| c/map/00046.scn | c/map/00046.scn | 1.00 | 0.99 | 0.96 |
| c/map/00047.scn | c/map/00047.scn | 1.01 | 1.00 | 0.97 |
| c/map/00048.scn | c/map/00048.scn | 1.01 | 1.00 | 0.97 |
| c/map/00049.scn | c/map/00049.scn | 1.01 | 1.00 | 0.97 |
| c/map/00050.scn | c/map/00050.scn | 1.00 | 1.00 | 0.95 |
| c/map/00051.scn | c/map/00051.scn | 1.00 | 1.00 | 0.97 |
| c/map/00052.scn | c/map/00052.scn | 1.00 | 0.99 | 0.96 |
| c/map/00053.scn | c/map/00053.scn | 1.00 | 1.00 | 0.95 |
| c/map/00054.scn | c/map/00054.scn | 1.01 | 1.00 | 0.97 |
| c/map/00055.scn | c/map/00055.scn | 1.01 | 1.00 | 0.98 |
| c/map/00056.scn | c/map/00056.scn | 0.99 | 1.00 | 0.93 |
| c/map/00057.scn | c/map/00057.scn | 0.98 | 0.96 | 0.97 |
| c/map/00058.scn | c/map/00058.scn | 1.01 | 1.00 | 0.98 |
| c/map/00059.scn | c/map/00059.scn | 1.01 | 1.00 | 0.99 |
| c/map/00060.scn | c/map/00060.scn | 1.00 | 1.00 | 0.96 |
| c/map/00061.scn | c/map/00061.scn | 1.01 | 1.00 | 0.98 |
| c/map/00062.scn | c/map/00062.scn | 1.02 | 1.00 | 1.00 |
| c/map/00063.scn | c/map/00063.scn | 1.00 | 0.99 | 0.97 |
| c/map/00064.scn | c/map/00064.scn | 1.02 | 1.00 | 0.99 |
| c/map/00065.scn | c/map/00065.scn | 1.02 | 1.00 | 1.00 |
| c/map/00066.scn | c/map/00066.scn | 1.00 | 0.99 | 0.97 |
| c/map/00067.scn | c/map/00067.scn | 1.01 | 1.00 | 0.98 |
| c/map/00068.scn | c/map/00068.scn | 0.91 | 0.86 | 0.95 |
| c/map/00069.scn | c/map/00069.scn | 1.01 | 0.98 | 0.99 |
| c/map/00070.scn | c/map/00070.scn | 1.00 | 1.00 | 0.95 |
| c/map/00071.scn | c/map/00071.scn | 1.02 | 1.00 | 1.00 |
| c/map/00072.scn | c/map/00072.scn | 1.01 | 1.00 | 0.97 |
| c/map/00073.scn | c/map/00073.scn | 1.00 | 1.00 | 0.94 |
| c/map/00074.scn | c/map/00074.scn | 1.02 | 1.00 | 0.99 |
| c/map/00075.scn | c/map/00075.scn | 1.00 | 1.00 | 0.96 |
| c/map/00076.scn | c/map/00076.scn | 1.00 | 0.99 | 0.97 |
| c/map/00077.scn | c/map/00077.scn | 1.00 | 0.99 | 0.97 |
| c/map/00078.scn | c/map/00078.scn | 1.00 | 1.00 | 0.95 |
| c/map/00079.scn | c/map/00079.scn | 0.99 | 0.97 | 0.98 |
| c/map/00080.scn | c/map/00080.scn | 1.00 | 1.00 | 0.95 |
| c/map/00081.scn | c/map/00081.scn | 1.00 | 0.99 | 0.96 |
| c/map/00082.scn | c/map/00082.scn | 1.00 | 1.00 | 0.96 |
| c/map/00083.scn | c/map/00083.scn | 0.99 | 0.99 | 0.94 |
| c/map/00084.scn | c/map/00084.scn | 1.00 | 1.00 | 0.96 |
| c/map/00085.scn | c/map/00085.scn | 0.96 | 0.93 | 0.95 |
| c/map/00086.scn | c/map/00086.scn | 1.00 | 0.98 | 0.97 |
| c/map/00087.scn | c/map/00087.scn | 0.87 | 0.78 | 0.95 |
| c/map/00088.scn | c/map/00088.scn | 0.99 | 0.99 | 0.95 |
| c/map/00089.scn | c/map/00089.scn | 1.00 | 0.99 | 0.97 |
| c/map/00090.scn | c/map/00090.scn | 1.00 | 1.00 | 0.96 |
| c/map/00091.scn | c/map/00091.scn | 1.02 | 1.00 | 1.00 |
| c/map/00092.scn | c/map/00092.scn | 1.02 | 1.00 | 1.00 |
| c/map/00093.scn | c/map/00093.scn | 1.02 | 1.00 | 1.00 |
| c/map/00094.scn | c/map/00094.scn | 0.93 | 0.88 | 0.96 |
| c/map/00095.scn | c/map/00095.scn | 1.00 | 0.98 | 0.97 |
| c/map/00096.scn | c/map/00096.scn | 1.02 | 1.00 | 1.00 |
| c/map/00097.scn | c/map/00097.scn | 1.00 | 1.00 | 0.95 |
| c/map/00098.scn | c/map/00098.scn | 1.00 | 1.00 | 0.97 |
| c/map/00099.scn | c/map/00099.scn | 0.97 | 0.95 | 0.95 |
| c/map/00100.scn | c/map/00100.scn | 1.01 | 1.00 | 0.97 |
| c/map/00101.scn | c/map/00101.scn | 1.02 | 1.00 | 1.00 |
| c/map/00102.scn | c/map/00102.scn | 1.02 | 1.00 | 1.00 |
| c/map/00103.scn | c/map/00103.scn | 0.92 | 0.86 | 0.95 |
| c/map/00104.scn | c/map/00104.scn | 1.01 | 1.00 | 0.96 |
| c/map/00105.scn | c/map/00105.scn | 1.01 | 1.00 | 0.96 |
| c/map/00106.scn | c/map/00106.scn | 0.94 | 0.86 | 1.00 |
| c/map/00107.scn | c/map/00107.scn | 0.91 | 0.92 | 0.83 |
| c/map/00108.scn | c/map/00108.scn | 0.91 | 0.87 | 0.92 |
| c/map/00109.scn | c/map/00109.scn | 0.99 | 1.00 | 0.91 |
| c/map/00110.scn | c/map/00110.scn | 1.02 | 1.00 | 1.00 |
| c/map/00111.scn | c/map/00111.scn | 0.98 | 0.95 | 0.99 |
| c/map/00112.scn | c/map/00112.scn | 1.02 | 1.00 | 1.00 |
| c/map/00113.scn | c/map/00113.scn | 0.80 | 0.76 | 0.82 |
| c/map/00114.scn | c/map/00114.scn | 1.00 | 1.00 | 0.96 |
| c/map/00115.scn | c/map/00115.scn | 1.02 | 1.00 | 1.00 |
| c/map/00116.scn | c/map/00116.scn | 1.00 | 1.00 | 0.96 |
| c/map/00117.scn | c/map/00117.scn | 1.02 | 1.00 | 1.00 |
| c/map/00118.scn | c/map/00118.scn | 1.02 | 1.00 | 1.00 |
| c/map/00119.scn | c/map/00119.scn | 1.02 | 1.00 | 1.00 |
| c/map/00120.scn | c/map/00120.scn | 0.99 | 0.94 | 1.00 |
| c/map/00121.scn | c/map/00121.scn | 0.99 | 0.94 | 1.00 |
| c/map/00122.scn | c/map/00122.scn | 0.99 | 0.94 | 1.00 |
| c/map/00123.scn | c/map/00123.scn | 1.00 | 0.97 | 1.00 |
| c/map/00124.scn | c/map/00124.scn | 1.02 | 1.00 | 1.00 |
| c/map/00125.scn | c/map/00125.scn | 0.97 | 0.91 | 1.00 |
| c/map/00126.scn | c/map/00126.scn | 1.02 | 1.00 | 1.00 |
| c/map/00127.scn | c/map/00127.scn | 1.01 | 1.00 | 0.97 |
| c/map/00128.scn | c/map/00128.scn | 0.99 | 1.00 | 0.92 |
| c/map/00129.scn | c/map/00129.scn | 1.00 | 1.00 | 0.96 |
| c/map/00130.scn | c/map/00130.scn | 0.99 | 0.97 | 0.96 |
| c/map/00131.scn | c/map/00131.scn | 1.01 | 1.00 | 0.98 |
| c/map/00132.scn | c/map/00132.scn | 0.99 | 0.99 | 0.96 |
| c/map/00133.scn | c/map/00133.scn | 1.00 | 0.99 | 0.95 |
| c/map/00134.scn | c/map/00134.scn | 1.01 | 1.00 | 0.98 |
| c/map/00135.scn | c/map/00135.scn | 1.02 | 1.00 | 1.00 |
| c/map/00136.scn | c/map/00136.scn | 0.55 | 0.46 | 0.63 |
| c/map/00137.scn | c/map/00137.scn | 1.02 | 1.00 | 0.99 |
| c/map/00138.scn | c/map/00138.scn | 0.99 | 0.94 | 1.00 |
| c/map/00139.scn | c/map/00139.scn | 1.00 | 0.98 | 0.99 |
| c/map/00140.scn | c/map/00140.scn | 1.01 | 0.99 | 0.98 |
| c/map/00141.scn | c/map/00141.scn | 1.02 | 1.00 | 1.00 |
| c/map/00142.scn | c/map/00142.scn | 1.02 | 1.00 | 1.00 |
| c/map/00143.scn | c/map/00143.scn | 1.02 | 1.00 | 1.00 |
| c/map/00144.scn | c/map/00144.scn | 0.96 | 0.94 | 0.93 |
| c/map/00145.scn | c/map/00145.scn | 1.02 | 1.00 | 1.00 |
| c/map/00146.scn | c/map/00146.scn | 0.97 | 0.95 | 0.96 |
| c/map/00147.scn | c/map/00147.scn | 1.02 | 1.00 | 1.00 |
| c/map/00148.scn | c/map/00148.scn | 0.76 | 0.61 | 0.94 |
| c/map/00149.scn | c/map/00149.scn | 1.02 | 1.00 | 1.00 |
| c/map/00150.scn | c/map/00150.scn | 0.81 | 0.71 | 0.91 |
| c/map/00151.scn | c/map/00151.scn | 0.98 | 0.98 | 0.92 |
| c/map/00152.scn | c/map/00152.scn | 1.00 | 0.99 | 0.98 |
| c/map/00153.scn | c/map/00153.scn | 1.00 | 0.99 | 0.95 |
| c/map/00154.scn | c/map/00154.scn | 1.00 | 0.99 | 0.98 |
| c/map/00155.scn | c/map/00155.scn | 1.00 | 1.00 | 0.96 |
| c/map/00156.scn | c/map/00156.scn | 0.97 | 0.92 | 1.00 |
| c/map/00157.scn | c/map/00157.scn | 1.00 | 1.00 | 0.95 |
| c/map/00158.scn | c/map/00158.scn | 1.02 | 1.00 | 1.00 |
| c/map/00159.scn | c/map/00159.scn | 1.02 | 1.00 | 1.00 |
| c/map/00160.scn | c/map/00160.scn | 1.00 | 1.00 | 0.96 |
| c/map/00161.scn | c/map/00161.scn | 1.00 | 0.99 | 0.97 |
| c/map/00162.scn | c/map/00162.scn | 1.00 | 1.00 | 0.96 |
| c/map/00163.scn | c/map/00163.scn | 0.87 | 0.77 | 0.98 |
| c/map/00164.scn | c/map/00164.scn | 1.02 | 1.00 | 1.00 |
| c/map/00165.scn | c/map/00165.scn | 1.02 | 1.00 | 1.00 |
| c/map/00166.scn | c/map/00166.scn | 1.02 | 1.00 | 1.00 |
| c/map/00167.scn | c/map/00167.scn | 1.02 | 1.00 | 1.00 |
| c/map/00168.scn | c/map/00168.scn | 1.02 | 1.00 | 1.00 |
| c/map/00169.scn | c/map/00169.scn | 1.02 | 1.00 | 1.00 |
| c/map/00170.scn | c/map/00170.scn | 0.59 | 0.50 | 0.68 |
| c/map/00171.scn | c/map/00171.scn | 0.99 | 0.99 | 0.94 |
| c/map/00172.scn | c/map/00172.scn | 0.99 | 0.98 | 0.95 |
| c/map/00173.scn | c/map/00173.scn | 1.02 | 1.00 | 1.00 |
| c/map/00174.scn | c/map/00174.scn | 0.74 | 0.66 | 0.81 |
| c/map/00175.scn | - | - | - | - |
| c/map/00176.scn | c/map/00176.scn | 0.97 | 0.95 | 0.96 |
| c/map/00181.scn | c/map/00181.scn | 1.00 | 1.00 | 0.96 |
| c/map/00182.scn | c/map/00182.scn | 1.01 | 1.00 | 0.96 |
| c/map/00186.scn | - | - | - | - |
| c/map/00187.scn | c/map/00187.scn | 1.00 | 1.00 | 0.94 |
| c/map/00188.scn | c/map/00188.scn | 1.00 | 1.00 | 0.96 |
| c/map/00189.scn | c/map/00189.scn | 0.99 | 0.96 | 0.99 |
| c/map/00190.scn | c/map/00190.scn | 1.02 | 1.00 | 1.00 |
| c/map/00191.scn | c/map/00191.scn | 1.02 | 1.00 | 1.00 |
| c/map/00192.scn | c/map/00192.scn | 1.01 | 1.00 | 0.97 |
| c/map/00193.scn | c/map/00193.scn | 1.02 | 1.00 | 1.00 |
| c/map/00194.scn | c/map/00194.scn | 1.02 | 1.00 | 1.00 |
| c/map/00195.scn | c/map/00195.scn | 1.00 | 1.00 | 0.96 |
| c/map/00196.scn | c/map/00196.scn | 1.02 | 1.00 | 1.00 |
| c/map/00197.scn | c/map/00197.scn | 0.99 | 0.97 | 0.98 |
| c/map/00198.scn | c/map/00198.scn | 0.74 | 0.64 | 0.83 |
| c/map/00199.scn | c/map/00199.scn | 1.01 | 1.00 | 0.97 |
| c/map/00200.scn | c/map/00200.scn | 0.86 | 0.85 | 0.84 |
| c/map/00201.scn | c/map/00201.scn | 0.82 | 0.77 | 0.86 |
| c/map/00202.scn | c/map/00202.scn | 0.89 | 0.88 | 0.85 |
| c/map/00203.scn | c/map/00203.scn | 1.02 | 1.00 | 1.00 |
| c/map/00204.scn | c/map/00204.scn | 1.01 | 1.00 | 0.96 |
| c/map/00205.scn | c/map/00205.scn | 1.01 | 1.00 | 0.97 |
| c/map/00207.scn | c/map/00208.scn | 0.98 | 1.00 | 0.95 |
| c/map/00208.scn | c/map/00209.scn | 0.93 | 0.88 | 1.00 |
| c/map/00209.scn | c/map/00211.scn | 0.96 | 0.94 | 0.98 |
| c/map/00210.scn | c/map/00212.scn | 1.00 | 1.00 | 1.00 |
| c/map/00211.scn | c/map/00213.scn | 1.00 | 1.00 | 1.00 |
| c/map/00212.scn | c/map/00214.scn | 1.00 | 1.00 | 1.00 |
| c/map/00213.scn | c/map/00215.scn | 0.91 | 0.88 | 0.94 |
| c/map/00214.scn | - | - | - | - |
| c/map/00215.scn | c/map/00216.scn | 0.97 | 1.00 | 0.91 |
| c/map/00216.scn | - | - | - | - |

## scn khác số câu (DP)

| file | bảng Android | bảng LGT | câu Android có chữ | ghép | NEW |
|---|---:|---:|---:|---:|---:|
| c/map/00000.scn | 19 | 41 | 18 | 18 | 0 |
| c/map/00019.scn | None | None | 19 | 0 | 19 |
| c/map/00030.scn | 43 | 30 | 43 | 30 | 13 |
| c/map/00068.scn | 111 | 110 | 111 | 108 | 3 |
| c/map/00087.scn | 99 | 96 | 99 | 94 | 5 |
| c/map/00107.scn | 22 | 24 | 22 | 12 | 10 |
| c/map/00113.scn | 29 | 28 | 29 | 24 | 5 |
| c/map/00136.scn | 45 | 34 | 45 | 2 | 43 |
| c/map/00144.scn | 66 | 67 | 66 | 53 | 13 |
| c/map/00150.scn | 13 | 12 | 13 | 11 | 2 |
| c/map/00151.scn | 188 | 185 | 188 | 182 | 6 |
| c/map/00170.scn | 149 | 103 | 148 | 96 | 52 |
| c/map/00174.scn | 218 | 181 | 218 | 173 | 45 |
| c/map/00175.scn | None | None | 17 | 0 | 17 |
| c/map/00186.scn | None | None | 36 | 0 | 36 |
| c/map/00198.scn | 52 | 44 | 52 | 42 | 10 |
| c/map/00200.scn | 19 | 16 | 19 | 16 | 3 |
| c/map/00201.scn | 40 | 35 | 40 | 35 | 5 |
| c/map/00202.scn | 23 | 20 | 23 | 20 | 3 |
| c/map/00214.scn | None | None | 16 | 0 | 16 |
| c/map/00216.scn | None | None | 53 | 0 | 53 |

## .dat: kết luận về trường khoá

Không có trường ID duy nhất đầu bản ghi dùng chung cho mọi file (nhiều file bản ghi bắt đầu bằng số loại/kiểu lặp lại hoặc chỉ chứa chuỗi). Khoá thực dụng: **bản ghi LGT và Android giữ nguyên thứ tự tương đối; Android chỉ chèn thêm/bớt bản ghi**. Do đó ghép bản ghi bằng DP tuần tự theo (a) khung xương = bản ghi bỏ các chuỗi (giống nhau từng byte ở phần lớn bản ghi), (b) độ giống chuỗi. Cột `diag` = số bản ghi ghép vẫn cùng chỉ số; `ident` = số cặp khung xương giống hệt; `shifts` = độ lệch chỉ số phổ biến (K - A).

| file Android | file LGT | bản ghi A | bản ghi K | ghép | diag | khung giống hệt | độ lệch phổ biến | câu A | NEW |
|---|---|---:|---:|---:|---:|---:|---|---:|---:|
| c/csv/buildup.dat | c/csv/buildup.dat | 111 | 111 | 111 | 111 | 110 | +0:111 | 111 | 1 |
| c/csv/cash_expert.dat | c/csv/cash_expert.dat | 9 | 9 | 9 | 9 | 8 | +0:9 | 18 | 7 |
| c/csv/cash_network.dat | c/csv/cash_network.dat | 34 | 34 | 34 | 34 | 34 | +0:34 | 68 | 2 |
| vfs:0x831ffd41.dat | c/csv/cash_single.dat | 30 | 29 | 24 | 1 | 0 | +5:17 +1:3 +0:1 +2:1 | 60 | 14 |
| c/csv/cash_single.dat | c/csv/cash_single.dat | 29 | 29 | 29 | 29 | 29 | +0:29 | 58 | 2 |
| vfs:0x2b3c2959.dat | c/csv/cash_single.dat | 30 | 29 | 24 | 1 | 0 | +5:17 +1:3 +0:1 +2:1 | 60 | 14 |
| c/csv/class.dat | c/csv/class.dat | 5 | 5 | 5 | 5 | 5 | +0:5 | 5 | 0 |
| c/csv/common_text.dat | c/csv/common_text.dat | 351 | 333 | 304 | 304 | 304 | +0:304 | 347 | 46 |
| c/csv/enemy_0.dat | c/csv/enemy_0.dat | 166 | 166 | 166 | 166 | 0 | +0:166 | 166 | 0 |
| c/csv/enemy_1.dat | c/csv/enemy_1.dat | 166 | 166 | 166 | 166 | 0 | +0:166 | 166 | 1 |
| c/csv/enemy_2.dat | c/csv/enemy_2.dat | 166 | 166 | 166 | 166 | 0 | +0:166 | 166 | 1 |
| c/csv/enemy_expert_0.dat | c/csv/enemy_expert_0.dat | 142 | 142 | 142 | 142 | 0 | +0:142 | 142 | 0 |
| c/csv/enemy_expert_1.dat | c/csv/enemy_expert_1.dat | 142 | 142 | 142 | 142 | 0 | +0:142 | 142 | 0 |
| c/csv/enemy_expert_2.dat | c/csv/enemy_expert_2.dat | 142 | 142 | 142 | 142 | 0 | +0:142 | 142 | 0 |
| c/csv/ingame_text.dat | c/csv/ingame_text.dat | 357 | 355 | 347 | 340 | 347 | +0:340 +1:6 +2:1 | 356 | 16 |
| c/csv/item_00.dat | c/csv/item_00.dat | 86 | 81 | 81 | 81 | 81 | +0:81 | 172 | 10 |
| c/csv/item_01.dat | c/csv/item_01.dat | 86 | 81 | 81 | 81 | 81 | +0:81 | 172 | 10 |
| c/csv/item_02.dat | c/csv/item_02.dat | 86 | 81 | 81 | 81 | 81 | +0:81 | 172 | 10 |
| c/csv/item_03.dat | c/csv/item_03.dat | 86 | 81 | 81 | 81 | 81 | +0:81 | 172 | 10 |
| c/csv/item_04.dat | c/csv/item_04.dat | 1 | 1 | 1 | 1 | 1 | +0:1 | 2 | 0 |
| c/csv/item_05.dat | c/csv/item_05.dat | 90 | 90 | 90 | 90 | 90 | +0:90 | 90 | 0 |
| c/csv/item_06.dat | c/csv/item_06.dat | 93 | 93 | 93 | 93 | 93 | +0:93 | 93 | 0 |
| c/csv/item_07.dat | c/csv/item_07.dat | 81 | 81 | 81 | 81 | 81 | +0:81 | 81 | 0 |
| c/csv/item_08.dat | c/csv/item_08.dat | 81 | 81 | 81 | 81 | 81 | +0:81 | 81 | 0 |
| c/csv/item_09.dat | c/csv/item_09.dat | 81 | 81 | 81 | 81 | 81 | +0:81 | 81 | 0 |
| c/csv/item_10.dat | c/csv/item_10.dat | 18 | 17 | 17 | 17 | 10 | +0:17 | 19 | 1 |
| c/csv/item_11.dat | c/csv/item_11.dat | 16 | 16 | 16 | 16 | 16 | +0:16 | 32 | 5 |
| c/csv/item_12.dat | c/csv/item_12.dat | 53 | 53 | 53 | 53 | 14 | +0:53 | 106 | 25 |
| c/csv/item_13.dat | c/csv/item_13.dat | 86 | 86 | 86 | 86 | 86 | +0:86 | 172 | 3 |
| c/csv/item_14.dat | c/csv/item_14.dat | 58 | 58 | 58 | 58 | 58 | +0:58 | 116 | 8 |
| c/csv/item_15.dat | c/csv/item_15.dat | 116 | 116 | 116 | 116 | 116 | +0:116 | 232 | 51 |
| c/csv/item_16.dat | c/csv/item_16.dat | 95 | 95 | 95 | 95 | 95 | +0:95 | 190 | 91 |
| c/csv/item_17.dat | c/csv/item_17.dat | 98 | 98 | 98 | 98 | 98 | +0:98 | 196 | 83 |
| c/csv/item_18.dat | c/csv/item_18.dat | 49 | 43 | 43 | 43 | 43 | +0:43 | 98 | 19 |
| c/csv/menu_text.dat | c/csv/menu_text.dat | 210 | 205 | 195 | 189 | 195 | +0:189 -1:5 -6:1 | 193 | 21 |
| c/csv/mission_list.dat | c/csv/mission_list.dat | 105 | 105 | 105 | 105 | 100 | +0:105 | 104 | 1 |
| c/csv/mission_text.dat | c/csv/mission_text.dat | 105 | 105 | 104 | 104 | 101 | +0:104 | 105 | 2 |
| c/csv/name.dat | c/csv/name.dat | 63 | 63 | 63 | 63 | 63 | +0:63 | 62 | 0 |
| c/csv/quest_0.dat | c/csv/quest_0.dat | 151 | 152 | 151 | 151 | 103 | +0:151 | 400 | 11 |
| c/csv/quest_1.dat | c/csv/quest_1.dat | 151 | 152 | 151 | 151 | 95 | +0:151 | 400 | 11 |
| c/csv/quest_2.dat | c/csv/quest_2.dat | 151 | 152 | 151 | 151 | 95 | +0:151 | 400 | 11 |
| c/csv/set_option.dat | c/csv/set_option.dat | 14 | 14 | 14 | 14 | 14 | +0:14 | 14 | 0 |
| c/csv/skill_00.dat | c/csv/skill_00.dat | 43 | 43 | 43 | 43 | 39 | +0:43 | 86 | 4 |
| c/csv/skill_01.dat | c/csv/skill_01.dat | 43 | 43 | 43 | 43 | 42 | +0:43 | 86 | 3 |
| c/csv/skill_02.dat | c/csv/skill_02.dat | 43 | 43 | 43 | 43 | 41 | +0:43 | 86 | 1 |
| c/csv/skill_03.dat | c/csv/skill_03.dat | 43 | 43 | 43 | 43 | 43 | +0:43 | 86 | 2 |
| c/csv/skill_05.dat | c/csv/skill_05.dat | 16 | 16 | 16 | 16 | 16 | +0:16 | 32 | 0 |
| c/csv/smith_0.dat | c/csv/smith_0.dat | 96 | 96 | 96 | 96 | 69 | +0:96 | 165 | 27 |
| c/csv/smith_1.dat | c/csv/smith_1.dat | 96 | 96 | 96 | 96 | 81 | +0:96 | 177 | 15 |
| c/csv/smith_2.dat | c/csv/smith_2.dat | 96 | 96 | 96 | 96 | 81 | +0:96 | 177 | 15 |
| c/csv/tips.dat | c/csv/tips.dat | 26 | 26 | 26 | 26 | 26 | +0:26 | 26 | 1 |
| vfs:0x92eafcd8.dat | c/csv2/help_skt.dat | 20 | 21 | 20 | 19 | 20 | +0:19 +1:1 | 39 | 16 |
| vfs:0xc28f807a.dat | c/csv2/help_lgt.dat | 20 | 20 | 20 | 20 | 20 | +0:20 | 39 | 16 |
| vfs:0xe6bbcc45.dat | c/csv2/help_skt.dat | 21 | 21 | 21 | 21 | 21 | +0:21 | 41 | 17 |
| c/csv2/help_ktf.dat | c/csv2/help_ktf.dat | 20 | 20 | 20 | 20 | 20 | +0:20 | 39 | 15 |
| c/csv2/help_lgt.dat | c/csv2/help_lgt.dat | 20 | 20 | 20 | 20 | 20 | +0:20 | 39 | 15 |
| c/csv2/help_skt.dat | c/csv2/help_skt.dat | 21 | 21 | 21 | 21 | 21 | +0:21 | 41 | 16 |

## Tên riêng học được (Hàn -> token Anh), 77 mục

9번가의 성녀=street, 감시자=watcher, 강림자의 교단=order, 강림자의 방주=ark/descendant, 강화=enhance, 결합=fuse/orb, 고급=high/refine, 고위사도=apostles/high/radiance, 공격력=atk, 공화국=republic, 광산 협회=mining, 광산조합=mining, 교리의 관=dogma/hall, 그라함=graham, 기원의 관=hall, 네베드=neved, 네오 그라함 호=graham/neo, 누아다=nuada, 로건=logan, 론베르크=ronberg, 루레인=lurain, 루퍼스=rufus, 마르크=marc, 모이투라=moytura, 반군=tyr, 방어력=def, 방주=ark, 베쓰무라=bethmura, 베인 가=bane/house, 브리안=brian, 비명길=frozen/screaming/sea, 살리실리아=salicilia, 설녀=soulstone/yuki, 성지=sanctuary, 성지전쟁=sanctuary, 센트럴=central, 센트럴 타워=central/tower, 소켓=items, 솔티안=soltian, 수호자=guardian, 슈르츠=schultz, 슈우=schu, 신비조사회=society, 실버힐=hill/silver, 아미노페나=aminofena, 아일린=eileen, 악몽의 주인=lord/nightmares, 알테나=altena, 알프레드=alfred, 액티브=active/skill, 얼어붙은 바다=frozen/sea, 에블린=evelyn, 엘렌=ellen, 영혼가루=soul, 영혼석=soulstone, 오르비스=archipelago/orbis, 오브=orb, 웨이드 가=house/wade, 위그드라실=yggdrasil, 이자벨=isabel, 이터니움=eternium, 일반=normal/refine, 제련=refine, 제이크=jake, 카스틀러=castler, 케프네스=kefnes, 크래드=krad, 크리스=chris, 테오=theo/theodore, 테오도어=theodore, 티르=tyr, 티아나=tiana, 판매=items/sell, 패시브=passive/skill, 황혼섬=isle/twilight, 황혼의 다리=bridge/twilight, 흑의병=black/guard

## Phân bố điểm (cặp được ghép)

| khoảng | số cặp |
|---|---:|
| 0.0-0.1 | 0 |
| 0.1-0.2 | 0 |
| 0.2-0.3 | 1 |
| 0.3-0.4 | 1 |
| 0.4-0.5 | 4 |
| 0.5-0.6 | 14 |
| 0.6-0.7 | 108 |
| 0.7-0.8 | 566 |
| 0.8-0.9 | 1577 |
| 0.9-1.0 | 11525 |

## 50 cặp điểm thấp nhất (có câu Hàn đề xuất) để quản đốc soát

| # | file | vị trí | điểm | kết quả | Anh | Hàn |
|---:|---|---|---:|---|---|---|
| 1 | c/map/00108.scn | idx 51 | 0.26 | NEW | If more people show up, I might not be able to handle it all alone... | \|(돌아갈 곳이라...){ |
| 2 | c/map/00108.scn | idx 50 | 0.34 | NEW | \|(A place to return to...){ | 물론 네가 어디 정착해서 돌아갈 곳이 있으면 그게 제일 좋겠지만. |
| 3 | c/map/00136.scn | idx 39 | 0.46 | NEW | There are plenty of monsters now, so mercenaries make a living, but wh | 5년전의 성지전쟁은 인류 최악의 실수였어. 이렇게 많은 피해자들을 낳다니.. |
| 4 | c/csv/quest_0.dat | rec 136@10 | 0.50 | NEW | Graham asked for;materials to repair his;power suit. | 여분 |
| 5 | c/csv/quest_1.dat | rec 136@10 | 0.50 | NEW | Graham asked for;materials to repair his;power suit. | 여분 |
| 6 | c/csv/quest_2.dat | rec 136@10 | 0.50 | NEW | Graham asked for;materials to repair his;power suit. | 여분 |
| 7 | c/map/00108.scn | idx 47 | 0.50 | NEW | I do not know what kind of work you are involved in, but... | 그렇군. |
| 8 | c/map/00038.scn | idx 64 | 0.56 | NEW | Failures happen too, locking the item from more refining. A Critical F | 물론 실패해서 더는 제련할 수 없는 경우도 있고. 재수가 없어서 대실패를 하면 장비가 망가질 때도 있지만 |
| 9 | c/map/00060.scn | idx 125 | 0.56 | NEW | Basically, a ten-year installment plan! Some whine it is just tenant f | 간단히 말해서 10년 할부!랄까. 소작농이랑 뭐가 다르냐는 녀석도 있지만, 어쨌든 버티면 자기 땅이 되는 거잖아? |
| 10 | c/map/00094.scn | idx 22 | 0.56 | NEW | Unless you keep your gear up to par, you will not survive against the  | 우리 손으로 직접 벼린 무기들이라면, 센트럴의 공장에서 찍어내는 무기들 따위 무섭지 않다고! |
| 11 | c/map/00144.scn | idx 54 | 0.56 | NEW | Looking at this used to bring me peace of mind... | 정말 기적이라고 밖에 할 수 없는 빛이에요. 네베드들의 기술인 걸까요? |
| 12 | c/map/00174.scn | idx 38 | 0.56 | NEW | ...If four remain, does that mean their power... is about a quarter of | .. 4명이 남았다면, 그 정도의 힘이.. 완성체의 1/4 수준의 힘이라는 건가요.. |
| 13 | c/map/00136.scn | idx 36 | 0.57 | NEW | I will edit this a bit, get it properly published, and then place a co | 아직도 가슴에 }노예의 낙인{이 찍힌 채로 살아가는 사람들이 있지.. |
| 14 | c/csv/quest_0.dat | rec 126@13 | 0.58 | NEW | An ancient relic might;help investigate the;ruins below Hall of;Origin | 여분 |
| 15 | c/csv/quest_1.dat | rec 126@13 | 0.58 | NEW | An ancient relic might;help investigate the;ruins below Hall of;Origin | 여분 |
| 16 | c/csv/quest_2.dat | rec 126@13 | 0.58 | NEW | An ancient relic might;help investigate the;ruins below Hall of;Origin | 여분 |
| 17 | c/csv/quest_0.dat | rec 135@15 | 0.58 | NEW | Destroy the reviving;ancient god vessel in;Sanctuary again. It;will on | 여분 |
| 18 | c/csv/quest_1.dat | rec 135@15 | 0.58 | NEW | Destroy the reviving;ancient god vessel in;Sanctuary again. It;will on | 여분 |
| 19 | c/csv/quest_2.dat | rec 135@15 | 0.58 | NEW | Destroy the reviving;ancient god vessel in;Sanctuary again. It;will on | 여분 |
| 20 | c/csv/ingame_text.dat | rec 328@0 | 0.60 | NEW | Failed! | 에서;실패 하였습니다. |
| 21 | c/map/00136.scn | idx 34 | 0.61 | NEW | Regardless, for that reason... | 온 섬이 불타는 데도, 웨이드 가에서 이 도서관만큼은 지켜주었어. 감사한 일이지. |
| 22 | c/csv/item_16.dat | rec 0@25 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.1;Attacks enemies up to;3 times in me | 워리어 스킬북;제한레벨 LV1 |
| 23 | c/csv/item_16.dat | rec 1@25 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.4;Attacks enemies up to;3 times in me | 워리어 스킬북;제한레벨 LV4 |
| 24 | c/csv/item_16.dat | rec 2@23 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.22;Attack enemies up to;3 times with  | 워리어 스킬북;제한레벨 LV22 |
| 25 | c/csv/item_16.dat | rec 7@15 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.14;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV14 |
| 26 | c/csv/item_16.dat | rec 8@15 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.26;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV26 |
| 27 | c/csv/item_16.dat | rec 9@15 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.38;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV38 |
| 28 | c/csv/item_16.dat | rec 10@15 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.46;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV46 |
| 29 | c/csv/item_16.dat | rec 11@15 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.58;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV58 |
| 30 | c/csv/item_16.dat | rec 12@15 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.68;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV68 |
| 31 | c/csv/item_16.dat | rec 13@15 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.80;Leap and strike;enemies ahead to d | 워리어 스킬북;제한레벨 LV80 |
| 32 | c/csv/item_16.dat | rec 18@23 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.4;Slash upward to deal;melee damage.  | 워리어 스킬북;제한레벨 LV4 |
| 33 | c/csv/item_16.dat | rec 19@23 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.16;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV16 |
| 34 | c/csv/item_16.dat | rec 20@23 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.34;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV34 |
| 35 | c/csv/item_16.dat | rec 21@23 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.50;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV50 |
| 36 | c/csv/item_16.dat | rec 22@23 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.64;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV64 |
| 37 | c/csv/item_16.dat | rec 23@23 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.82;Slash upward to deal;melee damage. | 워리어 스킬북;제한레벨 LV82 |
| 38 | c/csv/item_16.dat | rec 24@21 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.20;Spin and strike all;nearby enemies | 워리어 스킬북;제한레벨 LV20 |
| 39 | c/csv/item_16.dat | rec 25@21 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.36;Spin and strike all;nearby enemies | 워리어 스킬북;제한레벨 LV36 |
| 40 | c/csv/item_16.dat | rec 26@21 | 0.62 | NEW | Warrior Skillbook;Req. Level: Lv.52;Spin and strike all;nearby enemies | 워리어 스킬북;제한레벨 LV52 |
| 41 | c/csv/item_16.dat | rec 48@17 | 0.62 | NEW | Rogue Skill Book Req;Lv1 Strikes up to 4;times with Dagger/1H;Sword in | 로그 스킬북;제한레벨 LV1 |
| 42 | c/csv/item_16.dat | rec 49@17 | 0.62 | NEW | Rogue Skill Book Req;Lv4 Strikes up to 4;times with Dagger/1H;Sword in | 로그 스킬북;제한레벨 LV4 |
| 43 | c/csv/item_16.dat | rec 56@16 | 0.62 | NEW | Rogue Skillbook Req.;Level: LV8 Melee;strike against;frontal foes.;Con | 로그 스킬북;제한레벨 LV8 |
| 44 | c/csv/item_16.dat | rec 57@16 | 0.62 | NEW | Rogue Skillbook Req.;Level: LV28 Melee;strike against;frontal foes.;Co | 로그 스킬북;제한레벨 LV28 |
| 45 | c/csv/item_16.dat | rec 58@16 | 0.62 | NEW | Rogue Skillbook Req.;Level: LV42 Melee;strike against;frontal foes.;Co | 로그 스킬북;제한레벨 LV42 |
| 46 | c/csv/item_16.dat | rec 59@16 | 0.62 | NEW | Rogue Skillbook Req.;Level: LV62 Melee;strike against;frontal foes.;Co | 로그 스킬북;제한레벨 LV62 |
| 47 | c/csv/item_16.dat | rec 60@16 | 0.62 | NEW | Rogue Skillbook Req.;Level: LV78 Melee;strike against;frontal foes.;Co | 로그 스킬북;제한레벨 LV78 |
| 48 | c/csv/item_16.dat | rec 61@16 | 0.62 | NEW | Rogue Skillbook Req.;Level: LV10 Perform;melee strike while;dodging si | 로그 스킬북;제한레벨 LV10 |
| 49 | c/csv/item_16.dat | rec 62@16 | 0.62 | NEW | Rogue Skillbook Req.;Level: LV36 Perform;melee strike while;dodging si | 로그 스킬북;제한레벨 LV36 |
| 50 | c/csv/item_16.dat | rec 63@16 | 0.62 | NEW | Rogue Skillbook Req.;Level: LV56 Perform;melee strike while;dodging si | 로그 스킬북;제한레벨 LV56 |

## Mẫu 40 cặp ngẫu nhiên (seed 20240601) trong dải 0.70-0.80 (đã ghép) để quản đốc soát; tổng 485 cặp trong dải

| # | file | vị trí | điểm | Anh | Hàn |
|---:|---|---|---:|---|---|
| 1 | c/csv/ingame_text.dat | rec 17@0 | 0.78 | Moitura Plain | 모이투라평야 |
| 2 | c/map/00056.scn | idx 12 | 0.71 | Then he vanished out of nowhere. I have no idea where he went. | 그러다가 갑자기 사라졌는데 어디로 갔는지는 모르겠는데? |
| 3 | c/map/00052.scn | idx 2 | 0.71 | Hey, if you told me to jump, you should have had a plan--! | 야, 뛰어내리라고 했으면 뭔가 대책을.. |
| 4 | c/map/00060.scn | idx 1 | 0.78 | Black Guard...?! | 흑의병..!? |
| 5 | c/map/00151.scn | idx 96 | 0.71 | Not just deep, this is... a bottomless abyss... How could you...? | 깊은게 아니라 여긴.. 끝이 없는 심연인데.. 어떻게.. |
| 6 | c/csv/tips.dat | rec 3@0 | 0.78 | You must save at least once to use Cash Shop. | 캐쉬 상점 이용을 위해서는 최소 1회 저장을 해야 합니다. |
| 7 | c/map/00080.scn | idx 5 | 0.71 | Talking with Kaze. Unlike you, he is quite the refined gentleman. | 마침 카즈와 대화중이야. 너와는 완전 딴판으로 귀공자 스타일이던데? |
| 8 | c/map/00197.scn | idx 1 | 0.78 | I am quite interested in the Irem revolution from four years ago, too. | 4년 전에 벌어진 이렘의 혁명에도 사실 꽤 흥미가 있는데... |
| 9 | c/map/00171.scn | idx 26 | 0.78 | We need something like the }Soul Stones{ from the }Frozen Sea{, and a  | }얼어붙은 바다{에서 구했던 }영혼석{과 비슷한 것이 필요해, 그것도 많이.. |
| 10 | c/csv/mission_text.dat | rec 64@0 | 0.78 | Achieved upon;acquiring 1,000,000;Gold. | 골드 획득 시 달성할 수 있습니다. |
| 11 | c/map/00052.scn | idx 31 | 0.71 | Eh...? | 에.. |
| 12 | c/map/00026.scn | idx 4 | 0.78 | Tiana... What about Tiana?! | 티아나, 티아나는!? |
| 13 | c/csv/item_18.dat | rec 2@2 | 0.73 | Listing Ext. | 판매기간연장 |
| 14 | c/map/00053.scn | idx 44 | 0.71 | H-Hey, hear me out! Do not make me sound like some villain! | 이.. 이봐, 내 말을 끝까지 들어. 날 무슨 악당처럼 몰지 말라고. |
| 15 | c/map/00070.scn | idx 63 | 0.78 | Yeah, Schultzy. We need to know what she is to find a countermeasure. | 그래, 슈우. 어떤 존재인지를 알아야.. 대책을 세울 수 있을거야. |
| 16 | c/map/00042.scn | idx 27 | 0.71 | What state was I in...? How did I get here...? | 나는.. 어떤 상태였지? 어떻게 이곳에.. |
| 17 | c/csv/common_text.dat | rec 129@0 | 0.78 | Select a sphere. | 오브를 선택하십시오. |
| 18 | c/map/00036.scn | idx 4 | 0.78 | Young mercenary... Has it been three months since you moved in? | 용병 젊은이.. 벌써 우리 건물에 들어온지 3달짼가? |
| 19 | c/map/00050.scn | idx 71 | 0.78 | Then I will match your taste, Schultz! | 그럼, 내가 맞춰줄게. 슈르츠님 취향. |
| 20 | c/map/00181.scn | idx 70 | 0.71 | That... bad...? | 그 정도... 인가.. |
| 21 | c/map/00008.scn | idx 5 | 0.71 | Phew... did it shut down...? | 후.. 작동 정지 했나.. |
| 22 | c/map/00160.scn | idx 2 | 0.71 | I mean, what are they approving when they do not even know what the or | 아니 대체, 광석이 어디 쓰이는지도 제대로 모르는 놈들이 뭘 승인하겠다는 건지.. |
| 23 | c/map/00172.scn | idx 12 | 0.78 | This is it. The reason I thought there were... seven Successors. | 이거다. 내가 계승자를.. 7명이라고 생각했던 이유. |
| 24 | c/map/00077.scn | idx 31 | 0.78 | Sch... Schultz! What is happening?! | 슈.. 슈르츠! 이게 무슨 일이지? |
| 25 | c/map/00066.scn | idx 76 | 0.71 | I really like what you just said! | 지금 그말, 완전 마음에 드는데? |
| 26 | c/map/00029.scn | idx 76 | 0.71 | No... The nightmare will come back... Those memories will return! | 안돼.. 그 악몽이 돌아올거야.. 그 기억들이 돌아올거야. |
| 27 | c/csv/common_text.dat | rec 103@0 | 0.78 | ADVANCED | 고급제련석 |
| 28 | c/csv/ingame_text.dat | rec 67@0 | 0.78 | To Moitura Island | 모이투라 섬으로 |
| 29 | c/map/00089.scn | idx 69 | 0.78 | Why was the ship ridden by the first humans... sealed with a Descendan | 어째서 최초의 인간들이 타고온 방주가.. 강림자의 마법진으로 봉인되어 있었던 거지? |
| 30 | c/map/00176.scn | idx 28 | 0.78 | What are you talking about? You said Central was the safest place! | 무슨 소리야? 등잔 밑이 어둡다고 센트럴은 안전할 거라며? |
| 31 | c/map/00109.scn | idx 4 | 0.71 | Who was that again...? | 누구시더라... |
| 32 | c/map/00080.scn | idx 18 | 0.71 | Hey... We were staying here first. Who followed who? | 이봐.. 우리가 먼저 여기서 묵고 있었다고. 따라오기는.. |
| 33 | c/csv/name.dat | rec 23@0 | 0.78 | Commander | 일반지휘관 |
| 34 | c/csv/ingame_text.dat | rec 65@0 | 0.78 | Aileen | 아일린 |
| 35 | c/map/00187.scn | idx 6 | 0.71 | Over just a few goblins...? | 고작 고블린 몇 마리 가지고... |
| 36 | c/map/00066.scn | idx 112 | 0.71 | I will hide! | 난 숨을게. |
| 37 | c/map/00014.scn | idx 2 | 0.78 | The rebels launched a big uprising four years ago, but failed utterly. | 4년전에 반란군이 한번 크게 일어났었는데, 완전히 망해버렸거든. |
| 38 | c/map/00105.scn | idx 22 | 0.78 | The battle five years ago weakened the }seal{. That is why ancient bea | 5년전의 싸움으로 }봉인{은 쇠약해졌어. 그래서 고대종들이 출몰하기 시작한거고. |
| 39 | c/map/00070.scn | idx 187 | 0.71 | Not often... but he said he would stay on this island for a while, so  | 자주는 아니지만.. 한동안은 이 섬에 머물거라고 했으니 한번쯤 더 오지 않을까? |
| 40 | c/csv/common_text.dat | rec 128@0 | 0.78 | Do you want to combine? | 결합하시겠습니까? |

## NEW có câu Hàn gợi ý (ko_hint): 565, theo lý do: {'Anh nhiều đoạn ; hơn Hàn >= 2': 327, 'câu ngắn lệch độ dài Anh/Hàn': 25, 'điểm < 0.70': 128, "soát nghĩa T2d: LỆCH (Anh 'Victorious!' vs Hàn hậu tố '에서;승리 하였습니다.' (ghép sau tên địa điểm))": 1, 'Anh dài hơn Hàn > 3.5 lần': 4, 'câu Hàn là chỗ giữ chỗ': 15, 'soát nghĩa T2d: SAI (không có Hàn; đúng K07572 ở idx 36 (NEW))': 1, 'soát nghĩa T2d: SAI (không có Hàn; đúng K07573 ở idx 37 (NEW))': 1, "soát nghĩa T2d: LỆCH (EN chỉ 'I heard a rumor before...' còn Hàn dài hơn (thành phố hiện đại nhiều tin đồn phi khoa học))": 1, "soát nghĩa T2d: SAI (Anh 'drag you off to Central Tower' vs Hàn 'mọi công nghệ từ Central Tower'; Hàn này đúng với idx 14)": 1, "soát nghĩa T2d: SAI (Anh 'waterboard' vs Hàn 'Wade gom bao nhiêu thiên tài'; không có Hàn đúng)": 1, 'soát nghĩa T2d: SAI (đúng K04731 (Hàn mọi công nghệ nhân loại từ Central Tower))': 1, "soát nghĩa T2d: SAI (đúng K08196 (Hàn 'đóng cống ngầm'))": 1, "soát nghĩa T2d: SAI (đúng K08197 (Hàn 'có xác chết... giá đất'))": 1, "soát nghĩa T2d: SAI (đúng K04736 (Hàn 'đứa trẻ vui thì tốt'))": 1, "soát nghĩa T2d: SAI (đúng K04737 (Hàn 'muốn cho con xem Central Tower'))": 1, "soát nghĩa T2d: SAI (Anh 'xe tăng bốn chân' vs Hàn 'bức bối, chỉ đi vùng cố định'; không có Hàn đúng)": 1, "soát nghĩa T2d: SAI (Anh 'mech hai chân' vs Hàn 'thiên tài nghiên cứu ở đâu'; không có Hàn đúng)": 1, "soát nghĩa T2d: SAI (Anh 'Someone came by saying you sent them' vs Hàn 'Schultz... không có nhiệm vụ nhưng có việc riêng')": 1, "soát nghĩa T2d: SAI (Anh 'Must be nice for Neveds' vs Hàn 'Tôi không phải học giả.. biết kiến thức mới luôn vui')": 1, 'soát nghĩa T2d: SAI (lệch hàng cả file 00136; Hàn ở hàng này thuộc ý khác)': 22, "soát nghĩa T2d: SAI (đúng K08468 (Hàn 'sách chứa sự thật quý giá'))": 1, "soát nghĩa T2d: SAI (đúng K08469 (Hàn 'cấm sách đốt sách vẫn sống sót'))": 1, "soát nghĩa T2d: SAI (đúng K06466 (Hàn 'lính đánh thuê cần biết thú hoang/địa hình'))": 1, "soát nghĩa T2d: LỆCH (Hàn là nhãn nội bộ EVENT_게임오버, Anh là 'G A M E  O V E R')": 2, 'soát nghĩa T2d: SAI (Hàn là câu của 00068 (K0671x..) khác cảnh; không có Hàn đúng)': 9, "soát nghĩa T2d: SAI (đúng K09316 ('내 생각도 그래'))": 1, "soát nghĩa T2d: SAI (đúng K09317 (Hàn 'tới lần xuất phát sau sẽ chỉnh Neo Graham ở trạng thái tốt nhất'))": 1, "soát nghĩa T2d: SAI (Hàn 'Tyr hình như giấu gì đó' không khớp)": 1, "soát nghĩa T2d: SAI (Hàn '내 생각도 그래' (đúng cho idx 25))": 1, 'soát nghĩa T2d: SAI (Hàn về Neo Graham (đúng cho idx 26))': 1, "soát nghĩa T2d: SAI (Hàn là lời Graham 'Khởi hành à?'; Anh khác ý)": 1, 'soát nghĩa T2d: SAI (Hàn là lời thương nhân (K06949-lệch))': 1, "soát nghĩa T2d: SAI (đúng K09509 (Hàn 'chỉ lợi dụng trận chiến không tránh được'))": 1, "soát nghĩa T2d: SAI (đúng K09510 (Hàn 'không bao giờ hoàn thành, kết thúc ở đó'))": 1, "soát nghĩa T2d: SAI (đúng K09514 (Hàn 'đặt gánh nặng lên cậu, xin lỗi'))": 1, "soát nghĩa T2d: SAI (Anh 'Well, of course, it is not easy' vs Hàn 'sức mạnh của thần 1/3')": 1, "soát nghĩa T2d: SAI (Anh 'You are lying, are not you?' vs Hàn 'Schultz, anh tự tin vượt qua?')": 1, 'soát nghĩa T2d: SAI (Hàn là lời xin lỗi gánh nặng (đúng cho idx 71))': 1, "soát nghĩa T2d: SAI (Anh 'Mom only tells me to study' vs Hàn '(bị lừa hoàn toàn)')": 1, "soát nghĩa T2d: SAI (Anh 'I kept it... arrested?' vs Hàn 'Lại đến đòi nợ à?')": 1}

Xử lý 2 câu dính `\u0000` (c/map/00041.scn idx 54, c/map/00042.scn idx 24): khi chấm điểm bỏ đoạn `\u0000`; khi build (T4) giữ nguyên byte gốc của câu Android nếu không dịch, còn nếu thay bằng bản dịch thì KHÔNG thêm `\u0000` (nó là chữ rác 6 ký tự, không phải byte NUL).

## Thống kê NEW theo file (20 file nhiều nhất)

| file | NEW |
|---|---:|
| c/csv/item_16.dat | 91 |
| c/csv/item_17.dat | 83 |
| c/map/00216.scn | 53 |
| c/map/00170.scn | 52 |
| c/csv/item_15.dat | 51 |
| c/csv/common_text.dat | 46 |
| c/map/00174.scn | 45 |
| c/map/00136.scn | 43 |
| c/map/00186.scn | 36 |
| c/csv/smith_0.dat | 27 |
| c/csv/item_12.dat | 25 |
| c/csv/menu_text.dat | 21 |
| c/csv/item_18.dat | 19 |
| c/map/00019.scn | 19 |
| vfs:0xe6bbcc45.dat | 17 |
| c/map/00175.scn | 17 |
| c/csv/ingame_text.dat | 16 |
| vfs:0x92eafcd8.dat | 16 |
| vfs:0xc28f807a.dat | 16 |
| c/csv2/help_skt.dat | 16 |

## Ghi chú kỹ thuật

- `tools/scn.py:parse` chọn `hits[-1]` nên sai ở các file có nhiều điểm khớp EOF (vd Android c/map/00154.scn, và vài file LGT như 00026/00034 trả về 0 chuỗi). `extract_android.parse_scn` ưu tiên điểm bắt đầu s=15 (header chuẩn mọi file) rồi mới chọn hit nhiều chuỗi nhất; T4 phải dùng `extract_android.parse_scn/build_scn`, không dùng `scn.build`.
- 2 câu Android có chữ `\u0000` dính cuối (c/map/00041.scn idx 54, c/map/00042.scn idx 24): lỗi của bản Anh hoá, bỏ khi chấm điểm; T4 nên bỏ đoạn này khi ghi.
- Dấu nháy sở hữu trong .dat tiếng Anh ("Marc's") là chữ thường, nhưng `'` là mã điều khiển của engine; chấm điểm bỏ qua dấu nháy giữa 2 chữ cái. Cần T4/T3 kiểm tra cách engine vẽ.
- Ví dụ nhiễu còn sót trong android_catalog (chuỗi .dat 2 ký tự trùng bố cục số): `HX` (enemy_expert_*), `oAl`: ghi nhận, sẽ ra NEW và khi dịch có thể bỏ qua.
- Câu NEW của .dat thuộc bản ghi mới của Android (item_00..03/18 thêm 5-6 bản ghi, common_text +18, v.v.), của file chỉ Android, hoặc bản ghi LGT có chữ giữ chỗ `여분`.

NEW chia theo lý do: {'bản ghi LGT không có chuỗi Hàn': 59, 'điểm thấp': 559, 'bản ghi mới (không có bản ghi LGT tương ứng)': 141, 'K không có chữ Hàn ở chỉ số này': 6, 'không ghép được file LGT theo nội dung': 141, 'không ghép được': 141, 'nghi lệch +1': 3, 'nghi lệch -1': 3}
