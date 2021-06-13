package com.gb.msg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import com.gb.msg.base.BaseScreen;
import com.gb.msg.math.Rect;
import com.gb.msg.pool.BulletPool;
import com.gb.msg.pool.EnemyShipPool;
import com.gb.msg.pool.ExplosionPool;
import com.gb.msg.sprite.Background;
import com.gb.msg.sprite.Bullet;
import com.gb.msg.sprite.EnemyShip;
import com.gb.msg.sprite.GameOver;
import com.gb.msg.sprite.MainShip;
import com.gb.msg.sprite.NewGame;
import com.gb.msg.sprite.Star;
import com.gb.msg.utils.EnemyEmitter;

import java.util.List;

public class GameScreen extends BaseScreen {

    private static int STARS_COUNT = 64;

    private enum State {PLAYING, GAME_OVER}

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;
    private GameOver gameOver;
    private NewGame newGame;

    private BulletPool bulletPool;
    private MainShip mainShip;

    private Music backgroundMusic;
    private Sound bulletSound;
    private Sound laserSound;
    private Sound explosionSound;

    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;

    private State state;

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
        gameOver = new GameOver(atlas);
        newGame = new NewGame(atlas, this);
        bulletPool = new BulletPool();
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlas, explosionPool, bulletPool, laserSound);
        enemyShipPool = new EnemyShipPool(worldBounds, explosionPool, bulletPool, bulletSound);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, atlas);
        startMusic("sounds/music.mp3");
        state = State.PLAYING;
    }

    private void startMusic(String path){
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    private void checkCollisions() {
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroyWithExplosion();
                mainShip.damage(enemyShip.getDamage() * 2);
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == mainShip) {
                for (EnemyShip enemyShip : enemyShipList) {
                    if (enemyShip.isDestroyed()) {
                        continue;
                    }
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        bullet.destroy();
                    }
                }
            } else {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for(Star star : stars){
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
        explosionSound.dispose();
        backgroundMusic.dispose();
        bulletSound.dispose();
        laserSound.dispose();
    }

    public void update(float delta) {
        for(Star star : stars){
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);

        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        } else {
            newGame.update(delta);
        }
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyed();
        enemyShipPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    public void draw() {
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        background.draw(batch);
        for(Star star : stars){
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyShipPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        } else {
            newGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else {
            newGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    public void startNewGame() {
        if (state == State.PLAYING) {
            return;
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            bullet.destroy();
        }
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            enemyShip.destroy();
        }
        state = State.PLAYING;
        mainShip.newGame();
    }
}
