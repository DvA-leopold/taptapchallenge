package com.taptap.game.screens.realisation.game.tap.icons.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.screens.realisation.game.GameScreen;
import com.taptap.game.screens.realisation.game.tap.icons.BlueIcons;
import com.taptap.game.screens.realisation.game.tap.icons.Icon;
import com.taptap.game.screens.realisation.game.tap.icons.RedIcons;
import com.taptap.game.screens.realisation.game.tap.icons.YellowIcons;

public class AbstractIconFactory {
    public AbstractIconFactory(float blockHeight, float blockWidth){
        this.tapImageHeight = 40;
        this.tapImageWidth = 40;
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;

        tapIcons = new Array<Icon>(10);
    }

    public void spawn(){
        int rand = MathUtils.random(0, 100);
        if (rand < 25) {
            tapIcons.add(new RedIcons(
                            MathUtils.random(blockHeight+20, Gdx.graphics.getWidth()-tapImageWidth),
                            MathUtils.random(0, Gdx.graphics.getHeight()-tapImageHeight-blockWidth-20),
                            tapImageWidth, tapImageHeight)
            );
        } else if (rand > 25 && rand < 60) {
            tapIcons.add(new BlueIcons(
                            MathUtils.random(blockHeight+20,Gdx.graphics.getWidth()-tapImageWidth),
                            MathUtils.random(0, Gdx.graphics.getHeight()-tapImageHeight-blockWidth-20),
                            tapImageWidth, tapImageHeight)
            );
        } else {
            tapIcons.add(new YellowIcons(
                            MathUtils.random(blockHeight+20,Gdx.graphics.getWidth()-tapImageWidth),
                            MathUtils.random(0, Gdx.graphics.getHeight()-tapImageHeight-blockWidth-20),
                            tapImageWidth, tapImageHeight)
            );
        }
        // todo add more stuff
    }
    public int controlGameState(){
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
            numberOfFigures++;
            spawn();
            lastDropTime = TimeUtils.nanoTime();
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

    private Array<Icon> tapIcons;
    private int numberOfFigures;
    private int totalScore;
    private long lastDropTime;
    private final int tapImageHeight;
    private final int tapImageWidth;
    private final float blockHeight;
    private final float blockWidth;
}
