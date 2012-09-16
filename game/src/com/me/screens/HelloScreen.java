package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.me.aaction.AASprite;
import com.me.aaction.ACall;
import com.me.aaction.AFadeIn;
import com.me.aaction.AFadeOut;
import com.me.aaction.AMoveBy;
import com.me.aaction.AScaleTo;
import com.me.aaction.ASequence;
import com.me.aaction.ASprite;
import com.me.aaction.ICallFunc;
import com.me.game.G;
import com.me.game.G.TAG;

public class HelloScreen implements Screen,InputProcessor {
	
	Stage stg;
	AASprite bg;
	AASprite title;
	MyButton start;
	MyButton end;
	ASprite sound;
	
	public HelloScreen() {
		stg=new Stage(G.ScreenWidth, G.ScreenHeight, true);
		bg=new AASprite("bg1.png");
		bg.x=0;bg.y=-16;
		bg.width=512;bg.height=256;
		bg.color.a=0;bg.visibleForPlayer=false;
		title=new AASprite("game_title.png");
		start=new MyButton("start.png");
		end=new MyButton("end.png");
		
		stg.addActor(bg);
		
		bg.addActor(title);
		title.x=32;title.y=240;
		
		bg.addActor(start);
		start.x=-64;start.y=104;
		start.originX=96;start.originY=32;

		bg.addActor(end);
		end.x=320;end.y=48;
		end.originX=96;end.originY=32;
		
		
		start.listener=new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				G.playSound(TAG.SOUND_JUMP);
				Gdx.input.setInputProcessor(null);
				bg.runAction(ASequence.$(
						AFadeOut.$(0.3f),
						ACall.$(new ICallFunc() {
							@Override
							public void onCall(Object[] params) {
								G.game.setScreen(new SelectScreen());								
							}
						})
						));
				
			}
		};
		
		end.listener=new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				G.playSound(TAG.SOUND_JUMP);
				Gdx.app.exit();				
			}
		};
		
		bg.addActor(new ASprite() {
			{x=280;y=220;}
			public void setTextureRegion(TextureRegion textureRegion) {}
			public TextureRegion getTextureRegion() {return null;}
			public void draw(SpriteBatch batch, float parentAlpha) {batch.draw(G.sbtp.getTexture(TAG.SKILL_MUSIC, G.soundOn), x, y);	}
			public boolean touchDown(float x, float y, int pointer) {G.SoundOn(!G.soundOn);	G.game.save();return true;	}
			public Actor hit(float x, float y) {
				if (x>=0&&y>=0&&x<32&&y<32) 
					return this;
				return super.hit(x, y);
			}			
		});
		
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
		bg.runAction(AFadeIn.$(1f));
		title.runAction(AMoveBy.$(1, 0, -72));
		start.runAction(AMoveBy.$(1, 224, 0));
		end  .runAction(AMoveBy.$(1,-160, 0));
		Gdx.input.setInputProcessor(this);
		G.playMusic(TAG.MUSIC_BGM1);
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
		stg.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode==Input.Keys.BACK){
			G.game.fin=true;
			Gdx.app.exit();
		}
		return true;
	}

	public boolean keyUp(int keycode) {return false;}
	public boolean keyTyped(char character) {return false;}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return stg.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return stg.touchUp(x, y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return stg.touchDragged(x, y, pointer);
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return stg.touchMoved(x, y);
	}

	public boolean scrolled(int amount) {return false;}
	
	

}


class MyButton extends AASprite{
	
	ClickListener listener = null;

	public MyButton(String filename) {
		super(filename);
	}

	@Override
	public TextureRegion getTextureRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTextureRegion(TextureRegion textureRegion) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Actor hit(float x, float y) {
		if (x>0&&y>0&&x<width&&y<height){
			return this;
		}else
			return super.hit(x, y);
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer) {
		runAction(AScaleTo.$(0.1f, 1.2f, 1.2f));
		return true;
	}
	
	@Override
	public void touchUp(float x, float y, int pointer) {
		runAction(AScaleTo.$(0.1f, 1f, 1f));
		if (listener!=null) listener.click(this, x, y);
		super.touchUp(x, y, pointer);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		return false;
	}
}
