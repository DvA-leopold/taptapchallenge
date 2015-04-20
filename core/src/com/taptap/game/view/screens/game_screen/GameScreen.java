package com.taptap.game.view.screens.game_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.TapTap;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.view.renderer.GameRenderer;

public class GameScreen implements Screen {
    public GameScreen(final SpriteBatch batch) {
        inputMultiplexer = new InputMultiplexer();

        gameWorld = new GameWorld(inputMultiplexer);
        gameRenderer = new GameRenderer(batch, gameWorld);

        gameWorld.initializeActors(batch);
        ((TapTap) Gdx.app.getApplicationListener()).
                getMusicManager().registerMusic(this.getClass(), MusicManager.MusicTypes.MAIN_MUSIC);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameWorld.update();
        gameRenderer.render();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().playMusic();
    }

    @Override
    public void hide() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
        dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
        gameWorld.changeWorldState(GameWorld.States.GAME_PAUSED);
    }

    @Override
    public void resume() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().playMusic();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        gameWorld.dispose();
    }

    final private InputMultiplexer inputMultiplexer;
    final private GameWorld gameWorld;
    final private GameRenderer gameRenderer;
}
