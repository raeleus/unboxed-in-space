package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.*;

public class SpriteBehaviour extends BehaviourAdapter {
    public float offsetX;
    public float offsetY;
    public final Sprite sprite;
    private final Vector2 position = new Vector2();

    public SpriteBehaviour(GameObject gameObject, float offsetX, float offsetY, Sprite sprite, float renderOrder) {
        super(gameObject);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.sprite = sprite;
        sprite.setSize(sprite.getWidth() * P2M, sprite.getHeight() * P2M);
        sprite.setOriginCenter();
        setRenderOrder(renderOrder);
    }

    @Override
    public void fixedUpdate() {
        if (getGameObject().getBehaviour(Box2dBehaviour.class) != null) {
            Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
            position.set(body.getPosition());
            sprite.setRotation(MathUtils.radDeg * body.getTransform().getRotation());
        }
        else {
            position.setZero();
            sprite.setRotation(0);
        }

        position.add(offsetX, offsetY);
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void render(Batch batch) {
        sprite.draw(batch);
    }
}
