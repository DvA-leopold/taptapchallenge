package com.taptap.game.screens.realisation.game.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.taptap.game.screens.realisation.game.tap.icons.factory.Icon;

public class BlueIcons implements Icon {
    public BlueIcons(float blockHeight, float blockWidth){
        taptapImage = new Texture(Gdx.files.internal("skins/game_menu/tap_icons/hud_gem_blue.png"));
        coords = new Rectangle(
                MathUtils.random(blockHeight + 20, Gdx.graphics.getWidth()-taptapImage.getWidth()),
                MathUtils.random(0, Gdx.graphics.getHeight()-blockWidth - 20),
                taptapImage.getWidth(), taptapImage.getHeight());
        listener = new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                System.out.println("BlueIcons.touchDown");
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                System.out.println("BlueIcons.tap");
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                System.out.println("BlueIcons.longPress");
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                System.out.println("BlueIcons.fling");
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                System.out.println("BlueIcons.pan");
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                System.out.println("BlueIcons.panStop");
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                System.out.println("BlueIcons.zoom");
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                System.out.println("BlueIcons.pinch");
                return false;
            }
        };
    }

    @Override
    public int addScore() {
        return 40;
    }

    @Override
    public void setCoords(int x, int y, int z, int w) {
        coords.set(x,y,z,w);
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
        return coords.getWidth();
    }

    private GestureDetector.GestureListener listener;
    private Texture taptapImage;
    private Rectangle coords;
}
