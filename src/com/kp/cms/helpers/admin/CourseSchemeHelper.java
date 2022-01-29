package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.to.admin.CourseSchemeTO;

public class CourseSchemeHelper {
	private static volatile CourseSchemeHelper courseSchemeHelper = null;

	private CourseSchemeHelper() {
	}

	public static CourseSchemeHelper getInstance() {
		if (courseSchemeHelper == null) {
			courseSchemeHelper = new CourseSchemeHelper();
		}
		return courseSchemeHelper;
	}

	public List<CourseSchemeTO> populateCourseSchemeBOtoTO(List<CourseScheme> list1) throws Exception {
		List<CourseSchemeTO> courseSchemeList = new ArrayList();
		CourseScheme coursescheme;
		CourseSchemeTO courseSchemeTO;

		Iterator<CourseScheme> iterator = list1.iterator();
		while (iterator.hasNext()) {
			courseSchemeTO = new CourseSchemeTO();
			coursescheme = iterator.next();
			courseSchemeTO.setCourseSchemeId(coursescheme.getId());
			courseSchemeTO.setCourseSchemeName(coursescheme.getName());
			courseSchemeList.add(courseSchemeTO);

		}
		return courseSchemeList;
	}
}
