package com.me.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SkillButtonGroup extends Stage {
	
	
	public SkillButtonGroup(float width, float height, boolean stretch) 
	{super(width, height, stretch);G.skillBottonGroup=this;}
	public SkillButtonGroup(float width, float height, boolean stretch,	SpriteBatch batch) 
	{super(width, height, stretch, batch);G.skillBottonGroup=this;}
	
	private SkillButton getButton(G.TAG tag){
		for (Actor p:getActors()){
			if (((SkillButton)p).tag==tag)
				return (SkillButton)p;
		}
		return null;
	}
	
	public boolean getIsActived(G.TAG tag){
		SkillButton button=getButton(tag);
		if (button==null)
			return false;
		else return button.getIsactived();
	}
	
	public void active(G.TAG tag,boolean isActive){
		SkillButton button=getButton(tag);
		if (button==null)
			return;
		else 
			button.active(isActive);
	}
	
	public void select(SkillButton skillButton) {
		for (Actor p:getActors()){
			if (((SkillButton)p).type==SkillButton.TYPE.SELECT)
				((SkillButton)p).active(false);
		}
		skillButton.active(true);		
	}
	
	public void oganize(){
		ArrayList<Actor> auto=new ArrayList<Actor>();
		ArrayList<Actor> sel=new ArrayList<Actor>();
		ArrayList<Actor> act=new ArrayList<Actor>();
		for (Actor p:getActors()){
			switch (((SkillButton)p).type){
			case AUTO:auto.add(p);break;
			case SELECT:sel.add(p);break;
			case ACTIVE:act.add(p);break;
			}
		}
		switch(auto.size()){
		case 1:auto.get(0).x=176;auto.get(0).y=8;
			break;
		}
		switch(sel.size()){
		case 1:sel.get(0).x=8;sel.get(0).y=136;
			break;
		}
		switch(act.size()){
		case 1:act.get(0).x=280;act.get(0).y=104;
			break;
		}
	}

}
