package com.taptap.game.resource.manager;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ResourceManager {
    private ResourceManager() {
        storage = new AssetManager();
    }

    public static AssetManager getInstance(){
        return SingletonHolder.SINGLETON_INSTANCE.storage;
    }

    public static void queueLoading(){
        recordScreenLoad();
        menuScreenLoad();
        gameScreenLoad();
        helpScreenLoad();
        musicLoad();
    }

    public static void updateLoadingQueue(){
        while (getInstance().getProgress()<1){
            ResourceManager.getInstance().update();
        }
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
    public static final AssetDescriptor<TextureAtlas> atlasMainMenu =
            new AssetDescriptor<TextureAtlas>("skins/main_menu/buttons/buttons.pack", TextureAtlas.class);

    private static void menuScreenLoad() {
        getInstance().load(menuBackground);
        getInstance().load(atlasMainMenu);
    }

    //RecordScreen
    public static final AssetDescriptor<TextureAtlas> atlasRecordMenu =
            new AssetDescriptor<TextureAtlas>("skins/main_menu/buttons/buttons.pack", TextureAtlas.class);
    public static final AssetDescriptor<Texture> recordBackground =
            new AssetDescriptor<Texture>("skins/main_menu/background/bg_desert.png", Texture.class);

    private static void recordScreenLoad(){
        getInstance().load(atlasRecordMenu);
        getInstance().load(recordBackground);
    }

    // HelpScreen
    public static final AssetDescriptor<Texture> helpBackground =
            new AssetDescriptor<Texture>("skins/help_menu/bg_grasslands.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> atlasHelpMenu =
            new AssetDescriptor<TextureAtlas>("skins/help_menu/buttons/helpButton.pack", TextureAtlas.class);

    private static void helpScreenLoad(){
        getInstance().load(helpBackground);
        getInstance().load(atlasHelpMenu);
    }

    // GameScreen
    public static final AssetDescriptor<Texture> gameBackground =
            new AssetDescriptor<Texture>("skins/game_menu/game_bg.png", Texture.class);
    public static final AssetDescriptor<Texture> gameOver =
            new AssetDescriptor<Texture>("skins/game_menu/game_over.png", Texture.class);
    public static final AssetDescriptor<TextureAtlas> buttonAtlas =
            new AssetDescriptor<TextureAtlas>("skins/game_menu/popUpButtons.pack", TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> atlasOptionMenu =
            new AssetDescriptor<TextureAtlas>("skins/game_menu/popUpButtons.pack", TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> atlasPopupMenu =
            new AssetDescriptor<TextureAtlas>("skins/main_menu/buttons/buttons.pack", TextureAtlas.class);
    public static final AssetDescriptor<BitmapFont> fonts =
            new AssetDescriptor<BitmapFont>("fonts/whiteFont.fnt", BitmapFont.class);

    public static final String TextureAtlasNumber = "skins/game_menu/coins_and_numb/coins_and_hud.pack";

    private static void gameScreenLoad(){
        getInstance().load(gameBackground);
        getInstance().load(gameOver);
        getInstance().load(TextureAtlasNumber, TextureAtlas.class);
        getInstance().load(buttonAtlas);
        getInstance().load(atlasOptionMenu);
        getInstance().load(atlasPopupMenu);
        getInstance().load(fonts);
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
