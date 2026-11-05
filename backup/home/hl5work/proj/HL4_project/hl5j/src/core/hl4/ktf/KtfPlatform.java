package hl4.ktf;

import hl4.core.Runtime;
import hl4.cpu.CpuException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Host implementations of the WIPI Java platform classes used by a KTF Clet
 * (org.kwis.msp.lcdui.Jlet / Display / Card / Graphics and java.lang.Object basics).
 * Object state is kept host side, keyed by guest object pointer.
 */
final class KtfPlatform {
    final KJvm jvm;
    final KtfRuntime rt;

    int jlet;          // the application's Jlet instance
    int display;       // Display object
    int eventQueue;
    int graphics;      // Graphics object passed to paint()
    final Vector cards = new Vector(); // Integer card objects, top = last
    final Vector listeners = new Vector();
    final Hashtable cardRects = new Hashtable(); // card -> int[4]

    KtfPlatform(KJvm jvm, KtfRuntime rt) {
        this.jvm = jvm;
        this.rt = rt;
    }

    void log(String s) {
        rt.log(s);
    }

    void start(String mainClass) {
        int cls = jvm.findClass(mainClass);
        if (cls == 0) throw new CpuException("main class not found " + mainClass);
        int obj = jvm.newObject(cls);
        int init = jvm.findMethod(cls, "<init>", "()V");
        jvm.invoke(init, new int[]{obj});
        if (jlet == 0) jlet = obj;
        int args = jvm.newArray("[Ljava/lang/String;", 0);
        log("KTF startApp " + jvm.className(cls));
        jvm.invokeVirtual(obj, "startApp", "([Ljava/lang/String;)V", new int[]{args});
    }

    // ---- events from the runtime ----

    void paint() {
        if (cards.isEmpty()) return;
        if (graphics == 0) graphics = jvm.newObject(jvm.findClass("org/kwis/msp/lcdui/Graphics"));
        for (int i = 0; i < cards.size(); i++) {
            int card = ((Integer) cards.elementAt(i)).intValue();
            jvm.invokeVirtual(card, "paint", "(Lorg/kwis/msp/lcdui/Graphics;)V", new int[]{graphics});
        }
    }

    void key(int type, int key) {
        for (int i = cards.size() - 1; i >= 0; i--) {
            int card = ((Integer) cards.elementAt(i)).intValue();
            int r = jvm.invokeVirtual(card, "keyNotify", "(II)Z", new int[]{type, key});
            if (r == 0) break;
        }
    }

    void notifyEvent(int type, int p1, int p2) {
        for (int i = 0; i < listeners.size(); i++) {
            int l = ((Integer) listeners.elementAt(i)).intValue();
            if (jvm.virtualMethod(l, "notifyEvent", "(III)V") != 0) jvm.invokeVirtual(l, "notifyEvent", "(III)V", new int[]{type, p1, p2});
        }
    }

    void pause() {
        if (jlet != 0) jvm.invokeVirtual(jlet, "pauseApp", "()V", new int[0]);
    }

    void resume() {
        if (jlet != 0) jvm.invokeVirtual(jlet, "resumeApp", "()V", new int[0]);
    }

    void destroy() {
        if (jlet != 0) jvm.invokeVirtual(jlet, "destroyApp", "(Z)V", new int[]{1});
    }

    int ensureDisplay() {
        if (display == 0) display = jvm.newObject(jvm.findClass("org/kwis/msp/lcdui/Display"));
        return display;
    }

    // ---- host method dispatch ----

