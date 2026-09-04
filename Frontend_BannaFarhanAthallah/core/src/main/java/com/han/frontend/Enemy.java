package com.han.frontend;

public class Enemy {
    String name;
    int hp;
    int maxHP;

    public Enemy(String name, int hp) {
    }

    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        maxHP = hp;
        hp =hp-damage;
        // 2. HP must not go below 0.
        if(hp<=0){
            hp = 0;
        }
        // 3. Display the current HP in the format: [EnemyName] took [damage] damage! HP: [currentHP]/[maxHP]
        else {
            System.out.print(name + " took " + damage + " damage! HP: " + hp + "/" + maxHP);
        }
        // 4. If HP reaches 0, display that the Enemy has been defeated, in the format: [EnemyName] was defeated!
        if(hp<=0){
            System.out.print(name + " was defeated!");
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
