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
	 * @param context �����Ļ���
	 * @param name �������ݿ���ļ���
	 * @param factory �α깤��
	 * @param version ���ݿⴴ���汾
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
	 * �������ݿ⺯�����ص��������ڳ���������ֻ�����һ��
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
	 * �������ݿ�
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + ConstantValue.TABLE_NAME);
		onCreate(db);
	}
		
	/**
	 * �ر����ݿ�
	 */
	public static void closeDB(SQLiteDatabase db) {
		if(db != null) {
			db.close();
		}
	}
	
	/**
	 *�ͷ��α� 
	 */
	public static void closeCursor(Cursor c) {
		if (c != null) {
			c.close();
		}
	}
}
