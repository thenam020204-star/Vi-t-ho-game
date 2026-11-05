import sys,struct,bisect,re
bin=open('/home/claude/work/vi/build/ktf/client.bin387872','rb').read()
BASE=0x100000
prof={}
for l in open(sys.argv[1]):
    a,c=l.split(); prof[int(a,16)+BASE]=int(c)
tot=sum(prof.values())
# function starts: thumb push with lr
starts=[]
for o in range(0,len(bin)-1,2):
    h=struct.unpack_from('<H',bin,o)[0]
    if (h&0xFF00)==0xB500: starts.append(o+BASE)
# aot coverage: entries in KtfAot
src=open('/home/claude/work/hl4j/src/aot/hl4/aot/KtfAot.java').read()
m=re.search(r'ENTRIES = \{(.*?)\};',src,re.S)
nums=[int(x) for x in re.findall(r'-?\d+',m.group(1))]
ent=set(nums[0::2])
fn={}
for a,c in prof.items():
    i=bisect.bisect_right(starts,a)-1
    f=starts[i] if i>=0 else 0
    fn.setdefault(f,[0,0,set()]); fn[f][0]+=c; fn[f][2].add(a)
    if a in ent: fn[f][1]+=1
print('total',tot)
acc=0
for f,(c,e,addrs) in sorted(fn.items(),key=lambda x:-x[1][0])[:int(sys.argv[2]) if len(sys.argv)>2 else 40]:
    acc+=c
    print('%x %6.2f%% cum %5.1f%% span %x-%x aotentries %d'%(f,100*c/tot,100*acc/tot,min(addrs),max(addrs),e))
