package com.me.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;

public class ToggleGroup {

	private List<Toggle> list=new ArrayList<Toggle>();
	
	public ToggleGroup() {
		super();
		G.toggleGroup=this;
		if (G.tmx.getToggles()==null) return;
		for (TiledObject p:G.tmx.getToggles()){
			add(new Toggle(p));
		}
	}
	
	public void add(Toggle t){
		list.add(t);
		
	}
	
	public void remove(Toggle t){
		list.remove(t);
	}
	
	public Toggle find(String name){
		for (Toggle p:list){
			if (p.name.equals(name))
				return p;
		}
		return null;
	}
	
	
}
