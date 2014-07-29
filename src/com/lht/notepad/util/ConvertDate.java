/**
 * @author LHT
 */
package com.lht.notepad.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {
	public static SimpleDateFormat sdf;
	static{
		sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * ��DateתΪString
	 * @param date
	 * @return String
	 */
	public static String datetoString(Date date){
		String stringDate = sdf.format(date);
		return stringDate;
	}
	
	/**
	 * ��StringתΪDate
	 * @param string
	 * @return Date
	 */
	public static Date stringtodate(String string){
		Date dateString = null;
        try {
			dateString = sdf.parse(string);
			return dateString;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
