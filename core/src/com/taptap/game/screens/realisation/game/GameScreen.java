package com.taptap.game.screens.realisation.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.screens.realisation.MainMenuScreen;
import com.taptap.game.screens.realisation.game.tap.icons.BlueIcons;
import com.taptap.game.screens.realisation.game.tap.icons.Icon;
import com.taptap.game.screens.realisation.game.tap.icons.RedIcons;
import com.taptap.game.screens.realisation.game.tap.icons.YellowIcons;

public class GameScreen implements Screen {
    public GameScreen(final TapTap game){
        this.game = game;
        stateManager = StateManager.GAME_RUNNING;
        tapImage = new Texture(Gdx.files.internal("skins/game_menu/tap_icons/hud_gem_green.png"));
        popUpMenuBackground = new Texture(Gdx.files.internal("skins/game_menu/popup_menu/bg_popup_panel.png"));
        gameBackground = new Texture (Gdx.files.internal("skins/game_menu/game_bg.png"));
        gameOver = new Texture (Gdx.files.internal("skins/game_menu/game_over.png"));
        coinsAndNumbers = new TextureAtlas(Gdx.files.internal("skins/game_menu/coins_and_numb/coins_and_hud.pack"));

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tapIcons = new Array<Icon>(10);

        //-------------------- popup menu caller button
        atlasGameMenu = new TextureAtlas("skins/game_menu/GameOptions.pack");
        skinGameMenu = new Skin(Gdx.files.internal("skins/json_skins/optionIconSkin.json"), atlasGameMenu);
        mainTable = new Table(skinGameMenu);
        optionButton = new Button(skinGameMenu, "gameButton");
        mainTable.add(optionButton).padTop(-Gdx.graphics.getHeight()+50).padLeft(-Gdx.graphics.getWidth()+50);

        //--------------------- popup menu buttons etc
        atlasPopupMenu = new TextureAtlas("skins/main_menu/buttons/buttons.pack"); //todo change style later
        skinPopupMenu = new Skin(Gdx.files.internal("skins/json_skins/popUpSkin.json"), atlasPopupMenu);
        popupTable = new Table(skinPopupMenu);
        resumeGameButton = new TextButton("return", skinPopupMenu, "popUpButtons");
        exitMainMenuButton = new TextButton("save & exit", skinPopupMenu, "popUpButtons");

        resumeGameButton.pad(10); //todo add this to .json??
        exitMainMenuButton.pad(10);
        popupTable.add(resumeGameButton).row().pad(20);
        popupTable.add(exitMainMenuButton).row().pad(20);

        mainBatch = new SpriteBatch();
        transparentBatch = new SpriteBatch();
        stage = new Stage();
        //tapSound = Gdx.audio.newSound(Gdx.files.internal("tap.wav"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        switch (stateManager){
            case GAME_RUNNING:
                runState();
                break;
            case GAME_PAUSED:
                pauseState();
                break;
            case GAME_OVER:
                overState();
                break;
            case GAME_EXIT:
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
        //mainBatch.setProjectionMatrix(camera.combined); todo don`t know is this needed
        //transparentBatch.setProjectionMatrix(camera.combined);
        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
        addButtonsListeners();
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
        mainBatch.dispose();
        transparentBatch.dispose();
        atlasGameMenu.dispose();
        skinGameMenu.dispose();
        stage.dispose();
        atlasPopupMenu.dispose();
        skinPopupMenu.dispose();
        popUpMenuBackground.dispose();
        gameBackground.dispose();
        MusicManager.dispose();
    }

    private void spawnAndControlIcons(){
        int rand = MathUtils.random(0,100);
        if (rand < 25){
            tapIcons.add(new RedIcons(
                    MathUtils.random(optionButton.getHeight()+20, Gdx.graphics.getWidth() - tapImage.getWidth()),
                    MathUtils.random(0, Gdx.graphics.getHeight() - tapImage.getHeight() - optionButton.getWidth()-20),
                    tapImage.getWidth(), tapImage.getHeight())
            );
        } else if (rand > 25 && rand < 60){
            tapIcons.add(new BlueIcons(
                    MathUtils.random(optionButton.getHeight()+20, Gdx.graphics.getWidth() - tapImage.getWidth()),
                    MathUtils.random(0, Gdx.graphics.getHeight() - tapImage.getHeight() - optionButton.getWidth()-20),
                    tapImage.getWidth(), tapImage.getHeight())
            );
        } else {
            tapIcons.add(new YellowIcons(
                    MathUtils.random(optionButton.getHeight()+20, Gdx.graphics.getWidth() - tapImage.getWidth()),
                    MathUtils.random(0, Gdx.graphics.getHeight() - tapImage.getHeight() - optionButton.getWidth()-20),
                    tapImage.getWidth(), tapImage.getHeight())
            );
        }
        // todo add more stuff
        lastDropTime = TimeUtils.nanoTime();
        if (numberOfFigures > 10){
            tapIcons.removeIndex(0);
            totalScore -=50;
            if (totalScore < 0){
                totalScore = 0;
                stateManager = StateManager.GAME_OVER;
            }
            numberOfFigures--;
        }
    }

