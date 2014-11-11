package com.taptap.game.task.manager;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.taptap.game.screens.realisation.game.GameScreen;

public class TaskManager implements Disposable {
    public TaskManager(final GameScreen game){
        this.game = game;
    }

    @Override
    public void dispose() {
    }

    private final GameScreen game;
}
