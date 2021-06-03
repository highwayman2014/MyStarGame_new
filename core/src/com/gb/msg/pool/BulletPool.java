package com.gb.msg.pool;

import com.gb.msg.base.SpritesPool;
import com.gb.msg.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
