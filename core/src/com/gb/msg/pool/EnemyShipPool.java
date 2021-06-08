package com.gb.msg.pool;

import com.badlogic.gdx.audio.Sound;

import com.gb.msg.base.SpritesPool;
import com.gb.msg.math.Rect;
import com.gb.msg.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private final Rect worldBounds;
    private final BulletPool bulletPool;
    private final Sound bulletSound;

    public EnemyShipPool(Rect worldBounds, BulletPool bulletPool, Sound bulletSound) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(worldBounds, bulletPool, bulletSound);
    }
}
