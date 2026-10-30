package com.han.frontend.objects.patterns;

import com.han.frontend.systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}

