package com.me.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "game";
		cfg.useGL20 = false;
		cfg.width = 320;
		cfg.height = 240;
		
		new LwjglApplication(new GameMain(), cfg);
	}
}
