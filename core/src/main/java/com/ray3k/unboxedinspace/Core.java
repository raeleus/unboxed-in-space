package com.ray3k.unboxedinspace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends Game {
    public static Skin skin;
    public static Sound soundLaser;
    public static Sound soundBonus;
    public static Sound soundExplosion;
    public static Core core;

    @Override
    public void create() {
        core = this;
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("textures.atlas"));
        skin = new Skin(Gdx.files.internal("skin.json"), textureAtlas);
        soundLaser = Gdx.audio.newSound(Gdx.files.internal("laser.mp3"));
        soundBonus = Gdx.audio.newSound(Gdx.files.internal("bonus.mp3"));
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));

        setScreen(new MenuScreen());
    }
}
