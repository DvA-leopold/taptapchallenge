package com.taptap.game.model.game.world;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.taptap.game.TapTap;
import com.taptap.game.model.tap.icons.factory.Icon;
import com.taptap.game.model.tap.icons.factory.ObjectsFactory;
import com.taptap.game.view.buttons.interfaces.Buttons;
import com.taptap.game.view.screens.game_screen.buttons.GameButtonsInitializer;
import com.taptap.game.view.screens.game_screen.buttons.PopUpButtonInitializer;

public class GameWorld {
    public GameWorld() {
        Box2D.init();
        world = new World(new Vector2(0, 0), false);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        objects = new ObjectsFactory(camera);

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        new PointLight(rayHandler, 5000, Color.BLUE, 500, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
    }

    public void update() {
        if (worldState != States.GAME_RUNNING) {
            return;
        }
        camera.update();
        rayHandler.update();
        world.step(1/60f, 6, 2);
        totalTime -= Gdx.graphics.getDeltaTime();

        if (objects.controlFiguresNumber() < 0) {
            changeWorldState(GameWorld.States.GAME_OVER);
        }
        if (totalTime<= 0) {
            changeWorldState(GameWorld.States.GAME_EXIT);
        }
    }

    public void initialiseButtons(final SpriteBatch batch) {
        gameButtons = new GameButtonsInitializer(batch);
        popUpButtons = new PopUpButtonInitializer(batch);
        gameButtons.setListeners(this);
        popUpButtons.setListeners(this);
    }

    public Body createBody(BodyDef bodyForCreate) {
        return world.createBody(bodyForCreate);
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
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
        switch (state){
            case GAME_RUNNING:
                objects.startGestureDetector(); // change delays, etc.
                gameButtons.setVisible(true);
                //inputMultiplexer.addProcessor(iconFactory.getGestureDetector()); //можно изменить задержку измерения и т.п
                break;
            case GAME_PAUSED:
                //inputMultiplexer.addProcessor(popUpButtons.getStage());
                objects.stopGestureDetector();
                //inputMultiplexer.removeProcessor(iconFactory.getGestureDetector());
                break;
            case GAME_EXIT:
                TapTap.getStorage().saveDataValue(
                        "player " + TapTap.getStorage().getAllData().size(),
                        getTotalScore()
                );
                break;
            case GAME_OVER:

                break;
        }
        worldState = state;
    }

    public States getWorldState() {
        return worldState;
    }

    public Array<Icon> getObjectsPool() {
        return objects.getIconsArray();
    }

    public int getTotalScore() {
        return objects.getTotalScore();
    }

    public float getTotalTime() {
        return totalTime;
    }

    /**
     * @param i gameButtons (i=0), popUpButtons (i=1)
     * @return select button stage */
    public Stage getButtonStage(int i) {
        switch (i) {
            case 0:
                return gameButtons.getStage();
            case 1:
                return popUpButtons.getStage();
            default:
                Gdx.app.log("ERROR", "wrong i type");
                return null;
        }
    }

    /**
     * @param i button type
     * @return chosen type of button */
    public Buttons getButtons(int i) {
        switch (i) {
            case 0:
                return gameButtons;
            case 1:
                return popUpButtons;
            default:
                Gdx.app.log("ERROR ", "wrong button type i");
                return null;
        }
    }

    public void dispose() {
        rayHandler.dispose();
        gameButtons.dispose();
        popUpButtons.dispose();
        world.dispose();
    }

    public enum States {
        GAME_RUNNING,
        GAME_PAUSED,
        GAME_OVER,
        GAME_EXIT;
    }

    private float totalTime = 150;
    private float alpha = 0;

    private GameButtonsInitializer gameButtons;
    private PopUpButtonInitializer popUpButtons;

    private States worldState = States.GAME_RUNNING;
    private ObjectsFactory objects;
    private final World world;

    private RayHandler rayHandler;
    private final OrthographicCamera camera;
}
