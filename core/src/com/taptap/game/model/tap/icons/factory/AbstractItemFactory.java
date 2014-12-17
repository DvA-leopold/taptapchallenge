package com.taptap.game.model.tap.icons.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.model.tap.icons.BlueIcons;
import com.taptap.game.model.tap.icons.RedIcons;
import com.taptap.game.model.tap.icons.YellowIcons;

import java.util.Vector;

public class AbstractItemFactory {
    public AbstractItemFactory(Camera camera) {
        tapIcons = new Array<Icon>(15);
        initListener(camera);
    }

    public void initListener(final Camera camera) {
        final Vector3 touchPoint = new Vector3();
        final Array<Vector2> array = new Array<Vector2>(4);
        final Vector2 point1 = new Vector2();
        final Vector2 point2 = new Vector2();
        final Vector2 point3 = new Vector2();
        final Vector2 point4 = new Vector2();
        final Vector<Boolean> panFlag = new Vector<Boolean>(1);
        panFlag.add(true);
        GestureDetector.GestureListener gesturesListener = new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                System.out.println("AbstractItemFactory.touchDown");
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                camera.unproject(touchPoint.set(x, y, 0));
                for (int i = 0; i < tapIcons.size; ++i) {
                    array.add(point1.set(
                                    tapIcons.get(i).getRect().x,
                                    tapIcons.get(i).getRect().y)
                    );
                    array.add(point2.set(
                                    tapIcons.get(i).getRect().x + getItemsSize(),
                                    tapIcons.get(i).getRect().y)
                    );
                    array.add(point3.set(
                                    tapIcons.get(i).getRect().x + getItemsSize(),
                                    tapIcons.get(i).getRect().y + getItemsSize())
                    );
                    array.add(point4.set(
                                    tapIcons.get(i).getRect().x,
                                    tapIcons.get(i).getRect().y + getItemsSize())
                    );
                    if (Intersector.isPointInPolygon(array, new Vector2(touchPoint.x, touchPoint.y)) &&
                            tapIcons.get(i) instanceof YellowIcons &&
                            count >=2 ) {
                        totalScore += (tapIcons.get(i).getScore());
                        tapIcons.removeIndex(i);
                        break;
                    }
                    array.clear();
                }
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                camera.unproject(touchPoint.set(x, y, 0));
                for (int i = 0; i < tapIcons.size; ++i) {
                    array.add(point1.set(
                                    tapIcons.get(i).getRect().x,
                                    tapIcons.get(i).getRect().y)
                    );
                    array.add(point2.set(
                                    tapIcons.get(i).getRect().x + getItemsSize(),
                                    tapIcons.get(i).getRect().y)
                    );
                    array.add(point3.set(
                                    tapIcons.get(i).getRect().x + getItemsSize(),
                                    tapIcons.get(i).getRect().y + getItemsSize())
                    );
                    array.add(point4.set(
                                    tapIcons.get(i).getRect().x,
                                    tapIcons.get(i).getRect().y + getItemsSize())
                    );
                    if (Intersector.isPointInPolygon(array, new Vector2(touchPoint.x, touchPoint.y)) &&
                            tapIcons.get(i) instanceof RedIcons) {
                        totalScore += (tapIcons.get(i).getScore());
                        tapIcons.removeIndex(i);
                        break;
                    }
                    array.clear();
                }

                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                //System.out.println("AbstractItemFactory.fling "+ velocityX);
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                if (panFlag.get(0)){
                    camera.unproject(touchPoint.set(x, y, 0));
                    for (int i=tapIcons.size-1; i>=0; --i){ // todo ужасное решение, попробовать сделать лучше
                        array.add(point1.set(
                                        tapIcons.get(i).getRect().x,
                                        tapIcons.get(i).getRect().y)
                        );
                        array.add(point2.set(
                                        tapIcons.get(i).getRect().x + getItemsSize(),
                                        tapIcons.get(i).getRect().y)
                        );
                        array.add(point3.set(
                                        tapIcons.get(i).getRect().x + getItemsSize(),
                                        tapIcons.get(i).getRect().y + getItemsSize())
                        );
                        array.add(point4.set(
                                        tapIcons.get(i).getRect().x,
                                        tapIcons.get(i).getRect().y + getItemsSize())
                        );

                        if (Intersector.isPointInPolygon(array, new Vector2(touchPoint.x,touchPoint.y))
                                && tapIcons.get(i) instanceof BlueIcons){
                            totalScore+=tapIcons.get(i).getScore();
                            tapIcons.removeIndex(i);
                            panFlag.set(0, false);
                            break;
                        }
                        array.clear();
                    }
                }
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                panFlag.set(0,true);
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                //System.out.println("AbstractItemFactory.zoom");
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                //System.out.println("AbstractItemFactory.pinch");
                return false;
            }
        };
        gestureDetector = new GestureDetector(gesturesListener);
    }

    public void spawn() {
        int rand = MathUtils.random(0, 100);
        if (rand < 25) {
            tapIcons.add(new BlueIcons(
                    Gdx.graphics.getWidth() * boarderIndentP,
                    Gdx.graphics.getHeight() * boarderIndentP + getItemsSize())
            ); // todo исправить значения
        } else if (rand > 25 && rand < 60) {
            tapIcons.add(new RedIcons(
                    Gdx.graphics.getWidth() * boarderIndentP,
                    Gdx.graphics.getHeight() * boarderIndentP + getItemsSize())
            );
        } else {
            tapIcons.add(new YellowIcons(
                    Gdx.graphics.getWidth() * boarderIndentP,
                    Gdx.graphics.getHeight() * boarderIndentP + getItemsSize())
            );
        }
    }

    public int controlFiguresNumber() {
        if(TimeUtils.millis() - lastDropTime > 1000){
            spawn();
            lastDropTime = TimeUtils.millis();
        }
        if (tapIcons.size > 10){
            tapIcons.removeIndex(0);
            totalScore -=50;
        }
        return totalScore;
    }

    public GestureDetector getGestureDetector(){
        return gestureDetector;
    }

    public float getItemsSize() {
        return Gdx.graphics.getHeight()/5;
    }

    public Array<Icon> getIconsArray(){
        return tapIcons;
    }
    public int getTotalScore(){
        return totalScore;
    }

    private GestureDetector gestureDetector;

    private Array<Icon> tapIcons;
    private float boarderIndentP = 0.05f; //5% boarder
    private int totalScore;
    private long lastDropTime;
}
