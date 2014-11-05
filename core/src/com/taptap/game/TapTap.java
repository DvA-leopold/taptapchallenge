package com.taptap.game;

import com.badlogic.gdx.Game;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;

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
