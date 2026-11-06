package com.han.frontend.commands;
import com.han.frontend.objects.GameObject;
import com.han.frontend.objects.Player;
import com.han.frontend.systems.BulletManager;
import java.util.List;
public class BombCommand implements Command {
    private List<GameObject> entities;

    public BombCommand() {}

    public BombCommand(List<GameObject> entities) {
        // TODO: Initialize the entities attribute from the constructor parameter
        this.entities = entities;
    }

    public void setEntities(List<GameObject> entities) {
        // TODO: Set entities according to the parameter
        this.entities = entities;
    }

    // TODO: Override `execute` with the following business logic:
    // - Check if player exists (is not null)
    // - If so, call the player's useBomb(bulletManager, entities) method
    @Override
    public void execute(Player player, BulletManager bulletManager){
        if (player != null){
            player.useBomb(bulletManager,entities);
        }
    }
}
