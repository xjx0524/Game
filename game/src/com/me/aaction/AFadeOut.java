package com.me.aaction;

public class AFadeOut extends AActionInterval {
	
	public String discribtion() {return "AFadeOut:duration("+getDuration()+")";}
	
	@Override
	protected void update(float t) {
		super.update(t);
		if (t==1){
			getTarget().visibleForPlayer=false;
		}else
			getTarget().color.a=1-t;
	}
	
	public static AFadeOut $(float duration){
		AFadeOut a=new AFadeOut();
		a.initWithDuration(duration);
		return a;
	}

}