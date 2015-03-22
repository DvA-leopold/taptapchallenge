package com.taptap.game.versioning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class VersionHandler {
    static {
        fontStandart = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt"));
        FileHandle versionFile = Gdx.files.internal("version/version.properties");
        if (versionFile.exists()) {
            int equalIndex = versionFile.readString().indexOf("\n");
            String stringWithoutInitData = versionFile.readString().substring(equalIndex);
            version = stringWithoutInitData.
                    replaceAll("major=", "v").
                    replaceAll("minor=", ".").
                    replaceAll("patch=", ".");

        } else {
            version = "";
        }
        setScale();
    }

    private static void setScale() {
        fontStandart.setScale(0.5f);
    }

    public static void render(final SpriteBatch batch) {
        batch.begin();
        fontStandart.draw(batch, version, Gdx.graphics.getWidth() - 50, 15);
        batch.end();
    }

    private static final String version;
    public static BitmapFont fontStandart;
}
