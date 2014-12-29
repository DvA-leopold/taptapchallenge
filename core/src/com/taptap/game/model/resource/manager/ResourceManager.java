package com.taptap.game.model.resource.manager;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager {
    private ResourceManager() {
        storage = new AssetManager();
    }

    public static AssetManager getInstance() {
        return SingletonHolder.SINGLETON_INSTANCE.storage;
    }

    public static void queueLoading() {
        recordScreenLoad();
        menuScreenLoad();
        gameScreenLoad();
        helpScreenLoad();
        musicLoad();
    }

    public static float updateLoadingQueue(){
        ResourceManager.getInstance().update();
        return getInstance().getProgress();
    }

    public void dispose(){
        storage.dispose();
    }

    private static class SingletonHolder {
        private static final ResourceManager SINGLETON_INSTANCE = new ResourceManager();
    }

    // MainMenuScreen
    public static final AssetDescriptor<Texture> menuBackground =
            new AssetDescriptor<Texture>("skins/main_menu/background/bg_desert.png", Texture.class);
    public static final AssetDescriptor<Skin> mainMenuSkin =
            new AssetDescriptor<Skin>("skins/main_menu/buttons/menuSkin.json", Skin.class);

    private static void menuScreenLoad() {
        getInstance().load(menuBackground);
        getInstance().load(mainMenuSkin);
    }

    //RecordScreen
    public static final AssetDescriptor<Skin> recordSkin =
            new AssetDescriptor<Skin>("skins/record_menu/buttons/recordScreen.json", Skin.class);
    public static final AssetDescriptor<Texture> recordBackground =
            new AssetDescriptor<Texture>("skins/main_menu/background/bg_desert.png", Texture.class);

    private static void recordScreenLoad(){
        getInstance().load(recordSkin);
        getInstance().load(recordBackground);
    }

    // HelpScreen
    public static final AssetDescriptor<Texture> helpBackground =
            new AssetDescriptor<Texture>("skins/help_menu/bg_grasslands.png", Texture.class);
    public static final AssetDescriptor<Skin> helpSkin =
            new AssetDescriptor<Skin>("skins/help_menu/buttons/helpSkin.json", Skin.class);
    //public static final AssetDescriptor<TextureAtlas> atlasHelpMenu =
    //        new AssetDescriptor<TextureAtlas>("skins/help_menu/buttons/helpButton.pack", TextureAtlas.class);

    private static void helpScreenLoad(){
        getInstance().load(helpBackground);
        getInstance().load(helpSkin);
        //getInstance().load(atlasHelpMenu);
    }

    // GameScreen
    public static final AssetDescriptor<Texture> gameBackground =
            new AssetDescriptor<Texture>("skins/game_menu/game_bg.png", Texture.class);
    public static final AssetDescriptor<Texture> gameOver =
            new AssetDescriptor<Texture>("skins/game_menu/game_over.png", Texture.class);
    public static final AssetDescriptor<Skin> popUpSkin =
            new AssetDescriptor<Skin>("skins/game_menu/buttons/pop_up/popUpSkin.json", Skin.class);
    public static final AssetDescriptor<Skin> optionSkin =
            new AssetDescriptor<Skin>("skins/game_menu/buttons/option_menu/optionIconSkin.json", Skin.class);
    public static final AssetDescriptor<BitmapFont> fonts =
            new AssetDescriptor<BitmapFont>("fonts/whiteFont.fnt", BitmapFont.class);
    public static final AssetDescriptor<Texture> iconRed =
            new AssetDescriptor<Texture>("skins/game_menu/tap_icons/hud_gem_red.png", Texture.class);
    public static final AssetDescriptor<Texture> iconBlue =
            new AssetDescriptor<Texture>("skins/game_menu/tap_icons/hud_gem_blue.png", Texture.class);
    public static final AssetDescriptor<Texture> iconYellow =
            new AssetDescriptor<Texture>("skins/game_menu/tap_icons/hud_gem_yellow.png", Texture.class);
    //public static final AssetDescriptor<Texture> zombiLAL =
    //        new AssetDescriptor<Texture>("skins/game_menu/tap_icons/zombi_lol.png", Texture.class);

    public static final String TextureAtlasNumber = "skins/game_menu/coins_and_numb/coins_and_hud.pack";

    private static void gameScreenLoad(){
        getInstance().load(gameBackground);
        getInstance().load(gameOver);
        getInstance().load(TextureAtlasNumber, TextureAtlas.class);
        //getInstance().load(zombiLAL);
        getInstance().load(optionSkin);
        getInstance().load(popUpSkin);
        getInstance().load(fonts);
        getInstance().load(iconRed);
        getInstance().load(iconBlue);
        getInstance().load(iconYellow);
    }

    // music and sounds
    public static final AssetDescriptor<Music> mainGameMusic =
            new AssetDescriptor<Music>("music/Black Vortex.mp3", Music.class);
    public static final AssetDescriptor<Music> notGameLoopMusic =
            new AssetDescriptor<Music>("music/The Path of the Goblin King.mp3", Music.class);

    private static void musicLoad(){
        getInstance().load(mainGameMusic);
        getInstance().load(notGameLoopMusic);
    }

    public final AssetManager storage;
}
