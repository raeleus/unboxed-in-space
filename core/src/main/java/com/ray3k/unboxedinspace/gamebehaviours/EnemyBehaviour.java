package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Manifold;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

public class EnemyBehaviour extends BehaviourAdapter {
    public static final float COLLISION_DAMAGE = 100;

    public EnemyBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {
        Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        body.setTransform(body.getTransform().getPosition().x, body.getTransform().getPosition().y, MathUtils.degRad * 270);
        body.setLinearVelocity(0, -5);
    }

    @Override
    public void update(float delta) {
        Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        if (body.getTransform().getPosition().y < 0) getGameObject().destroy();
    }

    @Override
    public boolean onCollisionPreSolve(Behaviour other, Contact contact, Manifold oldManifold) {
        if (other.getGameObject().getBehaviour(WallBehaviour.class) != null) contact.setEnabled(false);
        return super.onCollisionPreSolve(other, contact, oldManifold);
    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        if (other.getGameObject().getBehaviour(PlayerBehaviour.class) != null) {
            other.getGameObject().getBehaviour(HealthBehaviour.class).health -= COLLISION_DAMAGE;
            getGameObject().destroy();
        }
    }
}
