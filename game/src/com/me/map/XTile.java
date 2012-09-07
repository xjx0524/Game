package com.me.map;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.me.aaction.ABreakIf;
import com.me.aaction.ACall;
import com.me.aaction.ADelay;
import com.me.aaction.AForever;
import com.me.aaction.ASequence;
import com.me.aaction.ICallFunc;
import com.me.aaction.IIfFunc;
import com.me.game.G;
import com.me.game.G.TAG;
import com.me.game.Hero;
import com.me.game.MapObject;
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
		case TILE_PEDAL_1:case TILE_PEDAL_2:case TILE_PEDAL_3:case TILE_PEDAL_4:case TILE_PEDAL:
			arg=new Object[]
			{Integer.decode(map.map.getTileProperty(id, G.Label.Arg0)),map.map.getTileProperty(id, G.Label.Arg1),id,type};
			break;
		case TILE_TELEPORT:arg=new Object[]{
							Integer.parseInt(map.map.getTileProperty(id, G.Label.Arg0)),
							Integer.parseInt(map.map.getTileProperty(id, G.Label.Arg1))
							};break;
		}			
	}
	
	void setTile(TAG tag,Object... arg){
		G.flowmanager.remove(this);
		map.map.layers.get(0).tiles[map.map.height-1-y][x]=id=toId(tag);
		type=tag;
		G.flowmanager.add(this);
		if (G.log) System.out.println("Change to "+tag+" id is "+id);
		if (arg.length==0) {
			G.signToSave = true;
			return;
		}
		if (this.arg==null||arg.length>this.arg.length) this.arg=arg;
		else
			for (int i=0;i<arg.length;++i){
				this.arg[i]=arg[i];
			}
		G.signToSave = true;
	}
	
	void setTile(int id,TAG tag,Object... arg){
		G.flowmanager.remove(this);
		map.map.layers.get(0).tiles[map.map.height-1-y][x]=this.id=id;
		type=tag;
		G.flowmanager.add(this);
		if (G.log) System.out.println("Change to "+tag+" id is "+id);
		if (arg.length==0) {
			G.signToSave = true;
			return;
		}if (this.arg==null||arg.length>this.arg.length) this.arg=arg;
		else
			for (int i=0;i<arg.length;++i){
				this.arg[i]=arg[i];
			}
		G.signToSave = true;
	}
	
	boolean check(TAG...tags){
		for (TAG p:tags){
			if (getTag()==p) return true;
		}
		return false;
	}

	private static TAG parseName(String name) {
		if (name==null) return G.TAG.TILE_OTHER;
		if (name.equalsIgnoreCase("ground1"))	return G.TAG.TILE_GROUND1; else
		if (name.equalsIgnoreCase("ground2"))	return G.TAG.TILE_GROUND2; else
		if (name.equalsIgnoreCase("pedal"))		return G.TAG.TILE_PEDAL; else
		if (name.equalsIgnoreCase("pedal1"))	return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("pedal2"))	return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("pedal3"))	return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("pedal4"))	return G.TAG.TILE_PEDAL_1; else
		if (name.equalsIgnoreCase("water"))		return G.TAG.TILE_WATER; else
		if (name.equalsIgnoreCase("stream_u"))	return G.TAG.TILE_STREAM_U1; else
		if (name.equalsIgnoreCase("stream_u1"))	return G.TAG.TILE_STREAM_U1; else
		if (name.equalsIgnoreCase("stream_u2"))	return G.TAG.TILE_STREAM_U2; else
		if (name.equalsIgnoreCase("stream_u3"))	return G.TAG.TILE_STREAM_U3; else
		if (name.equalsIgnoreCase("stream_u4"))	return G.TAG.TILE_STREAM_U4; else
		if (name.equalsIgnoreCase("stream_d"))	return G.TAG.TILE_STREAM_D1; else
		if (name.equalsIgnoreCase("stream_d1"))	return G.TAG.TILE_STREAM_D1; else
		if (name.equalsIgnoreCase("stream_d2"))	return G.TAG.TILE_STREAM_D2; else
		if (name.equalsIgnoreCase("stream_d3"))	return G.TAG.TILE_STREAM_D3; else
		if (name.equalsIgnoreCase("stream_d4"))	return G.TAG.TILE_STREAM_D4; else
		if (name.equalsIgnoreCase("stream_l"))	return G.TAG.TILE_STREAM_L1; else
		if (name.equalsIgnoreCase("stream_l1"))	return G.TAG.TILE_STREAM_L1; else
		if (name.equalsIgnoreCase("stream_l2"))	return G.TAG.TILE_STREAM_L2; else
		if (name.equalsIgnoreCase("stream_l3"))	return G.TAG.TILE_STREAM_L3; else
		if (name.equalsIgnoreCase("stream_l4"))	return G.TAG.TILE_STREAM_L4; else
		if (name.equalsIgnoreCase("stream_r"))	return G.TAG.TILE_STREAM_R1; else
		if (name.equalsIgnoreCase("stream_r1"))	return G.TAG.TILE_STREAM_R1; else
		if (name.equalsIgnoreCase("stream_r2"))	return G.TAG.TILE_STREAM_R2; else
		if (name.equalsIgnoreCase("stream_r3"))	return G.TAG.TILE_STREAM_R3; else
		if (name.equalsIgnoreCase("stream_r4"))	return G.TAG.TILE_STREAM_R4; else
		if (name.equalsIgnoreCase("sand_u"))	return G.TAG.TILE_SAND_U1; else
		if (name.equalsIgnoreCase("sand_u1"))	return G.TAG.TILE_SAND_U1; else
		if (name.equalsIgnoreCase("sand_u2"))	return G.TAG.TILE_SAND_U2; else
		if (name.equalsIgnoreCase("sand_u3"))	return G.TAG.TILE_SAND_U3; else
		if (name.equalsIgnoreCase("sand_u4"))	return G.TAG.TILE_SAND_U4; else
		if (name.equalsIgnoreCase("sand_d"))	return G.TAG.TILE_SAND_D1; else
		if (name.equalsIgnoreCase("sand_d1"))	return G.TAG.TILE_SAND_D1; else
		if (name.equalsIgnoreCase("sand_d2"))	return G.TAG.TILE_SAND_D2; else
		if (name.equalsIgnoreCase("sand_d3"))	return G.TAG.TILE_SAND_D3; else
		if (name.equalsIgnoreCase("sand_d4"))	return G.TAG.TILE_SAND_D4; else
		if (name.equalsIgnoreCase("sand_l"))	return G.TAG.TILE_SAND_L1; else
		if (name.equalsIgnoreCase("sand_l1"))	return G.TAG.TILE_SAND_L1; else
		if (name.equalsIgnoreCase("sand_l2"))	return G.TAG.TILE_SAND_L2; else
		if (name.equalsIgnoreCase("sand_l3"))	return G.TAG.TILE_SAND_L3; else
		if (name.equalsIgnoreCase("sand_l4"))	return G.TAG.TILE_SAND_L4; else
		if (name.equalsIgnoreCase("sand_r"))	return G.TAG.TILE_SAND_R1; else
		if (name.equalsIgnoreCase("sand_r1"))	return G.TAG.TILE_SAND_R1; else
		if (name.equalsIgnoreCase("sand_r2"))	return G.TAG.TILE_SAND_R2; else
		if (name.equalsIgnoreCase("sand_r3"))	return G.TAG.TILE_SAND_R3; else
		if (name.equalsIgnoreCase("sand_r4"))	return G.TAG.TILE_SAND_R4; else
		if (name.equalsIgnoreCase("teleport"))	return G.TAG.TILE_TELEPORT; else
		if (name.equalsIgnoreCase("hole"))		return G.TAG.TILE_HOLE; else
		if (name.equalsIgnoreCase("ice"))		return G.TAG.TILE_ICE; else
		if (name.equalsIgnoreCase("destination"))return G.TAG.TILE_DESTINATION;
		return null;
	}
	
	private static String parseTag(TAG tag) {
		switch(tag) {
		default: return null;
		case TILE_GROUND1: return "ground1"; 
		case TILE_GROUND2: return "ground2";
		case TILE_PEDAL	 : return "pedal";
		case TILE_PEDAL_1: return "pedal1"; 
		case TILE_PEDAL_2: return "pedal2";
		case TILE_PEDAL_3: return "pedal3";
		case TILE_PEDAL_4: return "pedal4";
		case TILE_PEDAL_DOWN: return "pedaldown";
		case TILE_WATER: return "water"; 
		
		case TILE_STREAM_U1:return "stream_u1";
		case TILE_STREAM_U2:return "stream_u2";
		case TILE_STREAM_U3:return "stream_u3";
		case TILE_STREAM_U4:return "stream_u4";
		case TILE_STREAM_D1:return "stream_d1";
		case TILE_STREAM_D2:return "stream_d2";
		case TILE_STREAM_D3:return "stream_d3";
		case TILE_STREAM_D4:return "stream_d4";
		case TILE_STREAM_L1:return "stream_l1";
		case TILE_STREAM_L2:return "stream_l2";
		case TILE_STREAM_L3:return "stream_l3";
		case TILE_STREAM_L4:return "stream_l4";
		case TILE_STREAM_R1:return "stream_r1";
		case TILE_STREAM_R2:return "stream_r2";
		case TILE_STREAM_R3:return "stream_r3";
		case TILE_STREAM_R4:return "stream_r4";
		
		case TILE_SAND_U1:return "sand_u1";
		case TILE_SAND_U2:return "sand_u2";
		case TILE_SAND_U3:return "sand_u3";
		case TILE_SAND_U4:return "sand_u4";
		case TILE_SAND_D1:return "sand_d1";
		case TILE_SAND_D2:return "sand_d2";
		case TILE_SAND_D3:return "sand_d3";
		case TILE_SAND_D4:return "sand_d4";
		case TILE_SAND_L1:return "sand_l1";
		case TILE_SAND_L2:return "sand_l2";
		case TILE_SAND_L3:return "sand_l3";
		case TILE_SAND_L4:return "sand_l4";
		case TILE_SAND_R1:return "sand_r1";
		case TILE_SAND_R2:return "sand_r2";
		case TILE_SAND_R3:return "sand_r3";
		case TILE_SAND_R4:return "sand_r4";
		
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
		if(G.log) System.out.println("ActiveTile:("+x+","+y+") "+type+ " by "+skill);
		if (getObject()!=null)	for (IGObject p:getObject()) p.active(skill);
		switch ((G.TAG)skill.getIndex()){
		case SKILL_MOVE:win();pedalActive();heroDead();heroFlow(skill);teleportActive();heroSlide();break;
		case SKILL_OBJECTMOVEDON:pedalActive();objectMovedIn();flow();slide(skill);teleObject();break;
		case SKILL_FREEZE:freeze();break;
		case SKILL_THAW:thaw();break;
		case SKILL_TELE:pedalActive();heroDead();heroFlow(skill);heroSlide();break;
		case SKILL_PUSH:
		default:break;			
		}		
	}

	private void win() {
		if (type!=TAG.TILE_DESTINATION) return;
		G.gameScreen.nextMisstion();	
	}

	private void teleObject() {
		if (!check(TAG.TILE_TELEPORT)) return;
		if (getObject()==null){
			if (G.log) System.out.println("!!!!!!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!");
		}
		else{
			if(!G.tmx.getTile((Integer)arg[0], (Integer)arg[1]).getIsAvaliableForObject()) return;
			G.playSound(TAG.SOUND_TELEPORT);
			for (IGObject p:getObject()){
				final MapObject  o=(MapObject)p;
				++G.hero.lock;++G.lockInput;
				o.runAction(ASequence.$(
						ADelay.$(1f),
						ACall.$(new ICallFunc() {
							public void onCall(Object[] params) {
								o.mapx = (Integer)arg[0];
								o.mapy = (Integer)arg[1];
								o.x=o.mapx*32;
								o.y=o.mapy*32;
								--G.hero.lock;--G.lockInput;
							}
						})
						));
				
			}
		}
	}

	private void heroSlide() {
		if (!check(TAG.TILE_ICE)) return;
		G.playSound(TAG.SOUND_SLIDE);
		G.hero.forward();
	}

	private void slide(IGSkill skill) {
		if (!check(TAG.TILE_ICE)) return;
		if (skill.getParams()[0]==TAG.DIR_NONE) return;
		if (getObject()==null){
			if (G.log) System.out.println("!!!!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!");
		}else{
			for (IGObject p:getObject())
				p.forward((TAG)skill.getParams()[0]);
			G.playSound(TAG.SOUND_SLIDE);
		}
	}

	private void thaw() {
		if (!check(TAG.TILE_ICE)) {
			active(new Skill(TAG.SKILL_OBJECTMOVEDON,TAG.DIR_NONE));
			return;
		}
		if (arg==null||arg[0]==null)			
			setTile(TAG.TILE_WATER);
		else{			
			switch((TAG)arg[0]){
			case DIR_DOWN:	setTile(TAG.TILE_STREAM_D1);break;
			case DIR_UP:	setTile(TAG.TILE_STREAM_U1);break;
			case DIR_LEFT:	setTile(TAG.TILE_STREAM_L1);break;
			case DIR_RIGHT:	setTile(TAG.TILE_STREAM_R1);break;
			default:		setTile(TAG.TILE_WATER);break;
			}
		}
		active(new Skill(TAG.SKILL_OBJECTMOVEDON,TAG.DIR_NONE));
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
		if(!G.tmx.getTile((Integer)arg[0], (Integer)arg[1]).getIsAvaliable()) return;
		G.playSound(TAG.SOUND_TELEPORT);
		++G.hero.lock;++G.lockInput;
		G.hero.runAction(ASequence.$(
				ADelay.$(1f),
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {
						G.hero.mapx=(Integer)arg[0];
						G.hero.mapy=(Integer)arg[1];
						G.hero.x=(Integer)arg[0]*32;
						G.hero.y=(Integer)arg[1]*32;
						G.tmx.getTile(G.hero.mapx, G.hero.mapy).active(new Skill(TAG.SKILL_TELE));
						if (G.log) System.out.println("Tele to ("+G.hero.mapx+","+G.hero.mapy+") CameraPos("+G.hero.getStage().getCamera().position.x+","+G.hero.getStage().getCamera().position.y+")");
						--G.hero.lock;--G.lockInput;
					}
				})
				));
		
	}

	void flow() {
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
		}else{
			for (IGObject p:getObject())
				p.forward(dir);
			G.playSound(TAG.SOUND_FLOW);
		}
	}

	private void heroFlow(IGSkill skill) {
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
		G.playSound(TAG.SOUND_FLOW);
		final TAG DIR=dir;
		if (checkDir((TAG)skill.getParams()[0]))
			G.hero.forward(dir);
		else{
			G.Log("Hero wait forward");
			G.hero.runAction(ASequence.$(
				AForever.$(ABreakIf.$(new IIfFunc() {public boolean onCall(Object[] params) {return G.hero.lock==0;}})),
				ACall.$(new ICallFunc() {public void onCall(Object[] params) { G.hero.forward(DIR);}})		
			));
		}
	}
	
	public boolean checkDir(TAG direction){
		TAG dir;
		if (check(TAG.TILE_STREAM_U1,TAG.TILE_STREAM_U2,TAG.TILE_STREAM_U3,TAG.TILE_STREAM_U4,
				  TAG.TILE_SAND_U1,  TAG.TILE_SAND_U2,  TAG.TILE_SAND_U3,  TAG.TILE_SAND_U4)) dir=TAG.DIR_DOWN; else
		if (check(TAG.TILE_STREAM_D1,TAG.TILE_STREAM_D2,TAG.TILE_STREAM_D3,TAG.TILE_STREAM_D4,
				  TAG.TILE_SAND_D1,  TAG.TILE_SAND_D2,  TAG.TILE_SAND_D3,  TAG.TILE_SAND_D4)) dir=TAG.DIR_UP; else
		if (check(TAG.TILE_STREAM_L1,TAG.TILE_STREAM_L2,TAG.TILE_STREAM_L3,TAG.TILE_STREAM_L4,
				  TAG.TILE_SAND_L1,  TAG.TILE_SAND_L2,  TAG.TILE_SAND_L3,  TAG.TILE_SAND_L4)) dir=TAG.DIR_RIGHT; else
		if (check(TAG.TILE_STREAM_R1,TAG.TILE_STREAM_R2,TAG.TILE_STREAM_R3,TAG.TILE_STREAM_R4,
				  TAG.TILE_SAND_R1,  TAG.TILE_SAND_R2,  TAG.TILE_SAND_R3,  TAG.TILE_SAND_R4)) dir=TAG.DIR_LEFT; else
			return true;
		return dir!=direction;		
	}
	
	private void objectMovedIn() {
		if (check(TAG.TILE_WATER,TAG.TILE_HOLE)){
			if (getObject()==null){
				if (G.log) System.out.print("!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!!!!!");
			}else{
				TAG tag = null;
				for (IGObject o:getObject()){
				//IGObject o=p;
					switch(o.getId()){
					case OBJ_ICE:tag=TAG.TILE_ICE;break;
					case OBJ_PULLABLE:tag=TAG.TILE_GROUND1;break;
					case OBJ_PUSHABLE:tag=TAG.TILE_GROUND1;break;
					case OBJ_BLOCK:tag=TAG.TILE_GROUND1;break;
					case OBJ_WATER:o.remove();return;
					default:if (G.log) System.out.println("!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!!!!!");break;
					}
					if  (check(TAG.TILE_WATER)) G.playSound(TAG.SOUND_WATER);
					else if (check(TAG.TILE_HOLE)) G.playSound(TAG.SOUND_FALL);
					setTile(tag);
					avaliable=true;
					o.remove();				
				}
			}
		}else{
			if (check(TAG.TILE_STREAM_U1,TAG.TILE_STREAM_U2,TAG.TILE_STREAM_U3,TAG.TILE_STREAM_U4,
					TAG.TILE_STREAM_D1,TAG.TILE_STREAM_D2,TAG.TILE_STREAM_D3,TAG.TILE_STREAM_D4,
					TAG.TILE_STREAM_L1,TAG.TILE_STREAM_L2,TAG.TILE_STREAM_L3,TAG.TILE_STREAM_L4,
					TAG.TILE_STREAM_R1,TAG.TILE_STREAM_R2,TAG.TILE_STREAM_R3,TAG.TILE_STREAM_R4)){
					if (getObject()==null) return;
					for (IGObject o:getObject()){
						switch(o.getId()){
						case OBJ_WATER:o.remove();return;
						default:if (G.log) System.out.println("!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!!!!!");break;
						}
					}
		}else{
			if (!check(TAG.TILE_SAND_U1,TAG.TILE_SAND_U2,TAG.TILE_SAND_U3,TAG.TILE_SAND_U4,
					TAG.TILE_SAND_D1,TAG.TILE_SAND_D2,TAG.TILE_SAND_D3,TAG.TILE_SAND_D4,
					TAG.TILE_SAND_L1,TAG.TILE_SAND_L2,TAG.TILE_SAND_L3,TAG.TILE_SAND_L4,
					TAG.TILE_SAND_R1,TAG.TILE_SAND_R2,TAG.TILE_SAND_R3,TAG.TILE_SAND_R4)) return;
			boolean b=false;
			for (IGObject o:getObject()) if (o.getId()==TAG.OBJ_WATER) b=true;
			if (b){
				if (check(XFlowManager.tsandd)) setTile(TAG.TILE_STREAM_D1); else
					if (check(XFlowManager.tsandu)) setTile(TAG.TILE_STREAM_U1); else
						if (check(XFlowManager.tsandl)) setTile(TAG.TILE_STREAM_L1); else
							if (check(XFlowManager.tsandr)) setTile(TAG.TILE_STREAM_R1);
				if (getObject()==null) return;
				for (IGObject o:getObject()){
					switch(o.getId()){
					case OBJ_WATER:o.remove();return;
					default:if (G.log) System.out.println("!!!!!!!!!!!!!!!!!error!!!!!!!!!!!!!!!!!!!!!!!");break;
					}
				}
			}
			
		}
		}
		
	}

	private void heroDead() {
		if (!check(TAG.TILE_WATER,TAG.TILE_HOLE)) return;
		if  (check(TAG.TILE_WATER)) G.playSound(TAG.SOUND_WATER);
		else if (check(TAG.TILE_HOLE)) G.playSound(TAG.SOUND_FALL);
		G.hero.dead();		
	}

	private void pedalActive() {
		if (!check(TAG.TILE_PEDAL_1,TAG.TILE_PEDAL_2,TAG.TILE_PEDAL_3,TAG.TILE_PEDAL_4)) return;
		G.toggleGroup.find((String)arg[1]).add();
		setTile(TAG.TILE_PEDAL_DOWN);
		G.playSound(TAG.SOUND_PEDAL);
	}

	@Override
	public void unactive(IGSkill skill) {
		if(G.log) System.out.println("UnactiveTile:("+x+","+y+") "+type+ " by "+skill);
		if (getObject()!=null) for (IGObject p:getObject()) p.unactive(skill);
		switch ((G.TAG)skill.getIndex()){
		case SKILL_MOVE:pedalUnactive();break;
		case SKILL_OBJECTMOVEDON:pedalUnactive();break;
		case SKILL_PUSH:
		}
	}
	
	

	private void pedalUnactive() {
		if (getTag()!=TAG.TILE_PEDAL_DOWN) return;
		if ((Integer)arg[0]!=2) return;
		G.toggleGroup.find((String)arg[1]).sub();
		setTile((Integer)arg[2],(TAG)arg[3]);
		G.playSound(TAG.SOUND_PEDAL);
	}

	@Override
	public boolean getIsAvaliable() {
		boolean ret = true;
		if (getObject()!=null){
			for (IGObject p:getObject())
				ret=ret&&p.getIsAvaliable();
		}
		return (avaliable)&&(ret);
	}
	
	@Override
	public boolean getIsAvaliableForObject() {
		//String s=map.map.getTileProperty(id, G.Label.Avaliable);
		return getTag()==TAG.TILE_HOLE||(getIsAvaliable()&&getTag()!=TAG.TILE_GROUND2);
	}
	
	@Override
	public boolean getIsAvaliableForJump() {
		if (type==TAG.TILE_OTHER) return false;  
		if (getObject()==null)
			return true;
		else{
			boolean ret = true;
			if (getObject()!=null){
				for (IGObject p:getObject())
					ret=ret&&p.getIsAvaliable();
			}
			return ret;
		}
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
	public IGObject[] getObject() {
		if (map==null||map.objectGroup==null) return null;
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

	public void fastSetTile(TAG tag, Object... arg) {
		map.map.layers.get(0).tiles[map.map.height-1-y][x]=id=toId(tag);
		type=tag;
		//if (G.log) System.out.println("Change to "+tag+" id is "+id);
		if (arg.length==0) return;
		if (arg.length>this.arg.length) this.arg=arg;
		else
			for (int i=0;i<arg.length;++i){
				this.arg[i]=arg[i];
			}
	}

	void saveTo(TileSaveStruct ss) {
		ss.id=id;
		ss.tag=type;
		ss.avaliable=avaliable;
		if (arg!=null){
			ss.arg=new Object[arg.length];
			for (int i=0;i<arg.length;++i){
				ss.arg[i]=arg[i];
			}
		}else 
			ss.arg=null;				
	}
	
	void loadFrom(TileSaveStruct ss){
		G.flowmanager.remove(this);
		map.map.layers.get(0).tiles[map.map.height-1-y][x]=id=ss.id;
		type=ss.tag;
		G.flowmanager.add(this);
		avaliable=ss.avaliable;
		arg=ss.arg;
	}

	public void reparseArg(ArrayList<String> iarg) {
		switch(type){
		default:return;
		case TILE_PEDAL_1:case TILE_PEDAL_2:case TILE_PEDAL_3:case TILE_PEDAL_4:case TILE_PEDAL:
			arg=new Object[]
			{Integer.decode(iarg.get(0)),iarg.get(1),id,type};
			break;
		case TILE_TELEPORT:arg=new Object[]{
							Integer.parseInt(iarg.get(0)),
							Integer.parseInt(iarg.get(1))
							};break;
		}
	}
}
