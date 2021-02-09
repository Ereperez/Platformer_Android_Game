package com.ereperez.platformer.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class StaticEntity extends Entity {
    protected Bitmap bitmap = null;

    public StaticEntity(final String spriteName, final int xPos, final int yPos) {
        x = xPos;
        y = yPos;
        loadBitMap(spriteName, xPos, yPos);
    }

    protected void loadBitMap(final String spriteName, final int xPos, final int yPos){
        bitmap = game.pool.createBitmap(spriteName, width, height);
    }

    @Override
    public void render(Canvas canvas, Matrix transform, Paint paint) {
        canvas.drawBitmap(bitmap, transform, paint);
    }
}
