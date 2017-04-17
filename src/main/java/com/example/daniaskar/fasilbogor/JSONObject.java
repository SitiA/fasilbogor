package com.example.daniaskar.fasilbogor;


import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public class JSONObject {


    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);



    private static final class Null {


        protected final Object clone() {
            return this;
        }



        public boolean equals(Object object) {
            return object == null || object == this;
        }



        public String toString() {
            return "null";
        }
    }



    private Hashtable myHashMap;



    public static final Object NULL = new Null();


    public JSONObject() {
        this.myHashMap = new Hashtable();
    }



    public JSONObject(JSONTokener x) throws JSONException {
        this();
        char c;
        String key;

        if (x.nextClean() != '{') {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        }
        for (;;) {
            c = x.nextClean();
            switch (c) {
                case 0:
                    throw x.syntaxError("A JSONObject text must end with '}'");
                case '}':
                    return;
                default:
                    x.back();
                    key = x.nextValue().toString();
            }



            c = x.nextClean();
            if (c == '=') {
                if (x.next() != '>') {
                    x.back();
                }
            } else if (c != ':') {
                throw x.syntaxError("Expected a ':' after a key");
            }
            put(key, x.nextValue());



            switch (x.nextClean()) {
                case ';':
                case ',':
                    if (x.nextClean() == '}') {
                        return;
                    }
                    x.back();
                    break;
                case '}':
                    return;
                default:
                    throw x.syntaxError("Expected a ',' or '}'");
            }
        }
    }





    public JSONObject(String string) throws JSONException {
        this(new JSONTokener(string));
    }



    public JSONObject accumulate(String key, Object value)
            throws JSONException {
        testValidity(value);
        Object o = opt(key);
        if (o == null) {
            put(key, value);
        } else if (o instanceof JSONArray) {
            ((JSONArray)o).put(value);
        } else {
            put(key, new JSONArray().put(o).put(value));
        }
        return this;
    }



    public Object get(String key) throws JSONException {
        Object o = opt(key);
        if (o == null) {
            throw new JSONException("JSONObject[" + quote(key) +
                    "] not found.");
        }
        return o;
    }



    public boolean getBoolean(String key) throws JSONException {
        Object o = get(key);

        if (o.equals(FALSE) ||

                (o instanceof String &&
                        ((String)o).toLowerCase().equals("false"))) {
            return false;

        } else if (o.equals(TRUE) ||

                (o instanceof String &&
                        ((String)o).toLowerCase().equals("true"))) {
            return true;
        }
        throw new JSONException("JSONObject[" + quote(key) +
                "] is not a Boolean.");
    }


    public int getInt(String key) throws JSONException {
        Object o = get(key);
        if (o instanceof Byte) {
            return ((Byte)o).byteValue();
        } else if (o instanceof Short) {
            return ((Short)o).shortValue();
        } else if (o instanceof Integer) {
            return ((Integer)o).intValue();
        } else if (o instanceof Long) {
            return (int) ((Long)o).longValue();

        }
        throw new JSONException("JSONObject[" + quote(key) +
                "] is not a number.");
    }



    public JSONArray getJSONArray(String key) throws JSONException {
        Object o = get(key);
        if (o instanceof JSONArray) {
            return (JSONArray)o;
        }
        throw new JSONException("JSONObject[" + quote(key) +
                "] is not a JSONArray.");
    }


    public JSONObject getJSONObject(String key) throws JSONException {
        Object o = get(key);
        if (o instanceof JSONObject) {
            return (JSONObject)o;
        }
        throw new JSONException("JSONObject[" + quote(key) +
                "] is not a JSONObject.");
    }


    public long getLong(String key) throws JSONException {
        Object o = get(key);
        if (o instanceof Byte) {
            return ((Byte)o).byteValue();
        } else if (o instanceof Short) {
            return ((Short)o).shortValue();
        } else if (o instanceof Integer) {
            return ((Integer)o).intValue();
        } else if (o instanceof Long) {
            return ((Long)o).longValue();

        }
        throw new JSONException("JSONObject[" + quote(key) +
                "] is not a number.");
    }


    public String getString(String key) throws JSONException {
        return get(key).toString();
    }


    public boolean has(String key) {
        return this.myHashMap.containsKey(key);
    }



    public boolean isNull(String key) {
        return JSONObject.NULL.equals(opt(key));
    }



    public Enumeration keys() {
        return this.myHashMap.keys();
    }



    public int length() {
        return this.myHashMap.size();
    }



    public JSONArray names() {
        JSONArray ja = new JSONArray();
        Enumeration  keys = keys();
        while (keys.hasMoreElements()) {
            ja.put(keys.nextElement());
        }
        return ja.length() == 0 ? null : ja;
    }



    static public String trimNumber(String s) {
        if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
            while (s.endsWith("0")) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.endsWith(".")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }


    static public String numberToString(Object n)
            throws JSONException {
        if (n == null) {
            throw new JSONException("Null pointer");
        }
        testValidity(n);
        return trimNumber(n.toString());
    }


    public Object opt(String key) {
        return key == null ? null : this.myHashMap.get(key);
    }



    public boolean optBoolean(String key) {
        return optBoolean(key, false);
    }



    public boolean optBoolean(String key, boolean defaultValue) {
        try {
            return getBoolean(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }



    public JSONObject put(String key, Vector value) throws JSONException {
        put(key, new JSONArray(value));
        return this;
    }





    public int optInt(String key) {
        return optInt(key, 0);
    }



    public int optInt(String key, int defaultValue) {
        try {
            return getInt(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }



    public JSONArray optJSONArray(String key) {
        Object o = opt(key);
        return o instanceof JSONArray ? (JSONArray)o : null;
    }



    public JSONObject optJSONObject(String key) {
        Object o = opt(key);
        return o instanceof JSONObject ? (JSONObject)o : null;
    }



    public long optLong(String key) {
        return optLong(key, 0);
    }


    public long optLong(String key, long defaultValue) {
        try {
            return getLong(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }



    public String optString(String key) {
        return optString(key, "");
    }



    public String optString(String key, String defaultValue) {
        Object o = opt(key);
        return o != null ? o.toString() : defaultValue;
    }



    public JSONObject put(String key, boolean value) throws JSONException {

        put(key, value ? TRUE : FALSE);

        return this;
    }



    public JSONObject put(String key, int value) throws JSONException {
        put(key, new Integer(value));
        return this;
    }



    public JSONObject put(String key, long value) throws JSONException {
        put(key, new Long(value));
        return this;
    }



    public JSONObject put(String key, Object value) throws JSONException {
        if (key == null) {
            throw new JSONException("Null key.");
        }
        if (value != null) {
            testValidity(value);
            this.myHashMap.put(key, value);
        } else {
            remove(key);
        }
        return this;
    }


    public JSONObject putOpt(String key, Object value) throws JSONException {
        if (key != null && value != null) {
            put(key, value);
        }
        return this;
    }


    public static String quote(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char         b;
        char         c = 0;
        int          i;
        int          len = string.length();
        StringBuffer sb = new StringBuffer(len + 4);
        String       t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (b == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }


    public Object remove(String key) {
        return this.myHashMap.remove(key);
    }


    static void testValidity(Object o) throws JSONException {
        if (o != null) {

        }
    }


    public JSONArray toJSONArray(JSONArray names) throws JSONException {
        if (names == null || names.length() == 0) {
            return null;
        }
        JSONArray ja = new JSONArray();
        for (int i = 0; i < names.length(); i += 1) {
            ja.put(this.opt(names.getString(i)));
        }
        return ja;
    }


    public String toString() {
        try {
            Enumeration keys = keys();
            StringBuffer sb = new StringBuffer("{");

            while (keys.hasMoreElements()) {
                if (sb.length() > 1) {
                    sb.append(',');
                }
                Object o = keys.nextElement();
                sb.append(quote(o.toString()));
                sb.append(':');
                sb.append(valueToString(this.myHashMap.get(o)));
            }
            sb.append('}');
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }


    public String toString(int indentFactor) throws JSONException {
        return toString(indentFactor, 0);
    }


    String toString(int indentFactor, int indent) throws JSONException {
        int          i;
        int          n = length();
        if (n == 0) {
            return "{}";
        }
        Enumeration keys = keys();
        StringBuffer sb = new StringBuffer("{");
        int          newindent = indent + indentFactor;
        Object       o;
        if (n == 1) {
            o = keys.nextElement();
            sb.append(quote(o.toString()));
            sb.append(": ");
            sb.append(valueToString(this.myHashMap.get(o), indentFactor,
                    indent));
        } else {
            while (keys.hasMoreElements()) {
                o = keys.nextElement();
                if (sb.length() > 1) {
                    sb.append(",\n");
                } else {
                    sb.append('\n');
                }
                for (i = 0; i < newindent; i += 1) {
                    sb.append(' ');
                }
                sb.append(quote(o.toString()));
                sb.append(": ");
                sb.append(valueToString(this.myHashMap.get(o), indentFactor,
                        newindent));
            }
            if (sb.length() > 1) {
                sb.append('\n');
                for (i = 0; i < indent; i += 1) {
                    sb.append(' ');
                }
            }
        }
        sb.append('}');
        return sb.toString();
    }


    static String valueToString(Object value) throws JSONException {
        if (value == null || value.equals(null)) {
            return "null";
        }
        if (value instanceof JSONString) {
            Object o;
            try {
                o = ((JSONString)value).toJSONString();
            } catch (Exception e) {
                throw new JSONException(e);
            }
            if (o instanceof String) {
                return (String)o;
            }
            throw new JSONException("Bad value from toJSONString: " + o);
        }
        if (
                value instanceof Byte || value instanceof Short ||
                        value instanceof Integer || value instanceof Long) {
            return numberToString(value);
        }
        if (value instanceof Boolean || value instanceof JSONObject ||
                value instanceof JSONArray) {
            return value.toString();
        }
        return quote(value.toString());
    }


    static String valueToString(Object value, int indentFactor, int indent)
            throws JSONException {
        if (value == null || value.equals(null)) {
            return "null";
        }
        try {
            if (value instanceof JSONString) {
                Object o = ((JSONString)value).toJSONString();
                if (o instanceof String) {
                    return (String)o;
                }
            }
        } catch (Exception e) {
        }
        if (
                value instanceof Byte || value instanceof Short ||
                        value instanceof Integer || value instanceof Long) {
            return numberToString(value);
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof JSONObject) {
            return ((JSONObject)value).toString(indentFactor, indent);
        }
        if (value instanceof JSONArray) {
            return ((JSONArray)value).toString(indentFactor, indent);
        }
        return quote(value.toString());
    }

   public Writer write(Writer writer) throws JSONException {
        try {
            boolean  b = false;
            Enumeration keys = keys();
            writer.write('{');

            while (keys.hasMoreElements()) {
                if (b) {
                    writer.write(',');
                }
                Object k = keys.nextElement();
                writer.write(quote(k.toString()));
                writer.write(':');
                Object v = this.myHashMap.get(k);
                if (v instanceof JSONObject) {
                    ((JSONObject)v).write(writer);
                } else if (v instanceof JSONArray) {
                    ((JSONArray)v).write(writer);
                } else {
                    writer.write(valueToString(v));
                }
                b = true;
            }
            writer.write('}');
            return writer;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }
}