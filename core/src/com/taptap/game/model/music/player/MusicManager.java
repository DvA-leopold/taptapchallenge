package com.taptap.game.model.music.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.taptap.game.model.resource.manager.DResourceManager;

import java.util.HashMap;


/**
 * this class work with all music in the game,
 * all its method choose the music to playMusic inside of them
 * depend on screen, there they was called,
 * <code>OnCompletionListener</code> implement if needed.
 */
//TODO: move all duplicated checks into separate function
public class MusicManager {
    public MusicManager() {
        musicTable = new HashMap<>(10);
        musicEnableFlag = true;
    }

    /**
     * this method must be called before using this class first time
     */
    public void initialize() {
        mainGameMusic = (Music) DResourceManager.
                getInstance().
                get("music/Black Vortex.mp3");
        additionMusic = (Music) DResourceManager.
                getInstance().
                get("music/The Path of the Goblin King.mp3");
    }

    /**
     * every screen should register the <code>MusicType</code> which will playMusic
     *
     * @param sClass    class of the current screen
     * @param musicType instance of <code>MusicType</code> enum class
     */
    public void registerMusic(Class<? extends Screen> sClass, MusicTypes musicType) {
        musicTable.put(sClass, musicType);
    }

    public void onOffMusic() {
        musicEnableFlag = !musicEnableFlag;
        if (musicEnableFlag) {
            play();
        } else {
            pauseMusic();
        }
    }

    //TODO оптимизировать, убрать промежуточную остановку музыки при переключении экрана
    public void playMusic() {
        if (musicEnableFlag) {
            Class<? extends Screen> sClass = ((Game) Gdx.app.getApplicationListener()).getScreen().getClass();
            mainGameMusic.setLooping(true);
            additionMusic.setLooping(true);
            if (musicTable.get(sClass) == MusicTypes.MAIN_MUSIC) {
                additionMusic.stop();
                mainGameMusic.play();
            } else if (musicTable.get(sClass) == MusicTypes.ADD_MUSIC) {
                mainGameMusic.stop();
                additionMusic.play();
            }
        }
    }

    public void pauseMusic() {
        Class<? extends Screen> sClass = ((Game) Gdx.app.getApplicationListener()).getScreen().getClass();
        if (musicTable.get(sClass) == MusicTypes.MAIN_MUSIC) {
            mainGameMusic.pause();
        } else if (musicTable.get(sClass) == MusicTypes.ADD_MUSIC) {
            additionMusic.pause();
        }
    }

    public static boolean isMusicEnable() {
        return musicEnableFlag;
    }

    public void dispose() {
        DResourceManager.getInstance().unloadSection("music");
        additionMusic.dispose();
        mainGameMusic.dispose();
    }

    private void play() {
        Class<? extends Screen> sClass = ((Game) Gdx.app.getApplicationListener()).getScreen().getClass();
        if (musicTable.get(sClass) == MusicTypes.MAIN_MUSIC) {
            mainGameMusic.play();
        } else if (musicTable.get(sClass) == MusicTypes.ADD_MUSIC) {
            additionMusic.play();
        }
    }

    public enum MusicTypes {
        MAIN_MUSIC,
        ADD_MUSIC
    }


    private HashMap<Class, MusicTypes> musicTable;
    private Music additionMusic = null, mainGameMusic = null;

    private static boolean musicEnableFlag;
}
