package com.me.aaction;

import java.util.LinkedList;
import java.util.List;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ASprite extends Actor {
	List<AAction> actions;
	List<AAction> actionsWillRemove;
	List<AAction> actionsWillAdd;
	void removeAction(AAction a){
		actionsWillRemove.add(a);
	}
	void addAction(AAction a){
		actionsWillAdd.add(a);
	}
	
	{
		actions=new LinkedList<AAction>();
		actionsWillRemove=new LinkedList<AAction>();
		actionsWillAdd=new LinkedList<AAction>();
	}
	
	
	public void runAction(AAction action){
		if (action==null) return;
		action.startWithTarget(this);		
	}
	
	public void stopAction(AAction action){
		if (action==null) return;
		action.stop();
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
	}
	
	public void draw(SpriteBatch batch, float parentAlpha,Texture tex) {
		doAlter(batch,parentAlpha);
		batch.draw (tex, x, y, originX, originY, width, height, scaleX,
				 scaleY, rotation, 0, 0, tex.getWidth(),  tex.getHeight(),  false,  false);
		//Log.i("", "DrawParam:"+ x+","+ y+"," +originX+","+ originY+","+ width+","+ height+","+ scaleX+",\n"+
			//	 scaleY+","+ rotation+","+ 0+","+ 0+","+ tex.getWidth()+","+  tex.getHeight()+","+  false+"," + false);
	}
	
	private void doAlter(SpriteBatch batch, float parentAlpha) {
		for (AAction p:actions)	p.step(Gdx.graphics.getDeltaTime());
		for (AAction p:actionsWillRemove)actions.remove(p);
		for (AAction p:actionsWillAdd)actions.add(p);		
		actionsWillRemove.clear();
		actionsWillAdd.clear();		
	}

}


