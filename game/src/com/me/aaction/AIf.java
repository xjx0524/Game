package com.me.aaction;

public class AIf extends AActionInterval {
	
	private AFiniteTimeAction a1,a2,a;
	private IIfFunc f;
	private Object[] params;
	
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		if (f.onCall(params))	a=a1;
		else a=a2;
		initWithDuration(a.getDuration());
		a.startWithTarget(pTarget);		
	}
	
	@Override
	protected void step(float t) {
		if (!getIsRunning()) return;
			if (a.getIsStoped()){
				stop();
				return;
			}
			else
				a.step(t);
	}

	public static AIf $(IIfFunc f,AFiniteTimeAction a1,AFiniteTimeAction a2,Object...params){
		AIf a=new AIf();
		a.f=f;
		a.a1=a1;a.a2=a2;
		a.params=params;
		return a;
	}
}
