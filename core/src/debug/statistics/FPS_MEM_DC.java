package debug.statistics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class FPS_MEM_DC {
    static {
        startTime = TimeUtils.nanoTime();
        font = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"), false);
        logBatch = new SpriteBatch();
        drawCalls = 1;
    }

    public static void fpsLog(){
        logBatch.begin();
        font.draw(logBatch,
                "fps: "+ Gdx.graphics.getFramesPerSecond() +
                " dc: " + drawCalls,
                 Gdx.graphics.getWidth()-230, font.getLineHeight());
        logBatch.end();
        //Gdx.app.log("FPSLogger", "fps: " + Gdx.graphics.getFramesPerSecond());
        startTime = TimeUtils.nanoTime();
        drawCalls=1;
    }

    public static int drawCalls;
    private static BitmapFont font;
    private static SpriteBatch logBatch;

    private static double startTime;
}
