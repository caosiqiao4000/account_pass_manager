package com.shai.manage.respondbean;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个分组信息
 * 
 * @author Administrator
 * 
 */
public class UserPassGroupBean extends BaseResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String serverid;

	private String decribe;
	private String icon;
	private String groupName;
	// 最近一个月使用次数   每一个月末时,将使用次数折半
	private int useNum;
	
	private List<UserPassItemBean> groups = new ArrayList<UserPassItemBean>();

	public UserPassGroupBean() {
	};

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public UserPassGroupBean(String icon, String groupName) {
		this.icon = icon;
		this.groupName = groupName;
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDecribe() {
		return decribe;
	}

	public void setDecribe(String decribe) {
		this.decribe = decribe;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<UserPassItemBean> getGroups() {
		return groups;
	}

	public void setGroups(List<UserPassItemBean> groups) {
		this.groups = groups;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getUseNum() {
		return useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}

}
