package com.taptap.game.model.tap.icons.factory;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface Icon {
    public int getScore();

    public Sprite getSprite();

    public float getX();

    public float getY();

    public float getWidth();

    public float getHeight();
}
