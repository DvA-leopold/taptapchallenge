package com.taptap.game.view.screens.mainmenu_screen.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.taptap.game.view.screens.game_screen.GameScreen;
import com.taptap.game.view.screens.help_screen.HelpScreen;
import com.taptap.game.view.screens.records_screen.RecordScreen;
import com.taptap.game.view.screens.Buttons;
import com.taptap.game.view.screens.town_screen.TownScreen;

public class MenuButtonInitializer implements Buttons {
    public MenuButtonInitializer(final SpriteBatch batch) {
        this.batch = batch;
        float buttonWidth = Gdx.graphics.getWidth() * 0.4f;
        float buttonHeight = Gdx.graphics.getHeight() * 0.15f;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Skin skin = (Skin) DResourceManager.getInstance().
                get("skins/main_menu/buttons/menuSkin.json");
        skin.getFont("blackFont").setScale(2, 2);

        Label heading = new Label(I18NBundleMy.getString("game_name"), skin);
        buttonPlay = new TextButton(I18NBundleMy.getString("play"), skin, "mainButtons");
        buttonHelp = new TextButton(I18NBundleMy.getString("help"), skin, "mainButtons");
        buttonRecords = new TextButton(I18NBundleMy.getString("records"), skin, "mainButtons");
        soundButton = new TextButton(I18NBundleMy.getString("music"), skin,"soundTest");
        soundButton.setChecked(!MusicManager.isMusicEnable());

        buttonPlay.pad(10);
        buttonHelp.pad(10);
        buttonRecords.pad(10);
        soundButton.pad(10);

        table = new Table(skin);
        table.setFillParent(true);
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
    public void render() {
        stage.act();
        stage.draw();
    }

    public void setListeners(final GameWorld gameWorld) {
        buttonPlay.addListener(new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new TownScreen(batch));
        }
        });
        buttonHelp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HelpScreen(batch));
            }
        });
        buttonRecords.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new RecordScreen(batch));
            }
        });
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().onOffMusic();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setVisible(boolean visible) {
        table.setVisible(visible);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    final private SpriteBatch batch;

    final private Stage stage;
    final private Table table;
    final private TextButton
            buttonPlay,
            buttonHelp,
            soundButton,
            buttonRecords;
}
