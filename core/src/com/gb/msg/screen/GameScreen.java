package com.gb.msg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.msg.base.BaseScreen;
import com.gb.msg.math.Rect;
import com.gb.msg.pool.BulletPool;
import com.gb.msg.pool.EnemyShipPool;
import com.gb.msg.sounds.BulletSound;
import com.gb.msg.sprite.Background;
import com.gb.msg.sprite.EnemyShip;
import com.gb.msg.sprite.MainShip;
import com.gb.msg.sprite.Star;

import java.util.Random;

public class GameScreen extends BaseScreen {

    private static int STARS_COUNT = 64;

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;

    private BulletPool bulletPool;
    private MainShip mainShip;

    private Music backgroundMusic;
    private BulletSound bulletSound;

    private EnemyShipPool enemyShipPool;
    private TextureRegion enemyRegion_0;

    private Rect worldBounds;
    private Random random;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        bulletSound = new BulletSound();
        mainShip = new MainShip(atlas, bulletPool, bulletSound);
        enemyRegion_0 = new TextureRegion(atlas.findRegion("enemy0"));
        enemyShipPool = new EnemyShipPool();
        startMusic("sounds/music.mp3");
        random = new Random();
    }

    private void startMusic(String path){
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for(Star star : stars){
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        backgroundMusic.dispose();
        bulletSound.dispose();
        enemyShipPool.dispose();
    }

    public void update(float delta) {
        for(Star star : stars){
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        if(random.nextFloat() <= 0.03f){
            createEnemy();
        }
    }

    private void createEnemy() {
        EnemyShip enemyShip = enemyShipPool.obtain();
        float enemySpeed = random.nextFloat() * -1;
        float posX = random.nextFloat() - 0.5f;
        enemyShip.set(enemyRegion_0, posX, enemySpeed, worldBounds, 0.15f);
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyed();
        enemyShipPool.freeAllDestroyed();
    }

    public void draw() {
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        background.draw(batch);
        for(Star star : stars){
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }
}
