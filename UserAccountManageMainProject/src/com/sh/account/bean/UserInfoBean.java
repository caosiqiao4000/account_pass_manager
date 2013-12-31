package com.sh.account.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 一条相关信息
 * @author Administrator
 * 2013年12月27日上午9:38:14
 */
public class UserInfoBean  {
	private int infoByFristGroupId;// 信息所属一级分组 : QQ,微信 未知
	private int infoBySecondGroupId;// 信息所属二级分组 : 共享OR不共享
	private int infoByThreeGroupId;// 信息所属三级分组 : 共享OR不共享
	
	private String infoID;
	private String infoDescribe;// 描述
	private int recentUseNum;// 最近使用次数,排序使用
	private int importanceLevel;//信息的重要性 1:一般 2:重要 3:危险
	
}
