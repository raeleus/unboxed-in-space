package com.ray3k.unboxedinspace.gamebehaviours.shoot;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ray3k.unboxedinspace.Core;
import com.ray3k.unboxedinspace.gamebehaviours.ProjectileBehaviour;
import com.ray3k.unboxedinspace.gamebehaviours.TeamBehaviour;
import com.ray3k.unboxedinspace.gamebehaviours.TeamBehaviour.Team;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.Core.*;
import static com.ray3k.unboxedinspace.GameScreen.*;

public class ShootSingleBehaviour extends BehaviourAdapter {
    protected float delay = .1f;
    protected float bulletVelocity = 10f;
    protected float damage = 25f;
    private float timer;

    public ShootSingleBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {
        timer = delay;
    }

    @Override
    public void update(float delta) {
        Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        TeamBehaviour teamBehaviour = getGameObject().getBehaviour(TeamBehaviour.class);
        timer -= delta;
        if (timer < 0) {
            timer = delay;

            if (teamBehaviour.team == Team.PLAYER) soundLaser.play(.1f);

            GameObject go = new GameObject(unBox);
            Vector2 velocity = new Vector2(bulletVelocity, 0);
            velocity.rotateDeg(MathUtils.radDeg * body.getTransform().getRotation());
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