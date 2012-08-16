package com.me.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class SkillButton extends Actor {
	
	

	static enum TYPE{AUTO,SELECT,ACTIVE};
	private boolean actived;
	private TextureRegion tex[]=new TextureRegion[2];
	public final G.TAG tag;
	public final TYPE type;
	
	public SkillButton(G.TAG tag,TYPE type) {		
		super();
		tex[0]=G.sbtp.getTexture(tag,true);
		tex[1]=G.sbtp.getTexture(tag,false);
		this.tag=tag;
		this.type=type;
		if (type==TYPE.AUTO)actived=true;else actived=false; 
	}
	
	
	
	boolean getIsactived(){return actived;}
	
	void active(boolean actived){
		if (G.log) System.out.println(""+tag+" actived is "+actived);
		this.actived=actived;
		if (actived){
			Skill.cast(tag);
		}
	}
	
	private void select(boolean actived) {
		if (actived)
			((SkillButtonGroup)getStage()).select(this);
		else
			active(false);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (actived)
			batch.draw(tex[0], x, y);
		else
			batch.draw(tex[1], x, y);
	}

	@Override
	public Actor hit(float x, float y) {
		return x>=0&&x<32&&y>=0&&y<32?this:null;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer) {
		switch(type){
		case AUTO:return false;
		case SELECT:select(!actived);return true;
		case ACTIVE:active(true);return true;
		}
		return false;
	}

	@Override
	public void touchUp(float x, float y, int pointer) {
		switch(type){
		case AUTO:return;
		case SELECT:return;
		case ACTIVE:active(false);return;
		}
		return;
	}
	
	
	

}
