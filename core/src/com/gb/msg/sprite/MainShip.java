package com.gb.msg.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gb.msg.base.Sprite;
import com.gb.msg.math.Rect;
import com.gb.msg.pool.BulletPool;
import com.gb.msg.sounds.BulletSound;

public class MainShip extends Sprite {

    public static final float HEIGHT = 0.15f;
    public static final float PADDING = 0.03f;
    private static final int INVALID_POINTER = -1;
    private static final float TIME_TO_SHUT = 0.2f;

    private final Vector2 v0 = new Vector2(0.5f, 0);
    private final Vector2 v = new Vector2();

    private boolean pressedLeft;
    private boolean pressedRight;
    private boolean autoShuttingOn;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Vector2 bulletPos;
    private BulletSound bulletSound;
    private float autoShuttingTimer;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, BulletSound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletPos = new Vector2();
        this.bulletSound = bulletSound;
        this.autoShuttingOn = false;
        this.autoShuttingTimer = 0f;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);

        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        autoShut(delta);
    }

    private void autoShut(float delta) {
        if (autoShuttingOn) {
            if (autoShuttingTimer >= TIME_TO_SHUT) {
                shut();
                autoShuttingTimer = 0f;
            } else {
                autoShuttingTimer += delta;
            }

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
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shut();
                break;
            case Input.Keys.DOWN:
                changeAutoShuttingMode();
                break;
        }
        return false;
    }

    private void changeAutoShuttingMode() {
        if (autoShuttingOn) {
            autoShuttingOn = false;
        } else {
            autoShuttingOn = true;
            autoShuttingTimer = TIME_TO_SHUT;
        }
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if(pressedRight){
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft){
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x){
            if(leftPointer != INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if(rightPointer != INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if(rightPointer != INVALID_POINTER){
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER){
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    private void moveRight(){
        v.set(v0);
    }

    private void moveLeft(){
        v.set(v0).rotateDeg(180);
    }

    private void stop(){
        v.setZero();
    }

    private void shut(){
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, 1, 0.01f);
        bulletSound.play(0.05f);
    }
}
