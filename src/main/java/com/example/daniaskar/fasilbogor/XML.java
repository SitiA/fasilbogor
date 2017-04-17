package com.example.daniaskar.fasilbogor;



        import java.util.Enumeration;

public class XML {

    public static final Character AMP   = new Character('&');

    public static final Character APOS  = new Character('\'');

    public static final Character BANG  = new Character('!');

    public static final Character EQ    = new Character('=');

    public static final Character GT    = new Character('>');

    public static final Character LT    = new Character('<');

    public static final Character QUEST = new Character('?');

    public static final Character QUOT  = new Character('"');

    public static final Character SLASH = new Character('/');


    public static String escape(String string) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, len = string.length(); i < len; i++) {
            char c = string.charAt(i);
            switch (c) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    private static boolean parse(XMLTokener x, JSONObject context,
                                 String name) throws JSONException {
        char       c;
        int        i;
        String     n;
        JSONObject o = null;
        String     s;
        Object     t;


        t = x.nextToken();

        if (t == BANG) {
            c = x.next();
            if (c == '-') {
                if (x.next() == '-') {
                    x.skipPast("-->");
                    return false;
                }
                x.back();
            } else if (c == '[') {
                t = x.nextToken();
                if (t.equals("CDATA")) {
                    if (x.next() == '[') {
                        s = x.nextCDATA();
                        if (s.length() > 0) {
                            context.accumulate("content", s);
                        }
                        return false;
                    }
                }
                throw x.syntaxError("Expected 'CDATA['");
            }
            i = 1;
            do {
                t = x.nextMeta();
                if (t == null) {
                    throw x.syntaxError("Missing '>' after '<!'.");
                } else if (t == LT) {
                    i += 1;
                } else if (t == GT) {
                    i -= 1;
                }
            } while (i > 0);
            return false;
        } else if (t == QUEST) {


            x.skipPast("?>");
            return false;
        } else if (t == SLASH) {


            if (name == null || !x.nextToken().equals(name)) {
                throw x.syntaxError("Mismatched close tag "+t);
            }
            if (x.nextToken() != GT) {
                throw x.syntaxError("Misshaped close tag");
            }
            return true;

        } else if (t instanceof Character) {
            throw x.syntaxError("Misshaped tag");


        } else {
            n = (String)t;
            t = null;
            o = new JSONObject();
            for (;;) {
                if (t == null) {
                    t = x.nextToken();
                }


                if (t instanceof String) {
                    s = (String)t;
                    t = x.nextToken();
                    if (t == EQ) {
                        t = x.nextToken();
                        if (!(t instanceof String)) {
                            throw x.syntaxError("Missing value");
                        }
                        o.accumulate(s, t);
                        t = null;
                    } else {
                        o.accumulate(s, "");
                    }


                } else if (t == SLASH) {
                    if (x.nextToken() != GT) {
                        throw x.syntaxError("Misshaped tag");
                    }
                    context.accumulate(n, o);
                    return false;


                } else if (t == GT) {
                    for (;;) {
                        t = x.nextContent();
                        if (t == null) {
                            if (name != null) {
                                throw x.syntaxError("Unclosed tag " + name);
                            }
                            return false;
                        } else if (t instanceof String) {
                            s = (String)t;
                            if (s.length() > 0) {
                                o.accumulate("content", s);
                            }



                        } else if (t == LT) {
                            if (parse(x, o, n)) {
                                if (o.length() == 0) {
                                    context.accumulate(n, "");
                                } else if (o.length() == 1 &&
                                        o.opt("content") != null) {
                                    context.accumulate(n, o.opt("content"));
                                } else {
                                    context.accumulate(n, o);
                                }
                                return false;
                            }
                        }
                    }
                } else {
                    throw x.syntaxError("Misshaped tag");
                }
            }
        }
    }



    public static JSONObject toJSONObject(String string) throws JSONException {
//    	string = string.replaceAll("\n", "");
        JSONObject o = new JSONObject();
        XMLTokener x = new XMLTokener(string);
        while (x.more()) {
            x.skipPast("<");
            if(x.myIndex != x.mySource.length())
                parse(x, o, null);
        }
        return o;
    }


    public static String toString(Object o) throws JSONException {
        return toString(o, null);
    }

    public static String toString(Object o, String tagName)
            throws JSONException {
        StringBuffer b = new StringBuffer();
        int          i;
        JSONArray    ja;
        JSONObject   jo;
        String       k;
        Enumeration  keys;
        int          len;
        String       s;
        Object       v;
        if (o instanceof JSONObject) {


            if (tagName != null) {
                b.append('<');
                b.append(tagName);
                b.append('>');
            }


            jo = (JSONObject)o;
            keys = jo.keys();
            while (keys.hasMoreElements()) {
                k = keys.nextElement().toString();
                v = jo.get(k);
                if (v instanceof String) {
                    s = (String)v;
                } else {
                    s = null;
                }


                if (k.equals("content")) {
                    if (v instanceof JSONArray) {
                        ja = (JSONArray)v;
                        len = ja.length();
                        for (i = 0; i < len; i += 1) {
                            if (i > 0) {
                                b.append('\n');
                            }
                            b.append(escape(ja.get(i).toString()));
                        }
                    } else {
                        b.append(escape(v.toString()));
                    }

                } else if (v instanceof JSONArray) {
                    ja = (JSONArray)v;
                    len = ja.length();
                    for (i = 0; i < len; i += 1) {
                        b.append(toString(ja.get(i), k));
                    }
                } else if (v.equals("")) {
                    b.append('<');
                    b.append(k);
                    b.append("/>");


                } else {
                    b.append(toString(v, k));
                }
            }
            if (tagName != null) {



                b.append("</");
                b.append(tagName);
                b.append('>');
            }
            return b.toString();



        } else if (o instanceof JSONArray) {
            ja = (JSONArray)o;
            len = ja.length();
            for (i = 0; i < len; ++i) {
                b.append(toString(
                        ja.opt(i), (tagName == null) ? "array" : tagName));
            }
            return b.toString();
        } else {
            s = (o == null) ? "null" : escape(o.toString());
            return (tagName == null) ? "\"" + s + "\"" :
                    (s.length() == 0) ? "<" + tagName + "/>" :
                            "<" + tagName + ">" + s + "</" + tagName + ">";
        }
    }
}
