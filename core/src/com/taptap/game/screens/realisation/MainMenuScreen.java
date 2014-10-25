package com.taptap.game.screens.realisation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
        whiteFont = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"), false);
        blackFont = new BitmapFont(Gdx.files.internal("fonts/blackFont.fnt"), false);

        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("skins/main_menu/bg_desert.png"));

        music = new MusicManager();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // buttons and styles
        atlasMainMenu = new TextureAtlas("skins/main_menu/buttons.pack");
        skinMainMenu = new Skin (atlasMainMenu);
        stage = new Stage();
        table = new Table(skinMainMenu);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // todo move all of this to json file (optional)
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skinMainMenu.getDrawable("button.up"); // get this names from the .pack file
        textButtonStyle.down = skinMainMenu.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -2;
        textButtonStyle.font = whiteFont;

        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonHelp = new TextButton("Help", textButtonStyle);

        buttonPlay.pad(10);
        buttonHelp.pad(10);

        //heading
        Label.LabelStyle headingStyle = new Label.LabelStyle(whiteFont, Color.WHITE);
        heading = new Label("TAP TAP Game", headingStyle);
        table.add(heading).row().padTop(100);

        table.add(buttonPlay).row().pad(30);
        table.add(buttonHelp).row().pad(30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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

        music.play(this);
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
//        menuMusicTheme.dispose();
        blackFont.dispose();
        whiteFont.dispose();
        stage.dispose();
        atlasMainMenu.dispose();
        skinMainMenu.dispose();
        batch.dispose();
        background.dispose();
    }
    private MusicManager music;

    private Stage stage;
    private TextureAtlas atlasMainMenu;
    private Table table;
    private Skin skinMainMenu;

    private TextButton buttonPlay, buttonHelp;
    private BitmapFont whiteFont, blackFont;
    private Label heading;

    private SpriteBatch batch;
    private Texture background;

    private OrthographicCamera camera;
    private final TapTap game;
}
