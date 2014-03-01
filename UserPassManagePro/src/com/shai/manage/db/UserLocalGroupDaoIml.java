package com.shai.manage.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.shai.manage.activity.PassSetting;
import com.shai.manage.respondbean.UserPassGroupBean;
import com.shai.manage.respondbean.UserPassItemBean;

public class UserLocalGroupDaoIml implements UserLocalGroupDao {
	public DBAdapter db;
	private Context context;

	public UserLocalGroupDaoIml(Context context) {
		this.context = context;
	}

	@Override
	public List<UserPassGroupBean> getAllGroupBeans() {
		List<UserPassGroupBean> beans = new ArrayList<UserPassGroupBean>();
		Cursor cursor = null;

		try {
			db = new DBAdapter(context);
			db.open();
			cursor = db.getAllDatas(db.TABLE_GROUP_USER_PASS);
			if (cursor == null)
				return beans;
			if (cursor.getCount() == 0)
				return beans;
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				UserPassGroupBean agentInfo = new UserPassGroupBean();
				agentInfo.setId(cursor.getInt(cursor
						.getColumnIndex(db.PASSGROUP_ID)));
				agentInfo.setServerid(cursor.getString(cursor
						.getColumnIndex(db.PASSGROUP_SERVER_ID)));
				agentInfo.setDecribe(cursor.getString(cursor
						.getColumnIndex(db.PASSGROUP_DECRIBE)));
				agentInfo.setGroupName(cursor.getString(cursor
						.getColumnIndex(db.PASSGROUP_NAME)));
				agentInfo.setUseNum(cursor.getInt(cursor
						.getColumnIndex(db.PASSGROUP_USENUM)));
				agentInfo.setIcon(cursor.getString(cursor
						
						.getColumnIndex(db.PASSGROUP_ICON)));
				beans.add(agentInfo);
			}
		} catch (Exception e) {
			return beans;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				if (db.mSQLiteDatabase != null) {
					db.mSQLiteDatabase.close();
				}
				db.close();
			}
		}
		return beans;
	}

	@Override
	public List<UserPassItemBean> getAllGroupItemBeans(String groupID) {
		List<UserPassItemBean> citys = new ArrayList<UserPassItemBean>();
		Cursor cursor = null;
		try {
			db = new DBAdapter(context);
			db.open();
			String sql = "select * from " + db.TABLE_USER_PASS + " where "
					+ db.PASS_MULT_GROUP_id + " like '%"+groupID+"%'";
			if (PassSetting.Debug) {
				Log.i(PassSetting.Debug_flag,"sql = "+sql);
			}
			cursor = db.mSQLiteDatabase.rawQuery(sql, new String[] {});
			if (cursor == null)
				return citys;
			if (cursor.getCount() == 0)
				return citys;

			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				UserPassItemBean city = new UserPassItemBean();
				city.setServerID(cursor.getInt(cursor
						.getColumnIndex(db.PASS_SERVER_ID)));
				city.setSecurityLevel(cursor.getString(cursor
						.getColumnIndex(db.PASS_SECURITY_LEVEL)));
				city.setDescribe(cursor.getString(cursor
						.getColumnIndex(db.PASS_DECRIBE)));
				city.setUserName(cursor.getString(cursor
						.getColumnIndex(db.PASS_NAME)));
				city.setUserPass(cursor.getString(cursor
						.getColumnIndex(db.PASS_VALUE)));
				city.setGroupID(cursor.getString(cursor
						.getColumnIndex(db.PASS_MULT_GROUP_id)));
				city.setUpdataTime(cursor.getString(cursor
						.getColumnIndex(db.PASS_UPDATA)));
				city.setUserPhoneNum(cursor.getString(cursor
						.getColumnIndex(db.PASS_PHONE_NUM)));
				city.setUserEmail(cursor.getString(cursor
						.getColumnIndex(db.PASS_USER_EMAIL)));

				citys.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				if (db.mSQLiteDatabase != null) {
					db.mSQLiteDatabase.close();
				}
				db.close();
			}
		}
		return citys;
	}

	@Override
	public void saveAllUserPassGroupBean(
			List<UserPassGroupBean> userPassGroupBeans) {
		try {
			db = new DBAdapter(context);
			db.open();
			db.mSQLiteDatabase.beginTransaction();
			for (int i = 0; i < userPassGroupBeans.size(); i++) {
				UserPassGroupBean bean = userPassGroupBeans.get(i);
				ContentValues cv = new ContentValues();
				cv.put(db.PASSGROUP_SERVER_ID, bean.getServerid());
				cv.put(db.PASSGROUP_NAME, bean.getGroupName());
				cv.put(db.PASSGROUP_DECRIBE, bean.getDecribe());
				cv.put(db.PASSGROUP_USENUM, bean.getUseNum());
				cv.put(db.PASSGROUP_ICON, bean.getIcon());
				db.insertData(db.TABLE_GROUP_USER_PASS, cv);
			}
			db.mSQLiteDatabase.setTransactionSuccessful();
			if (PassSetting.Debug) {
				Log.e(PassSetting.Debug_flag, "saveAllUserPassGroupBean end: "
						+ new Date().getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			db.mSQLiteDatabase.endTransaction();
			if (db != null) {
				if (db.mSQLiteDatabase != null) {
					db.mSQLiteDatabase.close();
				}
				db.close();
			}
		}
	}

	@Override
	public boolean saveUserPassItme(UserPassItemBean bean) {
		boolean isSuccess = true;
		try {
			db = new DBAdapter(context);
			db.open();
			db.mSQLiteDatabase.beginTransaction();

			ContentValues cv = new ContentValues();
			cv.put(db.PASS_SERVER_ID, bean.getServerID());
			cv.put(db.PASS_DECRIBE, bean.getDescribe());
			cv.put(db.PASS_NAME, bean.getUserName());
			cv.put(db.PASS_VALUE, bean.getUserPass());
			cv.put(db.PASS_SECURITY_LEVEL, bean.getSecurityLevel());
			cv.put(db.PASS_MULT_GROUP_id, bean.getGroupID());
			cv.put(db.PASS_UPDATA, bean.getUpdataTime());
			cv.put(db.PASS_PHONE_NUM, bean.getUserPhoneNum());
			cv.put(db.PASS_USER_EMAIL, bean.getUserEmail());
			cv.put(db.PASS_USER_RELEVANCE_ACCOUNT,
					bean.getUserRelevanceAccount());

			db.insertData(db.TABLE_USER_PASS, cv);
			db.mSQLiteDatabase.setTransactionSuccessful();
			if (PassSetting.Debug) {
				Log.e(PassSetting.Debug_flag, "saveAllUserPassGroupBean end: "
						+ new Date().getTime());
			}
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		} finally {
			db.mSQLiteDatabase.endTransaction();
			if (db != null) {
				if (db.mSQLiteDatabase != null) {
					db.mSQLiteDatabase.close();
				}
				db.close();
			}
		}
		return isSuccess;
	}

	@Override
	public boolean saveUserPassGroup(UserPassGroupBean bean) {
		// TODO Auto-generated method stub
		return false;
	}
}
