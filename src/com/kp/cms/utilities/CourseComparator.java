package com.kp.cms.utilities;

import java.util.Comparator;

import com.kp.cms.to.admin.CourseTO;

public class CourseComparator implements Comparator<CourseTO> {
	@Override
	public int compare(CourseTO to1, CourseTO to2) {
		if (to1 != null    && to2 != null  ) {
			if(to1.getName() == null) {
				to1.setName("");
			}
			if(to2.getName() == null) {
				to2.setName("");
			}
			return to1.getName().compareTo(to2.getName());
		}
		return 0;
	}
}
