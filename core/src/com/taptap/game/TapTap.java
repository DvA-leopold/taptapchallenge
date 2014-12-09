package com.taptap.game;

import com.badlogic.gdx.Game;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;

public class TapTap extends Game {

    @Override
    public void create() {
        ResourceManager.queueLoading();
        ResourceManager.updateLoadingQueue();
        setScreen(new MainMenuScreen());
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
