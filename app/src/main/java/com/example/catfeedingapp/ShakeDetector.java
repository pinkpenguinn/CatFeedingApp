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
            float x = event.values[0];  // acceleration along the x axis
            float y = event.values[1];  // acceleration along the y axis
            float z = event.values[2];  // acceleration along the z axis

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // if gForce is close to 1, means there is no shake event
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);


            if (gForce > SHAKE_GRAVITY) {
                final long now = System.currentTimeMillis();

                if (shakeTimestamp + SHAKE_TIME > now) { // ignores shakes that are too close to one another
                    return;
                }

                if (shakeTimestamp + SHAKE_RESET_TIME < now) { // resets shake counter after 3 seconds of no shakes
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
         void onShake(int count);


    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.listener = listener;
    }


}
