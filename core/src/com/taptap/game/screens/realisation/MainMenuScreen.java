package com.taptap.game.screens.realisation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.taptap.game.TapTap;

import java.awt.*;

public class MainMenuScreen implements Screen {
    public MainMenuScreen(TapTap game){
        this.game = game;
        batch = new SpriteBatch();
        whiteFont = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"), false);
        blackFont = new BitmapFont(Gdx.files.internal("fonts/blackFont.fnt"), false);
        menuMusicTheme = Gdx.audio.newMusic(Gdx.files.internal("The Path of the Goblin King.mp3"));
        menuMusicTheme.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // стили кнопок главного меню и работа с ними
        atlas = new TextureAtlas("skins/buttons.pack");
        skin = new Skin (atlas);
        stage = new Stage();
        table= new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up"); // get this name from the .pack file
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = whiteFont;

        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonHelp = new TextButton("Help", textButtonStyle);

        buttonPlay.pad(10);
        buttonHelp.pad(10);

        table.add(buttonPlay);
        table.debug(); // todo remove dat shitty debug later

        //todo need add some heading

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.3f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        whiteFont.draw(batch, "Welcome to TapTap !", 100, 150);
        whiteFont.draw(batch, "Tap anywhere to begin!", 100, 100);
        batch.end();

        if (Gdx.input.isTouched()) {
//            game.setScreen(new GameScreen(game));
//            dispose();
        }
    }

    @Override
    public void show() {
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        menuMusicTheme.play();
    }

    @Override
    public void hide() {
        dispose();
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
    public void dispose() {
        menuMusicTheme.dispose();
        batch.dispose();
        blackFont.dispose();
        whiteFont.dispose();
        stage.dispose();
        skin.dispose();
    }

    private Stage stage;
    private TextureAtlas atlas;
    private Table table;
    private Skin skin;
    private TextButton buttonPlay, buttonHelp;
    private BitmapFont whiteFont, blackFont;
    private com.badlogic.gdx.scenes.scene2d.ui.Label heading;

    private SpriteBatch batch;
    private Music menuMusicTheme;
    private OrthographicCamera camera;
    private final TapTap game;
}
