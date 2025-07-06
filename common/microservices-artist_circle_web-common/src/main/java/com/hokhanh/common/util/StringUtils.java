package com.hokhanh.common.util;

public final class StringUtils {
	public static String cleanBlank(String str) {
		return (str == null || str.isBlank()) ? null : str.trim();
	}
	
	private StringUtils() {}
}
