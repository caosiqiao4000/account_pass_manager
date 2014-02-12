package com.shai.manage.respondbean;

/**
 * 
 * @author Administrator 一条用户信息
 */
public class UserPassItemBean extends BaseResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 安全等级 "A" "AA" "AAA"
	private String securityLevel;
	// 所属群组ID 属于多个群组,以-间隔    属于层级1的编号id  qq 属于层级2的编号id 国内,国外 属于层级3的编号id  ...
	private String groupID;
	//
	private String describe;
	//
	private String userName;
	private String userPass;
	private int id;//本地表ID
	private int serverID;//
	// 更新时间
	private String updataTime;
	// 关联帐号
	private String userPhoneNum;
	private String userEmail;
	private String userRelevanceAccount;

	public int getServerID() {
		return serverID;
	}

	public void setServerID(int serverID) {
		this.serverID = serverID;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpdataTime() {
		return updataTime;
	}

	public void setUpdataTime(String updataTime) {
		this.updataTime = updataTime;
	}

	public String getUserPhoneNum() {
		return userPhoneNum;
	}

	public void setUserPhoneNum(String userPhoneNum) {
		this.userPhoneNum = userPhoneNum;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getUserRelevanceAccount() {
		return userRelevanceAccount;
	}

	public void setUserRelevanceAccount(String userRelevanceAccount) {
		this.userRelevanceAccount = userRelevanceAccount;
	}
	
}
