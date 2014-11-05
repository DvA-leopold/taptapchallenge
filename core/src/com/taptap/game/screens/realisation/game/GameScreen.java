package com.taptap.game.screens.realisation.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.taptap.game.TapTap;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.save.manager.StorageManager;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;
import com.taptap.game.screens.realisation.game.button.styles.gameButtonsInitializer;
import com.taptap.game.screens.realisation.game.button.styles.popUpButtonsInitializer;
import com.taptap.game.screens.realisation.game.tap.icons.factory.Icon;
import com.taptap.game.screens.realisation.game.tap.icons.factory.AbstractItemFactory;
import com.taptap.game.task.manager.TaskManager;

public class GameScreen implements Screen {
    public GameScreen(final TapTap game){
        this.game = game;
        stateManager = StateManager.GAME_RUNNING;
        taskManager = new TaskManager(this);
        timer = new Timer();
        popUpMenuBackground = new Texture(Gdx.files.internal("skins/game_menu/popup_menu/panel_blue.png"));
        gameBackground = new Texture (Gdx.files.internal("skins/game_menu/game_bg.png"));
        gameOver = new Texture (Gdx.files.internal("skins/game_menu/game_over.png"));
        coinsAndNumbers = new TextureAtlas(Gdx.files.internal("skins/game_menu/coins_and_numb/coins_and_hud.pack"));

        gameButtons = new gameButtonsInitializer(this);
        popUpButtons = new popUpButtonsInitializer(this);

        iconFactory = new AbstractItemFactory(
                gameButtons.getOptionButton().getHeight(),
                gameButtons.getOptionButton().getWidth()
        );

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mainBatch = new SpriteBatch();
        transparentBatch = new SpriteBatch();
        stage = new Stage();
        //tapSound = Gdx.audio.newSound(Gdx.files.internal("tap.wav"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        camera.update();

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
        stage.act();
        stage.draw();

        playTime+=Gdx.graphics.getDeltaTime();
        System.out.println(playTime);
    }

    @Override
    public void show() {
        gameButtons.getTable().setFillParent(true);
        popUpButtons.getPopupTable().setFillParent(true);
        //mainBatch.setProjectionMatrix(camera.combined); хз надо ли это
        //transparentBatch.setProjectionMatrix(camera.combined);
        stage.addActor(gameButtons.getTable());
        Gdx.input.setInputProcessor(stage);
        popUpButtons.setListeners(stage, gameButtons.getTable());
        gameButtons.setListeners(stage, popUpButtons.getPopupTable());
        MusicManager.play(this);

        timer.scheduleTask(taskManager.saveScore(), 10.f);
    }

    @Override
    public void hide() {
        MusicManager.pause(this);
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // работает только для кнопок
    }

    @Override
    public void pause() {
        timer.stop();
        MusicManager.pause(this);
        stateManager = StateManager.GAME_PAUSED;
    }

    @Override
    public void resume() {
        MusicManager.play(this);
        timer.start();
        stateManager = StateManager.GAME_RUNNING;
    }

    @Override
    public void dispose() {
        mainBatch.dispose();
        transparentBatch.dispose();
        stage.dispose();
        popUpMenuBackground.dispose();
        gameBackground.dispose();
        gameButtons.dispose();
        popUpButtons.dispose();
        taskManager.dispose();
        //MusicManager.dispose();
    }

    private void renderNumbers(int numbForRender, float renderPosition){
        if (numbForRender>0){
            int temp = numbForRender;
            float range = Gdx.graphics.getWidth();
            Sprite picture = new Sprite();
            while (temp!=0){
                int val = temp % 10;
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
                mainBatch.draw(picture, range,renderPosition);
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
            screen.mainBatch.begin();
            screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            for(Icon iconsDrop : screen.iconFactory.getIconsArray()) {
                screen.mainBatch.draw(iconsDrop.getTexture(), iconsDrop.getX(), iconsDrop.getY());
            }
            screen.renderNumbers(screen.iconFactory.getTotalScore(), Gdx.graphics.getHeight() -
                    screen.coinsAndNumbers.createSprite("hud0").getHeight());

//            long executeTaskTime = (screen.saveScore.getExecuteTimeMillis() - TimeUtils.millis());
//            System.out.println(screen.saveScore.getExecuteTimeMillis()+ " "+ TimeUtils.millis());
//            screen.renderNumbers(executeTaskTime,Gdx.graphics.getHeight()/2);

            screen.mainBatch.end();
            Vector3 touchPoint = new Vector3(); //костыль с координатами(улучшенный)
            if (Gdx.input.isTouched()){
                for (int i=0; i<screen.iconFactory.getIconsArray().size; ++i){
                    Icon temp = screen.iconFactory.getIconsArray().get(i);
                    screen.camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                    if (Gdx.input.getX() > temp.getX() &&
                            touchPoint.y > temp.getY() &&
                            Gdx.input.getX() < temp.getX() + temp.getWidth() &&
                            touchPoint.y < temp.getY() + temp.getHeight()){
                        screen.iconFactory.removeIcon(i);
                        screen.iconFactory.decNumberOfFigures();
                        screen.iconFactory.addToTotal(temp.addScore());
                        break; // todo переделать это убогое решение
                    }
                }
            }
            if (screen.iconFactory.controlFiguresNumber()<0){
                screen.stateManager = GAME_OVER;
            }
        }

        private void pauseState(final GameScreen screen){
            screen.mainBatch.begin();
            screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            screen.mainBatch.end();

            screen.transparentBatch.begin();
            screen.transparentBatch.setColor(0, 0, 0, 0.6f);
            for(Icon tapIcon : screen.iconFactory.getIconsArray()) {
                screen.transparentBatch.draw(tapIcon.getTexture(), tapIcon.getX(), tapIcon.getY());
            }
            screen.transparentBatch.end();

            screen.mainBatch.begin();
            screen.mainBatch.draw(screen.popUpMenuBackground,
                    Gdx.graphics.getWidth() / 2 - screen.popUpMenuBackground.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - screen.popUpMenuBackground.getHeight() / 2,
                    Gdx.graphics.getWidth(),
                    Gdx.graphics.getHeight()); //todo решить проблему с этой картинкой
            screen.mainBatch.end();
        }

        private void gameOverState(final GameScreen screen){
            screen.mainBatch.begin();
            screen.mainBatch.setColor(0.5f, 0f, 0f, 0.6f);
            screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            screen.mainBatch.draw(screen.gameOver,
                    Gdx.graphics.getWidth() / 2 - screen.gameOver.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - screen.gameOver.getHeight() / 2);
            screen.mainBatch.end();
            if (Gdx.input.isTouched()){
                screen.game.setScreen(new MainMenuScreen(screen.game));
            }
        }

        private void gameExitState(final GameScreen screen){
            screen.game.setScreen(new MainMenuScreen(screen.game));
        }
    }


    private AbstractItemFactory iconFactory;
    private TextureAtlas coinsAndNumbers;

    private gameButtonsInitializer gameButtons;
    private popUpButtonsInitializer popUpButtons;
    private Stage stage;

    private Texture popUpMenuBackground;
    private Texture gameBackground;
    private Texture gameOver;
    private SpriteBatch transparentBatch;
    private OrthographicCamera camera;
    private TaskManager taskManager;
    //    private Sound tapSound;

    private int playTime=0;
    private SpriteBatch mainBatch;
    private Timer timer;
    public StateManager stateManager; // todo change to private
    private static StorageManager storage = new StorageManager(true);
    private final TapTap game;
}
