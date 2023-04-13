package com.ray3k.unboxedinspace.gamebehaviours;

import com.ray3k.unboxedinspace.GameScreen;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class PlayerBehaviour extends BehaviourAdapter {
    public PlayerBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void onDestroy() {
        GameObject go = new GameObject(GameScreen.unBox);
        new GameOverBehaviour(go);
    }
}
