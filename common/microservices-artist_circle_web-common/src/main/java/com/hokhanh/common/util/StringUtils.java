package com.hokhanh.common.util;

import java.util.Arrays;
import java.util.List;

public final class StringUtils {
	public static String cleanBlank(String str) {
		return (str == null || str.isBlank()) ? null : str.trim();
	}
	
	public static String cleanListString(String str) {
		if(str == null || str.isBlank()) {
			return null;
		}
		
		List<String> cleanedList =  Arrays.stream(str.trim().split(","))
				.map(eachStr -> eachStr.trim())
				.filter(eachStr -> !eachStr.isBlank())
				.toList();
		
		return !cleanedList.isEmpty() ? String.join(",", cleanedList) : null;
	}
	
	private StringUtils() {}
}
