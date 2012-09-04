package com.me.aaction;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ASequence extends AActionInterval {
	
	@Override
	public String discribtion() { return "ASequence";	}
	
	private List<AFiniteTimeAction> list;
	private Iterator<AFiniteTimeAction> iter;
	private AFiniteTimeAction curAction;
	boolean shouldbreak = false;
	
	@Override
	protected void step(float t) {
		if (!getIsRunning()) return;
		curAction.step(t);
		if (curAction.getIsStoped()){
			if ((curAction instanceof ABreakIf   &&((ABreakIf)curAction).shouldbreak)
			   ||curAction instanceof AContinueIf&&((AContinueIf)curAction).shouldcontinue)
			{
				if (curAction instanceof ABreakIf) shouldbreak=true;
				stop();					
			}else
			{
				if (iter.hasNext())	{
					curAction=iter.next();
					if (curAction==null)
						//	Log.e("AAction","Sequence meet null action!")
					;else
						curAction.startWithTarget(getTarget());
				}else 
					stop();
			}
		}
		
	}
	@Override
	protected void startWithTarget(ASprite pTarget) {
		super.startWithTarget(pTarget);
		iter=list.iterator();
		if (iter.hasNext()){
			curAction=iter.next();
		}else return;
		if (curAction==null)
		//	Log.e("AAction","Sequence meet null action!")
		;else
			curAction.startWithTarget(pTarget);
	}
	
	static public ASequence actions(AFiniteTimeAction...aActions){
		ASequence a = new ASequence();
		a.list = new LinkedList<AFiniteTimeAction>();
		float d = 0;
		for (AFiniteTimeAction p:aActions){
			d+=p.getDuration();
			a.list.add(p);			
		}
		a.initWithDuration(d);
		a.setIsRunning(false);
		
		return a;		
	}
	
	static public ASequence $(AFiniteTimeAction...aActions){
		return actions(aActions);
	}
}