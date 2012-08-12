package com.me.inerface;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;

/**地图<br/>
 * 其实是抽象类，不是接口，<br/>
 * 名字问题就无视吧。*/

public interface  IGTMX {
	
	/**通过地图逻辑坐标获取瓦片<br/>
	 * @param x x坐标（地图逻辑坐标）
	 * @param y y坐标（地图逻辑坐标）
	 * @return 获取的瓦片*/
	public IGTile getTile(int x,int y);
	
	/**获取起点坐标（屏幕坐标）
	 * @deprecated 已废弃
	 */
	public Vector2 getStartPoint();
	
	/**
	 * 设置TMX的原点坐标（屏幕坐标）
	 * @param x x坐标
	 * @param y y坐标
	 * @deprecated 由于采取移动镜头策略，此函数废弃
	 */
	public void setPosition(float x,float y);
	
	/**
	 * 获取TMX的原点坐标（屏幕坐标）
	 * @return 坐标
	 * @deprecated 由于采取移动镜头策略，此函数废弃
	 */
	public Vector2 getPosition();
	
	/**获取起点坐标（地图逻辑坐标）*/
	public Vector2 getStartPointIndex();
	
	/**获取地图大小（地图逻辑坐标）*/
	public Vector2 getSize();
	
	/**获取放置物
	 * @param x x坐标（地图逻辑坐标）
	 * @param y y坐标（地图逻辑坐标）
	 * @return 获取的放置物*/
	public IGObject getObject(int x,int y);
	
	/**获取该瓦片属性(包括放置物属性)*/
	public Map<String,String> getProperties();
	
	public void draw(OrthographicCamera c);
	
	public List<TiledObject> getObjects();

}
