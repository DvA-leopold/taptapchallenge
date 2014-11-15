package com.taptap.game.screens.realisation.game.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.taptap.game.screens.realisation.game.tap.icons.factory.Icon;

public class RedIcons implements Icon {
    static {
        redInput = new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                System.out.println("RedIcons.tap");
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                System.out.println("RedIcons.longPress");
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                System.out.println("RedIcons.fling");
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                System.out.println("RedIcons.pan");
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                System.out.println("RedIcons.panStop");
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                System.out.println("RedIcons.pinch");
                return false;
            }
        };
    }

    public static GestureDetector.GestureListener getListener(){
        return redInput;
    }

    public RedIcons(float blockHeight, float blockWidth){
        taptapImage = new Texture(Gdx.files.internal("skins/game_menu/tap_icons/hud_gem_red.png"));
        coords = new Rectangle(
                MathUtils.random(blockHeight + 20, Gdx.graphics.getWidth() - taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-taptapImage.getHeight()-blockWidth-20),
                taptapImage.getWidth(), taptapImage.getHeight());
    }

    @Override
    public int addScore() {
        return 25;
    }

    @Override
    public void setCoords(int x, int y, int w, int h) {
        coords.set(x,y,w,h);
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

    private static GestureDetector.GestureListener redInput;
    private Rectangle coords;

    private static Texture taptapImage;
}
