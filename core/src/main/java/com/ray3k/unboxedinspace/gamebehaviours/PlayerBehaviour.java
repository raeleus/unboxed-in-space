package com.ray3k.unboxedinspace.gamebehaviours;

import com.badlogic.gdx.math.MathUtils;
import com.ray3k.unboxedinspace.GameScreen;
import com.ray3k.unboxedinspace.gamebehaviours.shoot.*;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class PlayerBehaviour extends BehaviourAdapter {
    private Behaviour shootBehaviour;
    public PlayerBehaviour(GameObject gameObject, Behaviour shootBehaviour) {
        super(gameObject);
        this.shootBehaviour = shootBehaviour;
    }

    @Override
    public void onDestroy() {
        GameObject go = new GameObject(GameScreen.unBox);
        new GameOverBehaviour(go);
    }

    public void newShootBehaviour() {
        shootBehaviour.destroy();

        switch (MathUtils.random(17)) {
            case (0):
                shootBehaviour = new ShootAsteriskBehaviour(getGameObject());
                break;
            case (1):
                shootBehaviour = new ShootBackAndForthBehaviour(getGameObject());
                break;
            case (2):
                shootBehaviour = new ShootDoubleBehaviour(getGameObject());
                break;
            case (3):
                shootBehaviour = new ShootEqualsBehaviour(getGameObject());
                break;
            case (4):
                shootBehaviour = new ShootFartBehaviour(getGameObject());
                break;
            case (5):
                shootBehaviour = new ShootSingleBehaviour(getGameObject());
                break;
            case (6):
                shootBehaviour = new ShootSmartBehaviour(getGameObject());
                break;
            case (7):
                shootBehaviour = new ShootSmartRapidBehaviour(getGameObject());
                break;
            case (8):
                shootBehaviour = new ShootSmartStrongBehaviour(getGameObject());
                break;
            case (9):
                shootBehaviour = new ShootSpiralBehaviour(getGameObject());
                break;
            case (10):
                shootBehaviour = new ShootSpiralDoubleBehaviour(getGameObject());
                break;
            case (11):
                shootBehaviour = new ShootSpiralQuadBehaviour(getGameObject());
                break;
            case (12):
                shootBehaviour = new ShootSpiralDoubleInverseBehaviour(getGameObject());
                break;
            case (13):
                shootBehaviour = new ShootSpiralQuadInverseBehaviour(getGameObject());
                break;
            case (14):
                shootBehaviour = new ShootStrongBehaviour(getGameObject());
                break;
            case (15):
                shootBehaviour = new ShootUpsideDownTeeBehaviour(getGameObject());
                break;
            case (16):
                shootBehaviour = new ShootVeeBehaviour(getGameObject());
                break;
            case (17):
                shootBehaviour = new ShootXBehaviour(getGameObject());
                break;
        }
    }
}
