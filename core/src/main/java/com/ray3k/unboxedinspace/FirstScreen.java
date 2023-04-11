package com.ray3k.unboxedinspace;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.ray3k.unboxedinspace.Core.skin;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen extends ScreenAdapter {
    private FillViewport starViewport;
    private ScreenViewport uiViewport;
    private Stage stage;

    @Override
    public void show() {
        starViewport = new FillViewport(800, 800);
        uiViewport = new ScreenViewport();
        stage = new Stage(uiViewport);

        createStarField();
        createUI();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        uiViewport.apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
    }

    private void createStarField() {
        
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
