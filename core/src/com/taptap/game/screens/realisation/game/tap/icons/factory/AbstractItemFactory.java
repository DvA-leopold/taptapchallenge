package com.taptap.game.screens.realisation.game.tap.icons.factory;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
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
        xyAxe = new Vector2();
        deltaSum = new Vector2();
        gesturesListener = new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                System.out.println("AbstractItemFactory.touchDown");
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                camera.unproject(touchPoint.set(x, y, 0));
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
                }
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
                //camera.unproject(touchPoint.set(x, y, 0));
                deltaSum.add(deltaX, deltaY);
                //xyAxe.set(x,y);

                System.out.println("AbstractItemFactory.pan: "+ touchPoint.x + " "+ deltaX);
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                camera.unproject(touchPoint.set(x,y,0));
                Vector2 startPoint = new Vector2(touchPoint.x-deltaSum.x, touchPoint.y-deltaSum.y);
                Vector2 finishPoint = new Vector2(touchPoint.x, touchPoint.y);
                //Line swipeLine = new Line(startPoint.x, startPoint.y, x, y);

                for (Icon tapIcon : tapIcons){
                    if (
                            startPoint.x < tapIcon.getX()+tapIcon.getWidth() &&
                            startPoint.x > tapIcon.getX() ||
                                    startPoint.y < tapIcon.getY()+tapIcon.getHeight() &&
                                            startPoint.y > tapIcon.getY()
                            ){
                        System.out.println("gotcha");

                    }

                    if (true){
                        //System.out.println("start :" + startPoint.x + " " + startPoint.y + " " + finishPoint.x + " " + finishPoint.y);
                        //System.out.println(tapIcon.getX() + " " + tapIcon.getY());
                        //System.out.println(tapIcon.getWidth() + " " + tapIcon.getHeight());
                    }
                    //System.out.println(intersect3+ " ");
                }
                deltaSum.set(0,0);

                //System.out.println("AbstractItemFactory.panStop");
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
    private Vector2 xyAxe, deltaSum = new Vector2();
    ShapeRenderer render = new ShapeRenderer();
////////////////////////////////////////////////////////////////
    private Array<Icon> tapIcons;
    private int totalScore;
    private long lastDropTime;

    private final float spawnBoarderX;
    private final float spawnBoarderY;
}
