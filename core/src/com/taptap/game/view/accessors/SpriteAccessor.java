package com.taptap.game.view.accessors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteAccessor {//implements TweenAccessor<SpriteBatch> {
    //@Override
    public int getValues(SpriteBatch target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    //@Override
    public void setValues(SpriteBatch target, int tweenType, float[] newValues) {
        switch (tweenType){
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            default:
                assert false;
        }
    }

    public static final int ALPHA = 0;
}
