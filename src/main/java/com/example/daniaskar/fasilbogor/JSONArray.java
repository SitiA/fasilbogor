package com.example.daniaskar.fasilbogor;

import java.io.IOException;
import java.io.Writer;
import java.util.Vector;

public class JSONArray {


    private Vector myArrayList;


    public JSONArray() {
        this.myArrayList = new Vector();
    }

    public JSONArray(JSONTokener x) throws JSONException {
        this();
        if (x.nextClean() != '[') {
            throw x.syntaxError("A JSONArray text must start with '['");
        }
        if (x.nextClean() == ']') {
            return;
        }
        x.back();
        for (;;) {
            if (x.nextClean() == ',') {
                x.back();
                this.myArrayList.addElement(null);
            } else {
                x.back();
                this.myArrayList.addElement(x.nextValue());
            }
            switch (x.nextClean()) {
                case ';':
                case ',':
                    if (x.nextClean() == ']') {
                        return;
                    }
                    x.back();
                    break;
                case ']':
                    return;
                default:
                    throw x.syntaxError("Expected a ',' or ']'");
            }
        }
    }


    public JSONArray(String string) throws JSONException {
        this(new JSONTokener(string));
    }


    public JSONArray(Vector collection) {
        if (collection == null) {
            this.myArrayList = new Vector();
        } else {
            int size = collection.size();
            this.myArrayList = new Vector(size);
            for (int i=0; i < size; i++) {
                this.myArrayList.addElement(collection.elementAt(i));
            }
        }
    }

    public Object get(int index) throws JSONException {
        Object o = opt(index);
        if (o == null) {
            throw new JSONException("JSONArray[" + index + "] not found.");
        }
        return o;
    }

    public boolean getBoolean(int index) throws JSONException {
        Object o = get(index);
        if (o.equals(JSONObject.FALSE) ||
                (o instanceof String &&
                        ((String)o).toLowerCase().equals("false"))) {
            return false;
        } else if (o.equals(JSONObject.TRUE) ||
                (o instanceof String &&
                        ((String)o).toLowerCase().equals("true"))) {
            return true;
        }
        throw new JSONException("JSONArray[" + index + "] is not a Boolean.");
    }

    public JSONArray getJSONArray(int index) throws JSONException {
        Object o = get(index);
        if (o instanceof JSONArray) {
            return (JSONArray)o;
        }
        throw new JSONException("JSONArray[" + index +
                "] is not a JSONArray.");
    }


    public JSONObject getJSONObject(int index) throws JSONException {
        Object o = get(index);
        if (o instanceof JSONObject) {
            return (JSONObject)o;
        }
        throw new JSONException("JSONArray[" + index +
                "] is not a JSONObject.");
    }



    public String getString(int index) throws JSONException {
        return get(index).toString();
    }


    public boolean isNull(int index) {
        return JSONObject.NULL.equals(opt(index));
    }


    public String join(String separator) throws JSONException {
        int len = length();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < len; i += 1) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(JSONObject.valueToString(this.myArrayList.elementAt(i)));
        }
        return sb.toString();
    }


    public int length() {
        return this.myArrayList.size();
    }


    public Object opt(int index) {
        return (index < 0 || index >= length()) ?
                null : this.myArrayList.elementAt(index);
    }

    public boolean optBoolean(int index)  {
        return optBoolean(index, false);
    }

    public boolean optBoolean(int index, boolean defaultValue)  {
        try {
            return getBoolean(index);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public JSONArray optJSONArray(int index) {
        Object o = opt(index);
        return o instanceof JSONArray ? (JSONArray)o : null;
    }


    public JSONObject optJSONObject(int index) {
        Object o = opt(index);
        return o instanceof JSONObject ? (JSONObject)o : null;
    }

    public String optString(int index) {
        return optString(index, "");
    }


    public String optString(int index, String defaultValue) {
        Object o = opt(index);
        return o != null ? o.toString() : defaultValue;
    }


    public JSONArray put(boolean value) {
        put(value ? JSONObject.TRUE : JSONObject.FALSE);
        return this;
    }


    public JSONArray put(Vector value) {
        put(new JSONArray(value));
        return this;
    }



    public JSONArray put(int value) {
        put(new Integer(value));
        return this;
    }



    public JSONArray put(long value) {
        put(new Long(value));
        return this;
    }




    public JSONArray put(Object value) {
        this.myArrayList.addElement(value);
        return this;
    }



    public JSONArray put(int index, boolean value) throws JSONException {

        put(index, value ? JSONObject.TRUE : JSONObject.FALSE);
        return this;
    }


    public JSONArray put(int index, Vector value) throws JSONException {
        put(index, new JSONArray(value));
        return this;
    }


    public JSONArray put(int index, int value) throws JSONException {
        put(index, new Integer(value));
        return this;
    }



    public JSONArray put(int index, long value) throws JSONException {
        put(index, new Long(value));
        return this;
    }



    public JSONArray put(int index, Object value) throws JSONException {
        JSONObject.testValidity(value);
        if (index < 0) {
            throw new JSONException("JSONArray[" + index + "] not found.");
        }
        if (index < length()) {
            this.myArrayList.setElementAt(value, index);
        } else {
            while (index != length()) {
                put(JSONObject.NULL);
            }
            put(value);
        }
        return this;
    }


    public JSONObject toJSONObject(JSONArray names) throws JSONException {
        if (names == null || names.length() == 0 || length() == 0) {
            return null;
        }
        JSONObject jo = new JSONObject();
        for (int i = 0; i < names.length(); i += 1) {
            jo.put(names.getString(i), this.opt(i));
        }
        return jo;
    }


    public String toString() {
        try {
            return '[' + join(",") + ']';
        } catch (Exception e) {
            return null;
        }
    }



    public String toString(int indentFactor) throws JSONException {
        return toString(indentFactor, 0);
    }



    String toString(int indentFactor, int indent) throws JSONException {
        int len = length();
        if (len == 0) {
            return "[]";
        }
        int i;
        StringBuffer sb = new StringBuffer("[");
        if (len == 1) {
            sb.append(JSONObject.valueToString(this.myArrayList.elementAt(0),
                    indentFactor, indent));
        } else {
            int newindent = indent + indentFactor;
            sb.append('\n');
            for (i = 0; i < len; i += 1) {
                if (i > 0) {
                    sb.append(",\n");
                }
                for (int j = 0; j < newindent; j += 1) {
                    sb.append(' ');
                }
                sb.append(JSONObject.valueToString(this.myArrayList.elementAt(i),
                        indentFactor, newindent));
            }
            sb.append('\n');
            for (i = 0; i < indent; i += 1) {
                sb.append(' ');
            }
        }
        sb.append(']');
        return sb.toString();
    }



    public Writer write(Writer writer) throws JSONException {
        try {
            boolean b = false;
            int     len = length();

            writer.write('[');

            for (int i = 0; i < len; i += 1) {
                if (b) {
                    writer.write(',');
                }
                Object v = this.myArrayList.elementAt(i);
                if (v instanceof JSONObject) {
                    ((JSONObject)v).write(writer);
                } else if (v instanceof JSONArray) {
                    ((JSONArray)v).write(writer);
                } else {
                    writer.write(JSONObject.valueToString(v));
                }
                b = true;
            }
            writer.write(']');
            return writer;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }
}