package com.example.daniaskar.fasilbogor;


class XMLTokener extends JSONTokener {

    public static final java.util.Hashtable entity;

    static {
        entity = new java.util.Hashtable(8);
        entity.put("amp",  XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt",   XML.GT);
        entity.put("lt",   XML.LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String s) {
        super(s);
    }

    public String nextCDATA() throws JSONException {
        char         c;
        int          i;
        StringBuffer sb = new StringBuffer();
        for (;;) {
            c = next();
            if (c == 0) {
                throw syntaxError("Unclosed CDATA.");
            }
            sb.append(c);
            i = sb.length() - 3;
            if (i >= 0 && sb.charAt(i) == ']' &&
                    sb.charAt(i + 1) == ']' && sb.charAt(i + 2) == '>') {
                sb.setLength(i);
                return sb.toString();
            }
        }
    }


    public Object nextContent() throws JSONException {
        char         c;
        StringBuffer sb;
        do {
            c = next();
        } while (isWhitespace(c));
        if (c == 0) {
            return null;
        }
        if (c == '<') {
            return XML.LT;
        }
        sb = new StringBuffer();
        for (;;) {
            if (c == '<' || c == 0) {
                back();
                return sb.toString().trim();
            }
            if (c == '&') {
                sb.append(nextEntity(c));
            } else {
                sb.append(c);
            }
            c = next();
        }
    }


    public Object nextEntity(char a) throws JSONException {
        StringBuffer sb = new StringBuffer();
        for (;;) {
            char c = next();
            if (isLetterOrDigit(c) || c == '#') {
                sb.append(Character.toLowerCase(c));
            } else if (c == ';') {
                break;
            } else {
                throw syntaxError("Missing ';' in XML entity: &" + sb);
            }
        }
        String s = sb.toString();
        Object e = entity.get(s);
        return e != null ? e : a + s + ";";
    }



    public Object nextMeta() throws JSONException {
        char c;
        char q;
        do {
            c = next();
        } while (isWhitespace(c));
        switch (c) {
            case 0:
                throw syntaxError("Misshaped meta tag.");
            case '<':
                return XML.LT;
            case '>':
                return XML.GT;
            case '/':
                return XML.SLASH;
            case '=':
                return XML.EQ;
            case '!':
                return XML.BANG;
            case '?':
                return XML.QUEST;
            case '"':
            case '\'':
                q = c;
                for (;;) {
                    c = next();
                    if (c == 0) {
                        throw syntaxError("Unterminated string.");
                    }
                    if (c == q) {

                        return JSONObject.TRUE;

                    }
                }
            default:
                for (;;) {
                    c = next();
                    if (isWhitespace(c)) {

                        return JSONObject.TRUE;

                    }
                    switch (c) {
                        case 0:
                        case '<':
                        case '>':
                        case '/':
                        case '=':
                        case '!':
                        case '?':
                        case '"':
                        case '\'':
                            back();

                            return JSONObject.TRUE;

                    }
                }
        }
    }


    public Object nextToken() throws JSONException {
        char c;
        char q;
        StringBuffer sb;
        do {
            c = next();
        } while (isWhitespace(c));
        switch (c) {
            case 0:
                throw syntaxError("Misshaped element.");
            case '<':
                throw syntaxError("Misplaced '<'.");
            case '>':
                return XML.GT;
            case '/':
                return XML.SLASH;
            case '=':
                return XML.EQ;
            case '!':
                return XML.BANG;
            case '?':
                return XML.QUEST;


            case '"':
            case '\'':
                q = c;
                sb = new StringBuffer();
                for (;;) {
                    c = next();
                    if (c == 0) {
                        throw syntaxError("Unterminated string.");
                    }
                    if (c == q) {
                        return sb.toString();
                    }
                    if (c == '&') {
                        sb.append(nextEntity(c));
                    } else {
                        sb.append(c);
                    }
                }
            default:


                sb = new StringBuffer();
                for (;;) {
                    sb.append(c);
                    c = next();
                    if (isWhitespace(c)) {
                        return sb.toString();
                    }
                    switch (c) {
                        case 0:
                        case '>':
                        case '/':
                        case '=':
                        case '!':
                        case '?':
                        case '[':
                        case ']':
                            back();
                            return sb.toString();
                        case '<':
                        case '"':
                        case '\'':
                            throw syntaxError("Bad character in a name.");
                    }
                }
        }
    }

    // TODO
    private static boolean isWhitespace(char c) {
        switch (c) {
            case ' ':
            case '\r':
            case '\n':
            case '\t':
                return true;
        }
        return false;
    }

    // TODO
    private static boolean isLetterOrDigit(char c) {
        switch (c) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':

            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':

            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return true;
        }
        return false;
    }

}

