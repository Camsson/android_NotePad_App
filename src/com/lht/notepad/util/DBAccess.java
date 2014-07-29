package com.lht.notepad.util;

import java.util.ArrayList;
import java.util.List;

import com.lht.notepad.values.ConstantValue;
import com.lht.notepad.vo.NoteVO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAccess {
	private DBUtil dbUtil;
	private SQLiteDatabase db;
	
	/**
	 * 列名
	 */
	private static String[] colNames = new String[]{
		ConstantValue.DB_MetaData.NOTEID_COL,
		ConstantValue.DB_MetaData.NOTETITLE_COL,
		ConstantValue.DB_MetaData.NOTECONTENT_COL,
		ConstantValue.DB_MetaData.NOTEDATE_COL
	};
	
	public DBAccess(Context context) {
		dbUtil = new DBUtil(context);
	}
	
	/**
	 * 增加Note到数据库
	 * @param note
	 */
	public void insertNote(NoteVO note) {
		db = dbUtil.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(ConstantValue.DB_MetaData.NOTETITLE_COL, note.getNoteTitle());
		cv.put(ConstantValue.DB_MetaData.NOTECONTENT_COL, note.getNoteContent());
		cv.put(ConstantValue.DB_MetaData.NOTEDATE_COL, 
				ConvertDate.datetoString(note.getNoteDate()));
		db.insert(ConstantValue.TABLE_NAME, null, cv);
	}
	
	
	/**
	 * 删除Note
	 * @param note
	 */
	public void deleteNote(NoteVO note) {
		db = dbUtil.getWritableDatabase();
		
		db.delete(ConstantValue.TABLE_NAME, 
				ConstantValue.DB_MetaData.NOTEID_COL + "=" + note.getNoteId(), null);
	}
	
	/**
	 * 更新Note
	 * @param note
	 */
	public void updateNote(NoteVO note) {
		db = dbUtil.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(ConstantValue.DB_MetaData.NOTETITLE_COL, note.getNoteTitle());
		cv.put(ConstantValue.DB_MetaData.NOTECONTENT_COL, note.getNoteContent());
		cv.put(ConstantValue.DB_MetaData.NOTEDATE_COL, 
				ConvertDate.datetoString(note.getNoteDate()));
		db.update(ConstantValue.TABLE_NAME, cv, 
				ConstantValue.DB_MetaData.NOTEID_COL + "=" + note.getNoteId(), null);
	}
	
	/**
	 * 查找约束相关的所有Note
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public Cursor selectAllNoteCursor(String selection, String[] selectionArgs) {
		db = dbUtil.getReadableDatabase();
		Cursor c = db.query(ConstantValue.TABLE_NAME, colNames, 
				selection, selectionArgs, null, null, 
				ConstantValue.DB_MetaData.DEFAULT_ORDER);
		
		return c;
	}
	
	/**
	 * 获取Note列表
	 * @return
	 */
	public List<NoteVO> findAllNote() {
		Cursor c = selectAllNoteCursor(null, null);
		List<NoteVO> noteList = new ArrayList<NoteVO>();
		
		while (c.moveToNext()) {
			int noteID = c.getInt(0);
			String noteTitle = c.getString(1);
			String noteContent = c.getString(2);
			String noteDate = c.getString(3);
			
			noteList.add(new NoteVO(noteID, noteTitle, noteContent, ConvertDate.stringtodate(noteDate)));
		}
		
		DBUtil.closeCursor(c);
		DBUtil.closeDB(db);
		
		return noteList;
	}
	
}
