package com.taptap.game.view.renderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.taptap.game.internationalization.I18NBundleMy;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.model.tap.icons.objects.Icon;
import com.taptap.game.view.screens.Buttons;
import com.taptap.game.view.screens.town_screen.TownScreen;

public class GameRenderer {
    public GameRenderer(final SpriteBatch batch, final GameWorld gameWorld) {
        this.batch = batch;
        this.gameWorld = gameWorld;
        renderer = new Box2DDebugRenderer();

        font = (BitmapFont) DResourceManager.getInstance().get("fonts/whiteFont.fnt");
        gameBackground = new Sprite((Texture)
                DResourceManager.getInstance().get("skins/game_menu/game_bg.png"));
        gameOver = new Sprite(
                (Texture) DResourceManager.getInstance().get("skins/game_menu/game_over.png"));
    }

    public void render() {
        switch (gameWorld.getWorldState()) {
            case GAME_PREPARING:
                this.renderBackground();
                this.renderPreparingState();
                break;
            case GAME_RUNNING:
                this.renderBackground();
                this.renderWorldAndLight();
                this.renderRunState();
                renderNumbers(gameWorld.getTotalScore(), 0, 0);
                renderNumbers((int) gameWorld.getTotalTime(), -Gdx.graphics.getWidth() / 2, 0);
                break;
            case GAME_PAUSED:
                this.renderBackground();
                this.renderWorldAndLight();
                this.renderPauseState();
                break;
            case GAME_OVER:
                this.renderBackground();
                this.renderWorldAndLight();
                this.renderGameOverState();
                break;
            case GAME_EXIT:
                this.renderBackground();
                this.renderWorldAndLight();
                this.renderGameExitState();
                break;
        }

        for (Buttons button : gameWorld.getButtonsArray()) {
            button.render();
        }
    }

    private void renderWorldAndLight() {
        gameWorld.getRayHandler().render();
        renderer.render(gameWorld.getWorld(), gameWorld.getCamera().combined);
    }

    private void renderNumbers(int numbForRender, float widthAlign, float heightAlign) {
        int temp = numbForRender;
        float width = Gdx.graphics.getWidth();
        Sprite picture;
        batch.begin();
        while (temp>=0) {
            int val = temp % 10;
            temp /= 10;
            picture = ((TextureAtlas) DResourceManager.getInstance().
                    get("skins/game_menu/coins_and_numb/coins_and_hud.pack")).
                    createSprite("hud" + Integer.toString(val));

            width -= picture.getWidth();
            batch.draw(
                    picture, width + widthAlign,
                    Gdx.graphics.getHeight() + heightAlign - picture.getHeight()
            );
            if (temp<=0) {
                batch.end();
                return;
            }
        }
        batch.end();
    }

    private void renderPreparingState() {
        batch.begin();
        font.draw(batch, I18NBundleMy.getString("tap_anyway"), 5, Gdx.graphics.getHeight() / 2);
        if (Gdx.input.isTouched()) {
            gameWorld.changeWorldState(GameWorld.States.GAME_RUNNING);
        }
        batch.end();
    }

    private void renderRunState() {
        batch.begin();
        gameWorld.getSun().getSunSprite().draw(batch);
        for (Icon iconsDrop : gameWorld.getObjectsPool()) {
            batch.draw(iconsDrop.getSprite(),
                    iconsDrop.getX(), iconsDrop.getY(),
                    iconsDrop.getWidth(), iconsDrop.getHeight()
            );
        }
        batch.end();
    }

    private void renderPauseState() {
        batch.begin();
        for (Icon tapIcon : gameWorld.getObjectsPool()) {
            batch.draw(
                    tapIcon.getSprite(),
                    tapIcon.getX(), tapIcon.getY(),
                    tapIcon.getWidth(), tapIcon.getHeight()
            );
        }
        batch.end();
    }

    private void renderGameOverState() {
        batch.begin();
        batch.draw(gameOver,
                Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
        batch.end();
        if (Gdx.input.isTouched()) { // todo fix that
            gameWorld.changeWorldState(GameWorld.States.GAME_EXIT);
        }
    }

    private void renderBackground() {
        batch.begin();
        batch.disableBlending();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        batch.end();
    }

    private void renderGameExitState() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new TownScreen(batch));
    }

    public void dispose() {
        renderer.dispose();
    }

    private BitmapFont font;

    final private SpriteBatch batch;
    private Sprite gameBackground;
    private Sprite gameOver;

    final private GameWorld gameWorld;
    final private Box2DDebugRenderer renderer; // todo change to release renderer
}
