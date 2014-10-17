package com.taptap.game.screens.realisation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.TapTap;


public class GameScreen implements Screen {
    public GameScreen(final TapTap game){
        this.game = game;
        tapImage = new Texture(Gdx.files.internal("badlogic.jpg"));

        mainMusicTheme = Gdx.audio.newMusic(Gdx.files.internal("Black Vortex.mp3"));
        mainMusicTheme.setLooping(true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

//        bucket = new Rectangle(800/2-64/2, 20, 64, 64);

        //tapSound = Gdx.audio.newSound(Gdx.files.internal("tap.wav"));
        //mainMusicTheme = Gdx.audio.newMusic(Gdx.files.internal("main_theme.mp3"));

        iconsForTap = new Array<Rectangle>();
        spawnTapIcon();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // здесь мы запоминаем что надо отрисвать
//        batch.draw(tapImage, bucket.x, bucket.y);
        for(Rectangle raindrop : iconsForTap) {
            batch.draw(tapImage, raindrop.x, raindrop.y);
        }
        batch.end();
/*
        if (Gdx.input.isTouched()){ // todo здесь нужно поработать с обработкой нажатий
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int)touchPos.x - 64 / 2;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 800 - 64) bucket.x = 800 - 64;
*/
        if (Gdx.input.isTouched()){
            for (int i=0; i<iconsForTap.size; ++i){
                Rectangle temp = iconsForTap.get(i);
                if (Gdx.input.getX() > temp.getX() &&
                        Gdx.input.getY() > temp.getY() &&
                        Gdx.input.getX() < temp.getX() + temp.getWidth() &&
                        Gdx.input.getY() < temp.getY() + temp.getHeight()){
                    iconsForTap.removeIndex(i);
                    System.out.println("removed " + Gdx.input.getX() + " "+ Gdx.input.getY());
                    break; // todo оптимизировать эту шнягу
                }
            }
        }
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
            spawnTapIcon();
        }
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
        //        mainMusicTheme.dispose();
        tapImage.dispose();
        mainMusicTheme.dispose();
//        tapSound.dispose();
        batch.dispose();
    }

    private void spawnTapIcon() {
        iconsForTap.add(new Rectangle(
                MathUtils.random(0, 800 - 64),
                MathUtils.random(0, 480 - 64), 64, 64));
        lastDropTime = TimeUtils.nanoTime();
    }

//    private Vector3 touchPos = new Vector3();
    private Array<Rectangle> iconsForTap;
    private long lastDropTime;

    private Texture tapImage;
//    private Sound tapSound;
    private Music mainMusicTheme;
    private SpriteBatch batch;
    private OrthographicCamera camera;
//    private Rectangle bucket;

    private final TapTap game;
}
