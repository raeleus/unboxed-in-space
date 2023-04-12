package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ray3k.unboxedinspace.gamebehaviours.TeamBehaviour.Team;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

public class ProjectileBehaviour extends BehaviourAdapter {
    private final Vector2 velocity;
    private boolean destroy;
    public ProjectileBehaviour(GameObject gameObject, Vector2 velocity) {
        super(gameObject);
        this.velocity = velocity;
    }

    @Override
    public void start() {
        GameObject go = getGameObject();
        Box2dBehaviour box2dBehaviour = go.getBehaviour(Box2dBehaviour.class);
        Body body = box2dBehaviour.getBody();
        body.setTransform(body.getPosition().x, body.getPosition().y, MathUtils.degRad * velocity.angleDeg());
        body.setLinearVelocity(velocity);
    }

    @Override
    public void update(float delta) {
//        if (destroy) getGameObject().destroy();
    }

    @Override
    public boolean onCollisionPreSolve(Behaviour other, Contact contact, Manifold oldManifold) {
        Team team = getGameObject().getBehaviour(TeamBehaviour.class).team;
        TeamBehaviour otherTeamBehavior = other.getGameObject().getBehaviour(TeamBehaviour.class);
        if (otherTeamBehavior != null) {
            Team otherTeam = otherTeamBehavior.team;
            if (team == otherTeam) contact.setEnabled(false);
        }
        return super.onCollisionPreSolve(other, contact, oldManifold);
    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        Team team = getGameObject().getBehaviour(TeamBehaviour.class).team;
        TeamBehaviour otherTeamBehaviour = other.getGameObject().getBehaviour(TeamBehaviour.class);
        if (otherTeamBehaviour == null || otherTeamBehaviour.team != team) {
//            destroy = true;
            getGameObject().destroy();
        }
    }
}
