package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.screens.realisation.game.GameScreen;

public class GameButtonsInitializer implements Buttons {
    public GameButtonsInitializer() {
        stage = new Stage();
        Skin skinGameMenu = new Skin(
                Gdx.files.internal("skins/json_skins/optionIconSkin.json"),
                ResourceManager.getInstance().get(ResourceManager.buttonAtlas)
        );
        Table table = new Table(skinGameMenu);
        //skinGameMenu.getFont()
        optionButton = new Button(skinGameMenu, "popUpMenuButton");
        table.setFillParent(true);
        table.add(optionButton).
                padTop(-Gdx.graphics.getHeight()+Gdx.graphics.getHeight()/10).
                padLeft(-Gdx.graphics.getWidth()+Gdx.graphics.getWidth()/15).
                width(Gdx.graphics.getWidth()/15).
                height(Gdx.graphics.getHeight()/10);

        stage.addActor(table);
    }

    public void setListeners(final GameScreen gameScreen) {
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.stateManager = GameScreen.StateManager.GAME_PAUSED;
                //stage.clear();
                //stage.addActor(changeToTable);
            }
        });
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        //skinGameMenu.dispose();
        //atlasGameMenu.dispose();
    }
    public Stage getStage(){
        return stage;
    }

    private final Stage stage;
    private final Button optionButton;
}
