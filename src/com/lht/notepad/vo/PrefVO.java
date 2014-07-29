package com.lht.notepad.vo;

import com.lht.notepad.values.ConstantValue;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

public class PrefVO {
	
	public static String themeListValue;
	public static int themeColorValue;
	public static String userPasswordValue;
	public static boolean ifLocked;
	
	private static Context context;
	private static SharedPreferences share;
	private static Editor editor;
	
	public PrefVO(Context context) {
		super();
		PrefVO.context = context;
		PrefVO.share = context.getSharedPreferences("com.lht.notepad_preferences", Context.MODE_PRIVATE);
		PrefVO.editor = share.edit();
		
		getThemeListValue();
		getUserPassword();
	}
	
	/**
	 * ������ɫ��д
	 */
	public static void getThemeListValue() {
		themeListValue = share.getString("themelist", "����");
		
		if (themeListValue.equals("����")) {
			themeColorValue = ConstantValue.THEME_BLUE;
		}
		else if (themeListValue.equals("Ҭ����")) {
			themeColorValue = ConstantValue.THEME_GREEN;
		}
		else if (themeListValue.equals("â��")) {
			themeColorValue = ConstantValue.THEME_YELLOW;
		}
		else if (themeListValue.equals("��֬��")) {
			themeColorValue = ConstantValue.THEME_RED;
		}
		else if (themeListValue.equals("����")) {
			themeColorValue = ConstantValue.THEME_BROWN;
		}
		else if (themeListValue.equals("�ʳ�")) {
			themeColorValue = ConstantValue.THEME_ORANGE;
		}
		else if (themeListValue.equals("�϶���")) {
			themeColorValue = ConstantValue.THEME_PURPLE;
		}
	}
	
	public static void setThemeListValue(String value) {
		editor.putString("themelist", value);
		editor.commit();
		
		getThemeListValue();
	}
	
	/**
	 * �û������д
	 */
	public static void getUserPassword() {
		userPasswordValue = share.getString("userpassword", "");
		ifLocked = share.getBoolean("iflocked", false);
	}
	
	public static void setUserPassword(String value) {
		editor.putString("userpassword", value);
		editor.putBoolean("iflocked", true);
		editor.commit();
		Toast.makeText(context, "������������", Toast.LENGTH_LONG).show();
		
		getUserPassword();
	}
	
	/**
	 * ����ˢ��
	 */
	public static void dataFlush() {
		getThemeListValue();
		getUserPassword();
	}
	
	/**
	 * ��������
	 */
	public static void appLock(boolean flag) {
		editor.putBoolean("iflocked", flag);
		editor.commit();
		
		getUserPassword();
	}
	
}
