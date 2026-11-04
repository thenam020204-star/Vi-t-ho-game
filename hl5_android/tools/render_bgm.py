#!/usr/bin/env python3
"""Render bgm_00..20.mid bằng SoundFont (fluidsynth, 44100 Hz stereo, gain 0.3) -> cắt đúng độ dài MIDI, bọc đuôi reverb về đầu (lặp liền), chuẩn hoá đỉnh -1 dBFS, Vorbis q4.
   Chạy trong thư mục có in/mid/*.mid, ra wav/ và ogg/. Đặt SF=đường dẫn .sf2. Sau đó: tools/swap_bgm.py IN.apk ogg OUT_unsigned.apk"""
import mido,sys,subprocess,numpy as np,wave
import os; SF=os.environ['SF']
R=44100
for i in range(21):
    n='bgm_%02d'%i; mid='in/mid/%s.mid'%n
    L=int(round(mido.MidiFile(mid).length*R))
    subprocess.run(['fluidsynth','-ni','-q','-g','0.3','-r',str(R),'-F','wav/%s_raw.wav'%n,SF,mid],check=True,capture_output=True)
    w=wave.open('wav/%s_raw.wav'%n); assert w.getframerate()==R and w.getnchannels()==2
    a=np.frombuffer(w.readframes(w.getnframes()),dtype='<i2').reshape(-1,2).astype(np.float64)
    tail=a[L:]; out=a[:L].copy()
    if len(out)<L: out=np.vstack([out,np.zeros((L-len(out),2))])
    k=min(len(tail),L); out[:k]+=tail[:k]          # bọc đuôi reverb về đầu -> lặp liền
    pk=np.abs(out).max(); out*=(32767*10**(-1/20))/pk
    with wave.open('wav/%s.wav'%n,'wb') as o:
        o.setnchannels(2);o.setsampwidth(2);o.setframerate(R);o.writeframes(np.round(out).astype('<i2').tobytes())
    subprocess.run(['ffmpeg','-v','error','-y','-i','wav/%s.wav'%n,'-c:a','libvorbis','-q:a','4','-map_metadata','-1','ogg/%s.ogg'%n],check=True)
    print(n,'%.2fs'%(L/R),'tail %.2fs'%(len(tail)/R),'peak_raw %.0f'%pk)
