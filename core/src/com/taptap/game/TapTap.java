package com.taptap.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taptap.game.debug.Debug;
import com.taptap.game.debug.DebugShader;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.model.save.manager.StorageManager;
import com.taptap.game.versioning.VersionHandler;
import com.taptap.game.view.screens.LoadScreen;

public final class TapTap extends Game {
    @Override
    public void create() {
        //class for debug
        debug = new Debug();
        // save score manager
        storage = new StorageManager(true);
        // first screen
        if (!debug.isWindows()) {
            mainBatch = new SpriteBatch(5000, DebugShader.createDefaultShader());
        } else {
            mainBatch = new SpriteBatch();
        }
        setScreen(new LoadScreen(mainBatch));
    }

    @Override
    public void render() {
        super.render();
        debug.render(mainBatch);
        VersionHandler.render(mainBatch);
    }

    @Override
    public void dispose() {
        DResourceManager.getInstance().dispose();
        mainBatch.dispose();
        debug.dispose();
    }

    public static StorageManager getStorage() {
        return storage;
    }

    private Debug debug;
    private static StorageManager storage;

    private SpriteBatch mainBatch;
}
