package com.taptap.game.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.mainmenu_screen.MainMenuScreen;

public class LoadScreen implements Screen {
    public LoadScreen(final SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int progress = (int)(DResourceManager.getInstance().updateAndGetProgress()*100);
        float position = 100;
        batch.begin();
        for (int i=0; i<progress/8; ++i){
            batch.draw(barHorizontalMid, 70, position);
            position+=barHorizontalMid.getWidth();
        }
        batch.end();
        if (progress>=100){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(batch));
        }
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void show() {
        barHorizontalMid = new Texture("skins/load_menu/bar_yellow_mid.png");
        DResourceManager.getInstance().loadSection("fonts", false);
        DResourceManager.getInstance().loadSection("music", false);
        DResourceManager.getInstance().loadSection("skins", false);
        DResourceManager.getInstance().loadSection("bodies", false);
        DResourceManager.getInstance().loadSection("sectors", false);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        barHorizontalMid.dispose();
    }

    private Texture barHorizontalMid;
    private SpriteBatch batch;
}
