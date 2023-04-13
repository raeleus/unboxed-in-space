package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.timeStep;
import static com.ray3k.unboxedinspace.GameScreen.unBox;

public class ShootSpiralBehaviour extends BehaviourAdapter {
    protected float delay = .05f;
    protected float bulletVelocity = 10f;
    protected float damage = 25f;
    private float timer;
    private float angle;
    private float angleSpeed = 200;

    public ShootSpiralBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {
        timer = delay;
    }

    @Override
    public void update(float delta) {
        angle += angleSpeed * delta;

        Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        TeamBehaviour teamBehaviour = getGameObject().getBehaviour(TeamBehaviour.class);
        timer -= delta;
        if (timer < 0) {
            timer = delay;

            GameObject go = new GameObject(unBox);
            Vector2 velocity = new Vector2(bulletVelocity, 0);
            velocity.rotateDeg(MathUtils.radDeg * body.getTransform().getRotation() + angle);
            new ProjectileBehaviour(go, velocity, damage);

            Vector2 position = new Vector2(new Vector2(body.getPosition()));
            position.add(body.getLinearVelocity().x * timeStep, body.getLinearVelocity().y * timeStep);
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(position);
            new Box2dBehaviour(bodyDef, go);

            new TeamBehaviour(go, teamBehaviour.team);
        }
    }
}
