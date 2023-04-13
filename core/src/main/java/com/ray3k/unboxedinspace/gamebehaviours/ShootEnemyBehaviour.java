package com.ray3k.unboxedinspace.gamebehaviours;

import dev.lyze.gdxUnBox2d.GameObject;

public class ShootEnemyBehaviour extends ShootSingleBehaviour {
    public ShootEnemyBehaviour(GameObject gameObject) {
        super(gameObject);
        delay = .2f;
        bulletVelocity = 10f;
        damage = 25f;
    }
}
