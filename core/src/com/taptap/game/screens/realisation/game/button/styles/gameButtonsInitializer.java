package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.screens.realisation.game.GameScreen;

public class gameButtonsInitializer {
    public gameButtonsInitializer(GameScreen game){
        this.game = game;
        atlasGameMenu = new TextureAtlas("skins/game_menu/gameOption.pack");
        skinGameMenu = new Skin(Gdx.files.internal("skins/json_skins/optionIconSkin.json"), atlasGameMenu);
        mainTable = new Table(skinGameMenu);
        optionButton = new Button(skinGameMenu, "gameButton");
        mainTable.add(optionButton).padTop(-Gdx.graphics.getHeight()+Gdx.graphics.getHeight()/10).
                padLeft(-Gdx.graphics.getWidth()+Gdx.graphics.getWidth()/15).
                width(Gdx.graphics.getWidth()/15).
                height(Gdx.graphics.getHeight()/10);
    }

    public void setListeners(final Stage stage, final Table changeToTable){
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.stateManager = GameScreen.StateManager.GAME_PAUSED;
                stage.clear();
                stage.addActor(changeToTable);
            }
        });
    }
    public Button getOptionButton(){
        return optionButton;
    }

    public Table getTable(){
        return mainTable;
    }
    public void dispose(){
        atlasGameMenu.dispose();
        skinGameMenu.dispose();
    }

    private TextureAtlas atlasGameMenu;
    private Skin skinGameMenu;
    private Table mainTable;
    private Button optionButton;

    private final GameScreen game;
}
