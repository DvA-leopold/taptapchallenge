package com.taptap.game;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class testLight implements Screen {
    public testLight() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -9.8f), false);
        BodyDef circleDef = new BodyDef();
        circleDef.type = BodyDef.BodyType.DynamicBody;
        circleDef.position.set(100, 100);

        Body circleBody = world.createBody(circleDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(3f);
        FixtureDef circleFixture = new FixtureDef();
        circleFixture.shape = circleShape;
        circleFixture.density = 0.4f;
        circleFixture.friction = 0.2f;
        circleFixture.restitution = 0.0f;

        circleBody.createFixture(circleFixture);

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);

        new PointLight(rayHandler, 5000, Color.BLUE, 500, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render(world, camera.combined);
        rayHandler.updateAndRender();

        world.step(1/60f, 6,2);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;

    private RayHandler rayHandler;
}
