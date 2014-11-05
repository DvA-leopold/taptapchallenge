package com.taptap.game.screens.realisation.mainmenu.button.styles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
        int buttonWidth = Gdx.graphics.getWidth()/3;
        int buttonHeight = Gdx.graphics.getHeight()/7; // todo change to global variables
        table = new Table(skinMainMenu);
        atlasMainMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack");
        skinMainMenu = new Skin(Gdx.files.internal("skins/json_skins/menuSkin.json"), atlasMainMenu);

        Label heading = new Label("TAP TAP Game", skinMainMenu, "default");
        buttonPlay = new TextButton("Play", skinMainMenu, "mainButtons");// from textButtonStyle .json
        buttonHelp = new TextButton("Help", skinMainMenu, "mainButtons");
        buttonRecords = new TextButton("Records", skinMainMenu, "mainButtons");
        soundButton = new TextButton("Music: on", skinMainMenu,"soundTest");

        buttonPlay.pad(10);
        buttonHelp.pad(10);
        buttonRecords.pad(10);
        soundButton.pad(10);

        table.add(heading).row().padTop(100).width(buttonWidth).height(buttonHeight);
        table.add(buttonPlay).row().pad(1).width(buttonWidth).height(buttonHeight);
        table.add(buttonRecords).row().pad(1).width(buttonWidth).height(buttonHeight);
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
            boolean help=true; // todo изменить эту штуку просто на перечеркнутый динамик
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (help){
                    soundButton.setText("Music: off");
                    help = false;
                } else {
                    soundButton.setText("Music: on");
                    help=true;
                }
                MusicManager.onOffSound();
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
