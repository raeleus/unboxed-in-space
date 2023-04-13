package com.ray3k.unboxedinspace.gamebehaviours;

import dev.lyze.gdxUnBox2d.GameObject;

public class ShootSmartStrongBehaviour extends ShootSmartBehaviour {

    public ShootSmartStrongBehaviour(GameObject gameObject) {
        super(gameObject);
        delay = .6f;
        bulletVelocity = 15f;
        damage = 50f;
    }
}
