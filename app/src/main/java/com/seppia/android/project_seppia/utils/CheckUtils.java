package com.seppia.android.project_seppia.utils;

import java.text.*;
import java.util.*;

/**
 * 数据检查工具
 * 
 */
public class CheckUtils {

    public static final String EXP_a_z = "[a-z]*";//匹配所有的小写字母
    public static final String EXP_A_Z = "[A-Z]*";//匹配所有的大写字母
    public static final String EXP_a_z_A_Z = "[a-zA-Z]*";//匹配所有的字母
    public static final String EXP_0_9 = "[0-9]*";//匹配所有的数字
    public static final String EXP_0_9_a_z = "[0-9a-z]*";
    public static final String EXP_0_9_a_z_A_Z = "[0-9a-zA-Z]*";
    public static final String EXP_0_9_a_z__ = "[0-9a-z_]*";
    public static final String EXP_EMAIL = "^([a-z0-9A-Z_]+[_|\\-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//EMAIL
    public static final String EXP_PRICE = "^([1-9]\\d+|[1-9])(\\.\\d\\d?)*$";//金额，2位小数
    public static final String EXP_MOBILE = "[0-9]{11}";//11位数的手机号码
    public static final String EXP_POSTALCODE = "[0-9]{6}";//6位数的邮编
    public static final String EXP_TEL = "[0-9]{3,4}[-]{1}[0-9]{7,8}";//电话号码：( 如021-12345678 or 0516-12345678 )
    public static final String EXP_IP = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";//匹配IP地址
    public static final String EXP_CHINESE = "[\u4e00-\u9fa5]*";//匹配中文
    public static final String EXP_0_9_a_z_A_Z_CHINESE = "[0-9a-zA-Z\u4e00-\u9fa5]*";//匹配中文,数字,小写字母,大写字母

    public static final String EXP_URL = "^[a-zA-z]+://[^><\"' ]+";

    public static final String EXP_DATE = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}";
    public static final String EXP_DATETIME = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}";
    public static final String EXP_DATETIMESECOND = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}";

    public static final String DATESTRING_TAIL = "000000000";
    
    private static final HashSet<Class<?>> clsSet;
    static {
    	clsSet = new HashSet<Class<?>>();
    	clsSet.add(void.class);
    	clsSet.add(boolean.class);
    	clsSet.add(byte.class);
    	clsSet.add(char.class);
    	clsSet.add(int.class);
    	clsSet.add(long.class);
    	clsSet.add(short.class);
    	clsSet.add(double.class);
    	clsSet.add(float.class);
    	clsSet.add(Void.class);
    	clsSet.add(Boolean.class);
    	clsSet.add(Byte.class);
    	clsSet.add(Character.class);
    	clsSet.add(Integer.class);
    	clsSet.add(Long.class);
    	clsSet.add(Short.class);
    	clsSet.add(Double.class);
    	clsSet.add(Float.class);
    	clsSet.add(String.class);
	}

    /**
	 * 判断对象是否为空
	 * @return boolean
	 */
	public static boolean isNull(Object obj) {
		return obj == null ? true : false;
	}
    
    /*--------------------------------------------------------------------------
	| 检查字符串
	--------------------------------------------------------------------------*/
	/**
	 * 判断字符串是否为空
	 * @param str String
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str)) ? true : false;
	}

	/**
	 * 判断是否符合指定的正则表达式 eg: [^0-9A-Za-z]
	 * @param str String
	 * @param patternStr String
	 * @return boolean
	 */
	public static boolean matches(String str, String patternStr) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches(patternStr);
	}

	/**
	 * 判断字符串是否是日期 2002-02-02
	 * @param str String
	 * @return boolean
	 */
	public static boolean isDate(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches(EXP_DATE);
	}

	/**
	 * 判断字符串是否是日期+时间 2002-02-02 10：30
	 * @param str String
	 * @return boolean
	 */
	public static boolean isDateTime(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches(EXP_DATETIME);
	}

	/**
	 * 判断字符串是否是金额 eg: 12.00
	 * @param str String
	 * @return boolean
	 */
	public static boolean isMoney(String str) {
		if (isEmpty(str)) {
			return false;
		}
		try {
			Double.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是整型
	 * @param str String
	 * @return boolean
	 */
	public static boolean isInteger(String str) {
		if (isEmpty(str)) {
			return false;
		}
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是长整型
	 * @param str String
	 * @return boolean
	 */
	public static boolean isLong(String str) {
		if (isEmpty(str)) {
			return false;
		}
		try {
			Long.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 * @param str String
	 * @return boolean
	 */
	public static boolean isDouble(String str) {
		if (isEmpty(str)) {
			return false;
		}
		try {
			Double.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*--------------------------------------------------------------------------
	| 检查超长
	--------------------------------------------------------------------------*/
	/**
	 * 字符串是否超长
	 * @param str String
	 * @param len int
	 * @return boolean
	 */
	public static boolean isOverLength(String str, int len) {
		if (isEmpty(str)) {
			return false;
		}
		return str.length() > len ? true : false;
	}

	/**
	 * Double类型是否超长
	 * @param d Double
	 * @param len int
	 * @return boolean
	 */
	public static boolean isOverLength(Double d, int len) {
		if (d == null) {
			return false;
		}
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setGroupingUsed(false); // 是否对结果分组（即使用","分组）
		formatter.setMaximumFractionDigits(0); // 小数位数最大值
		formatter.setMinimumFractionDigits(0); // 小数位数最小值
		if (formatter.format(d.doubleValue()).length() > len) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断对象是否基本类型
	 * @author wfc
	 * @param object
	 * @return
	 */
	public static boolean isBaseType(Object object) {
		if (object instanceof Integer || 
				object instanceof Double || 
				object instanceof String || 
				object instanceof Character || 
				object instanceof Byte || 
				object instanceof Long || 
				object instanceof Float || 
				object instanceof Boolean || 
				object instanceof Short) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断类是否基本类型
	 * @return
	 */
	public static boolean isBaseType(Class<?> cls) {
		return clsSet.contains(cls);
	}
	
	/**
	 * 判断字符串是否在列表中
	 * @param item
	 * @param list
	 * @return
	 */
	public static boolean isInList(String item, List<String> list) {
		for (String listItem : list) {
			if (item.equals(listItem)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否在列表中
	 * @param <T>
	 * @param item
	 * @param list
	 * @return
	 */
	public static <T> boolean isInList(T item, List<T> list) {
		for (T listItem : list) {
			if (item.equals(listItem)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断对象是否在数组中
	 * @param <T>
	 * @param item
	 * @param list
	 * @return
	 */
	public static <T> boolean isInArray(T item, T[] list) {
		for (T listItem : list) {
			if (item.equals(listItem)) {
				return true;
			}
		}
		return false;
	}
}
