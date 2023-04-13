package com.ray3k.unboxedinspace.gamebehaviours;

import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class HealthBehaviour extends BehaviourAdapter {
    public float health;

    public HealthBehaviour(GameObject gameObject, float health) {
        super(gameObject);
        this.health = health;
    }

    @Override
    public void update(float delta) {
        if (health <= 0) getGameObject().destroy();
    }
}
