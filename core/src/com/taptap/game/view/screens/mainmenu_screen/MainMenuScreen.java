package com.taptap.game.view.screens.mainmenu_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.view.screens.game_screen.button.styles.MenuButtonInitializer;
import debug.statistics.FPS_MEM_DC;

public class MainMenuScreen implements Screen {
    public MainMenuScreen() {
        buttons = new MenuButtonInitializer();
        batch = new SpriteBatch();
        background = ResourceManager.getInstance().get(ResourceManager.menuBackground);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        FPS_MEM_DC.drawCalls+=2;
        FPS_MEM_DC.fpsLog();
        camera.update();
        buttons.render();
    }

    @Override
    public void show() {
        buttons.setListeners(null);
        MusicManager.play(this);
    }

    @Override
    public void hide() {
        MusicManager.pause(this);
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        buttons.resize(width, height);
    }

    @Override
    public void pause() {
        MusicManager.pause(this);
    }

    @Override
    public void resume() {
        MusicManager.play(this);
    }

    @Override
    public void dispose() {
        buttons.dispose();
        batch.dispose();
        //background.dispose();
    }

    private MenuButtonInitializer buttons;

    private SpriteBatch batch;
    private Texture background;

    private OrthographicCamera camera;
}
