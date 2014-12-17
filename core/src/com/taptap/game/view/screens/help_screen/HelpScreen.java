package com.taptap.game.view.screens.help_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.view.screens.game_screen.button.styles.Buttons;
import com.taptap.game.view.screens.game_screen.button.styles.HelpButtonInitializer;
import debug.statistics.FPS_MEM_DC;

public class HelpScreen implements Screen {
    public HelpScreen() {
        batch = new SpriteBatch();
        background = ResourceManager.getInstance().get(ResourceManager.helpBackground);
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
        batch.dispose();
        //background.dispose();
    }

    private Buttons buttons;
    private SpriteBatch batch;
    private Texture background;
}
