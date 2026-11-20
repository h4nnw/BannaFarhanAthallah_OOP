package com.han.frontend.states;

import com.han.frontend.systems.LevelWaveManager;

public interface WaveState {
    // TODO: Fill in according to the UML!
    void onEnter(LevelWaveManager manager);
    void update(LevelWaveManager manager, float delta);
}
