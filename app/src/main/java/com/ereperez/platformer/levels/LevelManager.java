package com.ereperez.platformer.levels;

import com.ereperez.platformer.entities.Coins;
import com.ereperez.platformer.entities.EnemySpikes;
import com.ereperez.platformer.entities.Entity;
import com.ereperez.platformer.entities.Player;
import com.ereperez.platformer.entities.StaticEntity;
import com.ereperez.platformer.utils.BitmapPool;

import java.util.ArrayList;

public class LevelManager {
    public int levelHeight = 0;
    public int levelWidth = 0;
    public final static ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    private final ArrayList<Entity> entitiesToRemove = new ArrayList<>();
    public Player player = null;
    private BitmapPool pool = null;
    public int coinAmount = 0;

    public LevelManager(final LevelData map, final BitmapPool mPool) {
        pool = mPool;
        loadMapAssets(map);
    }

    public void update(final double dt){
        for (Entity e : entities){
            e.update(dt);
        }
        checkCollisions();
        addAndRemoveEntities();
    }

    private void checkCollisions(){
        final int count = entities.size();
        Entity a, b;
        for (int i = 0; i < count-1; i++){
            a = entities.get(i);
            for (int j = i+1; j < count; j++){
                b = entities.get(j);
                if (a.isColliding(b)){
                    a.onCollision(b);
                    b.onCollision(a);
                    if (a.getClass().equals(Coins.class) && b.equals(player) || a.equals(player) && b.getClass().equals(Coins.class)){
                        removeEntity(b);
                    }
                }
            }
        }
    }

    private void loadMapAssets(final LevelData map){
        cleanup();
        levelHeight = map.mHeight;
        levelWidth = map.mWidth;
        for (int y = 0; y < levelHeight; y++){
            final int[] row = map.getRow(y);
            for (int x = 0; x < row.length; x++){
                final int tileID = row[x];
                if(tileID == LevelData.NO_TILE){ continue; }
                final String spriteName = map.getSpriteName(tileID);
                createEntity(spriteName, x, y);
            }
        }
    }

    private void createEntity(final String spriteName, final int xPos, final int yPos){
        Entity e = null;
        if (spriteName.equalsIgnoreCase(LevelData.PLAYER)){
            e = new Player(spriteName, xPos, yPos);
            if (player == null){
                player = (Player) e;
            }
        }else if (spriteName.equalsIgnoreCase(LevelData.SPIKES)){
            e = new EnemySpikes(spriteName, xPos, yPos);
        }else if (spriteName.equalsIgnoreCase(LevelData.COINS)){
            e = new Coins(spriteName, xPos, yPos);
            coinAmount++;
        }else{
            e = new StaticEntity(spriteName, xPos, yPos);
        }
        addEntity(e);
    }

    private void addAndRemoveEntities(){
        for (Entity e : entitiesToRemove){
            entities.remove(e);
        }
        for (Entity e : entitiesToAdd){
            entities.add(e);
        }
        entitiesToRemove.clear();
        entitiesToAdd.clear();
    }

    public void addEntity(final Entity e){
        if (e != null){ entitiesToAdd.add(e); }
    }

    public void removeEntity(final Entity e){
        if (e != null){ entitiesToRemove.add(e); }
    }

    private void cleanup(){
        addAndRemoveEntities();
        for (Entity e : entities){
            e.destroy();
        }
        entities.clear();
        player = null;
        pool.empty();
    }

    public void destroy(){
        cleanup();
    }
}
