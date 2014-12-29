package com.taptap.game.model.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.tap.icons.factory.Icon;
import com.taptap.game.model.world.manager.WorldHandler;

public class BlueIcons implements Icon {
    static {
        image = ResourceManager.getInstance().get(ResourceManager.iconBlue);
    }

    public BlueIcons(float spawnBoarderX, float spawnBoarderY){
        tapSprite = new Sprite(image);
        tapSprite.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight()*0.2f);
        tapSprite.setPosition(
                MathUtils.random(spawnBoarderX + 20, Gdx.graphics.getWidth() - tapSprite.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()- tapSprite.getHeight()-spawnBoarderY-20)
        );

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.position.set(cords.x, cords.y);
        Body body = WorldHandler.createBody(bodyDef);
        body.setUserData(tapSprite);
    }

    @Override
    public int getScore() {
        return 40;
    }

    @Override
    public Sprite getTexture() {
        return tapSprite;
    }

    @Override
    public float getX() {
        return tapSprite.getX();
    }

    @Override
    public float getY() {
        return tapSprite.getY();
    }

    @Override
    public float getWidth() {
        return tapSprite.getWidth();
    }

    @Override
    public float getHeight() {
        return tapSprite.getWidth();
    }

    private BodyDef bodyDef;
    private Sprite tapSprite;
    private static Texture image;
}
