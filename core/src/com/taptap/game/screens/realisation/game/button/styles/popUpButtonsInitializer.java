package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.screens.realisation.game.GameScreen;

public class popUpButtonsInitializer {
    public popUpButtonsInitializer(GameScreen game){
        this.game = game;

        atlasPopupMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack");
        skinPopupMenu = new Skin(Gdx.files.internal("skins/json_skins/popUpSkin.json"), atlasPopupMenu);
        popupTable = new Table(skinPopupMenu);
        resumeGameButton = new TextButton("return", skinPopupMenu, "popUpButtons");
        exitMainMenuButton = new TextButton("exit", skinPopupMenu, "popUpButtons");
        int buttonWidth = Gdx.graphics.getWidth()/3;
        int buttonHeight = Gdx.graphics.getHeight()/7; // todo change to global variables
        resumeGameButton.pad(10);
        exitMainMenuButton.pad(10);

        popupTable.add().row().pad(10).height(buttonHeight).width(buttonWidth);
        popupTable.add(resumeGameButton).row().pad(20).width(buttonWidth).height(buttonHeight);
        popupTable.add(exitMainMenuButton).row().pad(20);
    }
    public void setListeners(final Stage stage,final Table changeToTable){
        resumeGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.stateManager = GameScreen.StateManager.GAME_RUNNING;
                stage.clear();
                stage.addActor(changeToTable);
            }
        });
        exitMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.stateManager = GameScreen.StateManager.GAME_EXIT;
            }
        });
    }

    public Table getPopupTable(){
        return popupTable;
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

    private final GameScreen game;
}
