package com.taptap.game.view.screens.game_screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.tap.icons.factory.Icon;
import com.taptap.game.view.screens.mainmenu_screen.MainMenuScreen;

public class GameRenderer {
    public GameRenderer(final SpriteBatch batch, final GameWorld gameWorld) {
        this.batch = batch;
        this.gameWorld = gameWorld;
        renderer = new Box2DDebugRenderer();

        font = ResourceManager.getInstance().get(ResourceManager.fonts);
        gameBackground = new Sprite(ResourceManager.getInstance().get(ResourceManager.gameBackground));
        gameOver = new Sprite(ResourceManager.getInstance().get(ResourceManager.gameOver));
    }

    public void render() {
        switch (gameWorld.getWorldState()) {
            case GAME_RUNNING:
                this.renderRunState();
                break;
            case GAME_PAUSED:
                this.renderPauseState();
                break;
            case GAME_OVER:
                this.renderGameOverState();
                break;
            case GAME_EXIT:
                this.renderGameExitState();
                break;
        }
        renderer.render(gameWorld.getWorld(), gameWorld.getCamera().combined);
        gameWorld.getRayHandler().render();
    }

    private void renderNumbers(int numbForRender, float widthAlign, float heightAlign) {
        int temp = numbForRender;
        float width = Gdx.graphics.getWidth();
        Sprite picture;
        while (temp>=0) {
            int val = temp % 10;
            temp /= 10;
            picture = ResourceManager.getInstance().
                    get(ResourceManager.TextureAtlasNumber, TextureAtlas.class).
                    createSprite("hud" + Integer.toString(val));

            width -= picture.getWidth();
            batch.draw(
                    picture, width + widthAlign,
                    Gdx.graphics.getHeight() + heightAlign - picture.getHeight()
            );
            if (temp<=0){
                return;
            }
        }
    }

    private void renderRunState() {
        batch.begin();
        batch.disableBlending();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        if (!readyToStart) {
            font.draw(batch, " press anywhere to start", 5, Gdx.graphics.getHeight() / 2);
            batch.end();
            if (Gdx.input.isTouched()) {
                readyToStart = true;
            }
        } else {
            for (Icon iconsDrop : gameWorld.getObjectsPool()) {
                batch.draw(iconsDrop.getSprite(),
                        iconsDrop.getX(), iconsDrop.getY()
                );
            }
            renderNumbers(gameWorld.getTotalScore(), 0, 0);
            renderNumbers((int)gameWorld.getTotalTime(), - Gdx.graphics.getWidth() / 2, 0);

            batch.end();
            gameWorld.getButtons(0).render();

        }
    }

    private void renderPauseState() {
//        alpha = (float) Math.min(alpha + Gdx.graphics.getDeltaTime() / 2, 0.7);
        batch.begin();
        batch.disableBlending();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        for (Icon tapIcon : gameWorld.getObjectsPool()) {
            batch.draw(tapIcon.getSprite(), tapIcon.getX(), tapIcon.getY());
        }
        batch.end();
        gameWorld.getButtons(1).render();
    }

    private void renderGameOverState() {
        batch.begin();
        batch.setColor(0.5f, 0f, 0f, 0.6f);
        batch.disableBlending();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        batch.draw(gameOver,
                Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
        batch.end();
        if (Gdx.input.isTouched()) {
            gameWorld.changeWorldState(GameWorld.States.GAME_EXIT);
        }
    }

    private void renderGameExitState() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(batch));
    }

    private BitmapFont font;//todo change fonts

    private boolean readyToStart = false;

    private final SpriteBatch batch;
    private Sprite gameBackground;
    private Sprite gameOver;

    private final GameWorld gameWorld;
    private final Box2DDebugRenderer renderer; // todo change to release renderer
}
