package com.taptap.game.view.screens.game_screen.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.view.screens.game_screen.GameScreen;
import com.taptap.game.view.buttons.interfaces.Buttons;

public class PopUpButtonInitializer implements Buttons {
    public PopUpButtonInitializer(Batch batch) {
        menuStageInit(batch);
        optionStageInit(batch);
    }

    public void menuStageInit(Batch batch) {
        int buttonWidth = Gdx.graphics.getWidth() / 3;
        int buttonHeight = Gdx.graphics.getHeight() / 7;

        pauseMenuStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()), batch);
        Skin skinMainMenu = ResourceManager.getInstance().get(ResourceManager.popUpSkin);

        skinMainMenu.getFont("blackFont").setScale(2, 2);
        resumeGameButton = new TextButton("return", skinMainMenu, "popUpButtons");
        optionGameButton = new TextButton("options", skinMainMenu, "popUpButtons");
        exitMainMenuButton = new TextButton("exit", skinMainMenu, "popUpButtons");

        resumeGameButton.pad(10);
        optionGameButton.pad(10);
        exitMainMenuButton.pad(10);

        Window pauseWindow = new Window("", skinMainMenu);
        pauseWindow.setFillParent(true);
        pauseWindow.add(resumeGameButton).width(buttonWidth).height(buttonHeight);
        pauseWindow.row();
        pauseWindow.add(optionGameButton).width(buttonWidth).height(buttonHeight);
        pauseWindow.row();
        pauseWindow.add(exitMainMenuButton).width(buttonWidth).height(buttonHeight);

        pauseMenuStage.addActor(pauseWindow);
    }

    public void optionStageInit(Batch batch) {
        int buttonWidth = Gdx.graphics.getWidth()/10;
        int buttonHeight = Gdx.graphics.getHeight()/8;
        optionMenuStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),batch);

        Skin skinOptions = ResourceManager.getInstance().get(ResourceManager.optionSkin);

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
                gameScreen.changeState(GameScreen.States.GAME_RUNNING);
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
                gameScreen.changeState(GameScreen.States.GAME_EXIT);
            }
        });
    }

    @Override
    public Stage getStage() {
        return pauseMenuStage;
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
    }

    private boolean windowOptionFlag = false;
    private Stage pauseMenuStage, optionMenuStage;
    private TextButton
            resumeGameButton,
            optionGameButton,
            exitMainMenuButton;

    private CheckBox
            musicControl,
            soundControl;

    private Button back;

}
