package com.ray3k.unboxedinspace.gamebehaviours;

import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.box2d.Box2dBehaviourAdapter;

public class CharacterBehaviour extends Box2dBehaviourAdapter {
    public Behaviour shootBehaviour;

    public CharacterBehaviour(GameObject gameObject, Behaviour shootBehaviour) {
        super(gameObject);
        this.shootBehaviour = shootBehaviour;
    }

}
