package com.ray3k.unboxedinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.unboxedinspace.gamebehaviours.*;
import com.ray3k.unboxedinspace.gamebehaviours.TeamBehaviour.Team;
import dev.lyze.gdxUnBox2d.*;
import dev.lyze.gdxUnBox2d.behaviours.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateBoxFixtureBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateCircleFixtureBehaviour;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    public static final float WORLD_WIDTH = 10f;
    public static final float WORLD_HEIGHT = 10f;
    public static final float P2M = .0125f;
    public static final float M2P = 1 / P2M;
    public static final float RO_BACKGROUND = -10f;
    public static final float RO_PROJECTILES = -5f;
    public static final float RO_CHARACTERS = 0f;
    public static final float RO_EXPLOSIONS = 5f;
    public static final short CAT_WALL = 0x1 << 1;
    public static final short CAT_PLAYER = 0x1 << 2;
    public static final short CAT_ENEMY = 0x1 << 3;
    public static final short CAT_PROJECTILE = 0x1 << 4;

    public static ExtendViewport gameViewport;
    private SpriteBatch spriteBatch;
    public ScreenViewport uiViewport;
    private Box2DDebugRenderer debugRenderer;
    private Stage stage;
    public static UnBox<Box2dPhysicsWorld> unBox;
    public static float timeStep;
    public static final Array<GameObject> enemies = new Array<>();
    public static GameObject player;

    @Override
    public void show() {
        gameViewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        uiViewport = new ScreenViewport();
        spriteBatch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        stage = new Stage(uiViewport, spriteBatch);
        unBox =  new UnBox<>(new Box2dPhysicsWorld(new World(new Vector2(0, 0), true)));
        timeStep = unBox.getOptions().getTimeStep();

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

        debugRenderer.render(unBox.getPhysicsWorld().getWorld(), gameViewport.getCamera().combined);

        uiViewport.apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, false);
        gameViewport.getCamera().position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        uiViewport.update(width, height, true);
    }

    private void initGame() {
        player = new GameObject(unBox);
        new PlayerBehaviour(player);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(5, 2);
        new Box2dBehaviour(bodyDef, player);
        new MovementKeyboardBehaviour(player);
        new ShootAsteriskBehaviour(player);
        new TeamBehaviour(player, Team.PLAYER);
        new HealthBehaviour(player, 100);

        Sprite sprite = new Sprite(Core.skin.getSprite("player"));
        sprite.setScale(.5f);
        new SpriteBehaviour(player, 0, 0, sprite, RO_CHARACTERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = CAT_PLAYER;
        fixtureDef.filter.maskBits = CAT_WALL + CAT_ENEMY + CAT_PROJECTILE;
        new CreateCircleFixtureBehaviour(Vector2.Zero, .1f, fixtureDef, player);

        GameObject go = new GameObject(unBox);
        new EnemySpawnerBehaviour(go);

        go = new GameObject(unBox);
        new StarSpawnerBehaviour(go);

        go = new GameObject(unBox);
        new Box2dBehaviour(BodyDefType.StaticBody, go);
        fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = CAT_WALL;
        new CreateBoxFixtureBehaviour(.5f, WORLD_HEIGHT / 2, new Vector2(-.5f, WORLD_HEIGHT / 2), fixtureDef, go);
        new WallBehaviour(go);

        go = new GameObject(unBox);
        new Box2dBehaviour(BodyDefType.StaticBody, go);
        new CreateBoxFixtureBehaviour( .5f, WORLD_HEIGHT / 2, new Vector2(WORLD_WIDTH + .5f, WORLD_HEIGHT / 2), fixtureDef, go);
        new WallBehaviour(go);

        go = new GameObject(unBox);
        new Box2dBehaviour(BodyDefType.StaticBody, go);
        new CreateBoxFixtureBehaviour(WORLD_WIDTH / 2, .5f, new Vector2(WORLD_WIDTH / 2, -.5f), fixtureDef, go);
        new WallBehaviour(go);

        go = new GameObject(unBox);
        new Box2dBehaviour(BodyDefType.StaticBody, go);
        new CreateBoxFixtureBehaviour(WORLD_WIDTH / 2, .5f, new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT + .5f), fixtureDef, go);
        new WallBehaviour(go);
    }

    private void createUI() {
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
    }
}
