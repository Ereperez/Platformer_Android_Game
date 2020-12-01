package com.ereperez.platformer.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.ereperez.platformer.GameEvent;
import com.ereperez.platformer.GameSettings;
import com.ereperez.platformer.input.InputManager;
import com.ereperez.platformer.levels.LevelData;
import com.ereperez.platformer.levels.LevelManager;
import com.ereperez.platformer.utils.BitmapUtils;

public class Player extends DynamicEntity {
    static final String TAG = "Player";
/*    private static final int PLAYER_HEIGHT = GameSettings.PLAYER_HEIGHT;
    private static final int STARTING_POSITION = GameSettings.STARTING_POSITION;
    private static final int STARTING_HEALTH = GameSettings.STARTING_HEALTH;
    private static final float ACC = GameSettings.ACC;
    private static final float MIN_VEL = GameSettings.MIN_VEL;
    private static final float MAX_VEL = GameSettings.MAX_VEL;
    private static final float GRAVITY = GameSettings.GRAVITY;
    private static final float LIFT = GameSettings.LIFT;
    private static final float DRAG = GameSettings.DRAG;
    int health = 0;*/
    private static final float PLAYER_RUN_SPEED = 6.0f; //meter per second TODO: resource
    private static final float PLAYER_JUMP_FORCE = -(GRAVITY/2); //whatever feel good TODO: resource
    private static final float MIN_INPUT_TO_TURN = 0.05f; //5% joystick input before we start turning TODO: RESOURCE
    private static final int COLOR = GameSettings.PLAYER_BLINK_COLOR;
    private static final int IMMUNITY_TIME = 60; //GameSettings.PLAYER_IMMUNITY_TIME;
    private final int LEFT = 1; //TODO: RESORUCE
    private final int RIGHT = -1;
    private int facing = LEFT;

    public int health = 5; //TODO: resource
    int updateCount = 0;
    boolean immunity = false;
    final Bitmap tempBit;

    public Player(final String spriteName, final int xPos, final int yPos) {
        super(spriteName, xPos, yPos);
        width = 0.5f;//DEFAULT_DIMENSION;
        height = 0.5f;//DEFAULT_DIMENSION;
        loadBitMap(spriteName, xPos, yPos);
        tempBit = bitmap;
        //respawn();
    }

    @Override
    public void render(Canvas canvas, Matrix transform, Paint paint) {
        transform.preScale(facing, 1.0f);
        if (facing == RIGHT){
            final float offset = game.worldToScreenX(width);
            transform.postTranslate(offset, 0);
        }
        super.render(canvas, transform, paint);
    }

/*    @Override
    void respawn() {
        _x = STARTING_POSITION;
        health = STARTING_HEALTH;
        _velX = 0f;
        _velY = 0f;
        bitmap = tempBit;
        immunity = false;
    }*/

    @Override
    public void update(final double dt) {
        //_x += _velX * dt;
        final InputManager controls = game.getControls();
        final float direction = controls.horizontalFactor;
        velX = direction * PLAYER_RUN_SPEED;
        updateFacingDirection(direction);
        if (controls.isJumping && isOnGround){
            game.onGameEvent(GameEvent.Jump, this);
            velY = PLAYER_JUMP_FORCE;
            isOnGround = false;
        }
        super.update(dt);
        updateCount++;
        checkImmunity();
    }

    private void updateFacingDirection(final float controlDirection){//TODO don't turn on slight movement
        if (Math.abs(controlDirection) < MIN_INPUT_TO_TURN) { return; }
        if (controlDirection < 0) { facing = LEFT; }
        else if (controlDirection > 0 ) { facing = RIGHT; }
    }

    @Override
    public void onCollision(Entity that) {
        super.onCollision(that);
        if (that.equals(LevelManager.spikes)){
            Log.d("Player Collision: ", "with Spikes");
            Log.d("Player health: ", String.valueOf(health));
            if (updateCount > IMMUNITY_TIME){
                updateCount = 0;
                immunity = true;
                health--;
                game.onGameEvent(GameEvent.SpikeDamage, this);
                //TODO sound effect
            }
        }
    }

    private void checkImmunity(){
        if (immunity && updateCount > 0){
            if (updateCount %2 == 0 && updateCount < IMMUNITY_TIME) {
                bitmap = BitmapUtils.colorBitmap(bitmap, COLOR);
            }
            else {
                bitmap = tempBit;
            }
            if (updateCount >= IMMUNITY_TIME){
                immunity = false;
            }
        }
    }
}
