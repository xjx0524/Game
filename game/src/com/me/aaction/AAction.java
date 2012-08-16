package com.me.aaction;

import com.me.game.G;

public class AAction {
	public static boolean showlog = true;
	
	private ASprite mTarget;
	private Object mTag = null;
	private boolean running;
	private boolean stoped = false; 
	public String discribtion(){return "AAction"; }
	
	
	protected void startWithTarget(ASprite pTarget){
		mTarget=pTarget;
		mTarget.addAction(this);
		running=true;
		stoped = false;
		//if (G.log)	System.out.println(mTarget+"Start: "+discribtion());
	}
	
	public void stop(){
		running = false;
		mTarget.removeAction(this);
		stoped = true;
		//if (G.log)	System.out.println(mTarget+ "Stop : "+discribtion());
	}
	
	public void setTag(Object tag){ mTag = tag; }
	public Object getTag(){ return mTag; }
	public void setTarget(ASprite pTarget) { mTarget=pTarget; }
	public ASprite getTarget(){ return mTarget; }
	public boolean getIsRunning(){ return running;}
	public void setIsRunning(boolean b){running=b;} 
	public boolean getIsStoped(){return stoped;}
	
	protected void step(float t){};
	protected void update(float t){};
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();		
	}
	
	
}
