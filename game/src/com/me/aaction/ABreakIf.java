package com.me.aaction;

public class ABreakIf extends AActionInstant {

	public String discribtion(){return "ABreakIf"; }
	
	Object[] params;
	private IIfFunc func;
	boolean shouldbreak = false;
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		shouldbreak = func.onCall(params);
		stop();
	}
	
	static public ABreakIf $(IIfFunc function,Object... params){
		ABreakIf a=new ABreakIf();
		a.params=params;
		a.func=function;
		return a;
	}

}
