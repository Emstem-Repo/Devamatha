package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.timetable.TimeTablePeriodTo;

public class TimeTableComparator implements Comparator<TimeTablePeriodTo> {
	@Override
	public int compare(TimeTablePeriodTo to1, TimeTablePeriodTo to2) {
		if (to1 != null    && to2 != null  ) {
			
			return to1.getPeriodName().compareTo(to2.getPeriodName());
		}
		return 0;
	}
}
