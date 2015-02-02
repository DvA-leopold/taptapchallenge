package debug.statistics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.taptap.game.TapTap;

public class FPS_MEM_DC {
    static {
        font = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"), false);
        logBatch = new SpriteBatch(1000, TapTap.createDefaultShader());
    }

    public static void fpsLog(){
        logBatch.begin();
        font.draw(logBatch,
                "fps: "+ Gdx.graphics.getFramesPerSecond(), // fps = 1/delta(delta из функции рендера в )
                 Gdx.graphics.getWidth()-130, font.getLineHeight());
        logBatch.end();
    }

    private static BitmapFont font;
    private static SpriteBatch logBatch;
}
