package com.ereperez.platformer.levels;

public abstract class LevelData  {
    public static final String NULLSPRITE = "nullsprite"; //TODO resource
    public static final String PLAYER = "red_left1";
    public static final String SPIKES = "spearsdown_brown";
    public static final String COINS = "coin_yellow";
    public static final int NO_TILE = 0;

    int [][] mTiles;
    int mHeight;
    int mWidth;

    public int getTile(final int x, final int y){
        return mTiles[y][x];
    }

    int[] getRow(final int y){
        return mTiles[y];
    }

    void updateLevelDimensions(){
        mHeight = mTiles.length;
        mWidth = mTiles[0].length;
    }

    abstract public String getSpriteName(final int tileType);

}
