package com.taptap.game.screens.realisation.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.resource.manager.ResourceManager;
import com.taptap.game.save.manager.StorageManager;
import com.taptap.game.screens.realisation.game.button.styles.optionButtonsInitializer;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;
import com.taptap.game.screens.realisation.game.button.styles.gameButtonsInitializer;
import com.taptap.game.screens.realisation.game.button.styles.popUpButtonsInitializer;
import com.taptap.game.screens.realisation.game.tap.icons.factory.Icon;
import com.taptap.game.screens.realisation.game.tap.icons.factory.AbstractItemFactory;
import debug.statistics.FPS_MEM_DC;

public class GameScreen implements Screen {
    public GameScreen(final TapTap game){
        this.game = game;
        double startTime = System.currentTimeMillis();
        stateManager = StateManager.GAME_RUNNING;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //taskManager = new TaskManager(this);

        //gameBackground = new Texture(Gdx.files.internal("skins/game_menu/game_bg.png"));
        //gameOver = new Texture(Gdx.files.internal("skins/game_menu/game_over.png"));
        //coinsAndNumbers = new TextureAtlas(Gdx.files.internal("skins/game_menu/coins_and_numb/coins_and_hud.pack"));
        gameBackground = new Sprite(ResourceManager.getInstance().storage.get(ResourceManager.gameBackground));
        gameOver = new Sprite(ResourceManager.getInstance().storage.get(ResourceManager.gameOver));

        gameButtons = new gameButtonsInitializer(this);
        popUpButtons = new popUpButtonsInitializer(this);
        optionButtons = new optionButtonsInitializer(this);

        System.out.println("TIME: " + (System.currentTimeMillis() - startTime));

        iconFactory = new AbstractItemFactory(
                gameButtons.getOptionButton().getHeight(),
                gameButtons.getOptionButton().getWidth(),
                camera
        );
        mainBatch = new SpriteBatch();
        transparentBatch = new SpriteBatch();
        stage = new Stage();

        inputMultiplexer = new InputMultiplexer();

        //tapSound = Gdx.audio.newSound(Gdx.files.internal("tap.wav"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        switch (stateManager){
            case GAME_RUNNING:
                stateManager.runState(this);
                break;
            case GAME_PAUSED:
                stateManager.pauseState(this);
                break;
            case GAME_OVER:
                stateManager.gameOverState(this);
                break;
            case GAME_EXIT:
                stateManager.gameExitState(this);
                break;
        }
        FPS_MEM_DC.fpsLog();
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        gameButtons.getTable().setFillParent(true);
        popUpButtons.getTable().setFillParent(true);
        optionButtons.getTable().setFillParent(true);
        //mainBatch.setProjectionMatrix(camera.combined);
        //transparentBatch.setProjectionMatrix(camera.combined);
        stage.addActor(gameButtons.getTable());

        //Gdx.input.setInputProcessor(stage);
        popUpButtons.setListeners(stage, gameButtons.getTable(), optionButtons.getTable());
        gameButtons.setListeners(stage, popUpButtons.getTable());
        optionButtons.setListeners(stage, popUpButtons.getTable());

