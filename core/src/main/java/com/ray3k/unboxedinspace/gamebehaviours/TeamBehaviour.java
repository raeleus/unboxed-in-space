package com.ray3k.unboxedinspace.gamebehaviours;

import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class TeamBehaviour extends BehaviourAdapter {
    public enum Team {
        PLAYER, ENEMY, UPGRADE;
    }
    public Team team;

    public TeamBehaviour(GameObject gameObject, Team team) {
        super(gameObject);
        this.team = team;
    }
}
