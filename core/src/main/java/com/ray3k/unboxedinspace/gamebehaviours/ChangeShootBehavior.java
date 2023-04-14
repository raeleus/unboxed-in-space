package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.ray3k.unboxedinspace.gamebehaviours.shoot.*;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

public class ChangeShootBehavior extends BehaviourAdapter {
    public ChangeShootBehavior(GameObject gameObject) {
        super(gameObject);
        setExecutionOrder(10);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float delta) {
        CharacterBehaviour characterBehaviour = getGameObject().getBehaviour(PlayerBehaviour.class);
        if (characterBehaviour == null) characterBehaviour = getGameObject().getBehaviour(EnemyBehaviour.class);

        if (characterBehaviour.shootBehaviour != null) characterBehaviour.shootBehaviour.destroy();

        switch (MathUtils.random(17)) {
            case (0):
                characterBehaviour.shootBehaviour = new ShootAsteriskBehaviour(getGameObject());
                break;
            case (1):
                characterBehaviour.shootBehaviour = new ShootBackAndForthBehaviour(getGameObject());
                break;
            case (2):
                characterBehaviour.shootBehaviour = new ShootDoubleBehaviour(getGameObject());
                break;
            case (3):
                characterBehaviour.shootBehaviour = new ShootEqualsBehaviour(getGameObject());
                break;
            case (4):
                characterBehaviour.shootBehaviour = new ShootFartBehaviour(getGameObject());
                break;
            case (5):
                characterBehaviour.shootBehaviour = new ShootSingleBehaviour(getGameObject());
                break;
            case (6):
                characterBehaviour.shootBehaviour = new ShootSmartBehaviour(getGameObject());
                break;
            case (7):
                characterBehaviour.shootBehaviour = new ShootSmartRapidBehaviour(getGameObject());
                break;
            case (8):
                characterBehaviour.shootBehaviour = new ShootSmartStrongBehaviour(getGameObject());
                break;
            case (9):
                characterBehaviour.shootBehaviour = new ShootSpiralBehaviour(getGameObject());
                break;
            case (10):
                characterBehaviour.shootBehaviour = new ShootSpiralDoubleBehaviour(getGameObject());
                break;
            case (11):
                characterBehaviour.shootBehaviour = new ShootSpiralQuadBehaviour(getGameObject());
                break;
            case (12):
                characterBehaviour.shootBehaviour = new ShootSpiralDoubleInverseBehaviour(getGameObject());
                break;
            case (13):
                characterBehaviour.shootBehaviour = new ShootSpiralQuadInverseBehaviour(getGameObject());
                break;
            case (14):
                characterBehaviour.shootBehaviour = new ShootStrongBehaviour(getGameObject());
                break;
            case (15):
                characterBehaviour.shootBehaviour = new ShootUpsideDownTeeBehaviour(getGameObject());
                break;
            case (16):
                characterBehaviour.shootBehaviour = new ShootVeeBehaviour(getGameObject());
                break;
            case (17):
                characterBehaviour.shootBehaviour = new ShootXBehaviour(getGameObject());
                break;
        }

        destroy();
    }
}
