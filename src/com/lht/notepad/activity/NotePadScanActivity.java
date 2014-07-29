package com.lht.notepad.activity;

import com.lht.notepad.R;
import com.lht.notepad.util.ConvertDate;
import com.lht.notepad.util.DBAccess;
import com.lht.notepad.vo.NoteVO;
import com.lht.notepad.vo.PrefVO;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NotePadScanActivity extends Activity {
	
	private LinearLayout scanLayout;
	private TextView noteTitleText;
	private TextView noteContentText;
	private TextView notedateText;
	private MenuItem menuItem_0, menuItem_1, menuItem_2;
	
	private Intent intent;
	private NoteVO note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.scan);
		
		scanLayout = (LinearLayout)findViewById(R.id.scanlayout);
	    scanLayout.setBackgroundColor(PrefVO.themeColorValue);
	     
	    noteTitleText = (TextView)findViewById(R.id.titlescan);
	    noteContentText = (TextView)findViewById(R.id.contentscan);
	    noteContentText.setMovementMethod(ScrollingMovementMethod.getInstance());
	    notedateText = (TextView)findViewById(R.id.datescan);
	        
	    intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra("noteBundle");
		note = (NoteVO)bundle.getParcelable("note");
		    
		noteTitleText.setText(note.getNoteTitle());
		noteContentText.setText(note.getNoteContent());
		notedateText.setText(ConvertDate.datetoString(note.getNoteDate()));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menuItem_0 = menu.add(0, 0, 0, "编辑");
		menuItem_0.setIcon(R.drawable.edit_dark);
		menuItem_0.setOnMenuItemClickListener(new ItemClickListenerClass());
		
		menuItem_1 = menu.add(0, 1, 1, "删除");
		menuItem_1.setIcon(R.drawable.delete_dark);
		menuItem_1.setOnMenuItemClickListener(new ItemClickListenerClass());
		
		menuItem_2 = menu.add(0, 2, 2, "短信发送");
		menuItem_2.setIcon(R.drawable.message_dark);
		menuItem_2.setOnMenuItemClickListener(new ItemClickListenerClass());
		
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * 菜单按钮事件
	 */
	private class ItemClickListenerClass implements MenuItem.OnMenuItemClickListener
	{

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()) {
			case 0: {
				Intent intent = new Intent();
				intent.setClass(NotePadScanActivity.this, NotePadEditActivity.class);
				
				Bundle bundle = new Bundle();
		    	bundle.putParcelable("note", note);
		    	intent.putExtra("noteBundle", bundle);
		    	
		    	NotePadScanActivity.this.startActivity(intent);
		    	NotePadScanActivity.this.finish();
		    	
		    	break;
			}
			case 1: {
				AlertDialog.Builder builder = new Builder(NotePadScanActivity.this);
				builder.setTitle("删除");
				builder.setIcon(R.drawable.delete_light);
				builder.setMessage("您确定要把日志删除吗？");
				builder.setPositiveButton("确定",new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						DBAccess access = new DBAccess(NotePadScanActivity.this);
						access.deleteNote(note);
							
						dialog.dismiss();
						Toast.makeText(NotePadScanActivity.this, "已删除", Toast.LENGTH_LONG).show();
						NotePadScanActivity.this.finish();
					}
				});
				builder.setNegativeButton("取消", null);
				builder.create().show();
				
				break;
			}
			case 2: {
				Intent iIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
				
				if (!note.getNoteContent().equals(note.getNoteTitle())) {
					iIntent.putExtra("sms_body", note.getNoteTitle() + "\n" + note.getNoteContent());
				}
				else {
					iIntent.putExtra("sms_body", note.getNoteContent());
				}
				NotePadScanActivity.this.startActivity(iIntent);
				
				break;
			}
			}
			
			return false;
		}
		
	}
	
}
