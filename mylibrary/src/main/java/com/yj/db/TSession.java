package com.yj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yj.entity.Constants;
import com.yj.entity.Session;
import com.yj.util.Utils;

/**
 * @author lufengkai 
 * @date 2015年5月25日
 * @copyright 游鹏科技
 */
public class TSession {
	
	private static TSession tSession;
	private SQLiteDatabase mDb;
	
	
	/**
	 * 单例
	 * @return
	 */
	public static TSession getInstance(Context context){
		if(tSession  == null){
			tSession = new TSession(context);
		}
		return tSession;
	}
	
	private TSession(Context context){
		DatabaseHelper helper = new DatabaseHelper(context);
		mDb = helper.getWritableDatabase();
	}
	
	
	
	/**
	 * 数据库
	 * @author lufengkai 2014-7-16
	 *
	 */
	class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, Constants.DBNAME, null, 3);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// 创建用户基本信息表
						db.execSQL("create table if not exists "
								+ Constants.TABLE_NAME_SESSION
								+ " ( _id integer primary key autoincrement , " 
								+ Constants.USERID
								+ " String, " 
								+ Constants.USERNAME 
								+ " String, " 
								+ Constants.PASSWORD
								+ " String , " 
								+ Constants.EMAIL 
								+ " String , " 
								+ Constants.MONEY
								+ " integer, " 
								+ Constants.AUTOLOGIN 
								+ " integer, " 
								+ Constants.LASTLOGINTIME
								+ " long " + ");");		
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			Utils.youaiLog("Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_SESSION);
			onCreate(db);
		}
		
	}
	
	/**
	 * 更新或者添加用户信息
	 * 
	 * @param session
	 *            用户信息
	 * @return 是否更新成功
	 */
	public boolean update(Session session) {
		if (session == null || mDb == null)
			return false;
		
		//获取帐号密码
		String useName = Utils.encode(session.userAccount);
		String password=Utils.encode(session.password);
		
//		if (session.newPwd != null) {//假如新密码不为空  修改密码
//			removeSessionByAccountId(useName); //删除表记录
//		} else {
			if (isHasAccount(useName,password)) {//假如已经注册过
//				MyLog.d(" has account");
				return false;
			} else {
//				MyLog.d("new account");
//			}
		}

		ContentValues value = parseContenValues(session);
		// System.out.println("content value -> " + value);
		long rows = mDb.update(Constants.TABLE_NAME_SESSION, value, Constants.USERNAME + "=?",
				new String[] {useName });
		if (rows <= 0) {
			rows = mDb.insert(Constants.TABLE_NAME_SESSION, null, value);
		}
		return rows > 0;
	}
	
	/**
	 * 删除对应用户名的用户信息
	 * 
	 * @param userName
	 *            用户名
	 * @return 是否删除成功
	 */
	public boolean removeSessionByAccountId(String userName) {
		if (mDb == null)
			return false;
		return mDb.delete(Constants.TABLE_NAME_SESSION, Constants.USERNAME + "=?",
				new String[] {userName }) > 0;
	}
	
	/**
	 * @param password 
	 * 验证密码
	 */
	public boolean isHasAccount(String userName, String password) {
		if (mDb == null) {
			return false;
		}
//		MyLog.d("string" + userName);
		Cursor query = mDb
				.query(Constants.TABLE_NAME_SESSION, null, Constants.USERNAME + "=?",
                                       new String[] {userName }, null, null, Constants.LASTLOGINTIME
                                                                             + " desc ");//按插入时间排序
//		MyLog.d("query.getCount()" + query.getCount());
		if (query.getCount() < 1) {
			return false;
		} else {
			Session session=getSessionByUserName(userName);
			if (password.equals(session.password)) {
				return true;
			}else {
				return false;
			}	
			
		}

	}
	
	/**
	 * 根据用户名获取对应的用户信息
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户信息
	 */
	public Session getSessionByUserName(String userName) {
		if (userName == null)
			return null;
		return getSessionBySeletion(Constants.USERNAME + "=?", new String[] {userName });
	}
	
	/**
	 * 获取符合条件的单一用户信息
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @return 用户信息
	 */
	public Session getSessionBySeletion(String selection, String[] selectionArgs) {
		if (mDb == null) {
			return null;
		}
		Session user = null;
		Cursor query = mDb.query(Constants.TABLE_NAME_SESSION, null, selection,
                                         selectionArgs, null, null, Constants.LASTLOGINTIME + " desc ");
		if (query.moveToFirst()) {
			if (!query.isAfterLast()) {
				user = getSession(query);
			}
		}
		query.close();
//		MyLog.d("getSessionBySeletion");
		return user;
	}
	
	private Session getSession(Cursor cursor) {
		Session session = new Session();
		// 用户名解码
		session.userAccount = Utils.decode(cursor.getString(cursor
				.getColumnIndex(Constants.USERNAME)));
		// 密码解码
		session.password = Utils.decode(cursor.getString(cursor
				.getColumnIndex(Constants.PASSWORD)));
//		session.bindEmail = cursor.getString(cursor.getColumnIndex(Constants.EMAIL));
		
		return session;
	}
	
	private ContentValues parseContenValues(Session session) {
		if (session == null)
			return null;
		ContentValues value = new ContentValues();
		// 编码保存用户名
		value.put(Constants.USERNAME, Utils.encode(session.userAccount));
		// 编码保存密码
		value.put(Constants.PASSWORD, Utils.encode(session.password));
//		value.put(Constants.EMAIL, session.bindEmail);
		value.put(Constants.LASTLOGINTIME, System.currentTimeMillis());
		return value;
	}

}
