package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.box2d.Box2dBehaviour;

import static com.ray3k.unboxedinspace.Core.skin;
import static com.ray3k.unboxedinspace.Core.soundExplosion;
import static com.ray3k.unboxedinspace.GameScreen.unBox;

public class HealthBehaviour extends BehaviourAdapter {
    private float health;
    public float bonusChance;

    public HealthBehaviour(GameObject gameObject, float health, float bonusChance) {
        super(gameObject);
        this.health = health;
        this.bonusChance = bonusChance;
    }

    public void subtractHealth(float damage) {
        health -= damage;

        if (health <= 0) {
            Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
            GameObject go = new GameObject(unBox);
            Sprite sprite = new Sprite(skin.getSprite("explosion"));
            new FadeSpriteBehaviour(go, body.getPosition(), body.getLinearVelocity(), 0, sprite, 0, .5f, .2f);
            soundExplosion.play();

            if (MathUtils.randomBoolean(bonusChance)) {
                go = new GameObject(unBox);
                new BonusBehaviour(go, getGameObject().getBehaviour(Box2dBehaviour.class).getBody().getPosition());
            }

            getGameObject().destroy();
        } else {
            Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
            Sprite sprite = new Sprite(skin.getSprite(getGameObject().getBehaviour(PlayerBehaviour.class) != null ? "player-white" : "enemy-white"));
            new FadeSpriteBehaviour(new GameObject(unBox), body.getPosition(), body.getLinearVelocity(), body.getTransform().getRotation(), sprite, .5f, .5f, .2f);
        }
    }
}
