package com.taptap.game.resource.manager;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ResourceManager {
    private ResourceManager(){
        storage = new AssetManager();
    }

    public static ResourceManager getInstance(){
        return SingletonHolder.SINGLETON_INSTANCE;
    }

    public void queueLoading(){
        storage.load(gameBackground);
        storage.load(gameOver);
        storage.load(TextureAtlasNumber, TextureAtlas.class);

    }

    public void updateLoadingQueue(){
        while (!storage.update()){
            System.out.println(storage.getProgress()*100 + "%");
        }
    }

    public void dispose(){
        storage.dispose();
    }

    private static class SingletonHolder {
        private static final ResourceManager SINGLETON_INSTANCE = new ResourceManager();
    }

    // GameScreen
    public static AssetDescriptor<Texture> gameBackground = new AssetDescriptor<Texture>("skins/game_menu/game_bg.png", Texture.class); // todo  возмжно статик не нужен
    public static AssetDescriptor<Texture> gameOver = new AssetDescriptor<Texture>("skins/game_menu/game_over.png", Texture.class);
    public static String TextureAtlasNumber = "skins/game_menu/coins_and_numb/coins_and_hud.pack";

    public AssetManager storage;
}
