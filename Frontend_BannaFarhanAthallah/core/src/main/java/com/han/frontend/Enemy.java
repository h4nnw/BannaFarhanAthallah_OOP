package com.han.frontend;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends GameObject {
    String name;
    int hp;
    int maxHP;
    protected long scoreValue;

    public Enemy(String name, int hp) {
        super(200, 380, 24, 24, 0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
        this.scoreValue = 100L;
    }
    public Enemy(float x, float y, float width, float height,float speed, Color color, String name, int hp, long scoreValue){
        super(x,y,width,height,speed,color);
        this.name = name;
        this.hp = hp;
        this.scoreValue = scoreValue;
    }


    public boolean takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        maxHP = hp;
        // 2. HP must not go below 0.
        if(hp==0){
            return true;
        }
        hp =hp-damage;
        // 3. Display the current HP in the format: [EnemyName] took [damage] damage! HP: [currentHP]/[maxHP]
        if(hp==0) {
            System.out.print(name + " was defeated!");
            return true;
        }
        // 4. If HP reaches 0, display that the Enemy has been defeated, in the format: [EnemyName] was defeated!
        else {
            System.out.println(name + " took " + damage + " damage! HP: " + hp + "/" + maxHP);
            return false;
        }

    }
    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.print( name + " unleashes bullet barrage on " + player.name);
        // 2. Call the Player's takeDamage() method using the given damage.
        player.takeDamage(damage);
    }

    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        if(hp>0) {return true;}
        else {return false;}
    }


}
