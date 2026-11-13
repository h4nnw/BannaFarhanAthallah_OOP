package com.han.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.han.frontend.objects.patterns.ShootingPattern;
import com.han.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.han.frontend.objects.patterns.entityStrategy.FixedMovement;
import com.han.frontend.systems.BulletManager;

public class Boss extends Enemy {

    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        setMovementPattern(new FixedMovement());
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
        setShootingPattern(new SpreadShot(200f, 3, 30f));
        setMovementPattern(new FixedMovement());
    }
}
