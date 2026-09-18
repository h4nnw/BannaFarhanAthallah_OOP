package com.han.frontend.objects.items;

import com.badlogic.gdx.graphics.Color;
import com.han.frontend.objects.Collidable;
import com.han.frontend.objects.GameObject;
import com.han.frontend.objects.Player;

public class Item extends GameObject {
    private String itemType;
    private long scoreValue;
    private ItemType itemTypeEnum;

    public long getScoreValue(){return this.scoreValue;}

    public String getItemType() {return this.itemType;}

    public ItemType getItemTypeEnum() {return this.itemTypeEnum;}

    public Item(float x, float y, String itemType){
        super(x,y,16,16,100f, Color.WHITE);
        this.scoreValue = 1000L;
        this.itemType = itemType;
    }

    public Item(float x, float y, float width, float height, float speed, String itemType){
        super(x,y,width,height,speed,Color.WHITE);
        this.scoreValue = 1000L;
        this.itemType = itemType;
    }
    public Item(float x, float y, float width, float height, float speed, String itemType, long scoreValue){
        super(x,y,width,height,speed,Color.WHITE);
        this.scoreValue = scoreValue;
        this.itemType = itemType;
    }

    public Item(float x, float y, ItemType itemTypeEnum){
        super(x,y,16,16,100f,Color.WHITE);
        this.scoreValue = itemTypeEnum.getScoreValue();
    }

    public Item(float x, float y, float width, float height, float speed, ItemType itemTypeEnum, long scoreValue){
        super(x,y,width,height,speed,Color.WHITE);
        this.itemTypeEnum = itemTypeEnum;
        this.scoreValue = scoreValue;
    }


    @Override
    public void update(float delta) {
        this.y -= speed * delta;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player) {
            // Item pickup is handled on the Player side via collectItem()

        }
    }

}
