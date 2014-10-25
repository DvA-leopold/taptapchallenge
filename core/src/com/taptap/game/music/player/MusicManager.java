package com.taptap.game.music.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.taptap.game.screens.realisation.MainMenuScreen;
import com.taptap.game.screens.realisation.game.GameScreen;
import com.taptap.game.screens.realisation.help.HelpScreen;

public class MusicManager {
    public static void init(){
        mainGameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Trance sample.mp3"));
        notGameLoopMusic = Gdx.audio.newMusic(Gdx.files.internal("music/The Path of the Goblin King.mp3"));
        mainGameMusic.setLooping(true);
        notGameLoopMusic.setLooping(true);
        flag = false;
    }

    public static void play(Screen screen){
        if ((screen instanceof MainMenuScreen ||
                screen instanceof HelpScreen) &&
                !notGameLoopMusic.isPlaying()){
            mainGameMusic.stop();
            notGameLoopMusic.play();
            flag=true;
            //System.out.println(flag);
        }
        if (screen instanceof GameScreen){
            notGameLoopMusic.stop();
            mainGameMusic.play();
            flag = false;
            //System.out.println(flag);
        }
        //System.out.println(flag);
    }

    public void setLooping(boolean flag){
    //    if (screen instanceof MainMenuScreen ||
    //            screen instanceof HelpScreen)
    }

    public void dispose(){
//        notGameLoopMusic.dispose();
    }

    private static boolean flag;

    private static Music notGameLoopMusic;
    private static Music mainGameMusic;
}
