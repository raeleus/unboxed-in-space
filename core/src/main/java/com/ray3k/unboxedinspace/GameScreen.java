package com.ray3k.unboxedinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.unboxedinspace.gamebehaviours.*;
import dev.lyze.gdxUnBox2d.*;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    public static final float WORLD_WIDTH = 10;
    public static final float WORLD_HEIGHT = 10;
    public static final float P2M = .0125f;
    public static final float M2P = 1 / P2M;
    private ExtendViewport gameViewport;
    private SpriteBatch spriteBatch;
    public ScreenViewport uiViewport;
    private Stage stage;
    public static UnBox unBox;

    @Override
    public void show() {
        gameViewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        uiViewport = new ScreenViewport();
        spriteBatch = new SpriteBatch();
        stage = new Stage(uiViewport, spriteBatch);
        unBox =  new UnBox<>(new Box2dPhysicsWorld(new World(new Vector2(0, 0), true)));

        initGame();
        createUI();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        unBox.preRender(delta);
        gameViewport.apply();
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);
        unBox.render(stage.getBatch());
        spriteBatch.end();
        unBox.postRender();

        uiViewport.apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
        uiViewport.update(width, height, true);
    }

    private void initGame() {
        GameObject go = new GameObject(unBox);
        new PlayerBehaviour(go);
        new Box2dBehaviour(BodyDefType.DynamicBody, go);
        new MovementKeyboardBehaviour(go);
        new ShootSingleBehaviour(go);
        new TeamPlayerBehaviour(go);

        Sprite sprite = new Sprite(Core.skin.getSprite("player"));
        sprite.setScale(.5f);
        new SpriteBehaviour(go, 0, 0, sprite);

        go = new GameObject(unBox);
        new SpawnerBehaviour(go);
    }

    private void createUI() {
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
    }
}
