package com.han.frontend.commands;

import com.han.frontend.objects.Player;
import com.han.frontend.systems.BulletManager;

public class FocusCommand implements Command {
    private boolean focused;

    public FocusCommand(boolean focused) {
        this.focused = focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null) {
            player.setFocused(focused);
        }
    }
}
