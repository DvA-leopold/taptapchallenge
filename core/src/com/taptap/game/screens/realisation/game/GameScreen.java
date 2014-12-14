package com.taptap.game.screens.realisation.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.taptap.game.music.player.MusicManager;
import com.taptap.game.save.manager.StorageManager;
import com.taptap.game.screens.realisation.game.button.styles.Buttons;
import com.taptap.game.screens.realisation.game.button.styles.GameButtonsInitializer;
import com.taptap.game.screens.realisation.game.button.styles.PopUpButtonInitializer;
import com.taptap.game.screens.realisation.game.tap.icons.factory.AbstractItemFactory;
import com.taptap.game.screens.realisation.game.tap.icons.factory.Icon;
import com.taptap.game.screens.realisation.mainmenu.MainMenuScreen;
import com.taptap.game.resource.manager.ResourceManager;
import debug.statistics.FPS_MEM_DC;

public class GameScreen implements Screen {
    public GameScreen(){
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = ResourceManager.getInstance().get(ResourceManager.fonts); //todo поменять шрифты
        gameBackground = new Sprite(ResourceManager.getInstance().get(ResourceManager.gameBackground));
        gameOver = new Sprite(ResourceManager.getInstance().get(ResourceManager.gameOver));

        gameButtons = new GameButtonsInitializer();
        popUpButtons = new PopUpButtonInitializer();

        iconFactory = new AbstractItemFactory(camera);
        mainBatch = new SpriteBatch();
        transparentBatch = new SpriteBatch();

        inputMultiplexer = new InputMultiplexer();

        stateManager = StateManager.GAME_RUNNING;
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
                stateManager.gameExitState();
                break;
        }
        FPS_MEM_DC.fpsLog();
    }

    @Override
    public void show() {
        //mainBatch.setProjectionMatrix(camera.combined);
        //transparentBatch.setProjectionMatrix(camera.combined);
        popUpButtons.setListeners(this);
        gameButtons.setListeners(this);

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
        gameButtons.dispose();
        popUpButtons.dispose();
    }

    private void renderNumbers(int numbForRender, float widthAlign, float heightAlign) {
        int temp = numbForRender;// todo можно оптимизировать
        float width = Gdx.graphics.getWidth();
        Sprite picture;
        while (temp>=0) {
            int val = temp % 10;
            temp /= 10;
            switch (val) {
                case 0:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud0");
                    break;
                case 1:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud1");
                    break;
                case 2:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud2");
                    break;
                case 3:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud3");
                    break;
                case 4:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud4");
                    break;
                case 5:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud5");
                    break;
                case 6:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud6");
                    break;
                case 7:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud7");
                    break;
                case 8:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud8");
                    break;
                case 9:
                    picture = ResourceManager.getInstance().
                            get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                            createSprite("hud9");
                    //picture = coinsAndNumbers.createSprite("hud9");
                    break;
                default:
                    Gdx.app.error("Error", " no such number");
                    return;
            }
            width -= picture.getWidth();
            mainBatch.draw(
                    picture, width + widthAlign,
                    Gdx.graphics.getHeight() + heightAlign - picture.getHeight()
            );
            if (temp<=0){
                return;
            }
            FPS_MEM_DC.drawCalls++;
        }
    }

    public static StorageManager getStorage(){
        return storage;
    }

    public enum StateManager {
        GAME_RUNNING(true),
        GAME_PAUSED(true),
        GAME_OVER(true),
        GAME_EXIT(true);

        private boolean firstTimeInit;

        private StateManager(boolean firstTimeInit){
            this.firstTimeInit = firstTimeInit;
        }
/*
        private boolean gameStartDelayFinished(float delayInSeconds, final GameScreen screen) {
            screen.mainBatch.begin();
            screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            screen.renderNumbers((int)delayInSeconds, -Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight() / 2);
            screen.mainBatch.end();
            return delayInSeconds > -1;
        }
*/
        private void runState(final GameScreen screen){
            if (GAME_RUNNING.firstTimeInit){
                GAME_RUNNING.firstTimeInit = false;
                screen.alpha = 0;
                GAME_PAUSED.firstTimeInit = true;
                screen.inputMultiplexer.addProcessor(screen.gameButtons.getStage());
                screen.inputMultiplexer.addProcessor(screen.iconFactory.getGestureDetector()); //можно изменить задержку измерения и т.п
            }
            if (!screen.readyToStart){
                screen.mainBatch.begin();
                screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                screen.font.draw(screen.mainBatch, "press anywhere to start", 5, Gdx.graphics.getHeight()/2);
                screen.mainBatch.end();
                if (Gdx.input.isTouched()){
                    screen.readyToStart = true;
                }
            } else {
                screen.totalTime -= Gdx.graphics.getDeltaTime();
                screen.mainBatch.begin();
                screen.mainBatch.draw(screen.gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                FPS_MEM_DC.drawCalls++;
                for(Icon iconsDrop : screen.iconFactory.getIconsArray()) {
                    screen.mainBatch.draw(iconsDrop.getTexture(),
                            iconsDrop.getX(), iconsDrop.getY(),
                            screen.iconFactory.getItemsSize(),screen.iconFactory.getItemsSize());
                    FPS_MEM_DC.drawCalls++;
                }
                screen.renderNumbers(screen.iconFactory.getTotalScore(), 0, 0);
                screen.renderNumbers((int)screen.totalTime, -Gdx.graphics.getWidth()/2 ,0);

                screen.mainBatch.end();

                if (screen.iconFactory.controlFiguresNumber()<0) {
                    screen.stateManager = GAME_OVER;
                }
                if (screen.totalTime <= 0) {
                    GameScreen.getStorage().saveDataValue(
                            "player " + GameScreen.getStorage().getAllData().size(),
                            screen.iconFactory.getTotalScore()
                    );
                    screen.stateManager = GameScreen.StateManager.GAME_EXIT;
                }
                screen.gameButtons.render();
            }

        }

        private void pauseState(final GameScreen screen){
            if (GAME_PAUSED.firstTimeInit){
                GAME_PAUSED.firstTimeInit = false;
                screen.inputMultiplexer.addProcessor(screen.popUpButtons.getStage());
                screen.inputMultiplexer.removeProcessor(screen.iconFactory.getGestureDetector());
                GAME_RUNNING.firstTimeInit = true;
            }
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
            screen.popUpButtons.render();
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

        private void gameExitState(){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }
    }

    private AbstractItemFactory iconFactory;
    private final Buttons
            gameButtons,
            popUpButtons;

    private final Sprite gameBackground;
    private final Sprite gameOver;
    private final SpriteBatch transparentBatch;
    private final SpriteBatch mainBatch;
    private OrthographicCamera camera;
    public StateManager stateManager; // todo change to private
    public final InputMultiplexer inputMultiplexer;

    //    private Sound tapSound;
    private float totalTime = 150;
    boolean readyToStart = false;
    private float alpha = 0;
    private BitmapFont font;

    private static StorageManager storage = new StorageManager(true);
}
