package com.me.inerface;

/**这个接口由我实现*/
public interface IGSkill {

	/**获取技能名字
	 * @deprecated 已废弃*/
	public String getName();
	/**获取技能编号<br/>
	 * (即G.TAG中SKILL前缀的标签)*/
	public Object getIndex();
	
	/**获取技能参数*/
	public Object[] getParams();
	public boolean getIsForce();

}
