package com.me.game;

import com.badlogic.gdx.math.Vector2;
import com.me.aaction.ACall;
import com.me.aaction.AParabola;
import com.me.aaction.ASequence;
import com.me.aaction.ICallFunc;
import com.me.game.G.TAG;
import com.me.inerface.IGSkill;
import com.me.inerface.IGTile;

public class Skill implements IGSkill {
	
	private Object index;
	private Object[] params;
	private Hero hero;
	
	public Skill(){
		super();
		this.index=G.TAG.SKILL_NULL;
		this.params=null;
		hero=G.hero;
	}
	
	public Skill(G.TAG index,Object...params){
		super();
		this.index=index;
		this.params=params;
		hero=null;
	}
	
	public Skill(G.TAG index,String[] params){
		super();
		this.index=index;
		this.params=params;
		hero=null;
		parse();
	}
	
	private void parse(){
		switch ((G.TAG)index){
		case SKILL_PUSH:
		case SKILL_PULL:
		case SKILL_JUMP:
		case SKILL_MOVE:
		case SKILL_OBJECTMOVEDON:params[0]=G.TAG.valueOf((String)params[0]);break;
		case SKILL_NULL:
		case SKILL_FREEZE:
		case SKILL_THAW:return;
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
			{
				index=G.TAG.SKILL_PUSH;
				params=new Object[]{G.parseDirection(hero.curdirection)};
			}
		}break;
		case GEN_PULL:{
			if (G.skillisactived(G.TAG.SKILL_PULL)){
				index=G.TAG.SKILL_PULL;
				params=new Object[]{G.parseDirection(hero.curdirection)};
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
	
	static public void cast(G.TAG tag){
		if (G.lockInput) return;
		switch (tag){
		case SKILL_JUMP:jump();break;
		case SKILL_FREEZE:freeze();break;
		case SKILL_THAW:thaw();break;
		}
	}

	private static void thaw() {
		int mapx=G.hero.mapx;
		int mapy=G.hero.mapy;
		int gx=mapx,gy=mapy;
		switch(G.parseDirection(G.hero.curdirection)){
		case DIR_DOWN:gy=mapy-1;break;
		case DIR_LEFT:gx=mapx-1;break;
		case DIR_RIGHT:gx=mapx+1;break;
		case DIR_UP:gy=mapy+1;break;
		}
		if (G.tmx.getTile(gx, gy)==null) return;
		G.tmx.getTile(gx, gy).active(new Skill(TAG.SKILL_THAW));		
	}

	private static void freeze() {
		int mapx=G.hero.mapx;
		int mapy=G.hero.mapy;
		int gx=mapx,gy=mapy;
		switch(G.parseDirection(G.hero.curdirection)){
		case DIR_DOWN:gy=mapy-1;break;
		case DIR_LEFT:gx=mapx-1;break;
		case DIR_RIGHT:gx=mapx+1;break;
		case DIR_UP:gy=mapy+1;break;
		}
		if (G.tmx.getTile(gx, gy)==null) return;
		G.tmx.getTile(gx, gy).active(new Skill(TAG.SKILL_FREEZE));	
	}

	static private void jump() {
		if (G.hero.lock) return;
		int mapx=G.hero.mapx;
		int mapy=G.hero.mapy;
		int gx=mapx,gy=mapy;
		G.tmx.getTile(mapx, mapy).unactive(G.hero.skill.generate(G.TAG.GEN_STAY));
		switch(G.parseDirection(G.hero.curdirection)){
		case DIR_DOWN:gy=mapy-1;break;
		case DIR_LEFT:gx=mapx-1;break;
		case DIR_RIGHT:gx=mapx+1;break;
		case DIR_UP:gy=mapy+1;break;
		}
		int gx2=gx,gy2=gy;
		switch(G.parseDirection(G.hero.curdirection)){
		case DIR_DOWN:gy2=gy-1;break;
		case DIR_LEFT:gx2=gx-1;break;
		case DIR_RIGHT:gx2=gx+1;break;
		case DIR_UP:gy2=gy+1;break;
		}
		IGTile t1=G.tmx.getTile(gx, gy);
		IGTile t2=G.tmx.getTile(gx2, gy2);
		final int GX,GY;
		if (
			(!(t1!=null&&t1.getIsAvaliableForJump()))
			||((t1==null||!t1.getIsAvaliable())&&(t2==null||!t2.getIsAvaliable()))
			)
		{
			GX=mapx;GY=mapy;
		}else if (
				(!(t2!=null&&t2.getIsAvaliableForJump()))
				||(t2==null||!t2.getIsAvaliable())
				){
			GX=gx;GY=gy;
		}else{
			GX=gx2;GY=gy2;
		}
		G.hero.runAction(ASequence.$(
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {
						G.lockInput=true;
						if (G.log) System.out.println("JUMP TO ("+GX+","+GY+")");
						
					}
				}),
				AParabola.$(1,(mapy+GY)*16+48, new Vector2(GX*32,GY*32)),
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {
						G.lockInput=false;
						G.hero.mapx=GX;
						G.hero.mapy=GY;
						G.tmx.getTile(GX, GY).active(G.hero.skill.generate(G.TAG.GEN_STAY));
						G.hero.lx=G.hero.mapx;
						G.hero.ly=G.hero.mapy;
					}
				})
				));
	}
}
;