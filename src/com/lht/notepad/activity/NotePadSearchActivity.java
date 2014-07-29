package com.lht.notepad.activity;

import com.lht.notepad.R;
import com.lht.notepad.adapter.NoteCursorAdapter;
import com.lht.notepad.util.DBAccess;
import com.lht.notepad.vo.NoteVO;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NotePadSearchActivity extends Activity {
	
	private AutoCompleteTextView searchTextView;
	private ListView searchListView;
	
	private NoteCursorAdapter noteCursorAdapter;
	private Cursor c;
	private DBAccess access;
	
	private NoteVO note = new NoteVO();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.search);
		
		access = new DBAccess(this);
		
		c = access.selectAllNoteCursor(null, null);
		noteCursorAdapter = new NoteCursorAdapter(this, c, true);
		searchTextView = (AutoCompleteTextView)findViewById(R.id.searchtext);
		searchTextView.setDropDownHeight(0);
		searchTextView.requestFocus();
		searchTextView.setAdapter(noteCursorAdapter);
		
		searchListView = (ListView)findViewById(R.id.searchList);
		searchListView.setAdapter(noteCursorAdapter);
		searchListView.setOnItemClickListener(new OnItemSelectedListener());
	}
	
	/**
	 * 短按日志事件
	 */
	private class OnItemSelectedListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			note = noteCursorAdapter.getList().get(position);
			
			Intent intent = new Intent();
			intent.setClass(NotePadSearchActivity.this, NotePadScanActivity.class);
			
			Bundle bundle = new Bundle();
	    	bundle.putParcelable("note", note);
	    	intent.putExtra("noteBundle", bundle);
	    	
	    	NotePadSearchActivity.this.startActivity(intent);
		}
	}
	
}
