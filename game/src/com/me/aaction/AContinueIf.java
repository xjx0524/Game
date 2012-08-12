package com.me.aaction;

public class AContinueIf extends AActionInstant {

	public String discribtion(){return "AContinueIf"; }
	
	Object[] params;
	private IIfFunc func;
	boolean shouldcontinue = false;
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		shouldcontinue = func.onCall(params);
		stop();
	}
	
	static public AContinueIf $(IIfFunc function,Object... params){
		AContinueIf a=new AContinueIf();
		a.params=params;
		a.func=function;
		return a;
	}
}
