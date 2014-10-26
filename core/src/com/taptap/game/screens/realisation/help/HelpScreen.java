package com.taptap.game.screens.realisation.help;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.MainMenuScreen;

public class HelpScreen implements Screen {
    public HelpScreen(TapTap game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"), false);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("skins/help_menu/bg_shroom.png"));
        stage = new Stage();

        Label.LabelStyle headingStyle1 = new Label.LabelStyle(font, Color.BLACK);
        Label.LabelStyle headingStyle2 = new Label.LabelStyle(font, Color.WHITE);
        Label helpString1 = new Label("ПРОСТО ТЫКАЙ В ПОЯВЛЯЮЩИЕСЯ ШТУЧКИ", headingStyle1);
        Label helpString2 = new Label("нельзя тыкать в неправильные штучки", headingStyle2);
        Label helpString3 = new Label("В ПРАВИЛЬНЫЕ ШТУЧКИ ТЫКАТЬ МОЖНО", headingStyle1);
        Label helpString4 = new Label("И да прибудет с тобой сила", headingStyle2);

        table = new Table();
        table.add(helpString1).row();
        table.add(helpString2).row();
        table.add(helpString3).row().row();
        table.add(helpString4);
    }

    @Override
    public void render(float delta) {
        /*
        mBatch.setProjectionMatrix(mFixedCamera.combined);
        mBatch.begin();
        //render "static" elements
        mBatch.end();

        mBatch.setProjectionMatrix(mStageCamera.combined);
        mBatch.begin();
        //render "movable" elements
        mBatch.end();
        */
        //Gdx.gl.glClearColor(0,0,0.0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isTouched()){
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        table.setFillParent(true);
        stage.addActor(table);
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

    }

    @Override
    public void dispose() {
//        music.dispose();
        font.dispose();
        stage.dispose();
        batch.dispose();
        background.dispose();
    }

    //private MusicManager music;

    private BitmapFont font;
    private Table table;
    private Stage stage;

    private SpriteBatch batch;
    private Texture background;

    private final TapTap game;
}
