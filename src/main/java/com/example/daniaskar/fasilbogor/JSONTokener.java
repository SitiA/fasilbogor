package com.example.daniaskar.fasilbogor;

public class JSONTokener {


    public int myIndex;



    public String mySource;


    public JSONTokener(String s) {
        this.myIndex = 0;
        this.mySource = s;
    }



    public void back() {
        if (this.myIndex > 0) {
            this.myIndex -= 1;
        }
    }




    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - ('A' - 10);
        }
        if (c >= 'a' && c <= 'f') {
            return c - ('a' - 10);
        }
        return -1;
    }



    public boolean more() {
        return this.myIndex < this.mySource.length();
    }



    public char next() {
        if (more()) {
            char c = this.mySource.charAt(this.myIndex);
            this.myIndex += 1;
            return c;
        }
        return 0;
    }



    public char next(char c) throws JSONException {
        char n = next();
        if (n != c) {
            throw syntaxError("Expected '" + c + "' and instead saw '" +
                    n + "'.");
        }
        return n;
    }



    public String next(int n) throws JSONException {
        int i = this.myIndex;
        int j = i + n;
        if (j >= this.mySource.length()) {
            throw syntaxError("Substring bounds error");
        }
        this.myIndex += n;
        return this.mySource.substring(i, j);
    }



    public char nextClean() throws JSONException {
        for (;;) {
            char c = next();
            if (c == '/') {
                switch (next()) {
                    case '/':
                        do {
                            c = next();
                        } while (c != '\n' && c != '\r' && c != 0);
                        break;
                    case '*':
                        for (;;) {
                            c = next();
                            if (c == 0) {
                                throw syntaxError("Unclosed comment.");
                            }
                            if (c == '*') {
                                if (next() == '/') {
                                    break;
                                }
                                back();
                            }
                        }
                        break;
                    default:
                        back();
                        return '/';
                }
            } else if (c == '#') {
                do {
                    c = next();
                } while (c != '\n' && c != '\r' && c != 0);
            } else if (c == 0 || c > ' ') {
                return c;
            }
        }
    }



    public String nextString(char quote) throws JSONException {
        char c;
        StringBuffer sb = new StringBuffer();
        for (;;) {
            c = next();
            switch (c) {
                case 0:
                case '\n':
                case '\r':
                    throw syntaxError("Unterminated string");
                case '\\':
                    c = next();
                    switch (c) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':
                            sb.append((char)Integer.parseInt(next(4), 16));
                            break;
                        case 'x' :
                            sb.append((char) Integer.parseInt(next(2), 16));
                            break;
                        default:
                            sb.append(c);
                    }
                    break;
                default:
                    if (c == quote) {
                        return sb.toString();
                    }
                    sb.append(c);
            }
        }
    }



    public String nextTo(char d) {
        StringBuffer sb = new StringBuffer();
        for (;;) {
            char c = next();
            if (c == d || c == 0 || c == '\n' || c == '\r') {
                if (c != 0) {
                    back();
                }
                return sb.toString().trim();
            }
            sb.append(c);
        }
    }



    public String nextTo(String delimiters) {
        char c;
        StringBuffer sb = new StringBuffer();
        for (;;) {
            c = next();
            if (delimiters.indexOf(c) >= 0 || c == 0 ||
                    c == '\n' || c == '\r') {
                if (c != 0) {
                    back();
                }
                return sb.toString().trim();
            }
            sb.append(c);
        }
    }



    public Object nextValue() throws JSONException {
        char c = nextClean();
        String s;

        switch (c) {
            case '"':
            case '\'':
                return nextString(c);
            case '{':
                back();
                return new JSONObject(this);
            case '[':
                back();
                return new JSONArray(this);
        }



        StringBuffer sb = new StringBuffer();
        char b = c;
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            c = next();
        }
        back();



        s = sb.toString().trim();
        if (s.equals("")) {
            throw syntaxError("Missing value.");
        }
        if (s.toLowerCase().equals("true")) {

            return JSONObject.TRUE;

        }
        if (s.toLowerCase().equals("false")) {

            return JSONObject.FALSE;

        }
        if (s.toLowerCase().equals("null")) {
            return JSONObject.NULL;
        }



        if ((b >= '0' && b <= '9') || b == '.' || b == '-' || b == '+') {
            if (b == '0') {
                if (s.length() > 2 &&
                        (s.charAt(1) == 'x' || s.charAt(1) == 'X')) {
                    try {
                        return new Integer(Integer.parseInt(s.substring(2),
                                16));
                    } catch (Exception e) {
                        /* Ignore the error */
                    }
                } else {
                    try {
                        return new Integer(Integer.parseInt(s, 8));
                    } catch (Exception e) {
                        /* Ignore the error */
                    }
                }
            }
            try {
                return Integer.valueOf(s);
            } catch (Exception e) {
                try {
                    return new Long(Long.parseLong(s));
                } catch (Exception f) {

                    return s;

                }
            }
        }
        return s;
    }



    public char skipTo(char to) {
        char c;
        int index = this.myIndex;
        do {
            c = next();
            if (c == 0) {
                this.myIndex = index;
                return c;
            }
        } while (c != to);
        back();
        return c;
    }



    public void skipPast(String to) {
        this.myIndex = this.mySource.indexOf(to, this.myIndex);
        if (this.myIndex < 0) {
            this.myIndex = this.mySource.length();
        } else {
            this.myIndex += to.length();
        }
    }


    public JSONException syntaxError(String message) {
        return new JSONException(message + toString());
    }



    public String toString() {
        return " at character " + this.myIndex + " of " + this.mySource;
    }
}
