package com.taptap.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;

public class TapTap extends Game {

    @Override
    public void create() {
        ResourceManager.getInstance().queueLoading();
        ResourceManager.getInstance().updateLoadingQueue();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render(){
        super.render();
    }

    @Override
    public void dispose(){
        ResourceManager.getInstance().dispose();
    }
}
