package com.ischoolbar.programmer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	
	/**
	 * ����ָ����־ָ����ʽ�������ַ���
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String getDate(String pattern,Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
}
