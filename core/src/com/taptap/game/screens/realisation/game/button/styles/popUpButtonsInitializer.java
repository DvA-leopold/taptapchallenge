package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.screens.realisation.game.GameScreen;

import javax.xml.soap.Text;

public class popUpButtonsInitializer implements Disposable {
    public popUpButtonsInitializer(GameScreen game){
        this.game = game;
        int buttonWidth = Gdx.graphics.getWidth()/3;
        int buttonHeight = Gdx.graphics.getHeight()/7;

        atlasPopupMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack");
        skinPopupMenu = new Skin(
                Gdx.files.internal("skins/json_skins/popUpSkin.json"),
                atlasPopupMenu);
        popupTable = new Table(skinPopupMenu);
        resumeGameButton = new TextButton("return", skinPopupMenu, "popUpButtons");
        optionGameButton = new TextButton("options", skinPopupMenu, "popUpButtons");
        exitMainMenuButton = new TextButton("exit", skinPopupMenu, "popUpButtons");

        resumeGameButton.pad(10);
        optionGameButton.pad(10);
        exitMainMenuButton.pad(10);

        popupTable.add().row().width(buttonWidth).height(buttonHeight);
        popupTable.add(resumeGameButton).row().width(buttonWidth).height(buttonHeight);
        popupTable.add(optionGameButton).row().width(buttonWidth).height(buttonHeight);
        popupTable.add(exitMainMenuButton);
    }

    public void setListeners(final Stage stage, final Table resumeTable, final Table optionTable){
        resumeGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.stateManager = GameScreen.StateManager.GAME_RUNNING;
                stage.clear();
                stage.addActor(resumeTable);
            }
        });
        optionGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                stage.clear();
                optionTable.setFillParent(true);
                stage.addActor(optionTable);
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

    public Table getTable(){
        return popupTable;
    }

    @Override
    public void dispose(){
        atlasPopupMenu.dispose();
        skinPopupMenu.dispose();
    }

    private TextureAtlas atlasPopupMenu;
    private Skin skinPopupMenu;
    private Table popupTable;
    private TextButton resumeGameButton;
    private TextButton optionGameButton;
    private TextButton exitMainMenuButton;

    private final GameScreen game;
}
