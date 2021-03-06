/**
 * @author WJZ
 */
package com.lht.notepad.view;

import com.lht.notepad.vo.PrefVO;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class NoteTextView extends TextView {
	
	private Paint paint;

	public NoteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paint = new Paint();
		paint.setColor(PrefVO.themeColorValue);
		paint.setStyle(Paint.Style.STROKE);	
	}
	
	/**
	 * �ػ�����
	 * @see android.widget.TextView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		int lineHeight = this.getLineHeight();
		
		int topPadding = this.getPaddingTop();
		int leftPadding = this.getPaddingLeft();
		
		float textSize = getTextSize();
		setGravity(Gravity.LEFT|Gravity.TOP);
		
		int y = (int)(topPadding + textSize);
		
		for(int i = 0; i < getLineCount(); i++) {
			canvas.drawLine(leftPadding, y + 5, getRight() - leftPadding, y + 5, paint);
			y += lineHeight;
		}
		canvas.translate(0, 0);
		
		super.onDraw(canvas);
	}

	@Override
	protected int computeVerticalScrollRange() {
		return super.computeVerticalScrollRange();
	}
}
