package com.ereperez.platformer.levels;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.ereperez.platformer.R;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by edwin on 03,February,2021
 */
public class LevelLoader {
    private final Context ctx;
    public static int [][] loadedTiles = null;

    public LevelLoader(Context context) {
        ctx = context;
        loadLevel();
    }

    public void loadLevel(){

        int rows = 30;
        int columns = 15;
        loadedTiles = new int[columns][rows];//TODO change?
        Resources res = ctx.getResources();

        try {
            String[] test = res.getStringArray(R.array.game_level);
/*            int a = 0;
            //int b = 0;
            for (String t : test){
                Log.d("test for T", t);
                String[] line = t.trim().split("[, {}]");
                //String[] line = t.split("[, {}]");
                int b = 0;
                for (String l : line){
                    Log.d("test for L", l);
                    loadedTiles[a][b] = Integer.parseInt(l);
                    //String[] single = l.split(",");
*//*                    for (String s : single){
                        Log.d("test for S", s);
                    }*//*
                    b++;
                }
                a++;
            }*/
            for (int i=0; i<test.length; i++) {
                String[] line = test[i].trim().split("[, {}]");
                //String[] line = test[i].split("[, {}]");
                //String[] line = test[i].split(",");
                Log.d("test array", Arrays.deepToString(test));
                Log.d("test [i]", (test[i]));
                //Log.d("LINE", String.valueOf((Arrays.toString(line))));
                Log.d("LINE i", (line[i]));
                for (int j=0; j<line.length; j++) {
                    loadedTiles[i][j] = Integer.parseInt(line[j]);
                    //loadedTiles[i][j] = Integer.parseInt(Arrays.toString(test[i].split("[, {}]")));
                    Log.d("LINE j", (line[j]));
                }
            }
            Log.d("loadedtiles", Arrays.deepToString(loadedTiles));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
