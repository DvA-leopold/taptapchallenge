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
        taptapImage = new Sprite(ResourceManager.getInstance().get(ResourceManager.iconBlue));
        BlueIcons.taptapImage.setSize(Gdx.graphics.getHeight() * 0.2f, Gdx.graphics.getHeight() * 0.2f);
    }

    public BlueIcons(float spawnBoarderX, float spawnBoarderY){
        cords = new Rectangle(
                MathUtils.random(spawnBoarderX + 20, Gdx.graphics.getWidth() - taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-taptapImage.getHeight()-spawnBoarderY-20),
                taptapImage.getWidth(), taptapImage.getHeight()
        );
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(cords.x, cords.y);
        Body gemeBody = WorldHandler.createBody(bodyDef);
    }

    @Override
    public int getScore() {
        return 40;
    }

    @Override
    public Texture getTexture() {
        return taptapImage.getTexture();
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
        return cords.getWidth();
    }

    @Override
    public Rectangle getRect() {
        return cords;
    }


    private Rectangle cords;
    private BodyDef bodyDef;
    private static Sprite taptapImage;
}
