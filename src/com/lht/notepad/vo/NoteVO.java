/**
 * @author LHT
 */
package com.lht.notepad.vo;

import java.util.Date;

import com.lht.notepad.util.ConvertDate;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteVO implements Parcelable {
	
	private int noteId;
	private String noteTitle;
	private String noteContent;
	private Date noteDate;
	
	public NoteVO() {
		
	}
	
	/**
	 * �洢��־
	 * @param noteTitle
	 * @param noteContent
	 * @param noteDate
	 */
	public NoteVO(String noteTitle, String noteContent,
			Date noteDate) {
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteDate = noteDate;
	}
	
	/**
	 * ��ȡ��־
	 * @param noteId
	 * @param noteTitle
	 * @param noteContent
	 * @param noteDate
	 */
	public NoteVO(int noteId, String noteTitle, String noteContent,
			Date noteDate) {
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteDate = noteDate;
	}

	/**
	 * @return the noteId
	 */
	public int getNoteId() {
		return noteId;
	}

	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	/**
	 * @return the noteTitle
	 */
	public String getNoteTitle() {
		return noteTitle;
	}

	/**
	 * @param noteTitle the noteTitle to set
	 */
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	/**
	 * @return the noteContent
	 */
	public String getNoteContent() {
		return noteContent;
	}

	/**
	 * @param noteContent the noteContent to set
	 */
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	/**
	 * @return the noteDate
	 */
	public Date getNoteDate() {
		return noteDate;
	}

	/**
	 * @param noteDate the noteDate to set
	 */
	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}
	
	/**
	 * ��ȡ���л�������
	 */
	public static final Parcelable.Creator<NoteVO> CREATOR = new Parcelable.Creator<NoteVO>(){
		
		@Override
		public NoteVO createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			NoteVO note = new NoteVO();
			note.setNoteId(source.readInt());
			note.setNoteTitle(source.readString());
			note.setNoteContent(source.readString());
			note.setNoteDate(ConvertDate.stringtodate(source.readString()));
			
			return note;
		}
		
		@Override
		public NoteVO[] newArray(int size) {
			// TODO Auto-generated method stub
			return new NoteVO[size];
		}
		
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * ���л�����д�Ĳ���
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(this.noteId);
		dest.writeString(this.noteTitle);
		dest.writeString(this.noteContent);
		dest.writeString(ConvertDate.datetoString(this.noteDate));
	}
	
}
