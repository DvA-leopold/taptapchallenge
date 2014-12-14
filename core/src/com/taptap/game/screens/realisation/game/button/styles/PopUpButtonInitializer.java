package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.screens.realisation.game.GameScreen;

public class PopUpButtonInitializer implements Buttons {
    public PopUpButtonInitializer() {
        menuStageInit();
        optionStageInit();
    }

    public void menuStageInit() {
        int buttonWidth = Gdx.graphics.getWidth() / 3;
        int buttonHeight = Gdx.graphics.getHeight() / 7;
        pauseMenuStage = new Stage();
        Skin skinPopupMenu = new Skin(
                Gdx.files.internal("skins/json_skins/popUpSkin.json"),
                ResourceManager.getInstance().get(ResourceManager.atlasPopupMenu)
        );
        skinPopupMenu.getFont("blackFont").setScale(2, 2);
        resumeGameButton = new TextButton("return", skinPopupMenu, "popUpButtons");
        optionGameButton = new TextButton("options", skinPopupMenu, "popUpButtons");
        exitMainMenuButton = new TextButton("exit", skinPopupMenu, "popUpButtons");

        resumeGameButton.pad(10);
        optionGameButton.pad(10);
        exitMainMenuButton.pad(10);

        Window pauseWindow = new Window("", skinPopupMenu);
        pauseWindow.setFillParent(true);
        pauseWindow.add(resumeGameButton).width(buttonWidth).height(buttonHeight);
        pauseWindow.row();
        pauseWindow.add(optionGameButton).width(buttonWidth).height(buttonHeight);
        pauseWindow.row();
        pauseWindow.add(exitMainMenuButton).width(buttonWidth).height(buttonHeight);

        pauseMenuStage.addActor(pauseWindow);
    }

    public void optionStageInit() {
        int buttonWidth = Gdx.graphics.getWidth()/10;
        int buttonHeight = Gdx.graphics.getHeight()/8;
        optionMenuStage = new Stage();

        Skin skinOptions = new Skin(
                Gdx.files.internal("skins/json_skins/optionIconSkin.json"),
                ResourceManager.getInstance().get(ResourceManager.atlasOptionMenu)
        );
        //Table optionWindow = new Table(skinOptions);
        musicControl = new CheckBox(null, skinOptions, "stateOption");
        musicControl.setChecked(!MusicManager.isMusicEnable());
        soundControl = new CheckBox(null, skinOptions, "stateOption");
        back = new Button(skinOptions, "arrow");

        skinOptions.getFont("whiteFont").setScale(2, 2);
        final Label musicLabel = new Label("Music: ", skinOptions);
        final Label soundLabel = new Label("Sound: ", skinOptions);

        //optionWindow.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Window optionWindow = new Window("options", skinOptions);
        optionWindow.setFillParent(true);
        optionWindow.padTop(Value.percentHeight(0.35f)).padRight(Value.percentWidth(0.20f));
        optionWindow.add(musicLabel).center();
        optionWindow.add(musicControl).width(buttonWidth).height(buttonHeight);
        optionWindow.row();
        optionWindow.add(soundLabel).center();
        optionWindow.add(soundControl).width(buttonWidth).height(buttonHeight);
        optionWindow.row();
        optionWindow.add(back).width(buttonWidth).height(buttonHeight).bottom().left().expand();

        optionMenuStage.addActor(optionWindow);
    }

    @Override
    public void setListeners(final GameScreen gameScreen) {
        initOptionListeners(gameScreen);
        initMenuListeners(gameScreen);
    }

    public void initOptionListeners(final GameScreen gameScreen){
        musicControl.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.onOffMusic();
            }
        });
        soundControl.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.onOffSound();
            }
        });
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.inputMultiplexer.addProcessor(pauseMenuStage);
                gameScreen.inputMultiplexer.removeProcessor(optionMenuStage);
                windowOptionFlag = false;
            }
        });
    }

    public void initMenuListeners(final GameScreen gameScreen){
        resumeGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.stateManager = GameScreen.StateManager.GAME_RUNNING;
                gameScreen.inputMultiplexer.removeProcessor(pauseMenuStage);
            }
        });
        optionGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.inputMultiplexer.removeProcessor(pauseMenuStage);
                gameScreen.inputMultiplexer.addProcessor(optionMenuStage);
                windowOptionFlag = true;
            }
        });
        exitMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.stateManager = GameScreen.StateManager.GAME_EXIT;
            }
        });
    }

    @Override
    public Stage getStage() {
        return pauseMenuStage;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        if (!windowOptionFlag){
            pauseMenuStage.act(); // это можно убрать если у нас не будет ресайза
            pauseMenuStage.draw();
        } else {
            optionMenuStage.act();
            optionMenuStage.draw();
        }
    }

    @Override
    public void dispose() {
        pauseMenuStage.dispose();
        optionMenuStage.dispose();
        //skinPopupMenu.dispose();
        //atlasPopupMenu.dispose();
    }

    private boolean windowOptionFlag = false;
    private Stage pauseMenuStage, optionMenuStage;
    private TextButton
            resumeGameButton,
            optionGameButton,
            exitMainMenuButton;

    private CheckBox // todo change to checkbox
            musicControl,
            soundControl;

    private Button back;

}
