package com.me.aaction;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class ASprite extends Group {
	protected List<AAction> actions;
	List<AAction> actionsWillRemove;
	List<AAction> actionsWillAdd;
	public boolean visibleForPlayer = true;
	
	
	{
		actions=new LinkedList<AAction>();
		actionsWillRemove=new LinkedList<AAction>();
		actionsWillAdd=new LinkedList<AAction>();
	}
	
	public void removeAction(AAction a){
		actionsWillRemove.add(a);
	}
	public void addAction(AAction a){
		actionsWillAdd.add(a);
	}
	
	public void runAction(AAction action){
		if (action==null) return;
		action.isRoot=true;
		action.startWithTarget(this);
		//actions.add(action);
	}
	
	public void stopAction(AAction action){
		if (action==null) return;
		action.stop();
	}
	
	public void stopAllActions(){
		for (AAction p:actions)	p.stop();
		actions.clear();
	}
	
	public void stopActionByTag(Object tag){
		AAction action=null;
		for (AAction p:actions){
			if (p.getTag()==tag)
			{
				action = p;
				break;
			}
		}
		stopAction(action);
	}
	
	public boolean isActionRunning(AAction action){
		if (action==null) return false;
		for (AAction p:actions)
			if (p == action)
			{
				return true;
			}
		return false;
	}
	
	public boolean isActionRunningByTag(Object tag){
		if (tag==null) return false;
		for (AAction p:actions)
			if (p.getTag() == tag)
			{
				return true;
			}
		return false;
	}
	
	public boolean isRunning(){
		return !actions.isEmpty();
	}
	
	int count;
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		doAlter(batch,parentAlpha);
		super.draw(batch, color.a*parentAlpha);
	}
	
	public void draw(SpriteBatch batch, float parentAlpha,Texture tex) {
		doAlter(batch,parentAlpha);
		if (!visibleForPlayer) return;
		batch.setColor(color.r,color.g,color.b,color.a*parentAlpha);
		batch.draw (tex, x, y, originX, originY, width, height, scaleX,
				 scaleY, rotation, 0, 0, tex.getWidth(),  tex.getHeight(),  false,  false);
		super.draw(batch, color.a*parentAlpha);
		//Log.i("", "DrawParam:"+ x+","+ y+"," +originX+","+ originY+","+ width+","+ height+","+ scaleX+",\n"+
			//	 scaleY+","+ rotation+","+ 0+","+ 0+","+ tex.getWidth()+","+  tex.getHeight()+","+  false+"," + false);
	}
	
	private void doAlter(SpriteBatch batch, float parentAlpha) {
		for (AAction p:actions)	if (p.isRoot) p.step(Gdx.graphics.getDeltaTime());
		for (AAction p:actionsWillRemove)actions.remove(p);
		for (AAction p:actionsWillAdd)actions.add(p);		
		actionsWillRemove.clear();
		actionsWillAdd.clear();		
	}
	
	public abstract TextureRegion getTextureRegion();
	public abstract void setTextureRegion(TextureRegion textureRegion);
}


