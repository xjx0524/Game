package com.me.aaction;

public class ARotateBy extends AActionInterval {
	
	@Override
	public String discribtion() { return "ARotateBy:StartAngle("+sr+"),DeltaAngle("+gr+")";}
	
	private float sr,gr;
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		sr=pTarget.rotation;
		super.startWithTarget(pTarget);
	}
	
	@Override
	protected void update(float t) {
		super.update(t);
		getTarget().rotation=sr+gr*t;
	}
	
	static public ARotateBy actionWithDuration(float duration,float deltaAngle){
		ARotateBy a=new ARotateBy();
		a.gr=deltaAngle;
		a.initWithDuration(duration);
		return a;		
	}
	
	static public ARotateBy $(float duration,float deltaAngle){ return actionWithDuration(duration, deltaAngle);}

}
