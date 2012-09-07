package com.me.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameMain extends Game {
	Screen curScreen;
	
	public void create() {
				G.game=this;
				Gdx.graphics.setDisplayMode(G.ScreenWidth, G.ScreenHeight,false);
				G.initSound();
				setScreen(curScreen=new GameScreen(1));
			}
}
