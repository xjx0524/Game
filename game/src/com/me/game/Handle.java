package com.me.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.aaction.AForever;
import com.me.aaction.ARotateBy;
import com.me.aaction.ASprite;

public class Handle extends Stage {
	
	ASprite inner,outer;
	private boolean handleHolded = false;
	final float scale = 1.5f;
	float ox,oy;

	public Handle(float width, float height, boolean stretch, SpriteBatch batch) {
		super(width, height, stretch, batch);
		inner=new ASprite(){
			
			Texture tex;
			{
				tex=new Texture("handle_inner.png");
				originX=16;
				originY=16;
				this.x=32;
				this.y=32;
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
				float d=G.dis(x, y, ox, oy);
				if (d>ox-16){
					x-=ox;
					y-=oy;
					float par=(ox-16)/d;
					x=x*par;
					y=y*par;
					x+=ox;
					y+=oy;
				}
				if (d>=((ox-16)/2)){
					if (x>y){
						if (x+y<outer.width)
							G.hero.keyDown(Input.Keys.DPAD_DOWN);
						else
							G.hero.keyDown(Input.Keys.DPAD_RIGHT);
					}else
						if (x+y<outer.width)
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
				this.x=ox-16;
				this.y=oy-16;
				G.hero.keyUp(Input.Keys.DPAD_LEFT);
			}

			public TextureRegion getTextureRegion() {return null;	}
			public void setTextureRegion(TextureRegion textureRegion) {}
		};
		
		outer=new ASprite(){
			public TextureRegion getTextureRegion() {return null;	}
			public void setTextureRegion(TextureRegion textureRegion) {}
			Texture tex;
			{
				tex=new Texture("handle_outer.png");
				originX=32*scale;
				originY=32*scale;
				x=0;
				y=0;
				width=height=64*scale;
				tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			}
			@Override
			public void draw(SpriteBatch batch, float parentAlpha) {				
				draw(batch, parentAlpha,tex);				
			}
			@Override
			public Actor hit(float x, float y) {return null;}
		};
		
		ox=outer.width/2;oy=outer.height/2;
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
		if (G.dis(x, y, ox, oy)<=ox||handleHolded){
			handleHolded = true;
			return inner.touchDown(x, y, pointer)||ret;			
		}
		return ret;			
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		boolean ret=super.touchDragged(x, y, pointer);
		if (handleHolded){
			Vector2 p=new Vector2();
			toStageCoordinates(x, y, p);
			x=(int)p.x;y=(int)p.y;		
			inner.touchDragged(x, y, pointer);
		}
		return ret;
	}
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		boolean ret=super.touchUp(x, y, pointer, button);
		handleHolded=false;
		Vector2 p=new Vector2();
		toStageCoordinates(x, y, p);
		x=(int)p.x;y=(int)p.y;		
		inner.touchUp(x, y, pointer);
		return ret;
	}
}
