package com.me.inerface;

import com.me.game.G.TAG;

/**������<br/>
 * ����ʵ��*/
public interface IGObject {
	/**��ȡ�������������Ƭ*/
	IGTile getTile();
	
	/**��ȡ����������IGTMX*/
	IGTMX getTMX();
	
	/**ͨ�����ܴ���������<br/>
	 * Ӧ����IGTile��active(IGSkill skill)����*/
	void active(IGSkill skill);
	
	/**
	 * ͨ���ض����ܽ�����������Ƭ����������Ƭ�ϵķ����,<br/>
	 * ��������������<br/>
	 * �뿪һ����Ƭʱ��������뿪��Ƭ�Ĵ���<br/>
	 * @param skill ��������Ƭʱ���õļ��ܣ�null��ռ��ܣ�һ�ּ��ܣ�����û��ʹ�ü���
	 */
	public void unactive(IGSkill skill);
	
	/**��ȡ�������Ƿ��ͨ��*/
	public boolean getIsAvaliable();

	boolean getIsAvaliableForObject();

	TAG getId();
	
	public void remove();
	
	public void forward(final TAG dir);

}
