package com.taptap.game.model.music.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.taptap.game.model.resource.manager.DResourceManager;
import com.taptap.game.view.screens.mainmenu_screen.MainMenuScreen;
import com.taptap.game.view.screens.game_screen.GameScreen;
import com.taptap.game.view.screens.help_screen.HelpScreen;
import com.taptap.game.view.screens.records_screen.RecordScreen;

public class MusicManager {
    static {
        //.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mainGameMusic = ResourceManager.getInstance().get(ResourceManager.mainGameMusic);
        mainGameMusic = (Music) DResourceManager.
                getInstance().
                get("music/Black Vortex.mp3");
        notGameLoopMusic = (Music) DResourceManager.
                getInstance().
                get("music/The Path of the Goblin King.mp3");
        musicEnableFlag = true;
    }

    public static void play(Screen screen){
        if (musicEnableFlag){
            mainGameMusic.setLooping(true);
            notGameLoopMusic.setLooping(true);
            if ((screen instanceof MainMenuScreen ||
                    screen instanceof HelpScreen ||
                    screen instanceof RecordScreen) &&
                    !notGameLoopMusic.isPlaying()){
                mainGameMusic.stop();
                notGameLoopMusic.play();
            }
            if (screen instanceof GameScreen){
                notGameLoopMusic.stop();
                mainGameMusic.play();
            }
        }
    }

    public static void pause(Screen screen){
        if (musicEnableFlag){
            if (screen instanceof MainMenuScreen ||
                    screen instanceof HelpScreen ||
                    screen instanceof RecordScreen){
                notGameLoopMusic.pause();
            }
            if (screen instanceof GameScreen){
                mainGameMusic.pause();
            }
        }
    }

    public static void onOffMusic(){
        Screen screen = ((Game) Gdx.app.getApplicationListener()).getScreen();
        musicEnableFlag = !musicEnableFlag;
        if (musicEnableFlag){
            musicOn(screen);
        } else {
            musicOff(screen);
        }
    }

    private static void musicOff(Screen screen){
        if (screen instanceof MainMenuScreen ||
                screen instanceof HelpScreen ||
                screen instanceof RecordScreen){
            notGameLoopMusic.pause();
        }
        if (screen instanceof GameScreen){
            mainGameMusic.pause();
        }
    }

    private static void musicOn(Screen screen){
        if ((screen instanceof MainMenuScreen ||
                screen instanceof HelpScreen ||
                screen instanceof RecordScreen)) {
            notGameLoopMusic.play();
        }
        if (screen instanceof GameScreen) {
            mainGameMusic.play();
        }
    }

    public static boolean isMusicEnable(){
        return musicEnableFlag;
    }
    public static void onOffSound(){
        soundEnableFlag = !soundEnableFlag;
    }

    private static boolean soundEnableFlag;
    private static boolean musicEnableFlag;
    private static Music notGameLoopMusic;
    private static Music mainGameMusic;
}
