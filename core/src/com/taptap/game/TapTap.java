package com.taptap.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.screens.realisation.MainMenuScreen;

public class TapTap extends Game {

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render(){
        super.render();
    }

    public BitmapFont font;
    public SpriteBatch batch;
}
