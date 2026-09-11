package com.han.frontend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class GameObject {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Color color;

    public GameObject(float x, float y, float width, float height, float speed, Color color){
        this.x = x;
        this.y = y;
        this.width =width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    public float getX(){
        return this.x;
    }

    public float setX(float x){
        return this.x = x;
    }

    public float getY(){
        return this.y;
    }

    public float setY(float y){
        return this.y = y;
    }

    public Color getColor(){
        return this.color;
    }

    public Color setColor(Color color){
        return this.color = color;
    }

    public float getWidth(){
        return this.width;
    }

    public void setWidth(float width) {
        if (width > 0) this.width = width;
    }

    public float getHeight(){
        return this.height;
    }

    public void setHeight(float height) {
        if (height > 0) this.height = height;
    }

    public float getSpeed(){
        return this.speed;
    }
    public void setSpeed(float speed) {
        if (speed >= 0) this.speed = speed;
    }


    public void update(float delta){

    }
    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(this.color);
        shapeRenderer.rect(this.x, this.y, this.width, this.height);
    }
}
