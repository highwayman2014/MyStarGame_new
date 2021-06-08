package com.gb.msg.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class LaserSound {

    private Sound laserSound;

    public LaserSound() {
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/android_assets_sounds_laser.wav"));
    }

    public void play(float volume) {
        laserSound.play(volume);
    }

    public void dispose(){
        laserSound.dispose();
    }
}
