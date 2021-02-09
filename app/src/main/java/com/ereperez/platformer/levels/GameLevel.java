package com.ereperez.platformer.levels;

import android.util.SparseArray;


public class GameLevel extends LevelData {
    private final SparseArray<String> mTileIdToSpriteName = new SparseArray<>();

    public GameLevel() {
        mTileIdToSpriteName.put(BACKGROUND_INT, BACKGROUND);
        mTileIdToSpriteName.put(PLAYER_INT, PLAYER);
        mTileIdToSpriteName.put(GROUND_TILE_INT, GROUND_TILE);
        mTileIdToSpriteName.put(GROUND_LEFT_TILE_INT, GROUND_LEFT_TILE);
        mTileIdToSpriteName.put(GROUND_RIGHT_TILE_INT, GROUND_RIGHT_TILE);
        mTileIdToSpriteName.put(SPIKES_INT, SPIKES);
        mTileIdToSpriteName.put(COINS_INT, COINS);

        mTiles = LevelLoader.loadedTiles;
        updateLevelDimensions();
    }

    @Override
    public String getSpriteName(int tileType) {
        final String fileName = mTileIdToSpriteName.get(tileType);
        if (fileName != null){
            return fileName;
        }
        return NULLSPRITE;
    }
}
