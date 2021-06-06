package com.gb.msg.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class BulletSound {

    private Sound bulletSound;

    public BulletSound() {
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/android_assets_sounds_bullet.wav"));
    }

    public void play(float volume) {
        bulletSound.play(volume);
    }

    public void dispose(){
        bulletSound.dispose();
    }
}
