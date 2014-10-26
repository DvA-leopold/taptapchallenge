package com.taptap.game.screens.realisation.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.MainMenuScreen;

public class GameScreen implements Screen {
    public GameScreen(final TapTap game){
        this.game = game;
        stateManager  = StateManager.GAME_RUNNING;
        tapImage = new Texture(Gdx.files.internal("skins/tap_icons/hud_gem_green.png"));
        popUpMenuBackground = new Texture(Gdx.files.internal("skins/game_menu/popup_menu/bg_castle.png"));
        // решилась проблема с переворотом+правильно реагируют координаты(оптимизированный костыль)
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        iconsForTap = new Array<Rectangle>();

        //-------------------- main menu buttons etc
        atlasGameMenu = new TextureAtlas("skins/game_menu/GameOptions.pack");
        skinGameMenu = new Skin(atlasGameMenu);
        mainTable = new Table(skinGameMenu);
        // todo move to json
        Button.ButtonStyle optionButtonStyle = new Button.ButtonStyle();
        optionButtonStyle.up = skinGameMenu.getDrawable("tick_up"); // get this names from the .pack file
        optionButtonStyle.down = skinGameMenu.getDrawable("tick_down");
        optionButton = new Button(optionButtonStyle);
        mainTable.add(optionButton).padTop(-Gdx.graphics.getHeight()+50).padLeft(-Gdx.graphics.getWidth()+50);

        //--------------------- popup menu buttons etc
        atlasPopupMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack"); //todo change style later
        skinPopupMenu = new Skin(atlasPopupMenu);
        popupTable = new Table(skinPopupMenu);
        Button.ButtonStyle popupMenuStyleButtons = new Button.ButtonStyle();
        popupMenuStyleButtons.up = skinPopupMenu.getDrawable("button.up");
        popupMenuStyleButtons.down = skinPopupMenu.getDrawable("button.down");
        resumeGameButton = new Button(popupMenuStyleButtons);
        exitMainMenuButton = new Button(popupMenuStyleButtons);
        popupTable.add(resumeGameButton).row();
        popupTable.add(exitMainMenuButton);
        //stagePopupButton = new Stage();

        batch = new SpriteBatch();
        stage = new Stage();
        //tapSound = Gdx.audio.newSound(Gdx.files.internal("tap.wav"));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        camera.update();

        switch (stateManager){
            case GAME_RUNNING:
                batch.begin();
                // здесь мы запоминаем что надо отрисовать
                for(Rectangle raindrop : iconsForTap) {
                    batch.draw(tapImage, raindrop.x, raindrop.y);
                }
                batch.end();
                Vector3 touchPoint = new Vector3(); //костыль с координатами(улучшенный)
                if (Gdx.input.isTouched()){
                    for (int i=0; i<iconsForTap.size; ++i){
                        Rectangle temp = iconsForTap.get(i);
                        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                        if (Gdx.input.getX() > temp.getX() &&
                                touchPoint.y > temp.getY() &&
                                Gdx.input.getX() < temp.getX() + temp.getWidth() &&
                                touchPoint.y < temp.getY() + temp.getHeight()){
                            iconsForTap.removeIndex(i);
                            break;
                        }
                    }
                }
                if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
                    spawnTapIcon();
                }
                optionButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        stage.clear();
                        stage.addActor(popupTable);
                        stage.act();
                        stage.draw();
                        stateManager = StateManager.GAME_PAUSED;
                    }
                });
                break;
            case GAME_PAUSED:
                batch.begin();
                batch.draw(popUpMenuBackground, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2); // todo coordinates
                batch.end();

                resumeGameButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        stage.clear();
                        stage.addActor(mainTable);
                        stage.act();
                        stage.draw();
                        stateManager = StateManager.GAME_RUNNING;
                    }
                });

                break;
            case GAME_LEVEL_END:

                break;
            case GAME_OVER:
                game.setScreen(new MainMenuScreen(game));
                break;
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        // данный метод вызывается один раз и поэтому отрисовывает только какой-то первый экран
        mainTable.setFillParent(true);
        popupTable.setFillParent(true);
        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);

        MusicManager.play(this);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        MusicManager.pause(this);
        stateManager = StateManager.GAME_PAUSED;
    }

    @Override
    public void resume() {
        stateManager = StateManager.GAME_RUNNING;
    }

    @Override
    public void dispose() {
        tapImage.dispose();
        batch.dispose();
        atlasGameMenu.dispose();
        skinGameMenu.dispose();
        stage.dispose();
        atlasPopupMenu.dispose();
        skinPopupMenu.dispose();

    }

    private void spawnTapIcon() {
        iconsForTap.add(new Rectangle(
                MathUtils.random(0, Gdx.graphics.getWidth() - tapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight() - tapImage.getHeight()),
                tapImage.getWidth(), tapImage.getHeight()));
        lastDropTime = TimeUtils.nanoTime();
    }

    private Array<Rectangle> iconsForTap;
    private long lastDropTime;

    // main menu buttons
    private Stage stage;
    private TextureAtlas atlasGameMenu;
    private Skin skinGameMenu;
    private Table mainTable;
    private Button optionButton;

    // pop up menu buttons
   // private Stage stagePopupButton;
    private TextureAtlas atlasPopupMenu;
    private Skin skinPopupMenu;
    private Table popupTable;
    private Button resumeGameButton;
    private Button exitMainMenuButton;
    private Button soundButton;

    private Texture tapImage;
    private Texture popUpMenuBackground;
//    private Sound tapSound;
    private SpriteBatch batch;
    private OrthographicCamera camera;


    private StateManager stateManager;
    private final TapTap game;
}
