package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.*;

public class ShootSmartRapidBehaviour extends ShootSmartBehaviour {

    public ShootSmartRapidBehaviour(GameObject gameObject) {
        super(gameObject);
        delay = .1f;
        bulletVelocity = 4f;
        damage = 25f;
    }
}
