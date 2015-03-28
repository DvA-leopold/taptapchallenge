package com.taptap.game.model.tap.icons.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.taptap.game.model.resource.manager.DResourceManager;

public class BlueIcon implements Icon {
    static {
        image = (Texture) DResourceManager.getInstance().
                get("skins/game_menu/tap_icons/Farmer2.png");
    }

    public BlueIcon(Vector2 spawnBoarder, final World world) {
        tapSprite = new Sprite(image);
        tapSprite.setSize(Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.2f);
        tapSprite.setPosition(
                MathUtils.random(spawnBoarder.x + 20, Gdx.graphics.getWidth() - tapSprite.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight() - tapSprite.getHeight() - spawnBoarder.y - 20)
        );

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(tapSprite.getX(), tapSprite.getY());
        body = world.createBody(bodyDef);
        body.setUserData(tapSprite);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(3f);
        FixtureDef circleFixture = new FixtureDef();
        circleFixture.shape = circleShape;
        circleFixture.density = 0.4f;
        circleFixture.friction = 0.2f;
        circleFixture.restitution = 0.0f;

        body.createFixture(circleFixture);
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

    @Override
    public void destroyBody() {
        body.getWorld().destroyBody(body);
    }

    private Body body;
    private Sprite tapSprite;
    private static Texture image;
}
