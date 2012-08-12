package com.me.aaction;

public class AMoveBy extends AActionInterval {
	
	@Override
	public String discribtion() { return "AMoveBy:StartPoint("+sx+","+sy+"),Vector("+gx+","+gy+"),Duration("+getDuration()+")";	}
	
	private float gx;
	private float gy;
	private float sx;
	private float sy;
	
	static public AMoveBy actionWithDuration(float duration,float x, float y)
	{
		AMoveBy a=new AMoveBy();
		a.gx=(int)x;a.gy=(int)y;
		a.initWithDuration(duration);
		return a;
	}

	static public AMoveBy $(float duration,float x, float y){return actionWithDuration(duration, x,y);}
	static public AMoveBy $(float duration,int x, int y){return actionWithDuration(duration, x,y);}
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		sx=pTarget.x;
		sy=pTarget.y;
		super.startWithTarget(pTarget);
	}
	
	@Override
	protected void update(float t) {
		getTarget().x=sx+gx*t;
		getTarget().y=sy+gy*t;
	}

}
