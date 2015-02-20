package com.taptap.game.view.screens.game_screen.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.view.buttons.interfaces.Buttons;

public class PopUpButtonInitializer implements Buttons {
    public PopUpButtonInitializer(final Batch batch) {
        stage = new Stage(
                new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),
                batch);
        menuStageInit(stage);
        optionStageInit(stage);
    }

    public void menuStageInit(final Stage stage) {
        float buttonWidth = Gdx.graphics.getWidth() * 0.3f;
        float buttonHeight = Gdx.graphics.getHeight() * 0.15f;

        Skin skinMainMenu = ResourceManager.getInstance().get(ResourceManager.popUpSkin);

        skinMainMenu.getFont("blackFont").setScale(2, 2);
        resumeGameButton = new TextButton("return", skinMainMenu, "popUpButtons");
        optionGameButton = new TextButton("options", skinMainMenu, "popUpButtons");
        exitMainMenuButton = new TextButton("exit", skinMainMenu, "popUpButtons");

        resumeGameButton.pad(10);
        optionGameButton.pad(10);
        exitMainMenuButton.pad(10);

        pauseWindow = new Window("", skinMainMenu);
        pauseWindow.setFillParent(true);
        pauseWindow.add(resumeGameButton).width(buttonWidth).height(buttonHeight);
        pauseWindow.row();
        pauseWindow.add(optionGameButton).width(buttonWidth).height(buttonHeight);
        pauseWindow.row();
        pauseWindow.add(exitMainMenuButton).width(buttonWidth).height(buttonHeight);

        stage.addActor(pauseWindow);
    }

    public void optionStageInit(final Stage stage) {
        float buttonWidth = Gdx.graphics.getWidth()*0.1f;
        float buttonHeight = Gdx.graphics.getHeight()*0.15f;

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
        optionWindow = new Window("options", skinOptions);
        optionWindow.setFillParent(true);
        optionWindow.padTop(Value.percentHeight(0.35f)).padRight(Value.percentWidth(0.20f));
        optionWindow.add(musicLabel).center();
        optionWindow.add(musicControl).width(buttonWidth).height(buttonHeight);
        optionWindow.row();
        optionWindow.add(soundLabel).center();
        optionWindow.add(soundControl).width(buttonWidth).height(buttonHeight);
        optionWindow.row();
        optionWindow.add(back).width(buttonWidth).height(buttonHeight).bottom().left().expand();

        optionWindow.setVisible(false);
        stage.addActor(optionWindow);
    }

    @Override
    public void setListeners(final GameWorld gameWorld) {
        initOptionListeners();
        initMenuListeners(gameWorld);
    }

    public void initMenuListeners(final GameWorld gameWorld) {
        resumeGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameWorld.changeWorldState(GameWorld.States.GAME_RUNNING);

            }
        });
        optionGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseWindow.setVisible(false);
                optionWindow.setVisible(true);
            }
        });
        exitMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameWorld.changeWorldState(GameWorld.States.GAME_EXIT);
                pauseWindow.setVisible(false);
            }
        });
    }

    public void initOptionListeners() {
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
                optionWindow.setVisible(false);
                pauseWindow.setVisible(true);
            }
        });
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Window optionWindow, pauseWindow;
    private final Stage stage;
    private TextButton
            resumeGameButton,
            optionGameButton,
            exitMainMenuButton;

    private CheckBox
            musicControl,
            soundControl;

    private Button back;

}
