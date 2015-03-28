package com.taptap.game.view.screens.game_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.view.renderer.GameRenderer;

public class GameScreen implements Screen {
    public GameScreen(final SpriteBatch batch) {
        inputMultiplexer = new InputMultiplexer();

        gameWorld = new GameWorld(inputMultiplexer);
        gameRenderer = new GameRenderer(batch, gameWorld);

        gameWorld.initializeActors(batch);
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
        MusicManager.play(this);
    }

    @Override
    public void hide() {
        MusicManager.pause(this);
        dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        MusicManager.pause(this);
        gameWorld.changeWorldState(GameWorld.States.GAME_PAUSED);
    }

    @Override
    public void resume() {
        MusicManager.play(this);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        gameWorld.dispose();
    }

    private final InputMultiplexer inputMultiplexer;
    private final GameWorld gameWorld;
    private final GameRenderer gameRenderer;
}
