package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

import static com.ray3k.unboxedinspace.Core.skin;
import static com.ray3k.unboxedinspace.Core.soundExplosion;
import static com.ray3k.unboxedinspace.GameScreen.P2M;
import static com.ray3k.unboxedinspace.GameScreen.RO_EXPLOSIONS;

public class ExplosionBehaviour extends BehaviourAdapter {
    private float delay = .2f;
    private float timer = delay;
    public final Sprite sprite;
    public float offsetX;
    public float offsetY;
    private final Vector2 position = new Vector2();

    public ExplosionBehaviour(GameObject gameObject, float offsetX, float offsetY) {
        super(gameObject);
        this.sprite = new Sprite(skin.getSprite("explosion"));
        sprite.setSize(sprite.getWidth() * P2M, sprite.getHeight() * P2M);
        sprite.setOriginCenter();
        sprite.setScale(0);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        setRenderOrder(RO_EXPLOSIONS);
        soundExplosion.play();
    }

    @Override
    public void update(float delta) {
        if (getGameObject().getBehaviour(Box2dBehaviour.class) != null) {
            Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
            position.set(body.getPosition());
            sprite.setRotation(MathUtils.radDeg * body.getTransform().getRotation());
        } else {
            position.setZero();
            sprite.setRotation(0);
        }

        position.add(offsetX - sprite.getWidth() / 2, offsetY - sprite.getHeight() / 2);
        sprite.setPosition(position.x, position.y);

        timer -= delta;
        sprite.setScale(.5f * (1 - timer / delay));
        sprite.setAlpha(timer / delay);
        if (timer < 0) {
            destroy();
        }
    }

    @Override
    public void render(Batch batch) {
        sprite.draw(batch);
    }
}
