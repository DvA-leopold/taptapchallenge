package com.taptap.game.view.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.taptap.game.model.game.world.GameWorld;

public interface Buttons {
    //public void resize(int width, int height);
    public void render();
    public void setListeners(final GameWorld gameWorld);
    public void dispose();
    public Stage getStage();
    public void setVisible(boolean visible);
}
