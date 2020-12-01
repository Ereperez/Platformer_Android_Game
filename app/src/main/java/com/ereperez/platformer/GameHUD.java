package com.ereperez.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Locale;

/**
 * Created by edwin on 01,December,2020
 */
public class GameHUD {
    private static final String HEALTH = GameSettings.HEALTH_HUD;
    private static final String COINS_COLLECTED = GameSettings.COINS_COLLECTED_HUD;
    private static final String COINS_TOTAL = GameSettings.COINS_TOTAL_HUD;
    private static final int STAGE_HEIGHT = GameSettings.STAGE_HEIGHT;
    private static final int HUD_TEXT_OFFSET = GameSettings.HUD_TEXT_OFFSET;
    private static final float HUD_TEXT_SIZE = GameSettings.HUD_TEXT_SIZE;
    private int health = 0;
    private int coinsCollected = 0;
    private int coinsTotal = 0;
    private Canvas canvas = null;
    private Paint paint = null;

    public void renderHUD(final int mHealth, final int mCoinsCollected, final int mCoinsTotal, final Canvas mCanvas, final Paint mPaint){
        health = mHealth;
        coinsCollected = mCoinsCollected;
        coinsTotal = mCoinsTotal;
        canvas = mCanvas;
        paint = mPaint;
        gameHUD();
    }

    public void gameHUD(){
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(HUD_TEXT_SIZE);
        canvas.drawText(String.format(Locale.getDefault(), "%s%d", HEALTH, health), HUD_TEXT_OFFSET, HUD_TEXT_SIZE, paint);
        canvas.drawText(String.format(Locale.getDefault(),"%s%s", COINS_COLLECTED, coinsCollected), HUD_TEXT_OFFSET, HUD_TEXT_SIZE*2, paint);
        canvas.drawText(String.format(Locale.getDefault(),"%s%d", COINS_TOTAL, coinsTotal), HUD_TEXT_OFFSET, HUD_TEXT_SIZE*3, paint);
    }
}