    private void runState(){
        mainBatch.begin();
        mainBatch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for(Icon raindrop : tapIcons) {
            mainBatch.draw(raindrop.getTexture(), raindrop.getX(), raindrop.getY());
        }
        renderTotalScore();
        mainBatch.end();
        Vector3 touchPoint = new Vector3(); //костыль с координатами(улучшенный)
        if (Gdx.input.isTouched()){
            for (int i=0; i<tapIcons.size; ++i){
                Icon temp = tapIcons.get(i);
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                if (Gdx.input.getX() > temp.getX() &&
                        touchPoint.y > temp.getY() &&
                        Gdx.input.getX() < temp.getX() + temp.getWidth() &&
                        touchPoint.y < temp.getY() + temp.getHeight()){
                    tapIcons.removeIndex(i);
                    numberOfFigures--;
                    totalScore +=temp.addScore();
                    break; // todo здесь вывести функцию опроса событий из рендера
                }
            }
        }
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
            numberOfFigures++;
            spawnAndControlIcons();
        }
    }

    private void pauseState(){
        mainBatch.begin();
        mainBatch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainBatch.end();

        transparentBatch.begin();
        transparentBatch.setColor(0, 0, 0, 0.6f);
        for(Icon raindrop : tapIcons) {
            transparentBatch.draw(raindrop.getTexture(), raindrop.getX(), raindrop.getY());
        }
        transparentBatch.end();

        mainBatch.begin();
        mainBatch.draw(popUpMenuBackground,
                Gdx.graphics.getWidth() / 2 - popUpMenuBackground.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - popUpMenuBackground.getHeight() / 2);
        mainBatch.end();
    }

    private void overState(){
        mainBatch.begin();
        mainBatch.setColor(0.5f, 0f, 0f, 0.6f);
        mainBatch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainBatch.draw(gameOver,
                Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
        mainBatch.end();
        if (Gdx.input.isTouched()){
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void addButtonsListeners(){
        resumeGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                stateManager = StateManager.GAME_RUNNING;
                stage.clear();
                stage.addActor(mainTable);
            }
        });
        exitMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                stateManager = StateManager.GAME_EXIT;
            }
        });
        optionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                stateManager = StateManager.GAME_PAUSED;
                stage.clear();
                stage.addActor(popupTable);
            }
        });

    }
    private void renderTotalScore(){
        if (totalScore>0){
            long temp = totalScore;
            float range = Gdx.graphics.getWidth();
            Sprite picture = new Sprite();
            while (temp!=0){
                int val = (int)(temp % 10);
                temp/=10;
                switch (val){
                    case 0:
                        picture = coinsAndNumbers.createSprite("hud0");
                        break;
                    case 1:
                        picture = coinsAndNumbers.createSprite("hud1");
                        break;
                    case 2:
                        picture = coinsAndNumbers.createSprite("hud2");
                        break;
                    case 3:
                        picture = coinsAndNumbers.createSprite("hud3");
                        break;
                    case 4:
                        picture = coinsAndNumbers.createSprite("hud4");
                        break;
                    case 5:
                        picture = coinsAndNumbers.createSprite("hud5");
                        break;
                    case 6:
                        picture = coinsAndNumbers.createSprite("hud6");
                        break;
                    case 7:
                        picture = coinsAndNumbers.createSprite("hud7");
                        break;
                    case 8:
                        picture = coinsAndNumbers.createSprite("hud8");
                        break;
                    case 9:
                        picture = coinsAndNumbers.createSprite("hud9");
                        break;
                    default:
                        System.out.println("Error");
                        break;
                }
                range -= picture.getWidth();
                mainBatch.draw(picture, range, Gdx.graphics.getHeight()-picture.getHeight());
            }
        }
    }

    private enum StateManager {
        GAME_RUNNING,
        GAME_PAUSED,
        GAME_OVER,
        GAME_EXIT
    }

    private Array<Icon> tapIcons;
    private int numberOfFigures;
    private long lastDropTime;
    private long totalScore;

    //coins and numbers
    private TextureAtlas coinsAndNumbers;

    // main menu buttons
    private Stage stage;
    private TextureAtlas atlasGameMenu;
    private Skin skinGameMenu;
    private Table mainTable;
    private Button optionButton;

    // pop up menu buttons
    private TextureAtlas atlasPopupMenu;
    private Skin skinPopupMenu;
    private Table popupTable;
    private TextButton resumeGameButton;
    private TextButton exitMainMenuButton;
    private Button soundButton;

    private Texture tapImage;
    private Texture popUpMenuBackground;
    private Texture gameBackground;
    private Texture gameOver;
//    private Sound tapSound;
    private SpriteBatch mainBatch;
    private SpriteBatch transparentBatch;
    private OrthographicCamera camera;

    private StateManager stateManager;
    private final TapTap game;
}
