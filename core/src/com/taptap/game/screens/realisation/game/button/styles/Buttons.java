package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.taptap.game.screens.realisation.game.GameScreen;

public interface Buttons {
    public void resize(int width, int height);
    public void render();
    public void setListeners(final GameScreen screen);
    public Stage getStage();
    public void dispose();

}
