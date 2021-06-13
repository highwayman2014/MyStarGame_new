package com.gb.msg.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.gb.msg.base.Ship;
import com.gb.msg.math.Rect;
import com.gb.msg.pool.BulletPool;
import com.gb.msg.pool.ExplosionPool;

public class EnemyShip extends Ship {

    public EnemyShip(Rect worldBounds, ExplosionPool explosionPool, BulletPool bulletPool, Sound bulletSound) {
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        v0 = new Vector2();
        v = new Vector2();
        this.bulletV = new Vector2();
        this.bulletPos = new Vector2();
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float timeToShut,
            float height,
            int hp){
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.damage = damage;
        this.timeToShut = timeToShut;
        setHeightProportion(height);
        this.hp = hp;
        v.set(0, -0.3f);
        autoShuttingOn = true;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        if (getTop() < worldBounds.getTop()) {
            v.set(v0);
        } else {
            autoShuttingTimer = timeToShut * 0.8f;
        }
        autoShut(delta);
        if (worldBounds.isOutside(this)) {
            destroy();
        }
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y
        );
    }


}
