package com.taptap.game.screens.realisation.records_screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.screens.realisation.mainmenu_screen.MainMenuScreen;
import com.taptap.game.screens.realisation.game_screen.GameScreen;
import debug.statistics.FPS_MEM_DC;

public class RecordScreen implements Screen {
    public RecordScreen(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Skin skinRecords = new Skin(
                Gdx.files.internal("skins/json_skins/menuSkin.json"),
                ResourceManager.getInstance().get(ResourceManager.atlasRecordMenu));
        table = new Table(skinRecords);
        exitButton = new Button(skinRecords);

        stage = new Stage();
        batch = new SpriteBatch();
        background = ResourceManager.getInstance().get(ResourceManager.recordBackground);

        int buttonWidth = Gdx.graphics.getWidth()/3;
        int buttonHeight = Gdx.graphics.getHeight()/7;

        GameScreen.getStorage().displayData(table, skinRecords);
        table.add(exitButton).height(buttonHeight).width(buttonWidth);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();
        FPS_MEM_DC.drawCalls+=2;
        FPS_MEM_DC.fpsLog();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        MusicManager.play(this);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });
    }

    @Override
    public void hide() {
        MusicManager.pause(this);
        dispose();
    }

    @Override
    public void pause() {
        MusicManager.pause(this);
    }

    @Override
    public void resume() {
        MusicManager.play(this);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    private final Stage stage;
    private final Table table;
    private final Button exitButton;

    private final SpriteBatch batch;
    private final Texture background;

    private OrthographicCamera camera;
}
