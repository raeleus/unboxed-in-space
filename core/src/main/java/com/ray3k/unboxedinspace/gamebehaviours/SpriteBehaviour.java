package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.*;

public class SpriteBehaviour extends BehaviourAdapter {
    private float offsetX;
    private float offsetY;
    private Sprite sprite;
    private final Vector2 position = new Vector2();

    public SpriteBehaviour(GameObject gameObject, float offsetX, float offsetY, Sprite sprite) {
        super(gameObject);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.sprite = sprite;
        sprite.setSize(sprite.getWidth() * P2M, sprite.getHeight() * P2M);
        sprite.setOriginCenter();
    }

    @Override
    public void fixedUpdate() {
        if (getGameObject().getBehaviour(Box2dBehaviour.class) != null)
            position.set(getGameObject().getBehaviour(Box2dBehaviour.class).getBody().getPosition());
        else position.setZero();

        position.add(offsetX, offsetY);
        this.sprite.setPosition(position.x, position.y);
    }

    @Override
    public void render(Batch batch) {
        sprite.draw(batch);
    }
}
