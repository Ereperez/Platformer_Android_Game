package com.ereperez.platformer;

import android.content.Context;
import android.content.res.Resources;

public class GameSettings {
    public static int STAGE_WIDTH;
    public static int STAGE_HEIGHT;
    public static int ENEMY_COUNT_EASY;
    public static int ENEMY_COUNT_NORMAL;
    public static int ENEMY_COUNT_HARD;
    public static int METEOR_COUNT;
    public static int STAR_COUNT;
    public static int PLAYER_HEIGHT;
    public static int STARTING_POSITION;
    public static int STARTING_HEALTH;
    public static float ACC;
    public static float MIN_VEL;
    public static float MAX_VEL;
    public static float GRAVITY;
    public static float LIFT;
    public static float DRAG;
    public static int STAR_COLOR1;
    public static int STAR_COLOR2;
    public static int STAR_COLOR3;
    public static int STAR_COLOR4;
    public static int PLAYER_BLINK_COLOR;
    public static int PLAYER_IMMUNITY_TIME;

    public static int ENEMY_HEIGHT;
    public static int METEOR_HEIGHT;
    public static float METEOR_SPEED;
    public static int ENEMY_SPAWN_OFFSET;
    public static float STAR_SPEED;
    public static int STAR_MAX_RADIUS;
    public static int STAR_MIN_RADIUS;

    public static String EASY;
    public static String HARD;
    public static String DIFFICULTY_HUD;
    public static String HEALTH_HUD;
    public static String DISTANCE_HUD;
    public static String GAME_OVER_HUD;
    public static String GAME_OVER_RESTART_HUD;
    public static String START_SCREEN_HUD;
    public static float HUD_TEXT_SIZE;
    public static float FULLSCREEN_TEXT_SIZE;
    public static int HUD_TEXT_OFFSET;

    public static float LEFT_VOLUME;
    public static float RIGHT_VOLUME;
    public static float RATE_VOLUME;
    public static int PRIORITY_VOLUME;
    public static int LOOP_VOLUME;

    GameSettings(Context ctx) {
        loadSettings(ctx);
    }

    public void loadSettings(Context ctx){
        Resources res = ctx.getResources();

        STAGE_WIDTH = res.getInteger(R.integer.stage_width);
        STAGE_HEIGHT = res.getInteger(R.integer.stage_height);
        ENEMY_COUNT_EASY = res.getInteger(R.integer.enemy_count_easy);
        ENEMY_COUNT_NORMAL = res.getInteger(R.integer.enemy_count_normal);
        ENEMY_COUNT_HARD = res.getInteger(R.integer.enemy_count_hard);
        METEOR_COUNT = res.getInteger(R.integer.enemy_meteor_count);
        STAR_COUNT = res.getInteger(R.integer.star_count);
        PLAYER_HEIGHT = res.getInteger(R.integer.player_height);
        STARTING_POSITION = res.getInteger(R.integer.starting_position);
        STARTING_HEALTH = res.getInteger(R.integer.starting_health);
        ACC = Float.parseFloat(res.getString(R.string.acc));
        MIN_VEL = Float.parseFloat(res.getString(R.string.min_vel));
        MAX_VEL = Float.parseFloat(res.getString(R.string.max_vel));
        GRAVITY = Float.parseFloat(res.getString(R.string.gravity));
        LIFT = Float.parseFloat(res.getString(R.string.lift));
        DRAG = Float.parseFloat(res.getString(R.string.drag));
        STAR_COLOR1 = res.getInteger(R.integer.star_color1);
        STAR_COLOR2 = res.getInteger(R.integer.star_color2);
        STAR_COLOR3 = res.getInteger(R.integer.star_color3);
        STAR_COLOR4 = res.getInteger(R.integer.star_color4);
        PLAYER_BLINK_COLOR = res.getInteger(R.integer.player_blink_color);
        PLAYER_IMMUNITY_TIME = res.getInteger(R.integer.player_immunity_time);

        ENEMY_HEIGHT = res.getInteger(R.integer.enemy_height);
        METEOR_HEIGHT = res.getInteger(R.integer.meteor_height);
        METEOR_SPEED = Float.parseFloat(res.getString(R.string.meteor_speed));
        ENEMY_SPAWN_OFFSET = res.getInteger(R.integer.enemy_spawn_offset);
        STAR_SPEED = Float.parseFloat(res.getString(R.string.star_speed));
        STAR_MAX_RADIUS = res.getInteger(R.integer.star_max_radius);
        STAR_MIN_RADIUS = res.getInteger(R.integer.star_min_radius);

        EASY = res.getString(R.string.difficulty_easy);
        HARD = res.getString(R.string.difficulty_hard);

        DIFFICULTY_HUD = res.getString(R.string.difficulty_hud);
        HEALTH_HUD = res.getString(R.string.health_hud);
        DISTANCE_HUD = res.getString(R.string.distance_hud);
        GAME_OVER_HUD = res.getString(R.string.game_over_hud);
        GAME_OVER_RESTART_HUD = res.getString(R.string.game_over_restart_hud);
        START_SCREEN_HUD = res.getString(R.string.start_screen_hud);
        HUD_TEXT_SIZE = Float.parseFloat(res.getString(R.string.hud_text_size));
        FULLSCREEN_TEXT_SIZE = Float.parseFloat(res.getString(R.string.fullscreen_text_size));
        HUD_TEXT_OFFSET = res.getInteger(R.integer.hud_text_offset);

        LEFT_VOLUME = Float.parseFloat(res.getString(R.string.left_volume));
        RIGHT_VOLUME = Float.parseFloat(res.getString(R.string.right_volume));
        RATE_VOLUME = Float.parseFloat(res.getString(R.string.rate_volume));
        PRIORITY_VOLUME = res.getInteger(R.integer.priority_volume);
        LOOP_VOLUME = res.getInteger(R.integer.loop_volume);
    }
}
