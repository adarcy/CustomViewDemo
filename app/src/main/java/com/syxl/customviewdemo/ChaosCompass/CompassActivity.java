package com.syxl.customviewdemo.ChaosCompass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.syxl.customviewdemo.R;

/**
 * Created by likun on 2018/3/7.
 */

public class CompassActivity extends AppCompatActivity{

    private ChaosCompassView chaosCompassView;
    private SensorManager sensorManager;
    private SensorEventListener eventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        chaosCompassView = (ChaosCompassView) findViewById(R.id.ccv);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float val = event.values[0];
                chaosCompassView.setVal(val);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(eventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(eventListener);
    }
}
