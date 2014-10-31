package com.taptap.game.screens.realisation.game.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class RedIcons implements Icon {
    public RedIcons(float x, float y, int W, int H){
        // todo change to asset manager;
        taptapImage = new Texture(Gdx.files.internal("skins/game_menu/tap_icons/hud_gem_red.png"));
        coords = new Rectangle(x,y,W,H);
    }

    @Override
    public int addScore() {
        return 25;
    }

    @Override
    public void setCoords(int x, int y, int w, int h) {
        coords.set(x,y,w,h);
    }

    @Override
    public Texture getTexture() {
        return taptapImage;
    }

    @Override
    public float getX() {
        return coords.getX();
    }

    @Override
    public float getY() {
        return coords.getY();
    }

    @Override
    public float getWidth() {
        return coords.getWidth();
    }

    @Override
    public float getHeight() {
        return coords.getHeight();
    }

    private Rectangle coords;
    private Texture taptapImage;
}
