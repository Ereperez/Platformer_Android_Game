package com.ereperez.platformer.levels;

import android.util.SparseArray;

public class TestLevel extends LevelData {
    private final SparseArray<String> mTileIdToSpriteName = new SparseArray<>();

    public TestLevel() {
        mTileIdToSpriteName.put(0, "background");
        mTileIdToSpriteName.put(1, PLAYER);
        mTileIdToSpriteName.put(2, "ground_square");
        mTileIdToSpriteName.put(3, "ground_left");
        mTileIdToSpriteName.put(4, "ground_right");
        mTileIdToSpriteName.put(5, "spearsdown_brown");

        mTiles = new int[][]{
                {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,4},
                {3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                {3,0,0,0,0,0,0,0,0,0,0,0,0,0,3,2,4},
                {3,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,4},
                {3,2,4,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                {3,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,4},
                {3,0,0,0,0,0,3,2,2,2,4,0,0,0,0,0,4},
                {3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                {3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                {3,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,4},
                {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,4},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
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
