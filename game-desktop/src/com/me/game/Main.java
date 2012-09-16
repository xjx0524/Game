package com.me.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	
	private static GameMain game;
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "game";
		cfg.useGL20 = false;
		cfg.width = 320;
		cfg.height = 240;
		game=new GameMain();
		game.file=new File("Mystemple.sav");
		if (!game.file.exists()){
			try {
				game.file.createNewFile();
				FileOutputStream s=new FileOutputStream(game.file);
				s.write(new byte[]{1,1,1,2}, 0, 4);
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		new LwjglApplication(game,cfg);
	}
}
