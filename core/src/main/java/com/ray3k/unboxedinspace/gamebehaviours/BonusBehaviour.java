package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ray3k.unboxedinspace.Core;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateCircleFixtureBehaviour;

import static com.ray3k.unboxedinspace.Core.*;
import static com.ray3k.unboxedinspace.GameScreen.*;

public class BonusBehaviour extends BehaviourAdapter {
    private static final int BOUNCES_MAX = 3;
    private static final float speed = 5f;
    private int bounces;

    public BonusBehaviour(GameObject gameObject, Vector2 position) {
        super(gameObject);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.linearVelocity.set(speed, 0);
        bodyDef.linearVelocity.rotateDeg(MathUtils.random(360f));
        new Box2dBehaviour(bodyDef, getGameObject());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = CAT_PROJECTILE;
        fixtureDef.filter.maskBits = CAT_PLAYER + CAT_WALL;
        fixtureDef.restitution = 1;
        new CreateCircleFixtureBehaviour(Vector2.Zero, .5f, fixtureDef, getGameObject());

        Sprite sprite = new Sprite(skin.getSprite("box"));
        sprite.setScale(.5f);
        new SpriteBehaviour(getGameObject(), 0, 0, sprite, RO_PROJECTILES);
    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        PlayerBehaviour playerBehaviour = other.getGameObject().getBehaviour(PlayerBehaviour.class);
        if (playerBehaviour != null) {
            getGameObject().destroy();
            addScore();
            new ChangeShootBehavior(other.getGameObject());
            soundBonus.play();
        } else {
            bounces++;
            if (bounces > BOUNCES_MAX) getGameObject().destroy();
        }
    }
}
