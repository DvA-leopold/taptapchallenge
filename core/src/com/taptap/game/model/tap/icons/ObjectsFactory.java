package com.taptap.game.model.tap.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.model.tap.icons.objects.BlueIcon;
import com.taptap.game.model.tap.icons.objects.Icon;
import com.taptap.game.model.tap.icons.objects.RedIcon;
import com.taptap.game.model.tap.icons.objects.YellowIcon;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ObjectsFactory {
    public ObjectsFactory(final Camera camera, final World world) {
        this.world = world;
        boarder = new Vector2(Gdx.graphics.getHeight() * 0.05f, Gdx.graphics.getWidth() * 0.05f);

        initListener(camera);

        tapIconsList = new LinkedList<>();
    }

    private void initListener(final Camera camera) { //todo need huge optimisations
        final Vector3 touchPoint = new Vector3();
        final Array<Vector2> array = new Array<>(4);
        final Vector2[] mass = new Vector2[4];
        for (int i = 0; i < mass.length; ++i) {
            mass[i] = new Vector2();
        }
        final Vector<Boolean> panFlag = new Vector<>(1);
        panFlag.add(true);

        GestureDetector.GestureListener gesturesListener = new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                camera.unproject(touchPoint.set(x, y, 0));
                for (int i = 0; i < tapIconsList.size(); ++i) {
                    fillArrayWithCords(array, mass, i);
                    if (Intersector.isPointInPolygon(array, new Vector2(touchPoint.x, touchPoint.y)) &&
                            tapIconsList.get(i) instanceof YellowIcon &&
                            count == 2) {
                        totalScore += (tapIconsList.get(i).getScore());
                        tapIconsList.remove(i).destroyBody();
                        break;
                    }
                    array.clear();
                }
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                camera.unproject(touchPoint.set(x, y, 0));
                for (int i = 0; i < tapIconsList.size(); ++i) {
                    fillArrayWithCords(array, mass, i);
                    if (Intersector.isPointInPolygon(array, new Vector2(touchPoint.x, touchPoint.y)) &&
                            tapIconsList.get(i) instanceof RedIcon) {
                        totalScore += tapIconsList.get(i).getScore();
                        tapIconsList.remove(i).destroyBody();
                        break;
                    }
                    array.clear();
                }
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                if (panFlag.get(0)) {
                    camera.unproject(touchPoint.set(x, y, 0));
                    for (int i = tapIconsList.size() - 1; i >= 0; --i) { // todo try to do this better
                        fillArrayWithCords(array, mass, i);
                        if (Intersector.isPointInPolygon(array, new Vector2(touchPoint.x, touchPoint.y))
                                && tapIconsList.get(i) instanceof BlueIcon) {
                            totalScore += tapIconsList.get(i).getScore();
                            tapIconsList.remove(i).destroyBody();
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
                panFlag.set(0, true);
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
        gestureDetector = new GestureDetector(gesturesListener);
        gestureDetector.invalidateTapSquare();
    }

    private void fillArrayWithCords(final Array<Vector2> array, final Vector2[] mass, final int i) {
        float x = tapIconsList.get(i).getX();
        float y = tapIconsList.get(i).getY();
        float width = tapIconsList.get(i).getWidth();
        float height = tapIconsList.get(i).getHeight();

        array.add(mass[0].set(x, y));
        array.add(mass[1].set(x + width, y));
        array.add(mass[2].set(x + width, y + height));
        array.add(mass[3].set(x, y + height));
    }

    public void spawn() {
        int rand = MathUtils.random(0, 100);
        if (rand < 25) {
            tapIconsList.add(new BlueIcon(boarder, world));
        } else if (rand > 25 && rand < 60) {
            tapIconsList.add(new RedIcon(boarder, world));
        } else {
            tapIconsList.add(new YellowIcon(boarder, world));
        }
    }

    public int controlFiguresNumber() {
        if (TimeUtils.millis() - lastDropTime > 1000) {
            spawn();
            lastDropTime = TimeUtils.millis();
        }
        if (tapIconsList.size() > 10) {
            tapIconsList.remove(0).destroyBody();
            totalScore -= 50;
        }
        return totalScore;
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    public List<Icon> getIconsList() {
        return tapIconsList;
    }

    public int getTotalScore() {
        return totalScore;
    }

    private final World world;
    private GestureDetector gestureDetector;

    private List<Icon> tapIconsList;

    private Vector2 boarder;
    private int totalScore;
    private long lastDropTime;
}
