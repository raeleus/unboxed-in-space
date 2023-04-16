package com.ray3k.unboxedinspace.gamebehaviours.shoot;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ray3k.unboxedinspace.GameScreen;
import com.ray3k.unboxedinspace.gamebehaviours.ProjectileBehaviour;
import com.ray3k.unboxedinspace.gamebehaviours.TeamBehaviour;
import com.ray3k.unboxedinspace.gamebehaviours.TeamBehaviour.Team;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.box2d.Box2dBehaviour;

import static com.ray3k.unboxedinspace.Core.soundLaser;
import static com.ray3k.unboxedinspace.GameScreen.*;

public class ShootSmartBehaviour extends BehaviourAdapter {
    protected float delay = .4f;
    protected float bulletVelocity = 10f;
    protected float damage = 25f;
    private float timer;
    private float angle;
    private static final Vector2 temp = new Vector2();

    public ShootSmartBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void start() {
        timer = delay;
    }

    @Override
    public void update(float delta) {
        timer -= delta;
        if (timer < 0) {
            timer = delay;
            Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
            TeamBehaviour teamBehaviour = getGameObject().getBehaviour(TeamBehaviour.class);

            if (teamBehaviour.team == Team.PLAYER) soundLaser.play(.1f);

            float closestDistance = Float.MAX_VALUE;
            GameObject closest = null;

            if (teamBehaviour.team == Team.PLAYER) for (GameObject go : enemies) {
                Box2dBehaviour beh = go.getBehaviour(Box2dBehaviour.class);
                if (beh != null) {
                    temp.set(go.getBehaviour(Box2dBehaviour.class).getBody().getTransform().getPosition());
                    temp.sub(getGameObject().getBehaviour(Box2dBehaviour.class).getBody().getTransform().getPosition());
                    float distance = temp.len();
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closest = go;
                    }
                }
            } else closest = player;

            if (closest != null) {
                Body enemyBody = closest.getBehaviour(Box2dBehaviour.class).getBody();
                Vector2 sol = intercept(body.getPosition(), bulletVelocity, enemyBody.getPosition(), enemyBody.getLinearVelocity());

                if (sol != null) {
                    angle = sol.sub(body.getTransform().getPosition()).angleDeg();
                } else {
                    angle = 90;
                }
            }

            if (teamBehaviour.team == Team.PLAYER) soundLaser.play(.1f);

            GameObject go = new GameObject(unBox);
            Vector2 velocity = new Vector2(bulletVelocity, 0);
            velocity.rotateDeg(angle);
            new ProjectileBehaviour(go, velocity, damage);

            Vector2 position = new Vector2(new Vector2(body.getPosition()));
            position.add(body.getLinearVelocity().x * timeStep, body.getLinearVelocity().y * timeStep);
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.position.set(position);
            new Box2dBehaviour(bodyDef, go);

            new TeamBehaviour(go, teamBehaviour.team);
        }
    }

    public static Vector2 intercept(Vector2 bulletOrigin, float bulletSpeed, Vector2 enemyPosition, Vector2 enemyVelocity) {
        float tx = enemyPosition.x - bulletOrigin.x;
        float ty = enemyPosition.y - bulletOrigin.y;
        float tvx = enemyVelocity.x;
        float tvy = enemyVelocity.y;

        // Get quadratic equation components
        float a = tvx * tvx + tvy * tvy - bulletSpeed * bulletSpeed;
        float b = 2 * (tvx * tx + tvy * ty);
        float c = tx * tx + ty * ty;

        // Solve quadratic
        Vector2 ts = quad(a, b, c); // See quad(), below

        // Find smallest positive solution
        Vector2 sol = null;
        if (ts != null) {
            float t0 = ts.x;
            float t1 = ts.y;
            float t = Math.min(t0, t1);
            if (t < 0) t = Math.max(t0, t1);
            if (t > 0) {

                sol = new Vector2();
                sol.x = enemyPosition.x + enemyVelocity.x * t;
                sol.y = enemyPosition.y + enemyVelocity.y * t;
            }
        }

        return sol;
    }

    private static Vector2 quad(float a, float b, float c) {
        Vector2 sol = null;
        if (Math.abs(a) < 1e-6) {
            if (Math.abs(b) < 1e-6) {
                if (Math.abs(c) < 1e-6) {
                    sol = new Vector2(0, 0);
                } else {
                    sol = null;
                }
            } else {
                sol = new Vector2(-c / b, -c / b);
            }
        } else {
            float disc = b * b - 4 * a * c;
            if (disc >= 0) {
                disc = (float) Math.sqrt(disc);
                a = 2 * a;
                sol = new Vector2((-b - disc) / a, (-b + disc) / a);
            }
        }
        return sol;
    }
}
