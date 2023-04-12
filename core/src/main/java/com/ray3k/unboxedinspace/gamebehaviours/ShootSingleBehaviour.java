package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ray3k.unboxedinspace.Core;
import com.ray3k.unboxedinspace.GameScreen;
import dev.lyze.gdxUnBox2d.BodyDefType;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateCircleFixtureBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.*;

public class ShootSingleBehaviour extends BehaviourAdapter {
    private static final float DELAY = .1f;
    private static final float bulletVelocity = 20f;
    private float timer;

    public ShootSingleBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {
        timer = DELAY;
    }

    @Override
    public void update(float delta) {
        GameObject go = getGameObject();
        Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        TeamBehaviour teamBehaviour = getGameObject().getBehaviour(TeamBehaviour.class);
        timer -= delta;
        if (timer < 0) {
            timer = DELAY;

            go = new GameObject(unBox);
            Vector2 position = new Vector2(new Vector2(body.getPosition()));
            position.add(body.getLinearVelocity().x * timeStep, body.getLinearVelocity().y * timeStep);
            Vector2 velocity = new Vector2(bulletVelocity, 0);
            velocity.rotateDeg(90);
            velocity.add(body.getLinearVelocity());
            new ProjectileBehaviour(go, velocity);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(position);
            new Box2dBehaviour(bodyDef, go);
            new CreateCircleFixtureBehaviour(.1f, go);

            Sprite sprite = new Sprite(Core.skin.getSprite("bullet-player"));
            new SpriteBehaviour(go, 0, 0, sprite, RO_PROJECTILES);

            new TeamBehaviour(go, teamBehaviour.team);
        }
    }
}
