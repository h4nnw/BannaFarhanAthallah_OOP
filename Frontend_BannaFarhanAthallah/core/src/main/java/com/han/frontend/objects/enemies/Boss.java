package com.han.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.han.frontend.objects.Collidable;
import com.han.frontend.objects.Player;
import com.han.frontend.objects.patterns.ShootingPattern;
import com.han.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.han.frontend.systems.BulletManager;

public class Boss extends Enemy {
    private ShootingPattern shootingPattern = new SpreadShot(200f, 3, 30f);
    public Boss(String name, int hp){
        super(380,400,64,64,0, Color.BLUE,name,hp,5000L);
    }

    public Boss(float x, float y, String name, int hp){
        super(x,y,64,64,0, Color.BLUE,name,hp,5000L);
    }

    public void shootBullet(BulletManager bulletManager){
        if (shootingPattern != null ){
            shootingPattern.execute(x + width / 2 - 8, y,   bulletManager, false);
        }
    }

    public ShootingPattern getShootingPattern() { return this.shootingPattern;}
    public void setShootingPattern(ShootingPattern shootingPattern){this.shootingPattern = shootingPattern;}

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        // TODO: Print "Player touches boss"
        if (other instanceof Player){
            System.out.print("Player touches boss\n");
        }
    }

}
