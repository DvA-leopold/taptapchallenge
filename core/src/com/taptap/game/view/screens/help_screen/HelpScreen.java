package com.taptap.game.view.screens.help_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.view.screens.help_screen.buttons.HelpButtonInitializer;
import com.taptap.game.view.buttons.interfaces.Buttons;
import debug.statistics.FPS_MEM_DC;

public class HelpScreen implements Screen {
    public HelpScreen() {
        batch = new SpriteBatch();
        buttons = new HelpButtonInitializer(batch);

        background = ResourceManager.getInstance().get(ResourceManager.helpBackground);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.disableBlending();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.enableBlending();
        batch.end();
        buttons.render();

        FPS_MEM_DC.fpsLog();
    }

    @Override
    public void resize(int width, int height) {
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
    public void pause() {
        MusicManager.pause(this);
    }

    @Override
    public void resume() {
        MusicManager.play(this);
    }

    @Override
    public void dispose() {
        batch.dispose();
        //background.dispose();
    }

    private Buttons buttons;
    private SpriteBatch batch;
    private Texture background;
}
