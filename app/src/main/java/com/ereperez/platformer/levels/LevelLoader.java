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
/*        Scanner sc = null;
        try {
            sc = new Scanner(new BufferedReader(new FileReader("gameLevel.txt")));
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
            //TODO log.d(TAG
        }
        int rows = 30;
        int columns = 15;
        int [][] mTiles = new int[rows][columns];
        while(sc.hasNextLine()) {
            for (int i=0; i<mTiles.length; i++) {
                String[] line = sc.nextLine().trim().split(",");
                for (int j=0; j<line.length; j++) {
                    mTiles[i][j] = Integer.parseInt(line[j]);
                }
            }
        }*/
        int rows = 30;
        int columns = 15;
        loadedTiles = new int[rows][columns];
        Resources res = ctx.getResources();

        try {
            String[] test = res.getStringArray(R.array.game_level);
/*            Log.d("LEVEL", Arrays.toString(test));//todo fix
            for (int i = 0; i < test.length; i++) {
                Log.d("LEVEL_TILES", test[i]);
                //Log.d("LEVEL_testArray", Arrays.toString(new String[]{test[i]}));
*//*                String[] line = Arrays.toString(test).split("[, ]");
                for (int j=0; j<line.length; j++) {
                    loadedTiles[i][j] = Integer.parseInt(line[j]);
                    //Log.d("LEVEL_LoadedTiles", Arrays.toString(loadedTiles));//todo fix
                }*//*
                for (String t : test){
                    Log.d("test for", t);
                }
            }*/
            for (int i=0; i<test.length; i++) {
                //String[] line = test[i].trim().split("[, {}]");
                String[] line = test[i].split("[, {}]");
                for (int j=0; j<line.length; j++) {
                    loadedTiles[i][j] = Integer.parseInt(line[j]);
                }
            }
            Log.d("loadedtiles", Arrays.deepToString(loadedTiles));
        } catch (Exception e) {
            e.printStackTrace();
        }

/*        while(sc.hasNextLine()) {
            for (int i=0; i<mTiles.length; i++) {
                String[] line = sc.nextLine().trim().split(",");
                for (int j=0; j<line.length; j++) {
                    mTiles[i][j] = Integer.parseInt(line[j]);
                }
            }
        }*/

/*        https://stackoverflow.com/questions/2707357/how-to-create-dynamic-two-dimensional-array-in-java
        https://howtodoinjava.com/java/string/java-string-split-example/
        https://stackoverflow.com/questions/13512912/print-array-in-the-log-cat-android
        https://stackoverflow.com/questions/11377004/how-to-retrieve-2d-array-from-xml-string-resource-for-android*/

/*        loadedTiles = new int[][]{
                {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,4},
                {3,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,4},
                {3,2,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                {3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,4},
                {3,2,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,2,4},
                {3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                {3,0,0,0,0,0,3,2,2,2,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                {3,2,4,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,2,2,2,2,4},
                {3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                {3,0,0,0,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,5,4},
                {3,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,6,0,5,4},
                {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,4},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };*/
    }
}
