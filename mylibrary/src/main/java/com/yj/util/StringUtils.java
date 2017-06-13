package com.yj.util;


/**
 *@author lufengkai  
 */
public class StringUtils {
	public static boolean isEmpty(String input) {

		if (input == null || "".equals(input.trim()))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
}
