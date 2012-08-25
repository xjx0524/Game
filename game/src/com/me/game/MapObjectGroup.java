package com.me.game;

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
		if (G.tmx.getObject(G.hero.mapx, G.hero.mapy)!=null&&G.tmx.getObject(G.hero.mapx, G.hero.mapy).getId()==TAG.OBJ_DOOR)((MapObject)G.tmx.getObject(G.hero.mapx, G.hero.mapy)).y-=18;
		if (G.tmx.getObject(G.hero.mapx, G.hero.mapy-1)!=null&&G.tmx.getObject(G.hero.mapx, G.hero.mapy-1).getId()==TAG.OBJ_DOOR)((MapObject)G.tmx.getObject(G.hero.mapx, G.hero.mapy-1)).y-=18;
		heap.init(getActors().size());
		while (!getActors().isEmpty()){
			Actor o = getActors().get(0);
			heap.push(o);
			o.remove();
		}
		//if (G.log) System.out.print(heap.len);
		while (!heap.isEmpty()) 
			addActor(heap.pop());
		heap.fin();	
		G.hero.y+=17;
		if (G.tmx.getObject(G.hero.mapx, G.hero.mapy)!=null&&G.tmx.getObject(G.hero.mapx, G.hero.mapy).getId()==TAG.OBJ_DOOR)((MapObject)G.tmx.getObject(G.hero.mapx, G.hero.mapy)).y+=18;
		if (G.tmx.getObject(G.hero.mapx, G.hero.mapy-1)!=null&&G.tmx.getObject(G.hero.mapx, G.hero.mapy-1).getId()==TAG.OBJ_DOOR)((MapObject)G.tmx.getObject(G.hero.mapx, G.hero.mapy-1)).y+=18;
	}

 
	public void draw() {
		//if (G.log) System.out.println("BD"+getActors().size());
		sort();
		//if (G.log) System.out.println("AD"+getActors().size());
		super.draw();
	}

	 
	public IGObject getObject(int x, int y) {
		for (Actor p:getActors()){
			if (p instanceof MapObject){
				if (((MapObject)p).mapx==x&&((MapObject)p).mapy==y){
					return (MapObject)p;
				}
			}
		}
		return null;
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