package com.taptap.game.music.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.taptap.game.screens.realisation.MainMenuScreen;
import com.taptap.game.screens.realisation.game.GameScreen;
import com.taptap.game.screens.realisation.help.HelpScreen;

public class MusicManager {
    static {
        mainGameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Black Vortex.mp3"));
        notGameLoopMusic = Gdx.audio.newMusic(Gdx.files.internal("music/The Path of the Goblin King.mp3"));
        soundEnableFlag = true;
        System.out.println("mew");
    }

    public static void play(Screen screen){
        if (soundEnableFlag){
            mainGameMusic.setLooping(true);
            notGameLoopMusic.setLooping(true);
            if ((screen instanceof MainMenuScreen ||
                    screen instanceof HelpScreen) &&
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
        if (soundEnableFlag){
            if (screen instanceof MainMenuScreen ||
                    screen instanceof HelpScreen){
                notGameLoopMusic.pause();
            }
            if (screen instanceof GameScreen){
                mainGameMusic.pause();
            }
        }
    }
    public static void onOffSound(){
        soundEnableFlag = !soundEnableFlag;
        if (soundEnableFlag){
            notGameLoopMusic.play();
        } else {
            notGameLoopMusic.stop();
        }
    }

    private static boolean soundEnableFlag;
    private static Music notGameLoopMusic;
    private static Music mainGameMusic;
}
