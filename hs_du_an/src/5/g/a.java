/*
 * Decompiled with CFR 0.152.
 */
package g;

import g.b;
import g.c;

public final class a {
    private int a = 0;
    private String b;

    public a(String string) {
        this.b = string;
    }

    public final void a() {
        if (this.a > 0) {
            --this.a;
        }
    }

    public final char b() {
        if (this.a < this.b.length()) {
            char c2 = this.b.charAt(this.a);
            ++this.a;
            return c2;
        }
        return '\u0000';
    }

    private String a(int n2) {
        int n3 = this.a;
        int n4 = n3 + n2;
        if (n4 >= this.b.length()) {
            throw this.a("Substring bounds error");
        }
        this.a += n2;
        return this.b.substring(n3, n4);
    }

    public final char c() {
        char c2;
        block4: while (true) {
            if ((c2 = this.b()) == '/') {
                switch (this.b()) {
                    case '/': {
                        while ((c2 = this.b()) != '\n' && c2 != '\r' && c2 != '\u0000') {
                        }
                        continue block4;
                    }
                    case '*': {
                        while (true) {
                            if ((c2 = this.b()) == '\u0000') {
                                throw this.a("Unclosed comment.");
                            }
                            if (c2 != '*') continue;
                            if (this.b() == '/') continue block4;
                            this.a();
                        }
                    }
                }
                this.a();
                return '/';
            }
            if (c2 == '#') {
                while ((c2 = this.b()) != '\n' && c2 != '\r' && c2 != '\u0000') {
                }
                continue;
            }
            if (c2 == '\u0000' || c2 > ' ') break;
        }
        return c2;
    }

    public final Object d() {
        char c2 = this.c();
        switch (c2) {
            case '\"': 
            case '\'': {
                StringBuffer stringBuffer = new StringBuffer();
                block18: while (true) {
                    char c3 = this.b();
                    switch (c3) {
                        case '\u0000': 
                        case '\n': 
                        case '\r': {
                            throw this.a("Unterminated string");
                        }
                        case '\\': {
                            c3 = this.b();
                            switch (c3) {
                                case 'b': {
                                    stringBuffer.append('\b');
                                    continue block18;
                                }
                                case 't': {
                                    stringBuffer.append('\t');
                                    continue block18;
                                }
                                case 'n': {
                                    stringBuffer.append('\n');
                                    continue block18;
                                }
                                case 'f': {
                                    stringBuffer.append('\f');
                                    continue block18;
                                }
                                case 'r': {
                                    stringBuffer.append('\r');
                                    continue block18;
                                }
                                case 'u': {
                                    stringBuffer.append((char)Integer.parseInt(this.a(4), 16));
                                    continue block18;
                                }
                                case 'x': {
                                    stringBuffer.append((char)Integer.parseInt(this.a(2), 16));
                                    continue block18;
                                }
                            }
                            stringBuffer.append(c3);
                            continue block18;
                        }
                    }
                    if (c3 == c2) {
                        return stringBuffer.toString();
                    }
                    stringBuffer.append(c3);
                }
            }
            case '{': {
                this.a();
                return new c(this);
            }
            case '[': {
                this.a();
                return new b(this);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (c2 >= ' ' && ",:]}/\\\"[{;=#".indexOf(c2) < 0) {
            stringBuffer.append(c2);
            c2 = this.b();
        }
        this.a();
        String string = stringBuffer.toString().trim();
        if (string.equals("")) {
            throw this.a("Missing value.");
        }
        if (string.toLowerCase().equals("null")) {
            return c.a;
        }
        return string;
    }

    public final Exception a(String string) {
        return new Exception(string + this.toString());
    }
}

