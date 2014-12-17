package com.taptap.game.model.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.tap.icons.factory.Icon;

public class BlueIcons implements Icon {
    static {
        taptapImage = ResourceManager.getInstance().get(ResourceManager.iconBlue);
    }

    public BlueIcons(float spawnBoarderX, float spawnBoarderY){
        coords = new Rectangle(
                MathUtils.random(spawnBoarderX + 20, Gdx.graphics.getWidth()-taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-spawnBoarderY - 20),
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

    @Override
    public Rectangle getRect() {
        return coords;
    }


    private Rectangle coords;
    private static Texture taptapImage;
}
