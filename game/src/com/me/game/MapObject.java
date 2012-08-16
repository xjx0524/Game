package com.me.game;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.aaction.AActionInterval;
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
	public Object[] arg;
	public String name;
	public MapObjectGroup group;
	private boolean lock = false;
	private AActionInterval move[] = new AActionInterval[4]; 

	static MapObject create(MapObjectGroup mapObjectGroup, TiledObject p){
		MapObject o=new MapObject();
		o.group=mapObjectGroup;
		o.tmx=G.tmx;
		o.mapx=p.x>>5;o.mapy=(int)o.tmx.getSize().y-1-(p.y>>5);		
		o.name=p.name;
		if (o.name==null) return null;
		o.tex=G.motp.getTexture(o.name);
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
		o.move[0]=AMoveBy.$(0.5f, 0,-32);
		o.move[1]=AMoveBy.$(0.5f, -32,0);
		o.move[2]=AMoveBy.$(0.5f, 32,0);
		o.move[3]=AMoveBy.$(0.5f, 0,32);
		return o;
		
	}
	
	private void parseArg(Map<String, String> map) {
	//	switch(name){
	//	default:return;
	//	}
		
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
		batch.draw (tex, x, y);
	}

	int gx,gy;
	 
	public void active(IGSkill skill) {
		if (lock) return;
		if(G.log) System.out.println("ActiveObject:("+mapx+","+mapy+") by "+skill);
		switch((G.TAG)skill.getIndex()){
		default:return;
		case SKILL_PULL:
		case SKILL_PUSH:pushedactive(skill);break;
		}		
	}


	@Override
	public void unactive(IGSkill skill) {
		if(G.log) System.out.println("UnactiveObject:("+mapx+","+mapy+") by "+skill);
		
	}
	
	private void pushedactive(final IGSkill skill) {
		lock=true;
		runAction(ASequence.$(
				ABreakIf.$(new IIfFunc(){
					public boolean onCall(Object[] params) {
						gx=mapx;gy=mapy;
						switch((TAG)skill.getParams()[0]){
						case DIR_DOWN:gy=mapy-1;break;
						case DIR_LEFT:gx=mapx-1;break;
						case DIR_RIGHT:gx=mapx+1;break;
						case DIR_UP:gy=mapy+1;break;
						}
						boolean b=(tmx.getTile(gx, gy)!=null)&&(tmx.getTile(gx, gy).getIsAvaliable());
						if (!b) lock=false;
						return !b;
					}				
				}),
				move[G.parseDirection((TAG)skill.getParams()[0])],
				ACall.$(new ICallFunc() {
					public void onCall(Object[] params) {
						mapx=gx;mapy=gy;
						x=mapx*32;y=mapy*32;
						lock=false;
						tmx.getTile(mapx, mapy).active(new Skill(G.TAG.SKILL_OBJECTMOVEDON));					
					}
				})
				));
	}
	
	@Override
	public boolean getIsAvaliableForObject() {
		return getIsAvaliable();
	}
}
