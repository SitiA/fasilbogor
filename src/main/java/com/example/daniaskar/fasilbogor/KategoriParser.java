package com.example.daniaskar.fasilbogor;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class KategoriParser {


    private JSONArray responseArray;


    public List<Kategori> parse(String dataResponse)

            throws UnsupportedEncodingException, JSONException {

        List<Kategori> items = new ArrayList<Kategori>();

        JSONObject tempJson = new JSONObject(dataResponse);

        responseArray = null;

        try {


            responseArray = new JSONArray(tempJson.getString(Kategori.TAG_KAT));

            if (responseArray != null) {

                Log.i("JSON Array",	"data with array items" + responseArray.length());

                for (int i = 0; i < responseArray.length(); i++) {

                    items.add(getPost(responseArray.getJSONObject(i)));

                }

            }

        } catch (JSONException ex) {

        }


        return items;

    }


    private Kategori getPost(JSONObject responseObject) {

        Kategori bean = new Kategori();

        try {

            if (responseObject != null) {

                bean.setId_kategori(responseObject.isNull(Kategori.KAT_ID) ? null: responseObject.getString(Kategori.KAT_ID));

                bean.setNama_kategory(responseObject.isNull(Kategori.KAT_NAMA) ? null	: responseObject.getString(Kategori.KAT_NAMA));

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return bean;

    }


}

