package com.taptap.game.screens.realisation.game_screen.tap.icons.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public interface Icon {
    public int getScore();
    public void setCoords(int x, int y, int z, int w);
    public Texture getTexture();
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();
    public Rectangle getRect();
}
