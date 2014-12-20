package com.taptap.game.view.screens.game_screen;

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
import com.taptap.game.model.music.player.MusicManager;
import com.taptap.game.model.save.manager.StorageManager;
import com.taptap.game.view.screens.game_screen.buttons.GameButtonsInitializer;
import com.taptap.game.view.screens.game_screen.buttons.PopUpButtonInitializer;
import com.taptap.game.view.buttons.interfaces.Buttons;
import com.taptap.game.model.tap.icons.factory.AbstractItemFactory;
import com.taptap.game.model.tap.icons.factory.Icon;
import com.taptap.game.view.screens.mainmenu_screen.MainMenuScreen;
import com.taptap.game.model.resource.manager.ResourceManager;
import debug.statistics.FPS_MEM_DC;

public class GameScreen implements Screen {
    public GameScreen() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = ResourceManager.getInstance().get(ResourceManager.fonts); //todo поменять шрифты
        gameBackground = new Sprite(ResourceManager.getInstance().get(ResourceManager.gameBackground));
        gameOver = new Sprite(ResourceManager.getInstance().get(ResourceManager.gameOver));

        mainBatch = new SpriteBatch();
        transparentBatch = new SpriteBatch();

        gameButtons = new GameButtonsInitializer(mainBatch);
        popUpButtons = new PopUpButtonInitializer(mainBatch);

        iconFactory = new AbstractItemFactory(camera);
        inputMultiplexer = new InputMultiplexer();

        stateManager = new StateManager();
        states = States.GAME_RUNNING;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        switch (states){
            case GAME_RUNNING:
                stateManager.runState();
                break;
            case GAME_PAUSED:
                stateManager.pauseState();
                break;
            case GAME_OVER:
                stateManager.gameOverState();
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
        states = States.GAME_PAUSED;
    }

    @Override
    public void resume() {
        MusicManager.play(this);
        //states = StateManager.GAME_RUNNING;
    }

    @Override
    public void dispose() {
        mainBatch.dispose();
        transparentBatch.dispose();
        gameButtons.dispose();
        popUpButtons.dispose();
    }

    private void renderNumbers(int numbForRender, float widthAlign, float heightAlign) {
        int temp = numbForRender;
        float width = Gdx.graphics.getWidth();
        Sprite picture;
        while (temp>=0) {
            int val = temp % 10;
            temp /= 10;
            picture = ResourceManager.getInstance().
                    get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                    createSprite("hud" + Integer.valueOf(val));

            width -= picture.getWidth();
            mainBatch.draw(
                    picture, width + widthAlign,
                    Gdx.graphics.getHeight() + heightAlign - picture.getHeight()
            );
            if (temp<=0){
                return;
            }
        }
    }

    public static StorageManager getStorage(){
        return storage;
    }

    public enum States {
        GAME_RUNNING(true),
        GAME_PAUSED(true),
        GAME_OVER(true),
        GAME_EXIT(true);

        private boolean firstTimeInit;

        private States (boolean firstTimeInit){
            this.firstTimeInit = firstTimeInit;
        }
    }

    public void changeState(States state){
        this.states = state;
    }

    public class StateManager {
        private void runState() {
            if (States.GAME_RUNNING.firstTimeInit) {
                States.GAME_RUNNING.firstTimeInit = false;
                States.GAME_PAUSED.firstTimeInit = true;
                alpha = 0;
                inputMultiplexer.addProcessor(gameButtons.getStage());
                inputMultiplexer.addProcessor(iconFactory.getGestureDetector()); //можно изменить задержку измерения и т.п
            }
            if (!readyToStart) {
                mainBatch.begin();
                mainBatch.disableBlending();
                mainBatch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                mainBatch.enableBlending();
                font.draw(mainBatch, "press anywhere to start", 5, Gdx.graphics.getHeight() / 2);
                mainBatch.end();
                if (Gdx.input.isTouched()) {
                    readyToStart = true;
                }
            } else {
                totalTime -= Gdx.graphics.getDeltaTime();
                mainBatch.begin();
                mainBatch.disableBlending();
                mainBatch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                mainBatch.enableBlending();
                for (Icon iconsDrop : iconFactory.getIconsArray()) {
                    mainBatch.draw(iconsDrop.getTexture(),
                            iconsDrop.getX(), iconsDrop.getY(),
                            iconFactory.getItemsSize(),
                            iconFactory.getItemsSize());
                }
                renderNumbers(iconFactory.getTotalScore(), 0, 0);
                renderNumbers((int)totalTime, -Gdx.graphics.getWidth() / 2, 0);

                mainBatch.end();
                if (iconFactory.controlFiguresNumber() < 0) {
                    states = States.GAME_OVER;
                }
                if (totalTime <= 0) {
                    GameScreen.getStorage().saveDataValue(
                            "player " + GameScreen.getStorage().getAllData().size(),
                            iconFactory.getTotalScore()
                    );
                    states = States.GAME_EXIT;
                }
                gameButtons.render();
                //System.out.println(mainBatch.renderCalls);
            }

        }
        private void pauseState() {
            if (States.GAME_PAUSED.firstTimeInit) {
                States.GAME_PAUSED.firstTimeInit = false;
                States.GAME_RUNNING.firstTimeInit = true;
                inputMultiplexer.addProcessor(popUpButtons.getStage());
                inputMultiplexer.removeProcessor(iconFactory.getGestureDetector());
            }
            alpha = (float) Math.min(alpha + Gdx.graphics.getDeltaTime() / 2, 0.7);
            mainBatch.begin();
            mainBatch.disableBlending();
            mainBatch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            mainBatch.enableBlending();
            mainBatch.end();
            // todo оптимизировать, здесь куча лишней прорисовки
            transparentBatch.begin();
            transparentBatch.setColor(0.0f, 0.0f, 0.0f, alpha);
            transparentBatch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            for (Icon tapIcon : iconFactory.getIconsArray()) {
                transparentBatch.draw(tapIcon.getTexture(), tapIcon.getX(), tapIcon.getY());
            }
            transparentBatch.end();
            popUpButtons.render();
        }

        private void gameOverState() {
            mainBatch.begin();
            mainBatch.setColor(0.5f, 0f, 0f, 0.6f);
            mainBatch.disableBlending();
            mainBatch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            mainBatch.enableBlending();
            mainBatch.draw(gameOver,
                    Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
            mainBatch.end();
            if (Gdx.input.isTouched()) {
                States.GAME_PAUSED.firstTimeInit = true;
                States.GAME_RUNNING.firstTimeInit = true;
                states = States.GAME_EXIT;
            }
            //System.out.println(mainBatch.renderCalls);
        }

        private void gameExitState() {
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
    private StateManager stateManager;
    private States states;
    public final InputMultiplexer inputMultiplexer;

    //    private Sound tapSound;
    private float totalTime = 150;
    boolean readyToStart = false;
    private float alpha = 0;
    private BitmapFont font;

    private static StorageManager storage = new StorageManager(true);
}
