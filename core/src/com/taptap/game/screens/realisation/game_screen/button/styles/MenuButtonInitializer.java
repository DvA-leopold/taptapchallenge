package com.taptap.game.screens.realisation.game_screen.button.styles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.screens.realisation.game_screen.GameScreen;
import com.taptap.game.screens.realisation.help_screen.HelpScreen;
import com.taptap.game.screens.realisation.records_screen.RecordScreen;

public class MenuButtonInitializer implements Buttons {
    public MenuButtonInitializer(){
        int buttonWidth = Gdx.graphics.getWidth()/2;
        int buttonHeight = Gdx.graphics.getHeight()/6;
        stage = new Stage();
        table = new Table(skinMainMenu);
        skinMainMenu = new Skin(
                Gdx.files.internal("skins/json_skins/menuSkin.json"),
                ResourceManager.getInstance().get(ResourceManager.atlasMainMenu));
        skinMainMenu.getFont("blackFont").setScale(2, 2);

        Label heading = new Label("TAP TAP Game", skinMainMenu, "default");
        buttonPlay = new TextButton("Play", skinMainMenu, "mainButtons");// from textButtonStyle .json
        buttonHelp = new TextButton("Help", skinMainMenu, "mainButtons");
        buttonRecords = new TextButton("Records", skinMainMenu, "mainButtons");
        soundButton = new TextButton("Music:", skinMainMenu,"soundTest");
        soundButton.setChecked(!MusicManager.isMusicEnable());

        buttonPlay.pad(10);
        buttonHelp.pad(10);
        buttonRecords.pad(10);
        soundButton.pad(10);

        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.add(heading).padTop(100).width(buttonWidth).height(buttonHeight);
        table.row();
        table.add(buttonPlay).width(buttonWidth).height(buttonHeight);
        table.row();
        table.add(buttonRecords).width(buttonWidth).height(buttonHeight);
        table.row();
        table.add(buttonHelp).pad(5).width(buttonWidth).height(buttonHeight);
        table.row();
        table.add(soundButton).width(buttonWidth).height(buttonHeight);

        stage.addActor(table);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    public void setListeners(final GameScreen game) {
        buttonPlay.addListener(new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
        }
        });
        buttonHelp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HelpScreen());
            }
        });
        buttonRecords.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new RecordScreen());
            }
        });
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.onOffMusic();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @Override
    public void dispose() {
        //skinMainMenu.dispose();
        stage.dispose();
    }

    private Stage stage;
    private Skin skinMainMenu;
    private Table table;
    private final TextButton
            buttonPlay,
            buttonHelp,
            soundButton,
            buttonRecords;
}
