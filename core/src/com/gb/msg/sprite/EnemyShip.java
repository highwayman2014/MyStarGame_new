package com.gb.msg.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.msg.base.Sprite;
import com.gb.msg.math.Rect;
import com.gb.msg.utils.Regions;

public class EnemyShip extends Sprite {

    private Rect worldBounds;
    private Vector2 v;
    private float height;

    public EnemyShip() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    public void set(
            TextureRegion region,
            float posX,
            float speed,
            Rect worldBounds,
            float height){
        this.regions = Regions.split(region, 1, 2, 2);
        this.height = height;
        setHeightProportion(height);
        this.pos.set(posX, worldBounds.getHalfHeight());
        this.v.set(0f, speed);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)){
            destroy();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(this.height);
    }

}
