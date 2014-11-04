package com.taptap.game.screens.realisation.game.tap.icons.factory;

import com.badlogic.gdx.graphics.Texture;

public interface Icon {
    public int addScore();
    public void setCoords(int x, int y, int z, int w);
    public Texture getTexture();
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();
}
