package com.taptap.game.model.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.tap.icons.factory.Icon;

public class RedIcons implements Icon {
    static {
        image = ResourceManager.getInstance().get(ResourceManager.iconRed);
    }

    public RedIcons(float spawnBoarderX, float spawnBoarderY) {
        taptapImage = new Sprite(image);
        taptapImage.setSize(Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.2f);
        taptapImage.setPosition(
                MathUtils.random(spawnBoarderX + 20, Gdx.graphics.getWidth() - taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-taptapImage.getHeight()-spawnBoarderY-20)
        );
    }

    @Override
    public int getScore() {
        return 20;
    }

    @Override
    public Sprite getSprite() {
        return taptapImage;
    }

    @Override
    public float getX() {
        return taptapImage.getX();
    }

    @Override
    public float getY() {
        return taptapImage.getY();
    }

    @Override
    public float getWidth() {
        return taptapImage.getWidth();
    }

    @Override
    public float getHeight() {
        return taptapImage.getHeight();
    }

    private Sprite taptapImage;
    private static Texture image;
}
