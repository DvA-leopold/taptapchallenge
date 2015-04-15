package com.taptap.game.model.music.player;

import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundManager {
    public SoundManager() {
        soundEnable = true;
    }

    public void initSoundForLevel() {

    }

    public void playSound(String soundKey) {
        if(soundEnable) {
            soundMap.get(soundKey).play();
        }
    }

    public static void onOfSound() {
        soundEnable = !soundEnable;
    }

    private HashMap<String, Sound> soundMap;
    private static boolean soundEnable;
}