    long call(KJvm.HostMethod h, int[] a) {
        String c = h.className, n = h.name, d = h.desc;
        if (jvm.trace) log("host " + c + "." + n + d);
        if (c.equals(KJvm.OBJ)) {
            if (n.equals("<init>")) return 0;
            if (n.equals("hashCode")) return a[0];
            if (n.equals("equals")) return a[0] == a[1] ? 1 : 0;
            if (n.equals("getClass")) return 0;
            if (n.equals("toString")) return jvm.newString(jvm.className(jvm.objClass(a[0])));
            if (n.equals("notify") || n.equals("notifyAll") || n.equals("wait")) return 0;
        } else if (c.equals("org/kwis/msp/lcdui/Jlet") || c.equals("org/kwis/msp/lcdui/PluginJlet")) {
            if (n.equals("<init>")) {
                jlet = a[0];
                ensureDisplay();
                return 0;
            }
            if (n.equals("getActiveJlet") || n.equals("getCurrentJlet")) return jlet;
            if (n.equals("getEventQueue")) {
                if (eventQueue == 0) eventQueue = jvm.newObject(jvm.findClass("org/kwis/msp/lcdui/EventQueue"));
                return eventQueue;
            }
            if (n.equals("getAppProperty")) return 0;
            if (n.equals("notifyDestroyed")) {
                rt.exited = true;
                rt.host.exit();
                return 0;
            }
            if (n.equals("notifyPaused") || n.equals("requestResumed")) return 0;
        } else if (c.equals("org/kwis/msp/lcdui/Display")) {
            if (n.equals("getDisplay") || n.equals("getDefaultDisplay")) return ensureDisplay();
            if (n.equals("pushCard")) {
                int card = a[1];
                if (card != 0 && !cards.contains(new Integer(card))) {
                    cards.addElement(new Integer(card));
                    jvm.invokeVirtual(card, "showNotify", "(Z)V", new int[]{1});
                }
                rt.requestRepaint();
                return 0;
            }
            if (n.equals("removeCard")) {
                cards.removeElement(new Integer(a[1]));
                return 0;
            }
            if (n.equals("addJletEventListener")) {
                listeners.addElement(new Integer(a[1]));
                return 0;
            }
            if (n.equals("getWidth")) return rt.screenWidth;
            if (n.equals("getHeight")) return rt.screenHeight;
            if (n.equals("callSerially")) return 0;
        } else if (c.equals("org/kwis/msp/lcdui/Card")) {
            if (n.equals("<init>")) {
                int[] r = {0, 0, rt.screenWidth, rt.screenHeight};
                if (d.equals("(IIII)V")) r = new int[]{a[1], a[2], a[3], a[4]};
                else if (d.startsWith("(Lorg/kwis/msp/lcdui/Display;IIII")) r = new int[]{a[2], a[3], a[4], a[5]};
                cardRects.put(new Integer(a[0]), r);
                return 0;
            }
            int[] r = (int[]) cardRects.get(new Integer(a[0]));
            if (r == null) r = new int[]{0, 0, rt.screenWidth, rt.screenHeight};
            if (n.equals("repaint")) {
                rt.requestRepaint();
                return 0;
            }
            if (n.equals("serviceRepaints")) {
                if (rt.repaintRequested) {
                    rt.repaintRequested = false;
                    paint();
                }
                return 0;
            }
            if (n.equals("getX")) return r[0];
            if (n.equals("getY")) return r[1];
            if (n.equals("getWidth")) return r[2];
            if (n.equals("getHeight")) return r[3];
            if (n.equals("move")) {
                r[0] = a[1];
                r[1] = a[2];
                return 0;
            }
            if (n.equals("resize")) {
                r[2] = a[1];
                r[3] = a[2];
                return 0;
            }
            if (n.equals("isShown")) return cards.contains(new Integer(a[0])) ? 1 : 0;
            if (n.equals("showNotify") || n.equals("paint") || n.equals("setTransparent")) return 0;
            if (n.equals("keyNotify") || n.equals("pointerNotify") || n.equals("isTransparent")) return 0;
        } else if (c.equals("org/kwis/msp/lcdui/Graphics")) {
            if (n.equals("getClipX") || n.equals("getClipY")) return 0;
            if (n.equals("getClipWidth")) return rt.screenWidth;
            if (n.equals("getClipHeight")) return rt.screenHeight;
        } else if (c.equals("java/lang/Class")) {
            return 0;
        } else if (n.equals("<init>")) {
            return 0;
        }
        log("KTF host method not implemented: " + c + "." + n + d + " (returning 0)");
        return 0;
    }
}
