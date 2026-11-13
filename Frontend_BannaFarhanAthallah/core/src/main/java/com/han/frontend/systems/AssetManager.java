package com.han.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static AssetManager instance;
    private Map<String, TextureRegion> textureRegionMap;
    private Map<String, Animation<TextureRegion>> animationMap;
    private Map<String, Texture> textureMap;

    // Maps for audio
    private Map<String, Sound> sfxMap;
    private Map<String, Music> bgmMap;
    private Music currentBgm;
    private float bgmVolume = 0.4f;
    private float sfxVolume = 0.125f;

    private AssetManager() {
        textureRegionMap = new HashMap<>();
        animationMap = new HashMap<>();
        textureMap = new HashMap<>();
        sfxMap = new HashMap<>();
        bgmMap = new HashMap<>();
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public void init() {
        // Reimu Animations (player.png: 32x48 per tile, 8 columns)
        // Row 0: Idle (8 frames), Row 1: Left loop (Cols 0..3, 4 frames), Row 2: Right loop (Cols 0..3, 4 frames)
        registerAnimationFromSheet("player_idle",       "player.png", 32, 48, 0, 0, 8, 0.125f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("player_left",       "player.png", 32, 48, 1, 0, 4, 0.12f,  Animation.PlayMode.LOOP);
        registerAnimationFromSheet("player_right",      "player.png", 32, 48, 2, 0, 4, 0.12f,  Animation.PlayMode.LOOP);
        registerAnimationFromSheet("player_left_loop",  "player.png", 32, 48, 1, 0, 4, 0.12f,  Animation.PlayMode.LOOP);
        registerAnimationFromSheet("player_right_loop", "player.png", 32, 48, 2, 0, 4, 0.12f,  Animation.PlayMode.LOOP);

        // Boss Rumia Animations (rumia.png: 64x64 per tile, 4 columns)
        // Row 0: Idle (4 frames), Row 1: Left (4 frames, REVERSED), Row 2: Right (4 frames, NORMAL)
        registerAnimationFromSheet("boss_idle",       "rumia.png", 64, 64, 0, 0, 4, 0.2f,  Animation.PlayMode.LOOP);
        registerAnimationFromSheet("boss_left",       "rumia.png", 64, 64, 1, 0, 4, 0.15f, Animation.PlayMode.REVERSED);
        registerAnimationFromSheet("boss_right",      "rumia.png", 64, 64, 2, 0, 4, 0.15f, Animation.PlayMode.NORMAL);
        registerAnimationFromSheet("boss_left_loop",  "rumia.png", 64, 64, 1, 0, 4, 0.15f, Animation.PlayMode.REVERSED);
        registerAnimationFromSheet("boss_right_loop", "rumia.png", 64, 64, 2, 0, 4, 0.15f, Animation.PlayMode.NORMAL);

        // Stage 1 Fairy (fairy.png: 32x32 per tile)
        registerAnimationFromSheet("fairy_idle_red",  "fairy.png", 32, 32, 1, 8, 0.125f);
        registerAnimationFromSheet("fairy_idle_blue", "fairy.png", 32, 32, 0, 8, 0.125f);
        registerAnimationFromSheet("fairy_idle",      "fairy.png", 32, 32, 1, 8, 0.125f);

        // Bullets:
        // Authentic Touhou 6 Ofuda Amulets from amulet_reimu.png (16x16)
        // Col 0: Straight Amulet, Col 1: Homing Amulet
        registerRegionFromSheet("bullet_amulet",        "amulet_reimu.png", 16, 16, 0, 0);
        registerRegionFromSheet("bullet_amulet_homing", "amulet_reimu.png", 16, 16, 1, 0);

        // Enemy Danmaku from bullets_small.png (16x16 per tile)
        registerRegionFromSheet("bullet_danmaku",        "bullets_small.png", 16, 16, 2, 3); // Default / Blue
        registerRegionFromSheet("bullet_danmaku_blue",   "bullets_small.png", 16, 16, 2, 3);
        registerRegionFromSheet("bullet_danmaku_red",    "bullets_small.png", 16, 16, 2, 6);
        registerRegionFromSheet("bullet_danmaku_purple", "bullets_small.png", 16, 16, 2, 4);
        registerRegionFromSheet("bullet_danmaku_orange", "bullets_small.png", 16, 16, 2, 15);
        registerRegionFromSheet("bullet_danmaku_green",  "bullets_small.png", 16, 16, 2, 10);

        // Correct items.png column coordinates (16x16 per cell):
        registerRegionFromSheet("item_power", "items.png", 16, 16, 0, 0);
        registerRegionFromSheet("item_point", "items.png", 16, 16, 0, 1);
        registerRegionFromSheet("item_bomb",  "items.png", 16, 16, 0, 3);
        registerRegionFromSheet("item_life",  "items.png", 16, 16, 0, 5);
        registerBgm("bgm", "audio/bgm.ogg");
        registerSfx("powerup", "audio/se_powerup.wav");
        registerSfx("player_shoot", "audio/se_plst00.wav");
        registerSfx("player_bullet_collision", "audio/se_enep00.wav");
        registerSfx("enemy_dies", "audio/se_enep01.wav");
        registerSfx("item_pickup", "audio/se_item00.wav");
        registerSfx("shoot", "audio/se_tan00.wav");
    }

    // 1. O(1) Dynamic Flyweight Lookups (No switch statements)
    public TextureRegion getTextureRegion(String key) {
        return textureRegionMap.get(key);
    }

    public TextureRegion getRegion(String key) {
        return getTextureRegion(key);
    }

    public Animation<TextureRegion> getAnimation(String key) {
        return animationMap.get(key);
    }

    public Sound loadSfx(String filename) {
        Sound sfx = sfxMap.get(filename);
        if (sfx != null) {
            return sfx;
        }
        if (Gdx.audio == null || Gdx.files == null || !Gdx.files.internal(filename).exists()) {
            return null;
        }
        sfx = Gdx.audio.newSound(Gdx.files.internal(filename));
        sfxMap.put(filename, sfx);
        return sfx;
    }

    public Music loadBgm(String filename) {
        Music bgm = bgmMap.get(filename);
        if (bgm != null) {
            return bgm;
        }
        if (Gdx.audio == null || Gdx.files == null || !Gdx.files.internal(filename).exists()) {
            return null;
        }
        bgm = Gdx.audio.newMusic(Gdx.files.internal(filename));
        bgmMap.put(filename, bgm);
        return bgm;
    }

    public void registerSfx(String key, String filename) {
        Sound sfx = loadSfx(filename);
        if (sfx != null) {
            sfxMap.put(key, sfx);
        }
    }

    public void registerBgm(String key, String filename) {
        Music bgm = loadBgm(filename);
        if (bgm != null) {
            bgmMap.put(key, bgm);
        }
    }

    public Sound getSfx(String key) {
        return sfxMap.get(key);
    }

    public Music getBgm(String key) {
        return bgmMap.get(key);
    }

    public void playSfx(String key) {
        Sound sfx = sfxMap.get(key);
        if (sfx != null) {
            sfx.play(sfxVolume);
        }
    }

    public void playBgm(String key, Boolean looping) {
        Music bgm = loadBgm(key);
        if (currentBgm != null && currentBgm != bgm) {
            currentBgm.stop();
        }
        currentBgm = bgm;
        if (currentBgm != null) {
            currentBgm.setLooping(looping);
            currentBgm.setVolume(bgmVolume);
            currentBgm.play();
        }
    }

    public void setSfxVolume(float volume) {
        sfxVolume = Math.max(0.0f, Math.min(1.0f, volume));
    }

    public void setBgmVolume(float volume) {
        bgmVolume = Math.max(0.0f, Math.min(1.0f, volume));
        if (currentBgm != null) {
            currentBgm.setVolume(bgmVolume);
        }
    }

    // 2. Generic Registration Methods
    public Texture loadTexture(String filename) {
        if (!textureMap.containsKey(filename)) {
            if (Gdx.files != null && Gdx.files.internal(filename).exists()) {
                textureMap.put(filename, new Texture(Gdx.files.internal(filename)));
            } else {
                return null;
            }
        }
        return textureMap.get(filename);
    }

    public void registerRegion(String key, TextureRegion region) {
        textureRegionMap.put(key, region);
    }

    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            textureRegionMap.put(key, grid[row][col]);
        }
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP);
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);

            TextureRegion[] frames = new TextureRegion[numFrames];
            for (int i = 0; i < numFrames; i++) {
                frames[i] = grid[row][startCol + i];
            }

            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);

            animationMap.put(key, anim);
            textureRegionMap.put(key, frames[0]);
        }
    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration, boolean flipX, boolean flipY) {
        registerFlippedAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP, flipX, flipY);
    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode, boolean flipX, boolean flipY) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);

            TextureRegion[] frames = new TextureRegion[numFrames];
            for (int i = 0; i < numFrames; i++) {
                frames[i] = new TextureRegion(grid[row][startCol + i]);
                frames[i].flip(flipX, flipY);
            }

            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);

            animationMap.put(key, anim);
            textureRegionMap.put(key, frames[0]);
        }
    }

    public void dispose() {
        for (Texture tex : textureMap.values()) {
            if (tex != null) tex.dispose();
        }
        for (Sound sound : sfxMap.values()) {
            if (sound != null) sound.dispose();
        }
        for (Music music : bgmMap.values()) {
            if (music != null) music.dispose();
        }
        sfxMap.clear();
        bgmMap.clear();
        textureMap.clear();
        textureRegionMap.clear();
        animationMap.clear();
    }
}
