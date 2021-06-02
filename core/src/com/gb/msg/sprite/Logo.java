package com.gb.msg.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.msg.base.Sprite;
import com.gb.msg.math.Rect;

public class Logo extends Sprite {

    private Vector2 touch;
    private Vector2 v;
    private Vector2 tmp;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        touch = new Vector2();
        v = new Vector2();
        tmp = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        tmp.set(touch);
        if (tmp.sub(pos).len() < 0.01f){
            pos.set(touch);
        } else {
            pos.add(v);
        }

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.15f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.cpy().sub(pos)).setLength(0.01f);
        return false;
    }
}
