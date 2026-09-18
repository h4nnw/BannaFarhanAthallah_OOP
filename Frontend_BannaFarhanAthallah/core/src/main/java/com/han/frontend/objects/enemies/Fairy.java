package com.han.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.han.frontend.objects.Collidable;
import com.han.frontend.objects.Player;

public class Fairy extends Enemy {
    public Fairy(String name, int hp){
        super(150,380,24,24,0, Color.PINK,name, hp,500L);
    }

    public Fairy(float x, float y, String name, int hp){
        super(x,y,24,24,0, Color.PINK,name, hp,500L);
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        // TODO: Print "Player touches fairy"
        if (other instanceof Player){
            System.out.print("Player touches fairy\n");
        }
    }

}
