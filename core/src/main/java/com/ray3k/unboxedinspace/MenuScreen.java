package com.ray3k.unboxedinspace;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.unboxedinspace.menubehaviors.MenuStarBehaviour;
import com.ray3k.unboxedinspace.menubehaviors.MenuStarGeneratorBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.NoPhysicsWorld;
import dev.lyze.gdxUnBox2d.UnBox;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.ray3k.unboxedinspace.Core.skin;

/** First screen of the application. Displayed after the application is created. */
public class MenuScreen extends ScreenAdapter {
    public static final float WORLD_WIDTH = 800;
    public static final float WORLD_HEIGHT = 800;
    private FillViewport starViewport;
    private SpriteBatch spriteBatch;
    public ScreenViewport uiViewport;
    private Stage stage;
    public static UnBox unBox;

    @Override
    public void show() {
        starViewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT);
        uiViewport = new ScreenViewport();
        spriteBatch = new SpriteBatch();
        stage = new Stage(uiViewport, spriteBatch);
        unBox = new UnBox<>(new NoPhysicsWorld());

        createStarField();
        createUI();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        unBox.preRender(delta);
        starViewport.apply();
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(starViewport.getCamera().combined);
        unBox.render(stage.getBatch());
        spriteBatch.end();
        unBox.postRender();

        uiViewport.apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        starViewport.update(width, height, true);
        uiViewport.update(width, height, true);
    }

    private void createStarField() {
        GameObject go = new GameObject(unBox);
        new MenuStarGeneratorBehaviour(go);
    }

    private void createUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        root.pad(30);
        Image image = new Image(skin, "title");
        image.setScaling(Scaling.fit);
        root.add(image);
        image.addAction(sequence(alpha(0), fadeIn(1)));

        root.row();
        image = new Image(skin, "press-any-key");
        image.setScaling(Scaling.fit);
        root.add(image);
        image.addAction(sequence(alpha(0), delay(1), fadeIn(1)));
    }
}
