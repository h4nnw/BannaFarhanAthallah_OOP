package com.han.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.han.frontend.network.ScoreboardFacade;
import com.han.frontend.systems.AssetManager;
import com.han.frontend.systems.StageFacade;
import com.han.frontend.ui.GameHUD;

public class Main extends ApplicationAdapter {
    // TODO (2 points):
    // Complete the additional constants and fields of `Main` referring to the UML above
    // (player name constant to be saved to the leaderboard using your own username,
    // leaderboard size limit = 5, ScoreboardFacade instance, and a flag so the score
    // is only submitted once per run).
    public static String PLAYER_NAME;
    private static final int LEADERBOARD_SIZE = 5;
    private SpriteBatch batch;
    private GameHUD gameHUD;
    private StageFacade stage;
    private ScoreboardFacade scoreboard;
    private boolean scoreSubmitted;


    @Override
    public void create() {
        batch = new SpriteBatch();
        gameHUD = new GameHUD();
        AssetManager.getInstance().init();

        stage = new StageFacade(gameHUD);
        AssetManager.getInstance().playBgm("audio/bgm.ogg", true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input != null && Gdx.input.isKeyJustPressed(Input.Keys.R)
                && (stage.isGameOver() || stage.isStageClear())) {
            stage.restart();
            gameHUD.clearScoreboard();
        }

        stage.update(delta);
        gameHUD.setStageClear(stage.isStageClear());

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);
        batch.begin();
        stage.render(batch);
        gameHUD.render(batch);
        batch.end();

        gameHUD.renderBackground();
        stage.renderOverlay(gameHUD.getShapeRenderer());
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (gameHUD != null) gameHUD.dispose();
        AssetManager.getInstance().dispose();
    }
}
