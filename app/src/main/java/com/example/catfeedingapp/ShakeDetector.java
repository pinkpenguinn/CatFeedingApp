package com.example.catfeedingapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class ShakeDetector implements SensorEventListener {
    private static final float SHAKE_GRAVITY = 2.7f;
    private static final int SHAKE_TIME = 500;
    private static final int SHAKE_RESET_TIME = 3000;

    private OnShakeListener listener;
    private long shakeTimestamp;
    private int shakeCount;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(listener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_GRAVITY) {
                final long now = System.currentTimeMillis();

                if (shakeTimestamp + SHAKE_TIME > now) {
                    return;
                }

                if (shakeTimestamp + SHAKE_RESET_TIME < now) {
                    shakeCount = 0;
                }

                shakeTimestamp = now;
                shakeCount++;

                listener.onShake(shakeCount);
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.listener = listener;
    }


}
