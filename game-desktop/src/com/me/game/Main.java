package com.me.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.game.GameMain;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "game";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320;		
		new LwjglApplication(new GameMain(), cfg);
	}
}
