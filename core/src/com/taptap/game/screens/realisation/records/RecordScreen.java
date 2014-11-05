package com.taptap.game.screens.realisation.records;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;
import com.taptap.game.screens.realisation.game.GameScreen;

public class RecordScreen implements Screen {
    public RecordScreen(final TapTap game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        final TextureAtlas atlasMainMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack");
        final Skin skinRecords = new Skin(Gdx.files.internal("skins/json_skins/menuSkin.json"), atlasMainMenu);
        table = new Table(skinRecords);
        exitButton = new Button(skinRecords);

        stage = new Stage();
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("skins/main_menu/background/bg_desert.png"));

        int buttonWidth = Gdx.graphics.getWidth()/3;
        int buttonHeight = Gdx.graphics.getHeight()/7;

        font = new BitmapFont(Gdx.files.internal("fonts/blackFont.fnt"), false);
        Label.LabelStyle textStyle = new Label.LabelStyle(font, Color.BLACK);
        ObjectMap<String, Object> saves = GameScreen.getStorage().getAllData();

        String[] scores = saves.toString().replace("}","").replace("{"," ").split(",");
        for (String score : scores){
            table.add(new Label(score, textStyle)).pad(10).row();
        }
        table.add(exitButton).height(buttonHeight).width(buttonWidth);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();

        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        MusicManager.play(this);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen(game));
            }
        });
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
        background.dispose();
        stage.dispose();
        font.dispose();
    }

    private BitmapFont font;

    private SpriteBatch batch;
    private Table table;
    private Button exitButton;
    private Stage stage;

    private final Texture background;
    private OrthographicCamera camera;

    private final TapTap game;
}
