package com.taptap.game.view.screens.town_screen.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.Buttons;

public class TownButtonInitializer implements Buttons {
    public TownButtonInitializer(final SpriteBatch batch, final OrthographicCamera camera) {
        this.camera = camera;
        float buttonWidth = Gdx.graphics.getWidth() * 0.1f;
        float buttonHeight = Gdx.graphics.getHeight() * 0.15f;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Skin skin = (Skin) DResourceManager.getInstance().get(""); // TODO: write skin file
        table = new Table();
        trainingCamp = new Button(skin, "");
        library = new Button(skin, "");
        wallOfFame = new Button(skin, "");
        bank = new Button(skin, "");
        mail = new Button(skin, "");
        options = new Button(skin, "");

        energy = new ProgressBar(0f, 100f, 10f, false, skin);

        setListeners(null);

        stage.addActor(table);
    }

    @Override
    public void render() {

    }

    @Override
    public void setListeners(GameWorld gameWorld) {
        gestureDetector = new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                camera.position.add(-deltaX, 0, 0);
                return super.pan(x, y, deltaX, deltaY);
            }
        });
        Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public Stage getStage() {
        return null;
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
    private final ProgressBar energy;

    private final OrthographicCamera camera;

    private GestureDetector gestureDetector;
}
