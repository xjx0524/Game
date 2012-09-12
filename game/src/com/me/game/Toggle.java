package com.me.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.me.game.G.TAG;
import com.me.inerface.IGTile;

public class Toggle{
	
	private int count,activeCount,saveCount;
	private String[] t;
	public final String name;
	private String[][] arg;
	private int[] x,y;
	private TAG[] skill;
	private ToggleGroup group;
	
	public Toggle(TiledObject obj){
		super();
		group=G.toggleGroup;
		//name
		name=obj.name;
		//t
		String s;
		int i=0;
		ArrayList<String> a=new ArrayList<String>();
		while ((s=obj.properties.get("t"+i))!=null){	
			a.add(s);
			++i;
		}
		if (a.size()==0) t=null;
		else t=(String[])a.toArray();
		
		//x,y,arg,skill;
		s=obj.properties.get("count");
		int n;
		if (s==null) n=1;else n=Integer.decode(s);
		x=new int[n];y=new int[n];
		arg=new String[n][];
		skill=new TAG[n];
		for (i=0;i<n;++i){
			s=obj.properties.get("x"+i);
			if (s==null) x[i]=0; else x[i]=Integer.decode(s);
			s=obj.properties.get("y"+i);
			if (s==null) y[i]=0; else y[i]=Integer.decode(s);
			s=obj.properties.get("skill"+i);
			if (s==null)
				if (i==0)
					skill[i]=TAG.SKILL_NULL;
				else{
					skill[i]=skill[0];
					arg[i]=arg[0];
				}
			else{
				skill[i]=G.TAG.valueOf(s);
				a.clear();
				int j=0;
				while ((s=obj.properties.get("arg"+i+j))!=null){a.add(s);++j;};
				if (a.size()==0) arg[i]=null;else
				arg[i]=(String[])a.toArray();
			}
						
		}
		
		//activecount
		s=obj.properties.get("ActiveCount");
		if (s==null) activeCount=1; else activeCount=Integer.decode(s);		
	}
	
	public void add(){
		++count;
		if (G.log) System.out.println("Toggle "+name+" added¡£  Count is "+count);
		if (count>=activeCount) active();
		G.signToSave = true;
	}
	
	public void sub(){
		--count;
		if (G.log) System.out.println("Toggle "+name+" subtracted. Count is "+count);
		if (count<activeCount) unactive();
		G.signToSave = true;
	}
	
	private void unactive() {
		if (G.log) System.out.println("Toggle "+name+" unactived");
		if (t!=null) for (String p:t){group.find(p).sub();}
		for (int i=0;i<skill.length;++i){
			IGTile tile=G.tmx.getTile(x[i], y[i]);
			if (tile!=null) tile.unactive(new Skill(skill[i],arg[i]));
		}
	}

	private void active() {
		if (G.log) System.out.println("Toggle "+name+" actived, "+skill.length+" tiles to be actived.");
		if (t!=null) for (String p:t){group.find(p).add();}
		for (int i=0;i<skill.length;++i){
			IGTile tile=G.tmx.getTile(x[i], y[i]);
			if (tile!=null) tile.active(new Skill(skill[i],arg[i]));
		}
	}
	
	public void save(){
		saveCount=count;
	}
	
	public void load(){
		count=saveCount;
	}
}
