package com.example.daniaskar.fasilbogor;

import java.io.IOException;
import java.io.Writer;


class JSONWriter {
    private static final int maxdepth = 20;

    private boolean comma;

    protected char mode;

    private char stack[];

    private int top;

    protected Writer writer;


    public JSONWriter(Writer w) {
        this.comma = false;
        this.mode = 'i';
        this.stack = new char[maxdepth];
        this.top = 0;
        this.writer = w;
    }

    private JSONWriter append(String s) throws JSONException {
        if (s == null) {
            throw new JSONException("Null pointer");
        }
        if (this.mode == 'o' || this.mode == 'a') {
            try {
                if (this.comma && this.mode == 'a') {
                    this.writer.write(',');
                }
                this.writer.write(s);
            } catch (IOException e) {
                throw new JSONException(e);
            }
            if (this.mode == 'o') {
                this.mode = 'k';
            }
            this.comma = true;
            return this;
        }
        throw new JSONException("Ammount out of sequence.");
    }


    public JSONWriter array() throws JSONException {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            this.push('a');
            this.append("[");
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }


    private JSONWriter end(char m, char c) throws JSONException {
        if (this.mode != m) {
            throw new JSONException(m == 'o' ? "Misplaced endObject." :
                    "Misplaced endArray.");
        }
        this.pop(m);
        try {
            this.writer.write(c);
        } catch (IOException e) {
            throw new JSONException(e);
        }
        this.comma = true;
        return this;
    }


    public JSONWriter endArray() throws JSONException {
        return this.end('a', ']');
    }


    public JSONWriter endObject() throws JSONException {
        return this.end('k', '}');
    }


    public JSONWriter key(String s) throws JSONException {
        if (s == null) {
            throw new JSONException("Null key.");
        }
        if (this.mode == 'k') {
            try {
                if (this.comma) {
                    this.writer.write(',');
                }
                this.writer.write(JSONObject.quote(s));
                this.writer.write(':');
                this.comma = false;
                this.mode = 'o';
                return this;
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        throw new JSONException("Misplaced key.");
    }



    public JSONWriter object() throws JSONException {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        if (this.mode == 'o' || this.mode == 'a') {
            this.append("{");
            this.push('k');
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced object.");

    }



    private void pop(char c) throws JSONException {
        if (this.top <= 0 || this.stack[this.top - 1] != c) {
            throw new JSONException("Nesting error.");
        }
        this.top -= 1;
        this.mode = this.top == 0 ? 'd' : this.stack[this.top - 1];
    }

    private void push(char c) throws JSONException {
        if (this.top >= maxdepth) {
            throw new JSONException("Nesting too deep.");
        }
        this.stack[this.top] = c;
        this.mode = c;
        this.top += 1;
    }

    public JSONWriter value(boolean b) throws JSONException {
        return this.append(b ? "true" : "false");
    }

    public JSONWriter value(long l) throws JSONException {
        return this.append(Long.toString(l));
    }


    public JSONWriter value(Object o) throws JSONException {
        return this.append(JSONObject.valueToString(o));
    }
}
