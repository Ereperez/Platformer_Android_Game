package com.ereperez.platformer.input;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ereperez.platformer.R;
import com.ereperez.platformer.utils.Utils;

public class VirtualJoystick extends InputManager implements View.OnTouchListener{
    public static final String TAG = "VirtualJoystick";
    public static int maxDistance;
    private float startingPositionX;
    private float startingPositionY;

    public VirtualJoystick(View view) {
        view.findViewById(R.id.joystick_region)
                .setOnTouchListener(this);
        view.findViewById(R.id.button_region)
                .setOnTouchListener(this);
        maxDistance = Utils.dpToPx(48 * 2); //48dp = minimum hit target. maxDistance is in pixels.
        Log.d(TAG, "MaxDistance (pixels): " + maxDistance);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        int id = v.getId();
        if(action == MotionEvent.ACTION_DOWN){
            if(id == R.id.button_region){
                isJumping = true;
            }else if (id == R.id.joystick_region) {
                startingPositionX = event.getX(0);
                startingPositionY = event.getY(0);
            }
        }else if(action == MotionEvent.ACTION_UP){
            if(id == R.id.button_region){
                isJumping = false;
            }else if (id == R.id.joystick_region) {
                horizontalFactor = 0.0f;
                verticalFactor = 0.0f;
            }
        }else if(action == MotionEvent.ACTION_MOVE){
            if(id == R.id.joystick_region){
                //get the proportion to the maxDistance
                horizontalFactor = (event.getX(0) - startingPositionX)/VirtualJoystick.maxDistance;
                horizontalFactor = Utils.clamp(horizontalFactor, -1.0f, 1.0f);
                verticalFactor = (event.getY(0) - startingPositionY)/VirtualJoystick.maxDistance;
                verticalFactor = Utils.clamp(verticalFactor, -1.0f, 1.0f);
            }
        }
        return true;
    }
}

