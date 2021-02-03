package com.ereperez.platformer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Edwin on 01,December,2020
 */
public class Jukebox {
    public static final String TAG = "Jukebox";
    private static final float LEFT_VOLUME = GameSettings.LEFT_VOLUME; //TODO rename to SFX_
    private static final float RIGHT_VOLUME = GameSettings.RIGHT_VOLUME;
    private static final float RATE_VOLUME = GameSettings.RATE_VOLUME;
    private static final int PRIORITY_VOLUME = GameSettings.PRIORITY_VOLUME;
    private static final int LOOP_VOLUME = GameSettings.LOOP_VOLUME;
    private static final float DEFAULT_MUSIC_VOLUME = 0.5f;
    private static final String SOUNDS_PREF_KEY = "sounds_pref_key";
    private static final String MUSIC_PREF_KEY = "music_pref_key";

    /*    static int CRASH = 0;
    static int BOOST = 1;
    static int GAME_START = 2;
    static int GAME_OVER = 3;*/

    SoundPool soundPool = null;
    MediaPlayer mBgPlayer = null;
    private static final int MAX_STREAMS = 4; //TODO change
    private HashMap soundsMap;
    private boolean soundEnabled = true; //TODO: make prefs
    private boolean musicEnabled = true; //TODO: make pref
    private final Context context;

    public Jukebox(Context mContext) {
        context = mContext;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        soundEnabled = prefs.getBoolean(SOUNDS_PREF_KEY, true); //TODO toggle music on/off in UI
        musicEnabled = prefs.getBoolean(MUSIC_PREF_KEY, true);
        loadIfNeeded();
    }
    private void loadIfNeeded(){
        if(soundEnabled){
            loadSounds();
        }
        if(musicEnabled){
            loadMusic();
        }
    }

    //TODO add accessor method for getSoundStatus and getMusicStatus

    public void toggleSoundStatus(){
        soundEnabled = !soundEnabled;
        if(soundEnabled){
            loadSounds();
        }else{
            unloadSounds();
        }
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(SOUNDS_PREF_KEY, soundEnabled)
                .apply();
    }

    public void toggleMusicStatus(){
        musicEnabled = !musicEnabled;
        if(musicEnabled){
            loadMusic();
        }else{
            unloadMusic();
        }
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(MUSIC_PREF_KEY, musicEnabled) //soundEnabled
                .apply();
    }

    public void playSoundForGameEvent(GameEvent event){
        if(!soundEnabled){return;}
/*        final float leftVolume = DEFAULT_SFX_VOLUME;
        final float rightVolume = DEFAULT_SFX_VOLUME;*/
        final int priority = 1;
        final int loop = 0; //-1 loop forever, 0 play once
        final float rate = 1.0f; //TODO change to the gamesettings
        final Integer soundID = (Integer) soundsMap.get(event);
        if(soundID != null){
            soundPool.play(soundID, 1, 1, priority, loop, rate);
            //soundPool.play(soundID, LEFT_VOLUME, RIGHT_VOLUME, priority, loop, rate);
            //soundPool.play(soundID, leftVolume, rightVolume, priority, loop, rate);
        }
    }

    @SuppressWarnings("deprecation")
    private void createSoundPool() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(MAX_STREAMS)
                    .build();
        }
    }

    private void loadEventSound(final GameEvent event, final String fileName){
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(fileName);
            int soundId = soundPool.load(afd, 1);
            soundsMap.put(event, soundId);
            Log.d(TAG, "soundMap: " + soundId);
        }catch(IOException e){
            Log.e(TAG, "loadEventSound: error loading sound " + e.toString());
        }
    }

    private void loadSounds(){
        createSoundPool();
        soundsMap = new HashMap();
        loadEventSound(GameEvent.GameOver, "sfx/gameOver.wav");
        loadEventSound(GameEvent.GameStart, "sfx/gameStart.wav");
        loadEventSound(GameEvent.Jump, "sfx/jump.wav");
        loadEventSound(GameEvent.CoinPickup, "sfx/pickupCoin.wav");
        loadEventSound(GameEvent.SpikeDamage, "sfx/takeDamage.wav");
    }

    private void unloadSounds(){
        if(soundPool != null) {
            soundPool.release();
            soundPool = null;
            soundsMap.clear();
        }
    }

    private void loadMusic(){
        try{
            mBgPlayer = new MediaPlayer();
            AssetFileDescriptor afd = context
                    .getAssets().openFd("sfx/backgroundMusic.mp3");
            mBgPlayer.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength());
            mBgPlayer.setLooping(true);
            mBgPlayer.setVolume(DEFAULT_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
            mBgPlayer.prepare();
        }catch(IOException e){
            mBgPlayer = null;
            musicEnabled = false;
            Log.e(TAG, "loadMusic: error loading music " + e.toString());
        }
    }

    private void unloadMusic(){
        if(mBgPlayer != null) {
            mBgPlayer.stop();
            mBgPlayer.release();
        }
    }

    public void pauseBgMusic(){
        if(!musicEnabled){ return; }
        mBgPlayer.pause();
    }
    public void resumeBgMusic(){
        if(!musicEnabled){ return; }
        mBgPlayer.start();
    }
}
