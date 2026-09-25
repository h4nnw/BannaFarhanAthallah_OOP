package com.han.frontend.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject implements Collidable {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Color color;
    protected boolean active = true;

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

    public boolean isDestroyed() {
        // TODO: return true if the object is NOT active (active == false)
        return !active;
    }

    public void destroy() {
        // TODO: mark this object as inactive
        active = false;
    }


    @Override
    public Rectangle getCoreHitbox(){ return new Rectangle(this.x, this.y, this.width, this.height);}

    @Override
    public Rectangle getGrazeHitbox(){return new Rectangle(this.x-10, this.y-10, this.width+20, this.height+20);}

    @Override
    public void onCollision(Collidable other) {
        // Base collision handler (can be overridden by subclasses that need to react)
    }

    public void update(float delta){

    }
    public void render(ShapeRenderer shapeRenderer) {
        if (shapeRenderer != null && color != null && active) {
            shapeRenderer.setColor(color);
            shapeRenderer.rect(x, y, width, height);
        }
    }
    public boolean isOffScreen(float screenWidth, float screenHeight) {
        // TODO: return true if the x or y position is outside the screen boundaries
        // Use a 50px tolerance margin on each side, so objects that have only
        // slightly passed the edge of the screen are not immediately considered gone.
        final float offlimit = 50f;
        return x < -offlimit || x > screenHeight*screenWidth || y < -offlimit || y > screenHeight*screenWidth;
    }

}
