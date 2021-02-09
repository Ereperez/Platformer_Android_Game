package com.ereperez.platformer;

import android.content.Context;
import android.content.res.Resources;

/**
 * Loads values from res/values/game_settings and strings
 */
public class GameSettings {
    public static int STAGE_WIDTH;
    public static int STAGE_HEIGHT;
    public static float METERS_TO_SHOW_X;
    public static float METERS_TO_SHOW_Y;
    public static String COINS_COLLECTED_HUD;
    public static String COINS_LEFT_HUD;
    public static String HEALTH_HUD;
    public static String GAME_OVER_HUD;
    public static String GAME_OVER_RESTART_HUD;
    public static float FULLSCREEN_TEXT_SIZE;
    public static int HUD_TEXT_OFFSET;
    public static float HUD_TEXT_SIZE;

    public static float SFX_LEFT_VOLUME;
    public static float SFX_RIGHT_VOLUME;
    public static float SFX_RATE;
    public static int SFX_PRIORITY;
    public static int SFX_LOOP;
    public static float DEFAULT_MUSIC_VOLUME;
    public static int MAX_STREAMS;

    public static float MAX_DELTA;
    public static float ENTITY_WIDTH;
    public static float ENTITY_HEIGHT;
    public static float DEFAULT_DIMENSION;
    public static float PLAYER_DIMENSION;
    public static float GRAVITY;
    public static float DEGREES_PER_RADIAN;
    public static float MAX_ANGLE;
    public static float SHAKE_THRESHOLD;
    public static long COOLDOWN;
    public static int AXIS_COUNT;

    public static int PLAYER_HEALTH;
    public static int PLAYER_BLINK_COLOR;
    public static int PLAYER_IMMUNITY_TIME;
    public static float PLAYER_SPEED;
    public static float PLAYER_JUMP_FORCE;
    public static float MIN_TURN_INPUT;
    public static int LEFT_DIRECTION;
    public static int RIGHT_DIRECTION;

    public static int LEVEL_ROWS;
    public static int LEVEL_COLUMNS;

    public static int COLOR_R;
    public static int COLOR_B;
    public static int COLOR_G;

    public static int BACKGROUND_TILE_INT;
    public static int PLAYER_TILE_INT;
    public static int SPIKES_TILE_INT;
    public static int COINS_TILE_INT;
    public static int GROUND_TILE_INT;
    public static int GROUND_LEFT_TILE_INT;
    public static int GROUND_RIGHT_TILE_INT;
    public static String NULLSPRITE_TILE;
    public static String BACKGROUND_TILE;
    public static String PLAYER_TILE;
    public static String SPIKES_TILE;
    public static String COINS_TILE;
    public static String GROUND_TILE;
    public static String GROUND_LEFT_TILE;
    public static String GROUND_RIGHT_TILE;

    GameSettings(Context ctx) {
        loadSettings(ctx);
    }

