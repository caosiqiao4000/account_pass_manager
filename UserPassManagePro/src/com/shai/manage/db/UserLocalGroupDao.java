package com.shai.manage.db;

import java.util.List;

import com.shai.manage.respondbean.UserPassGroupBean;


/**
 * 用户本地数据查询接口
 * @author Administrator
 *
 */
public interface UserLocalGroupDao {
	
	 /**
     * 根据城市名称查询下级城市
     * 
     * @author wubo
     * @createtime 2012-9-1
     * @param name
     * @return
     */
    public List<UserPassGroupBean> getAllGroupBeans();

    /**
     * 保存所有群组信息
     * 
     * @author wubo
     * @createtime 2012-9-1
     * @param UserPassGroupBeans
     */
    public void saveAllUserPassGroupBean(List<UserPassGroupBean> UserPassGroupBeans);

    /**
     * 根据ID查询一个群组下的信息
     * 
     * @author caosq 2013-4-28 下午2:37:26
     * @param UserPassGroupBeans
     */
    public UserPassGroupBean queryUserPassGroupBeanById(String id);
    
}
