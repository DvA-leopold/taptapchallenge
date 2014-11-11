package com.taptap.game.screens.realisation.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.help.HelpScreen;
import com.taptap.game.screens.realisation.game.GameScreen;
import com.taptap.game.screens.realisation.mainmenu.button.styles.MenuButtonInitializer;
import com.taptap.game.screens.realisation.records.RecordScreen;

public class MainMenuScreen implements Screen {
    public MainMenuScreen(final TapTap game){
        buttons = new MenuButtonInitializer(game);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("skins/main_menu/background/bg_desert.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();

        camera.update();
    }

    @Override
    public void show() {
        //buttons.getTable().setFillParent(true);
        stage.addActor(buttons.getTable());
        Gdx.input.setInputProcessor(stage);
        buttons.setListeners();
        MusicManager.play(this);
    }

    @Override
    public void hide() {
        MusicManager.pause(this);
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // работает только для кнопок
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
        stage.dispose();
        buttons.dispose();
        batch.dispose();
        background.dispose();
    }

    private Stage stage;
    private MenuButtonInitializer buttons;

    private SpriteBatch batch;
    private Texture background;

    private OrthographicCamera camera;
}
