import struct,sys
from keystone import Ks,KS_ARCH_ARM,KS_MODE_ARM
src=open(sys.argv[1],'rb').read(); b=bytearray(src)
phoff=struct.unpack_from('<I',b,0x1c)[0]; phnum=struct.unpack_from('<H',b,0x2c)[0]
# phdr[4] = PT_GNU_STACK -> PT_LOAD
VA=0x171000
while len(b)%0x1000: b.append(0)
off=len(b)
ks=Ks(KS_ARCH_ARM,KS_MODE_ARM)
code,_=ks.asm("ldrsb r0,[r0,#12]\nadd r1,pc,#0xf00\nldr r2,[r1]\nadd r2,r2,#1\nstr r2,[r1]\nbx lr",VA)
payload=bytes(code)+bytes(0x1000-len(code))
b+=payload
ph=struct.pack('<8I',1,off,VA,VA,len(payload),0x2000,7,0x1000)   # memsz 0x2000 > filesz: zero-fill test
struct.pack_into('<8I',b,phoff+4*32,*struct.unpack('<8I',ph))
jmp,_=ks.asm("b 0x171000",0xe2eb0)
b[0xe2eb0:0xe2eb0+4]=bytes(jmp); b[0xe2eb4:0xe2eb8]=bytes(ks.asm("nop",0)[0])
open(sys.argv[2],'wb').write(b)
print('payload at file off',hex(off),'size',len(b),'counter at VA 0x171f00? (add r1,pc,#0xf00 from pc=VA+8 ->',hex(VA+4*1+8+0xf00-4*1+0))
