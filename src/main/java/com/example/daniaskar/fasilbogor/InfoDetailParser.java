package com.example.daniaskar.fasilbogor;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class InfoDetailParser {


    private JSONArray responseArray;


    public List<InfoDetail> parse(String dataResponse)
            throws UnsupportedEncodingException, JSONException {

        List<InfoDetail> items = new ArrayList<InfoDetail>();

        JSONObject tempJson = new JSONObject(dataResponse);

        responseArray = null;
        try {


            responseArray = new JSONArray(tempJson.getString(InfoDetail.TAG_INFO));

            if (responseArray != null) {

                Log.i("JSON Array","data with array items" + responseArray.length());

                for (int i = 0; i < responseArray.length(); i++) {

                    items.add(getPost(responseArray.getJSONObject(i)));

                }

            }

        } catch (JSONException ex) {
        }return items;

    }


    private InfoDetail getPost(JSONObject responseObject) {

        InfoDetail bean = new InfoDetail();

        try {

            if (responseObject != null) {

                bean.setId_info(responseObject.isNull(InfoDetail.INFO_DETAIL_ID) ? null: responseObject.getString(InfoDetail.INFO_DETAIL_ID));

                bean.setNama_info(responseObject.isNull(InfoDetail.INFO_DETAIL_NAMA) ? null: responseObject.getString(InfoDetail.INFO_DETAIL_NAMA));

                bean.setAlamat(responseObject.isNull(InfoDetail.INFO_DETAIL_ALAMAT) ? null: responseObject.getString(InfoDetail.INFO_DETAIL_ALAMAT));

                bean.setJam_operasional(responseObject.isNull(InfoDetail.INFO_DETAIL_OPR) ? null: responseObject.getString(InfoDetail.INFO_DETAIL_OPR));

                bean.setLongtitude(responseObject.isNull(InfoDetail.INFO_DETAIL_LONG) ? null: responseObject.getString(InfoDetail.INFO_DETAIL_LONG));

                bean.setLatitude(responseObject.isNull(InfoDetail.INFO_DETAIL_LAT) ? null: responseObject.getString(InfoDetail.INFO_DETAIL_LAT));

                bean.setPhone(responseObject.isNull(InfoDetail.INFO_NO_TELP) ? null: responseObject.getString(InfoDetail.INFO_NO_TELP));

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return bean;
    }

}

