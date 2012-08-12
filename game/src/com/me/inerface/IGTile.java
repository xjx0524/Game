package com.me.inerface;

import java.util.Map;

import com.badlogic.gdx.math.Vector2;

/**��Ƭ*/

public interface IGTile {
	
	/**��������Ƭ����������Ƭ�ϵķ����<br/>
	 * @deprecated �ѷ�����(��void active(IGSkill skill);��������Ϊnull��ռ��ܣ�һ�ּ��ܣ���״̬����˺���)
	 */
	public void active();
	
	/**
	 * ͨ���ض����ܴ�������Ƭ����������Ƭ�ϵķ����,<br/>
	 * ����������<br/>
	 * 1.����һ���µ���Ƭʱ��Խ������Ƭ���д���<br/>
	 * 2.����һ���ƶ�������ΪĿ����Ƭ����ͨ�е����ƶ�ʧ�ܵ�����£������ͼ�������Ƭ���д���<br/>
	 * @param skill ��������Ƭʱ���õļ��ܣ�null��ռ��ܣ�һ�ּ��ܣ�����û��ʹ�ü���
	 * */
	public void active(IGSkill skill);
	
	/**
	 * ͨ���ض����ܽ�����������Ƭ����������Ƭ�ϵķ����,<br/>
	 * ��������������<br/>
	 * �뿪һ����Ƭʱ��������뿪��Ƭ�Ĵ���<br/>
	 * @param skill ��������Ƭʱ���õļ��ܣ�null��ռ��ܣ�һ�ּ��ܣ�����û��ʹ�ü���
	 */
	public void unactive(IGSkill skill);
	
	
	/**��ȡ����Ƭ״̬*/
	public IGTileState getState();
	
	/**��ȡ����Ƭ�Ƿ��ͨ��*/
	public boolean getIsAvaliable();
	
	/**��ȡ����Ƭ��Ļ����*/
	public Vector2 getLocation();
		
	/**��ȡ����Ƭ ��ͼ�߼�����*/
	public Vector2 getIndex();
	
	/**��ȡ����Ƭ����ķ�����*/
	public IGObject getObject();
	
	/**��ȡ����������IGTMX*/
	public IGTMX getTMX();

	/**��ȡ����Ƭ����*/
	public Map<String, String> getProperties();

}