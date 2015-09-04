package com.taptap.game.view.screens.town_screen.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.popup.windows.TownMainOptionWindow;
import com.taptap.game.view.popup.windows.TrainingCampWindow;
import com.taptap.game.view.screens.WidgetsGroup;
import com.taptap.game.view.screens.game_screen.GameScreen;

public class TownScreenWidgetsGroup implements WidgetsGroup {
    public TownScreenWidgetsGroup(final SpriteBatch batch, final OrthographicCamera camera) {
        this.camera = camera;
        float buttonSize = Gdx.graphics.getWidth() * 0.1f;
        tableXaxesPosition = Gdx.graphics.getWidth() * 0.5f;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Skin skin = (Skin) DResourceManager.
                getInstance().get("skins/town_menu/buttons/townSkin.json");
        initTableAndButtons(skin, buttonSize);

        townMainOptionWindow = new TownMainOptionWindow("",
                (Skin) DResourceManager.getInstance().
                        get("skins/pop_up/town_option_window/optionWindowSkin.json")
        );
        townMainOptionWindow.initButtonSize(buttonSize, buttonSize);
        townMainOptionWindow.createButtons();

        trainingCampWindow = new TrainingCampWindow("",
                (Skin) DResourceManager.getInstance().
                        get("skins/pop_up/training_camp_window/trainingCampSkin.json")
        );
        trainingCampWindow.createButtons();

        setListeners(null);
        stage.addActor(table);
        stage.addActor(options);
        stage.addActor(testLevelButton);
        stage.addActor(townMainOptionWindow);
        stage.addActor(trainingCampWindow);
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void setListeners(GameWorld gameWorld) {
        InputMultiplexer gestureMultiplexer = new InputMultiplexer();
        townMainOptionWindow.setListeners();
        trainingCamp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                trainingCampWindow.setVisible(true);
            }
        });
        library.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((Game) Gdx.app.getApplicationListener()).setScreen(new Screen);
                System.out.println("library");
            }
        });
        wallOfFame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((Game) Gdx.app.getApplicationListener()).setScreen(new Screen);
                System.out.println("wall of fame");
            }
        });
        bank.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((Game) Gdx.app.getApplicationListener()).setScreen(new Screen);
                System.out.println("bank");
            }
        });
        mail.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((Game) Gdx.app.getApplicationListener()).setScreen(new Screen);
                System.out.println("mail");
            }
        });
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                townMainOptionWindow.setVisible(true);
            }
        });

        testLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen((SpriteBatch) stage.getBatch()));
            }
        });

        GestureDetector gestureDetector = new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                if (!townMainOptionWindow.isVisible() && !trainingCampWindow.isVisible()) {
                    camera.position.add(-deltaX, 0, 0);
                    table.setPosition(tableXaxesPosition += deltaX, Gdx.graphics.getHeight() / 2, 0);
                    testLevelButton.setPosition(testLevelButton.getX() + deltaX, testLevelButton.getY());
                }
                return super.pan(x, y, deltaX, deltaY);
            }
        });
        gestureMultiplexer.addProcessor(gestureDetector);
        gestureMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(gestureMultiplexer);
    }

    @Override
    public void dispose() {
        stage.dispose();
        townMainOptionWindow.dispose();
        trainingCampWindow.dispose();
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setVisible(boolean visible) {
        table.setVisible(visible);
    }

    private void initTableAndButtons(final Skin skin, float buttonSize) {
        table = new Table();
        trainingCamp = new Button(skin);
        library = new Button(skin);
        wallOfFame = new Button(skin);
        bank = new Button(skin);
        mail = new Button(skin);

        options = new Button(skin);
        options.setSize(buttonSize, buttonSize);
        options.setPosition(0, Gdx.graphics.getHeight() - buttonSize);

        testLevelButton = new Button(skin);
        testLevelButton.setSize(buttonSize, buttonSize);
        testLevelButton.setPosition(Gdx.graphics.getWidth() + 100, 300);

        table.setPosition(tableXaxesPosition, Gdx.graphics.getHeight() / 2);
        table.add(trainingCamp).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        table.add(library).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        table.add(wallOfFame).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        table.add(bank).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        table.add(mail).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        //table.debug();
    }


    final private Stage stage;
    private Table table;
    private Button
            trainingCamp,
            library,
            wallOfFame,
            bank,
            mail,
            options;

    private Button testLevelButton;

    final private TownMainOptionWindow townMainOptionWindow;
    final private TrainingCampWindow trainingCampWindow;

    final private OrthographicCamera camera;
    private float tableXaxesPosition;
}
