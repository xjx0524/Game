package com.me.aaction;


public class AScaleTo extends AActionInterval {
	
	public String discribtion() {return "AScaleTo:duration("+getDuration()+"),rate("+ratex+","+ratey+")";}
	
	private float ratex,ratey;
	private float startratex,startratey;
	private float deltaratex,deltaratey;
	
	@Override
	public void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		startratex=getTarget().scaleX;
		startratey=getTarget().scaleY;
		deltaratex=ratex-startratex;
		deltaratey=ratey-startratey;
	}
	
	@Override
	protected void update(float t) {
		super.update(t);
		getTarget().scaleX=startratex+t*deltaratex;
		getTarget().scaleY=startratey+t*deltaratey;
	}
	
	static public AScaleTo $(float duration,float x,float y) {
		AScaleTo a=new AScaleTo();
		a.initWithDuration(duration);
		a.ratex=x;
		a.ratey=y;
		return a;
	}
}
