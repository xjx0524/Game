package com.me.game;

import com.me.inerface.IGSkill;

public class Skill implements IGSkill {
	
	private Object index;
	private Object[] params;
	private Hero hero;
	
	Skill(){
		super();
		this.index=G.TAG.SKILL_NULL;
		this.params=null;
		hero=G.hero;
	}
	
	Skill(G.TAG index,Object...params){
		super();
		this.index=index;
		this.params=params;
		hero=null;
	}
	
	Skill(G.TAG index,String[] params){
		super();
		this.index=index;
		this.params=params;
		hero=null;
		parse();
	}
	
	private void parse(){
		switch ((G.TAG)index){
		case SKILL_PUSH:
		case SKILL_MOVE:params[0]=G.TAG.valueOf((String)params[0]);break;		
		}
	}
	
	public Skill generate(G.TAG gentag){
		if (hero==null) hero=G.hero;
		index=hero.skillindex;
		switch (gentag){
		case GEN_STAY:{
			switch(hero.skillindex){
			default:{
				index=G.TAG.SKILL_MOVE;
				params=new Object[]{G.parseDirection(hero.curdirection)};
				}break;
			}			
		}break;
		case GEN_PUSH:{
			if (G.skillisactived(G.TAG.SKILL_PUSH))
			index=G.TAG.SKILL_PUSH;
			switch(hero.curdirection){
				case 0:params=new Object[]{G.TAG.DIR_DOWN};break;
				case 1:params=new Object[]{G.TAG.DIR_LEFT};break;
				case 2:params=new Object[]{G.TAG.DIR_RIGHT};break;
				case 3:params=new Object[]{G.TAG.DIR_UP};break;
			}
		}break;
		}
		return this;
	}

	 
	public Object getIndex() {
		return index;
	}

	 
	public Object[] getParams() {
		return params;
	}

	 
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	 
	public String toString() {
		String s=index+": ";
		if (params!=null)
			for (Object p:params){
				s+=p+", ";
			}
		return s;
	}

}
