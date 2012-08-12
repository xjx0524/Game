package com.me.aaction;

public class ACall extends AActionInstant {
	public String discribtion(){return "ACall"; }
	
	Object[] params;
	private ICallFunc func;
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		func.onCall(params);
		stop();
	}
	
	static public ACall $(ICallFunc function,Object... params){
		ACall a=new ACall();
		a.params=params;
		a.func=function;
		return a;
	}

}
