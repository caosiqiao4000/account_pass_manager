package com.shai.manage.respondbean;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个分组信息
 * 
 * @author Administrator
 * 
 */
public class UserPassGroupBean  extends BaseResult {
	private int id;
	
	private String decribe;
	private String groupName;
	private List<UserPassItemBean> groups = new ArrayList<UserPassItemBean>();
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
	
}
