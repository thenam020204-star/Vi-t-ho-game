package hl4.ktf;

import hl4.cpu.Cpu;
import hl4.cpu.CpuException;
import hl4.cpu.Memory;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Minimal JVM for KTF AOT binaries. Classes and objects use the KTF in-memory layout
 * (see wie's wipi_types::ktf::java):
 * <pre>
 * JavaClass       { ptr_next, unk1, ptr_descriptor, ptr_vtable, u16 vtable_count, u16 unk_flag }   20 bytes
 * ClassDescriptor { ptr_name, unk1, ptr_parent, ptr_methods, ptr_interfaces, ptr_fields,
 *                   u16 method_count, u16 fields_size, u16 access, u16 interface_count, u16, u16 } 32 bytes
 * Method          { fn_body, ptr_class, fn_native_or_exc, ptr_name, u16 exc_count, u16,
 *                   u16 vtable_index, u16 access, unk }                                          24 bytes
 * Field           { access, ptr_class, ptr_name, offset_or_value }                                16 bytes
 * Instance        { ptr_fields, ptr_class }; fields[0] = (vtable_index*4) << 5
 * Array fields    { vtable word, length, elements... }
 * </pre>
 * Platform classes (java.lang.*, org.kwis.msp.lcdui.*) are defined here with host-call method bodies.
 */
public final class KJvm {
    final KtfRuntime rt;
    final Memory m;
    final Cpu cpu;

    static final int ACC_PUBLIC = 1, ACC_STATIC = 8, ACC_NATIVE = 0x100, ACC_ABSTRACT = 0x400, ACC_INTERFACE = 0x200;

    /** class name -> class ptr */
    final Hashtable classes = new Hashtable();
    /** class ptr -> name */
    final Hashtable classNames = new Hashtable();
    final Hashtable initialized = new Hashtable();

    /** host methods, index = host id */
    final Vector hostMethods = new Vector();

    public boolean trace;

    static final class HostMethod {
        int ptr;         // guest method struct
        int cls;         // guest class
        String className, name, desc;
        int access;
        int argWords;    // including this
        boolean wideRet;
    }

    KJvm(KtfRuntime rt) {
        this.rt = rt;
        this.m = rt.mem;
        this.cpu = rt.cpu;
    }

    void log(String s) {
        rt.log(s);
    }

    // ------------------------------------------------------------------
    // guest struct accessors

    int descriptor(int cls) {
        return m.read32(cls + 8);
    }

    String className(int cls) {
        return rt.readString(m.read32(descriptor(cls)));
    }

    int parentClass(int cls) {
        return m.read32(descriptor(cls) + 8);
    }

    int fieldsSize(int cls) {
        return m.read16(descriptor(cls) + 26);
    }

    int methodsTable(int cls) {
        return m.read32(descriptor(cls) + 12);
    }

    int vtable(int cls) {
        return m.read32(cls + 12);
    }

    /** "descriptor+name" of a method/field (after the tag byte). */
    String memberName(int ptrName) {
        return rt.readString(ptrName + 1);
    }

    static String nameOf(String full) {
        return full.substring(full.indexOf('+') + 1);
    }

    static String descOf(String full) {
        return full.substring(0, full.indexOf('+'));
    }

    // ------------------------------------------------------------------
    // class lookup / definition

    public int findClass(String name) {
        Integer c = (Integer) classes.get(name);
        if (c != null) return c.intValue();
        if (hostSpec(name) != null || name.startsWith("[")) return defineHostClass(name);
        int ptr = rt.nativeClass(name);
        if (ptr != 0) {
            registerClass(ptr, name);
            if (trace) dumpClass(ptr);
            return ptr;
        }
        ptr = defineHostClass(name);
        return ptr;
    }

    void registerClass(int ptr, String name) {
        classes.put(name, new Integer(ptr));
        classNames.put(new Integer(ptr), name);
    }

    public void dumpClass(int cls) {
        int d = descriptor(cls);
        StringBuffer sb = new StringBuffer();
        sb.append("class ").append(className(cls)).append(" @").append(rt.hex(cls));
        sb.append(" next=").append(rt.hex(m.read32(cls))).append(" unk1=").append(rt.hex(m.read32(cls + 4)));
        sb.append(" vtable=").append(rt.hex(m.read32(cls + 12))).append(" vcount=").append(m.read16(cls + 16)).append(" flag=").append(m.read16(cls + 18));
        sb.append(" parent=").append(rt.hex(m.read32(d + 8))).append(" methods=").append(m.read16(d + 24)).append(" fields_size=").append(m.read16(d + 26));
        sb.append(" access=").append(rt.hex(m.read16(d + 28))).append(" ifaces=").append(m.read16(d + 30));
        log(sb.toString());
        int mt = m.read32(d + 12);
        if (mt != 0) {
            for (int i = 0; ; i++) {
                int mp = m.read32(mt + i * 4);
                if (mp == 0) break;
                log("   method " + memberName(m.read32(mp + 12)) + " body=" + rt.hex(m.read32(mp)) + " cls=" + rt.hex(m.read32(mp + 4)) + " nat=" + rt.hex(m.read32(mp + 8)) + " vidx=" + m.read16(mp + 20) + " acc=" + rt.hex(m.read16(mp + 22)));
            }
        }
        int ft = m.read32(d + 20);
        if (ft != 0 && m.read8(m.read32(d)) != '[') {
            for (int i = 0; ; i++) {
                int fp = m.read32(ft + i * 4);
                if (fp == 0) break;
                log("   field " + memberName(m.read32(fp + 8)) + " acc=" + rt.hex(m.read32(fp)) + " off=" + rt.hex(m.read32(fp + 12)));
            }
        }
        int vt = m.read32(cls + 12);
        if (vt != 0) {
            for (int i = 0; i < 64; i++) {
                int mp = m.read32(vt + i * 4);
                if (mp == 0) break;
                log("   vtable[" + i + "] " + memberName(m.read32(mp + 12)) + " of " + className(m.read32(mp + 4)));
            }
        }
    }

    // ---- host class definitions ----

    static final String OBJ = "java/lang/Object";

    /** Returns [parent, methods...] spec for known platform classes. method spec: "flags|descriptor+name" */
    static String[] hostSpec(String name) {
        if (name.equals(OBJ)) return new String[]{null,
                "1|()V+<init>", "1|()Ljava/lang/Class;+getClass", "1|()I+hashCode", "1|(Ljava/lang/Object;)Z+equals",
                "1|()Ljava/lang/String;+toString", "1|()V+notify", "1|()V+notifyAll", "1|()V+wait", "1|(J)V+wait", "1|(JI)V+wait"};
        if (name.equals("java/lang/String")) return new String[]{OBJ};
        if (name.equals("java/lang/Class")) return new String[]{OBJ, "1|()Ljava/lang/String;+getName"};
        if (name.equals("org/kwis/msp/lcdui/Jlet")) return new String[]{OBJ,
                "1|()V+<init>", "9|()Lorg/kwis/msp/lcdui/Jlet;+getActiveJlet", "9|()Lorg/kwis/msp/lcdui/Jlet;+getCurrentJlet",
                "1|()Lorg/kwis/msp/lcdui/EventQueue;+getEventQueue", "1|(Ljava/lang/String;)Ljava/lang/String;+getAppProperty",
                "1|()V+notifyDestroyed", "1|()V+notifyPaused", "1|()V+requestResumed",
                "1025|([Ljava/lang/String;)V+startApp", "1025|()V+pauseApp", "1025|()V+resumeApp", "1025|(Z)V+destroyApp"};
        if (name.equals("org/kwis/msp/lcdui/Display")) return new String[]{OBJ,
                "9|(Ljava/lang/String;)Lorg/kwis/msp/lcdui/Display;+getDisplay", "9|()Lorg/kwis/msp/lcdui/Display;+getDefaultDisplay",
                "1|(Lorg/kwis/msp/lcdui/Card;)V+pushCard", "1|(Lorg/kwis/msp/lcdui/Card;)V+removeCard",
                "1|(Lorg/kwis/msp/lcdui/JletEventListener;)V+addJletEventListener", "1|()I+getWidth", "1|()I+getHeight",
                "1|()V+callSerially"};
        if (name.equals("org/kwis/msp/lcdui/Card")) return new String[]{OBJ,
                "1|()V+<init>", "1|(I)V+<init>", "1|(IIII)V+<init>", "1|(Lorg/kwis/msp/lcdui/Display;)V+<init>",
                "1|(Lorg/kwis/msp/lcdui/Display;IIII)V+<init>", "1|(Lorg/kwis/msp/lcdui/Display;IIIIZ)V+<init>",
                "1|()V+repaint", "1|(IIII)V+repaint", "1|()V+serviceRepaints", "1|()I+getX", "1|()I+getY",
                "1|()I+getWidth", "1|()I+getHeight", "1|(II)V+move", "1|(II)V+resize", "1|()Z+isShown",
                "1|(Z)V+showNotify", "1|(II)Z+keyNotify", "1|(III)Z+pointerNotify", "1|(Lorg/kwis/msp/lcdui/Graphics;)V+paint",
                "1|()Z+isTransparent", "1|(Z)V+setTransparent"};
        if (name.equals("org/kwis/msp/lcdui/Graphics")) return new String[]{OBJ,
                "1|()I+getClipX", "1|()I+getClipY", "1|()I+getClipWidth", "1|()I+getClipHeight"};
        if (name.equals("org/kwis/msp/lcdui/JletEventListener")) return new String[]{OBJ, "1025|(III)V+notifyEvent"};
        if (name.equals("org/kwis/msp/lcdui/EventQueue")) return new String[]{OBJ};
        if (name.equals("org/kwis/msp/lcdui/PluginJlet")) return new String[]{"org/kwis/msp/lcdui/Jlet"};
        if (name.startsWith("java/lang/") && (name.endsWith("Exception") || name.endsWith("Error"))) return new String[]{OBJ, "1|()V+<init>"};
        if (name.equals("org/kwis/msf/core/ProgramExitException")) return new String[]{OBJ, "1|()V+<init>"};
        return null;
    }

    int allocName(String s, boolean tagged) {
        byte[] b = hl4.core.Runtime.ascii(s);
        int off = tagged ? 1 : 0;
        int p = rt.alloc(b.length + 1 + off);
        if (tagged) m.write8(p, 0);
        m.writeCString(p + off, b);
        return p;
    }

    int defineHostClass(String name) {
        String[] spec = hostSpec(name);
        if (spec == null) {
            if (name.startsWith("[")) return defineArrayClass(name);
            log("KJvm: unknown platform class " + name + " (defining empty)");
            spec = new String[]{OBJ};
        }
        int parent = spec[0] == null ? 0 : findClass(spec[0]);
        int cls = rt.alloc(20);
        registerClass(cls, name);
        int nMethods = spec.length - 1;
        int mt = rt.alloc((nMethods + 1 + 32) * 4); // room for lazily added methods
        m.fill(mt, 0, (nMethods + 1 + 32) * 4);
        for (int i = 0; i < nMethods; i++) {
            String s = spec[i + 1];
            int bar = s.indexOf('|');
            int acc = Integer.parseInt(s.substring(0, bar));
            m.write32(mt + i * 4, newHostMethod(cls, name, s.substring(bar + 1), acc));
        }
        int d = rt.alloc(32);
        m.fill(d, 0, 32);
        m.write32(d, allocName(name, false));
        m.write32(d + 8, parent);
        m.write32(d + 12, mt);
        int ft = rt.alloc(4);
        m.write32(ft, 0);
        m.write32(d + 20, ft);
        m.write16(d + 24, nMethods);
        // host classes keep their state on the host side: no guest fields beyond the parent's
        m.write16(d + 26, parent == 0 ? 0 : fieldsSize(parent));
        m.write16(d + 28, name.equals("org/kwis/msp/lcdui/JletEventListener") ? (ACC_PUBLIC | ACC_INTERFACE | ACC_ABSTRACT) : ACC_PUBLIC);
        m.write32(cls, cls + 4);
        m.write32(cls + 4, 0);
        m.write32(cls + 8, d);
        m.write16(cls + 18, 8);
        buildVtable(cls);
        return cls;
    }

    int defineArrayClass(String name) {
        int obj = findClass(OBJ);
        int cls = rt.alloc(20);
        registerClass(cls, name);
        int d = rt.alloc(32);
        m.fill(d, 0, 32);
        m.write32(d, allocName(name, false));
        m.write32(d + 8, obj);
        String et = name.substring(1);
        if (et.startsWith("L")) m.write32(d + 20, findClass(et.substring(1, et.length() - 1)));
        else if (et.startsWith("[")) m.write32(d + 20, findClass(et));
        m.write16(d + 28, 0x21);
        m.write32(cls, cls + 4);
        m.write32(cls + 8, d);
        m.write32(cls + 12, vtable(obj));
        m.write16(cls + 16, m.read16(obj + 16));
        m.write16(cls + 18, 8);
        return cls;
    }

    int newHostMethod(int cls, String className, String full, int access) {
        HostMethod h = new HostMethod();
        int id = hostMethods.size();
        hostMethods.addElement(h);
        h.cls = cls;
        h.className = className;
        h.name = nameOf(full);
        h.desc = descOf(full);
        h.access = access;
        h.argWords = argWords(h.desc) + ((access & ACC_STATIC) != 0 ? 0 : 1);
        char rc = h.desc.charAt(h.desc.indexOf(')') + 1);
        h.wideRet = rc == 'J' || rc == 'D';
        int p = rt.alloc(24);
        m.fill(p, 0, 24);
        h.ptr = p;
        m.write32(p, rt.stub((KtfRuntime.CAT_JAVA << 16) | (id * 2)));
        m.write32(p + 4, cls);
        m.write32(p + 8, rt.stub((KtfRuntime.CAT_JAVA << 16) | (id * 2 + 1)));
        m.write32(p + 12, allocName(full, true));
        m.write16(p + 22, access);
        return p;
    }

    static int argWords(String desc) {
        int n = 0;
        int i = 1;
        while (desc.charAt(i) != ')') {
            char c = desc.charAt(i);
            if (c == 'J' || c == 'D') {
                n += 2;
                i++;
            } else if (c == 'L') {
                n++;
                i = desc.indexOf(';', i) + 1;
            } else if (c == '[') {
                while (desc.charAt(i) == '[') i++;
                if (desc.charAt(i) == 'L') i = desc.indexOf(';', i) + 1;
                else i++;
                n++;
            } else {
                n++;
                i++;
            }
        }
        return n;
    }

    /** Build vtable for cls from its hierarchy (wie JavaVtable::build_vtable). */
    void buildVtable(int cls) {
        Vector chain = new Vector();
        for (int c = cls; c != 0; c = parentClass(c)) chain.insertElementAt(new Integer(c), 0);
        Vector names = new Vector();
        Vector ms = new Vector();
        for (int k = 0; k < chain.size(); k++) {
            int c = ((Integer) chain.elementAt(k)).intValue();
            int mt = methodsTable(c);
            if (mt == 0) continue;
            for (int i = 0; ; i++) {
                int mp = m.read32(mt + i * 4);
                if (mp == 0) break;
                if (m.read32(mp + 4) != c) continue;
                String n = memberName(m.read32(mp + 12));
                int idx = names.indexOf(n);
                if (idx >= 0) ms.setElementAt(new Integer(mp), idx);
                else {
                    names.addElement(n);
                    ms.addElement(new Integer(mp));
                    idx = names.size() - 1;
                }
                m.write16(mp + 20, idx);
            }
        }
        int vt = rt.alloc((ms.size() + 1) * 4);
        for (int i = 0; i < ms.size(); i++) m.write32(vt + i * 4, ((Integer) ms.elementAt(i)).intValue());
        m.write32(vt + ms.size() * 4, 0);
        m.write32(cls + 12, vt);
        m.write16(cls + 16, ms.size());
    }

    /** Index of cls's vtable in the global vtable table (allocating a slot). */
    int vtableIndex(int cls) {
        int base = rt.vtablesBase();
        int vt = vtable(cls);
        int i = 0;
        for (; i < 128; i++) {
            int v = m.read32(base + i * 4);
            if (v == 0) break;
            if (v == vt) return i;
        }
        if (i >= 128) throw new CpuException("vtable table full");
        m.write32(base + i * 4, vt);
        return i;
    }

    // ------------------------------------------------------------------
    // objects

    public int newObject(int cls) {
        ensureInitialized(cls);
        int fs = fieldsSize(cls);
        return instantiate(cls, fs);
    }

    int instantiate(int cls, int fieldBytes) {
        int inst = rt.alloc(8);
        int fields = rt.alloc(fieldBytes + 4);
        m.fill(fields, 0, fieldBytes + 4);
        m.write32(inst, fields);
        m.write32(inst + 4, cls);
        m.write32(fields, (vtableIndex(cls) * 4) << 5);
        return inst;
    }

    int objClass(int obj) {
        return m.read32(obj + 4);
    }

    static int elementSize(String arrayName) {
        char c = arrayName.charAt(1);
        switch (c) {
            case 'Z': case 'B': return 1;
            case 'C': case 'S': return 2;
            case 'J': case 'D': return 8;
            default: return 4;
        }
    }

    public int newArray(String arrayName, int count) {
        int cls = findClass(arrayName);
        int es = elementSize(arrayName);
        int inst = instantiate(cls, count * es + 4);
        m.write32(m.read32(inst) + 4, count);
        return inst;
    }

    /** java_array_new(element_type, count): element type is a primitive char or a class pointer. */
    public int newArrayRaw(int elementType, int count) {
        String name;
        if (elementType > 0x100) {
            String cn = (String) classNames.get(new Integer(elementType));
            if (cn == null) cn = className(elementType);
            name = cn.startsWith("[") ? "[" + cn : "[L" + cn + ";";
        } else {
            name = "[" + (char) elementType;
        }
        return newArray(name, count);
    }

    public int arrayLength(int arr) {
        return m.read32(m.read32(arr) + 4);
    }

    public int arrayData(int arr) {
        return m.read32(arr) + 8;
    }

    // strings: host side value
    final Hashtable strings = new Hashtable();

    public int newString(String s) {
        int cls = findClass("java/lang/String");
        int o = instantiate(cls, 0);
        strings.put(new Integer(o), s);
        return o;
    }

    public String stringValue(int o) {
        if (o == 0) return null;
        return (String) strings.get(new Integer(o));
    }

    // ------------------------------------------------------------------
    // methods

    /** Find method in class hierarchy by name/descriptor. */
    public int findMethod(int cls, String name, String desc) {
        for (int c = cls; c != 0; c = parentClass(c)) {
            int mt = methodsTable(c);
            if (mt == 0) continue;
            for (int i = 0; ; i++) {
                int mp = m.read32(mt + i * 4);
                if (mp == 0) break;
                String n = memberName(m.read32(mp + 12));
                if (nameOf(n).equals(name) && descOf(n).equals(desc)) return mp;
            }
        }
        return 0;
    }

    /** Virtual lookup through the object's class hierarchy. */
    public int virtualMethod(int obj, String name, String desc) {
        return findMethod(objClass(obj), name, desc);
    }

    /** Invoke a guest (or host) method: r0 = 0, r1.. = args. */
    public int invoke(int method, int[] args) {
        int access = m.read16(method + 22);
        if ((access & ACC_NATIVE) != 0 && m.read32(method + 8) != 0) {
            int n = args.length;
            int container = rt.alloc(Math.max(4, n * 4));
            for (int i = 0; i < n; i++) m.write32(container + i * 4, args[i]);
            int r = cpu.call(m.read32(method + 8), new int[]{0, container});
            rt.heap.free(container);
            return r;
        }
        int[] a = new int[args.length + 1];
        System.arraycopy(args, 0, a, 1, args.length);
        return cpu.call(m.read32(method), a);
    }

    public int invokeVirtual(int obj, String name, String desc, int[] args) {
        int mp = virtualMethod(obj, name, desc);
        if (mp == 0) throw new CpuException("method not found " + name + desc + " on " + className(objClass(obj)));
        int[] a = new int[args.length + 1];
        a[0] = obj;
        System.arraycopy(args, 0, a, 1, args.length);
        return invoke(mp, a);
    }

    void ensureInitialized(int cls) {
        Integer k = new Integer(cls);
        if (initialized.get(k) != null) return;
        initialized.put(k, k);
        int p = parentClass(cls);
        if (p != 0) ensureInitialized(p);
        int clinit = 0;
        int mt = methodsTable(cls);
        if (mt != 0) {
            for (int i = 0; ; i++) {
                int mp = m.read32(mt + i * 4);
                if (mp == 0) break;
                if (m.read32(mp + 4) == cls && memberName(m.read32(mp + 12)).equals("()V+<clinit>")) clinit = mp;
            }
        }
        if (clinit != 0 && hostOf(clinit) == null) {
            if (trace) log("KJvm: <clinit> " + className(cls));
            invoke(clinit, new int[0]);
        }
    }

    HostMethod hostOf(int mp) {
        int body = m.read32(mp);
        for (int i = 0; i < hostMethods.size(); i++) {
            HostMethod h = (HostMethod) hostMethods.elementAt(i);
            if (h.ptr == mp) return h;
        }
        return null;
    }

    // ------------------------------------------------------------------
    // JB interface and init callbacks

    int jb;

    int jbInterface() {
        if (jb != 0) return jb;
        int[] t = new int[13];
        t[0] = 0;
        for (int i = 1; i <= 12; i++) t[i] = rt.stub((KtfRuntime.CAT_INIT << 16) | (6 + i));
        jb = rt.allocStruct(t);
        return jb;
    }

    void jb(int idx) {
        switch (idx) {
            case 7: { // java_jump_1(arg1, address)
                int a1 = cpu.arg(0), addr = cpu.arg(1);
                if (trace) log("java_jump_1(" + rt.hex(a1) + ", " + rt.hex(addr) + ")");
                rt.ret(cpu.call(addr, new int[]{a1, 0, 0}));
                return;
            }
            case 8: { // java_jump_2(arg1, arg2, address)
                int a1 = cpu.arg(0), a2 = cpu.arg(1), addr = cpu.arg(2);
                if (trace) log("java_jump_2(" + rt.hex(a1) + ", " + rt.hex(a2) + ", " + rt.hex(addr) + ")");
                rt.ret(cpu.call(addr, new int[]{a1, a2, 0}));
                return;
            }
            case 9: { // java_jump_3
                int a1 = cpu.arg(0), a2 = cpu.arg(1), a3 = cpu.arg(2), addr = cpu.arg(3);
                if (trace) log("java_jump_3(" + rt.hex(a1) + ", " + rt.hex(a2) + ", " + rt.hex(a3) + ", " + rt.hex(addr) + ")");
                rt.ret(cpu.call(addr, new int[]{a1, a2, a3}));
                return;
            }
            case 10: getJavaMethod(); return;
            case 11: getField(); return;
            case 12:
            case 13:
                log("stub jb_unk" + (idx - 8) + "(" + rt.hex(cpu.arg(0)) + ", " + rt.hex(cpu.arg(1)) + ")");
                rt.ret(0);
                return;
            case 14: // monitor enter
            case 15: // monitor exit
                rt.ret(0);
                return;
            case 16: { // register_class(ptr_class): ensure registered + initialized
                int cls = cpu.arg(0);
                Integer k = new Integer(cls);
                if (classNames.get(k) == null) {
                    String n = className(cls);
                    registerClass(cls, n);
                    if (trace) {
                        log("register_class " + n);
                        dumpClass(cls);
                    }
                }
                ensureInitialized(cls);
                return;
            }
            case 17: { // register_java_string(offset, length)
                int p = cpu.arg(0), len = cpu.arg(1);
                if (len == -1) {
                    len = m.read16(p);
                    p += 2;
                }
                char[] ch = new char[len];
                for (int i = 0; i < len; i++) ch[i] = (char) m.read16(p + i * 2);
                rt.ret(newString(new String(ch)));
                return;
            }
            case 18: { // call_native(address, ptr_data)
                int addr = cpu.arg(0), data = cpu.arg(1);
                if (trace) log("call_native(" + rt.hex(addr) + ", " + rt.hex(data) + ")");
                int r = cpu.call(addr, new int[]{data, data});
                m.write32(data, r);
                m.write32(data + 4, 0);
                rt.ret(data);
                return;
            }
        }
    }

    void getJavaMethod() {
        int ptrClass = cpu.arg(0), ptrName = cpu.arg(1);
        String full = memberName(ptrName);
        String name = nameOf(full), desc = descOf(full);
        int mp;
        int first = m.read32(ptrClass);
        if (first != ptrClass + 4) {
            // jvm-context relative: search all vtables in the table
            int base = m.read32(ptrClass + 12);
            mp = 0;
            log("get_java_method via context " + rt.hex(ptrClass) + " " + full);
            throw new CpuException("context-relative get_java_method unsupported");
        }
        mp = findMethod(ptrClass, name, desc);
        if (mp == 0) {
            // unknown platform method: add a logging stub to the class
            String cn = className(ptrClass);
            if (hostSpec(cn) != null || classNames.get(new Integer(ptrClass)) != null) {
                log("KJvm: adding missing method " + cn + "." + name + desc);
                mp = addHostMethod(ptrClass, cn, full, ACC_PUBLIC);
            } else throw new CpuException("method not found " + cn + "." + name + desc);
        }
        if (trace) log("get_java_method(" + className(ptrClass) + ", " + name + desc + ") -> " + rt.hex(mp));
        rt.ret(mp);
    }

    int addHostMethod(int cls, String cn, String full, int acc) {
        int mt = methodsTable(cls);
        int n = 0;
        while (m.read32(mt + n * 4) != 0) n++;
        int mp = newHostMethod(cls, cn, full, acc);
        m.write32(mt + n * 4, mp);
        m.write32(mt + (n + 1) * 4, 0);
        m.write16(descriptor(cls) + 24, n + 1);
        return mp;
    }

    void getField() {
        int cls = cpu.arg(0), ptrName = cpu.arg(1);
        String full = memberName(ptrName);
        for (int c = cls; c != 0; c = parentClass(c)) {
            int d = descriptor(c);
            int ft = m.read32(d + 20);
            if (ft == 0) continue;
            for (int i = 0; ; i++) {
                int fp = m.read32(ft + i * 4);
                if (fp == 0) break;
                if (memberName(m.read32(fp + 8)).equals(full)) {
                    rt.ret(fp);
                    return;
                }
            }
        }
        throw new CpuException("field not found " + className(cls) + "." + full);
    }

    void classLoad() {
        int target = cpu.arg(0);
        String name = rt.readString(cpu.arg(1));
        int cls = findClass(name);
        if (trace) log("load_java_class(" + name + ") -> " + rt.hex(cls));
        m.write32(target, cls);
        rt.ret(cls == 0 ? 1 : 0);
    }

    void javaThrow() {
        String err = rt.readString(cpu.arg(0));
        throw new CpuException("Java exception thrown by native code: " + err);
    }

    void checkType() {
        int cls = cpu.arg(0), obj = cpu.arg(1), unk = cpu.arg(2);
        if (obj == 0) {
            rt.ret(1);
            return;
        }
        int oc = objClass(obj);
        if (className(oc).startsWith("[") || unk != 0) {
            rt.ret(1);
            return;
        }
        rt.ret(isAssignable(oc, cls) ? 1 : 0);
    }

    boolean isAssignable(int from, int to) {
        for (int c = from; c != 0; c = parentClass(c)) {
            if (c == to) return true;
            int d = descriptor(c);
            int n = m.read16(d + 30);
            int it = m.read32(d + 16);
            for (int i = 0; i < n; i++) if (isAssignable(m.read32(it + i * 4), to)) return true;
        }
        return false;
    }

    // ------------------------------------------------------------------
    // host method dispatch

    void hostMethod(int id) {
        HostMethod h = (HostMethod) hostMethods.elementAt(id >> 1);
        boolean nativeEntry = (id & 1) != 0;
        int[] args = new int[h.argWords];
        if (nativeEntry) {
            int base = cpu.arg(1);
            for (int i = 0; i < args.length; i++) args[i] = m.read32(base + i * 4);
        } else {
            for (int i = 0; i < args.length; i++) args[i] = cpu.arg(i + 1);
        }
        long r = platform.call(h, args);
        cpu.r[0] = (int) r;
        if (h.wideRet) cpu.r[1] = (int) (r >>> 32);
    }

    KtfPlatform platform;

    // ------------------------------------------------------------------
    // program start

    void startMain(String mainClass) {
        platform = new KtfPlatform(this, rt);
        platform.start(mainClass);
    }
}
