package com.siqi.geli.localbean;

import java.io.Serializable;

/**
 * 我的加盟商家信息
 * 
 * @author: chenjd
 * @createTime: 2012-9-8 下午3:24:19
 * @version: 1.0
 * @desc:
 */
public class AgentInfo implements Serializable {

	private String uid;// 加盟商家唯一标识
	private String name;// 加盟商家名称
	private String cityId;// 地市
	private String grade;// 等级
	private Integer goods;// 销售商品数
	private String userName;// 加盟商家姓名
	private String mobile;// 手机
	private String phone;// 固话
	private String email;// 邮箱
	private String qq;// qq
	private String msn;// MSN
	private String zipCode;// 邮编
	private String address;// 联系地址
	private String webUrl;//网址

	public AgentInfo() {
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getGoods() {
		return goods;
	}

	public void setGoods(Integer goods) {
		this.goods = goods;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	
	
}
