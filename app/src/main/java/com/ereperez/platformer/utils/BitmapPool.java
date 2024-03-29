package com.ereperez.platformer.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.ereperez.platformer.Game;

import java.util.HashMap;
import java.util.Objects;

public class BitmapPool {
    private static final String TAG = "BitmapPool";
    private final HashMap<String, Bitmap> bitmaps = new HashMap<>();
    private Bitmap nullsprite = null;
    private final Game game;

    public BitmapPool(final Game mGame){
        Objects.requireNonNull(mGame, "BitmapPool requires a valid Game instance");
        game = mGame;
        nullsprite = createBitmap("nullsprite", 0.5f, 0.5f); //the nullsprite, in case loading fails later
        Objects.requireNonNull(nullsprite, "BitmapPool: unable to create nullsprite!");
    }

    public Bitmap createBitmap(final String sprite, float widthMeters, float heightMeters){
        final String key = makeKey(sprite, widthMeters, heightMeters);
        Bitmap bmp = getBitmap(key);
        if(bmp != null){
            return bmp;
        }
        try {
            bmp = BitmapUtils.loadScaledBitmap(game.getContext(), sprite, game.worldToScreenX(widthMeters), game.worldToScreenY(heightMeters));
            put(key, bmp);
        }catch(final OutOfMemoryError e){
            //this is very very bad! Ideally you have some reference counted assets and can start unloading as needed
            Log.w(TAG, "Out of Memory!",  e);
        }finally{
            if(bmp == null) {
                bmp = nullsprite;
            }
        }
        return bmp;
    }

    public int size(){ return bitmaps.size(); }
    public String makeKey(final String name, final float widthMeters, final float heightMeters){
        return name+"_"+widthMeters+"_"+heightMeters;
    }
    public void put(final String key, final Bitmap bmp){
        if(bitmaps.containsKey(key)) {
            return;
        }
        bitmaps.put(key, bmp);
    }
    public boolean contains(final String key){
        return bitmaps.containsKey(key);
    }
    public boolean contains(final Bitmap bmp){ return bitmaps.containsValue(bmp); }
    public Bitmap getBitmap(final String key){
        return bitmaps.get(key);
    }
    private String getKey(final Bitmap bmp){
        if(bmp != null) {
            for (HashMap.Entry<String, Bitmap> entry : bitmaps.entrySet()) {
                if (bmp == entry.getValue()) {
                    return entry.getKey();
                }
            }
        }
        return "";
    }
    private void remove(final String key){
        Bitmap tmp = bitmaps.get(key);
        if(tmp != null){
            bitmaps.remove(key);
            tmp.recycle();
        }
    }
    public void remove(Bitmap bmp){
        if(bmp == null){return;}
        remove(getKey(bmp));
    }
    public void empty(){
        for (final HashMap.Entry<String, Bitmap> entry : bitmaps.entrySet()) {
            if (entry.getValue() != nullsprite){
                entry.getValue().recycle();
            }
        }
        bitmaps.clear();
    }
}