package com.seppia.android.project_seppia.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtils {
	@SuppressWarnings("unused")
	private static String weekStr[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	@SuppressWarnings("unused")
	private static String weekStr1[] = {"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
	
	
	/**
	 * 获得当月第一天是星期几
	 * @param calendar
	 * @return
	 */
	public static int getWeekOfFirstDayInMonth(Calendar calendar){
		//取得当月第一天
		int firstDate = calendar.getActualMinimum(Calendar.DATE);
		
		//calendar设定日期为当月第一天，通过Calendar.DAY_OF_WEEK取得周几
		calendar.set(Calendar.DATE, firstDate);	
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		//将取到的值转换为中国对应的星期值
		if(firstDay > 1){
			firstDay = firstDay-1;
		}else if(firstDay == 1){
			firstDay = firstDay+6;
		}
		
		//1--->星期一，2-->星期二,.....6--->星期六,7--->星期日
		return firstDay;

	}
	/**
	 * 获得当月最后一天是星期几
	 * @param calendar
	 * @return
	 */
	public static int getWeekOfEndDayInMonth(Calendar calendar){
		//取得当月第一天
		int endDate = calendar.getActualMaximum(Calendar.DATE);
		
		//calendar设定日期为当月第一天，通过Calendar.DAY_OF_WEEK取得周几
		calendar.set(Calendar.DATE, endDate);	
		int endDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		//国内地区，lastDay的值为1-7，1表示周日，2表示周一.....7表示周六
		return endDay-1;
		
	}
	
	//获得前一个月的总天数
    public static int getMaxDaysOfLastMonth(){
 	   Calendar lastDate = Calendar.getInstance();
 	  lastDate.add(Calendar.MONTH,-1);//减一个月
 	  lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天 
 	  lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天 
 	  int lastMonthMaxDays = lastDate.get(Calendar.DAY_OF_MONTH);
 	  System.out.println(lastMonthMaxDays);
 	   return lastMonthMaxDays;  
    }
	
    
    /**
     * 获得当前时间的“时间戳”
     * @param date 需要格式化的日期
     * @param format 格式，例如："yyyy年MM月dd日","yyyyMMddHHmmss"
     * @return timestamp 格式化后的数据
     */
	public static String getFormatTime(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String timestamp = sdf.format(date);
		return timestamp;
	}

	/**
	 * 毫秒时间转字符串(默认时区)
	 * @param milliseconds
	 * @param pattern
	 */
	public static String toDateString(long milliseconds, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(TimeZone.getDefault());
			return sdf.format(milliseconds);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 字符串转毫秒时间(指定时区)
	 * @param date
	 * @param pattern
	 * @param timeZone
	 * @return
	 */
	public static long toMilliseconds(String date, String pattern, String timeZone) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
			return sdf.parse(date).getTime();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 返回两日期之间相差的精确天数
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static int getAccurateIntervalDays(Calendar startDay, Calendar endDay) {
		int days = endDay.get(Calendar.DAY_OF_YEAR) - startDay.get(Calendar.DAY_OF_YEAR);
		int endYear = endDay.get(Calendar.YEAR);
		if (startDay.get(Calendar.YEAR) != endYear) {
			startDay = (Calendar) startDay.clone();
			do {
				days += startDay.getActualMaximum(Calendar.DAY_OF_YEAR); // 得到当年的实际天数
				startDay.add(Calendar.YEAR, 1);
			} while (startDay.get(Calendar.YEAR) != endYear);
		}
		return days;
	}
	
	public static int getWeekOfCal(Calendar cal){
		int week = cal.get(Calendar.DAY_OF_WEEK);
		//将取到的值转换为中国对应的星期值
		if(week > 1){
			week = week-1;
		}else if(week == 1){
			week = week+6;
		}
		//1--->星期一，2-->星期二,.....6--->星期六,7--->星期日
		return week;
	}
}