        //inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GestureDetector(iconFactory.getListener())); // todo set fling delay and etc.
        Gdx.input.setInputProcessor(inputMultiplexer);
        MusicManager.play(this);
    }

    @Override
    public void hide() {
        MusicManager.pause(this);
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        MusicManager.pause(this);
        stateManager = StateManager.GAME_PAUSED;
    }

    @Override
    public void resume() {
        MusicManager.play(this);
        stateManager = StateManager.GAME_RUNNING;
    }

    @Override
    public void dispose() {
        mainBatch.dispose();
        transparentBatch.dispose();
        stage.dispose();
        //gameBackground.dispose();
        gameButtons.dispose();
        popUpButtons.dispose();
        //taskManager.dispose();
        optionButtons.dispose();
        //MusicManager.dispose();
    }

    private void renderNumbers(int numbForRender, float widthAlign, float heightAlign){
        if (numbForRender>0){ // todo возможно можно оптимизировать
            int temp = numbForRender;
            float width = Gdx.graphics.getWidth();
            Sprite picture = new Sprite();
            while (temp!=0){
                int val = temp % 10;
                temp/=10;
                switch (val){
                    case 0:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud0");
                        break;
                    case 1:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud1");
                        break;
                    case 2:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud2");
                        break;
                    case 3:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud3");
                        break;
                    case 4:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud4");
                        break;
                    case 5:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud5");
                        break;
                    case 6:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud6");
                        break;
                    case 7:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud7");
                        break;
                    case 8:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud8");
                        break;
                    case 9:
                        picture = ResourceManager.getInstance().storage.
                                get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                                createSprite("hud9");
                        //picture = coinsAndNumbers.createSprite("hud9");
                        break;
                    default:
                        System.out.println("Error");
                        break;
                }
                width -= picture.getWidth();
                mainBatch.draw(
                        picture, width + widthAlign,
                        Gdx.graphics.getHeight() + heightAlign - picture.getHeight());
                FPS_MEM_DC.drawCalls++;
            }
        }
    }

    public static StorageManager getStorage(){
        return storage;
    }
    public int getTotalScore(){
        return iconFactory.getTotalScore();
    }

    public enum StateManager {
        GAME_RUNNING,
        GAME_PAUSED,
        GAME_OVER,
        GAME_EXIT;

        private void runState(final GameScreen screen){
            screen.totalTime -= Gdx.graphics.getDeltaTime();
            screen.alpha = 0; // todo нужно переместить
            screen.mainBatch.begin();
            screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            FPS_MEM_DC.drawCalls++;
            for(Icon iconsDrop : screen.iconFactory.getIconsArray()) {
                screen.mainBatch.draw(iconsDrop.getTexture(), iconsDrop.getX(), iconsDrop.getY());
                FPS_MEM_DC.drawCalls++;
            }
            screen.renderNumbers(screen.iconFactory.getTotalScore(), 0, 0);
            screen.renderNumbers((int)screen.totalTime, -Gdx.graphics.getWidth()/2 ,0);

            screen.mainBatch.end();

            if (screen.iconFactory.controlFiguresNumber()<0){
                //screen.stateManager = GAME_OVER;
            }
            if (screen.totalTime <= 0){
                GameScreen.getStorage().saveDataValue("player " + GameScreen.getStorage().getAllData().size(),
                        screen.getTotalScore());
                screen.stateManager = GameScreen.StateManager.GAME_EXIT;
            }
        }

        private void pauseState(final GameScreen screen){
            screen.alpha = (float)Math.min(screen.alpha + Gdx.graphics.getDeltaTime()/2, 0.7);
            screen.mainBatch.begin();
            screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            FPS_MEM_DC.drawCalls++;
            screen.mainBatch.end();

            screen.transparentBatch.begin();
            screen.transparentBatch.setColor(0.0f,0.0f,0.0f,screen.alpha);
            screen.transparentBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            FPS_MEM_DC.drawCalls++;
            for(Icon tapIcon : screen.iconFactory.getIconsArray()) {
                screen.transparentBatch.draw(tapIcon.getTexture(), tapIcon.getX(), tapIcon.getY());
                FPS_MEM_DC.drawCalls++;
            }
            screen.transparentBatch.end();

        }

        private void gameOverState(final GameScreen screen){
            screen.mainBatch.begin();
            screen.mainBatch.setColor(0.5f, 0f, 0f, 0.6f);
            screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            screen.mainBatch.draw(screen.gameOver,
                    Gdx.graphics.getWidth() / 2 - screen.gameOver.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - screen.gameOver.getHeight() / 2);
            FPS_MEM_DC.drawCalls+=2;
            screen.mainBatch.end();
            if (Gdx.input.isTouched()){
                screen.stateManager = GAME_EXIT;
            }
        }

        private void gameExitState(final GameScreen screen){
            screen.game.setScreen(new MainMenuScreen(screen.game));
        }
    }

    private AbstractItemFactory iconFactory;
    //private TextureAtlas coinsAndNumbers;

    private gameButtonsInitializer gameButtons;
    private popUpButtonsInitializer popUpButtons;
    private optionButtonsInitializer optionButtons;

    private Stage stage;

    private Sprite gameBackground;
    private Sprite gameOver;
    private SpriteBatch transparentBatch;
    private SpriteBatch mainBatch;
    private OrthographicCamera camera;
    public StateManager stateManager; // todo change to private
    //private TaskManager taskManager;
    private InputMultiplexer inputMultiplexer;

    //    private Sound tapSound;
    private float totalTime = 150;
    private float alpha = 0;

    private static StorageManager storage = new StorageManager(true);
    private final TapTap game;
}
