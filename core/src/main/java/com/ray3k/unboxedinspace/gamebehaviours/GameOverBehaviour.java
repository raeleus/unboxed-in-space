package com.ray3k.unboxedinspace.gamebehaviours;

import com.ray3k.unboxedinspace.Core;
import com.ray3k.unboxedinspace.GameScreen;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class GameOverBehaviour extends BehaviourAdapter {
    private float timer = 2f;

    public GameOverBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void update(float delta) {
        timer -= delta;
        if (timer < 0) {
            Core.core.setScreen(new GameScreen());
        }
    }
}
