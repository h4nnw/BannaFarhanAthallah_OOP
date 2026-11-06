package com.han.frontend.commands;

import com.han.frontend.objects.Player;
import com.han.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}
