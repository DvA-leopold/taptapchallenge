package com.taptap.game.screens.realisation.game.button.styles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.screens.realisation.game.GameScreen;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;

public class HelpButtonInitializer implements Buttons {
    public HelpButtonInitializer(){
        int buttonWidth = Gdx.graphics.getWidth()/8;
        int buttonHeight = Gdx.graphics.getHeight()/7;
        //atlas = new TextureAtlas("skins/help_menu/buttons/helpButton.pack");
        skin = new Skin(
                Gdx.files.internal("skins/json_skins/helpSkin.json"),
                new TextureAtlas("skins/help_menu/buttons/helpButton.pack"));
        Table table = new Table(skin);
        stage = new Stage();

        button = new TextButton("menu", skin, "default");

        //table.add(helpString1).center().padLeft(Gdx.graphics.getWidth());
        table.setFillParent(true);
        table.add(button).
                padRight(Gdx.graphics.getWidth()-buttonWidth).
                padTop(Gdx.graphics.getHeight()-buttonHeight).
                height(buttonHeight).width(buttonWidth).reset();
        stage.addActor(table);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void setListeners(final GameScreen gameScreen) {
        //stage.addActor(table);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
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
        //atlas.dispose();
        skin.dispose();
        stage.dispose();
    }

    //private TextureAtlas atlas;
    private TextButton button;
    private Skin skin;
    private Stage stage;
}
