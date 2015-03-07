package com.taptap.game.view.screens.game_screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.taptap.game.internationalization.I18NBundleMy;
import com.taptap.game.model.game.world.GameWorld;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.tap.icons.objects.Icon;
import com.taptap.game.view.screens.Buttons;
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
            case GAME_PREPARING:
                this.renderPreparingState();
                break;
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
        gameWorld.getRayHandler().render();
        renderer.render(gameWorld.getWorld(), gameWorld.getCamera().combined);

        for (Buttons button : gameWorld.getButtonsArray()) {
            button.render();
        }
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

    private void renderPreparingState() {
        batch.begin();
        batch.disableBlending();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        font.draw(batch, I18NBundleMy.getString("tap_anyway"), 5, Gdx.graphics.getHeight() / 2);
        if (Gdx.input.justTouched()){
            gameWorld.changeWorldState(GameWorld.States.GAME_RUNNING);
        }
        batch.end();
    }

    private void renderRunState() {
        batch.begin();
        batch.disableBlending();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        for (Icon iconsDrop : gameWorld.getObjectsPool()) {
            batch.draw(iconsDrop.getSprite(),
                    iconsDrop.getX(), iconsDrop.getY()
            );
        }
        renderNumbers(gameWorld.getTotalScore(), 0, 0);
        renderNumbers((int) gameWorld.getTotalTime(), -Gdx.graphics.getWidth() / 2, 0);

        batch.end();
    }

    private void renderPauseState() {
        batch.begin();
        batch.disableBlending();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        for (Icon tapIcon : gameWorld.getObjectsPool()) {
            batch.draw(tapIcon.getSprite(), tapIcon.getX(), tapIcon.getY());
        }
        batch.end();
    }

    private void renderGameOverState() {
        batch.begin();
        batch.disableBlending();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();
        batch.draw(gameOver,
                Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
        batch.end();
        if (Gdx.input.isTouched()) { // todo fix that
            gameWorld.changeWorldState(GameWorld.States.GAME_EXIT);
        }
    }

    private void renderGameExitState() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(batch));
    }

    public void dispose() {
        //renderer.dispose();
    }

    private BitmapFont font;

    private final SpriteBatch batch;
    private Sprite gameBackground;
    private Sprite gameOver;

    private final GameWorld gameWorld;
    private final Box2DDebugRenderer renderer; // todo change to release renderer
}
