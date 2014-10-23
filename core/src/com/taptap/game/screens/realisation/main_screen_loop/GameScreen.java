package com.taptap.game.screens.realisation.main_screen_loop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.TapTap;


public class GameScreen implements Screen {
    public GameScreen(final TapTap game){
        this.game = game;
        tapImage = new Texture(Gdx.files.internal("skins/tap_icons/hud_gem_green.png"));

        mainMusicTheme = Gdx.audio.newMusic(Gdx.files.internal("music/Black Vortex.mp3"));
        mainMusicTheme.setLooping(true);

        // todo решилась проблема с переворотом+правильно реагируют координаты(оптимизированный костыль)
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch = new SpriteBatch();

//        bucket = new Rectangle(800/2-64/2, 20, 64, 64);

        //tapSound = Gdx.audio.newSound(Gdx.files.internal("tap.wav"));
        //mainMusicTheme = Gdx.audio.newMusic(Gdx.files.internal("main_theme.mp3"));

        iconsForTap = new Array<Rectangle>();
        // todo move to json
        Button.ButtonStyle style = new Button.ButtonStyle();

        optionButton = new Button(style);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // здесь мы запоминаем что надо отрисвать
        for(Rectangle raindrop : iconsForTap) {
            batch.draw(tapImage, raindrop.x, raindrop.y);
        }
        batch.end();

        Vector3 touchPoint = new Vector3(); // todo костыль с координатами(улучшеный)
        if (Gdx.input.isTouched()){
            for (int i=0; i<iconsForTap.size; ++i){
                Rectangle temp = iconsForTap.get(i);
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                if (Gdx.input.getX() > temp.getX() &&
                        touchPoint.y > temp.getY() &&
                        Gdx.input.getX() < temp.getX() + temp.getWidth() &&
                        touchPoint.y < temp.getY() + temp.getHeight()){
                    iconsForTap.removeIndex(i);
                    System.out.println("removed " + Gdx.input.getX() + " "+ Gdx.input.getY());
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
                // pause game and call option menu
            }
        });
    }

    @Override
    public void show() {
        mainMusicTheme.play();
    }

    @Override
    public void hide() {
        mainMusicTheme.pause();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        tapImage.dispose();
        mainMusicTheme.dispose();
//        tapSound.dispose();
        batch.dispose();
    }
    private void spawnTapIcon() {
        iconsForTap.add(new Rectangle(
                MathUtils.random(0, Gdx.graphics.getWidth() - tapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight() - tapImage.getHeight()),
                tapImage.getWidth(), tapImage.getHeight()));
        lastDropTime = TimeUtils.nanoTime();
        // todo заменить захардкоденые размеры
    }

//    private Vector3 touchPos = new Vector3();
    private Array<Rectangle> iconsForTap;
    private long lastDropTime;

    private Texture tapImage;
//    private Sound tapSound;
    private Music mainMusicTheme;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Button optionButton;

    private StateManager states;
    private final TapTap game;
}
