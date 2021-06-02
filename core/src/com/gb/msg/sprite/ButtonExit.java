package com.gb.msg.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.msg.base.ScaledButton;
import com.gb.msg.math.Rect;

public class ButtonExit extends ScaledButton {

    public static final float HEIGHT = 0.2f;
    public static final float PADDING = 0.03f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        setRight(worldBounds.getRight() - PADDING);
    }

    @Override
    protected void action() {
        Gdx.app.exit();
    }
}
