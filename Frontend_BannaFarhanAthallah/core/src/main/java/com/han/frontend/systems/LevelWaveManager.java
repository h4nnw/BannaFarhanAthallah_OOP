package com.han.frontend.systems;

import com.han.frontend.objects.GameObject;
import com.han.frontend.objects.Player;
import com.han.frontend.objects.enemies.Boss;
import com.han.frontend.objects.enemies.Fairy;
import com.han.frontend.objects.items.Item;
import com.han.frontend.objects.items.ItemType;
import com.han.frontend.states.FairyWaveState;
import com.han.frontend.states.WaveState;

import java.util.ArrayList;
import java.util.List;

public class LevelWaveManager {
    private List<GameObject> entities;
    private BulletManager bulletManager;
    private Player player;
    private Boss boss;
    private Fairy fairy;
    private List<Fairy> fairies = new ArrayList<>();

    // TODO: Add the required field according to the UML!
    private WaveState currentState;

    // ====================================================
    // Constructor
    // ====================================================
    public LevelWaveManager(List<GameObject> entities, BulletManager bulletManager, Player player) {
        this.entities = entities;
        this.bulletManager = bulletManager;
        this.player = player;
        setState(new FairyWaveState());
    }

    public LevelWaveManager(List<GameObject> entities, BulletManager bulletManager, Boss boss, Fairy fairy) {
        this.entities = entities;
        this.bulletManager = bulletManager;
        this.boss = boss;
        this.fairy = fairy;
        if (fairy != null) {
            fairies.add(fairy);
        }
        setState(new FairyWaveState());
    }

    // ====================================================
    // Core Methods
    // ====================================================

    public LevelWaveManager(){}

    public void setState(WaveState newState) {
        this.currentState = newState;
        if (this.currentState != null) {
            this.currentState.onEnter(this);
        }
    }

    public void update(float delta) {
        if (currentState != null) {
            currentState.update(this, delta);
        }
    }

    // ====================================================
    // Helper
    // ====================================================
    public Fairy spawnFairy(float x, float y, String name, int hp) {
        Fairy f = EntityFactory.createFairy(x, y, name, hp, "fairy_idle_blue");
        if (entities != null && !entities.contains(f)) {
            entities.add(f);
        }
        if (!fairies.contains(f)) {
            fairies.add(f);
        }
        this.fairy = f;
        return f;
    }

    public Boss spawnBoss(float x, float y, String name, int hp) {
        Boss b = EntityFactory.createBoss(x, y, name, hp);
        if (entities != null && !entities.contains(b)) {
            entities.add(b);
        }
        this.boss = b;
        return b;
    }

    public Item spawnItem(float x, float y, ItemType type) {
        Item item = EntityFactory.createItem(x, y, type);
        if (entities != null && !entities.contains(item)) {
            entities.add(item);
        }
        return item;
    }

    public boolean areAllFairiesDefeated() {
        if (fairies == null || fairies.isEmpty()) return true;
        for (Fairy f : fairies) {
            if (f != null && !f.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    // ====================================================
    // Getter
    // ====================================================
    public List<GameObject> getEntities() { return entities; }
    public BulletManager getBulletManager() { return bulletManager; }
    public Player getPlayer() { return player; }
    public Boss getBoss() { return boss; }
    public Fairy getFairy() { return fairy; }
    public List<Fairy> getFairies() { return fairies; }
    // TODO: Add getter for the current state
    public WaveState getCurrentState() { return currentState; }
}
