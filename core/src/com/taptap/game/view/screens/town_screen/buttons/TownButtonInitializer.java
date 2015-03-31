package com.taptap.game.view.screens.town_screen.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.Buttons;

public class TownButtonInitializer implements Buttons {
    public TownButtonInitializer(final SpriteBatch batch, final OrthographicCamera camera) {
        this.camera = camera;
        float buttonSize = Gdx.graphics.getWidth() * 0.1f;
        tableXaxesPosition = Gdx.graphics.getWidth()/2;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Skin skin = (Skin) DResourceManager.getInstance().get("skins/town_menu/buttons/townSkin.json");
        table = new Table();
        trainingCamp = new Button(skin);
        library = new Button(skin);
        wallOfFame = new Button(skin);
        bank = new Button(skin);
        mail = new Button(skin);
        options = new Button(skin);
        options.setPosition(0, Gdx.graphics.getWidth() - buttonSize * 3);
        options.setSize(buttonSize, buttonSize);

        table.setPosition(tableXaxesPosition, Gdx.graphics.getHeight() / 2);
        table.add(trainingCamp).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        table.add(library).padTop(Gdx.graphics.getHeight()/2).size(buttonSize);
        table.add(wallOfFame).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        table.add(bank).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        table.add(mail).padTop(Gdx.graphics.getHeight() / 2).size(buttonSize);
        table.debug();

        setListeners(null);

        stage.addActor(table);
        stage.addActor(options);
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void setListeners(GameWorld gameWorld) {
        InputMultiplexer gestureMultiplexer = new InputMultiplexer();
        trainingCamp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((Game) Gdx.app.getApplicationListener()).setScreen(new Screen);
                System.out.println("training camp");
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
                //((Game) Gdx.app.getApplicationListener()).setScreen(new Screen);
                System.out.println("options");
            }
        });

        GestureDetector gestureDetector = new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                camera.position.add(-deltaX, 0, 0);
                table.setPosition(tableXaxesPosition += deltaX, Gdx.graphics.getHeight() / 2, 0);
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
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setVisible(boolean visible) {
        table.setVisible(visible);
    }

    private final Stage stage;
    private final Table table;
    private final Button
            trainingCamp,
            library,
            wallOfFame,
            bank,
            mail,
            options;

    private final OrthographicCamera camera;
    private int tableXaxesPosition;
}
