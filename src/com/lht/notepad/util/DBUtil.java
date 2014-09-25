/**
 * @author WJZ
 */
package com.lht.notepad.util;

import com.lht.notepad.values.ConstantValue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtil extends SQLiteOpenHelper {
	
	/**
	 * @param context 上下文环境
	 * @param name 创建数据库的文件名
	 * @param factory 游标工厂
	 * @param version 数据库创建版本
	 */
	public DBUtil(Context context) {
		this(context, ConstantValue.DB_NAME, null, ConstantValue.DB_VERSION);
	}
	
	public DBUtil(Context context,int version) {
		this(context, ConstantValue.DB_NAME, null, version);
	}
	
	public DBUtil(Context context, String name,int version) {
		this(context, name, null, version);
	}
	
	public DBUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	/**
	 * 创建数据库函数，回调函数，在程序运行中只会调用一次
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + ConstantValue.TABLE_NAME + "(" +
				ConstantValue.DB_MetaData.NOTEID_COL + 
				" integer primary key autoincrement, " +
				ConstantValue.DB_MetaData.NOTETITLE_COL + 
				" varchar, " + ConstantValue.DB_MetaData.NOTECONTENT_COL + 
				" text, " + ConstantValue.DB_MetaData.NOTEDATE_COL + " date)");
	}
	
	/**
	 * 更新数据库
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + ConstantValue.TABLE_NAME);
		onCreate(db);
	}
		
	/**
	 * 关闭数据库
	 */
	public static void closeDB(SQLiteDatabase db) {
		if(db != null) {
			db.close();
		}
	}
	
	/**
	 *释放游标 
	 */
	public static void closeCursor(Cursor c) {
		if (c != null) {
			c.close();
		}
	}
}
