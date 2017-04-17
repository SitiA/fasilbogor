package com.example.daniaskar.fasilbogor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.UnsupportedEncodingException;

public class SpalshScreen extends Activity {
    HttpConnector connector;
    String dataAll;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        new BackgroundLoadForCheckUpdateVersion().execute();

    }

    public class BackgroundLoadForCheckUpdateVersion extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            if (dataAll != null) {
                Log.i("RESPONSE : ", dataAll);
                intent = new Intent(SpalshScreen.this, MainMenu.class);
                intent.putExtra("data", dataAll);
                startActivity(intent);
                finish();
            } else {
                Log.i("RESPONSE : ", "ERROR, NULL Response");
                finish();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            dataAll = getAllCategory();
            return null;
        }
    }
    private String getAllCategory() {
        String url_checkVersion = URLFactory.BASE_URL+"var1=1&var2=0";
        String result = null;
        String data = "";
        connector = new HttpConnector();
        try {
            Log.i("SEND URL : ", url_checkVersion);
            result = connector.send(url_checkVersion, "",data.getBytes("UTF-8"), 30000,SpalshScreen.this);
            Log.i("json?", result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}


