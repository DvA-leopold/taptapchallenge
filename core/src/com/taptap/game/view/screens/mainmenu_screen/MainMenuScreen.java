package com.taptap.game.view.screens.mainmenu_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.TapTap;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.mainmenu_screen.buttons.MenuScreenWidgetsGroup;

public class MainMenuScreen implements Screen {
    public MainMenuScreen() {
        this.batch = ((TapTap) Gdx.app.getApplicationListener()).getSpriteBatch();
        buttons = new MenuScreenWidgetsGroup(batch);
        background = (Texture) DResourceManager.getInstance().
                get("skins/main_menu/background/bg_desert.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ((TapTap) Gdx.app.getApplicationListener()).
                getMusicManager().registerMusic(this.getClass(), MusicManager.MusicTypes.ADD_MUSIC);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.disableBlending();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        batch.end();

        camera.update();
        buttons.render();
    }

    @Override
    public void show() {
        buttons.setListeners(null);
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().playMusic();
    }

    @Override
    public void hide() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
        dispose();
    }

    @Override
    public void resize(int width, int height) { }

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
        buttons.dispose();
    }


    final private SpriteBatch batch;
    private OrthographicCamera camera;
    private MenuScreenWidgetsGroup buttons;

    private Texture background;
}
