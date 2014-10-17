package com.taptap.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.screens.realisation.MainMenuScreen;

public class TapTap extends Game {

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render(){
        super.render();
    }
}
