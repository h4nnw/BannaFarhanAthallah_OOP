package com.han.frontend.systems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.han.frontend.objects.Player;
import com.han.frontend.objects.bullets.Bullet;
import com.han.frontend.objects.bullets.BulletType;
import com.han.frontend.objects.enemies.Boss;
import com.han.frontend.objects.enemies.Enemy;
import com.han.frontend.objects.enemies.Fairy;
import com.han.frontend.objects.items.Item;
import com.han.frontend.objects.items.ItemType;


public class EntityFactory {

    // Create a Player and assign the 'player_idle' animation from AssetManager
    public static Player createPlayer(float x, float y, String name, int hp, int power, int spellCards) {
        Player player = new Player(x, y, name, hp, power, spellCards);
        Animation<TextureRegion> idleAnim = AssetManager.getInstance().getAnimation("player_idle");
        player.setAnimation(idleAnim);
        return player;
    }

    // Create an Item and assign its sprite from AssetManager based on ItemType
    public static Item createItem(float x, float y, ItemType itemType) {
        Item item = new Item(x, y, itemType);
        String key = switch (itemType) {
            case POWER -> "item_power";
            case POINT -> "item_point";
            case BOMB  -> "item_bomb";
            case LIFE  -> "item_life";
        };
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(key);
        item.setSprite(sprite);
        return item;

    }

    public static Bullet createPlayerBullet(float x, float y, int damage, String spriteKey) {
        // TODO 1: Retrieve the TextureRegion for spriteKey using getTextureRegion from
        // AssetManager.getInstance(), then store it in a local variable named `sprite`.
        TextureRegion sprite = AssetManager.getInstance();
        // TODO 2:
        // Create a new Bullet using x, y, BulletType.AMULET, and damage;
        // store it in a local variable named `bullet`.
        Bullet bullet = new Bullet(x,y,BulletType.AMULET,damage);
        // TODO 3:
        // Assign sprite to bullet using bullet.setSprite(...).
        bullet.setSprite(sprite);
        // TODO 4:
        // Return bullet.
        return bullet;
    }

    public static Bullet createPlayerBullet(float x, float y, int damage) {
        // TODO 5:
        // Return the result of calling the previous createPlayerBullet overload with "bullet_amulet" as spriteKey.
        return createPlayerBullet(x,y,damage,"bullet_amulet")
    }


    // Create an enemy bullet (type DANMAKU, speed 0f, sprite bullet_danmaku)
    public static Bullet createEnemyBullet(float x, float y, int damage) {
        Bullet bullet = new Bullet(x, y, 0f, BulletType.DANMAKU, damage);
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion("bullet_danmaku");
        bullet.setSprite(sprite);
        return bullet;
    }

    public static Fairy createFairy(float x, float y, String name, int hp){
        Fairy fairy = new Fairy(x, y, name, hp);
        Animation<TextureRegion> idleAnim = AssetManager.getInstance().getAnimation("fairy_idle");
        fairy.setAnimation(idleAnim);
        return fairy;
    }

    public static Boss createBoss(float x, float y, String name, int hp){
        Boss boss = new Boss(x,y,name,hp);
        Animation<TextureRegion> idleAnim = AssetManager.getInstance().getAnimation("boss_idle");
        boss.setAnimation(idleAnim);
        return boss;
    }

    public static Fairy createFairy(float x, float y, String name, int hp, String keyString) {
        Fairy fairy = new Fairy(x,y,name,hp);
        Animation<TextureRegion> idleAnim = AssetManager.getInstance().getAnimation(keyString);
        fairy.setAnimation(idleAnim);
        return fairy;
    }
}
