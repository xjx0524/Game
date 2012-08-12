package com.me.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.map.XTiledLoader;
import com.me.map.XTiledMap;

public class GameScreen implements Screen,InputProcessor{
	
	Stage stg;
	Stage handlestg;
	SkillButtonGroup skillstg;
	Actor hero;
	SpriteBatch batch;
	Camera camera;
	XTiledMap tmx;
	
	public void show() {
		G.sbtp=new SkillButtonTextureProvider();
		G.motp=new MapObjectTextureProvider();
		G.batch=batch = new SpriteBatch();
		
		tmx=XTiledLoader.initMap(Gdx.files.internal("map/1.tmx"), "map/");
		
		stg=new MapObjectGroup();
		hero=new Hero();
		stg.addActor(hero);
		
		handlestg=new Handle(G.ScreenWidth, G.ScreenHeight,true,batch);
		Gdx.input.setInputProcessor(this);
		
		skillstg=new SkillButtonGroup(G.ScreenWidth, G.ScreenHeight,true,batch);
		SkillButton b=new SkillButton(G.TAG.SKILL_PUSH, SkillButton.TYPE.SELECT);
		skillstg.addActor(b);
		skillstg.oganize();
	}


	public void dispose() {
		stg.dispose();
		handlestg.dispose();
		skillstg.dispose();
		G.hero=null;
		G.tmx=null;
		G.motp=null;
		G.sbtp=null;
	}

	public void hide() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void render(float t) {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		tmx.draw((OrthographicCamera)stg.getCamera());
		handlestg.draw();
		skillstg.draw();
	}

	public void resize(int x, int y) {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}

	public boolean keyDown(int keycode) {	return hero.keyDown(keycode);}
	public boolean keyTyped(char arg0) {return false;	}
	public boolean keyUp(int keycode) {return hero.keyUp(keycode);}
	public boolean scrolled(int arg0) {	return false;}
	public boolean touchDown(int x, int y, int pointer, int button) 
	{	
		boolean b1=handlestg.touchDown(x, y, pointer,button);
		boolean b2=skillstg.touchDown(x, y, pointer, button);
		return b1||b2;
	}
	public boolean touchDragged(int x, int y, int pointer) 
	{
		return handlestg.touchDown(x, y, pointer,pointer);
	}
	public boolean touchMoved(int arg0, int arg1) {	return false;}
	public boolean touchUp(int x, int y, int pointer, int button) 
	{
		boolean b1=handlestg.touchUp(x,y,pointer,button);
		boolean b2=skillstg.touchUp(x, y, pointer, button);
		return b1||b2;
	}

	
}
