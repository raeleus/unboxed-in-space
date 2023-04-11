package com.ray3k.unboxedinspace.menubehaviours;

import com.badlogic.gdx.math.MathUtils;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

import static com.ray3k.unboxedinspace.MenuScreen.unBox;

public class MenuStarGeneratorBehaviour extends BehaviourAdapter {
    private static final float SPAWN_DELAY = .1f;
    private static final float STARS_PER_SPAWN = 3;
    private float timer;

    public MenuStarGeneratorBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {
        timer -= delta;
        if (timer < 0) {
            timer = MathUtils.random(SPAWN_DELAY);

            for (int i = 0; i < STARS_PER_SPAWN; i++) {
                GameObject gameObject = new GameObject(unBox);
                new MenuStarBehaviour(gameObject);
            }
        }
    }
}
