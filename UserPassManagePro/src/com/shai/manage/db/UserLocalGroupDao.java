package com.shai.manage.db;

import java.util.List;

import com.shai.manage.respondbean.UserPassGroupBean;
import com.shai.manage.respondbean.UserPassItemBean;

/**
 * 用户本地数据查询接口
 * 
 * @author Administrator
 * 
 */
public interface UserLocalGroupDao {

	/**
	 * 查询所有分组
	 * 
	 * @author wubo
	 * @createtime 2012-9-1
	 * @param name
	 * @return
	 */
	public List<UserPassGroupBean> getAllGroupBeans();

	/**
	 * 依据分组ID查询所有记录
	 * 
	 * @param groupID
	 * @return
	 */
	public List<UserPassItemBean> getAllGroupItemBeans(String groupID);

	/**
	 * 保存所有群组信息
	 * 
	 * @author wubo
	 * @createtime 2012-9-1
	 * @param UserPassGroupBeans
	 */
	public void saveAllUserPassGroupBean(List<UserPassGroupBean> UserPassGroupBeans);

	/**
	 * 保存一个组信息
	 * 
	 * @param bean
	 */
	public boolean saveUserPassGroup(UserPassGroupBean bean);

	/**
	 * 保存一个用户密码信息
	 * 
	 * @param bean
	 */
	public boolean saveUserPassItme(UserPassItemBean bean);
}
