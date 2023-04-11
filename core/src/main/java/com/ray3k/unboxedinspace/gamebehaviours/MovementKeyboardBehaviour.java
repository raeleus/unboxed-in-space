package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

public class MovementKeyboardBehaviour extends BehaviourAdapter {
    private static final float ACCELERATION = .05f;
    private static final float MAX_SPEED = 10f;
    private final Vector2 velocity = new Vector2();

    public MovementKeyboardBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void fixedUpdate() {
        getGameObject().getBehaviour(Box2dBehaviour.class).getBody().setLinearVelocity(velocity);
    }

    @Override
    public void update(float delta) {
        velocity.set(0, 0);

        if (Gdx.input.isKeyPressed(Keys.UP)) velocity.y += MAX_SPEED;
        if (Gdx.input.isKeyPressed(Keys.DOWN)) velocity.y -= MAX_SPEED;
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) velocity.x += MAX_SPEED;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) velocity.x -= MAX_SPEED;

        velocity.setLength(MathUtils.clamp(velocity.len(), 0, MAX_SPEED));
    }
}
