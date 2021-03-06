package com.taptap.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.taptap.game.TapTap;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = false;
		config.title = "TapTap";
		config.height = 600;
		config.width = 1000;
        new LwjglApplication(new TapTap(), config);
	}
}
