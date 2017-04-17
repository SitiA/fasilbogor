package com.example.daniaskar.fasilbogor;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CallActivity extends Activity{
    Button buttonCall;
    String phone;
    TextView textPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);
        init();
    }
    private void init() {
        phone =  getIntent().getExtras().getString("phone");
        textPhone = (TextView) findViewById(R.id.tvPhoneNumber);
        textPhone.setText(phone);
        buttonCall = (Button) findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phone));
                startActivity(callIntent);
            }
        });

    }
}
