package com.ereperez.platformer.entities;

import com.ereperez.platformer.utils.Utils;

public class DynamicEntity extends StaticEntity {
    private static final float MAX_DELTA = 0.48f; //TODO resource
    static final float GRAVITY = 40f; //TODO RESOURCE
    public float velX = 0;
    public float velY = 0;
    public final float gravity = GRAVITY;
    boolean isOnGround = false;

    public DynamicEntity(String spriteName, int xPos, int yPos) {
        super(spriteName, xPos, yPos);
    }

    @Override
    public void update(double dt) {
        x += Utils.clamp((float) (velX * dt), -MAX_DELTA, MAX_DELTA);
        if (!isOnGround){
            final float gravityThisTick = (float) (gravity * dt);
            velY += gravityThisTick;
        }
        y += Utils.clamp((float) (velY * dt), -MAX_DELTA, MAX_DELTA);
        if (y > game.getWorldHeight()){
            y = 0f;//Utils.between(-4f, 0f);
        }
        isOnGround = false;
    }

    @Override
    public void onCollision(Entity that) {
        Entity.getOverlap(this, that, Entity.overlap);
        x += Entity.overlap.x;
        y += Entity.overlap.y;
        if (Entity.overlap.y != 0){
            velY = 0;
            if (Entity.overlap.y < 0f){//we've hit our fett
                isOnGround = true;
            }//if overlap.y > 0f, we hit our head
        }
    }
}
