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
            	agentInfo.setServerid(cursor.getString(cursor.getColumnIndex(db.PASSGROUP_SERVER_ID)));
            	agentInfo.setDecribe(cursor.getString(cursor.getColumnIndex(db.PASSGROUP_DECRIBE)));
            	agentInfo.setGroupName(cursor.getString(cursor.getColumnIndex(db.PASSGROUP_NAME)));
            	agentInfo.setUseNum(cursor.getInt(cursor.getColumnIndex(db.PASSGROUP_USENUM)));
            	agentInfo.setIcon(cursor.getString(cursor.getColumnIndex(db.PASSGROUP_ICON)));
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
	            	Log.e(PassSetting.Debug_flag, "saveAllUserPassGroupBean end: " + new Date().getTime());
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
	public UserPassGroupBean queryUserPassGroupBeanById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
