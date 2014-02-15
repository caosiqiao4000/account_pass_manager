package com.siqi.geli.util;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 这是一个保存共享的数据工具类
 * 
 * @author Administrator
 * 
 */
public class SharedPreferencesUtil {
//	/**
//	 * 其他数据缓存
//	 */
//	private final String PREFERENCE_NAME = "shenhaisdkclient";
	/**
	 * 服务器接口更改缓存
	 */
	public static final String S_H_SERVICEADDRS_FLAG = "serviceAddrCache";

	private SharedPreferences mSharedPreferences;

	public SharedPreferencesUtil(Context context, String xmlName) {
		mSharedPreferences = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
	}

	// 保存字符串
	public void saveString(String key, String value) {
		mSharedPreferences.edit().putString(key, value).commit();
	}

	// 获取字符串
	public String getString(String key,String defaultStr) {
//		if (defValue.length > 0)
//			return mSharedPreferences.getString(key, defValue[0]);
//		else
			return mSharedPreferences.getString(key, defaultStr);

	}

	// 保存布尔值
	public void saveInt(String key, Integer value) {
		mSharedPreferences.edit().putInt(key, value).commit();
	}

	// 获取布尔值
	public Integer getInt(String key, Integer defValue) {
		return mSharedPreferences.getInt(key, defValue);
	}

	// 保存布尔值
	public void saveBoolean(String key, Boolean value) {
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	// 获取布尔值
	public Boolean getBoolean(String key, Boolean... defValue) {
		if (defValue.length > 0)
			return mSharedPreferences.getBoolean(key, defValue[0]);
		else
			return mSharedPreferences.getBoolean(key, false);

	}

	// 保存布尔值
	public void saveFloat(String key, Float value) {
		mSharedPreferences.edit().putFloat(key, value).commit();
	}

	// 获取布尔值
	public Float getFloat(String key, Float... defValue) {
		if (defValue.length > 0)
			return mSharedPreferences.getFloat(key, defValue[0]);
		else
			return mSharedPreferences.getFloat(key, 0.0f);

	}

	// 返回用户的信息。
	public JSONObject getJSON(String count) {
		JSONObject object;
		try {
			object = new JSONObject(mSharedPreferences.getString(count, ""));
			return object;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveJSONObject(String count, String json) {
		mSharedPreferences.edit().putString(count, json).commit();
	}

	public void removeJSONObject(String count) {
		try {
			mSharedPreferences.edit().remove(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, ?> getAll() {
		return mSharedPreferences.getAll();
	}
}
