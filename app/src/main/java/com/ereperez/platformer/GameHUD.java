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
    private static final String COINS_LEFT = GameSettings.COINS_LEFT_HUD;
    private static final int STAGE_HEIGHT = GameSettings.STAGE_HEIGHT;
    private static final int HUD_TEXT_OFFSET = GameSettings.HUD_TEXT_OFFSET;
    private static final float HUD_TEXT_SIZE = GameSettings.HUD_TEXT_SIZE;
    private static final float FULLSCREEN_TEXT_SIZE = GameSettings.FULLSCREEN_TEXT_SIZE;
    private static final String GAME_OVER = GameSettings.GAME_OVER_HUD;
    private static final String GAME_OVER_RESTART = GameSettings.GAME_OVER_RESTART_HUD;
    final float centerY = (float) STAGE_HEIGHT/2;
    final float centerX = (centerY)+300;

    private int health = 0;
    private int coinsCollected = 0;
    private int coinsLeft = 0;
    private Canvas canvas = null;
    private Paint paint = null;

    public void renderHUD(final int mHealth, final int mCoinsCollected, final int mCoinsTotal, final Canvas mCanvas, final Paint mPaint){
        health = mHealth;
        coinsCollected = mCoinsCollected;
        coinsLeft = mCoinsTotal;
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
        canvas.drawText(String.format(Locale.getDefault(),"%s%d", COINS_LEFT, coinsLeft), HUD_TEXT_OFFSET, HUD_TEXT_SIZE*3, paint);
    }

    public void gameOver(){
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(FULLSCREEN_TEXT_SIZE);
        canvas.drawText(GAME_OVER, centerX, centerY, paint);
        canvas.drawText(GAME_OVER_RESTART, centerX, centerY + FULLSCREEN_TEXT_SIZE, paint);
    }
}
