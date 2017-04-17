package com.example.daniaskar.fasilbogor;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InfoParser {


    private JSONArray responseArray;


    public List<InfoBeans> parse(String dataResponse)

            throws UnsupportedEncodingException, JSONException {

        List<InfoBeans> items = new ArrayList<InfoBeans>();

        JSONObject tempJson = new JSONObject(dataResponse);

        responseArray = null;

        try {


            responseArray = new JSONArray(tempJson.getString(InfoBeans.TAG_INFO));

            if (responseArray != null) {

                Log.i("JSON Array","data with array items" + responseArray.length());

                for (int i = 0; i < responseArray.length(); i++) {

                    items.add(getPost(responseArray.getJSONObject(i)));

                }

            }

        } catch (JSONException ex) {

        }


        return items;

    }


    private InfoBeans getPost(JSONObject responseObject) {

        InfoBeans bean = new InfoBeans();

        try {

            if (responseObject != null) {

                bean.setId_info(responseObject.isNull(InfoBeans.INFO_ID) ? null: responseObject.getString(InfoBeans.INFO_ID));

                bean.setNama_info(responseObject.isNull(InfoBeans.INFO_NAMA) ? null : responseObject.getString(InfoBeans.INFO_NAMA));

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return bean;

    }


}

