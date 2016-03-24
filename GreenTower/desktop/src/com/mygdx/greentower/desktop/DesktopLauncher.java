package com.mygdx.greentower.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.greentower.GreenTowerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = GreenTowerGame.TITLE;
		
		new LwjglApplication(new GreenTowerGame(), config);
	}
}
