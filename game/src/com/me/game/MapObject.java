package com.me.game;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.aaction.AActionInterval;
import com.me.aaction.AAnimate;
import com.me.aaction.ABreakIf;
import com.me.aaction.ACall;
import com.me.aaction.AMoveBy;
import com.me.aaction.ASequence;
import com.me.aaction.ASprite;
import com.me.aaction.ICallFunc;
import com.me.aaction.IIfFunc;
import com.me.game.G.TAG;
import com.me.inerface.IGObject;
import com.me.inerface.IGSkill;
import com.me.inerface.IGTMX;
import com.me.inerface.IGTile;
import com.me.map.XTiledMap;

public class MapObject extends ASprite implements IGObject {
	
	public int mapx,mapy;
	private float sx,sy;
	public IGTMX tmx;
	private TextureRegion tex;
	public boolean avaliable;
	public Object[] arg=new Object[5];
	public String name;
	public MapObjectGroup group;
	private boolean lock = false;
	private AActionInterval move[] = new AActionInterval[4];
	private G.TAG id;
	private boolean visibleOfGet = true;
	
	@Override
	public G.TAG getId(){return id;}

	static MapObject create(MapObjectGroup mapObjectGroup, TiledObject p){
		MapObject o=new MapObject();
		o.group=mapObjectGroup;
		o.tmx=G.tmx;
		o.mapx=p.x>>5;o.mapy=(int)o.tmx.getSize().y-1-(p.y>>5);		
		o.name=p.name;
		if (o.name==null) return null;
		o.tex=G.motp.getTexture(o.name);
		o.id=parseName(o.name);
		String s;
		Map<String,String> map=p.properties;
		if (map!=null){
			s=map.get(G.Label.OAvaliable);
			o.avaliable=("true").equals(s);
			o.parseArg(map);
		}else 
			o.avaliable=false;
		Vector2 v=XTiledMap.toScreenCoordinate(o.mapx, o.mapy);
		o.sx=v.x;o.sy=v.y;
		o.x=o.mapx*32;o.y=o.mapy*32;
		o.move[0]=AMoveBy.$(0.25f, 0,-32);
		o.move[1]=AMoveBy.$(0.25f, -32,0);
		o.move[2]=AMoveBy.$(0.25f, 32,0);
		o.move[3]=AMoveBy.$(0.25f, 0,32);
		
		return o;		
	}
	
	private static TAG parseName(String name) {
		if (name.equalsIgnoreCase("block"))		return G.TAG.OBJ_BLOCK; else
		if (name.equalsIgnoreCase("pushable"))	return G.TAG.OBJ_PUSHABLE; else
		if (name.equalsIgnoreCase("pullable"))	return G.TAG.OBJ_PULLABLE; else
		if (name.equalsIgnoreCase("door"))		return G.TAG.OBJ_DOOR; else
		if (name.equalsIgnoreCase("ice"))		return G.TAG.OBJ_ICE; else
		if (name.equalsIgnoreCase("water"))		return G.TAG.OBJ_WATER; else
		if (name.equalsIgnoreCase("wall"))		return G.TAG.OBJ_WALL; else
		return null;
	}

	private void parseArg(Map<String, String> map) {
		switch(id){
		default:return;
		case OBJ_DOOR:	if (map.get(G.Label.OArg0).equals("1")) arg[0]=1; else
						if (map.get(G.Label.OArg0).equals("2")) arg[0]=2; else
						if (map.get(G.Label.OArg0).equals("3")) arg[0]=3; 
						arg[1]=TAG.DOOR_CLOSE;break;
	//	case OBJ_ICE:
	//	case OBJ_WATER: arg[0]=G.TAG.valueOf(map.get(G.Label.OArg0));break;
		}		
	}
	
