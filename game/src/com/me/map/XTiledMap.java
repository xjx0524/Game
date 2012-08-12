package com.me.map;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.me.game.G;
import com.me.inerface.IGObject;
import com.me.inerface.IGObjectGroup;
import com.me.inerface.IGTMX;

public class XTiledMap implements IGTMX {
	
	public XTile[][][] tiles;
	public TiledMap map;
	public TileAtlas atlas;
	public TileMapRenderer tileMapRenderer;
	public IGObjectGroup objectGroup;
	public HashMap<String, String> properties;
	private Vector2 origin;
	public Vector2 startPos;
	private static Vector2 originc = new Vector2();
	
	public XTiledMap() {
		objectGroup=G.objectGroup;
	}
	
	@Override
	public XTile getTile(int x, int y) {
		if (x<0||y<0||x>=map.width||y>=map.height) return null;
		return tiles[0][y][x];
	}

	@Override
	public Vector2 getStartPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(float x, float y) {
		origin.set(x, y);
	}

	@Override
	public Vector2 getPosition() {
		return origin;
	}

	@Override
	public Vector2 getStartPointIndex() {
		return startPos;
	}

	@Override
	public Vector2 getSize() {
		return new Vector2(map.width, map.height);
	}

	@Override
	public IGObject getObject(int x, int y) {
		return objectGroup.getObject(x, y);
	}

	@Override
	public HashMap<String, String> getProperties() {
		return properties;
	}
	
	@Override
	public void draw(OrthographicCamera c) {
		tileMapRenderer.render(c);
		if (objectGroup==null) objectGroup=G.objectGroup;
		objectGroup.draw();
		originc.x=G.ScreenWidth/2-c.position.x;
		originc.y=G.ScreenHeight/2-c.position.y;
	}
	
	
	/**
	 * 将地图逻辑坐标转化为屏幕坐标<br/>
	 * （由于接口没法声明静态函数，所以这改成了抽象类）
	 * @param x x坐标（地图逻辑坐标）
	 * @param y y坐标（地图逻辑坐标）
	 * @return 屏幕坐标
	 */
	public static Vector2 toScreenCoordinate(float x,float y){
	//	if (G.log) System.out.println("("+x+","+y+")->("+(x*32+originc.x)+","+(y*32+originc.y)+")");
		return new Vector2(
				x*32+originc.x,
				y*32+originc.y
				);
		
	}
	
	/**
	 * 将屏幕坐标转化为地图逻辑坐标<br/>
	 * （由于接口没法声明静态函数，所以这改成了抽象类）
	 * @param x x坐标（屏幕坐标）
	 * @param y y坐标（屏幕坐标）
	 * @return 地图逻辑坐标
	 */
	public static Vector2 toMapCoordinate(float x,float y){
		//if (G.log) System.out.println("("+x+","+y+")->("+(((int)(x-originc.x))/32)+","+(((int)(y-originc.y))/32)+")");
		return new Vector2(
				((int)(x-originc.x))/32,
				((int)(y-originc.y))/32);
	} 
	
	public List<TiledObject> getObjects(){
		if (map.objectGroups==null||map.objectGroups.get(0)==null) return null;
		return map.objectGroups.get(0).objects;
	}
}
