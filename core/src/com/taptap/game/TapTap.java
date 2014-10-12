package com.taptap.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;

public class TapTap implements ApplicationListener {
	@Override
	public void create () {
	    tapImage = new Texture(Gdx.files.internal("badlogic.jpg"));
        menuScreenTheme = Gdx.audio.newMusic(Gdx.files.internal("Black Vortex.mp3"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        bucket = new Rectangle();
        bucket.x = 800/2-64/2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        //tapSound = Gdx.audio.newSound(Gdx.files.internal("tap.wav"));
        //mainMusicTheme = Gdx.audio.newMusic(Gdx.files.internal("main_theme.mp3"));

        menuScreenTheme.setLooping(true);
        menuScreenTheme.play();

        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
	public void render () {
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // здесь мы запоминаем что надо отрисвать
        batch.draw(tapImage, bucket.x, bucket.y);
        for(Rectangle raindrop : raindrops) {
            batch.draw(tapImage, raindrop.x, raindrop.y);
        }
        batch.end();

        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int)touchPos.x - 64 / 2;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 800 - 64) bucket.x = 800 - 64;

        if(TimeUtils.nanoTime() - lastDropTime > 100000000) spawnRaindrop();
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
        menuScreenTheme.dispose();
//        tapSound.dispose();
        batch.dispose();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    private Vector3 touchPos = new Vector3();

    private Array<Rectangle> raindrops;
    private long lastDropTime;

    private Texture tapImage;
    private Texture bucketImage;
    private Sound tapSound;
    private Music mainMusicTheme;
    private Music menuScreenTheme;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle bucket;

}
