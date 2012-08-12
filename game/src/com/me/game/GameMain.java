package com.me.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameMain extends Game {
	Screen curScreen;
	
	public void create() {
				Gdx.graphics.setDisplayMode(G.ScreenWidth, G.ScreenHeight,false);
				setScreen(curScreen=new GameScreen());				
			}
}
