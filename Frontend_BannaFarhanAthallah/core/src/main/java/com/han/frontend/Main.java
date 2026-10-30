package com.han.frontend;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.han.frontend.objects.GameObject;
import com.han.frontend.objects.Player;
import com.han.frontend.objects.enemies.Boss;
import com.han.frontend.objects.enemies.Fairy;
import com.han.frontend.objects.items.Item;
import com.han.frontend.objects.items.ItemType;
import com.han.frontend.systems.AssetManager;
import com.han.frontend.systems.BulletManager;
import com.han.frontend.systems.CollisionReferee;
import com.han.frontend.systems.EntityFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private AssetManager assets;
    private Player player;
    private Fairy fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;
    private SpriteBatch batch;
    private List<Fairy> fairyList;
    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;
    private float enemyShootTimer = 0f;

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            boss.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }

    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        // 1. Get an Iterator<T> from the given list.
        Iterator<T> listIterator = list.iterator();
        // 2. While there are still elements available (hasNext()):
        //    a. Get the current element using next() and store it in a variable of type T.
        //    b. Call update(delta) on the element.
        //    c. If the element is off-screen (isOffScreen(screenWidth, screenHeight))
        //       OR isDestroyed():
        //       - Display the message: "Removed via Generic Iterator: " + [entity class name, using getClass().getSimpleName()]
        //       - Remove the element from the list using the Iterator's method
        //         (NOT list.remove()!).
        while (listIterator.hasNext()){
            T obj = listIterator.next();
            obj.update(delta);
            if (obj.isOffScreen(screenWidth,screenHeight) || obj.isDestroyed()){
                System.out.println("Removed via Generic Iterator: " + obj.getClass().getSimpleName());
                listIterator.remove();
            }
        }
    }

    @Override
    public void create() {
        // TODO 1:
        // When initializing the renderer, create a SpriteBatch and store it in batch.
        // LibGDX hint: new SpriteBatch()
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();

        // TODO 2:
        // Initialize the fairy and entities lists as empty ArrayLists.
        fairyList = new ArrayList<>();
        entities = new ArrayList<>();

        // TODO 3:
        // Before creating entities, get the AssetManager instance and call init().
        AssetManager assets = AssetManager.getInstance();
        assets.init();

        // TODO 4:
        // Update how all entities are created! Follow the table and create Player, Fairy, Boss, and Item
        // using the appropriate EntityFactory methods.
        // Add both Fairies to the fairy list using add(...).
        player = EntityFactory.createPlayer(280, 40,"Reimu Hakurei",100,15,3);
        boss = EntityFactory.createBoss(300,400,"Rumia",150);
        powerItem = EntityFactory.createItem(200,450, ItemType.POWER);
        pointItem = EntityFactory.createItem(320,480, ItemType.POINT);
        fairyList.add(EntityFactory.createFairy(150,380,"Red Fairy",20,"fairy_idle_red"));
        fairyList.add(EntityFactory.createFairy(250,380,"Blue Fairy",20, "fairy_idle_blue"));

        assets.playBgm("audio/bgm.ogg", true);
        // TODO 5:
        // Add all the objects you have just created to entities.
        entities.add(player);
        entities.add(boss);
        entities.addAll(fairyList);
        entities.add(powerItem);
        entities.add(pointItem);


    }



    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            player.shootBullet(bulletManager);
        }

        updateEnemyScheduler(delta);

        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        collisionReferee.resolveCollisions(player, entities, bulletManager);



        // ... Keep the code above unchanged
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                // TODO: Call each entity's .render() method with the SpriteBatch as its argument.
                entity.render(batch);
            }
        }

        bulletManager.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }

        if (assets != null){
            assets.dispose();
        }

        // TODO: Call dispose on AssetManager to release the loaded Textures as well.
        AssetManager.getInstance().dispose();
    }
}

