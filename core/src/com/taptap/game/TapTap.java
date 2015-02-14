package com.taptap.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.taptap.game.model.resource.manager.ResourceManager;
import com.taptap.game.model.save.manager.StorageManager;
import com.taptap.game.view.screens.LoadScreen;

import java.util.logging.FileHandler;

public final class TapTap extends Game {
    @Override
    public void create() {
        mainBatch = new SpriteBatch();
        // resource storage
        storage = new StorageManager(true);
        //class for debug
        debug = new Debug();
        // first screen
        setScreen(new LoadScreen(mainBatch));
    }

    @Override
    public void render() {
        super.render();
        debug.render(mainBatch);
    }

    @Override
    public void dispose() {
        ResourceManager.getInstance().dispose();
        mainBatch.dispose();
        debug.dispose();
    }

    public static StorageManager getStorage() {
        return storage;
    }

    private static StorageManager storage;
    private SpriteBatch mainBatch;

    private Debug debug;
}

class ShaderForTest {
    /**
     * This shader is for Windows systems only, in next library build or
     * something it must work out of the box,
     * in linux or android or (not)Windows systems, just clean Batch arguments.
     * @return shader program
     */
    public static ShaderProgram createDefaultShader() {
        String vertexShader = "#version 330\n"
                + "in vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "in vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "in vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "uniform mat4 u_projTrans;\n" //
                + "out vec4 v_color;\n" //
                + "out vec2 v_texCoords;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "   v_color.a = v_color.a * (255.0/254.0);\n" //
                + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";
        String fragmentShader = "#version 330\n"
                + "#ifdef GL_ES\n" //
                + "#define LOWP lowp\n" //
                + "precision mediump float;\n" //
                + "#else\n" //
                + "#define LOWP \n" //
                + "#endif\n" //
                + "in LOWP vec4 v_color;\n" //
                + "in vec2 v_texCoords;\n" //
                + "out vec4 fragColor;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "void main()\n"//
                + "{\n" //
                + "  fragColor = v_color * texture(u_texture, v_texCoords);\n" //
                + "}";

        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
        if (!shader.isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
        return shader;
    }
}

class Debug {
    /**
     * для дебага, показывается fps и draw calls.
     * @param batch батч в по которому считаются draw calls
     */
    public void render(final SpriteBatch batch) {
        debugBatch.begin();
        fontStandart.draw(debugBatch,
                "D_C:" + batch.renderCalls +
                        " fps:" + Gdx.graphics.getFramesPerSecond(), 20, 30);
        debugBatch.end();
    }

    public void dispose() {
        debugBatch.dispose();
    }

    private BitmapFont fontStandart = new BitmapFont(new FileHandle("fonts/whiteFont.fnt"));
    private SpriteBatch debugBatch = new SpriteBatch();
}