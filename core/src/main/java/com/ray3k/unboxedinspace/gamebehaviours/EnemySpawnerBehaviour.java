package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ray3k.unboxedinspace.Core;
import com.ray3k.unboxedinspace.gamebehaviours.TeamBehaviour.Team;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateCircleFixtureBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.*;

public class EnemySpawnerBehaviour extends BehaviourAdapter {
    private float delay = 1f;
    private float delayAcceleration = .005f;
    private float timer = 2f;

    public EnemySpawnerBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {
        delay -= delayAcceleration * delta;

        timer -= delta;
        if (timer < 0) {
            timer = delay;

            GameObject go = new GameObject(unBox);
            new EnemyBehaviour(go);
            new ShootSlowBehaviour(go);
            new TeamBehaviour(go, Team.ENEMY);
            new HealthBehaviour(go, 50);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(MathUtils.random(WORLD_WIDTH - 2) + 1, WORLD_HEIGHT);
            new Box2dBehaviour(bodyDef, go);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.filter.categoryBits = CAT_ENEMY;
            fixtureDef.filter.maskBits = CAT_WALL + CAT_PLAYER + CAT_PROJECTILE;
            new CreateCircleFixtureBehaviour(Vector2.Zero, .25f, fixtureDef, go);

            Sprite sprite = new Sprite(Core.skin.getSprite("enemy"));
            sprite.setScale(.5f);
            new SpriteBehaviour(go, 0, 0, sprite, RO_CHARACTERS);

            enemies.add(go);
        }
    }
}
