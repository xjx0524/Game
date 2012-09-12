package com.me.inerface;

import com.badlogic.gdx.graphics.Camera;


/**管理&绘制IGObject的类<br/>
 * 由我实现*/
public interface IGObjectGroup {

	/**绘图*/
	void draw();
	
	/**获取相应坐标上的放置物（地图坐标）*/
	IGObject[] getObject(int x,int y);
	
	/**获取相机*/
	Camera getCamera();
	
	public void init();

	public void save();

	public void load();

	void dispose();

	void clear();

}
