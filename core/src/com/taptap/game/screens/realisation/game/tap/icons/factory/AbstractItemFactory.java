package com.taptap.game.screens.realisation.game.tap.icons.factory;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.screens.realisation.game.tap.icons.BlueIcons;
import com.taptap.game.screens.realisation.game.tap.icons.RedIcons;
import com.taptap.game.screens.realisation.game.tap.icons.YellowIcons;

public class AbstractItemFactory {
    public AbstractItemFactory(float spawnBoarderX, float spawnBoarderY, Camera camera){
        this.spawnBoarderX = spawnBoarderX;
        this.spawnBoarderY = spawnBoarderY;
        tapIcons = new Array<Icon>(15);
        initListener(camera);
    }

    public void initListener(final Camera camera) {
        final Vector3 touchPoint = new Vector3();
        gesturesListener = new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                System.out.println("AbstractItemFactory.touchDown");
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
              /*  camera.unproject(touchPoint.set(x, y, 0));
                for (int i=0; i<tapIcons.size; ++i){
                    Icon temp = tapIcons.get(i);
                    if (x > temp.getX() &&
                            touchPoint.y > temp.getY() &&
                            x < temp.getX() + temp.getWidth() &&
                            touchPoint.y < temp.getY() + temp.getHeight()){
                        tapIcons.removeIndex(i);
                        totalScore+=(temp.getScore());
                        break;
                    }
                }*/
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                System.out.println("AbstractItemFactory.longPress");
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                System.out.println("AbstractItemFactory.fling "+ velocityX);
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                camera.unproject(touchPoint.set(x, y, 0));
                Array<Vector2> array = new Array<Vector2>(4);
                for (int i=0; i<tapIcons.size; ++i){ //todo сделать рефакторинг кода, тут пиздец
                    array.add(new Vector2(
                            tapIcons.get(i).getRect().x,
                            tapIcons.get(i).getRect().y));
                    array.add(new Vector2(
                            tapIcons.get(i).getRect().x+tapIcons.get(i).getWidth(),
                            tapIcons.get(i).getRect().y));
                    array.add(new Vector2(
                            tapIcons.get(i).getRect().x+tapIcons.get(i).getWidth(),
                            tapIcons.get(i).getRect().y+tapIcons.get(i).getHeight()));
                    array.add(new Vector2(
                            tapIcons.get(i).getRect().x,
                            tapIcons.get(i).getRect().y+tapIcons.get(i).getHeight()));

                    if (Intersector.isPointInPolygon(array, new Vector2(touchPoint.x,touchPoint.y))){
                        tempCounter[i]++;
                        break;
                    }
                    array.clear();
                }
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                //camera.unproject(touchPoint.set(x,y,0));
                for (int i=0;i<tempCounter.length;++i){//todo сделать рефакторинг кода, тут тоже пиздец
                    if (tempCounter[i]>1){
                        tapIcons.removeIndex(i);
                        break;
                    }
                }
                for (int i=0;i<tempCounter.length;++i){
                    tempCounter[i]=0;
                }
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                System.out.println("AbstractItemFactory.zoom");
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                System.out.println("AbstractItemFactory.pinch");
                return false;
            }
        };
    }

    public void spawn() {
        int rand = MathUtils.random(0, 100);
        if (rand < 25) {
            tapIcons.add(new BlueIcons(spawnBoarderX, spawnBoarderY));
        } else if (rand > 25 && rand < 60) {
            tapIcons.add(new RedIcons(spawnBoarderX, spawnBoarderY));
        } else {
            tapIcons.add(new YellowIcons(spawnBoarderX, spawnBoarderY));
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

    public GestureDetector.GestureListener getListener(){
        return gesturesListener;
    }

    public Array<Icon> getIconsArray(){
        return tapIcons;
    }
    public int getTotalScore(){
        return totalScore;
    }

    private GestureDetector.GestureListener gesturesListener;
    //////////////////////////////////////////////////
    int[] tempCounter = new int[10];
    //////////////////////////////////////////////////
    private Array<Icon> tapIcons;
    private int totalScore;
    private long lastDropTime;

    private final float spawnBoarderX;
    private final float spawnBoarderY;
}
