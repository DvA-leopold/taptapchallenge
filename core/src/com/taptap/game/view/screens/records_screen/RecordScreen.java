package com.taptap.game.view.screens.records_screen;

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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.taptap.game.TapTap;
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.mainmenu_screen.MainMenuScreen;

public class RecordScreen implements Screen {
    public RecordScreen(final SpriteBatch batch){
        this.batch = batch;
        float buttonWidth = Gdx.graphics.getWidth()*0.35f;
        float buttonHeight = Gdx.graphics.getHeight()*0.2f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Skin skinRecords = (Skin) DResourceManager.getInstance().
                get("skins/record_menu/buttons/recordScreen.json");
        table = new Table(skinRecords);
        exitButton = new Button(skinRecords);

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()), batch);
        background = (Texture) DResourceManager.getInstance().
                get("skins/main_menu/background/bg_desert.png");

        TapTap.getStorage().displayData(table, skinRecords);
        table.add(exitButton).height(buttonHeight).width(buttonWidth);
        ((TapTap) Gdx.app.getApplicationListener()).
                getMusicManager().registerMusic(this.getClass(), MusicManager.MusicTypes.ADD_MUSIC);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.disableBlending();
        batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        batch.end();

        stage.act();
        stage.draw();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().playMusic();

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(batch));
            }
        });
    }

    @Override
    public void hide() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
        dispose();
    }

    @Override
    public void pause() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
    }

    @Override
    public void resume() {
        ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().playMusic();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    final private Stage stage;
    final private Table table;
    final private Button exitButton;

    final private SpriteBatch batch;
    final private Texture background;

    private OrthographicCamera camera;
}
