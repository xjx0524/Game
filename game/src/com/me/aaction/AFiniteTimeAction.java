package com.me.aaction;


public class AFiniteTimeAction extends AAction {
	
	private float mduration;
	@Override
	public String discribtion() { return "AFiniteTimeAction";	}
	
	public float getDuration(){return mduration;}
	public void setDuration(float duration) { mduration = duration; }

}
