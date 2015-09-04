package com.taptap.game.model.game.world;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.taptap.game.model.game.world.bodies.SunBody;
import com.taptap.game.model.tap.icons.ObjectsFactory;
import com.taptap.game.model.tap.icons.objects.Icon;

import java.util.List;

public class GameWorld {
    public GameWorld() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        objects = new ObjectsFactory(world);

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setBlur(false);
        rayHandler.setShadows(true);
        rayHandler.setAmbientLight(0.8f);
        sun = new SunBody(rayHandler);
    }

    public void update() {
        camera.update();
        if (worldState == States.GAME_RUNNING) {
            sun.moveSun(-0.5f, 0);
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
/*
    public void initializeActors(final SpriteBatch batch) {
        widgetsGroupArray = new WidgetsGroup[] {new GameScreenWidgetsGroup(batch), new PopUpScreenWidgetsGroup(batch)};
        for (WidgetsGroup button : widgetsGroupArray) {
            inputMultiplexer.addProcessor(button.getStage());
            button.setListeners(this);
        }
    }
*/
    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public World getWorld() {
        return  world;
    }

    /**
     * every newWorldState changes should be made only by this func
     * @param newWorldState, newWorldState to change with */
    public static void changeWorldState(States newWorldState) {
        switch (newWorldState) {
            case GAME_PREPARING:
                //inputMultiplexer.removeProcessor(objects.getGestureDetector());
                break;
            case GAME_RUNNING:
                //inputMultiplexer.addProcessor(objects.getGestureDetector());
                //widgetsGroupArray[0].setVisible(true);
                break;
            case GAME_PAUSED:
                //inputMultiplexer.removeProcessor(objects.getGestureDetector());

                //widgetsGroupArray[1].setVisible(true);
                break;
            case GAME_EXIT:
                /*
                TapTap.getStorage().saveDataValue(
                        "player " + TapTap.getStorage().getAllData().size(),
                        getTotalScore()
                );
                */
                // switch to another screen in gameRenderer
                break;
            case GAME_OVER:

                break;
            default:

                break;
        }
        worldState = newWorldState;
    }

    public static States getWorldState() {
        return worldState;
    }

    public List<Icon> getObjectsPool() {
        return objects.getIconsList();
    }

    public SunBody getSun() {
        return sun;
    }

    public int getTotalScore() {
        return objects.getTotalScore();
    }

    public float getTotalTime() {
        return totalTime;
    }

    public GestureDetector getFactoryGestureDetector() {
        return objects.getGestureDetector();
    }

    public void dispose() {
        //for (WidgetsGroup button : widgetsGroupArray) {
        //    button.dispose();
        //}
        rayHandler.getLightMapBuffer().dispose();
        rayHandler.getLightMapTexture().dispose();
        rayHandler.dispose();

        Array<Body> worldBodies = new Array<>(world.getBodyCount());
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

    //private WidgetsGroup[] widgetsGroupArray;

    private static States worldState = States.GAME_PREPARING;
    private ObjectsFactory objects;
    private SunBody sun;

    private World world;
    private RayHandler rayHandler;

    final private OrthographicCamera camera;
}
