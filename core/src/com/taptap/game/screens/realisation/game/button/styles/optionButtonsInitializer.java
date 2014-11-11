package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.game.GameScreen;

public class optionButtonsInitializer implements Disposable {
    public optionButtonsInitializer(final GameScreen game){
        this.game = game;
        atlasOptionMenu = new TextureAtlas("skins/game_menu/popUpButtons.pack");
        skinOptions = new Skin(Gdx.files.internal("skins/json_skins/optionIconSkin.json"), atlasOptionMenu);
        optionTable = new Table(skinOptions);
        musicControl = new Button(skinOptions, "stateOption");
        musicControl.setChecked(!MusicManager.isMusicEnable());
        soundControl = new Button(skinOptions, "stateOption");
        back = new Button(skinOptions, "arrow");

        int buttonWidth = Gdx.graphics.getWidth()/3;
        int buttonHeight = Gdx.graphics.getHeight()/7;

        final Label musicLabel = new Label("Music: ", skinOptions);
        final Label soundLabel = new Label("Sound: ", skinOptions);
        musicControl.pad(10);
        soundControl.pad(10);

        optionTable.padBottom(600).padLeft(500);
        optionTable.add(musicLabel).width(buttonWidth).height(buttonHeight);
        optionTable.add(musicControl).row();
        optionTable.add(soundLabel).width(buttonWidth).height(buttonHeight);
        optionTable.add(soundControl).row();
        optionTable.add(back).bottom().left();
        optionTable.debug();
    }

    public void setListeners(final Stage stage, final Table changeToTable){
        musicControl.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MusicManager.onOffMusic(game);
            }
        });
        soundControl.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MusicManager.onOffSound();
            }
        });
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                stage.clear();
                stage.addActor(changeToTable);
            }
        });
    }

    public Table getTale(){
        return optionTable;
    }

    @Override
    public void dispose() {
        skinOptions.dispose();
        atlasOptionMenu.dispose();
    }

    private final TextureAtlas atlasOptionMenu;
    private Button musicControl;
    private Button soundControl;
    private Button back;
    private final Table optionTable;
    private final Skin skinOptions;

    private final GameScreen game;
}
