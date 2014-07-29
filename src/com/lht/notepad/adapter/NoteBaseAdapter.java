/**
 * @author LHT
 */
package com.lht.notepad.adapter;

import java.util.Date;
import java.util.List;

import com.lht.notepad.R;
import com.lht.notepad.util.ConvertDate;
import com.lht.notepad.vo.NoteVO;
import com.lht.notepad.vo.PrefVO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteBaseAdapter extends BaseAdapter {

	private List<NoteVO> list;
	private Context context;
	private int resource;
	
	public NoteBaseAdapter(Context context, int resource, List<NoteVO> list) {
		this.context = context;
		this.resource = resource;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return ((NoteVO)list.get(position)).getNoteId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		NoteVO note = (NoteVO)list.get(position);
		String noteTitle = note.getNoteTitle();
		Date noteDate = note.getNoteDate();
				
		LayoutInflater layoutInflater = LayoutInflater.from(context);		
				
		View view = layoutInflater.inflate(resource, null);
		
		TextView imNoteIcon = (TextView)view.findViewById(R.id.itemimage);
		TextView tvNoteTitle = (TextView)view.findViewById(R.id.itemtitle);
		TextView tvNoteDate = (TextView)view.findViewById(R.id.itemdate);
		
		imNoteIcon.setText((position+1)+"");
		imNoteIcon.setBackgroundColor(PrefVO.themeColorValue);
		tvNoteTitle.setText(noteTitle);
		tvNoteDate.setText(ConvertDate.datetoString(noteDate));

		return view;
	}

}
