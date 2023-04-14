package com.ray3k.unboxedinspace.gamebehaviours.shoot;

import dev.lyze.gdxUnBox2d.GameObject;

public class ShootSlowBehaviour extends ShootSingleBehaviour {
    public ShootSlowBehaviour(GameObject gameObject) {
        super(gameObject);
        delay = .2f;
        bulletVelocity = 10f;
        damage = 25f;
    }
}
