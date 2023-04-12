package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.*;

public class StarBehaviour extends BehaviourAdapter {
    private static final float MIN_SPEED = 5;
    private static final float MAX_SPEED = 15;

    public StarBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {
        GameObject go = getGameObject();
        Body body = go.getBehaviour(Box2dBehaviour.class).getBody();
        body.setTransform((WORLD_WIDTH - gameViewport.getWorldWidth()) / 2 + MathUtils.random(gameViewport.getWorldWidth()), (WORLD_HEIGHT - gameViewport.getWorldHeight()) / 2 + gameViewport.getWorldHeight(), 0);
        body.setLinearVelocity(0, -MathUtils.random(MIN_SPEED, MAX_SPEED));
    }

    @Override
    public void fixedUpdate() {
        GameObject go = getGameObject();
        Box2dBehaviour box2dBehaviour = go.getBehaviour(Box2dBehaviour.class);
        if (box2dBehaviour != null) {
            Body body = box2dBehaviour.getBody();
            if (body.getTransform().getPosition().y < (WORLD_HEIGHT - gameViewport.getWorldHeight()) / 2) go.destroy();
        }
    }

    @Override
    public void onDestroy() {
        System.out.println("destroyed");
    }
}
