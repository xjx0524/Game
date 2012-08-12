package com.me.inerface;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;

/**��ͼ<br/>
 * ��ʵ�ǳ����࣬���ǽӿڣ�<br/>
 * ������������Ӱɡ�*/

public interface  IGTMX {
	
	/**ͨ����ͼ�߼������ȡ��Ƭ<br/>
	 * @param x x���꣨��ͼ�߼����꣩
	 * @param y y���꣨��ͼ�߼����꣩
	 * @return ��ȡ����Ƭ*/
	public IGTile getTile(int x,int y);
	
	/**��ȡ������꣨��Ļ���꣩
	 * @deprecated �ѷ���
	 */
	public Vector2 getStartPoint();
	
	/**
	 * ����TMX��ԭ�����꣨��Ļ���꣩
	 * @param x x����
	 * @param y y����
	 * @deprecated ���ڲ�ȡ�ƶ���ͷ���ԣ��˺�������
	 */
	public void setPosition(float x,float y);
	
	/**
	 * ��ȡTMX��ԭ�����꣨��Ļ���꣩
	 * @return ����
	 * @deprecated ���ڲ�ȡ�ƶ���ͷ���ԣ��˺�������
	 */
	public Vector2 getPosition();
	
	/**��ȡ������꣨��ͼ�߼����꣩*/
	public Vector2 getStartPointIndex();
	
	/**��ȡ��ͼ��С����ͼ�߼����꣩*/
	public Vector2 getSize();
	
	/**��ȡ������
	 * @param x x���꣨��ͼ�߼����꣩
	 * @param y y���꣨��ͼ�߼����꣩
	 * @return ��ȡ�ķ�����*/
	public IGObject getObject(int x,int y);
	
	/**��ȡ����Ƭ����(��������������)*/
	public Map<String,String> getProperties();
	
	public void draw(OrthographicCamera c);
	
	public List<TiledObject> getObjects();

}
