package com.example.daniaskar.fasilbogor;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.UnsupportedEncodingException;
import java.util.List;

//import com.google.android.gms.location.LocationListener;

public class InfoDetailActivity extends Activity {
    String idInfoDetail;
    private ProgressDialog dialog;
    String hasilGetData;
    InfoDetailParser infoParser = new InfoDetailParser();
    List<InfoDetail> infoDetail = null;
    InfoDetail detail = null;
    HttpConnector connector;
    TextView tvNama;
    TextView tvJam;
    TextView tvAlamat;
    TextView tvPhone;
    Button btnNav;
    Button btnPhone;
    private GoogleMap mMap;
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        init();
        idInfoDetail = getIntent().getExtras().getString("id_info");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        new BackgroundLoad().execute();
 
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_id))
                .getMap();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        LocationListener ll = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLocationChanged(Location location) {

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("my position")
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                        location.getLatitude(), location.getLongitude()), 15.0f));

            }
        };
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);



//		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_id))
//				.getMap();
//		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//        /* Use the LocationManager class to obtain GPS locations */
//        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//
//        LocationListener mlocListener = (LocationListener) new MyLocationListener();
//        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
//
//
//		 final LatLng KERKOF = new LatLng(-6.902473, 107.526228);
//		 mMap.addMarker(new MarkerOptions()
//		                            .position(KERKOF)
//		                            .title("Melbourne")
//		                            .snippet("Population: 4,137,400")
//		                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.point)));

    }

    private void init() {
        tvNama = (TextView) findViewById(R.id.tvNama);
        tvAlamat = (TextView) findViewById(R.id.tvAlamat);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvJam = (TextView) findViewById(R.id.tvJam);
        btnNav = (Button) findViewById(R.id.buttonNav);
        btnNav.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //set Navigasi
            }
        });


        btnPhone= (Button) findViewById(R.id.buttonLoadPhone);
        btnPhone.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(InfoDetailActivity.this, CallActivity.class);
                intent.putExtra("phone", detail.getPhone());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_right);

            }
        });

    }



    public class BackgroundLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void result) {
            tvNama.setText(detail.getNama_info());
            tvJam.setText(detail.getJam_operasional());
            tvAlamat.setText(detail.getAlamat());
            tvPhone.setText(detail.getPhone());
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(InfoDetailActivity.this, "Loading...",
                    "please wait...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            hasilGetData = getData(idInfoDetail);
            try {
                infoDetail = infoParser.parse(hasilGetData);
                detail = infoDetail.get(0);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }

    private String getData(String id_kat) {
        String url_checkVersion = URLFactory.BASE_URL + "var1=3&var2=" + id_kat;
        String result = null;
        String data = "";
        connector = new HttpConnector();
        try {
            Log.i("SEND URL : ", url_checkVersion);
            result = connector.send(url_checkVersion, "",
                    data.getBytes("UTF-8"), 30000, InfoDetailActivity.this);
            Log.i("json?", result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }



}

