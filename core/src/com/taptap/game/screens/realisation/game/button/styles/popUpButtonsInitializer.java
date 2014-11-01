package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class popUpButtonsInitializer {
    public popUpButtonsInitializer(){
        atlasPopupMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack"); //todo change style later
        skinPopupMenu = new Skin(Gdx.files.internal("skins/json_skins/popUpSkin.json"), atlasPopupMenu);
        popupTable = new Table(skinPopupMenu);
        resumeGameButton = new TextButton("return", skinPopupMenu, "popUpButtons");
        exitMainMenuButton = new TextButton("save & exit", skinPopupMenu, "popUpButtons");
        int buttonWidth = Gdx.graphics.getWidth()/4;
        int buttonHeight = Gdx.graphics.getHeight()/10;

        resumeGameButton.pad(10); //todo add this to .json??
        exitMainMenuButton.pad(10);
        popupTable.add().row().pad(10).height(buttonHeight).width(buttonWidth);
        popupTable.add(resumeGameButton).row().pad(20).width(buttonWidth).height(buttonHeight);
        popupTable.add(exitMainMenuButton).row().pad(20);
        //popupTable.debug();
    }

    public Table getPopupTable(){
        return popupTable;
    }
    public TextButton getResumeGameButton(){
        return resumeGameButton;
    }
    public TextButton getExitMainMenuButton(){
        return exitMainMenuButton;
    }

    public void dispose(){
        atlasPopupMenu.dispose();
        skinPopupMenu.dispose();
    }

    private TextureAtlas atlasPopupMenu;
    private Skin skinPopupMenu;
    private Table popupTable;
    private TextButton resumeGameButton;
    private TextButton exitMainMenuButton;
    private Button soundButton;
}
