package com.taptap.game.screens.realisation.help;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.MainMenuScreen;

public class HelpScreen implements Screen {
    public HelpScreen(TapTap game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"), false);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("skins/help_menu/bg_grasslands.png"));

        atlas = new TextureAtlas("skins/help_menu/buttons/helpButton.pack");
        skin = new Skin(Gdx.files.internal("skins/json_skins/helpSkin.json"), atlas);
        table = new Table(skin);
        stage = new Stage();

        button = new Button(skin, "default");

        // baobab, сиквояview
        Label.LabelStyle headingStyle1 = new Label.LabelStyle(font, Color.BLACK);
        Label.LabelStyle headingStyle2 = new Label.LabelStyle(font, Color.WHITE);
        Label helpString1 = new Label("ПРОСТО ТЫКАЙ В ПОЯВЛЯЮЩИЕСЯ ШТУЧКИ", headingStyle1);
        Label helpString2 = new Label("нельзя тыкать в неправильные штучки", headingStyle2);
        Label helpString3 = new Label("В ПРАВИЛЬНЫЕ ШТУЧКИ ТЫКАТЬ МОЖНО", headingStyle1);
        Label helpString4 = new Label("И да прибудет с тобой сила", headingStyle2);

        // todo change position and etc
        table.add(helpString1).row().pad(10);
        table.add(helpString2).row().pad(10);
        table.add(helpString3).row().pad(10);
        table.add(helpString4).row().pad(10);
        table.add(button).padLeft(10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    @Override
    public void resize(int width, int height) {
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
    public void pause() {
        MusicManager.pause(this);
    }

    @Override
    public void resume() {
        MusicManager.play(this);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        skin.dispose();
        font.dispose();
        stage.dispose();
        batch.dispose();
        background.dispose();
        MusicManager.dispose();
    }

    private TextureAtlas atlas;
    private Button button;
    private Skin skin;

    private BitmapFont font;
    private Table table;
    private Stage stage;

    private SpriteBatch batch;
    private Texture background;

    private final TapTap game;
}
