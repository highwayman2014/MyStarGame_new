package com.gb.msg.pool;

import com.gb.msg.base.SpritesPool;
import com.gb.msg.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }
}
