package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ray3k.unboxedinspace.GameScreen;
import dev.lyze.gdxUnBox2d.BodyDefType;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.Core.skin;
import static com.ray3k.unboxedinspace.GameScreen.*;

public class StarSpawnerBehaviour extends BehaviourAdapter {
    private static final float MAX_DELAY = .2f;
    private float timer;

    public StarSpawnerBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void update(float delta) {
        timer -= delta;
        if (timer < 0) {
            timer = MathUtils.random(MAX_DELAY);

            GameObject go = new GameObject(unBox);
            new StarBehaviour(go);

            new Box2dBehaviour(BodyDefType.DynamicBody, go);

            Sprite sprite = new Sprite(skin.getSprite("star"));
            new SpriteBehaviour(go, 0, 0, sprite, RO_BACKGROUND);
        }
    }
}
