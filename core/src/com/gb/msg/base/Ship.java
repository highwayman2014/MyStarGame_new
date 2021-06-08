package com.gb.msg.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.gb.msg.math.Rect;
import com.gb.msg.pool.BulletPool;
import com.gb.msg.sounds.LaserSound;
import com.gb.msg.sprite.Bullet;

public class Ship extends Sprite{

    protected Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected Sound bulletSound;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected float autoShuttingTimer;
    protected boolean autoShuttingOn;
    protected float timeToShut;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        autoShuttingOn = true;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        autoShut(delta);
    }

    public void autoShut(float delta) {
        if (autoShuttingOn) {
            if (autoShuttingTimer >= timeToShut) {
                shut();
                autoShuttingTimer = 0f;
            } else {
                autoShuttingTimer += delta;
            }

        }
    }

    public void shut(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play(0.05f);
    }


}
