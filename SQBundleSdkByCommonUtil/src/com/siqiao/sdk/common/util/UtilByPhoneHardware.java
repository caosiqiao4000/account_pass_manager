package com.siqiao.sdk.common.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 1.取手机号 设置ID 系统版本 型号 操作系统版本... 2.手机网络判断
 * 
 * <pre>
 * 3. 手机调用系统功能(照相机,浏览器,音乐,视频...)
 * 
 * 程序常用 的公共方法
 * 
 * @author Administrator
 * 
 */
public class UtilByPhoneHardware {

	/**
	 * 获取自身类名
	 * 
	 * @author wubo
	 * @createtime 2012-7-9
	 */
	public static String getClassName() {
		return new Throwable().getStackTrace()[1].getClassName();
	}

	/**
	 * 获取屏幕的大小
	 * 
	 * @param context
	 * 
	 * @return dm.widthPixels屏幕宽度 dm.heightPixels屏幕高度
	 */
	public static DisplayMetrics getPhoneScreenPixel(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);// dm用于获取手机屏幕大小
		return dm;
	}

	/**
	 * 检查是否有网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetworkIsActive(Context context) {
		boolean mIsNetworkUp = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			mIsNetworkUp = info.isAvailable();
		}
		return mIsNetworkUp;
	}
	
	/**
	 * 取得网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetworkType(Context context) {
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info != null) {
			return info.getType();
		}
		return -1;
	}


	/**
	 * 打开浏览器
	 * 
	 * @param context
	 * @param url
	 *            http://www.duotaozx.com http://xxx
	 */
	public static void toOSWebViewIntent(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(it);
	}

	/**
	 * 打开浏览器
	 * 
	 * @param context
	 * @param phoneNum
	 * 
	 */
	public static void toOSCallPhoneIntent(Context context, String phoneNum) {
		Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
		context.startActivity(it);
	}

	/**
	 * 判断SD卡目录是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isExitFileSD(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 将editView的光标设置到文字的最后
	 */
	public static void setEditCursorToTextEnd(EditText et_dialog) {
		CharSequence text = et_dialog.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}

	/**
	 * 隐藏手机号
	 * 
	 * @author wubo
	 * @createtime 2012-9-7
	 * @param phoneNum
	 * @return
	 */
	public static String hidePhoneNum(String phoneNum) {
		if (phoneNum == null) {
			return "";
		}
		if (phoneNum.length() == 11) {
			char[] cs = phoneNum.toCharArray();
			cs[3] = '*';
			cs[4] = '*';
			cs[5] = '*';
			cs[6] = '*';
			return new String(cs);
		}
		return phoneNum;
	}

	/**
	 * 删除指定的SD卡目录
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteFileSD(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 
	 * @param dir 一个程序的根目录名称
	 * @return
	 * 2016年1月20日 siqiao
	 */
	public static String getSdkardDir() {
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory().toString() + "/TeleMarket");
//			File file = new File(Environment.getExternalStorageDirectory().toString() + rootDir);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.toString();
		}
		return null;
	}

	/**
	 * 创建新的SD卡目录
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(String fileName) {
		try {
			File file = new File(getSdkardDir(), fileName);
			if (file.exists()) {
				file.delete();
			}
			if (file.createNewFile()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取版本编号
	 * 
	 * @author wubo
	 * @time 2012-11-23
	 * @param context
	 * @return
	 * 
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 将后台正常图片地址转换成自己需要的大小图片地址
	 * 
	 * @author caosq 2013-6-6 下午7:59:43
	 * @param url
	 *            后台正常大小的图片地址
	 * @param suffix
	 *            要获取的大小 如 FinalUtil.PIC_SMALL
	 * @return
	 */
	public static String converPicPath(String url, String suffix) {
		if (url == null || url.trim().length() < 1) {
			return null;
		}
		int a = url.lastIndexOf(".");
		String aString = url.substring(a, a + 1);
		return url.replace(aString, suffix + aString);
	}

	static long difference_time = 0;

	/**
	 * prompt user
	 * 
	 * @param context
	 * @param str
	 */
	public static void showToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int aa) {
		Toast.makeText(context, aa, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 取得当前时间 格式yyyy-MM-dd HH:MM:ss
	 * <p>
	 * 经过与服务器时间偏差处理
	 * 
	 * @return
	 */
	public static String getSysNowTime() {
		Date now = new Date();
		now = new Date(now.getTime() + difference_time);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		String formatTime = format.format(now);
		return formatTime;
	}

	/**
	 * 
	 * @param dateStr
	 *            yyyy-MM-dd HH:mm:ss "2008-08-08 12:10:12"
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date getDateByString(String dateStr) {
		if (null == dateStr) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 转换指定的日期
	 * @param date
	 * @return
	 * 2015年12月24日 siqiao
	 */
	public static String getStringByDate(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		String formatTime = format.format(date);
		return formatTime;
	}

	/**
	 * 系统时间与服务器时间差异
	 * 
	 * @author wubo
	 * @createtime 2012-9-12
	 * @param differenceTime
	 */
	// public static void setDifference_time() {
	// difference_time = Util.getServerSysTime() - new Date().getTime();
	// Log.e("myOpenfire", "系统时间与服务器时间差异 = " + difference_time);
	// }

	/**
	 * 获取毫秒级时间
	 * 
	 * @author wubo
	 * @createtime 2012-9-10
	 * @return
	 */
	public static String getLongDate() {
		return String.valueOf(new Date().getTime());
	}

	/** 判断邮箱是否存在 */
	public static boolean isEmpty(String x) {
		if (x == null) {
			return true;
		} else if (x.trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 转换
	 * 
	 * @author caosq 2013-6-8 下午4:49:43
	 * @return
	 */
	public static String getNowPullTime() {
		Date now = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
		String formatTime = format.format(now);
		return formatTime;
	}

	/**
	 * 验证手机号
	 * 
	 * @author wubo
	 * @createtime 2012-9-13
	 * @param str
	 * @return
	 */
	public static boolean isCellphone(String str) {
		String MOBILE_REGEX = "^((\\+86)|(86))?[1][3578]\\d{9}$";
		if (TextUtils.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile(MOBILE_REGEX);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	/**
	 *  [`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]
	 * @param str
	 * @return true 有特别字符 2015年11月18日 siqiao
	 */
	public static boolean isSpecialStr(String str) {

		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 验证邮箱
	 * 
	 * @author wubo
	 * @createtime 2012-9-13
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		// ^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param context
	 * @param imeiOrimsi
	 *            "imei" / "imsi"
	 * @return
	 */
	public static String getPhoneIMSIOrIMEI(Context context, String imeiOrimsi, String defaultStr) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String str;
		if ("imsi".equals(imeiOrimsi)) {
			str = mTelephonyMgr.getSubscriberId();
		} else {
			str = mTelephonyMgr.getDeviceId();
		}
		if (null == str) {
			return defaultStr;
		}
		return str;
	}

	/**
	 * 
	 * @param morePageInvestActivity
	 * @param nx_et_more_user_come_hk
	 * @param isBefore
	 *            判断 true 在当前时间之后 false (生日)在当前时间之前
	 * @return 2015年11月17日 siqiao
	 */
	public static void setEditViewDate(final Context context, final EditText nx_et, final boolean isBefore,
			final int promptId) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth;
		if (isBefore) {
			dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) + 1;
		} else {
			dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 1;
		}
		DatePickerDialog begindate = new DatePickerDialog(context, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, monthOfYear);
				cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
				String birthday_item = simple.format(cal.getTime());
				// true 选择的时间在当前时间之后
				boolean te = Calendar.getInstance().before(cal);
				if (te == isBefore) {
					nx_et.setText(birthday_item);
				} else {
					UtilByPhoneHardware.showToast(context, promptId);
				}
			}
		}, year, monthOfYear, dayOfMonth);
		begindate.show();
	}
}
