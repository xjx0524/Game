package com.me.inerface;

import com.badlogic.gdx.graphics.Camera;


/**����&����IGObject����<br/>
 * ����ʵ��*/
public interface IGObjectGroup {

	/**��ͼ*/
	void draw();
	
	/**��ȡ��Ӧ�����ϵķ������ͼ���꣩*/
	IGObject[] getObject(int x,int y);
	
	/**��ȡ���*/
	Camera getCamera();
	
	public void init();

	public void save();

	public void load();

	void dispose();

	void clear();

}
