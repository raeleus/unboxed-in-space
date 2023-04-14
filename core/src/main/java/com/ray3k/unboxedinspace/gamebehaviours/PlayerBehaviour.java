package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.ray3k.unboxedinspace.GameScreen;
import com.ray3k.unboxedinspace.gamebehaviours.shoot.*;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class PlayerBehaviour extends CharacterBehaviour {

    public PlayerBehaviour(GameObject gameObject, Behaviour shootBehaviour) {
        super(gameObject, shootBehaviour);
    }

    @Override
    public void onDestroy() {
        GameObject go = new GameObject(GameScreen.unBox);
        new GameOverBehaviour(go);

        GameScreen.player = null;
    }
}
