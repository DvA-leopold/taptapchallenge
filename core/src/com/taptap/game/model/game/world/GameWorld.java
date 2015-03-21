package com.taptap.game.model.game.world;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.taptap.game.TapTap;
import com.taptap.game.model.tap.icons.objects.Icon;
import com.taptap.game.model.tap.icons.ObjectsFactory;
import com.taptap.game.view.screens.Buttons;
import com.taptap.game.view.screens.game_screen.buttons.GameButtonsInitializer;
import com.taptap.game.view.screens.game_screen.buttons.PopUpButtonInitializer;

import java.util.List;

public class GameWorld {
    public GameWorld(final InputMultiplexer inputMultiplexer) {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        this.inputMultiplexer = inputMultiplexer;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        objects = new ObjectsFactory(camera, world);

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setBlur(false);
        rayHandler.setShadows(false);
        new PointLight(rayHandler, 500, Color.WHITE, 1000, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()).setSoftnessLength(0);
        //new ConeLight(rayHandler, 5000, Color.YELLOW, 2000, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight(), -90f, 35f);
    }

    public void update() {
        camera.update();
        if (worldState == States.GAME_RUNNING) {
            rayHandler.update();
            world.step(1 / 60f, 6, 2);
            totalTime -= Gdx.graphics.getDeltaTime();

            if (objects.controlFiguresNumber() < 0) {
                changeWorldState(GameWorld.States.GAME_OVER); // todo make a listener
            }
            if (totalTime <= 0) {
                changeWorldState(GameWorld.States.GAME_EXIT); // todo make a listener
            }
        }
    }

    public void initializeActors(final SpriteBatch batch) {
        buttonsArray = new Buttons[]{new GameButtonsInitializer(batch), new PopUpButtonInitializer(batch)};
        for (Buttons button : buttonsArray) {
            inputMultiplexer.addProcessor(button.getStage());
            button.setListeners(this);
        }
        //inputMultiplexer.addProcessor(objects.getGestureDetector());
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public World getWorld() {
        return  world;
    }

    /**
     * every state changes should be made only by this func
     * @param state, state to change with */
    public void changeWorldState(States state) {
        switch (state) {
            case GAME_PREPARING:
                //inputMultiplexer.removeProcessor(objects.getGestureDetector());
                break;
            case GAME_RUNNING:
                inputMultiplexer.addProcessor(objects.getGestureDetector());
                buttonsArray[0].setVisible(true);
                break;
            case GAME_PAUSED:
                inputMultiplexer.removeProcessor(objects.getGestureDetector());
                buttonsArray[1].setVisible(true);
                break;
            case GAME_EXIT:
                TapTap.getStorage().saveDataValue(
                        "player " + TapTap.getStorage().getAllData().size(),
                        getTotalScore()
                );
                break;
            case GAME_OVER:

                break;
            default:

                break;
        }
        worldState = state;
    }

    public States getWorldState() {
        return worldState;
    }

    public List<Icon> getObjectsPool() {
        return objects.getIconsList();
    }

    public int getTotalScore() {
        return objects.getTotalScore();
    }

    public float getTotalTime() {
        return totalTime;
    }

    public Buttons[] getButtonsArray() {
        return buttonsArray;
    }

    public void dispose() {
        for (Buttons button : buttonsArray) {
            button.dispose();
        }
        rayHandler.getLightMapBuffer().dispose();
        rayHandler.getLightMapTexture().dispose();
        //rayHandler.dispose(); todo fix error when dispose this

        Array<Body> worldBodies = new Array<Body>(world.getBodyCount());
        world.getBodies(worldBodies);
        for (Body body : worldBodies) {
            world.destroyBody(body);
        }
        world.dispose();
    }

    public enum States {
        GAME_PREPARING,
        GAME_RUNNING,
        GAME_PAUSED,
        GAME_OVER,
        GAME_EXIT
    }

    private float totalTime = 150;

    private Buttons[] buttonsArray;

    private States worldState = States.GAME_PREPARING;
    private ObjectsFactory objects;

    private World world;
    private final InputMultiplexer inputMultiplexer;
    private RayHandler rayHandler;

    private final OrthographicCamera camera;
}
