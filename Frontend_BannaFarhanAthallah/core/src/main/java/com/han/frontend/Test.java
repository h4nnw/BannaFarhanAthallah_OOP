package com.han.frontend;

import com.han.frontend.objects.Player;
import com.han.frontend.objects.bullets.Bullet;
import com.han.frontend.objects.enemies.Boss;
import com.han.frontend.objects.enemies.Enemy;
import com.han.frontend.objects.enemies.Fairy;
import com.han.frontend.objects.items.Item;

public class Test {
    public static void main(String[] args) {
        System.out.println("=== TOUHOU OOP PRACTICUM - MODULE 1: BASIC CLASSES & OBJECT INTERACTION ===");

        // Instantiating objects (Player and Enemy)
        Player reimu = new Player("Reimu Hakurei", 100, 15, 3);
        Bullet bullet = reimu.shootBullet();
        System.out.println("Bullet created at: (" + bullet.getX() + ", " + bullet.getY() + ") | Damage: " + bullet.getDamage());
        bullet.update(5f);
        System.out.println("Bullet Y after 0.1s: " + bullet.getY());
        System.out.println("Is bullet off screen? " + bullet.isOffScreen(640, 480));

    }
}
