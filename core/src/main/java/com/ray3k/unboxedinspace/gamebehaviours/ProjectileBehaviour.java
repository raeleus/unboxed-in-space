package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ray3k.unboxedinspace.Core;
import com.ray3k.unboxedinspace.gamebehaviours.TeamBehaviour.Team;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.box2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.box2d.Box2dBehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.box2d.fixtures.CreateBox2dCircleFixtureBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.*;

public class ProjectileBehaviour extends Box2dBehaviourAdapter {
    private final Vector2 velocity;
    private float damage;

    public ProjectileBehaviour(GameObject gameObject, Vector2 velocity, float damage) {
        super(gameObject);
        this.velocity = velocity;
        this.damage = damage;
    }

    @Override
    public void start() {
        GameObject go = getGameObject();
        Box2dBehaviour box2dBehaviour = go.getBehaviour(Box2dBehaviour.class);
        Body body = box2dBehaviour.getBody();
        body.setTransform(body.getPosition().x, body.getPosition().y, MathUtils.degRad * velocity.angleDeg());
        body.setLinearVelocity(velocity);

        TeamBehaviour teamBehaviour = getGameObject().getBehaviour(TeamBehaviour.class);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;

        fixtureDef.filter.categoryBits =  CAT_PROJECTILE;
        if (teamBehaviour.team == Team.PLAYER) fixtureDef.filter.maskBits = CAT_ENEMY + CAT_WALL;
        else fixtureDef.filter.maskBits = CAT_PLAYER + CAT_WALL;
        new CreateBox2dCircleFixtureBehaviour(Vector2.Zero, .1f, fixtureDef, go);

        Sprite sprite = new Sprite(Core.skin.getSprite(teamBehaviour.team == Team.PLAYER ? "bullet-player" : "bullet-enemy"));
        new SpriteBehaviour(go, 0, 0, sprite, RO_PROJECTILES);
    }

    @Override
    public boolean onCollisionPreSolve(Behaviour other, Contact contact, Manifold oldManifold) {
        Team team = getGameObject().getBehaviour(TeamBehaviour.class).team;
        TeamBehaviour otherTeamBehavior = other.getGameObject().getBehaviour(TeamBehaviour.class);
        if (otherTeamBehavior != null) {
            Team otherTeam = otherTeamBehavior.team;
            if (team == otherTeam) contact.setEnabled(false);
        }
        return false;
    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        Team team = getGameObject().getBehaviour(TeamBehaviour.class).team;
        TeamBehaviour otherTeamBehaviour = other.getGameObject().getBehaviour(TeamBehaviour.class);
        if (otherTeamBehaviour == null || otherTeamBehaviour.team != team) {
            getGameObject().destroy();
            HealthBehaviour otherHealth = other.getGameObject().getBehaviour(HealthBehaviour.class);
            if (otherHealth!= null) otherHealth.subtractHealth(damage);
        }
    }
}
