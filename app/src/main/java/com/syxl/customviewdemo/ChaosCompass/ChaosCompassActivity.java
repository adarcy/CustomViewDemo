package com.syxl.customviewdemo.ChaosCompass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.syxl.customviewdemo.R;

public class ChaosCompassActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaos_compass);
        findViewById(R.id.tv_compass).setOnClickListener(this);
        findViewById(R.id.tv_oclock).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_compass:
                startActivity(new Intent(ChaosCompassActivity.this,CompassActivity.class));
                break;
            case R.id.tv_oclock:
                startActivity(new Intent(ChaosCompassActivity.this,OclockActivity.class));
                break;
        }
    }
}
