package com.gb.msg.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.msg.base.ScaledButton;
import com.gb.msg.math.Rect;
import com.gb.msg.screen.GameScreen;

public class NewGame extends ScaledButton {

    private static final float HEIGHT = 0.05f;

    private final GameScreen gameScreen;

    public NewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(worldBounds.pos.y);
    }

    @Override
    protected void action() {
        gameScreen.startNewGame();
    }
}
