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
        if (saveScore.isScheduled()){
            saveScore.cancel();
        }
    }

    public Timer.Task saveScore(){
        return saveScore;
    }

    private Timer.Task saveScore = new Timer.Task() {
        @Override
        public void run() {
            if (GameScreen.getStorage().getAllData().size>3){
                GameScreen.getStorage().resetSavedData();
            }
            GameScreen.getStorage().saveDataValue("player " + GameScreen.getStorage().getAllData().size,
                    game.getTotalScore());
            game.stateManager = GameScreen.StateManager.GAME_EXIT;
        }
    };

    private final GameScreen game;
}
