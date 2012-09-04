package com.me.aaction;

import com.me.game.G;


public class AEaseOutIn extends AActionInterval {
	
	private AActionInterval action;
	private float k;
	double lttt;
	
	@Override
	public String discribtion() {
		return "AEaseOutIn:action("+action+")";
	}
	
	@Override
	protected void step(float t) {
		if (!getIsRunning()) return;
		elapsed+=t;
		if (elapsed>=getDuration())
		{
			action.step(t);
			if (action.getIsStoped()) stop();
			return;
		}
		double tt=elapsed/getDuration();
		double o=(tt-k)/(1-k);
		double ttt=0.5*(o*o*o+k/(1-k));
		action.step((float)(ttt*getDuration()-lttt));
		//G.Log("tt:"+tt+",ttt:"+ttt+",l:"+(ttt*getDuration()-lttt));
		lttt=ttt*getDuration();
		
	}
	
	@Override
	protected void startWithTarget(ASprite pTarget) {
		lttt=0;
		action.startWithTarget(pTarget);
		super.startWithTarget(pTarget);
	}
	
	public static AEaseOutIn $(AActionInterval action) {
		AEaseOutIn a=new AEaseOutIn();
		a.initWithDuration(action.getDuration());
		a.k=0.5f;
		a.action=action;
		return a;
	}
	
	public static AEaseOutIn $(float topTime,AActionInterval action) {
		AEaseOutIn a=new AEaseOutIn();
		a.initWithDuration(action.getDuration());
		a.k=topTime;
		a.action=action;
		return a;
	}

}