    public void loadSettings(Context ctx){
        Resources res = ctx.getResources();

        STAGE_WIDTH = res.getInteger(R.integer.stage_width);
        STAGE_HEIGHT = res.getInteger(R.integer.stage_height);

        PLAYER_HEALTH = res.getInteger(R.integer.player_health);

        PLAYER_BLINK_COLOR = res.getInteger(R.integer.player_blink_color);
        PLAYER_IMMUNITY_TIME = res.getInteger(R.integer.player_immunity_time);

        COINS_COLLECTED_HUD = res.getString(R.string.coins_collected_hud);
        COINS_LEFT_HUD = res.getString(R.string.coins_total_hud);
        HEALTH_HUD = res.getString(R.string.health_hud);
        GAME_OVER_HUD = res.getString(R.string.game_over_hud);
        GAME_OVER_RESTART_HUD = res.getString(R.string.game_over_restart_hud);
        FULLSCREEN_TEXT_SIZE = Float.parseFloat(res.getString(R.string.fullscreen_text_size));
        HUD_TEXT_OFFSET = res.getInteger(R.integer.hud_text_offset);
        HUD_TEXT_SIZE = Float.parseFloat(res.getString(R.string.hud_text_size));

        SFX_LEFT_VOLUME = Float.parseFloat(res.getString(R.string.sfx_left_volume));
        SFX_RIGHT_VOLUME = Float.parseFloat(res.getString(R.string.sfx_right_volume));
        SFX_RATE = Float.parseFloat(res.getString(R.string.sfx_rate));
        SFX_PRIORITY = res.getInteger(R.integer.sfx_priority);
        SFX_LOOP = res.getInteger(R.integer.sfx_loop);
        DEFAULT_MUSIC_VOLUME = Float.parseFloat(res.getString(R.string.default_music_volume));
        MAX_STREAMS = res.getInteger(R.integer.max_streams);

        MAX_DELTA = Float.parseFloat(res.getString(R.string.max_delta));
        ENTITY_WIDTH = Float.parseFloat(res.getString(R.string.entity_width));
        ENTITY_HEIGHT = Float.parseFloat(res.getString(R.string.entity_height));
        DEFAULT_DIMENSION = Float.parseFloat(res.getString(R.string.default_dimension));
        PLAYER_DIMENSION = Float.parseFloat(res.getString(R.string.player_dimension));
        GRAVITY = Float.parseFloat(res.getString(R.string.gravity));

        PLAYER_SPEED = Float.parseFloat(res.getString(R.string.player_speed));
        PLAYER_JUMP_FORCE = Float.parseFloat(res.getString(R.string.player_jump_force));
        MIN_TURN_INPUT = Float.parseFloat(res.getString(R.string.min_input_to_turn));
        LEFT_DIRECTION = res.getInteger(R.integer.left_direction);
        RIGHT_DIRECTION = res.getInteger(R.integer.right_direction);

        DEGREES_PER_RADIAN = Float.parseFloat(res.getString(R.string.degrees_per_radian));
        MAX_ANGLE = Float.parseFloat(res.getString(R.string.max_angle));
        SHAKE_THRESHOLD = Float.parseFloat(res.getString(R.string.shake_threshold));
        COOLDOWN = Long.parseLong(res.getString(R.string.cooldown));
        AXIS_COUNT = res.getInteger(R.integer.axis_count);

        LEVEL_ROWS = res.getInteger(R.integer.level_rows);
        LEVEL_COLUMNS = res.getInteger(R.integer.level_columns);

        METERS_TO_SHOW_X = Float.parseFloat(res.getString(R.string.meters_to_show_x));
        METERS_TO_SHOW_Y = Float.parseFloat(res.getString(R.string.meters_to_show_y));

        COLOR_R = res.getInteger(R.integer.color_r);
        COLOR_B = res.getInteger(R.integer.color_b);
        COLOR_G = res.getInteger(R.integer.color_g);

        BACKGROUND_TILE_INT = res.getInteger(R.integer.background_tile_int);
        PLAYER_TILE_INT = res.getInteger(R.integer.player_tile_int);
        GROUND_TILE_INT = res.getInteger(R.integer.ground_tile_int);
        GROUND_LEFT_TILE_INT = res.getInteger(R.integer.ground_left_tile_int);
        GROUND_RIGHT_TILE_INT = res.getInteger(R.integer.ground_right_tile_int);
        SPIKES_TILE_INT = res.getInteger(R.integer.spikes_tile_int);
        COINS_TILE_INT = res.getInteger(R.integer.coins_tile_int);

        BACKGROUND_TILE = res.getString(R.string.background_tile);
        NULLSPRITE_TILE = res.getString(R.string.nullsprite_tile);
        PLAYER_TILE = res.getString(R.string.player_tile);
        SPIKES_TILE = res.getString(R.string.spikes_tile);
        COINS_TILE = res.getString(R.string.coins_tile);
        GROUND_TILE = res.getString(R.string.ground_tile);
        GROUND_LEFT_TILE = res.getString(R.string.ground_left_tile);
        GROUND_RIGHT_TILE = res.getString(R.string.ground_right_tile);
    }
}
