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
	// 属于安全层级1的编号
	private int securityLevelFristID;
	// 属于安全层级2的编号
	private int securityLevelSecondID;
	// 属于安全层级3的编号
	private int securityLevelThreeID;
	//
	private String describe;
	//
	private String userName;
	private String userPass;
	private int id;
	private String updataTime;
	private String userPhoneNum;
	private String userEmail;

	public int getSecurityLevelFristID() {
		return securityLevelFristID;
	}

	public void setSecurityLevelFristID(int securityLevelFristID) {
		this.securityLevelFristID = securityLevelFristID;
	}

	public int getSecurityLevelSecondID() {
		return securityLevelSecondID;
	}

	public void setSecurityLevelSecondID(int securityLevelSecondID) {
		this.securityLevelSecondID = securityLevelSecondID;
	}

	public int getSecurityLevelThreeID() {
		return securityLevelThreeID;
	}

	public void setSecurityLevelThreeID(int securityLevelThreeID) {
		this.securityLevelThreeID = securityLevelThreeID;
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

}
