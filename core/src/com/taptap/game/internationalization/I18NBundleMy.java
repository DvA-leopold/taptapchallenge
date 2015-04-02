package com.taptap.game.internationalization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public final class I18NBundleMy {
    static {
        initLangCode();
    }

    public static String getString(String key) {
        return myBundle.get(key);
    }

    public static String getChainString(String key, String ...args) {
        return myBundle.format(key, args);
    }

    public static String getLangCodes() {
        return langCodes[0];
    }

    public static void initLangCode(String[] langCode) {
        langCodes = langCode;
        myBundle = I18NBundle.createBundle(
                Gdx.files.internal("i18n/MyBundle"),
                new Locale(langCode[0], langCode[1], langCode[2])
        );
    }

    private static void initLangCode() {
        langCodes = new String[3];
        langCodes[0] = "ru";
        langCodes[1] = "RU";
        langCodes[2] = "VAR1";
        myBundle = I18NBundle.createBundle(
                Gdx.files.internal("i18n/MyBundle"),
                new Locale(langCodes[0], langCodes[1], langCodes[2])
        );
    }

    static private String[] langCodes;
    static private I18NBundle myBundle;
}
