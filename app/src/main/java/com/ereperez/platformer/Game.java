package com.ereperez.platformer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ereperez.platformer.entities.Entity;
import com.ereperez.platformer.input.InputManager;
import com.ereperez.platformer.levels.GameLevel;
import com.ereperez.platformer.levels.LevelManager;
import com.ereperez.platformer.utils.BitmapPool;

import java.util.ArrayList;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    public static final String TAG = "Game";
    static int STAGE_WIDTH = GameSettings.STAGE_WIDTH;
    static int STAGE_HEIGHT = GameSettings.STAGE_HEIGHT;
    private static final float METERS_TO_SHOW_X = GameSettings.METERS_TO_SHOW_X;
    private static final float METERS_TO_SHOW_Y = GameSettings.METERS_TO_SHOW_Y; //calculated at runtime!
    private static final int BG_COLOR = Color.rgb(GameSettings.COLOR_R, GameSettings.COLOR_G, GameSettings.COLOR_B);
    private static final double NANOS_TO_SECONDS = 1.0 / 1000000000;
    private Thread gameThread = null;
    private volatile boolean isRunning = false;
    private static boolean playStartSound = true;
    private SurfaceHolder holder = null;
    private final Paint paint = new Paint();
    private Canvas canvas = null;
    private final Matrix transform = new Matrix();
    private LevelManager level = null;
    private InputManager controls = new InputManager();
    private Viewport camera = null;
    public final ArrayList<Entity> visibleEntities = new ArrayList<>();
    public BitmapPool pool = null;
    public Jukebox jukebox = null;
    private GameHUD gameHUD = null;
    public int coinAmount = 0;
    private boolean gameOver = false;

    public Game(Context context) {
        super(context);
        ctxInit(context);
        init();
    }
    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctxInit(context);
        init();
    }
    public Game(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctxInit(context);
        init();
    }
    public Game(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ctxInit(context);
        init();
    }

    private void ctxInit(Context context){
        jukebox = new Jukebox(context);
    }

    private void init(){
        final int TARGET_HEIGHT = STAGE_HEIGHT;
        final int actualHeight = getScreenHeight();
        final float ratio = (TARGET_HEIGHT >= actualHeight) ? 1 : (float) TARGET_HEIGHT / actualHeight;
        STAGE_WIDTH = (int) (ratio * getScreenWidth());
        STAGE_HEIGHT = TARGET_HEIGHT;
        camera = new Viewport(STAGE_WIDTH, STAGE_HEIGHT, METERS_TO_SHOW_X, METERS_TO_SHOW_Y);
        Log.d(TAG, camera.toString());
        Log.d(TAG, "Game created!");

        Entity.game = this;
        pool = new BitmapPool(this);
        level = new LevelManager(new GameLevel(), pool);
        gameHUD = new GameHUD();
        coinAmount = level.coinAmount;

        holder = getHolder();
        holder.addCallback(this);
        holder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);

        Log.d(TAG, "Resolution: " + STAGE_WIDTH + " : " + STAGE_HEIGHT);
    }

    public InputManager getControls(){
        return controls;
    }

    public void setControls(final InputManager mControls){
        controls.onPause();
        controls.onStop();
        controls = mControls;
        controls.onStart();
    }

    public float getWorldHeight (){ return level.levelHeight; }
    public float getWorldWidth (){ return level.levelWidth; }

    public int worldToScreenX(float worldDistance){ return (int) (worldDistance * camera.getPixelsPerMeterX()); }
    public int worldToScreenY(float worldDistance){ return (int) (worldDistance * camera.getPixelsPerMeterY()); }

    public float screenToWorldX(float pixelDistance){ return pixelDistance / camera.getPixelsPerMeterX(); }
    public float screenToWorldY(float pixelDistance){ return pixelDistance / camera.getPixelsPerMeterY(); }

    public static int getScreenWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void onGameEvent(GameEvent gameEvent, Entity e /*can be null!*/) {
        jukebox.playSoundForGameEvent(gameEvent);
    }

    @Override
    public void run() {
        long lastFrame = System.nanoTime();
        while (isRunning){
            final double deltaTime = ((System.nanoTime() - lastFrame) * NANOS_TO_SECONDS);
            lastFrame = System.nanoTime();
            update(deltaTime);
            buildVisibleSet();
            render(camera, visibleEntities);
            checkGameOver();
            if (playStartSound){
                onGameEvent(GameEvent.GameStart, null);
                playStartSound = false;
            }
        }
    }

    private void update(final double dt){
        controls.update((float) dt);
        camera.lookAt(level.player);//look at % of distance of current camera and player - easing lurk
        level.update(dt);
        checkPlayerHealth();
    }

    private void checkPlayerHealth(){
        if (level.player.health < 1){
            onGameEvent(GameEvent.GameOver, null);
            gameOver = true;
        }
    }

    private void checkGameOver(){
        if (gameOver){
            try {
                Thread.sleep(2000);//waits 2sec before resetting game
            } catch (InterruptedException e) {
                Log.d(TAG, Log.getStackTraceString(e.getCause()));
            }
            level = null;
            init();
            gameOver = false;
            playStartSound = true;
        }
    }

    private void buildVisibleSet(){
        visibleEntities.clear();
        for (final Entity e : LevelManager.entities){
            if (camera.inView(e)){
                visibleEntities.add(e);
            }
        }
    }

    private static final Point position = new Point();
    private void render(final Viewport camera, final ArrayList<Entity> visibleEntities){
        if (!lockCanvas()) {
            return;
        }
        try {
            canvas.drawColor(BG_COLOR); //clear the screen
            for (final Entity e : visibleEntities){
                transform.reset();
                camera.worldToScreen(e, position);
                transform.postTranslate(position.x, position.y);
                e.render(canvas, transform, paint);
            }
            gameHUD.renderHUD(level.player.health, level.player.coinsCollected, coinAmount, canvas, paint);
            if (gameOver){
                gameHUD.gameOver();
            }
        }finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private boolean lockCanvas() {
        if(!holder.getSurface().isValid()){
            return false;
        }
        canvas = holder.lockCanvas();
        return (canvas != null);
    }

    //Below here is executed on UI thread

    protected void onResume(){
        Log.d(TAG, "onResume");
        isRunning = true;
        controls.onResume();
        jukebox.resumeBgMusic();
        gameThread = new Thread(this);
    }

    protected void onPause(){
        Log.d(TAG, "onPause");
        isRunning = false;
        controls.onPause();
        jukebox.pauseBgMusic();
        while (true){ //Alternative: gameThread.getState() == Thread.State.TERMINATED
            try {
                gameThread.join();
                return;
            }catch (InterruptedException e) {
                Log.d(TAG, Log.getStackTraceString(e.getCause()));
            }
        }
    }

    protected void onDestroy(){
        Log.d(TAG, "onDestroy");
        gameThread = null;
        if (level != null){
            level.destroy(); //should empty the bitmap pool
            level = null;
        }
        controls = null;
        Entity.game = null;
        if (pool != null){
            pool.empty(); //safe, but redundant, the level manager already destroys it
        }
        holder.removeCallback(this);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated!");
    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {
        Log.d(TAG, "surfaceChanged!");
        Log.d(TAG, "\t Width: " + width + " Height: " + height);
        if (gameThread != null && isRunning){
            Log.d(TAG, "GameThread started!");
            gameThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed!");
    }
}
