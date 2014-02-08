package com.shai.manage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	// 数据库名称
	private final String DB_NAME = "gsta.db";
	// 数据库版本
	private final int DB_VERSION = 2;

	private Context mContext = null;
	public SQLiteDatabase mSQLiteDatabase = null;
	public DatabaseHelper mDatabaseHelper = null;
	public final String MYUID = "my_uid";

	/**
	 * 联系人表
	 */
	public final String TABLE_CONTACTS = "ContactsTable";
	public final String CONTACTS_SID = "_id";
	public final String CONTACTS_ID = "contact_id";
	public final String CONTACTS_USERNAME = "username";
	public final String CONTACTS_PHONENUM = "phonenum";
	public final String CONTACTS_SORTKEY = "sortkey";
	public final String CONTACTS_FIRSTLETTER = "firstLetter";

	/**
	 * 地区表
	 */
	public final String TABLE_CITY = "CityTable"; // 表名
	public final String CITY_CID = "_id";
	public final String CITY_ID = "id";
	public final String CITY_PARENTID = "parentid";
	public final String CITY_NAME = "name";

	/**
	 * 营销备忘录录音文件地址
	 */
	public final String TABLE_NOTE = "noteTable";
	public final String NOTE_ID = "note_id";// 营销备忘录编号
	public final String NOTE_FILEPATH = "note_filepath";// 文件地址

	/**
	 * 程序中使用过的上传的图片 在手机中的位置关系保存下来
	 * <p>
	 * 在加载图片时,优先使用手机里的图片,不从网络加载
	 */
	public final String TABLE_PIC_PATH = "picPathTable"; // 文件地址
	public final String PATH_DB_ID = "path_db_id"; // 主键ID
	public final String SERVER_DB_ID = "server_db_id"; // 服务器对应的主键ID
	public final String PIC_NATIVE_PATH = "picNativePath"; // 更改的图片在手机中的地址
	public final String PIC_CHANGE_TIME = "buyermsg_time"; // 更改的时间

	/**
	 * 手机用户数据群组表
	 */
	public final String TABLE_GROUP_USER_PASS = "passGroupTable";
	public final String PASSGROUP_ID = "passgroup_id"; // 主键ID
	public final String PASSGROUP_SERVER_ID = "server_db_id"; // 服务器对应的主键ID
	public final String PASSGROUP_DECRIBE = "db_decribe";
	public final String PASSGROUP_USENUM = "db_useNum";
	public final String PASSGROUP_ICON = "db_icon";
	public final String PASSGROUP_NAME = "db_group_name";

	/**
	 * 手机用户数据表(一条数据)
	 */
	public final String TABLE_USER_PASS = "passInfoTable";
	public final String PASS_ID = "passgroup_db_id"; // 主键ID
	public final String PASS_SERVER_ID = "server_db_id"; // 服务器对应的主键ID
	public final String PASS_DECRIBE = "db_decribe";
	public final String PASS_NAME = "pass_name";
	public final String PASS_VALUE = "pass_value";
	public final String PASS_SECURITY_LEVEL = "pass_security";
	public final String PASS_FIRST_LEVEL_id = "pass_first_id";
	public final String PASS_SECOND_LEVEL_id = "pass_second_id";
	public final String PASS_THREE_LEVEL_id = "pass_three_id";
	public final String PASS_UPDATA = "pass_change_time";
	public final String PASS_PHONE_NUM = "pass_phone_num";
	public final String PASS_USER_EMAIL = "pass_user_email";

	/**
	 * 创建手机用户数据群组表
	 */
	private String SQL_CREATE_GROUP_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_GROUP_USER_PASS + " (" + PASSGROUP_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + PASSGROUP_SERVER_ID
			+ " VARCHAR(15)," + PASSGROUP_DECRIBE + " VARCHAR(100),"
			+ PASSGROUP_USENUM + " VARCHAR(15)," + PASSGROUP_ICON
			+ " VARCHAR(100)," + PASSGROUP_NAME + " VARCHAR(15))";

	/**
	 * 创建手机用户数据群组表
	 */
	private String SQL_CREATE_USER_PASS_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_USER_PASS + " (" + PASS_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + PASS_SERVER_ID
			+ " VARCHAR(15)," + PASS_DECRIBE + " VARCHAR(100)," + PASS_NAME
			+ " VARCHAR(45)," + PASS_VALUE + " VARCHAR(45),"
			+ PASS_SECURITY_LEVEL + " VARCHAR(15)," + PASS_FIRST_LEVEL_id
			+ " VARCHAR(15)," + PASS_SECOND_LEVEL_id + " VARCHAR(15),"
			+ PASS_THREE_LEVEL_id + " VARCHAR(15)," + PASS_UPDATA
			+ " VARCHAR(15)," + PASS_PHONE_NUM + " VARCHAR(15),"
			+ PASS_USER_EMAIL + " VARCHAR(15))";

	/**
	 * 创建联系人表
	 */
	private String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_CONTACTS + " (" + CONTACTS_SID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTACTS_ID + " INTEGER,"
			+ MYUID + " VARCHAR(15)," + CONTACTS_USERNAME + " VARCHAR(15),"
			+ CONTACTS_PHONENUM + " VARCHAR(60)," + CONTACTS_SORTKEY
			+ " VARCHAR(15)," + CONTACTS_FIRSTLETTER + " VARCHAR(15))";

	/**
	 * 创建城市表
	 */
	private String SQL_CREATE_CITY_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_CITY + " (" + CITY_CID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CITY_ID + " VARCHAR(20),"
			+ CITY_PARENTID + " VARCHAR(20)," + CITY_NAME + " VARCHAR(50))";

	/**
	 * 创建手机本地图片与上传图片的关系
	 */
	private String SQL_CREATE_PicNativePath_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_PIC_PATH
			+ " ("
			+ PATH_DB_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ SERVER_DB_ID
			+ " VARCHAR(40),"
			+ PIC_NATIVE_PATH
			+ " VARCHAR(300),"
			+ PIC_CHANGE_TIME + " VARCHAR(20))";

	/**
	 * 创建营销备忘录录音文件地址表
	 * 
	 * @param context
	 */
	private String SQL_CREATE_NOTE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NOTE + " (" + MYUID + " VARCHAR(20), " + NOTE_ID
			+ " VARCHAR(20)," + NOTE_FILEPATH + " VARCHAR(20));";

	public DBAdapter(Context context) {
		mContext = context;
	}

	private class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_CONTACT_TABLE);
			db.execSQL(SQL_CREATE_CITY_TABLE);
			db.execSQL(SQL_CREATE_GROUP_USER_TABLE);
			db.execSQL(SQL_CREATE_USER_PASS_TABLE);
			db.execSQL(SQL_CREATE_NOTE_TABLE);
			db.execSQL(SQL_CREATE_PicNativePath_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			// alert table 原表A to 临时表temp_A
			// create 表A
			// insert into A select from temp_A
		}
	}

	/**
	 * 数据库升级
	 * 
	 * @author wubo
	 * @createtime 2012-9-12
	 */
	public void updateTable() {

	}

	// 打开数据库
	public synchronized void open() {
		try {
			mDatabaseHelper = new DatabaseHelper(mContext);
			if (mSQLiteDatabase == null) {
				mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	// 关闭数据库
	public void close() {
		if (mDatabaseHelper != null) {
			mDatabaseHelper.close();
		}
	}

	public Cursor rawQuery(String sql) {
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.rawQuery(sql, null);
		} catch (Exception e) {
		}
		return mCursor;
	}

	public Cursor rawQuery(String sql, String[] value) {
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.rawQuery(sql, value);
		} catch (Exception e) {
		}
		return mCursor;
	}

	// 插入一条数据
	public long insertData(String table, ContentValues initialValues) {
		return mSQLiteDatabase.insert(table, "_id", initialValues);
	}

	// 通过Cursor查询所有指定字段的数据
	public Cursor getAllDatas(String table, String[] keyString) {
		return mSQLiteDatabase.query(table, keyString, null, null, null, null,
				null);
	}

	// 通过Cursor查询所有指定字段的数据
	public Cursor getDatasbyLimit(String table, String[] keyString,
			String limitString) {
		return mSQLiteDatabase.query(table, keyString, null, null, null, null,
				null, limitString);
	}

	// 按条件，通过Cursor查询指定字段的数据
	public Cursor getAllDatas(String table, String[] keyString, String key,
			String value) {
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.query(true, table, keyString, key + "='"
					+ value + "'", null, null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
		} catch (Exception e) {
		}
		return mCursor;
	}

	// 通过Cursor查询所有数据
	public Cursor getAllDatas(String table) {
		String selectSQL = "select * from " + table;
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.rawQuery(selectSQL, null);
		} catch (Exception e) {
		}
		return mCursor;
	}

	// 通过Cursor查询所有数据
	public Cursor getAllDatas1(String table, String order) {
		String selectSQL = "select * from " + table + " Order By " + order;
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.rawQuery(selectSQL, null);
		} catch (Exception e) {
		}
		return mCursor;
	}

	// 按条件，通过Cursor查询数据
	public Cursor getAllDatas(String table, String wherekey, String wherevalue) {
		String selectSQL = "select * from " + table + " where " + wherekey
				+ " = '" + wherevalue + "'";
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.rawQuery(selectSQL, null);
		} catch (Exception e) {
		}
		return mCursor;
	}

	// 按条件，通过Cursor查询数据
	public Cursor getAllDatas(String table, String wherekey, String wherevalue,
			String orderby) {
		String selectSQL = "Select * From " + table + " Where " + wherekey
				+ " = '" + wherevalue + "' Order By " + orderby;
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.rawQuery(selectSQL, null);
		} catch (Exception e) {
		}
		return mCursor;
	}

	// 按条件，通过Cursor查询数据
	public Cursor getAllDatasexcept(String table, String wherekey,
			String wherevalue, String order) {
		String selectSQL = "select * from " + table + " where " + wherekey
				+ " != '" + wherevalue + "' Order By " + order;
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.rawQuery(selectSQL, null);
		} catch (Exception e) {
		}
		return mCursor;
	}

	/**
	 * 删,改
	 * 
	 * @author wubo
	 * @createtime 2012-9-8
	 * @param sql
	 * @param values
	 */
	public void deleteOrUpdate(String sql, Object[] values) {
		mSQLiteDatabase.execSQL(sql, values);
	}

	/**
	 * 查询
	 * 
	 * @author wubo
	 * @createtime 2012-9-8
	 * @param sql
	 * @param values
	 * @return
	 */
	public Cursor select(String sql, String[] values) {
		Cursor mCursor = null;
		try {
			mCursor = mSQLiteDatabase.rawQuery(sql, values);
		} catch (Exception e) {
			return null;
		}
		return mCursor;
	}

}
