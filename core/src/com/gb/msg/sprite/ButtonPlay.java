package com.gb.msg.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.msg.base.ScaledButton;
import com.gb.msg.math.Rect;
import com.gb.msg.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    public static final float HEIGHT = 0.26f;
    public static final float PADDING = 0.03f;

    private final Game GAME;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.GAME = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        setLeft(worldBounds.getLeft() + PADDING);
    }

    @Override
    protected void action() {
        GAME.setScreen(new GameScreen());
    }
}
