package com.taptap.game.model.resource.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.apache.commons.io.FilenameUtils;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class DResourceManager {
    private DResourceManager() {
        assetManager = new AssetManager();
        mimeFileTypes = new Hashtable<>(10);

        mimeFileTypes.put("png", Texture.class);
        mimeFileTypes.put("jpeg", Texture.class);
        mimeFileTypes.put("bmp", Texture.class);

        mimeFileTypes.put("pack", TextureAtlas.class);
        mimeFileTypes.put("atlas", TextureAtlas.class);

        mimeFileTypes.put("mp3", Music.class);

        mimeFileTypes.put("fnt", BitmapFont.class);

        mimeFileTypes.put("json", Skin.class);
    }

    public static DResourceManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * load all files in section folder and subfolder of this section
     * @param section path to the folder
     * @param sync if this is true than we will wait till all files are load
     */
    public void loadSection(String section, boolean sync) {
        FileHandle sectionRoot = Gdx.files.internal(section);
        FileHandle[] allFiles = new FileHandle[0];
        try {
            allFiles = getFiles(sectionRoot);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (FileHandle allFile : allFiles) {
            String fileName = allFile.file().getAbsolutePath();
            String extension = FilenameUtils.getExtension(fileName);
            if (mimeFileTypes.contains(extension)) {
                getInstance().assetManager.load(fileName, mimeFileTypes.get(extension));
            }
        }
        if (sync) {
            assetManager.finishLoading();
        }
    }

    /**
     * unload chosen files in a folder and files in a sub-folders
     * @param section path to the folder
     */
    public void unloadSection(String section) {
        FileHandle sectionRoot = Gdx.files.internal(section);
        FileHandle[] allFiles = new FileHandle[0];
        try {
            allFiles = getFiles(sectionRoot);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (FileHandle allFile : allFiles) {
            getInstance().assetManager.unload(allFile.file().getAbsolutePath());
        }
    }

    public float getProgress() {
        assetManager.update();
        return assetManager.getProgress();
    }

    private FileHandle[] getFiles(FileHandle sectionForLoading) throws FileNotFoundException {
        if (!sectionForLoading.isDirectory()){
            throw new FileNotFoundException("this is not a directory");
        }
        Queue<FileHandle> fileHandles = new LinkedList<>();
        LinkedList<FileHandle> filesList = new LinkedList<>();

        fileHandles.add(sectionForLoading);
        while (!fileHandles.isEmpty()) {
            FileHandle[] filesInFolder = fileHandles.poll().list();
            for (FileHandle aFilesInFolder : filesInFolder) {
                if (aFilesInFolder.isDirectory()) {
                    fileHandles.add(aFilesInFolder);
                } else {
                    filesList.add(aFilesInFolder);
                }
            }
        }

        return (FileHandle[]) filesList.toArray();
    }

    public static void dispose() {
        getInstance().assetManager.dispose();
    }

    private static class SingletonHolder {
        private static final DResourceManager instance = new DResourceManager();
    }

    private final Hashtable<String, Class> mimeFileTypes;
    private final AssetManager assetManager;
}
