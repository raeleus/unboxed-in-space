package com.ray3k.unboxedinspace.menubehaviors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.ray3k.unboxedinspace.Core;
import com.ray3k.unboxedinspace.MenuScreen;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class MenuStarBehaviour extends BehaviourAdapter {
    private static final float LIFE = 2f;
    private static final float MIN_START_SPEED = 30f;
    private static final float MAX_START_SPEED = 200f;
    private static final float ACCELERATION = 5f;
    private static final float SCALAR = .05f;
    private static final float SCALE_OFFSET = -10f;
    private float timer;
    private Sprite sprite;
    private Vector2 position;
    private Vector2 velocity;

    public MenuStarBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {
        timer = LIFE;
        sprite = new Sprite(Core.skin.getRegion("star"));
        position = new Vector2(MenuScreen.WORLD_WIDTH / 2, MenuScreen.WORLD_HEIGHT / 2);
        velocity = new Vector2(MathUtils.random(MIN_START_SPEED, MAX_START_SPEED), 0);
        velocity.rotateDeg(MathUtils.random(360));
    }

    @Override
    public void update(float delta) {
        velocity.scl(1 + ACCELERATION * delta);
        position.mulAdd(velocity, delta);
        sprite.setPosition(position.x, position.y);

        sprite.setScale(Math.max(velocity.len() * SCALAR + SCALE_OFFSET, 0), .5f);
        sprite.setRotation(velocity.angleDeg());

        timer -= delta;
        if (timer < 0) getGameObject().destroy();
    }

    @Override
    public void render(Batch batch) {
        sprite.draw(batch);
    }
}
