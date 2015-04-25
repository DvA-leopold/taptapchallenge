package com.taptap.game.model.game.world.bodies;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.taptap.game.model.resource.manager.DResourceManager;

/**
 * there is no physical bodies realised by this class.
 * this is only texture bodies cos of performance reasons
 */
public class SunBody {
    public SunBody(final RayHandler rayHandler) {
        fullCircleTime = 4f;
        int textureSize = (int) (Gdx.graphics.getWidth() * 0.15);
        sunSprite = new Sprite((Texture) DResourceManager.getInstance().get("bodies/sun.png"));
        sunSprite.setSize(textureSize, textureSize);
        sunSprite.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
        sunLight = new PointLight(rayHandler, 200, Color.YELLOW, 500,
                Gdx.graphics.getWidth() + sunSprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 + sunSprite.getHeight() / 2);
        circleRadius = Gdx.graphics.getWidth() -sunSprite.getWidth();
    }

    public void moveSun(float deltaX, float deltaY) {
        sunLight.setPosition(sunLight.getX() + deltaX, sunLight.getY() + deltaY);
        sunSprite.translate(deltaX, deltaY);
    }

    public Sprite getSunSprite() {
        return sunSprite;
    }

    private Vector2 movementDirectory(float delta) {
        Vector2 position = new Vector2();

        return position;
    }

    public void setCircleTime(float fullCircleTime) {
        this.fullCircleTime = fullCircleTime;
    }

    private float fullCircleTime;
    final private float circleRadius;
    private Sprite sunSprite;
    private PointLight sunLight;
}
