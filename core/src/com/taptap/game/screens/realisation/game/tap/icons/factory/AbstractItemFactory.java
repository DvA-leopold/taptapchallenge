package com.taptap.game.screens.realisation.game.tap.icons.factory;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.screens.realisation.game.tap.icons.BlueIcons;
import com.taptap.game.screens.realisation.game.tap.icons.RedIcons;
import com.taptap.game.screens.realisation.game.tap.icons.YellowIcons;

public class AbstractItemFactory {
    public AbstractItemFactory(float blockHeight, float blockWidth){
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;
        tapIcons = new Array<Icon>(15);
        initListener();
    }

    public void initListener() {
        gesturesListener = new GestureDetector.GestureListener() {
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

    public void spawn() {
        int rand = MathUtils.random(0, 100);
        if (rand < 25) {
            tapIcons.add(new BlueIcons(blockHeight, blockWidth));
        } else if (rand > 25 && rand < 60) {
            tapIcons.add(new RedIcons(blockHeight, blockWidth));
        } else {
            tapIcons.add(new YellowIcons(blockHeight, blockWidth));
        }
    }

    public int controlFiguresNumber() {
        if(TimeUtils.millis() - lastDropTime > 1000){
            numberOfFigures++;
            spawn();
            lastDropTime = TimeUtils.millis();
        }
        if (getNumberOfFigures() > 10){
            removeIcon(0);
            totalScore -=50;
            numberOfFigures--;
        }
        return totalScore;
    }
    public GestureDetector.GestureListener getListener(){
        return gesturesListener;
    }

    public void decNumberOfFigures(){
        numberOfFigures--;
    }
    public int getNumberOfFigures(){
        return numberOfFigures;
    }
    public void removeIcon(int index){
        tapIcons.removeIndex(index);
    }
    public Array<Icon> getIconsArray(){
        return tapIcons;
    }
    public int getTotalScore(){
        return totalScore;
    }
    public void addToTotal(int val){
        totalScore+=val;
    }

    private Array<Icon> tapIcons; // todo этот массив просто ужасен
    private GestureDetector.GestureListener gesturesListener;
    private int numberOfFigures;

    private int totalScore;
    private long lastDropTime;

    private final float blockHeight;
    private final float blockWidth;
}
