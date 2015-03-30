package com.taptap.game.view.screens.town_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.town_screen.buttons.TownButtonInitializer;

import java.util.LinkedList;

public class TownScreen implements Screen {
    public TownScreen(final SpriteBatch batch) {
        this.batch = batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        buttons = new TownButtonInitializer(batch, camera);
    }

    @Override
    public void show() {
        backgroundList = new LinkedList<>();
        backgroundList.add((Texture) DResourceManager.getInstance().get("sectors/Town/town_grass.png"));
        backgroundList.add((Texture) DResourceManager.getInstance().get("sectors/bad/black.png"));
        backgroundList.add((Texture) DResourceManager.getInstance().get("sectors/good/white.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float offset = 0;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Texture background : backgroundList) {
            batch.draw(background, 0 + offset, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
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
