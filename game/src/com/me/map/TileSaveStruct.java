package com.me.map;

import com.me.game.G.TAG;

public class TileSaveStruct {
	XTile t;
	int id;
	TAG tag;
	Object[] arg;
	boolean avaliable;
	public TileSaveStruct(XTile t) {
		save(t);
	}
	public void save(XTile t){
		this.t=t;
		t.saveTo(this);
	}
	public void load(){
		t.loadFrom(this);
	}
}
