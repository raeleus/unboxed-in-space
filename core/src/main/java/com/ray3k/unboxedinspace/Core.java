package com.ray3k.unboxedinspace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends Game {
    public static Skin skin;
    public static Core core;

    @Override
    public void create() {
        core = this;
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("textures.atlas"));
        skin = new Skin(textureAtlas);
        setScreen(new MenuScreen());
    }
}
