package com.me.aaction;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AAnimate extends AActionInterval {
	
	private Animation animation;
	
	@Override
	public String discribtion() { return "AActionInterval";}
	
	@Override
	protected void update(float t) {
		super.update(t);
		getTarget().setTextureRegion(animation.getKeyFrame(getDuration()*t));
	}
	
	public static AAnimate $(Animation animation){
		AAnimate a=new AAnimate();
		a.initWithDuration(animation.animationDuration);
		a.animation=animation;
		return a;
	}

}
