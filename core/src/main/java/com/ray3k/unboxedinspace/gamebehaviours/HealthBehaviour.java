package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.unBox;

public class HealthBehaviour extends BehaviourAdapter {
    public float health;
    public float bonusChance;

    public HealthBehaviour(GameObject gameObject, float health, float bonusChance) {
        super(gameObject);
        this.health = health;
        this.bonusChance = bonusChance;
    }

    @Override
    public void update(float delta) {
        if (health <= 0) {
            Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
            GameObject go = new GameObject(unBox);
            new ExplosionBehaviour(go, body.getPosition().x, body.getPosition().y);

            if (MathUtils.randomBoolean(bonusChance)) {
                go = new GameObject(unBox);
                new BonusBehaviour(go, getGameObject().getBehaviour(Box2dBehaviour.class).getBody().getPosition());
            }

            getGameObject().destroy();
        }
    }
}
