package com.me.aaction;

import com.badlogic.gdx.math.Vector2;

public class AParabola extends AActionInterval {
	
	private float sx,gx,sy,gy,top,k,b;
	
	@Override
	public String discribtion() {	return "AParabola:("+sx+","+sy+"),("+gx+","+gy+"),top:"+top; }
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		sx=pTarget.x;
		sy=pTarget.y;
		b=(float)Math.sqrt(top-sy);
		k=(float)(Math.sqrt(top-gy)+b);	
	}

	static public AParabola $(float duration,float topy,Vector2 targetPoint){
		AParabola a=new AParabola();
		a.initWithDuration(duration);
		a.gy=targetPoint.y;
		a.gx=targetPoint.x;
		a.top=topy;
		return a;	
	}
	
	@Override
	protected void update(float t) {
		super.update(t);
		if (t<1){
			getTarget().x=sx+(gx-sx)*t;
			getTarget().y=(float)(top-(k*t-b)*(k*t-b));
		}else
		{
			getTarget().x=gx;
			getTarget().y=gy;
		}
	}
	

}
