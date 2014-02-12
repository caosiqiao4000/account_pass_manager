package com.shai.manage.activity;

public class PassSetting {
	public static final boolean Debug = true;
	public static final String Debug_flag = "passManageflag";
	/**
	 * 传值时默认使用的KEY
	 */
	public static final String default_intent_keyone = "default_intent_keyone";
	public static final String default_intent_keytwo = "default_intent_keytwo";
	public static final String default_intent_keythree = "default_intent_keythree";
	public static final String default_intent_keyfour = "default_intent_keyfour";
	/**
	 * ContentFragment 类 创建区分cakekey
	 * 
	 * 首页 内容
	 */
	public static final int main_content_frist_flag = 0xaa1;
	/**
	 * 生成决不能内容页
	 */
	public static final int menu_fragment_flag = 0xaa2;

	/**
	 * 生成viewPage内容页
	 */
	public static final int menu_fragment_vp_flag = 0xaa3;
	/**
	 * 生成gridview内容页
	 */
	public static final int menu_fragment_grid_flag = 0xaa4;
	/**
	 * The default name for storing share history.
	 */
	public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";

	/**
	 * 安全等级 "A" "AA" "AAA"
	 */
	public static final String SECURITYLEVEL_ONE = "A";
	public static final String SECURITYLEVEL_TWO = "AA";
	public static final String SECURITYLEVEL_THREE = "AAA";

}
