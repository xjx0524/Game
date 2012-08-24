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
	private int x, y;	//µØÍ¼Âß¼­×ø±ê
	private XTiledMap map;
	public HashMap<String, String> properties;
	private boolean avaliable;
	private TAG type;
	private Object[] arg;
	
	public XTile(XTiledMap map, int id, int x, int y) {
		this.map = map;
		this.id = id;
		this.x = x;
		this.y = y;
		String s=map.map.getTileProperty(id, G.Label.Avaliable);
		this.avaliable=((s==null)||("true").equals(s));
		this.type = parseName(map.map.getTileProperty(id, "type"));
		parseArg();
	}
	
	private void parseArg() {
		switch(type){
		default:return;
		case TILE_PEDAL_1:arg=new Object[]
				{Integer.decode(map.map.getTileProperty(id, G.Label.Arg0)),map.map.getTileProperty(id, G.Label.Arg1),1};break;
		case TILE_PEDAL_2:arg=new Object[]
				{Integer.decode(map.map.getTileProperty(id, G.Label.Arg0)),map.map.getTileProperty(id, G.Label.Arg1),2};break;
		case TILE_PEDAL_3:arg=new Object[]
				{Integer.decode(map.map.getTileProperty(id, G.Label.Arg0)),map.map.getTileProperty(id, G.Label.Arg1),3};break;
		case TILE_PEDAL_4:arg=new Object[]
				{Integer.decode(map.map.getTileProperty(id, G.Label.Arg0)),map.map.getTileProperty(id, G.Label.Arg1),4};break;
		case TILE_TELEPORT:arg=new Object[]{
							Integer.parseInt(map.map.getTileProperty(id, G.Label.Arg0)),
							Integer.parseInt(map.map.getTileProperty(id, G.Label.Arg1))
							};break;
		}			
	}
	
	private void setTile(TAG tag,Object... arg){
		map.map.layers.get(0).tiles[map.map.height-1-y][x]=id=toId(tag);
		if (arg.length==0) return;
		if (arg.length>this.arg.length) this.arg=arg;
		else
			for (int i=0;i<arg.length;++i){
				this.arg[i]=arg[i];
			}
	}
	
	private boolean check(TAG...tags){
		for (TAG p:tags){
			if (getTag()==p) return true; 
		}
		return false;
	}

	private static TAG parseName(String name) {
		//if (name==null) return G.TAG.TILE_GROUND1;
		if (name.equalsIgnoreCase("ground1"))	return G.TAG.TILE_GROUND1; else
		if (name.equalsIgnoreCase("ground2"))	return G.TAG.TILE_GROUND2; else
		if (name.equalsIgnoreCase("pedal"))		return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("pedal1"))	return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("pedal2"))	return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("pedal3"))	return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("pedal4"))	return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("water"))		return G.TAG.TILE_WATER; else
		if (name.equalsIgnoreCase("stream_u"))	return G.TAG.TILE_STREAM_U1; else
		if (name.equalsIgnoreCase("stream_d"))	return G.TAG.TILE_STREAM_D1; else
		if (name.equalsIgnoreCase("stream_l"))	return G.TAG.TILE_STREAM_L1; else
		if (name.equalsIgnoreCase("stream_r"))	return G.TAG.TILE_STREAM_R1; else
		if (name.equalsIgnoreCase("sand_u"))	return G.TAG.TILE_SAND_U1; else
		if (name.equalsIgnoreCase("sand_d"))	return G.TAG.TILE_SAND_D1; else
		if (name.equalsIgnoreCase("sand_l"))	return G.TAG.TILE_SAND_L1; else
		if (name.equalsIgnoreCase("sand_r"))	return G.TAG.TILE_SAND_R1; else
		if (name.equalsIgnoreCase("teleport"))	return G.TAG.TILE_TELEPORT; else
		if (name.equalsIgnoreCase("hole"))		return G.TAG.TILE_HOLE; else
		if (name.equalsIgnoreCase("ice"))		return G.TAG.TILE_ICE; else
		return null;
	}
	
	private static String parseTag(TAG tag) {
		switch(tag) {
		default: return null;
		case TILE_GROUND1: return "ground1"; 
		case TILE_GROUND2: return "ground2"; 
		case TILE_PEDAL_1: return "pedal1"; 
		case TILE_PEDAL_2: return "pedal2";
		case TILE_PEDAL_3: return "pedal3";
		case TILE_PEDAL_4: return "pedal4";
		case TILE_PEDAL_DOWN: return "pedaldown";
		case TILE_WATER: return "water"; 
		case TILE_STREAM_U1:case TILE_STREAM_U2:case TILE_STREAM_U3:case TILE_STREAM_U4: return "stream_u";
		case TILE_STREAM_D1:case TILE_STREAM_D2:case TILE_STREAM_D3:case TILE_STREAM_D4: return "stream_d";
		case TILE_STREAM_L1:case TILE_STREAM_L2:case TILE_STREAM_L3:case TILE_STREAM_L4: return "stream_l";
		case TILE_STREAM_R1:case TILE_STREAM_R2:case TILE_STREAM_R3:case TILE_STREAM_R4: return "stream_r";
		case TILE_SAND_U1:case TILE_SAND_U2:case TILE_SAND_U3:case TILE_SAND_U4: return "sand_u"; 
		case TILE_SAND_D1:case TILE_SAND_D2:case TILE_SAND_D3:case TILE_SAND_D4: return "sand_d";
		case TILE_SAND_L1:case TILE_SAND_L2:case TILE_SAND_L3:case TILE_SAND_L4: return "sand_l";
		case TILE_SAND_R1:case TILE_SAND_R2:case TILE_SAND_R3:case TILE_SAND_R4: return "sand_r";
		case TILE_TELEPORT: return "teleport"; 
		case TILE_HOLE: return "hole"; 
		case TILE_ICE: return "ice";
		}
	}
	
	public static int toId(TAG tag) {
		for (int i=1;i<G.tmx.maxTileId;i++) {
			if (parseTag(tag).equals(G.tmx.map.getTileProperty(i, "type")))
				return i;
		}
		return 0;
	}
	
	public TAG getTag() {
		return type;
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
		case SKILL_TELE:pedalActive();lockHero();heroFlow();heroSlide();break;
		case SKILL_PUSH:
		default:break;			
		}		
	}

	private void heroSlide() {
		if (!check(TAG.TILE_ICE)) return;
		if (getObject()==null){
			if (G.log) System.out.println("!!!!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!");
		}else
			G.hero.forward();		
	}

	private void slide(IGSkill skill) {
		if (!check(TAG.TILE_ICE)) return;
		if (getObject()==null){
			if (G.log) System.out.println("!!!!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!");
		}else
			getObject().forward((TAG)skill.getParams()[0]);
	}

	private void thaw() {
		if (!check(TAG.TILE_ICE)) return;
		String s=getProperties().get(G.Label.Arg0);
		if (s==null)
			setTile(TAG.TILE_WATER);
		else
			switch((TAG)arg[0]){
			case DIR_DOWN:	setTile(TAG.TILE_STREAM_D1);break;
			case DIR_UP:	setTile(TAG.TILE_STREAM_U1);break;
			case DIR_LEFT:	setTile(TAG.TILE_STREAM_L1);break;
			case DIR_RIGHT:	setTile(TAG.TILE_STREAM_R1);break;
			default:		setTile(TAG.TILE_WATER);break;
			}
	}

	private void freeze() {
		if (check(TAG.TILE_STREAM_U1,TAG.TILE_STREAM_U2,TAG.TILE_STREAM_U3,TAG.TILE_STREAM_U4)) setTile(TAG.TILE_ICE, TAG.DIR_UP); else
		if (check(TAG.TILE_STREAM_D1,TAG.TILE_STREAM_D2,TAG.TILE_STREAM_D3,TAG.TILE_STREAM_D4)) setTile(TAG.TILE_ICE, TAG.DIR_DOWN); else
		if (check(TAG.TILE_STREAM_L1,TAG.TILE_STREAM_L2,TAG.TILE_STREAM_L3,TAG.TILE_STREAM_L4)) setTile(TAG.TILE_ICE, TAG.DIR_LEFT); else
		if (check(TAG.TILE_STREAM_R1,TAG.TILE_STREAM_R2,TAG.TILE_STREAM_R3,TAG.TILE_STREAM_R4)) setTile(TAG.TILE_ICE, TAG.DIR_RIGHT); else
		if (check(TAG.TILE_WATER))	setTile(TAG.TILE_ICE); else return;
	}

	private void teleportActive() {
		if (!check(TAG.TILE_TELEPORT)) return;
		float x=G.hero.x;
		float y=G.hero.y;
		G.hero.mapx=(int)Float.parseFloat(getProperties().get(G.Label.Arg0));
		G.hero.mapy=(int)Float.parseFloat(getProperties().get(G.Label.Arg1));
		G.hero.x=32*G.hero.mapx;
		G.hero.y=32*G.hero.mapy;
		G.hero.getStage().getCamera().translate(G.hero.x-x, G.hero.y-y, 0);
		G.hero.getStage().getCamera().update();
		G.hero.getStage().getCamera().apply(Gdx.gl10);
		G.tmx.getTile(G.hero.mapx, G.hero.mapy).active(new Skill(TAG.SKILL_TELE));
	}

	private void flow() {
		TAG dir=null;
		if (check(TAG.TILE_STREAM_U1,TAG.TILE_STREAM_U2,TAG.TILE_STREAM_U3,TAG.TILE_STREAM_U4,
				TAG.TILE_SAND_U1,TAG.TILE_SAND_U2,TAG.TILE_SAND_U3,TAG.TILE_SAND_U4)) dir=TAG.DIR_UP; else
		if (check(TAG.TILE_STREAM_D1,TAG.TILE_STREAM_D2,TAG.TILE_STREAM_D3,TAG.TILE_STREAM_D4,
				TAG.TILE_SAND_D1,TAG.TILE_SAND_D2,TAG.TILE_SAND_D3,TAG.TILE_SAND_D4)) dir=TAG.DIR_DOWN; else
		if (check(TAG.TILE_STREAM_L1,TAG.TILE_STREAM_L2,TAG.TILE_STREAM_L3,TAG.TILE_STREAM_L4,
				TAG.TILE_SAND_L1,TAG.TILE_SAND_L2,TAG.TILE_SAND_L3,TAG.TILE_SAND_L4)) dir=TAG.DIR_LEFT; else
		if (check(TAG.TILE_STREAM_R1,TAG.TILE_STREAM_R2,TAG.TILE_STREAM_R3,TAG.TILE_STREAM_R4,
				TAG.TILE_SAND_R1,TAG.TILE_SAND_R2,TAG.TILE_SAND_R3,TAG.TILE_SAND_R4)) dir=TAG.DIR_RIGHT; else
			return;
		if (getObject()==null){
			if (G.log) System.out.println("!!!!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!");
		}else
			getObject().forward(dir);
	}

	private void heroFlow() {
		TAG dir=null;
		if (check(TAG.TILE_STREAM_U1,TAG.TILE_STREAM_U2,TAG.TILE_STREAM_U3,TAG.TILE_STREAM_U4,
				  TAG.TILE_SAND_U1,  TAG.TILE_SAND_U2,  TAG.TILE_SAND_U3,  TAG.TILE_SAND_U4)) dir=TAG.DIR_UP; else
		if (check(TAG.TILE_STREAM_D1,TAG.TILE_STREAM_D2,TAG.TILE_STREAM_D3,TAG.TILE_STREAM_D4,
				  TAG.TILE_SAND_D1,  TAG.TILE_SAND_D2,  TAG.TILE_SAND_D3,  TAG.TILE_SAND_D4)) dir=TAG.DIR_DOWN; else
		if (check(TAG.TILE_STREAM_L1,TAG.TILE_STREAM_L2,TAG.TILE_STREAM_L3,TAG.TILE_STREAM_L4,
				  TAG.TILE_SAND_L1,  TAG.TILE_SAND_L2,  TAG.TILE_SAND_L3,  TAG.TILE_SAND_L4)) dir=TAG.DIR_LEFT; else
		if (check(TAG.TILE_STREAM_R1,TAG.TILE_STREAM_R2,TAG.TILE_STREAM_R3,TAG.TILE_STREAM_R4,
				  TAG.TILE_SAND_R1,  TAG.TILE_SAND_R2,  TAG.TILE_SAND_R3,  TAG.TILE_SAND_R4)) dir=TAG.DIR_RIGHT; else
			return;
		G.hero.forward(dir);
	}

	private void objectMovedIn() {
		if (!check(TAG.TILE_WATER,TAG.TILE_HOLE)) return;
		if (getObject()==null){
			if (G.log) System.out.print("!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!!!!!");
		}else{
			TAG tag = null;
			IGObject o=getObject();
			switch(o.getId()){
			case OBJ_ICE:tag=TAG.TILE_ICE;break;
			case OBJ_PULLABLE:tag=TAG.TILE_GROUND1;break;
			case OBJ_PUSHABLE:tag=TAG.TILE_GROUND1;break;
			default:if (G.log) System.out.print("!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!!!!!");break;
			}
			setTile(tag);
			avaliable=true;
			o.remove();			
		}		
	}

	private void lockHero() {
		if (getTag()!=TAG.TILE_WATER) return;
		G.hero.lock=true;
	}

	private void pedalActive() {
		if (!check(TAG.TILE_PEDAL_1,TAG.TILE_PEDAL_2,TAG.TILE_PEDAL_3,TAG.TILE_PEDAL_4)) return;
		G.toggleGroup.find(getProperties().get(G.Label.Arg0)).add();
		setTile(TAG.TILE_PEDAL_DOWN);
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
		if (getTag()!=TAG.TILE_PEDAL_DOWN) return;
		if ((Integer)arg[0]!=2) return;
		G.toggleGroup.find(getProperties().get(G.Label.Arg1)).sub();
		switch((Integer)arg[2]){
		case 1:setTile(TAG.TILE_PEDAL_1);break;
		case 2:setTile(TAG.TILE_PEDAL_2);break;
		case 3:setTile(TAG.TILE_PEDAL_3);break;
		case 4:setTile(TAG.TILE_PEDAL_4);break;
		}
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

}
