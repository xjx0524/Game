package com.me.aaction;

import java.util.LinkedList;
import java.util.List;

public class ASpawn extends AActionInterval {
	
	private List<AFiniteTimeAction> list;
	@Override
	public String discribtion() { return "ASpawn";	}
	
	@Override
	protected void step(float t) {
		if (!getIsRunning()) return;
		int count = 0;
		for (AFiniteTimeAction p:list)
			if (p.getIsStoped())
				++count;
			else
				p.step(t);
		if (list.size()==count) {
			stop();
			return;
		}
		
	}

	@Override
	protected void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		for (AFiniteTimeAction p:list){
			if (p==null)
			//	Log.e("AAction","Spawn meet null action!")
			;else
				p.startWithTarget(pTarget);
		}
	}
	
	static public ASpawn actions(AFiniteTimeAction...aActions){
		ASpawn a = new ASpawn();
		a.list = new LinkedList<AFiniteTimeAction>();
		float d = 0;
		for (AFiniteTimeAction p:aActions){
			if (d<p.getDuration()) d=p.getDuration();
			a.list.add(p);			
		}
		a.initWithDuration(d);
		a.setIsRunning(false);		
		return a;		
	}
	
	
	static public ASpawn $(AFiniteTimeAction...aActions){
		return actions(aActions);
	}
	
	@Override
	public void stop() {
		//for (AAction p:list) p.stop();
		super.stop();
	}

}
