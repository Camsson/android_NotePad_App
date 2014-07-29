package com.lht.notepad.activity;

import java.util.Date;

import com.lht.notepad.R;
import com.lht.notepad.util.DBAccess;
import com.lht.notepad.vo.NoteVO;
import com.lht.notepad.vo.PrefVO;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NotePadEditActivity extends Activity {
	
	private LinearLayout editLayout;
	private EditText noteTitleText;
	private EditText noteContentText;
	
	private NoteVO note;
	
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
        
        Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("noteBundle");
	    note = (NoteVO)bundle.getParcelable("note");
	    
	    noteTitleText.setText(note.getNoteTitle());
	    noteContentText.setText(note.getNoteContent());
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		String noteTitle = noteTitleText.getText().toString();
		String noteContent = noteContentText.getText().toString();
		
		note.setNoteTitle(noteTitle);
		note.setNoteContent(noteContent);
		note.setNoteDate(new Date());
		
		DBAccess access = new DBAccess(this);
		access.updateNote(note);
		
		Toast.makeText(this, "已保存", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent();
		intent.setClass(this, NotePadScanActivity.class);
		Bundle bundle = new Bundle();
    	bundle.putParcelable("note", note);
    	intent.putExtra("noteBundle", bundle);
    	
    	this.startActivity(intent);
    	this.finish();
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
				AlertDialog.Builder builder = new Builder(NotePadEditActivity.this);
				builder.setTitle("删除");
				builder.setIcon(R.drawable.delete_light);
				builder.setMessage("您确定要把日志删除吗？");
				builder.setPositiveButton("确定",new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						DBAccess access = new DBAccess(NotePadEditActivity.this);
						access.deleteNote(note);
							
						dialog.dismiss();
						Toast.makeText(NotePadEditActivity.this, "已删除", Toast.LENGTH_LONG).show();
						NotePadEditActivity.this.finish();
					}
				});
				builder.setNegativeButton("取消", null);
				builder.create().show();
					
				break;
			}
			}
		return false;
		}
	}
	
}
