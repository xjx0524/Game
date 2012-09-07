package com.me.aaction;

public class AForever extends AActionInterval {
	
	private AFiniteTimeAction action;
	
	@Override
	public String discribtion() { return "AForever:action("+action.discribtion()+")";	}
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		action.startWithTarget(pTarget);
	}
	
	@Override
	protected void step(float t) {
		action.step(t);
		if (action.getIsStoped())
			if (action instanceof ASequence&&((ASequence)action).shouldbreak||action instanceof ABreakIf&&((ABreakIf)action).shouldbreak)
				stop();
			else
				action.startWithTarget(getTarget());
	}
	
	static public AForever $(AFiniteTimeAction action){
		AForever a = new AForever();
		a.action=action;
		return a;
	}
	
	@Override
	public void stop() {
		//action.stop();
		super.stop();
	}

}
