package com.taptap.game;

import com.badlogic.gdx.Game;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.screens.realisation.LoadScreen;

public class TapTap extends Game {

    @Override
    public void create() {


        setScreen(new LoadScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        ResourceManager.getInstance().dispose();
    }
}
