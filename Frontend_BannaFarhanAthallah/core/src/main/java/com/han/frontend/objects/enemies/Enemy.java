package com.han.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.han.frontend.objects.GameObject;
import com.han.frontend.objects.Player;

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
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }
    public int getHp(){ return this.hp;}
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getMaxHP() { return this.maxHP; }

    public void setScoreValue(long scoreValue){ this.scoreValue = scoreValue;}

    public long getScoreValue(){ return this.scoreValue;}


    public boolean takeDamage(int damage) {
        boolean wasAlive = isAlive();
        setHp(getHp()-damage);
        if (getHp() < 0) {
            setHp(0);
        }
        System.out.println(getName() + " took " + damage + " damage! HP: " + getHp() + "/" + getMaxHP());
        if (wasAlive && getHp() == 0) {
            System.out.println(name + " was defeated!");
            // TODO: mark this enemy as destroyed
            destroy();
            return true;
        }
        return false;
    }

    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.print( name + " unleashes bullet barrage on " + player.getName());
        // 2. Call the Player's takeDamage() method using the given damage.
        player.takeDamage(damage);
    }

    public boolean isAlive() {

        if(hp>0) {return true;}
        else {return false;}
    }


}
