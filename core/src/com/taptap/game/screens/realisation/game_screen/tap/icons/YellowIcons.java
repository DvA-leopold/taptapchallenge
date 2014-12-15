package com.taptap.game.screens.realisation.game_screen.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.taptap.game.screens.realisation.game_screen.tap.icons.factory.Icon;

public class YellowIcons implements Icon {
    static {
        taptapImage = new Texture(Gdx.files.internal("skins/game_menu/tap_icons/hud_gem_yellow.png"));
    }

    public YellowIcons(float spawnBoarderX,float spawnBoarderY){
        coords = new Rectangle(
                MathUtils.random(spawnBoarderX + 20, Gdx.graphics.getWidth() - taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-taptapImage.getHeight()-spawnBoarderY-20),
                taptapImage.getWidth(), taptapImage.getHeight());
    }

    @Override
    public int getScore() {
        return 20;
    }

    @Override
    public void setCoords(int x, int y, int z, int w) {

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

    @Override
    public Rectangle getRect() {
        return coords;
    }

    private Rectangle coords;

    private static Texture taptapImage;
}
