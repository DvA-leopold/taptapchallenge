package com.taptap.game;

import com.badlogic.gdx.Game;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.view.screens.LoadScreen;

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
