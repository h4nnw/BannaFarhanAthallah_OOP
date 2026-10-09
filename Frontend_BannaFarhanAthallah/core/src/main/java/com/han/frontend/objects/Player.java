package com.han.frontend.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.han.frontend.objects.bullets.Bullet;
import com.han.frontend.objects.enemies.Enemy;
import com.han.frontend.objects.bullets.BulletType;
import com.han.frontend.objects.items.Item;
import com.han.frontend.objects.items.ItemType;
import com.han.frontend.systems.AssetManager;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir;

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
    }

    public int getPower(){
        return this.power;
    }

    public void setSpellCards(int spellCards){
        this.spellCards = spellCards;
    }

    public int getSpellCards(){
        return this.spellCards;
    }

    public long getScore(){
        return this.score;
    }

    public void setCurrentDir(int currentDir){this.currentDir = currentDir ;}
    public int getCurrentDir(){return this.currentDir;}

    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        setHp(getHp() - damage);

        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        if (getHp() > 0){
            System.out.print(getName() + " took " + damage + " damage! Remaining HP: " + getHp());
        }
        // 4. If HP reaches 0, display a message that the Player has been defeated.
        else {
            System.out.print(getName() + " has been defeated.");
        }
    }
    public void shoot(Enemy target) {
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        // 3. Call the Enemy object's takeDamage() method.
        target.takeDamage(damage);
    }

    public Bullet shootBullet() {
        int damage = 10 + getPower();
        System.out.println(name + " shoots bullet dealing " + damage + " DMG!");
        // TODO: return a new Bullet positioned at the top-center of the Player
        // (x + width/2 - 4, y + height), with BulletType.AMULET as its type,
        // and the damage calculated above
        return new Bullet(x+width/2-4, y+height, BulletType.AMULET, damage);
    }



    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if (getHp() > 0) {
            return true;
        }
        else {return false;}
    }
    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + getScore());
        }
    }

    public void collectItem(Item item) {
        ItemType type = item.getItemTypeEnum();
        if (item.isDestroyed()) return;
        if (type != null) {
            switch (type) {
                case POWER -> {
                    // 1. Increase power by type.getPowerBonus() via this.power
                    // 2. Add score by item.getScoreValue() via addScore() (addScore() already automatically prints "gained X pts!")
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                    this.power = type.getPowerBonus();
                    addScore(item.getScoreValue());
                    System.out.print(getName() + " collected POWER item! Power increased to " + getPower());
                }
                case POINT -> {
                    // 1. Add score by item.getScoreValue() via addScore()
                    // 2. Print: [name] collected POINT item!
                    addScore(item.getScoreValue());
                    System.out.print(getName() + " collected POINT item! ");
                }
                case BOMB -> {
                    // 1. Increase spellCards by 1
                    // 2. Add score by item.getScoreValue() via addScore()
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                    setSpellCards(getSpellCards()+1);
                    addScore(item.getScoreValue());
                    System.out.print(getName() + " collected BOMB item! Spellcards: " + getSpellCards());
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    // 2. Add score by item.getScoreValue() via addScore()
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                    setHp(getHp()+20);
                    addScore(item.getScoreValue());
                    System.out.print(getName() + " collected LIFE item! HP: " + getHp());
                }
            }
            // TODO: Mark this item as destroyed so it can later be removed by the Iterator
            // Call the item's destroy() method here
            item.destroy();
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
    }


    @Override
    public void update(float delta) {
        // TODO 1: Call GameObject's update(delta) through super.
        super.update(delta);

        // TODO 2: Declare a local float variable dx with an initial value of 0
        float dx = 0f;
        // (dx = delta x, tracks the change in horizontal direction for animation)
        if (Gdx.input != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x -= speed * delta;
                // TODO 3: Adjust dx to match the direction.
                // (If you move left, what should happen to dx?)
                dx = x;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x += speed * delta;
                // TODO 4: Adjust dx to match the direction.
                // (If you move right, what should happen to dx?)
                dx = x;
            }
        }

        // TODO 5: Call updateAnimationState(dx)
    updateAnimationState(dx);
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


}



