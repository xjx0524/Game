package com.me.inerface;

import com.me.game.G.TAG;

/**放置物<br/>
 * 由我实现*/
public interface IGObject {
	/**获取该物体下面的瓦片*/
	IGTile getTile();
	
	/**获取其所属耳朵IGTMX*/
	IGTMX getTMX();
	
	/**通过技能触发该物体<br/>
	 * 应该由IGTile。active(IGSkill skill)调用*/
	void active(IGSkill skill);
	
	/**
	 * 通过特定技能结束触发该瓦片（包括该瓦片上的放置物）,<br/>
	 * 结束触发条件：<br/>
	 * 离开一个瓦片时会结束对离开瓦片的触发<br/>
	 * @param skill 触发此瓦片时所用的技能，null或空技能（一种技能）代表没有使用技能
	 */
	public void unactive(IGSkill skill);
	
	/**获取该物体是否可通行*/
	public boolean getIsAvaliable();

	boolean getIsAvaliableForObject();

	TAG getId();
	
	public void remove();
	
	public void forward(final TAG dir);

}
