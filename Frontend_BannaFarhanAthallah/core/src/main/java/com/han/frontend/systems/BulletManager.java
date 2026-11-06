package com.han.frontend.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.han.frontend.objects.bullets.Bullet;
import com.han.frontend.objects.bullets.BulletType;
import com.han.frontend.objects.GameObject;
import com.han.frontend.objects.items.Item;
import com.han.frontend.objects.items.ItemType;


import java.util.*;

public class BulletManager {
    // 1. Pool Queues for Object Recycling (Object Pool Pattern)
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

    // 2. Lists of Active Bullets on Screen
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    public BulletManager() {
        // TODO:
        // 1. Initialize playerBulletPool and enemyBulletPool as empty LinkedLists
        // 2. Initialize activePlayerBullets and activeEnemyBullets as empty ArrayLists
        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();
        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();

    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        if (!playerBulletPool.isEmpty()){
            bullet = playerBulletPool.poll();
        }
        else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage);
        }
        bullet.init(x,y,vx,vy, BulletType.AMULET, damage, true);
        activePlayerBullets.add(bullet);
        return bullet;
    }
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        if (!enemyBulletPool.isEmpty()){
            bullet = enemyBulletPool.poll();
        }
        else {
            bullet = EntityFactory.createEnemyBullet(x,y,damage);
        }
        bullet.init(x,y,vx,vy,BulletType.DANMAKU, damage, false);
        activeEnemyBullets.add(bullet);
        return bullet;
    }

    // Spell Card (Bomb) Execution: Clear all active enemy bullets and convert them into POINT item drops!
    public void clearEnemyBullets(List<GameObject> entities) {
        // TODO:
        // 1. Create an Iterator<Bullet> to safely traverse activeEnemyBullets.
        // 2. While the iterator has a next element:
        //    - Retrieve the current Bullet object.
        //    - If the entities parameter is not null:
        //      * Create an Item object of type ItemType.POINT using EntityFactory.createItem(...)
        //        at position (bullet.getX(), bullet.getY()), then add it to the entities list.
        //    - Mark the bullet as destroyed (bullet.destroy()).
        //    - Remove the bullet from activeEnemyBullets using remove() on the iterator from step 1.
        //    - Return the bullet to the enemyBulletPool queue using offer().
        // TIP: See the rough outline in `clearPlayerBullets()` below
        Iterator<Bullet> bIter = activeEnemyBullets.iterator();
        while (bIter.hasNext()){
            Bullet bullet = bIter.next();
            if (entities != null){
                GameObject pointItem = EntityFactory.createItem(bullet.getX(), bullet.getY(), ItemType.POINT);
                entities.add(pointItem);
            }
            bullet.destroy();
            bIter.remove();
            enemyBulletPool.offer(bullet);
        }
    }

    public void clearEnemyBullets() {
        clearEnemyBullets(null);
    }

    // Clear all player bullets when restarting the game (Copy-Paste):
    public void clearPlayerBullets() {
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.destroy();
            pIter.remove();
            playerBulletPool.offer(bullet);
        }
    }


    public void update(float delta, float screenWidth, float screenHeight) {
        // 1. Player bullet recycling & movement (5 Points)
        // TODO:
        // - Copy-paste the iterator loop from updateAndClean() in Main.java
        // - Change the class referenced by the iterator to `Bullet` instead of T
        //   and change the referenced list to activePlayerBullets
        // - Change the check to whether the bullet is off-screen
        //   OR the bullet is already marked as destroyed
        //   - If YES:
        //     * Mark the bullet as destroyed (destroy)
        //     * Safely remove the bullet from the active bullet list
        //     * Return the bullet to the pool queue
        Iterator<Bullet> bulletIterator = activePlayerBullets.iterator();

        while (bulletIterator.hasNext()){
            Bullet obj = bulletIterator.next();
            obj.update(delta);
            if (obj.isOffScreen(screenWidth,screenHeight) || obj.isDestroyed()){
                System.out.println("Removed via Generic Iterator: " + obj.getClass().getSimpleName());
                bulletIterator.remove();
                obj.destroy();
                playerBulletPool.offer(obj);
            }
        }

        // 2. Enemy bullet recycling & movement (5 Points)
        // TODO:
        // - Follow the exact same procedure as above for activeEnemyBullets
        // - Enemy bullets that go off-screen or are destroyed are returned to enemyBulletPool via offer()
        Iterator<Bullet> enemyBulletIterator = activeEnemyBullets.iterator();

        while (enemyBulletIterator.hasNext()){
            Bullet obj = enemyBulletIterator.next();
            obj.update(delta);
            if (obj.isOffScreen(screenWidth,screenHeight) || obj.isDestroyed()){
                System.out.println("Removed via Generic Iterator: " + obj.getClass().getSimpleName());
                enemyBulletIterator.remove();
                obj.destroy();
                enemyBulletPool.offer(obj);
            }
        }
    }


    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    // Helper methods to access pools and active lists
    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }
}
