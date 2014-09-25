/**
 * @author WJZ
 */
package com.lht.notepad.activity;

import java.util.Date;

import com.lht.notepad.R;
import com.lht.notepad.util.DBAccess;
import com.lht.notepad.vo.NoteVO;
import com.lht.notepad.vo.PrefVO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NotePadNewActivity extends Activity {
	
	private LinearLayout editLayout;
	private EditText noteTitleText;
	private EditText noteContentText;
	
	private boolean flagTextChanged = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		editLayout = (LinearLayout)findViewById(R.id.editlayout);
        editLayout.setBackgroundColor(PrefVO.themeColorValue);
        
        noteTitleText = (EditText)findViewById(R.id.titleedit);
        noteContentText = (EditText)findViewById(R.id.contentedit);
        noteContentText.requestFocus();
        noteContentText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				if (flagTextChanged) {
					noteTitleText.setText(noteContentText.getText());
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				if (!noteTitleText.getText().toString().equals(noteContentText.getText().toString())) {
					flagTextChanged = false;
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
        
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		String noteTitle = noteTitleText.getText().toString();
		String noteContent = noteContentText.getText().toString();
		
		if(noteTitle.toString().trim().equals("") && noteContent.toString().trim().equals("")) {
			NotePadNewActivity.this.finish();
		}
		else {
			NoteVO note = new NoteVO();
			note.setNoteTitle(noteTitle);
			note.setNoteContent(noteContent);
			note.setNoteDate(new Date());
			
			DBAccess access = new DBAccess(this);
			access.insertNote(note);
			
			Toast.makeText(this, "已保存", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent();
			intent.setClass(this, NotePadScanActivity.class);
			Bundle bundle = new Bundle();
	    	bundle.putParcelable("note", note);
	    	intent.putExtra("noteBundle", bundle);
	    	
	    	this.startActivity(intent);
	    	this.finish();
		}
	}
	
	private MenuItem menuItem_0;
	
	/**
	 * 响应手机菜单按钮
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuItem_0 = menu.add(0, 0, 0, "删除");
		menuItem_0.setIcon(R.drawable.delete_dark);
		menuItem_0.setOnMenuItemClickListener(new ItemClickListenerClass());
				
		return true;
	}
	
	/**
	 * 菜单按钮事件
	 */
	private class ItemClickListenerClass implements MenuItem.OnMenuItemClickListener {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()) {
				case 0: {
					NotePadNewActivity.this.finish();
					break;
				}
			}
			return false;
		}
	}
}
