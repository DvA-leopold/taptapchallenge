package com.taptap.game.view.screens.mainmenu_screen.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.view.screens.game_screen.GameRenderer;
import com.taptap.game.view.screens.game_screen.GameScreen;
import com.taptap.game.view.screens.help_screen.HelpScreen;
import com.taptap.game.view.screens.records_screen.RecordScreen;
import com.taptap.game.view.buttons.interfaces.Buttons;

public class MenuButtonInitializer implements Buttons {
    public MenuButtonInitializer(final SpriteBatch batch){
        this.batch = batch;
        float buttonWidth = Gdx.graphics.getWidth()*0.5f;
        float buttonHeight = Gdx.graphics.getHeight()*0.15f;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Skin skin = ResourceManager.getInstance().get(ResourceManager.mainMenuSkin);
        skin.getFont("blackFont").setScale(2, 2); // todo поменять шрифты

        Label heading = new Label("TAP TAP Game", skin, "default");
        buttonPlay = new TextButton("Play", skin, "mainButtons");
        buttonHelp = new TextButton("Help", skin, "mainButtons");
        buttonRecords = new TextButton("Records", skin, "mainButtons");
        soundButton = new TextButton("Music:", skin,"soundTest");
        soundButton.setChecked(!MusicManager.isMusicEnable());

        buttonPlay.pad(10);
        buttonHelp.pad(10);
        buttonRecords.pad(10);
        soundButton.pad(10);

        Table table = new Table(skin);
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
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(batch));
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
                MusicManager.onOffMusic();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    private final SpriteBatch batch;

    private Stage stage;
    private final TextButton
            buttonPlay,
            buttonHelp,
            soundButton,
            buttonRecords;
}
