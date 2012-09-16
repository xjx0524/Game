package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.me.aaction.AASprite;
import com.me.aaction.ACall;
import com.me.aaction.ADelay;
import com.me.aaction.AFadeIn;
import com.me.aaction.AFadeOut;
import com.me.aaction.ARotateBy;
import com.me.aaction.AScaleTo;
import com.me.aaction.ASequence;
import com.me.aaction.ASpawn;
import com.me.aaction.ASprite;
import com.me.aaction.ICallFunc;
import com.me.game.G;
import com.me.game.G.TAG;
import com.me.game.GameScreen;

public class SelectScreen implements Screen,InputProcessor {
	
	Stage stg;
	SpriteBatch batch;
	AASprite image;
	Button button[][]=new Button[3][6];
	Texture b=new Texture("select.png"),bl=new Texture("select_lock.png");
	final boolean debug = false;
	
	@SuppressWarnings("unused")
	public SelectScreen() {
		batch=new SpriteBatch();
		stg=new Stage(G.ScreenWidth,G.ScreenHeight,true,batch);
		image=new AASprite("bg2.png");
		image.x=0;image.y=-16;
		image.color.a=0;
		image.visibleForPlayer=false;
		stg.addActor(image);
		for (int i=0;i<3;++i)
			for (int j=0;j<6;++j){
				if (i*6+j<G.maxMission||debug){
					button[i][j]=new Button(b);
					button[i][j].setListener(new SelectListener(i*6+j+1));
				}else{
					button[i][j]=new Button(bl);
				}
				button[i][j].tag=i*6+j+1;
				button[i][j].x=64+40*j;
				button[i][j].y=196-64*i;
				image.addActor(button[i][j]);
				com.badlogic.gdx.scenes.scene2d.ui.Button button=
						new com.badlogic.gdx.scenes.scene2d.ui.Button(G.sbtp.getTexture(TAG.SKILL_RESTART, true));
				button.x=8;button.y=24;
				button.setClickListener(new ClickListener() {
					public void click(Actor actor, float x, float y) {
						G.game.reset();
						reset();
					}
				});
				image.addActor(button);
				
				button=new com.badlogic.gdx.scenes.scene2d.ui.Button(
						G.sbtp.getTexture(TAG.BUTTON_BACK_BIG, false),
						G.sbtp.getTexture(TAG.BUTTON_BACK_BIG, true)
						);
				button.x=8;button.y=200;
				button.setClickListener(new ClickListener() {
					public void click(Actor actor, float x, float y) {
						G.playSound(TAG.SOUND_JUMP);
						Gdx.input.setInputProcessor(null);
						image.runAction(ASequence.$(
								AFadeOut.$(0.3f),
								ACall.$(new ICallFunc() {
									public void onCall(Object[] params) {
										G.game.setScreen(new HelloScreen());
									}
								})
								));
					}
				});
				
				image.addActor(button);
			}
		Gdx.input.setInputProcessor(this);
	}

	public void show() {
		G.playMusic(TAG.MUSIC_BGM1);
		image.runAction(AFadeIn.$(0.3f));
		for (int i=0;i<3;++i)
			for (int j=0;j<6;++j){
				final Button bn=button[i][j];
				bn.scaleX=bn.scaleY=0;
				bn.runAction(ASequence.$(
						ADelay.$((float)((i+j)*0.05)),
						ASpawn.$(
								AFadeIn.$(0.5f),
								ARotateBy.$(0.5f, -360),
								AScaleTo.$(0.5f,1,1)
						)));
			}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stg.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	public void reset() {
		G.playSound(TAG.SOUND_JUMP);
		for (int i=0;i<3;++i)
			for (int j=0;j<6;++j){
				final Button bn=button[i][j];
				bn.scaleX=bn.scaleY=1;
				bn.runAction(ASequence.$(
						ADelay.$((float)((i+j)*0.05)),
						ASpawn.$(
								AFadeOut.$(0.5f),
								ARotateBy.$(0.5f, -360),
								AScaleTo.$(0.5f,0,0)
						),
						ACall.$(new ICallFunc() {
							public void onCall(Object[] params) {
								if (bn.tag<=G.maxMission){
									bn.tex=b;
									bn.setListener(new SelectListener(bn.tag));
								}else {
									bn.tex=bl;
									bn.setListener(null);
								}
							}
						}),
						//ADelay.$((float)((i+j)*0.05)),
						ASpawn.$(
								AFadeIn.$(0.5f),
								ARotateBy.$(0.5f, -360),
								AScaleTo.$(0.5f,1,1)
						)));
			}
	}

	@Override
	public void hide() {
		

	}

	@Override
	public void pause() {
	

	}

	@Override
	public void resume() {
		

	}

	@Override
	public void dispose() {
		batch.dispose();
		stg.draw();
	}

	

	@Override
	public boolean keyDown(int keycode) {
		if (keycode==Input.Keys.BACK||keycode==Input.Keys.BACKSPACE){
			Gdx.input.setInputProcessor(null);
			G.game.setScreen(new HelloScreen());
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

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

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	class SelectListener implements ClickListener{
		private int tag;
		public SelectListener(int tag) {
			this.tag=tag;
		}
		@Override
		public void click(Actor actor, float x, float y) {
			G.playSound(TAG.SOUND_PEDAL);
			
			AASprite img=new AASprite("loading.png");
			img.x=120;img.y=150;
			img.visibleForPlayer=false;
			image.addActor(img);
			
			for (int i=0;i<3;++i)
				for (int j=0;j<6;++j){
					final Button bn=SelectScreen.this.button[i][j];
					bn.scaleX=bn.scaleY=1;
					bn.runAction(ASequence.$(
							ADelay.$((float)((i+j)*0.05)),
							ASpawn.$(
									AFadeOut.$(0.5f),
									ARotateBy.$(0.5f, -360),
									AScaleTo.$(0.5f,0,0)
							)
						));
				}

			img.runAction(AFadeIn.$(0.5f));
			image.runAction(ASequence.$(
					ADelay.$(0.5f),
					AFadeOut.$(0.3f),
					ACall.$(new ICallFunc() {
						public void onCall(Object[] params) {
							G.game.setScreen(new GameScreen(tag));
						}
					})
					));
			//G.game.setScreen(new GameScreen(tag));
		}
	}
}





class Button extends ASprite{
	
	Texture tex;
	int tag;
	
	ClickListener listener = null;
	
	Button(String s){
		tex=new Texture(s);
		originX=originY=16;
		height=width=32;
	}

	public Button(Texture t) {
		tex=t;
		originX=originY=16;
		height=width=32;
	}

	@Override
	public TextureRegion getTextureRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTextureRegion(TextureRegion textureRegion) {
		tex=textureRegion.getTexture();		
	}
	
	public void setListener(ClickListener listener) {
		this.listener = listener;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer) {
		if (listener!=null) listener.click(this, x, y);
		return true;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha, tex);
	}
	
}
