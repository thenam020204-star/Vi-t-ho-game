import sys, struct
from unicorn import *
from unicorn.arm64_const import *
from elftools.elf.elffile import ELFFile
def load(so):
    elf=ELFFile(open(so,'rb')); uc=Uc(UC_ARCH_ARM64,UC_MODE_ARM)
    top=max(s['p_vaddr']+s['p_memsz'] for s in elf.iter_segments() if s['p_type']=='PT_LOAD')
    uc.mem_map(0,(top+0xfff)&~0xfff)
    for s in elf.iter_segments():
        if s['p_type']=='PT_LOAD': uc.mem_write(s['p_vaddr'],s.data())
    uc.mem_map(0x10000000,0x400000)            # stack + heap
    uc.reg_write(UC_ARM64_REG_CPACR_EL1,0x300000)
    return uc
HEAP=[0x10100000]
def seg(so, start, stop, regs=None, stubs=None, retvals=None, maxn=200000, mem=None):
    """Emulate start..stop. every `bl` whose target is in `stubs` (dict addr->fn(uc)->ret) is replaced by call; returns (events, reached)"""
    uc=load(so); uc.reg_write(UC_ARM64_REG_SP,0x10080000)
    for r,v in (regs or {}).items(): uc.reg_write(getattr(__import__('unicorn.arm64_const',fromlist=['x']),'UC_ARM64_REG_'+r.upper()),v)
    for a,b in (mem or {}).items(): uc.mem_write(a,b)
    ev=[]; reached=[False]; cnt=[0]
    from capstone import Cs,CS_ARCH_ARM64,CS_MODE_ARM
    md=Cs(CS_ARCH_ARM64,CS_MODE_ARM)
    def hook(uc,addr,size,_):
        cnt[0]+=1
        if addr==stop: reached[0]=True; uc.emu_stop(); return
        if addr in stubs:
            lr=uc.reg_read(UC_ARM64_REG_X30)
            r=stubs[addr](uc,ev)
            uc.reg_write(UC_ARM64_REG_X0,r if r is not None else 0)
            uc.reg_write(UC_ARM64_REG_PC,lr); 
    uc.hook_add(UC_HOOK_CODE,hook)
    try: uc.emu_start(start,stop,count=maxn)
    except UcError as e: ev.append('UcError %s pc=%#x'%(e,uc.reg_read(UC_ARM64_REG_PC)))
    return ev,reached[0],uc
