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
import com.taptap.game.view.screens.WidgetsGroup;

public class PopUpScreenWidgetsGroup implements WidgetsGroup {
    public PopUpScreenWidgetsGroup(final Batch batch) {
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

        skin.getFont("blackFont").getData().setScale(2, 2);
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

    public void optionStageInit(final Stage stage) {
        float buttonWidth = Gdx.graphics.getWidth() * 0.1f;
        float buttonHeight = Gdx.graphics.getHeight() * 0.15f;

        Skin skinOptions = (Skin) DResourceManager.getInstance().
                get("skins/game_menu/buttons/option_menu/optionWindowSkin.json");

        musicControl = new CheckBox(null, skinOptions, "musicCheckBox");
        musicControl.setChecked(!MusicManager.isMusicEnable());
        soundControl = new CheckBox(null, skinOptions, "soundCheckBox");
        back = new Button(skinOptions, "back");

        optionWindow = new Window(I18NBundleMy.getString("options"), skinOptions);
        optionWindow.setSize(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        optionWindow.setPosition(
                Gdx.graphics.getWidth() * 0.5f - optionWindow.getWidth() * 0.5f,
                Gdx.graphics.getHeight() * 0.5f - optionWindow.getHeight() * 0.5f);
        optionWindow.add(back).width(buttonWidth * 0.7f).height(buttonHeight * 0.7f).
                top().right().expandX().colspan(2);
        optionWindow.row();
        optionWindow.add(musicControl).width(buttonWidth).height(buttonHeight).pad(10);
        optionWindow.add(soundControl).width(buttonWidth).height(buttonHeight).pad(10);

        optionWindow.setVisible(false);
        optionWindow.setModal(true);
        optionWindow.debug();
        stage.addActor(optionWindow);
    }

    @Override
    public void setListeners(final GameWorld gameWorld) {
        initOptionListeners();
        initMenuListeners();
    }

    public void initMenuListeners(/*final GameWorld gameWorld*/) {
        resumeGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameWorld.changeWorldState(GameWorld.States.GAME_RUNNING);
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
                GameWorld.changeWorldState(GameWorld.States.GAME_EXIT);
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
