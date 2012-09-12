package com.me.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.aaction.*;

public class Dialog extends ASprite {
	
	private Texture tex;
	private TextureRegion[][] texs;
	private Button close;
	private Button back;
	private Image next;

	@Override
	public TextureRegion getTextureRegion() {
		return null;
	}
	
	public Dialog(String text) {
		super();
		y=40;
		x=60;
		width=256;
		height=256;
		tex=new Texture("dialog.png");
		texs=TextureRegion.split(new Texture("dialog_button.png"),32,32);
		close=new Button(texs[0][5],texs[0][4]);
		close.x=160+x;close.y=120+y;
		next=new Image(texs[0][3]);
		next.x=160+x;next.y=12+y;
		back=new Button(texs[0][1],texs[0][0]);
		back.x=4+x;back.y=4+y;
		
		close.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y)  {
				hide();
			}
		});
	}

	@Override
	public void setTextureRegion(TextureRegion textureRegion) {		
	}

	@Override
	public Actor hit(float x, float y) {
		Vector2 v=new Vector2(x,y);
		toLocalCoordinates(v);
		Actor tmp;
		if ((tmp=close.hit(v.x, v.y))!=null) return tmp; else
			if ((back!=null)&&(tmp=back.hit(v.x, v.y))!=null) return tmp; else
				if (next!=null) return next; else return this;
			
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha,tex);
		close.draw(batch, color.a);
		if (back!=null) back.draw(batch, color.a);
		if (next!=null) next.draw(batch, color.a);
	}
	
	public void show(){
		++G.lockInput;
		G.dialogstg=new Stage(G.ScreenWidth, G.ScreenHeight,true);
		G.dialogstg.addActor(this);
		runAction(AFadeIn.$(1));
	}
	
	private void hide(){
		runAction(ASequence.$(
				AFadeIn.$(1),
				ACall.$(new ICallFunc() {					
					@Override
					public void onCall(Object[] params) {
						remove();
						G.dialogstg.dispose();
						G.dialogstg=null;
						--G.lockInput;						
					}
				})));
	}

}
