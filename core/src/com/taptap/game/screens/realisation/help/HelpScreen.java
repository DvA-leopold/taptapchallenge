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
import com.taptap.game.screens.realisation.game.button.styles.Buttons;
import com.taptap.game.screens.realisation.game.button.styles.HelpButtonInitializer;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;
import debug.statistics.FPS_MEM_DC;

public class HelpScreen implements Screen {
    public HelpScreen() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("skins/help_menu/bg_grasslands.png"));
        buttons = new HelpButtonInitializer();
/*
        font = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"), false);
        Label.LabelStyle headingStyle = new Label.LabelStyle(font, Color.BLACK);
        Label helpString1 = new Label(
                "ПРОСТО ТЫКАЙ В ПОЯВЛЯЮЩИЕСЯ ШТУЧКИ\n "+
                "пока у меня не хватило фантазии \n" +
                        "написать что-то обдуманное\n",
                headingStyle);
*/

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
        buttons.render();

        FPS_MEM_DC.drawCalls+=2;
        FPS_MEM_DC.fpsLog();
    }

    @Override
    public void resize(int width, int height) {
        buttons.resize(width, height);
    }

    @Override
    public void show() {
        buttons.setListeners(null);
        MusicManager.play(this);
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
        //font.dispose();

        batch.dispose();
        background.dispose();
        //MusicManager.dispose();
    }

    private Buttons buttons;
    //private BitmapFont font;
    private SpriteBatch batch;
    private Texture background;
}
