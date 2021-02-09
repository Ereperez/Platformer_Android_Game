package com.ereperez.platformer.entities;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.ereperez.platformer.GameSettings;
import com.ereperez.platformer.utils.Utils;

/**
 * Created by edwin on 01,December,2020
 */
public class Coins extends DynamicEntity{
    private static final float MAX_DELTA = GameSettings.MAX_DELTA;
    private static final float ENTITY_WIDTH = GameSettings.ENTITY_WIDTH;
    private static final float ENTITY_HEIGHT = GameSettings.ENTITY_HEIGHT;
    private static boolean swap = false;
    private double dts;

    public Coins(String spriteName, int xPos, int yPos) {
        super(spriteName, xPos, yPos);
        width = ENTITY_WIDTH;
        height = ENTITY_HEIGHT;
        loadBitMap(spriteName, xPos, yPos);
    }

    @Override
    public void update(double dt) {
        x += Utils.clamp((float) (velX * dt), -MAX_DELTA, MAX_DELTA);
        dts = dt;
        if (isOnGround){
            final float gravityThisTick = (float) (gravity * dt);
            velY -= gravityThisTick+2;//1
        }
        y += Utils.clamp((float) (velY * dt), -MAX_DELTA, MAX_DELTA);
        if (y > game.getWorldHeight()){
            y = 0f;
        }
        isOnGround = false;
        if (!swap){
            y -= dt;
        }else {
            y += dt;
        }
    }

    @Override
    public void render(Canvas canvas, Matrix transform, Paint paint) {
        transform.preScale(1.0f, 1.0f);
        super.render(canvas, transform, paint);
    }

    @Override
    public void onCollision(Entity that) {
        super.onCollision(that);
        if (that.getClass().equals(StaticEntity.class)){
            final float gravityThisTick = (float) (gravity * dts);
            velY += gravityThisTick;
            swap = true;
        }else if (that.getClass().equals(EnemySpikes.class)){
            swap = false;
        }
    }
}
