package com.ereperez.platformer.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class StaticEntity extends Entity {
    //static final float DEFAULT_DIMENSION = 1.0f;//TODO resources
    protected Bitmap bitmap = null;

    public StaticEntity(final String spriteName, final int xPos, final int yPos) {
/*        width = DEFAULT_DIMENSION;
        height = DEFAULT_DIMENSION;*/
        x = xPos;// * _game.worldToScreenX(_width);
        y = yPos;// * _game.worldToScreenY(_height);
        loadBitMap(spriteName, xPos, yPos);
    }

    protected void loadBitMap(final String spriteName, final int xPos, final int yPos){
        //destroy();
/*        final int widthPixels = game.worldToScreenX(width);
        final int heightPixels = game.worldToScreenY(height);*/
        bitmap = game.pool.createBitmap(spriteName, width, height);
/*        try {
            bitmap = BitmapUtils.loadScaledBitmap(game.getContext(), spriteName, widthPixels, heightPixels);
        } catch (Exception e){
            e.printStackTrace();//TODO log
        }*/
    }

    @Override
    public void render(Canvas canvas, Matrix transform, Paint paint) {
        canvas.drawBitmap(bitmap, transform, paint);
    } //bitmap, _x, _y, paint

    @Override
    public void destroy() {
/*        if (bitmap != null){
            bitmap.recycle();
            bitmap = null;
        }*/
    }

/*    @Override
    void onCollision(Entity that) {
        _x = STAGE_WIDTH + _game.rng.nextInt(ENEMY_SPAWN_OFFSET);
    }*/

}
