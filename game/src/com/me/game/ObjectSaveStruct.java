package com.me.game;

public class ObjectSaveStruct{
	MapObject o;
	int x;
	int y;
	boolean avaliable;
	Object[] arg=new Object[5];
	
	public ObjectSaveStruct(MapObject o) {
		super();
		save(o);
	}
	
	public void save(MapObject o){
		this.o=o;
		x=o.mapx;
		y=o.mapy;
		avaliable=o.avaliable;
		for (int i=0;i<5;++i)
			arg[i]=o.arg[i];		
	}
	
	public void load(){
		o.mapx=x;
		o.mapy=y;
		o.x=o.mapx*32;
		o.y=o.mapy*32;
		o.avaliable=avaliable;
		for (int i=0;i<5;++i)
			o.arg[i]=arg[i];
	}
}