package com.taptap.game.view.screens.help_screen.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.internationalization.I18NBundleMy;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.mainmenu_screen.MainMenuScreen;
import com.taptap.game.view.screens.WidgetsGroup;

public class HelpScreenWidgetsGroup implements WidgetsGroup {
    public HelpScreenWidgetsGroup(final SpriteBatch batch) {
        float buttonSize = Gdx.graphics.getWidth() * 0.3f;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Skin skin = (Skin) DResourceManager.getInstance().
                get("skins/help_menu/buttons/helpSkin.json");
        Table table = new Table(skin);
        button = new TextButton(I18NBundleMy.getString("menu"), skin, "default");

        table.setFillParent(true);
        table.add(button).
                padRight(Gdx.graphics.getWidth() - buttonSize).
                padTop(Gdx.graphics.getHeight() - buttonSize).
                height(buttonSize).width(buttonSize).reset();
        stage.addActor(table);
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void setListeners(final GameWorld gameWorld) {
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
        return stage;
    }

    @Override
    public void setVisible(boolean visible) { }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private TextButton button;
    private Stage stage;
}
