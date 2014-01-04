package com.sh.account.respondbean;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回相应分组信息
 * 
 * @author Administrator 2014年1月3日上午10:42:15
 */
public class InfoGroupBean extends BaseResult{

	/**
	 * 2014年1月3日上午10:47:25
	 */
	private static final long serialVersionUID = 1614726599944577851L;
	private List<UserInfoGroupBean> groupBeans = new ArrayList<UserInfoGroupBean>();

	public List<UserInfoGroupBean> getGroupBeans() {
		return groupBeans;
	}

	public void setGroupBeans(List<UserInfoGroupBean> groupBeans) {
		this.groupBeans = groupBeans;
	}

}
