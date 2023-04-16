package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.box2d.Box2dBehaviour;

import static com.ray3k.unboxedinspace.GameScreen.P2M;
import static com.ray3k.unboxedinspace.GameScreen.RO_EFFECTS;

public class FadeSpriteBehaviour extends BehaviourAdapter {
    private float delay;
    private float timer;
    public final Sprite sprite;
    private float startScale;
    private float endScale;
    private final Vector2 position = new Vector2();

    public FadeSpriteBehaviour(GameObject gameObject, Vector2 position, Vector2 velocity, float rotation, Sprite sprite, float startScale, float endScale, float time) {
        super(gameObject);
        this.sprite = sprite;
        this.startScale = startScale;
        this.endScale = endScale;
        this.delay = time;
        this.timer = time;
        sprite.setSize(sprite.getWidth() * P2M, sprite.getHeight() * P2M);
        sprite.setOriginCenter();
        sprite.setScale(startScale);
        setRenderOrder(RO_EFFECTS);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.linearVelocity.set(velocity);
        bodyDef.position.set(position);
        bodyDef.angle = rotation;
        new Box2dBehaviour(bodyDef, getGameObject());
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

        position.add(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
        sprite.setPosition(position.x, position.y);

        timer -= delta;
        timer = Math.max(timer, 0);
        sprite.setScale(startScale + (endScale - startScale) * (1 - timer / delay));
        sprite.setAlpha(timer / delay);
        if (timer == 0) {
            destroy();
        }
    }

    @Override
    public void render(Batch batch) {
        sprite.draw(batch);
    }
}
