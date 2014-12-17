package com.taptap.game.view.screens.help_screen.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.view.screens.game_screen.GameScreen;
import com.taptap.game.view.screens.mainmenu_screen.MainMenuScreen;
import com.taptap.game.view.buttons.interfaces.Buttons;

public class HelpButtonInitializer implements Buttons {
    public HelpButtonInitializer(Batch batch){
        int buttonWidth = Gdx.graphics.getWidth()/8;
        int buttonHeight = Gdx.graphics.getHeight()/7;
        // TODO решить проблему различных разрешений экрана ViewPort
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),batch);
        Skin skin = new Skin(
                Gdx.files.internal("skins/json_skins/helpSkin.json"),
                ResourceManager.getInstance().get(ResourceManager.atlasHelpMenu)
        );
        Table table = new Table(skin);

        button = new TextButton("menu", skin, "default");

        table.setFillParent(true);
        table.add(button).
                padRight(Gdx.graphics.getWidth()-buttonWidth).
                padTop(Gdx.graphics.getHeight()-buttonHeight).
                height(buttonHeight).width(buttonWidth).reset();
        stage.addActor(table);
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void setListeners(final GameScreen game) {
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
        //skin.dispose();
        stage.dispose();
    }

    //private TextureAtlas atlas;
    private TextButton button;
    private Stage stage;
}
