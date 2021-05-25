package com.gb.msg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gb.msg.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    Texture img;
    Vector2 pos;
    Vector2 v;
    Vector2 targetPos;
    Vector2 direction;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        pos = new Vector2();
        targetPos = pos.cpy();
        v = new Vector2(2, 1);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(distanceToTarget() != 0){
            changeDirection();
        }
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    /*
    Определим расстояние между целевой точкой и точкой положения картинки
    Получим вектор расстояния, вычитая вектора точек и определим его длину
     */
    private float distanceToTarget() {
        Vector2 distance = pos.cpy().sub(targetPos);
        return distance.len();
    }

    /*
    Получим вектор направления и (одновременно) скорости картинки
    и прибавим его к текущему положению
     */
    private void changeDirection() {
        direction = targetPos.cpy().sub(pos);
        direction.nor();
        pos.add(direction);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        targetPos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
