package com.yj.entity;

/**
 * @author lufengkai 
 * @date 2015年5月26日
 * @copyright 游鹏科技
 */
public final class Helper {

	private Helper(){} //禁止实例化
	
	/**
	 * 检查字符串是否为null或者为空串<br>
	 * 为空串或者为null返回true，否则返回false
	 */
	public static boolean isNullOrEmpty(String str) {
		boolean result = true;
		str = getCleanString(str);
		if (str != null && !"".equals(str.trim())) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 检查字符串是否为null或者为"null"<br>
	 * 为null或者为"null",返回""，否则返回字符串 <br>
	 * 用于处理数据库查询数据
	 */
	public static String getCleanString(Object obj) {
		if (obj == null) {
			return "";
		} else if (String.valueOf(obj).equals("null")) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}
}
