package com.ray3k.unboxedinspace.gamebehaviours;

import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class CharacterBehaviour extends BehaviourAdapter {
    public Behaviour shootBehaviour;

    public CharacterBehaviour(GameObject gameObject, Behaviour shootBehaviour) {
        super(gameObject);
        this.shootBehaviour = shootBehaviour;
    }

}
