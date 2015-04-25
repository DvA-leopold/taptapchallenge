package com.taptap.game.view.screens.game_screen.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
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

public class GameButtonsInitializer implements Buttons {
    public GameButtonsInitializer(final Batch batch) {
        float buttonSize = Gdx.graphics.getHeight()*0.15f;
        stage = new Stage(
                new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
                batch);
        Skin skin = (Skin) DResourceManager.getInstance().
                get("skins/game_menu/buttons/option_menu/optionIconSkin.json");
        table = new Table(skin);

        optionButton = new Button(skin, "popUpMenuButton");
        table.setFillParent(true);
        table.add(optionButton).
                padTop(-Gdx.graphics.getHeight() + buttonSize).
                padLeft(-Gdx.graphics.getWidth() + buttonSize).
                width(buttonSize).
                height(buttonSize);

        stage.addActor(table);
    }

    @Override
    public void setListeners(final GameWorld gameWorld) {
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.setVisible(false);
                gameWorld.changeWorldState(GameWorld.States.GAME_PAUSED);
            }
        });
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void setVisible(boolean visible) {
        table.setVisible(visible);
    }

    final private Stage stage;
    final private Table table;
    final private Button optionButton;
}
