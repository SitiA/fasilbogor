package com.example.daniaskar.fasilbogor;

        import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class HttpConnector {
    HttpURLConnection httpConn;
    public static String ERROR_NO_GPRS = "GPRS not available ";
    public static String ERROR_CONN_TIMEOUT = "Timeout ";
    public static String ERROR_RESPONSE = "Please try again later ";
    public static String ERROR_PERMISSION = "Connection Access Not Permitted ";
    public static String ERROR_GPRS_SETTING = "Please check your GPRS setting ";
    public static String ERROR_CONNECTION_INTERRUPTED = "Connection Interrupted ";
    public static String UNKNOWN_ERROR = "An error occurred while communicating with the server ";
    public boolean sending = false;


    private HttpURLConnection openConnection(String urlServer, Activity cnt,
                                             byte[] param) throws IOException {
        URL url;
        url = new URL(urlServer);
        URLConnection connection;
        connection = url.openConnection();
        HttpURLConnection httppost = (HttpURLConnection) connection;
        httppost.setDoInput(true);
        httppost.setDoOutput(true);
        httppost.setRequestMethod("POST");
        return httppost;
    }

    public String send(final String url, final String processingCode,
                       final byte[] param, final int timeout, final Activity act) {
        // closeAllResource(null, httpConn);
//		StringBuffer sb;
        String result = "";
        try {
            sending = true;
            httpConn = openConnection(url, act, param);
            OutputStream os = httpConn.getOutputStream();
            os.write(param); // bytes[] b of post data
            os.flush();

            if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                String theMsg = null;
                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_BAD_GATEWAY) {
                    theMsg = "Error: Bad Gateway";
                } else if (httpConn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                    theMsg = "Error: HTTP Not Found";
                } else if (httpConn.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    theMsg = "Error: Bad Request";
                }
                throw new IOException(theMsg);
            }
//			byte[] reply = null;
//			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            InputStream is = httpConn.getInputStream();
            if (is != null) {
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        result += line;

                    }
                    is.close();
                    reader.close();
                    return result;
                } catch (Exception ex) {
                    result = "Error";
                }

            } else {
            }
        } catch (Exception ex) {
            Log.i("Exception ", "Ga ada response....");
        }
        Log.i("Exception ", "response :"+result);
        return result;

        // thread.start();
    }

    public void closeAllResource(InputStream is, URLConnection _conn) {
        sending = false;
        try {
            if (is != null) {
                is.close();
                is = null;
            }
            if (_conn != null) {
                // _conn.notifyAll();
                _conn = null;
            }
            // Painter.connectionStatus = "";
        } catch (Exception ex) {
        }
    }

    public void cancel() {
        if (!sending) {
            return;
        }
        closeAllResource(null, httpConn);
    }
}

