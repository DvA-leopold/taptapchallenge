package com.taptap.game.model.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.tap.icons.factory.Icon;

public class YellowIcons implements Icon {
    static {
        taptapImage = ResourceManager.getInstance().get(ResourceManager.iconYellow);
    }

    public YellowIcons(float spawnBoarderX,float spawnBoarderY){
        cords = new Rectangle(
                MathUtils.random(spawnBoarderX + 20, Gdx.graphics.getWidth() - taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-taptapImage.getHeight()-spawnBoarderY-20),
                taptapImage.getWidth(), taptapImage.getHeight()
        );
    }

    @Override
    public int getScore() {
        return 20;
    }

    @Override
    public Texture getTexture() {
        return taptapImage;
    }

    @Override
    public float getX() {
        return cords.getX();
    }

    @Override
    public float getY() {
        return cords.getY();
    }

    @Override
    public float getWidth() {
        return cords.getWidth();
    }

    @Override
    public float getHeight() {
        return cords.getHeight();
    }

    @Override
    public Rectangle getRect() {
        return cords;
    }

    private Rectangle cords;
    private static Texture taptapImage;
}
