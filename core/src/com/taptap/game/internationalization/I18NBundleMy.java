package com.taptap.game.internationalization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public final class I18NBundleMy {
    static {
        myBundle = I18NBundle.createBundle(
                Gdx.files.internal("i18n/MyBundle"),
                new Locale("ru", "RU", "VAR1")
        );
    }

    public static String getString(String key) {
        return myBundle.get(key);
    }

    public static String getChainString(String key, String ...args) {
        return myBundle.format(key, args);
    }

    private static I18NBundle myBundle;
}
