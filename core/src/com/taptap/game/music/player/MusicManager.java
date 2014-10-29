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
    }

    public static void play(Screen screen){
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

    public static void pause(Screen screen){
        if (screen instanceof MainMenuScreen ||
                screen instanceof HelpScreen){
            notGameLoopMusic.pause();
        }
        if (screen instanceof GameScreen){
            notGameLoopMusic.pause();
        }
    }

    public static void dispose(){
        notGameLoopMusic.dispose();
        mainGameMusic.dispose();
    }

    private static Music notGameLoopMusic;
    private static Music mainGameMusic;
}
