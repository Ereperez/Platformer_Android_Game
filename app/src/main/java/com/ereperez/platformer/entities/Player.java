package com.ereperez.platformer.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import com.ereperez.platformer.GameEvent;
import com.ereperez.platformer.GameSettings;
import com.ereperez.platformer.input.InputManager;
import com.ereperez.platformer.utils.BitmapUtils;

public class Player extends DynamicEntity {
    static final String TAG = "Player";
    private static final int PLAYER_HEALTH = GameSettings.PLAYER_HEALTH;
    private static final float PLAYER_RUN_SPEED = GameSettings.PLAYER_SPEED;
    private static final float PLAYER_JUMP_FORCE = GameSettings.PLAYER_JUMP_FORCE;
    private static final float MIN_INPUT_TO_TURN = GameSettings.MIN_TURN_INPUT;
    private static final int COLOR = GameSettings.PLAYER_BLINK_COLOR;
    private static final int IMMUNITY_TIME = GameSettings.PLAYER_IMMUNITY_TIME;
    private static final int LEFT = GameSettings.LEFT_DIRECTION;
    private static final int RIGHT = GameSettings.RIGHT_DIRECTION;
    private static final float PLAYER_DIMENSION = GameSettings.PLAYER_DIMENSION;
    private int facing = LEFT;

    public int health = PLAYER_HEALTH;
    public int coinsCollected = 0;
    int updateCount = 0;
    boolean immunity = false;
    final Bitmap tempBit;

    public Player(final String spriteName, final int xPos, final int yPos) {
        super(spriteName, xPos, yPos);
        width = PLAYER_DIMENSION;
        height = PLAYER_DIMENSION;
        loadBitMap(spriteName, xPos, yPos);
        tempBit = bitmap;
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

    @Override
    public void update(final double dt) {
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

    private void updateFacingDirection(final float controlDirection){
        if (Math.abs(controlDirection) < MIN_INPUT_TO_TURN) { return; }
        if (controlDirection < 0) { facing = LEFT; }
        else if (controlDirection > 0 ) { facing = RIGHT; }
    }

    @Override
    public void onCollision(Entity that) {
        super.onCollision(that);
        if (that.getClass().equals(EnemySpikes.class)){
            Log.d("Player health: ", String.valueOf(health));
            if (updateCount > IMMUNITY_TIME) {
                updateCount = 0;
                immunity = true;
                health--;
                game.onGameEvent(GameEvent.SpikeDamage, this);
            }
        }else if(that.getClass().equals(Coins.class)){
            game.onGameEvent(GameEvent.CoinPickup, this);
            coinsCollected++;
            game.coinAmount--;
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
