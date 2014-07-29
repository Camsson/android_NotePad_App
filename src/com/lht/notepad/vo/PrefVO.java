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
	 * 主题颜色读写
	 */
	public static void getThemeListValue() {
		themeListValue = share.getString("themelist", "天蓝");
		
		if (themeListValue.equals("天蓝")) {
			themeColorValue = ConstantValue.THEME_BLUE;
		}
		else if (themeListValue.equals("椰壳绿")) {
			themeColorValue = ConstantValue.THEME_GREEN;
		}
		else if (themeListValue.equals("芒果")) {
			themeColorValue = ConstantValue.THEME_YELLOW;
		}
		else if (themeListValue.equals("胭脂红")) {
			themeColorValue = ConstantValue.THEME_RED;
		}
		else if (themeListValue.equals("驼绒")) {
			themeColorValue = ConstantValue.THEME_BROWN;
		}
		else if (themeListValue.equals("鲜橙")) {
			themeColorValue = ConstantValue.THEME_ORANGE;
		}
		else if (themeListValue.equals("紫丁香")) {
			themeColorValue = ConstantValue.THEME_PURPLE;
		}
	}
	
	public static void setThemeListValue(String value) {
		editor.putString("themelist", value);
		editor.commit();
		
		getThemeListValue();
	}
	
	/**
	 * 用户密码读写
	 */
	public static void getUserPassword() {
		userPasswordValue = share.getString("userpassword", "");
		ifLocked = share.getBoolean("iflocked", false);
	}
	
	public static void setUserPassword(String value) {
		editor.putString("userpassword", value);
		editor.putBoolean("iflocked", true);
		editor.commit();
		Toast.makeText(context, "已设置新密码", Toast.LENGTH_LONG).show();
		
		getUserPassword();
	}
	
	/**
	 * 界面刷新
	 */
	public static void dataFlush() {
		getThemeListValue();
		getUserPassword();
	}
	
	/**
	 * 程序锁定
	 */
	public static void appLock(boolean flag) {
		editor.putBoolean("iflocked", flag);
		editor.commit();
		
		getUserPassword();
	}
	
}
