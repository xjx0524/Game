package com.me.aaction;

public class ARepeat extends AActionInterval {
	
	private int time;
	private AFiniteTimeAction action;
	private int finished;
	
	@Override
	public String discribtion() { return "ARepeat:time(+"+time+"),Action("+action.discribtion()+")";}
	
	@Override
	protected void step(float t) {
		action.step(t);
		if (action.getIsStoped())	
			if (++finished>=time||(action instanceof ASequence&&((ASequence)action).shouldbreak))	
			{
				stop();	
				return;
			}else
				action.startWithTarget(getTarget());
	}
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		finished = 0;
		super.startWithTarget(pTarget);
		action.startWithTarget(pTarget);
	}
	
	static public ARepeat $(AFiniteTimeAction action,int time){
		ARepeat a=new ARepeat();
		a.time=time;
		a.action=action;
		a.finished=0;
		a.initWithDuration(action.getDuration()*time);
		return a;		
	}
	
	static public ARepeat actionWithAction(AFiniteTimeAction action,int time){
		return $(action,time);
	}	

}
