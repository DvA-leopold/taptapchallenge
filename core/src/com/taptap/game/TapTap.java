package com.taptap.game;

import com.badlogic.gdx.Game;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.save.manager.StorageManager;
import com.taptap.game.view.screens.LoadScreen;

public class TapTap extends Game {

    @Override
    public void create() {
        storage = new StorageManager(true);
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

    public static StorageManager getStorage() {
        return storage;
    }

    private static StorageManager storage;
}
