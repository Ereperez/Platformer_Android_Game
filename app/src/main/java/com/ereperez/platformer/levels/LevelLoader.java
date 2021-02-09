package com.ereperez.platformer.levels;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.ereperez.platformer.GameSettings;
import com.ereperez.platformer.R;
import java.util.Arrays;

/**
 * Created by edwin on 03,February,2021
 */
public class LevelLoader {
    public static final String TAG = "LevelLoader";
    private static final int LEVEL_ROWS = GameSettings.LEVEL_ROWS;
    private static final int LEVEL_COLUMNS = GameSettings.LEVEL_COLUMNS;
    public static int [][] loadedTiles = null;

    public LevelLoader(Context ctx) {
        loadLevel(ctx);
    }

    public void loadLevel(Context ctx){
        loadedTiles = new int[LEVEL_COLUMNS][LEVEL_ROWS];
        Resources res = ctx.getResources();

        try {
            String[] test = res.getStringArray(R.array.game_level);
            for (int i=0; i<test.length; i++) {
                String[] line = test[i].trim().split("[, {}]");
                for (int j=0; j<line.length; j++) {
                    loadedTiles[i][j] = Integer.parseInt(line[j]);
                }
            }
            Log.d(TAG, "Printing gameLevel Array: " + Arrays.deepToString(loadedTiles));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}