	/**获取该物体是否可通行*/
	public boolean getIsAvaliable(){
		return avaliable;		
	}

	 
	public IGTile getTile() {
		return getTMX().getTile(mapx, mapy);
	}

	 
	public IGTMX getTMX() {
		return tmx;
	}

	 
	public Actor hit(float x, float y) {
		return null;
	}
	
	 
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		Vector2 v=XTiledMap.toScreenCoordinate(mapx,mapy);
		sx = v.x; sy = v.y;
		if (sx<-48||sy<-32||sx>G.ScreenWidth||sy>G.ScreenHeight) return;
		batch.setColor(color);
		batch.draw (tex, x, y);
	}

	int gx,gy;
	 
	public void active(IGSkill skill) {
		if (isMarkedToRemove()) return;
		if (lock&&!skill.getIsForce()) {
			G.Log(("Refuse to ActiveObject:("+mapx+","+mapy+") "+id+" by "+skill));
			return;
		}
		if(G.log) System.out.println("ActiveObject:("+mapx+","+mapy+") "+id+" by "+skill);
		switch((G.TAG)skill.getIndex()){
		default:return;
		case SKILL_PULL:pulledActive(skill);break;
		case SKILL_PUSH:pushedActive(skill);break;
		case SKILL_FOOTSTEP:footstepActive(skill);break;
		case SKILL_FREEZE:freeze(skill);break;
		case SKILL_THAW:thaw(skill);break;
		case SKILL_OBJECTMOVEDON:closeDoor();break;
		}		
	}


	@Override
	public void unactive(IGSkill skill) {
		if (isMarkedToRemove()) return;
		if(G.log) System.out.println("UnactiveObject:("+mapx+","+mapy+") "+id+" by "+skill);
		switch((G.TAG)skill.getIndex()){
		default:return;
		case SKILL_FOOTSTEP:footstepUnactive(skill);break;
		case SKILL_MOVE:
		case SKILL_OBJECTMOVEDON:doorLeave(skill);break;
		}
	}
	
	private void doorLeave(IGSkill skill) {
		if (id!=TAG.OBJ_DOOR) return;
		if (arg[1]==TAG.DOOR_WAIT) closeDoor();
		else
			footstepUnactive(skill);
		
	}

	public void forward(final TAG dir){
		if (isMarkedToRemove()) return;
		++G.lockInput;++G.hero.lock;
		G.Log("LocInput:"+G.lockInput+" HeroLock:"+G.hero.lock+"  ---In MapObject.forward");
		lock=true;
		runAction(ASequence.$(
				ABreakIf.$(new IIfFunc(){
					public boolean onCall(Object[] params) {
						gx=mapx;gy=mapy;
						switch(dir){
						case DIR_DOWN:gy=mapy-1;break;
						case DIR_LEFT:gx=mapx-1;break;
						case DIR_RIGHT:gx=mapx+1;break;
						case DIR_UP:gy=mapy+1;break;
						}
						boolean b=(tmx.getTile(gx, gy)!=null)&&(tmx.getTile(gx, gy).getIsAvaliableForObject());
						b=b&&(gx!=G.hero.mapx||gy!=G.hero.mapy);
						if (b) {
							visibleOfGet=false;
							G.Log("Object "+id+" becomes invisible");							
							tmx.getTile(mapx, mapy).unactive(new Skill(G.TAG.SKILL_OBJECTMOVEDON,dir));
							mapx=gx;mapy=gy;
						}
						if (!b) 
						{
							--G.lockInput;lock=false;--G.hero.lock;
							G.Log("LocInput:"+G.lockInput+" HeroLock:"+G.hero.lock);
						}
						return !b;
					}				
				}),
				move[G.parseDirection(dir)],
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {
						mapx=gx;mapy=gy;
						x=mapx*32;y=mapy*32;
						--G.lockInput;--G.hero.lock;lock=false;
						G.Log("LocInput:"+G.lockInput+" HeroLock:"+G.hero.lock);
						visibleOfGet=true;
						G.Log("Object "+id+" becomes visible");
						tmx.getTile(mapx, mapy).active(new Skill(G.TAG.SKILL_OBJECTMOVEDON,dir));
						G.signToSave = true;
					}
				})
				));
	}
	
	private void pushedActive(final IGSkill skill) {
		if (id!=TAG.OBJ_PULLABLE&&id!=TAG.OBJ_PUSHABLE&&id!=TAG.OBJ_ICE) return;
		G.playSound(TAG.SOUND_PUSH);
		forward((TAG)skill.getParams()[0]);
	}
	
	private void pulledActive(final IGSkill skill) {
		if (id!=TAG.OBJ_PULLABLE) return;
		G.playSound(TAG.SOUND_PUSH);
		forward((TAG)skill.getParams()[0]);
	}
	
	private void footstepActive(IGSkill skill) {
		if (id!=TAG.OBJ_DOOR) return;
		switch ((Integer)arg[0]){
		case 1:openDoor();break;
		case 2:openDoor();break;
		case 3:	if (arg[1]==TAG.DOOR_CLOSE) {
					openDoor();
				}else{
					closeDoor();
				}
				break;			
		}
		avaliable=(arg[1]!=TAG.DOOR_CLOSE);
	}

	private void footstepUnactive(IGSkill skill) {
		if (id!=TAG.OBJ_DOOR) return;
		if (skill.getIndex()!=TAG.SKILL_FOOTSTEP){
			if ((Integer)arg[0]==1&&arg[1]==TAG.DOOR_WAIT) {
				//tex=G.motp.getTexture("door");
				closeDoor();
			}
		}else{
			if ((Integer)arg[0]==1) {
				//tex=G.motp.getTexture("door");
				closeDoor();
			}
		}
	}
	
	private void openDoor(){
		if (id!=TAG.OBJ_DOOR) return;
		if ((TAG)arg[1]==TAG.DOOR_CLOSE) {
			animate(TAG.AA_DOOROPEN);
			G.playSound(TAG.SOUND_DOOR);
		}
		arg[1]=TAG.DOOR_OPEN;
	}
	
	private void closeDoor(){
		if (id!=TAG.OBJ_DOOR) return;
		//if ((Integer)arg[0]==2) return;
		boolean b = ( mapx==G.hero.mapx && mapy==G.hero.mapy );
		if (!b){
			for (IGObject p:getTile().getObject()){
				if (p!=this)
				{
					b=true;
					break;
				}
			}
		}
		if(b) {
			if (arg[1]==TAG.DOOR_CLOSE){
				animate(TAG.AA_DOOROPEN);
				G.playSound(TAG.SOUND_DOOR);
			}
			arg[1]=TAG.DOOR_WAIT;
		}else{
			if (arg[1]!=TAG.DOOR_CLOSE){
				arg[1]=TAG.DOOR_CLOSE;
				animate(TAG.AA_DOORCLOSE);
				G.playSound(TAG.SOUND_DOOR);
				avaliable=false;
			}
		}
	}
	
	
	
	@Override
	public boolean getIsAvaliableForObject() {
		return getIsAvaliable();
	}
	
	private void thaw(IGSkill skill) {
		if (getId()!=TAG.OBJ_ICE) return;
		name="water";
		id=TAG.OBJ_WATER;
		tex=G.motp.getTexture(name);
		avaliable=true;
		for (IGObject p:getTMX().getObject(mapx, mapy)){
			if (p!=this&&((MapObject)p).getId()==TAG.OBJ_WATER){
				markToRemove(true);
			}
		}
		//G.playSound(TAG.SOUND_THAW);
	}

	private void freeze(IGSkill skill) {
		if (getId()!=TAG.OBJ_WATER) return;
		boolean b = true;
		for (IGObject p:getTMX().getObject(mapx, mapy)){
			if (p!=this){
				b=false;				
			}
		}
		if (!b) return;
		name="ice";
		id=TAG.OBJ_ICE;
		tex=G.motp.getTexture(name);
		avaliable=false;
		//G.playSound(TAG.SOUND_FREEZE);
	}
	
	void animate(TAG tag){
		AAnimate a;
		switch(tag){
		case AA_DOORCLOSE:a=AAnimate.$(new Animation(0.05f, G.motp.getTexture("door3"),G.motp.getTexture("door2"),G.motp.getTexture("door1"),G.motp.getTexture("door")));break;
		case AA_DOOROPEN :a=AAnimate.$(new Animation(0.05f, G.motp.getTexture("door1"),G.motp.getTexture("door2"),G.motp.getTexture("door3"),G.motp.getTexture("door_open")));break;
		default: return;
		}
		++G.lockInput;
		G.Log("LocInput:"+G.lockInput+" HeroLock:"+G.hero.lock);
		runAction(ASequence.$(
				a,
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {
						--G.lockInput;
						G.Log("LocInput:"+G.lockInput+" HeroLock:"+G.hero.lock);
					}
				})
				));
	}

	@Override
	public TextureRegion getTextureRegion() {
		return tex;
	}

	@Override
	public void setTextureRegion(TextureRegion textureRegion) {
		tex=textureRegion;		
	}

	public boolean getIsVisible() {
		return visibleOfGet;
	}
}