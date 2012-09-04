package com.me.aaction;

public class AFadeIn extends AActionInterval {
	
	@Override
	public String discribtion() {return "AFadeIn:duration("+getDuration()+")";}
	@Override
	protected void update(float t) {
		super.update(t);
		if (t>0)
			getTarget().visibleForPlayer=true;
		getTarget().color.a=t;
	}
	
	public static AFadeIn $(float duration){
		AFadeIn a=new AFadeIn();
		a.initWithDuration(duration);
		return a;
	}

}
