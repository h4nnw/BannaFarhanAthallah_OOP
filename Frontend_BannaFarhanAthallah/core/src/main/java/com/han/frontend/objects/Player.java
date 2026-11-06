package com.han.frontend.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.han.frontend.objects.bullets.Bullet;
import com.han.frontend.objects.enemies.Enemy;
import com.han.frontend.objects.bullets.BulletType;
import com.han.frontend.objects.items.Item;
import com.han.frontend.objects.items.ItemType;
import com.han.frontend.objects.patterns.ShootingPattern;
import com.han.frontend.systems.AssetManager;
import com.han.frontend.systems.BulletManager;
import com.han.frontend.systems.EntityFactory;
import com.han.frontend.observers.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;
    private int lives = 3;
    private int grazeCount = 0;
    private boolean focused = false;
    private float shootTimer = 0.1f;
    private float shootCooldown = 0.1f;
    private float invulnerableTimer = 0f;
    private int currentDir;
    private ShootingPattern shootingPattern;
    private List<GameObserver> observers = new ArrayList<>();

    public void registerObserver(GameObserver observer) {
        // TODO:
        // 1. Check whether observer is not null AND is not already in the observers list (!observers.contains(observer)).
        // 2. If valid:
        //    - Add observer to the observers list.
        //    - Broadcast all current initial player states to that observer:
        //      \* observer.onScoreChanged(score);
        //      \* observer.onHpChanged(hp);
        //      \* observer.onLivesChanged(lives);
        //      \* observer.onSpellCardsChanged(spellCards);
        //      \* observer.onPowerChanged(power);
        //      \* observer.onGrazeChanged(grazeCount);
        if (observer != null && !observers.contains(observer)){
            observers.add(observer);
            observer.onScoreChanged(score);
            observer.onHpChanged(hp);
            observer.onLivesChanged(lives);
            observer.onSpellCardsChanged(spellCards);
            observer.onPowerChanged(power);
            observer.onGrazeChanged(grazeCount);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    // Notification Broadcast Methods to All Observers (4 points)
// TODO: Create the following 6 private void methods that LOOP THROUGH ALL ELEMENTS IN 'observers'
//       and invoke the corresponding callback method:
// 1. notifyScoreChanged()      -> calls obs.onScoreChanged(score)
// 2. notifyHpChanged()         -> calls obs.onHpChanged(hp)
// 3. notifyLivesChanged()      -> calls obs.onLivesChanged(lives) (Touhou Life System)
// 4. notifySpellCardsChanged() -> calls obs.onSpellCardsChanged(spellCards)
// 5. notifyPowerChanged()      -> calls obs.onPowerChanged(power)
// 6. notifyGrazeChanged()      -> calls obs.onGrazeChanged(grazeCount)
    private void notifyScoreChanged(){
        for (GameObserver obs : observers){
            obs.onScoreChanged(this.score);
        }
    }

    private void notifyHpChanged(){
        for (GameObserver obs : observers){
            obs.onScoreChanged(this.hp);
        }
    }

    private void notifyLivesChanged(){
        for (GameObserver obs : observers){
            obs.onLivesChanged(this.lives);
        }
    }
    private void notifySpellCardsChanged(){
        for (GameObserver obs : observers){
            obs.onScoreChanged(this.spellCards);
        }
    }
    private void notifyPowerChanged(){
        for (GameObserver obs : observers){
            obs.onScoreChanged(this.power);
        }
    }
    private void notifyGrazeChanged(){
        for (GameObserver obs : observers){
            obs.onScoreChanged(this.grazeCount);
        }
    }

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 48, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }
    public Player(float x, float y, String name, int hp, int power, int spellCards){
        super(x,y,32,48,200f,Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
        notifyHpChanged();
    }
    public int getHp(){
        return this.hp;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setPower(int power){
        this.power = power;
        notifyPowerChanged();
    }

    public int getPower(){
        return this.power;
    }

    public void setSpellCards(int spellCards){
        this.spellCards = spellCards;
        notifySpellCardsChanged();
    }

    public int getSpellCards(){
        return this.spellCards;
    }

    public long getScore(){
        return this.score;
    }

    public void setCurrentDir(int currentDir){this.currentDir = currentDir ;}
    public int getCurrentDir(){return this.currentDir;}

    public ShootingPattern getShootingPattern() {return this.shootingPattern;}
    public void setShootingPattern(ShootingPattern shootingPattern) {this.shootingPattern = shootingPattern;}

    public void shoot(Enemy target) {
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        // 3. Call the Enemy object's takeDamage() method.
        target.takeDamage(damage);
    }

    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null) return;

        if (shootTimer >= shootCooldown) {
            if (shootingPattern != null) {
                shootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true);
            } else {
                bulletManager.spawnPlayerBullet(x + width / 2 - 8f, y + height, 0f, 400f, 25);
            }
        }
        shootTimer = 0;
    }



    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            notifyScoreChanged();
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + getScore());
        }
    }

    // Execute Spell Card (Bomb) from BombCommand
    public void useBomb(BulletManager bulletManager, List<GameObject> entities) {
        // TODO:
        // 1. Check whether spellCards > 0.
        // 2. If YES:
        //    - Decrement spellCards by 1 (spellCards--).
        //    - Activate invulnerability for 5 seconds: invulnerableTimer = 5.0f;
        //    - If bulletManager is not null, call bulletManager.clearEnemyBullets(entities).
        //    - Print to console: name + " unleashes SPELL CARD (Fantasy Seal)! All enemy bullets cleared!"
        // 3. If NO:
        //    - Print to console: "No Spell Cards (Bombs) remaining!"
        if (spellCards > 0){
            spellCards--;
            notifySpellCardsChanged();
            invulnerableTimer = 5.0f;
            if (bulletManager != null){
                bulletManager.clearEnemyBullets(entities);
            }
            System.out.println(name + " unleashes SPELL CARD (Fantasy Seal)! All enemy bullets cleared!");
        }
        System.out.println("No Spell Cards (Bombs) remaining!");
    }

    public void useBomb(BulletManager bulletManager) {
        useBomb(bulletManager, null);
    }

    public void addGraze() {
        // TODO:
        // 1. Increment grazeCount by 1 (this.grazeCount++)
        // 2. Add 50 to the score using addScore(50)
        this.grazeCount++;
        notifyGrazeChanged();
        addScore(50);
    }

    public void takeDamage(int damage) {
        takeDamage(damage, null, null);
    }

    public void takeDamage(int damage, BulletManager bulletManager, List<GameObject> entities) {
        if (isInvulnerable() || isDestroyed()) {
            if (isInvulnerable()) {
                System.out.println(name + " is INVULNERABLE! (0 damage taken)");
            }
            return;
        }
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        notifyHpChanged();

        System.out.println(name + " took " + damage + " damage! Remaining HP: " + this.hp);
        if (this.hp == 0) {
            System.out.println(name + " was defeated (Pichuun~)! ");
            loseLife(bulletManager, entities);
        }
    }

    public void loseLife(BulletManager bulletManager, List<GameObject> entities) {
        this.lives--;
        notifyLivesChanged();

        if (bulletManager != null) {
            bulletManager.clearEnemyBullets();
        }

        if (this.lives > 0) {
            if (bulletManager != null) {
                System.out.println("[Player] " + name + " lost a life! Remaining Lives: " + this.lives + ". Respawning at (200, 50)...");
            }
            respawn();
        } else {
            if (bulletManager != null) {
                System.out.println("[Player] GAME OVER! " + name + " has run out of lives!");
            }
            destroy();
        }
    }

    public void respawn() {
        this.active = true;
        this.x = 200;
        this.y = 50;
        this.hp = 100;
        this.invulnerableTimer = 3.0f;
        this.power = Math.max(10, this.power - 16); // Touhou death penalty
        this.spellCards = 3; // Reset bombs to 3 upon respawn
        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
    }

    public void resetGame() {
        this.lives = 3;
        this.hp = 100;
        this.power = 15;
        this.spellCards = 3;
        this.score = 0;
        this.grazeCount = 0;
        this.active = true;
        this.x = 200;
        this.y = 50;
        this.invulnerableTimer = 3.0f;
        notifyHpChanged();
        notifySpellCardsChanged();
        notifyPowerChanged();
        notifyLivesChanged();
        notifyScoreChanged();
        notifyGrazeChanged();
    }

    public void collectItem(Item item) {
        if (item.isDestroyed()) return;

        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    this.power += type.getPowerBonus();
                    notifyPowerChanged();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POWER item! Power increased to " + power);
                }
                case POINT -> {
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POINT item!");
                }
                case BOMB -> {
                    this.spellCards++;
                    notifySpellCardsChanged();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected BOMB item! SpellCards: " + spellCards);
                }
                case LIFE -> {
                    this.lives++;   // Note: This line was added!
                    this.hp = 100;
                    notifyLivesChanged();
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected LIFE item! Remaining Lives: " + lives);
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }

        item.destroy();
    }

    public boolean isAlive() {
        return this.hp > 0 && this.lives > 0 && !isDestroyed();
    }

    public boolean isGameOver() {
        return this.lives <= 0;
    }

    public boolean isInvulnerable() {
        return invulnerableTimer > 0f;
    }

    public float getInvulnerableTimer() {
        return invulnerableTimer;
    }

    public int getLives() { return lives; }
    public void setLives(int lives) {
        this.lives = lives;
        notifyLivesChanged();
    }



    @Override
    public void update(float delta) {
        super.update(delta); // Advances stateTime for idle animations
        // TODO: Increment shootTimer by delta (shootTimer += delta;)
        //       Make sure all old Gdx.input reading code inside update() has been removed

        shootTimer += delta;

        if (invulnerableTimer > 0f) {
            invulnerableTimer -= delta;
            if (invulnerableTimer < 0f) invulnerableTimer = 0f;
        }
    }

    // Execute movement from MoveCommand with playfield boundary clamping (Playfield Clamping)
    public void move(float dx, float dy) {
        // TODO:
        // 1. Increment x position by dx multiplied by speed (this.x += dx * speed)
        // 2. Increment y position by dy multiplied by speed (this.y += dy * speed)
        // 3. Call clampToPlayfield()
        this.x += dx*speed;
        this.y += dy*speed;
        clampToPlayfield();
    }

    private void clampToPlayfield() {
        float minX = 32;
        float maxX = 416 - width;
        float minY = 16;
        float maxY = 560 - height;

        // TODO: Clamp x so it is not less than minX and not greater than maxX,
        //       and clamp y so it is not less than minY and not greater than maxY.
        if(x < minX || x > maxX){
            x = Math.max(Math.min(x,maxX), minX);
        }
        if (y < minY || y > maxY){
            y = Math.max(Math.min(y, maxY), minY);
        }
    }

    public void updateAnimationState(float dx) {
        AssetManager assets = AssetManager.getInstance();
        if (dx < 0) {
            // TODO:
            // 1. Only make the following changes if currentDir is not -1.
            // 2. Set currentDir to -1.
            // 3. Retrieve the "player_left" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            // 4. If anim is not null, assign it using setAnimation(...).
            setCurrentDir(-1);
            Animation<TextureRegion> anim = assets.getAnimation("player_left");
            if (anim != null){
                setAnimation(anim);
            }
        } else if (dx > 0) {
            // TODO:
            // 1. Only make the following changes if currentDir is not 1.
            // 2. Set currentDir to 1.
            // 3. Retrieve the "player_right" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            // 4. If anim is not null, assign it using setAnimation(...).
            setCurrentDir(1);
            Animation<TextureRegion> anim = assets.getAnimation("player_right");
            if (anim != null){
                setAnimation(anim);
            }
        } else {
            // TODO:
            // 1. Only make the following changes if currentDir is not 0.
            // 2. Set currentDir to 0.
            // 3. Retrieve the "player_idle" animation using assets.getAnimation(...).
            //    Store it in a local variable of type Animation<TextureRegion> named anim.
            // 4. If anim is not null, assign it using setAnimation(...).
            setCurrentDir(0);
            Animation<TextureRegion> anim = assets.getAnimation("player_idle");
            if (anim != null){
                setAnimation(anim);
            }
        }
    }



    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is an Item
        // TODO: Print "Player touches items" then call collectItem((Item) other)
        if (other instanceof Item){
            System.out.print("Player touches items");
            collectItem((Item) other);
        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        // TODO: Return a new 8x8 Rectangle centered right in the middle of the sprite:
        // - X coordinate: x + (width / 2f) - 4f
        // - Y coordinate: y + (height / 2f) - 4f
        // - width: 8f, height: 8f
        return new Rectangle((width/2f)-4f, (height/2f)-2f, 8f, 8f );
    }

    @Override
    public Rectangle getGrazeHitbox() {
        // TODO: Return a new Rectangle at position coordinates (x, y)
        //       covering the entire visual sprite dimensions (width, height)
        return new Rectangle(x,y,width,height);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active && !isDestroyed()) {
            // High-frequency blinking animation while invulnerable (respawn / bomb i-frames)
            if (invulnerableTimer > 0f) {
                boolean visible = ((int) (invulnerableTimer * 16)) % 2 == 0;
                if (!visible) return; // Skip drawing this frame to create a blinking effect
            }

            if (focused) {
                batch.setColor(1f, 1f, 1f, 0.5f); // 50% opacity while Focus Mode is active
            }
            super.render(batch);
            if (focused) {
                batch.setColor(1f, 1f, 1f, 1.0f); // Restore opacity to 100%
            }
        }
    }

    // Draws the Core Hitbox indicator dot while Focus Mode (Shift) is active
    public void renderFocusIndicator(ShapeRenderer shapeRenderer) {
        if (focused && shapeRenderer != null && active) {
            Rectangle core = getCoreHitbox();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(core.x + core.width / 2f, core.y + core.height / 2f, 4f);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(core.x + core.width / 2f, core.y + core.height / 2f, 2f);
            shapeRenderer.end();
        }
    }

    // TODO: Create getter isFocused(), setter setFocused(boolean focused), and getter getGrazeCount()
    public boolean isFocused(){return this.focused;}
    public void setFocused(boolean focused){ this.focused = focused;}
    public int getGrazeCount() {return this.grazeCount;}


}



