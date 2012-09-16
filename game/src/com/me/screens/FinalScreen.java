package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.aaction.AASprite;
import com.me.aaction.ACall;
import com.me.aaction.ADelay;
import com.me.aaction.AFadeIn;
import com.me.aaction.AFadeOut;
import com.me.aaction.AMoveBy;
import com.me.aaction.ASequence;
import com.me.aaction.ICallFunc;
import com.me.game.G;
import com.me.game.G.TAG;

public class FinalScreen implements Screen,InputProcessor {
	
	private AASprite bg;
	private AASprite fg;  
	private SpriteBatch batch;
	private Stage stg;
	
	public FinalScreen() {
		batch = new SpriteBatch();
		stg = new Stage(G.ScreenWidth, G.ScreenHeight, true, batch);
		bg=new AASprite("bg1.png");
		fg=new AASprite("final.png");
		bg.x=0;bg.y=-16;
		bg.color.r=bg.color.g=bg.color.b=0.5f;
		bg.visibleForPlayer=false;
		bg.color.a=0;
		fg.x=(G.ScreenWidth-256)/2;
		fg.y=(G.ScreenHeight/2)-1024+32;
		stg.addActor(bg);
		bg.addActor(fg);
	}
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stg.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		G.playMusic(TAG.MUSIC_BGM3);
		fg.runAction(ASequence.$(
				ADelay.$(5f),
				AMoveBy.$(45f, 0, 992)			
				));
		bg.runAction(ASequence.$(
				AFadeIn.$(1f),
				ADelay.$(60f),
				AFadeOut.$(1f),
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {
						G.playMusic(null);
						Gdx.input.setInputProcessor(null);
						G.game.setScreen(new HelloScreen());
						
					}
				})
				));

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
		stg.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		if (keycode==Input.Keys.BACK||keycode==Input.Keys.BACKSPACE){
			Gdx.input.setInputProcessor(null);
			bg.runAction(ASequence.$(
					AFadeOut.$(1f),
					ACall.$(new ICallFunc() {
						public void onCall(Object[] params) {
							G.playMusic(null);
							G.game.setScreen(new HelloScreen());
							
						}
					})
					));
		}
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
