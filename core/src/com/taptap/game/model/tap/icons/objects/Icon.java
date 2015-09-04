package com.taptap.game.model.tap.icons.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface Icon {
    int getScore();
    Sprite getSprite();

    float getX();
    float getY();

    float getWidth();
    float getHeight();

    void destroyBody();

    float ICON_SIZE_X = Gdx.graphics.getHeight() * 0.25f;
    float ICON_SIZE_Y = Gdx.graphics.getWidth() * 0.25f;
}
