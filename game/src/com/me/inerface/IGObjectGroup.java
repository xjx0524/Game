package com.me.inerface;

import com.badlogic.gdx.graphics.Camera;


/**����&����IGObject����<br/>
 * ����ʵ��*/
public interface IGObjectGroup {

	/**��ͼ*/
	void draw();
	
	/**��ȡ��Ӧ�����ϵķ������ͼ���꣩*/
	IGObject getObject(int x,int y);
	
	/**��ȡ���*/
	Camera getCamera();

}