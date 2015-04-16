package com.taptap.game.view.screens.town_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.TapTap;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.town_screen.buttons.TownButtonInitializer;

import java.util.LinkedList;

public class TownScreen implements Screen {
    public TownScreen(final SpriteBatch batch) {
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        buttons = new TownButtonInitializer(batch, camera);
        ((TapTap) Gdx.app.getApplicationListener()).
                getMusicManager().registerMusic(this.getClass(), MusicManager.MusicTypes.ADD_MUSIC);
    }

    @Override
    public void show() {
        backgroundList = new LinkedList<>();
        backgroundList.add((Texture) DResourceManager.getInstance().get("sectors/main.png"));
        backgroundList.add((Texture) DResourceManager.getInstance().get("sectors/bad.png"));
        backgroundList.add((Texture) DResourceManager.getInstance().get("sectors/good.png"));
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().playMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float offset = 0;
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (Texture background : backgroundList) {
            batch.draw(background, offset, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            offset += Gdx.graphics.getWidth();
        }
        batch.end();

        camera.update();
        buttons.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
    }

    @Override
    public void resume() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().playMusic();
    }

    @Override
    public void hide() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
        dispose();
    }

    @Override
    public void dispose() {
        buttons.dispose();
    }

    private final SpriteBatch batch;
    private OrthographicCamera camera;
    private TownButtonInitializer buttons;

    private LinkedList<Texture> backgroundList;
}
