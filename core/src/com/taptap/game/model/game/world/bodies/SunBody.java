package com.taptap.game.model.game.world.bodies;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.taptap.game.model.resource.manager.DResourceManager;

/**
 * there is no physical bodies realised by this class.
 * this is only texture bodies cos of performance reasons
 */
public class SunBody {
    public SunBody(final RayHandler rayHandler) {
        fullCircleTime = 4f;
        int textureSize = (int) (Gdx.graphics.getWidth() * 0.10);
        sunSprite = new Sprite((Texture) DResourceManager.getInstance().get("bodies/sun.png"));
        sunSprite.setSize(textureSize, textureSize);
        sunSprite.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3);
        sunLight = new PointLight(rayHandler, 400, Color.YELLOW, 400,
                Gdx.graphics.getWidth() + sunSprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 3 + sunSprite.getHeight() / 2);
    }

    public void moveSun(float deltaX, float deltaY) {
        sunLight.setPosition(sunLight.getX() + deltaX, sunLight.getY() + deltaY);
        sunSprite.translate(deltaX, deltaY);
    }

    public Sprite getSunSprite() {
        return sunSprite;
    }


    private float fullCircleTime;
    private Sprite sunSprite;
    private PointLight sunLight;
}
