package com.me.map;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.me.game.G;
import com.me.inerface.IGObject;
import com.me.inerface.IGSkill;
import com.me.inerface.IGTMX;
import com.me.inerface.IGTile;
import com.me.inerface.IGTileState;

public class XTile implements IGTile {
	
	private int id;
	private int x, y;	//µØÍ¼Âß¼­×ø±ê
	private XTiledMap map;
	public HashMap<String, String> properties;
	
	public XTile(XTiledMap map, int id, int x, int y) {
		this.map = map;
		this.id = id;
		this.x = x;
		this.y = y;
	}

	@Override
	public void active(IGSkill skill) {
		if(G.log) System.out.println("ActiveTile:("+x+","+y+") by "+skill);
		switch ((G.TAG)skill.getIndex()){
		case SKILL_OBJECTMOVEDON:break;
		case SKILL_PUSH:
		default:
			if (getObject()!=null)	getObject().active(skill);
		}
		
		
	}

	@Override
	public void unactive(IGSkill skill) {
		if(G.log) System.out.println("UnactiveTile:("+x+","+y+") by "+skill);
		switch ((G.TAG)skill.getIndex()){
		case SKILL_OBJECTMOVEDON:break;
		case SKILL_PUSH:
		default:
			if (getObject()!=null)	getObject().active(skill);
		}
	}

	@Override
	public boolean getIsAvaliable() {
		String s=map.map.getTileProperty(id, G.Label.Avaliable);
		return (s==null||("true").equals(s))&&(getObject()==null||getObject().getIsAvaliable());
	}
	
	@Override
	public boolean getIsAvaliableForObject() {
		//String s=map.map.getTileProperty(id, G.Label.Avaliable);
		return getIsAvaliable();
	}
	
	@Override
	public boolean getIsAvaliableForJump() {
		//String s=map.map.getTileProperty(id, G.Label.Avaliable);
		if (getObject()==null)
			return true;
		else
			return getObject().getIsAvaliable();
	}


	@Override
	public Vector2 getLocation() {
		Vector2 tmp = new Vector2(x * map.map.tileWidth, y * map.map.tileHeight);
		Vector2 origin = map.getPosition();
		if (tmp.x < origin.x || tmp.y < origin.y) return null;
		return new Vector2(tmp.x - origin.x, tmp.y - origin.y);
	}

	@Override
	public Vector2 getIndex() {
		return new Vector2(x, y);
	}

	@Override
	public IGObject getObject() {
		return map.objectGroup.getObject(x, y);
	}

	@Override
	public IGTMX getTMX() {
		return map;
	}

	@Override
	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void active() {	return;	}
	public IGTileState getState() {return null;}
}
