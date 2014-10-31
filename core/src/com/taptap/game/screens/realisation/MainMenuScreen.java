package com.taptap.game.screens.realisation;

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

public class MainMenuScreen implements Screen {
    public MainMenuScreen(final TapTap game){
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("skins/main_menu/background/bg_desert.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // buttons and styles
        atlasMainMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack");
        skinMainMenu = new Skin(Gdx.files.internal("skins/json_skins/menuSkin.json"), atlasMainMenu);
        stage = new Stage();
        table = new Table(skinMainMenu);
        //table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        buttonPlay = new TextButton("Play", skinMainMenu, "mainButtons");// from textButtonStyle .json
        buttonHelp = new TextButton("Help", skinMainMenu, "mainButtons");
        buttonPlay.pad(10);
        buttonHelp.pad(10); //todo add this to .json??

        Label heading = new Label("TAP TAP Game", skinMainMenu, "default");
        table.add(heading).row().padTop(100);

        table.add(buttonPlay).row().pad(30);
        table.add(buttonHelp).row().pad(30);
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

        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new GameScreen(game));
            }
        });
        buttonHelp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new HelpScreen(game));
            }
        });

    }

    @Override
    public void show() {
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        MusicManager.play(this);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        table.invalidateHierarchy();
        table.setSize(width, height);
    }

    @Override
    public void pause() {
        MusicManager.pause(this);
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlasMainMenu.dispose();
        skinMainMenu.dispose();
        batch.dispose();
        background.dispose();
    }

    private Stage stage;
    private TextureAtlas atlasMainMenu;
    private Table table;
    private Skin skinMainMenu;

    private TextButton buttonPlay, buttonHelp;
    private SpriteBatch batch;
    private Texture background;

    private OrthographicCamera camera;
    private final TapTap game;
}
