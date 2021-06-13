package com.gb.msg.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gb.msg.base.ScaledButton;
import com.gb.msg.math.Rect;
import com.gb.msg.screen.GameScreen;

public class NewGame extends ScaledButton {

    private static final float HEIGHT = 0.05f;
    private static final float PULSE_TIME = 0.5f;

    private static float pulseTimer = 0f;
    private static boolean pulseOn = false;

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
    public void update(float delta) {
        pulseTimer += delta;
        if (pulseTimer >= PULSE_TIME && !pressed) {
            pulseTimer = 0f;
            if (pulseOn) {
                pulseOn = false;
                scale = 0.9f;
            } else {
                pulseOn = true;
                scale = 1f;
            }
        }
    }

    @Override
    protected void action() {
        gameScreen.startNewGame();
    }
}
