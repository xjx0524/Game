package com.me.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.game.G.TAG;
import com.me.inerface.IGObject;
import com.me.inerface.IGObjectGroup;
import com.me.inerface.IGTMX;

public class MapObjectGroup extends Stage implements IGObjectGroup {
	
	private IGTMX tmx;
	private Heap heap=new Heap();
	private ArrayList<ObjectSaveStruct> ss=new ArrayList<ObjectSaveStruct>();
	
	public MapObjectGroup() {
		super(G.ScreenWidth, G.ScreenHeight, true, G.batch);
		G.objectGroup=this;
		tmx=G.tmx;
		if (G.hasmap){
			if (tmx.getObjects()!=null)
				for (TiledObject p:tmx.getObjects()){
					MapObject o=MapObject.create(this,p);
					if (o!=null)
						addActor(o);
				}
		}
	}
	
	private void sort(){
		G.hero.y-=17;
		ArrayList<MapObject> door=new ArrayList<MapObject>(); 
		ArrayList<MapObject> water=new ArrayList<MapObject>();
		heap.init(getActors().size());
		while (!getActors().isEmpty()){
			Actor o = getActors().get(0);
			if (o instanceof MapObject){
				if (((MapObject)o).getId()==TAG.OBJ_DOOR)
				{
					door.add((MapObject)o);
					o.y-=1;
				}
				if (((MapObject)o).getId()==TAG.OBJ_WATER)
				{
					water.add((MapObject)o);
					o.y+=33;
				}
			}
			heap.push(o);
			o.remove();
		}
		//if (G.log) System.out.print(heap.len);
		while (!heap.isEmpty()) 
			addActor(heap.pop());
		heap.fin();	
		G.hero.y+=17;
		for (MapObject p:door) p.y+=1;
		for (MapObject p:water) p.y-=33;
		door.clear();
		water.clear();
		door=null;
		water=null;
	}

 
	public void draw() {
		//if (G.log) System.out.println("BD"+getActors().size());
		sort();
		//if (G.log) System.out.println("AD"+getActors().size());
		super.draw();
	}

	 
	public IGObject[] getObject(int x, int y) {
		if (getActors().size()==0) return null;
		ArrayList<IGObject> ret=new ArrayList<IGObject>(); 
		ret.clear();
		for (Actor p:getActors()){
			if (p instanceof MapObject){
				if (((MapObject)p).mapx==x&&((MapObject)p).mapy==y){
					if (((MapObject)p).getIsVisible()) 
						ret.add((IGObject)p);
				}
			}
		}
		if (ret.size()==0)
			return null;
		else{
			//G.Log("ObjectCount at("+x+","+y+"): "+ret.size());
			IGObject[] r=new IGObject[ret.size()];
			for (int i=0;i<ret.size();++i) r[i]=ret.get(i);
			return r;
		}
			
	}
	
	public void init(){
		if (getActors().size()==0) return;
		for (Actor p:getActors()){
			if (p instanceof MapObject)
				G.tmx.getTile(((MapObject)p).mapx,((MapObject)p).mapy).active(new Skill(TAG.SKILL_OBJECTMOVEDON,TAG.DIR_NONE));
		}
	}
	
	public void save(){
		ss.clear();
		for (Actor p:getActors()){
			if (p instanceof MapObject){
				ss.add(new ObjectSaveStruct((MapObject)p));
			}
		}
	}
	
	public void load(){
		if (ss==null||ss.size()==0) return;
		for (ObjectSaveStruct p:ss){
			p.load();
		}
	}

}

final class Heap{
	
	Actor[] a;
	int len;
	
	void init(int i){
		a = new Actor[i+1];
		len = 0;
	}
	
	void fin(){
		len = 0;
		a = null;
		
	}
	
	void push(Actor e){
		a[++len] = e;
		int i = len;
		while (i>1&&a[i>>1].y<a[i].y){
			a[i]=a[i>>1];
			i>>=1;
			a[i]=e;
		}
	}
	
	boolean isEmpty(){ return len==0;}
	
	Actor pop(){
		Actor e = a[1];
		Actor o=a[1]=a[len--];
		if (len==0) return e;
		int i=1;
		while ((i<<1)<len&&(a[i].y<a[i<<1].y||a[i].y<a[(i<<1)+1].y)){
			if (a[i<<1].y>a[(i<<1)+1].y){
				a[i]=a[i<<1];
				i=i<<1;
				a[i]=o;
			}else
			{
				a[i]=a[(i<<1)+1];
				i=(i<<1)+1;
				a[i]=o;
			}
		}
		while ((i<<1)<=len&&(a[i].y<a[i<<1].y)){
			a[i]=a[i<<1];
			i=i<<1;
			a[i]=o;
		}
		return e;
	}
}