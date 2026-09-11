package com.han.frontend;

import java.awt.*;

public class Player extends GameObject {
    String name;
    int hp;
    int power;
    int spellCards;
    long score;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }
    public Player(float x, float y, String name, int hp, int power, int spellCards){
        super(x,y,32,32,0,Color.RED);
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
        hp = hp-damage;

        // 2. HP must not become negative.
        if(hp<=0){
            hp = 0;
        }
        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        else{
            System.out.print(name + " took " + damage + " damage! Remaining HP: " + hp);
        }

        // 4. If HP reaches 0, display a message that the Player has been defeated.
        if(hp<=0){
            System.out.print(name + " has been defeated.");
        }
    }
    public void shoot(Enemy target) {
        // 1. Create an int named damage, calculated by adding 10 to power.
        int damage = power+10;
        // 2. Display information that the Player is shooting the Enemy, in the format: [name] shoots [TargetName] dealing [damage] DMG!
        System.out.print(name + " shoots " + target.name + " dealing " + damage + " DMG!");
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
        // TODO: Add the value to the player's score if points is greater than 0.
        if (points>0){
           score += points;
        }
    }
}



