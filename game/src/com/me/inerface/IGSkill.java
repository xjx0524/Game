package com.me.inerface;

/**����ӿ�����ʵ��*/
public interface IGSkill {

	/**��ȡ��������
	 * @deprecated �ѷ���*/
	public String getName();
	/**��ȡ���ܱ��<br/>
	 * (��G.TAG��SKILLǰ׺�ı�ǩ)*/
	public Object getIndex();
	
	/**��ȡ���ܲ���*/
	public Object[] getParams();
	public boolean getIsForce();

}
