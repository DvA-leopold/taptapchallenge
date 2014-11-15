package com.taptap.game.screens.realisation.game.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.taptap.game.screens.realisation.game.tap.icons.factory.Icon;

public class YellowIcons implements Icon {
    static {
        yellowInput = new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                return false;
            }
        };
    }

    public static GestureDetector.GestureListener getListener(){
        return yellowInput;
    }

    public YellowIcons(float blockHeight, float blockWidth){
        taptapImage = new Texture(Gdx.files.internal("skins/game_menu/tap_icons/hud_gem_yellow.png"));
        coords = new Rectangle(
                MathUtils.random(blockHeight + 20, Gdx.graphics.getWidth() - taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-taptapImage.getHeight()-blockWidth-20),
                taptapImage.getWidth(), taptapImage.getHeight());
    }

    @Override
    public int addScore() {
        return 20;
    }

    @Override
    public void setCoords(int x, int y, int z, int w) {

    }

    @Override
    public Texture getTexture() {
        return taptapImage;
    }

    @Override
    public float getX() {
        return coords.getX();
    }

    @Override
    public float getY() {
        return coords.getY();
    }

    @Override
    public float getWidth() {
        return coords.getWidth();
    }

    @Override
    public float getHeight() {
        return coords.getHeight();
    }

    private static GestureDetector.GestureListener yellowInput;
    private Rectangle coords;

    private static Texture taptapImage;
}
