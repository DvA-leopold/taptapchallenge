package com.taptap.game.model.world.manager;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class WorldHandler {
    static {
        world = new World(new Vector2(0,0), false);
    }

    public WorldHandler(final Camera camera) {
        this.camera = camera;
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        renderer = new Box2DDebugRenderer();
        new PointLight(rayHandler, 5000, Color.BLUE, 500, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        new PointLight(rayHandler, 5000, Color.MAGENTA, 500,
                Gdx.graphics.getWidth()- Gdx.graphics.getWidth()/3,
                Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/3);
    }

    public void renderWorld() {
        renderer.render(world, camera.combined);
        rayHandler.updateAndRender();

        world.step(1/60f, 6,2);
    }

    public static Body createBody(BodyDef bodyForCreate) {
        return world.createBody(bodyForCreate);
    }

    public static void destroyBody(Body body) {
        world.destroyBody(body);
    }

    private static final World world;
    private final Box2DDebugRenderer renderer;
    private final Camera camera;
    private RayHandler rayHandler;
}
