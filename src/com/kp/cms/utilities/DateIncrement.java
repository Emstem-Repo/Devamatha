package com.kp.cms.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DateIncrement {
	private static HashMap<Integer, Integer> monthMap = new HashMap<Integer, Integer>();
	static {
		monthMap.put(1, 31);
		monthMap.put(2, 28);
		monthMap.put(3, 31);
		monthMap.put(4, 30);
		monthMap.put(5, 31);
		monthMap.put(6, 30);
		monthMap.put(7, 31);
		monthMap.put(8, 31);
		monthMap.put(9, 30);
		monthMap.put(10, 31);
		monthMap.put(11, 30);
		monthMap.put(12, 31);
		monthMap.put(13, 29);
	}

	public List<String> getDays(String startDate, String endDate) {
		ArrayList<String> list = new ArrayList<String>();
		Integer[] st = getIntegerArray(startDate.split("/"));
		Integer[] et = getIntegerArray(endDate.split("/"));
		int startYear = st[2], endYear = et[2], startMonth = st[1], endMonth = et[1], startDay = st[0], endDay = et[0];
		int bufferEndMonth = endMonth, bufferEndDay = endDay;
		boolean flag = false;

		for (int year = startYear; year <= endYear; year++) {
			endMonth = 12;
			if (year == endYear) {
				endMonth = bufferEndMonth;
				flag = true;
			}
			for (int month = startMonth; month <= endMonth; month++) {
				endDay = monthMap.get(month);
				if (year % 4 == 0 && month == 2) {
					endDay = monthMap.get(13);
				}
				if (month == endMonth && flag) {
					endDay = bufferEndDay;
				}
				for (int day = startDay; day <= endDay; day++) {
					if (month <= 9) {
						list.add(day + "/0" + month + "/" + year);
					} else {
						list.add(day + "/" + month + "/" + year);
					}
				}
				startDay = 1;
			}
			startMonth = 1;
		}
		return list;
	}

	private Integer[] getIntegerArray(String[] split) {
		Integer[] list = new Integer[split.length];
		for (int i = 0; i < split.length; i++) {
			list[i] = Integer.parseInt(split[i]);
		}
		return list;
	}

	public static String getEnddate(int totalHoursAllocated) {

		return "";
	}
}
