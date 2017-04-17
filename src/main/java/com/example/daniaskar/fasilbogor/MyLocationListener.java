package com.example.daniaskar.fasilbogor;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

//import com.google.android.gms.location.LocationListener;

public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location loc)
    {
        loc.getLatitude();
        loc.getLongitude();

        String Text = "My current location is: " +
                "Latitud = " + loc.getLatitude() +
                "Longitud = " + loc.getLongitude();

        Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
    }

    private Context getApplicationContext() {
        // TODO Auto-generated method stub
        return null;
    }

    public void onProviderDisabled(String provider)
    {
        Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
    }

    public void onProviderEnabled(String provider)
    {
        Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }



    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

}

