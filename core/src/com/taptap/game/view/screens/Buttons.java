package com.taptap.game.view.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.taptap.game.model.game.world.GameWorld;

public interface Buttons {
    //public void resize(int width, int height);
    void render();
    void setListeners(final GameWorld gameWorld);
    void dispose();
    Stage getStage();
    void setVisible(boolean visible);
}
