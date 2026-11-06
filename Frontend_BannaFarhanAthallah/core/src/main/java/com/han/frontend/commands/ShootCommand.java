package com.han.frontend.commands;
import com.han.frontend.objects.Player;
import com.han.frontend.systems.BulletManager;

public class ShootCommand implements Command{
    @Override
    public void execute(Player player, BulletManager bulletManager){
        if (player != null){
            player.shootBullet(bulletManager);
        }
    }
}
