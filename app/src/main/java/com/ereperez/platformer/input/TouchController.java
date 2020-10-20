package com.ereperez.platformer.input;

import android.view.MotionEvent;
import android.view.View;
import com.ereperez.platformer.R;

public class TouchController extends InputManager implements View.OnTouchListener {

    public TouchController(View view){
        view.findViewById(R.id.keypad_up)
                .setOnTouchListener(this);
        view.findViewById(R.id.keypad_down)
                .setOnTouchListener(this);
        view.findViewById(R.id.keypad_left)
                .setOnTouchListener(this);
        view.findViewById(R.id.keypad_right)
                .setOnTouchListener(this);
        view.findViewById(R.id.keypad_jump)
                .setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        int action = event.getActionMasked();
        int id = v.getId();
        if(action == MotionEvent.ACTION_DOWN){
            // User started pressing a key
            if(id == R.id.keypad_up){
                verticalFactor -= 1;
            }else if (id == R.id.keypad_down) {
                verticalFactor += 1;
            }
            if (id == R.id.keypad_left) {
                horizontalFactor -= 1;
            } else if(id == R.id.keypad_right) {
                horizontalFactor += 1;
            }
            if (id == R.id.keypad_jump) {
                isJumping = true;
            }
        } else if(action == MotionEvent.ACTION_UP) {
            // User released a key
            if (id == R.id.keypad_up) {
                verticalFactor += 1;
            } else if (id == R.id.keypad_down) {
                verticalFactor -= 1;
            }
            if (id == R.id.keypad_left) {
                horizontalFactor += 1;
            } else if (id == R.id.keypad_right) {
                horizontalFactor -= 1;
            }
            if (id == R.id.keypad_jump) {
                isJumping = false;
            }
        }
        return false;
    }
}