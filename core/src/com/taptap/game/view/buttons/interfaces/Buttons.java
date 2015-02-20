package com.taptap.game.view.buttons.interfaces;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.view.screens.game_screen.GameRenderer;
import com.taptap.game.view.screens.game_screen.GameScreen;

public interface Buttons {
    //public void resize(int width, int height);
    public void render();
    public void setListeners(final GameWorld gameWorld);
    public void dispose();
}
