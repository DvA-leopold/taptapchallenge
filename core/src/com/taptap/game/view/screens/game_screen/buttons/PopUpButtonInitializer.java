package com.taptap.game.view.screens.game_screen.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.TapTap;
import com.taptap.game.internationalization.I18NBundleMy;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.Buttons;

public class PopUpButtonInitializer implements Buttons {
    public PopUpButtonInitializer(final Batch batch) {
        stage = new Stage(
                new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
                batch);
        menuStageInit(stage);
        optionStageInit(stage);
    }

    public void menuStageInit(final Stage stage) {
        float buttonWidth = Gdx.graphics.getWidth() * 0.3f;
        float buttonHeight = Gdx.graphics.getHeight() * 0.15f;

        Skin skin = (Skin) DResourceManager.getInstance().
                get("skins/game_menu/buttons/pop_up/popUpSkin.json");

        skin.getFont("blackFont").setScale(2, 2);
        resumeGameButton = new TextButton(I18NBundleMy.getString("return"), skin, "popUpButtons");
        optionGameButton = new TextButton(I18NBundleMy.getString("options"), skin, "popUpButtons");
        exitMainMenuButton = new TextButton(I18NBundleMy.getString("exit"), skin, "popUpButtons");

        resumeGameButton.pad(10);
        optionGameButton.pad(10);
        exitMainMenuButton.pad(10);

        pauseWindow = new Window("", skin);
        pauseWindow.setFillParent(true);
        pauseWindow.add(resumeGameButton).width(buttonWidth).height(buttonHeight);
        pauseWindow.row();
        pauseWindow.add(optionGameButton).width(buttonWidth).height(buttonHeight);
        pauseWindow.row();
        pauseWindow.add(exitMainMenuButton).width(buttonWidth).height(buttonHeight);

        pauseWindow.setVisible(false);
        stage.addActor(pauseWindow);
    }

    //TODO: change this window to custom OptionWindow from popup package
    public void optionStageInit(final Stage stage) {
        float buttonWidth = Gdx.graphics.getWidth() * 0.1f;
        float buttonHeight = Gdx.graphics.getHeight() * 0.15f;

        Skin skinOptions = (Skin) DResourceManager.getInstance().
                get("skins/game_menu/buttons/option_menu/optionIconSkin.json");

        musicControl = new CheckBox(null, skinOptions, "stateOption");
        musicControl.setChecked(!MusicManager.isMusicEnable());
        soundControl = new CheckBox(null, skinOptions, "stateOption");
        back = new Button(skinOptions, "arrow");

        skinOptions.getFont("whiteFont").setScale(2, 2);
        final Label musicLabel = new Label(I18NBundleMy.getString("music1"), skinOptions);
        final Label soundLabel = new Label(I18NBundleMy.getString("sound"), skinOptions);

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
                pauseWindow.setVisible(false);
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
                ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().onOffMusic();
            }
        });
        soundControl.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO: switch the sound
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
    public void setVisible(boolean visible) {
        pauseWindow.setVisible(visible);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Window optionWindow, pauseWindow;
    final private Stage stage;
    private TextButton
            resumeGameButton,
            optionGameButton,
            exitMainMenuButton;

    private CheckBox
            musicControl,
            soundControl;

    private Button back;
}
