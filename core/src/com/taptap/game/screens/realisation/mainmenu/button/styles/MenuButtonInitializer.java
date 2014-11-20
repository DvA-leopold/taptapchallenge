package com.taptap.game.screens.realisation.mainmenu.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.game.GameScreen;
import com.taptap.game.screens.realisation.help.HelpScreen;
import com.taptap.game.screens.realisation.records.RecordScreen;

public class MenuButtonInitializer implements Disposable {
    public MenuButtonInitializer(final TapTap game){
        this.game = game;
        int buttonWidth = Gdx.graphics.getWidth()/2;
        int buttonHeight = Gdx.graphics.getHeight()/6;
        table = new Table(skinMainMenu);
        atlasMainMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack");
        skinMainMenu = new Skin(Gdx.files.internal("skins/json_skins/menuSkin.json"), atlasMainMenu);
        skinMainMenu.getFont("blackFont").setScale(2, 2);

        Label heading = new Label("TAP TAP Game", skinMainMenu, "default");
        buttonPlay = new TextButton("Play", skinMainMenu, "mainButtons");// from textButtonStyle .json
        buttonHelp = new TextButton("Help", skinMainMenu, "mainButtons");
        buttonRecords = new TextButton("Records", skinMainMenu, "mainButtons");
        soundButton = new TextButton("Music: on", skinMainMenu,"soundTest");
        soundButton.setChecked(!MusicManager.isMusicEnable());

        buttonPlay.pad(10);
        buttonHelp.pad(10);
        buttonRecords.pad(10);
        soundButton.pad(10);

        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.add(heading).row().padTop(100).width(buttonWidth).height(buttonHeight);
        table.add(buttonPlay).row().width(buttonWidth).height(buttonHeight);
        table.add(buttonRecords).row().width(buttonWidth).height(buttonHeight);
        table.add(buttonHelp).row().pad(5).width(buttonWidth).height(buttonHeight);
        table.add(soundButton);
    }
    public void setListeners(){
        buttonPlay.addListener(new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            game.setScreen(new GameScreen(game));
        }
        });
        buttonHelp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new HelpScreen(game));
            }
        });
        buttonRecords.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new RecordScreen(game));
            }
        });
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!MusicManager.isMusicEnable()){
                    soundButton.setText("Music: on");
                } else {
                    soundButton.setText("Music: off");
                }
                MusicManager.onOffMusic(game.getScreen());
            }
        });
    }
    public Table getTable(){
        return table;
    }

    @Override
    public void dispose() {
        atlasMainMenu.dispose();
        skinMainMenu.dispose();
    }

    private Table table;
    private TextureAtlas atlasMainMenu;
    private Skin skinMainMenu;
    private final TextButton
            buttonPlay,
            buttonHelp,
            soundButton,
            buttonRecords;
    private final TapTap game;
}
