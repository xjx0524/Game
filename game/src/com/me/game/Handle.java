package com.me.game;

import com.me.aaction.*;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Handle extends Stage {
	
	ASprite inner,outer;
	private boolean touchisdown = false;

	public Handle(float width, float height, boolean stretch, SpriteBatch batch) {
		super(width, height, stretch, batch);
		inner=new ASprite(){
			
			Texture tex;
			{
				tex=new Texture("handle_inner.png");
				originX=16;
				originY=16;
				this.x=16;
				this.y=16;
				width=height=32;
				tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
			}

			@Override
			public void draw(SpriteBatch batch, float parentAlpha) {
				draw(batch, parentAlpha,tex);				
			}

			@Override
			public Actor hit(float arg0, float arg1) {	return null;	}
			
			@Override
			public boolean touchDown(float x, float y, int pointer) {
				float d=G.dis(x, y, 32, 32);
				if (d>16){
					x-=32;
					y-=32;
					float par=16/d;
					x=x*par;
					y=y*par;
					x+=32;
					y+=32;
				}
				if (d>=8){
					if (x>y){
						if (x+y<64)
							G.hero.keyDown(Input.Keys.DPAD_DOWN);
						else
							G.hero.keyDown(Input.Keys.DPAD_RIGHT);
					}else
						if (x+y<64)
							G.hero.keyDown(Input.Keys.DPAD_LEFT);
						else
							G.hero.keyDown(Input.Keys.DPAD_UP);
				}else
					G.hero.keyUp(Input.Keys.DPAD_LEFT);
					
					
				this.x=x-16;
				this.y=y-16;				
				return true; 
			}
			@Override
			public void touchDragged(float x, float y, int pointer) {
				touchDown(x, y, pointer);
			}
			
			@Override
			public void touchUp(float x, float y, int pointer) {
				this.x=16;
				this.y=16;
				G.hero.keyUp(Input.Keys.DPAD_LEFT);
			}
		};
		
		outer=new ASprite(){
			Texture tex;
			{
				tex=new Texture("handle_outer.png");
				originX=32;
				originY=32;
				x=0;
				y=0;
				width=height=64;
				tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			}
			@Override
			public void draw(SpriteBatch batch, float parentAlpha) {				
				draw(batch, parentAlpha,tex);				
			}
			@Override
			public Actor hit(float x, float y) {return null;}
		};
		addActor(outer);
		addActor(inner);		
		inner.runAction(AForever.$(ARotateBy.$(20, 360)));
		outer.runAction(AForever.$(ARotateBy.$(20, -360)));
		
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		boolean ret=super.touchDown(x, y, pointer, button);
		Vector2 p=new Vector2();
		toStageCoordinates(x, y, p);
		x=(int)p.x;y=(int)p.y;
		if (G.dis(x, y, 32, 32)<=32||touchisdown){
			touchisdown = true;
			return inner.touchDown(x, y, pointer)||ret;			
		}
		return ret;			
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		boolean ret=super.touchDragged(x, y, pointer);
		Vector2 p=new Vector2();
		toStageCoordinates(x, y, p);
		x=(int)p.x;y=(int)p.y;		
		inner.touchDragged(x, y, pointer);
		return ret;
	}
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		boolean ret=super.touchUp(x, y, pointer, button);
		touchisdown=false;
		Vector2 p=new Vector2();
		toStageCoordinates(x, y, p);
		x=(int)p.x;y=(int)p.y;		
		inner.touchUp(x, y, pointer);
		return ret;
	}
}
