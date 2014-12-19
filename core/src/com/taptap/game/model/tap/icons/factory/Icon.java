package com.taptap.game.model.tap.icons.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public interface Icon {
    public int getScore();
    public Texture getTexture();
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();
    public Rectangle getRect();
}