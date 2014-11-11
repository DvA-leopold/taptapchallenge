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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;

public class HelpScreen implements Screen {
    public HelpScreen(final TapTap game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"), false);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("skins/help_menu/bg_grasslands.png"));

        atlas = new TextureAtlas("skins/help_menu/buttons/helpButton.pack");
        skin = new Skin(Gdx.files.internal("skins/json_skins/helpSkin.json"), atlas);
        table = new Table(skin);
        stage = new Stage();

        button = new TextButton("menu", skin, "default");

        // baobab, сиквояview
        Label.LabelStyle headingStyle1 = new Label.LabelStyle(font, Color.BLACK);
        Label helpString1 = new Label(
                "ПРОСТО ТЫКАЙ В ПОЯВЛЯЮЩИЕСЯ ШТУЧКИ\n "+
                "иначе тебя покарает Влад\n",
                headingStyle1);

        int buttonWidth = Gdx.graphics.getWidth()/8;
        int buttonHeight = Gdx.graphics.getHeight()/7;

        table.add(helpString1).center().padLeft(Gdx.graphics.getWidth());
        table.add(button).padRight(Gdx.graphics.getWidth()-buttonWidth).
                padTop(Gdx.graphics.getHeight()-buttonHeight).
                height(buttonHeight).width(buttonWidth).reset();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();

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

        button.addListener(new ClickListener(){
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
        atlas.dispose();
        skin.dispose();
        font.dispose();
        stage.dispose();
        batch.dispose();
        background.dispose();
        //MusicManager.dispose();
    }

    private TextureAtlas atlas;
    private TextButton button;
    private Skin skin;

    private BitmapFont font;
    private Table table;
    private Stage stage;

    private SpriteBatch batch;
    private Texture background;

    private final TapTap game;
}
