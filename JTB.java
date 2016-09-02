package com.jtbgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javafx.application.Application;


/**
 * Created by ggarc_000 on 22/06/2016.
 */
public class JTB extends Game {

    SpriteBatch batch;
    BitmapFont font;


    @Override
    public void create() {

        batch = new SpriteBatch();
        font =  new BitmapFont(Gdx.files.internal("munto.fnt"));
        Preferences prf = Gdx.app.getPreferences("JTB");

        this.setScreen(new MainMenuScreen(this));

    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
