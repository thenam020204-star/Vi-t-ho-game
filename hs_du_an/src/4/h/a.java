/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.HttpConnection
 */
package h;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public final class a {
    private String a;
    private String b;
    private Hashtable c;
    private byte[] d;

    public a(String string, String string2, byte[] byArray, Hashtable hashtable) {
        this.a = string;
        this.b = string2.toUpperCase();
        this.d = byArray;
        this.c = hashtable;
    }

    public a(String string, String string2, String string3, Hashtable hashtable) {
        this.a = string;
        this.b = string2.toUpperCase();
        if (string3 != null && !string3.equals("")) {
            if ("GET".equals(this.b)) {
                this.a = this.a + "?" + string3;
            } else {
                try {
                    this.d = string3.getBytes("UTF-8");
                }
                catch (Exception exception) {
                    this.d = string3.getBytes();
                }
            }
        }
        this.c = hashtable;
    }

    public static Hashtable a(a a2, boolean bl, String string) {
        if (string == null) {
            string = "";
        }
        return h.a.a(a2, bl, string, (byte)0);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Hashtable a(a a2, boolean bl, String string, byte by) {
        Object object;
        InputStream inputStream;
        HttpConnection httpConnection;
        Object object2;
        block49: {
            int n2;
            Object object3;
            block48: {
                block47: {
                    XConnection xConnection;
                    XConnection xConnection2;
                    Object var20_37;
                    String string2;
                    int n3;
                    boolean bl2;
                    XConnection xConnection3;
                    XConnection xConnection4;
                    Object var14_31;
                    String string3;
                    int n4;
                    boolean bl3;
                    int n5;
                    if (a2 == null) {
                        return null;
                    }
                    object2 = a2;
                    object3 = ((a)object2).a;
                    if (object3 == null) return null;
                    if ("".equals(((String)object3).trim())) {
                        return null;
                    }
                    object2 = a2;
                    String string4 = ((a)object2).b;
                    object2 = a2;
                    Hashtable hashtable = ((a)object2).c;
                    object2 = a2;
                    byte[] byArray = ((a)object2).d;
                    httpConnection = null;
                    inputStream = null;
                    boolean bl4 = false;
                    Object var10_18 = null;
                    object = null;
                    object = object3;
                    object2 = null;
                    String string5 = null;
                    String string6 = null;
                    if (((String)object).toLowerCase().startsWith("http://")) {
                        object = ((String)object).substring(7);
                    }
                    if ((n5 = ((String)object).indexOf(47)) > 0) {
                        object2 = ((String)object).substring(n5);
                        string5 = ((String)object).substring(0, n5);
                    } else {
                        object2 = "";
                        string5 = object;
                    }
                    n5 = string5.indexOf(":");
                    if (n5 > -1) {
                        string6 = string5.substring(n5 + 1);
                        string5 = string5.substring(0, n5);
                    }
                    if (string6 == null) {
                        string6 = string5.equals("wap.moffy.com") ? "9080" : "80";
                    }
                    if (bl) {
                        object3 = "http://10.0.0.172:80" + (String)object2;
                    }
                    if ("POST".equals(string4)) {
                        bl3 = true;
                        n4 = 3;
                        string3 = object3;
                        var14_31 = null;
                        xConnection4 = null;
                        xConnection3 = new XConnection();
                        if (string3.startsWith("sms")) {
                            xConnection4 = xConnection3;
                        } else {
                            bl2 = bl3;
                            n3 = n4;
                            string2 = string3;
                            var20_37 = null;
                            xConnection2 = null;
                            xConnection = new XConnection();
                            xConnection2 = string2.startsWith("sms") ? xConnection : Connector.open((String)string2, (int)n3, (boolean)bl2);
                            xConnection4 = xConnection2;
                        }
                        httpConnection = (HttpConnection)xConnection4;
                    } else {
                        bl3 = true;
                        n4 = 1;
                        string3 = object3;
                        var14_31 = null;
                        xConnection4 = null;
                        xConnection3 = new XConnection();
                        if (string3.startsWith("sms")) {
                            xConnection4 = xConnection3;
                        } else {
                            bl2 = bl3;
                            n3 = n4;
                            string2 = string3;
                            var20_37 = null;
                            xConnection2 = null;
                            xConnection = new XConnection();
                            xConnection2 = string2.startsWith("sms") ? xConnection : Connector.open((String)string2, (int)n3, (boolean)bl2);
                            xConnection4 = xConnection2;
                        }
                        httpConnection = (HttpConnection)xConnection4;
                    }
                    httpConnection.setRequestMethod(string4);
                    httpConnection.setRequestProperty("Connection", "close");
                    if (hashtable != null) {
                        object2 = hashtable.keys();
                        while (object2.hasMoreElements()) {
                            object3 = (String)object2.nextElement();
                            String string7 = (String)hashtable.get(object3);
                            httpConnection.setRequestProperty((String)object3, string7);
                        }
                    }
                    if (string != null && !"".equals(string.trim())) {
                        httpConnection.setRequestProperty("Referer", string);
                    }
                    if (bl) {
                        httpConnection.setRequestProperty("X-Online-Host", string5 + ":" + string6);
                    }
                    if ("POST".equals(string4) && byArray != null) {
                        object2 = httpConnection.openOutputStream();
                        ((OutputStream)object2).write(byArray);
                        ((OutputStream)object2).close();
                    }
                    n2 = httpConnection.getResponseCode();
                    object = new Hashtable<String, Integer>();
                    ((Hashtable)object).put("respCode", new Integer(n2));
                    object2 = httpConnection.getHeaderField("Content-Type");
                    if (object2 != null) {
                        ((Hashtable)object).put("contType", object2);
                    }
                    if (n2 == 200 || n2 == 302 || n2 == 301) break block47;
                    httpConnection.close();
                    object3 = object;
                    if (httpConnection == null) return object3;
                    try {
                        httpConnection.close();
                        return object3;
                    }
                    catch (Exception exception) {}
                    return object3;
                }
                if (n2 != 302 && n2 != 301) break block48;
                object3 = httpConnection.getHeaderField("Location");
                if (object3 == null) throw new Exception("WapPay.sendHttpRequest() " + n2 + " Jump==> conn.getRequestProperty(\"Location\") ==null ");
                if ("".equals(((String)object3).trim())) {
                    throw new Exception("WapPay.sendHttpRequest() " + n2 + " Jump==> conn.getRequestProperty(\"Location\") ==null ");
                }
                object2 = a2;
                Object var10_22 = null;
                object2 = a2;
                Hashtable hashtable = h.a.a(new a((String)object3, "GET", null, ((a)object2).c), bl, ((a)object2).a);
                if (httpConnection == null) return hashtable;
                try {
                    httpConnection.close();
                    return hashtable;
                }
                catch (Exception exception) {}
                return hashtable;
            }
            inputStream = httpConnection.openInputStream();
            object3 = httpConnection.getHeaderField("content-length");
            try {
                n2 = Integer.parseInt((String)object3);
            }
            catch (Exception exception) {
                n2 = -1;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (n2 > 0) {
                int n6 = 0;
                for (int i2 = 0; i2 < n2 && (n6 = inputStream.read()) >= 0; ++i2) {
                    byteArrayOutputStream.write(n6);
                }
            } else {
                int n7 = 0;
                while ((n7 = inputStream.read()) != -1) {
                    byteArrayOutputStream.write((byte)n7);
                }
            }
            byte[] byArray = byteArrayOutputStream.toByteArray();
            ((Hashtable)object).put("data", byArray);
            byteArrayOutputStream.close();
            if (inputStream == null) break block49;
            try {
                inputStream.close();
            }
            catch (Exception exception) {}
        }
        if (httpConnection == null) return object;
        try {
            httpConnection.close();
            return object;
        }
        catch (Exception exception) {
            return object;
        }
        catch (SecurityException securityException) {
            try {
                throw securityException;
                catch (Exception exception) {
                    object2 = a2;
                    throw new Exception("WapPay.sendHttpRequest() ==> url: " + ((a)object2).a + " " + exception);
                }
            }
            catch (Throwable throwable) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (Exception exception) {}
                }
                if (httpConnection == null) throw throwable;
                try {
                    httpConnection.close();
                    throw throwable;
                }
                catch (Exception exception) {}
                throw throwable;
            }
        }
    }
}

