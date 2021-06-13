package com.gb.msg.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.gb.msg.math.Rect;
import com.gb.msg.pool.BulletPool;
import com.gb.msg.pool.ExplosionPool;
import com.gb.msg.sounds.LaserSound;
import com.gb.msg.sprite.Bullet;
import com.gb.msg.sprite.Explosion;

public class Ship extends Sprite{

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.05f;
    protected Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected ExplosionPool explosionPool;
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

    public float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

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
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
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

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public int getDamage() {
        return damage;
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.pos, getHeight());
    }
}
