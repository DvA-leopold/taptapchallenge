package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class gameButtonsInitializer {
    public gameButtonsInitializer(){
        atlasGameMenu = new TextureAtlas("skins/game_menu/GameOptions.pack");
        skinGameMenu = new Skin(Gdx.files.internal("skins/json_skins/optionIconSkin.json"), atlasGameMenu);
        mainTable = new Table(skinGameMenu);
        optionButton = new Button(skinGameMenu, "gameButton");
        mainTable.add(optionButton).padTop(-Gdx.graphics.getHeight()+50).padLeft(-Gdx.graphics.getWidth()+50);
    }
    public Table getTable(){
        return mainTable;
    }
    public void dispose(){
        atlasGameMenu.dispose();
        skinGameMenu.dispose();
    }
    public Button getOptionButton(){
        return optionButton;
    }

    private TextureAtlas atlasGameMenu;
    private Skin skinGameMenu;
    private Table mainTable;
    private Button optionButton;
}
