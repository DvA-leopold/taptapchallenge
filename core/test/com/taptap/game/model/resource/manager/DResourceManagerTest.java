package com.taptap.game.model.resource.manager;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DResourceManagerTest {

    @Before
    public void setUp() throws Exception {
        config = new LwjglApplicationConfiguration();
        config.useGL30 = false;
        config.title = "TapTap";
        config.height = 640;
        config.width = 800;
        //new LwjglApplication(new TapTap(), config);
    }

    @After
    public void tearDown() throws Exception {
        DResourceManager.getInstance().dispose();
    }

    @Test
    public void testLoadSection() throws Exception {
        DResourceManager.getInstance().loadSection("music", true);
        DResourceManager.getInstance().get("music/Black Vortex.mp3");
    }

    @Test
    public void testUnloadSection() throws Exception {

    }

    @Test
    public void testLoadFile() throws Exception {

    }

    @Test
    public void testUnloadFile() throws Exception {

    }

    @Test
    public void testGetProgress() throws Exception {

    }

    private LwjglApplicationConfiguration config;
}