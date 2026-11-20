package com.han.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.han.frontend.objects.patterns.ShootingPattern;
import com.han.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.han.frontend.objects.patterns.entityStrategy.FixedMovement;
import com.han.frontend.systems.BulletManager;

public class Boss extends Enemy {
    private boolean survivesAtZeroHp = false;

    public void setLife(int life) {
        this.hp = Math.max(0, life);
        this.maxHp = this.hp;
    }

    @Override
    public boolean takeDamage(int damage) {
        if (!damageable) {
            return false;
        }
        if (!survivesAtZeroHp) {
            return super.takeDamage(damage);
        }
        this.hp = Math.max(0, this.hp - damage);
        System.out.println(name + " took " + damage + " damage! HP: " + this.hp + "/" + this.maxHp);
        return false;
    }

    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        setMovementPattern(new FixedMovement());
        setShotHitboxSize(48f, 56f);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        setMovementPattern(new FixedMovement());
        setShotHitboxSize(48f, 56f);
    }

    public boolean isSurvivesAtZeroHp() { return survivesAtZeroHp; }
    public void setSurvivesAtZeroHp(boolean survivesAtZeroHp) { this.survivesAtZeroHp = survivesAtZeroHp; }

}
