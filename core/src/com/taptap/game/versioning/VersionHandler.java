package com.taptap.game.versioning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class VersionHandler {
    static {
        FileHandle versionFile = Gdx.files.internal("version/version.properties");
        if (versionFile.exists()) {
            int equalIndex = versionFile.readString().indexOf("\n");
            String stringWithoutInitData = versionFile.readString().substring(equalIndex);
            version = stringWithoutInitData.
                    replaceAll("major=", "").
                    replaceAll("minor=", ".").
                    replaceAll("patch=", ".");

        } else {
            version = "";
        }
    }

    public static void render(final SpriteBatch batch) {
            batch.begin();
            fontStandart.draw(batch, version, Gdx.graphics.getWidth() - 100, 30);
            batch.end();
    }

    private static final String version;
    private static BitmapFont fontStandart = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"));
}
