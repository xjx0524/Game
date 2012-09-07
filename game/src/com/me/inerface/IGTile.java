package com.me.inerface;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.me.game.G;
import com.me.game.G.TAG;

/**瓦片*/

public interface IGTile {
	
	/**触发该瓦片（包括该瓦片上的放置物）<br/>
	 * @deprecated 已废弃，(由void active(IGSkill skill);函数参数为null或空技能（一种技能）的状态代替此函数)
	 */
	public void active();
	
	/**
	 * 通过特定技能触发该瓦片（包括该瓦片上的放置物）,<br/>
	 * 触发条件：<br/>
	 * 1.进入一个新的瓦片时会对进入的瓦片进行触发<br/>
	 * 2.在向一方移动并且因为目标瓦片不可通行导致移动失败的情况下，会对试图进入的瓦片进行触发<br/>
	 * @param skill 触发此瓦片时所用的技能，null或空技能（一种技能）代表没有使用技能
	 * */
	public void active(IGSkill skill);
	
	/**
	 * 通过特定技能结束触发该瓦片（包括该瓦片上的放置物）,<br/>
	 * 结束触发条件：<br/>
	 * 离开一个瓦片时会结束对离开瓦片的触发<br/>
	 * @param skill 触发此瓦片时所用的技能，null或空技能（一种技能）代表没有使用技能
	 */
	public void unactive(IGSkill skill);
	
	
	/**获取该瓦片状态*/
	public IGTileState getState();
	
	/**获取该瓦片是否可通行*/
	public boolean getIsAvaliable();
	public boolean getIsAvaliableForObject();
	public boolean getIsAvaliableForJump();
	
	/**获取该瓦片屏幕坐标*/
	public Vector2 getLocation();
		
	/**获取该瓦片 地图逻辑坐标*/
	public Vector2 getIndex();
	
	/**获取该瓦片上面的放置物*/
	public IGObject[] getObject();
	
	/**获取其所属耳朵IGTMX*/
	public IGTMX getTMX();

	/**获取该瓦片属性*/
	public Map<String, String> getProperties();
	
	public G.TAG getTag();
	
	public boolean checkDir(TAG direction);

}
