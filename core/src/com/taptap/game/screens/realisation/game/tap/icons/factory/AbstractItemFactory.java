package com.taptap.game.screens.realisation.game.tap.icons.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.screens.realisation.game.tap.icons.BlueIcons;
import com.taptap.game.screens.realisation.game.tap.icons.RedIcons;
import com.taptap.game.screens.realisation.game.tap.icons.YellowIcons;

public class AbstractItemFactory {
    public AbstractItemFactory(float blockHeight, float blockWidth){
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;

        tapIcons = new Array<Icon>(10);
    }

    public void spawn() {
        int rand = MathUtils.random(0, 100);
        if (rand < 25) {
            tapIcons.add(new BlueIcons(blockHeight, blockWidth));
        } else if (rand > 25 && rand < 60) {
            tapIcons.add(new BlueIcons(blockHeight,blockWidth));
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

    private Array<Icon> tapIcons; // todo массив просто ужасен
    private int numberOfFigures;
    private int totalScore;
    private long lastDropTime;
    //private final float tapImageHeight;
    //private final float tapImageWidth;
    private final float blockHeight;
    private final float blockWidth;
}
