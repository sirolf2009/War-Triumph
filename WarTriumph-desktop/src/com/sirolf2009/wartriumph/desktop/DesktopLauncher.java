package com.sirolf2009.wartriumph.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sirolf2009.wartriumph.WarTriumph;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "War Triumph";
	    config.width = 1024;
	    config.height = 800;
		new LwjglApplication(new WarTriumph(), config);
	}
}
