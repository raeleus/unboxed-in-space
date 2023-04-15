package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Manifold;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.enemies;

public class EnemyBehaviour extends CharacterBehaviour {
    public static final float COLLISION_DAMAGE = 100;

    public EnemyBehaviour(GameObject gameObject, Behaviour shootBehaviour) {
        super(gameObject, shootBehaviour);
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
    public boolean onBox2dCollisionPreSolve(Behaviour other, Contact contact, Manifold oldManifold) {
        if (other.getGameObject().getBehaviour(WallBehaviour.class) != null) contact.setEnabled(false);
        return super.onBox2dCollisionPreSolve(other, contact, oldManifold);
    }

    @Override
    public void onBox2dCollisionEnter(Behaviour other, Contact contact) {
        if (other.getGameObject().getBehaviour(PlayerBehaviour.class) != null) {
            other.getGameObject().getBehaviour(HealthBehaviour.class).subtractHealth(COLLISION_DAMAGE);
            getGameObject().destroy();
        }
    }

    @Override
    public void onDestroy() {
        enemies.removeValue(getGameObject(), true);
    }
}
