package com.taptap.game.view.screens.help_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.TapTap;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.help_screen.buttons.HelpScreenWidgetsGroup;
import com.taptap.game.view.screens.WidgetsGroup;

public class HelpScreen implements Screen {
    public HelpScreen() {
        this.batch = ((TapTap) Gdx.app.getApplicationListener()).getSpriteBatch();
        widgetsGroup = new HelpScreenWidgetsGroup(batch);

        background = (Texture) DResourceManager.getInstance().
                get("skins/help_menu/bg_grasslands.png");
        ((TapTap) Gdx.app.getApplicationListener()).
                getMusicManager().registerMusic(this.getClass(), MusicManager.MusicTypes.ADD_MUSIC);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.disableBlending();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        batch.end();
        widgetsGroup.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        widgetsGroup.setListeners(null);
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().playMusic();
    }

    @Override
    public void hide() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
        dispose();
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
    public void dispose() {
        widgetsGroup.dispose();
    }

    final private SpriteBatch batch;
    private WidgetsGroup widgetsGroup;

    private Texture background;
}
