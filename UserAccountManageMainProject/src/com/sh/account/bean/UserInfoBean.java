package com.sh.account.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 一条相关信息
 * @author Administrator
 * 2013年12月27日上午9:38:14
 */
public class UserInfoBean  {
	private int infoByFristGroupId;// 信息所属一级分组ID : QQ,微信 未知
	private int infoBySecondGroupId;// 信息所属二级分组ID : 共享OR不共享
	private int infoByThreeGroupId;// 信息所属三级分组ID
	
	private String infoID;
	private String infoDescribe;// 描述
	private int recentUseNum;// 最近使用次数,排序使用
	private int importanceLevel;//信息的重要性 1:一般 2:重要 3:危险
	
	private String userName;//一个用户名
	//为用户生成6个密码,在回答一个问题后精确到2个 pass 危险:五位
	private List<String> passList = new ArrayList<String>(); 
	private String questionOne;
	private String questionOneAnswer;
	public int getInfoByFristGroupId() {
		return infoByFristGroupId;
	}
	public void setInfoByFristGroupId(int infoByFristGroupId) {
		this.infoByFristGroupId = infoByFristGroupId;
	}
	public int getInfoBySecondGroupId() {
		return infoBySecondGroupId;
	}
	public void setInfoBySecondGroupId(int infoBySecondGroupId) {
		this.infoBySecondGroupId = infoBySecondGroupId;
	}
	public int getInfoByThreeGroupId() {
		return infoByThreeGroupId;
	}
	public void setInfoByThreeGroupId(int infoByThreeGroupId) {
		this.infoByThreeGroupId = infoByThreeGroupId;
	}
	public String getInfoID() {
		return infoID;
	}
	public void setInfoID(String infoID) {
		this.infoID = infoID;
	}
	public String getInfoDescribe() {
		return infoDescribe;
	}
	public void setInfoDescribe(String infoDescribe) {
		this.infoDescribe = infoDescribe;
	}
	public int getRecentUseNum() {
		return recentUseNum;
	}
	public void setRecentUseNum(int recentUseNum) {
		this.recentUseNum = recentUseNum;
	}
	public int getImportanceLevel() {
		return importanceLevel;
	}
	public void setImportanceLevel(int importanceLevel) {
		this.importanceLevel = importanceLevel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<String> getPassList() {
		return passList;
	}
	public void setPassList(List<String> passList) {
		this.passList = passList;
	}
	public String getQuestionOne() {
		return questionOne;
	}
	public void setQuestionOne(String questionOne) {
		this.questionOne = questionOne;
	}
	public String getQuestionOneAnswer() {
		return questionOneAnswer;
	}
	public void setQuestionOneAnswer(String questionOneAnswer) {
		this.questionOneAnswer = questionOneAnswer;
	}
	
	
}
