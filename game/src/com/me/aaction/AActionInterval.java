package com.me.aaction;

public class AActionInterval extends AFiniteTimeAction {
	
	float elapsed;  
	
	@Override
	public String discribtion() { return "AActionInterval";	}

	@Override
	protected void step(float t) {
		if (!getIsRunning()) return;
		elapsed+=t;
		if (elapsed>=getDuration())
		{
			update(1.0f);
			stop();
			return;
		}
		float tt=(float)(((double)elapsed)/getDuration());	
		update(tt);
	}
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		elapsed = 0;
		super.startWithTarget(pTarget);
	}
	
	protected void initWithDuration(float a){
		setDuration(a);
		elapsed = 0;
	}

}
