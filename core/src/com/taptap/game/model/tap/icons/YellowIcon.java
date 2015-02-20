package com.taptap.game.model.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.tap.icons.factory.Icon;

public class YellowIcon implements Icon {
    static {
        image = ResourceManager.getInstance().get(ResourceManager.iconBlue);
    }

    public YellowIcon(float spawnBoarderX, float spawnBoarderY) {
        tapSprite = new Sprite(image);
        tapSprite.setSize(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight()*0.2f);
        tapSprite.setPosition(
                MathUtils.random(spawnBoarderX + 20, Gdx.graphics.getWidth() - tapSprite.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()- tapSprite.getHeight()-spawnBoarderY-20)
        );

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(tapSprite.getX(), tapSprite.getY());
        //Body body = WorldHandler.createBody(bodyDef);
        //body.setUserData(tapSprite);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(3f);
        FixtureDef circleFixture = new FixtureDef();
        circleFixture.shape = circleShape;
        circleFixture.density = 0.4f;
        circleFixture.friction = 0.2f;
        circleFixture.restitution = 0.0f;

        //body.createFixture(circleFixture);
    }

    @Override
    public int getScore() {
        return 40;
    }

    @Override
    public Sprite getSprite() {
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
