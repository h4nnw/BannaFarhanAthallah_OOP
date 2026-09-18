package com.han.frontend.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.han.frontend.objects.enemies.Enemy;
import com.han.frontend.objects.items.Item;
import com.han.frontend.objects.items.ItemType;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }
    public Player(float x, float y, String name, int hp, int power, int spellCards){
        super(x,y,32,32,200f,Color.RED);
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


    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if (hp > 0) {
            return true;
        }
        else {return false;}
    }
    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    public void collectItem(Item item) {
        ItemType type = item.getItemTypeEnum();
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
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
    }


    @Override
    public void update(float delta) {
        if (Gdx.input != null) {
            // TODO: Check W / UP input   → y += speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.W)){
                this.y += speed*delta;
            }
            // TODO: Check S / DOWN input → y -= speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.S)){
                this.y -= speed*delta;
            }
            // TODO: Check A / LEFT input → x -= speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.A)){
                this.x -= speed*delta;
            }
            // TODO: Check D / RIGHT input → x += speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.D)){
                this.x += speed*delta;
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



