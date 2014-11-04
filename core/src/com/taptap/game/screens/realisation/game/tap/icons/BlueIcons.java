package com.taptap.game.screens.realisation.game.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.taptap.game.screens.realisation.game.tap.icons.factory.Icon;

public class BlueIcons implements Icon {
    public BlueIcons(float x, float y, int W, int H){
        taptapImage = new Texture(Gdx.files.internal("skins/game_menu/tap_icons/hud_gem_blue.png"));
        coords = new Rectangle(x,y,W,H);
    }

    @Override
    public int addScore() {
        return 40;
    }

    @Override
    public void setCoords(int x, int y, int z, int w) {
        coords.set(x,y,z,w);
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
        return coords.getWidth();
    }

    private Texture taptapImage;
    private Rectangle coords;
}
