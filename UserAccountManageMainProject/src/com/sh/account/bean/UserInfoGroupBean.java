package com.sh.account.bean;

import java.util.ArrayList;
import java.util.List;

import com.sh.account.respondbean.BaseResult;

/**
 * 在主页上显示用户组信息
 * 
 * @author Administrator
 * 
 */
public class UserInfoGroupBean extends BaseResult {

	private int userGroupID;// 分组ID
	/**
	 * 信息所属一级分组 : QQ,微信 未知 信息所属二级分组 : 共享OR不共享;警告,一般与危险
	 * 如果是一般,则保存本地,警告则提示,危险都保存到SERVER
	 * 
	 * 警告,危险级开始不显示或者只显示头尾,通过提问后(验证码)显示
	 * 
	 * 信息所属三级分组 : 暂留 格式 0001_0001_0001 一级分组ID_二级分组ID_三级分组ID 如果不属于,填 0
	 */
	private String groupLevelByGroupId;

	private String groupIDName;// 组名
	private List<UserInfoBean> infos = new ArrayList<UserInfoBean>();
	private String groupDescribe;// 描述
	private int recentUseNum;// 最近使用次数,排序使用

}
