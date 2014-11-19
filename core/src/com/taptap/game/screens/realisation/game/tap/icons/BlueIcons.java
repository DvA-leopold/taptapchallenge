package com.taptap.game.screens.realisation.game.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.taptap.game.screens.realisation.game.tap.icons.factory.Icon;

public class BlueIcons implements Icon {
    static{
        taptapImage = new Texture(Gdx.files.internal("skins/game_menu/tap_icons/hud_gem_blue.png"));
    }

    public BlueIcons(float blockHeight, float blockWidth){
        coords = new Rectangle(
                MathUtils.random(blockHeight + 20, Gdx.graphics.getWidth()-taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-blockWidth - 20),
                taptapImage.getWidth(), taptapImage.getHeight()
        );
    }

    @Override
    public int getScore() {
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

    private Rectangle coords;

    private static Texture taptapImage;
}
