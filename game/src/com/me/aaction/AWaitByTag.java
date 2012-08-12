package com.me.aaction;

public class AWaitByTag extends AActionInterval {
	
	private Object tag;
	@Override
	public String discribtion() { return "AWaitByTag";	}
	
	@Override
	protected void update(float t) {
		super.update(t);
		if (!getTarget().isActionRunningByTag(tag))
			stop();	
	}
	@Override
	protected void step(float t) {
		if (!getIsRunning()) return;
		update(1);
	}
	
	static public AWaitByTag actionWithTag(Object tag){
		AWaitByTag a=new AWaitByTag();
		a.initWithDuration(-1);
		a.tag=tag;
		return a;		
	}

}
