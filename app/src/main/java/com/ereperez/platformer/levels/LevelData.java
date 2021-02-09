package com.ereperez.platformer.levels;

import com.ereperez.platformer.GameSettings;

public abstract class LevelData  {
    public static final String NULLSPRITE = GameSettings.NULLSPRITE_TILE;
    public static final String BACKGROUND = GameSettings.BACKGROUND_TILE;
    public static final String PLAYER = GameSettings.PLAYER_TILE;
    public static final String SPIKES = GameSettings.SPIKES_TILE;
    public static final String COINS = GameSettings.COINS_TILE;
    public static final String GROUND_TILE = GameSettings.GROUND_TILE;
    public static final String GROUND_LEFT_TILE = GameSettings.GROUND_LEFT_TILE;
    public static final String GROUND_RIGHT_TILE = GameSettings.GROUND_RIGHT_TILE;
    public static final int BACKGROUND_INT = GameSettings.BACKGROUND_TILE_INT;
    public static final int PLAYER_INT = GameSettings.PLAYER_TILE_INT;
    public static final int SPIKES_INT = GameSettings.SPIKES_TILE_INT;
    public static final int COINS_INT = GameSettings.COINS_TILE_INT;
    public static final int GROUND_TILE_INT = GameSettings.GROUND_TILE_INT;
    public static final int GROUND_LEFT_TILE_INT = GameSettings.GROUND_LEFT_TILE_INT;
    public static final int GROUND_RIGHT_TILE_INT = GameSettings.GROUND_RIGHT_TILE_INT;
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
