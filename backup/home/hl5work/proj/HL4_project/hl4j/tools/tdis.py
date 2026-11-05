import sys,capstone
bin=open('/home/claude/work/vi/build/ktf/client.bin387872','rb').read()
BASE=0x100000
prof={}
try:
    for l in open(sys.argv[3]):
        a,c=l.split(); prof[int(a,16)+BASE]=int(c)
except Exception: pass
md=capstone.Cs(capstone.CS_ARCH_ARM,capstone.CS_MODE_THUMB)
a=int(sys.argv[1],16); e=int(sys.argv[2],16)
for i in md.disasm(bin[a-BASE:e-BASE],a):
    print('%x %10s  %s %s'%(i.address,prof.get(i.address,''),i.mnemonic,i.op_str))
