package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.box2d.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.timeStep;

public class MovementKeyboardBehaviour extends BehaviourAdapter {
    private static final float ACCELERATION = 50f;
    private static final float MAX_SPEED = 7f;
    private static final float ROTATION_ANGLE = 15f;
    private final Vector2 velocity = new Vector2();
    private final Vector2 movementVector = new Vector2();

    public MovementKeyboardBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void fixedUpdate() {
        if (!movementVector.isZero()) {
            velocity.add(movementVector.x * ACCELERATION * timeStep, movementVector.y * ACCELERATION * timeStep);
            velocity.setLength(MathUtils.clamp(velocity.len(), 0, MAX_SPEED));
        } else velocity.setLength(MathUtils.clamp(velocity.len() - ACCELERATION * timeStep, 0, MAX_SPEED));

        Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        body.setLinearVelocity(velocity);
        body.setTransform(body.getTransform().getPosition().x, body.getTransform().getPosition().y, MathUtils.degRad * (-velocity.x / MAX_SPEED * ROTATION_ANGLE + 90));
    }

    @Override
    public void update(float delta) {
        movementVector.set(0, 0);

        if (Gdx.input.isKeyPressed(Keys.UP)) movementVector.y += 1;
        if (Gdx.input.isKeyPressed(Keys.DOWN)) movementVector.y -= 1;
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) movementVector.x += 1;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) movementVector.x -= 1;

        movementVector.setLength(MathUtils.clamp(movementVector.len(), 0, 1));
    }
}
