package com.ereperez.platformer.input;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;

import com.ereperez.platformer.MainActivity;
import com.ereperez.platformer.utils.Utils;

public class Accelerometer extends InputManager{
    public static final String TAG = "Accelerometer";
    private static final float DEGREES_PER_RADIAN = 57.2957795f;
    private static final float MAX_ANGLE = 30f;//TODO resource - possibly change
    private static final int AXIS_COUNT = 3; //azimuth (z), pitch (x), roll (y)
    private static final float SHAKE_THRESHOLD = 6f; //3.25f; // m/S^2     //TODO fix treshhold
    private static final long COOLDOWN = 300; //ms
    private final float[] rotationMatrix = new float[4*4];
    private final float[] orientation = new float[AXIS_COUNT];
    private final float[] lastMagFields = new float[AXIS_COUNT];
    private final float[] lastAccels = new float[AXIS_COUNT];
    private long lastShake = 0; //TODO remove?
    private final int rotation;
    private final MainActivity mActivity;//= null;

    public Accelerometer(MainActivity activity) {
        mActivity = activity;
        rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
    }

    private float getHorizontalAxis() {
        if (SensorManager.getRotationMatrix(rotationMatrix, null, lastAccels, lastMagFields)) {
            if (rotation == Surface.ROTATION_0) {
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, rotationMatrix);
                SensorManager.getOrientation(rotationMatrix, orientation);
                return orientation[1] * DEGREES_PER_RADIAN;
            }
            else {
                SensorManager.getOrientation(rotationMatrix, orientation);
                return -orientation[1] * DEGREES_PER_RADIAN;
            }
        }
        else {
            // Case for devices that DO NOT have magnetic sensors
            if (rotation == Surface.ROTATION_0) {
                return -lastAccels[0] * 5;
            }
            else {
                return -lastAccels[1] * -5;
            }
        }
    }

    //TODO remove?
    private boolean jumping(){
        if((System.currentTimeMillis() - lastShake) < COOLDOWN){
            return isJumping;
        }
        float x = lastAccels[0];
        float y = lastAccels[1];
        float z = lastAccels[2];
        float acceleration = (float) Math.sqrt(x*x + y*y + z*z)
                - SensorManager.GRAVITY_EARTH;
        if(acceleration > SHAKE_THRESHOLD){
            lastShake = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    @Override
    public void update(float dt) {
        horizontalFactor = getHorizontalAxis() / MAX_ANGLE;
        horizontalFactor = Utils.clamp(horizontalFactor, -1.0f, 1.0f);
        verticalFactor = 0.0f;
        isJumping = jumping();
    }

    private final SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            System.arraycopy(event.values, 0, lastAccels, 0, AXIS_COUNT);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private final SensorEventListener magneticListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            System.arraycopy(event.values, 0, lastMagFields, 0, AXIS_COUNT);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void registerListeners() {
        SensorManager sm = (SensorManager) mActivity
                .getSystemService(Activity.SENSOR_SERVICE);
        sm.registerListener(accelerometerListener,
                sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(magneticListener,
                sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
    }

    private void unregisterListeners() {
        SensorManager sm = (SensorManager) mActivity
                .getSystemService(Activity.SENSOR_SERVICE);
        sm.unregisterListener(accelerometerListener);
        sm.unregisterListener(magneticListener);
    }

    @Override
    public void onStart() {
        registerListeners();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        unregisterListeners();
    }

    @Override
    public void onResume() {
        registerListeners();
    }

    @Override
    public void onPause() {
        unregisterListeners();
    }
}
