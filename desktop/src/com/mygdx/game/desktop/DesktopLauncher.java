package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Test Game";
		Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		config.width = d.width;
		config.height = d.height;
		config.foregroundFPS = 60;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
