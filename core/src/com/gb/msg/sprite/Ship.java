package com.gb.msg.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.msg.base.Sprite;
import com.gb.msg.math.Rect;

public class Ship extends Sprite {

    public static final float HEIGHT = 0.15f;
    public static final float PADDING = 0.03f;

    private Vector2 v;
    private Rect worldBounds;

    public Ship(TextureRegion region) {
        super(region);
        v = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if((getRight() <= worldBounds.getRight() || v.x < 0)
                && (getLeft() >= worldBounds.getLeft() || v.x > 0)) {
            pos.mulAdd(v, delta);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case 22:
                v.set(0.5f, 0f);
                break;
            case 21:
                v.set(-0.5f, 0f);
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        if(keycode == 22 || keycode == 21){
            v.set(0f, 0f);
        }
        return false;
    }
}
