package com.taptap.game.screens.realisation.game.tap.icons.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.taptap.game.screens.realisation.game.tap.icons.BlueIcons;
import com.taptap.game.screens.realisation.game.tap.icons.Icon;
import com.taptap.game.screens.realisation.game.tap.icons.RedIcons;
import com.taptap.game.screens.realisation.game.tap.icons.YellowIcons;

public class AbstractIconFactory {
    public AbstractIconFactory(int tapImageHeight, int tapImageWidth, float blockHeight, float blockWidth){
        this.tapImageHeight = tapImageHeight;
        this.tapImageWidth = tapImageWidth;
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;

        tapIcons = new Array<Icon>(10);
    }

    public void spawn(){
        int rand = MathUtils.random(0, 100);
        if (rand < 25) {
            tapIcons.add(new RedIcons(
                            MathUtils.random(blockHeight + 20,
                                    Gdx.graphics.getWidth() - tapImageWidth),
                            MathUtils.random(0, Gdx.graphics.getHeight() - tapImageHeight -
                                    blockWidth - 20),
                            tapImageWidth, tapImageHeight)
            );
        } else if (rand > 25 && rand < 60) {
            tapIcons.add(new BlueIcons(
                            MathUtils.random(blockHeight +20,
                                    Gdx.graphics.getWidth() - tapImageWidth),
                            MathUtils.random(0, Gdx.graphics.getHeight() - tapImageHeight -
                                    blockWidth -20),
                            tapImageWidth, tapImageHeight)
            );
        } else {
            tapIcons.add(new YellowIcons(
                            MathUtils.random(blockHeight +20,
                                    Gdx.graphics.getWidth() - tapImageWidth),
                            MathUtils.random(0, Gdx.graphics.getHeight() - tapImageHeight -
                                    blockWidth -20),
                            tapImageWidth, tapImageHeight)
            );
        }
        // todo add more stuff
    }
    public void incNumberOfFigures(){
        numberOfFigures++;
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

    private Array<Icon> tapIcons;
    private int numberOfFigures;
    private final int tapImageHeight;
    private final int tapImageWidth;
    private final float blockHeight;
    private final float blockWidth;
}
