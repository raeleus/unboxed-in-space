package com.ray3k.unboxedinspace.gamebehaviours.shoot;

import dev.lyze.gdxUnBox2d.GameObject;

public class ShootSmartRapidBehaviour extends ShootSmartBehaviour {

    public ShootSmartRapidBehaviour(GameObject gameObject) {
        super(gameObject);
        delay = .1f;
        bulletVelocity = 4f;
        damage = 25f;
    }
}
