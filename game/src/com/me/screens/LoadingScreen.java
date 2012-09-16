package com.me.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.aaction.ACall;
import com.me.aaction.AFadeIn;
import com.me.aaction.AFadeOut;
import com.me.aaction.ASequence;
import com.me.aaction.ASprite;
import com.me.aaction.ICallFunc;
import com.me.game.G;
import com.me.game.GameScreen;

public class LoadingScreen implements Screen {
	
	private int mission;
	private Screen gs;
	private SpriteBatch batch;
	private Stage stage;
	private ASprite s;
	final int max = 18;
	
	public LoadingScreen(final int mission) {
		G.loadfinished=false;
		this.mission=mission;
		if (mission>G.maxMission){
			G.maxMission=mission;
			G.game.save();
		}
		//tex=new Texture("bg2.png");
		batch=new SpriteBatch();
		stage = new Stage(G.ScreenWidth,G.ScreenHeight,true,batch);
		s=new ASprite() {
			private Texture tex = new Texture("bg2.png");
			{
				width=512;height=256;
				x=0;y=-16;
				visibleForPlayer=false;
				color.a=0;
			}
			public void setTextureRegion(TextureRegion textureRegion) {	}
			public TextureRegion getTextureRegion() {return null;}
			public void draw(SpriteBatch batch, float parentAlpha) {super.draw(batch, parentAlpha,tex);	}
		};
		
		stage.addActor(s);
		
		if (mission<=max){
			Image img=new Image(new Texture("loading.png"));
			img.x=120;img.y=150;
			s.addActor(img);
		}else{
			Image img=new Image(new Texture("congratulatoins.png"));
			img.x=120;img.y=150;
			s.addActor(img);
		}
			
	}

	@Override
	public void render(float delta) {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.draw();
		if (G.loadfinished){
			G.game.setScreen(gs);
			s.remove();
			s=null;
			stage.dispose();
			stage=null;
			batch.dispose();
			batch=null;
		}
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {
		s.runAction(ASequence.$(
				AFadeIn.$(0.3f),
				ACall.$(
				new ICallFunc() {
					public void onCall(Object[] params) {
						if (mission<=max)
							gs=new GameScreen(mission);
						else
							gs=new FinalScreen();
						G.loadfinished=false;
					}
				}),
				AFadeOut.$(0.3f),
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {
						G.loadfinished=true;						
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
		if (batch!=null)
			batch.dispose();
		if (stage!=null) 
			stage.dispose();
	}
}