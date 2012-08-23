package com.me.map;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.me.game.G;
import com.me.game.G.TAG;
import com.me.game.Skill;
import com.me.inerface.IGObject;
import com.me.inerface.IGSkill;
import com.me.inerface.IGTMX;
import com.me.inerface.IGTile;
import com.me.inerface.IGTileState;

public class XTile implements IGTile {
	
	private int id;
	private int x, y;	//地图逻辑坐标
	private XTiledMap map;
	public HashMap<String, String> properties;
	private boolean avaliable;
	
	public XTile(XTiledMap map, int id, int x, int y) {
		this.map = map;
		this.id = id;
		this.x = x;
		this.y = y;
		String s=map.map.getTileProperty(id, G.Label.Avaliable);
		this.avaliable=((s==null)||("true").equals(s));
	}

	@Override
	public void active(IGSkill skill) {
		if(G.log) System.out.println("ActiveTile:("+x+","+y+") by "+skill);
		if (getObject()!=null)	getObject().active(skill);
		switch ((G.TAG)skill.getIndex()){
		case SKILL_MOVE:pedalActive();lockHero();heroFlow();teleportActive();heroSlide();break;
		case SKILL_OBJECTMOVEDON:pedalActive();objectMovedIn();flow();slide(skill);break;
		case SKILL_FREEZE:freeze();break;
		case SKILL_THAW:thaw();break;
		case SKILL_PUSH:
		default:break;			
		}		
	}

	private void heroSlide() {
		if (getTag()!=TAG.TILE_ICE) return;
		if (getObject()==null){
			if (G.log) System.out.println("!!!!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!");
		}else
			G.hero.forward();		
	}

	private void slide(IGSkill skill) {
		if (getTag()!=TAG.TILE_ICE) return;
		if (getObject()==null){
			if (G.log) System.out.println("!!!!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!");
		}else
			getObject().forward((TAG)skill.getParams()[0]);
	}

	private void thaw() {
		if (getTag()!=TAG.TILE_ICE) return;
		String s=getProperties().get(G.Label.Arg0);
		if (s==null)
			id=toId(G.TAG.TILE_WATER);
		else
			id=toId(G.TAG.TILE_STREAM);//此处不不科学，无法判断方向，需修正；
		map.map.layers.get(0).tiles[map.map.height-1-y][x]=id;
	}

	private void freeze() {
		if (getTag()!=TAG.TILE_STREAM&&getTag()!=TAG.TILE_WATER) return;
		id=toId(TAG.TILE_ICE);
		map.map.layers.get(0).tiles[map.map.height-1-y][x]=id;				
	}

	private void teleportActive() {
		if (getTag()!=TAG.TILE_TELEPORT) return;
		float x=G.hero.x;
		float y=G.hero.y;
		G.hero.x=Float.parseFloat(getProperties().get(G.Label.Arg0));
		G.hero.y=Float.parseFloat(getProperties().get(G.Label.Arg1));
		G.hero.getStage().getCamera().translate(G.hero.x-x, G.hero.y-y, 0);
		G.hero.getStage().getCamera().update();
		G.hero.getStage().getCamera().apply(Gdx.gl10);		
	}

	private void flow() {
		if (getTag()!=TAG.TILE_SAND&&getTag()!=TAG.TILE_STREAM) return;
		TAG dir=null;
		if (getProperties().get(G.Label.Arg0).equalsIgnoreCase(G.Label.DIR_LEFT)) 	dir=TAG.DIR_LEFT; 	else
		if (getProperties().get(G.Label.Arg0).equalsIgnoreCase(G.Label.DIR_RIGHT)) 	dir=TAG.DIR_RIGHT; 	else
		if (getProperties().get(G.Label.Arg0).equalsIgnoreCase(G.Label.DIR_UP)) 	dir=TAG.DIR_UP;		else
		if (getProperties().get(G.Label.Arg0).equalsIgnoreCase(G.Label.DIR_DOWN)) 	dir=TAG.DIR_DOWN; 	else
			return;
		if (getObject()==null){
			if (G.log) System.out.println("!!!!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!");
		}else
			getObject().forward(dir);
	}

	private void heroFlow() {
		if (getTag()!=TAG.TILE_SAND&&getTag()!=TAG.TILE_STREAM) return;
		TAG dir=null;
		if (getProperties().get(G.Label.Arg0).equalsIgnoreCase(G.Label.DIR_LEFT)) 	dir=TAG.DIR_LEFT; 	else
		if (getProperties().get(G.Label.Arg0).equalsIgnoreCase(G.Label.DIR_RIGHT)) 	dir=TAG.DIR_RIGHT; 	else
		if (getProperties().get(G.Label.Arg0).equalsIgnoreCase(G.Label.DIR_UP)) 	dir=TAG.DIR_UP;		else
		if (getProperties().get(G.Label.Arg0).equalsIgnoreCase(G.Label.DIR_DOWN)) 	dir=TAG.DIR_DOWN; 	else
			return;
		G.hero.forward(dir);
	}

	private void objectMovedIn() {
		if (getTag()!=TAG.TILE_WATER&&getTag()!=TAG.TILE_HOLE)return;
		if (getObject()==null){
			if (G.log) System.out.print("!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!!!!!");
		}else{
			int objId = 0;
			IGObject o=getObject();
			switch(o.getId()){
			case OBJ_ICE:objId=toId(TAG.TILE_ICE);break;
			case OBJ_PULLABLE:objId=toId(TAG.TILE_GROUND1);break;
			case OBJ_PUSHABLE:objId=toId(TAG.TILE_GROUND1);break;
			default:if (G.log) System.out.print("!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!!!!!");break;
			}
			id=objId;
			map.map.layers.get(0).tiles[map.map.height-1-y][x]=id;
			avaliable=true;
			o.remove();				
		}		
	}

	private void lockHero() {
		if (getTag()!=TAG.TILE_WATER) return;
		G.hero.lock=true;
	}

	private void pedalActive() {
		if (getTag()!=TAG.TILE_PEDAL) return;
		G.toggleGroup.find(getProperties().get(G.Label.Arg1)).add();		
	}

	@Override
	public void unactive(IGSkill skill) {
		if(G.log) System.out.println("UnactiveTile:("+x+","+y+") by "+skill);
		switch ((G.TAG)skill.getIndex()){
		case SKILL_MOVE:pedalUnactive();break;
		case SKILL_OBJECTMOVEDON:pedalUnactive();break;
		case SKILL_PUSH:
		default:
			if (getObject()!=null)	getObject().active(skill);
		}
	}
	
	

	private void pedalUnactive() {
		if (getTag()!=TAG.TILE_PEDAL) return;
		if (!getProperties().get(G.Label.Arg0).equals("2")) return;
		G.toggleGroup.find(getProperties().get(G.Label.Arg1)).sub();		
	}

	@Override
	public boolean getIsAvaliable() {
		return (avaliable)&&(getObject()==null||getObject().getIsAvaliable());
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

	@Override
	public TAG getTag() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static int toId(TAG tag) {
		return 0;
	}
}
