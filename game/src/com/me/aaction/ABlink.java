package com.me.aaction;

public class ABlink extends AActionInterval {
	
	private float rate;
	@Override
	public String discribtion() {return "ABlink:Rate("+rate+"),Duration("+getDuration()+")";}
	
	@Override
	protected void update(float t) {
		super.update(t);
		if (t==1){
			getTarget().visibleForPlayer=true;
			return;
		}
		float k=t*rate;
		getTarget().visibleForPlayer=(k-(int)k)>=0.5;
	}
	
	public static ABlink $(float duration,float rate){
		ABlink a=new ABlink();
		a.initWithDuration(duration);
		a.rate=rate*duration;
		return a;
	}

}
