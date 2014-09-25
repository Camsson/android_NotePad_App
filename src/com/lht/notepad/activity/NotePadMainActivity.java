/**
 * @author WJZ
 */
package com.lht.notepad.activity;

import java.util.ArrayList;
import java.util.List;

import com.lht.notepad.R;
import com.lht.notepad.adapter.NoteBaseAdapter;
import com.lht.notepad.util.DBAccess;
import com.lht.notepad.vo.NoteVO;
import com.lht.notepad.vo.PrefVO;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NotePadMainActivity extends Activity {
	
	private TextView noteNumTextView;
	private ListView noteListView;
	private ImageView imageViewAdd, imageViewSearch;
	private MenuItem menuItem_0, menuItem_1;
	
	private NoteBaseAdapter noteBaseAdapter;
	private ProgressDialog pgDialog;
	
	private DBAccess access;
	private List<NoteVO> noteVOList;
	private NoteVO note = new NoteVO();
	private PrefVO prefVO;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        noteNumTextView = (TextView)findViewById(R.id.numtext);
        
        imageViewAdd = (ImageView)findViewById(R.id.addbutton);
		imageViewAdd.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent intent = new Intent();
				intent.setClass(NotePadMainActivity.this, NotePadNewActivity.class);
				NotePadMainActivity.this.startActivity(intent);
				
				return false;
			}
		});
		
		imageViewSearch = (ImageView)findViewById(R.id.searchbutton);
		imageViewSearch.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent intent = new Intent();
				intent.setClass(NotePadMainActivity.this, NotePadSearchActivity.class);
				NotePadMainActivity.this.startActivity(intent);
				return false;
			}
		});
		
		noteListView = (ListView)findViewById(R.id.notelist);
		noteListView.setOnItemClickListener(new OnItemSelectedListener());
		
		pgDialog = new ProgressDialog(this);
		
		noteVOList = new ArrayList<NoteVO>();
		access = new DBAccess(this);
		prefVO = new PrefVO(this);
		
		this.registerForContextMenu(noteListView);
    }
    
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (PrefVO.ifLocked) {
			final EditText keytext = new EditText(NotePadMainActivity.this);
			keytext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			
			AlertDialog.Builder builder = new Builder(NotePadMainActivity.this);
			builder.setTitle("请输入密码");
			builder.setIcon(R.drawable.lock_light);
			builder.setView(keytext);
			builder.setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (PrefVO.userPasswordValue.equals(keytext.getText().toString())) {
						PrefVO.appLock(false);
						Toast.makeText(NotePadMainActivity.this, "已解除锁定", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(NotePadMainActivity.this, "密码错误", Toast.LENGTH_LONG).show();
						NotePadMainActivity.this.finish();
					}
				}
			});
			builder.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					NotePadMainActivity.this.finish();
				}
			});
			builder.create().show();
		}
		
		showProgressDialog();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menuItem_0 = menu.add(0, 0, 0, "设置");
		menuItem_0.setIcon(R.drawable.setting_dark);
		menuItem_0.setOnMenuItemClickListener(new ItemClickListtenerClass());
		
		menuItem_1 = menu.add(0, 1, 1, "锁定");
		menuItem_1.setIcon(R.drawable.lock_dark);
		menuItem_1.setOnMenuItemClickListener(new ItemClickListtenerClass());
		
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 *准备菜单按钮
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menuItem_1.setVisible(!PrefVO.userPasswordValue.equals(""));
		
		return super.onPrepareOptionsMenu(menu);
	}
    
    private class ItemClickListtenerClass implements MenuItem.OnMenuItemClickListener {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()) {
			case 0:{
				Intent intent = new Intent();
				intent.setClass(NotePadMainActivity.this, NotePadPreferenceActivity.class);
				NotePadMainActivity.this.startActivity(intent);
				
				break;
			}
			case 1:{
				final EditText keytext = new EditText(NotePadMainActivity.this);
				keytext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				
				AlertDialog.Builder builder = new Builder(NotePadMainActivity.this);
				builder.setTitle("请输入密码");
				builder.setIcon(R.drawable.lock_light);
				builder.setView(keytext);
				builder.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (PrefVO.userPasswordValue.equals(keytext.getText().toString())) {
							PrefVO.appLock(true);
							Toast.makeText(NotePadMainActivity.this, "已锁定", Toast.LENGTH_LONG).show();
							NotePadMainActivity.this.onResume();
						}
						else {
							Toast.makeText(NotePadMainActivity.this, "密码错误", Toast.LENGTH_LONG).show();
						}
					}
				});
				builder.setNegativeButton("取消", null);
				builder.create().show();
			}
			}
			
			return false;
		}
    	
    }
    
    /**
	 * 短按日志事件
	 */
	private class OnItemSelectedListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			note = noteVOList.get(position);
			
			Intent intent = new Intent();
			intent.setClass(NotePadMainActivity.this, NotePadScanActivity.class);
			
			Bundle bundle = new Bundle();
	    	bundle.putParcelable("note", note);
	    	intent.putExtra("noteBundle", bundle);
	    	
	    	NotePadMainActivity.this.startActivity(intent);
		}
	}
	
	/**
	 * 上下文菜单创建
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.setHeaderIcon(R.drawable.option_light);
		menu.setHeaderTitle("日志选项");
		menu.add(0, 1, 1, "删除");
		menu.add(0, 2, 2, "短信发送");
	}
	
	/**
	 * 上下文菜单事件
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		int index = info.position;
		final NoteVO note = noteVOList.get(index);
		
		switch (item.getItemId()) {
		case 1: {
			AlertDialog.Builder builder = new Builder(NotePadMainActivity.this);
			builder.setTitle("删除");
			builder.setIcon(R.drawable.delete_light);
			builder.setMessage("您确定要把日志删除吗？");
			builder.setPositiveButton("确定",new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					DBAccess access = new DBAccess(NotePadMainActivity.this);
					access.deleteNote(note);
						
					dialog.dismiss();
					Toast.makeText(NotePadMainActivity.this, "已删除", Toast.LENGTH_LONG).show();
					flush();
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
			NotePadMainActivity.this.startActivity(iIntent);
			
			break;
		}
		}
		
		return super.onContextItemSelected(item);
	}

	private void showProgressDialog() {
		pgDialog.setTitle("loading........");
		pgDialog.setMessage("正在读取数据,请稍等...");
		pgDialog.show();
		
		new progressThread().start();
	}
	
	private Handler handler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			flush();
			pgDialog.cancel();
		}
	};
	
	/**
	 * 进度条线程
	 */
	private class progressThread extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(500);
				handler.sendEmptyMessage(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    
	/**
	 * 界面刷新
	 */
    private void flush() {
    	PrefVO.dataFlush();
    	
    	noteVOList = access.findAllNote();
    	
    	noteBaseAdapter = new NoteBaseAdapter(this, R.layout.item, noteVOList);
    	noteListView.setAdapter(noteBaseAdapter);
    	noteNumTextView.setText(noteVOList.size() + "");
    }
    
}