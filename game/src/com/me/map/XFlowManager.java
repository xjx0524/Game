package com.me.map;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.me.game.G;
import com.me.game.G.TAG;
import com.me.inerface.IGTile;

public class XFlowManager {
	
	private XTiledMap tmx;
	private float frameTime;
	private float elapsedTime;
	private Vector<IGTile> sandu=new Vector<IGTile>();
	private Vector<IGTile> sandd=new Vector<IGTile>();
	private Vector<IGTile> sandl=new Vector<IGTile>();
	private Vector<IGTile> sandr=new Vector<IGTile>();
	private Vector<IGTile> streamu=new Vector<IGTile>();
	private Vector<IGTile> streamd=new Vector<IGTile>();
	private Vector<IGTile> streaml=new Vector<IGTile>();
	private Vector<IGTile> streamr=new Vector<IGTile>();
	private TAG[] tstreamu=new TAG[]{TAG.TILE_STREAM_U1,TAG.TILE_STREAM_U2,TAG.TILE_STREAM_U3,TAG.TILE_STREAM_U4};
	private TAG[] tstreamd=new TAG[]{TAG.TILE_STREAM_D1,TAG.TILE_STREAM_D2,TAG.TILE_STREAM_D3,TAG.TILE_STREAM_D4};
	private TAG[] tstreaml=new TAG[]{TAG.TILE_STREAM_L1,TAG.TILE_STREAM_L2,TAG.TILE_STREAM_L3,TAG.TILE_STREAM_L4};
	private TAG[] tstreamr=new TAG[]{TAG.TILE_STREAM_R1,TAG.TILE_STREAM_R2,TAG.TILE_STREAM_R3,TAG.TILE_STREAM_R4};
	private TAG[] tsandu=new TAG[]{TAG.TILE_SAND_U1,TAG.TILE_SAND_U2,TAG.TILE_SAND_U3,TAG.TILE_SAND_U4};
	private TAG[] tsandd=new TAG[]{TAG.TILE_SAND_D1,TAG.TILE_SAND_D2,TAG.TILE_SAND_D3,TAG.TILE_SAND_D4};
	private TAG[] tsandl=new TAG[]{TAG.TILE_SAND_L1,TAG.TILE_SAND_L2,TAG.TILE_SAND_L3,TAG.TILE_SAND_L4};
	private TAG[] tsandr=new TAG[]{TAG.TILE_SAND_R1,TAG.TILE_SAND_R2,TAG.TILE_SAND_R3,TAG.TILE_SAND_R4};	
	
	public XFlowManager(float frameTime){
		tmx=G.tmx;
		XTile tmp;
		for (int i=0;i<tmx.map.height;++i)
			for (int j=0;j<tmx.map.width;++j){
				if ((tmp=tmx.getTile(j, i)).check(tsandd)) sandd.add(tmp); else
				if ((tmp=tmx.getTile(j, i)).check(tsandu)) sandu.add(tmp); else
				if ((tmp=tmx.getTile(j, i)).check(tsandl)) sandl.add(tmp); else
				if ((tmp=tmx.getTile(j, i)).check(tsandr)) sandr.add(tmp); else
				if ((tmp=tmx.getTile(j, i)).check(tstreamd)) streamd.add(tmp); else
				if ((tmp=tmx.getTile(j, i)).check(tstreamu)) streamu.add(tmp); else
				if ((tmp=tmx.getTile(j, i)).check(tstreaml)) streaml.add(tmp); else
				if ((tmp=tmx.getTile(j, i)).check(tstreamr)) streamr.add(tmp);
			}
		this.elapsedTime=0;
		this.frameTime=frameTime;
		G.flowmanager=this;		
	}
	
	private void update(Vector<IGTile> v,TAG[] tags,int i){
		if (v.size()==0) return;
		for (IGTile p:v){
			((XTile)p).fastSetTile(tags[i]);
		}
	}
	
	public void update(){
		elapsedTime+=Gdx.graphics.getDeltaTime();
		while (elapsedTime>4*frameTime) elapsedTime-=4*frameTime;
		update(sandu,tsandu,(int)(elapsedTime/frameTime));
		update(sandd,tsandd,(int)(elapsedTime/frameTime));
		update(sandl,tsandl,(int)(elapsedTime/frameTime));
		update(sandr,tsandr,(int)(elapsedTime/frameTime));
		update(streamu,tstreamu,(int)(elapsedTime/frameTime));
		update(streamd,tstreamd,(int)(elapsedTime/frameTime));
		update(streaml,tstreaml,(int)(elapsedTime/frameTime));
		update(streamr,tstreamr,(int)(elapsedTime/frameTime));
	}
	
	
	public boolean add(IGTile tile){
		if (((XTile)tile).check(tstreamu)) streamu.add(tile); else
		if (((XTile)tile).check(tstreamd)) streamd.add(tile); else
		if (((XTile)tile).check(tstreaml)) streaml.add(tile); else
		if (((XTile)tile).check(tstreamr)) streamr.add(tile); else
		if (((XTile)tile).check(tsandu)) sandu.add(tile); else
		if (((XTile)tile).check(tsandd)) sandd.add(tile); else
		if (((XTile)tile).check(tsandl)) sandl.add(tile); else
		if (((XTile)tile).check(tsandr)) sandr.add(tile); else
		return false;
		return true;
	}
	
	public boolean remove(IGTile tile){
		if (((XTile)tile).check(tstreamu)) streamu.remove(tile); else
		if (((XTile)tile).check(tstreamd)) streamd.remove(tile); else
		if (((XTile)tile).check(tstreaml)) streaml.remove(tile); else
		if (((XTile)tile).check(tstreamr)) streamr.remove(tile); else
		if (((XTile)tile).check(tsandu)) sandu.remove(tile); else
		if (((XTile)tile).check(tsandd)) sandd.remove(tile); else
		if (((XTile)tile).check(tsandl)) sandl.remove(tile); else
		if (((XTile)tile).check(tsandr)) sandr.remove(tile); else
		return false;
		return true;
	}
		
}