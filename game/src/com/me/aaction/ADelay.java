package com.me.aaction;

public class ADelay extends AActionInterval {
	
	@Override
	public String discribtion() { return "ADelay:duration("+getDuration()+")";}

	static public ADelay actionWithDuration(float duration){
		ADelay a=new ADelay();
		a.initWithDuration(duration);
		return a;
	}
	static public ADelay $(float duration){
		return actionWithDuration(duration);
	}

}
