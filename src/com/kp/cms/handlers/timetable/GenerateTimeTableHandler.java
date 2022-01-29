package com.kp.cms.handlers.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.axis.utils.StringUtils;

import com.kp.cms.forms.timetable.GenerateTimeTableForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.timetable.GenerateTimeTableHelper;
import com.kp.cms.to.timetable.GenerateTimeTableTo;
import com.kp.cms.transactionsimpl.timetable.GenerateTimeTableImpl;
import com.kp.cms.utilities.CommonUtil;

public class GenerateTimeTableHandler extends ExamGenHandler {

	/**
	 * @Date 03 Nov 2009 This handler class for Cash Collection Management
	 * 
	 */
	private static volatile GenerateTimeTableHandler generateTimeTable;
	private static GenerateTimeTableHelper helper = GenerateTimeTableHelper
			.getInstance();
	private static GenerateTimeTableImpl impl = GenerateTimeTableImpl
			.getInstance();

	private GenerateTimeTableHandler() {

	}

	public static GenerateTimeTableHandler getinstance() {
		if (generateTimeTable == null) {
			generateTimeTable = new GenerateTimeTableHandler();
			return generateTimeTable;
		}
		return generateTimeTable;

	}

	public ArrayList<GenerateTimeTableTo> fetchData(
			GenerateTimeTableForm objForm) {
		StringBuffer buffer = new StringBuffer();
		objForm.setSchemeDuration(helper.convertIntoCorrectDateFormat(impl
				.getSchemeDuration(objForm.getClasses()), buffer));
		int NoOfWeeks = 1;
		if (!StringUtils.isEmpty(buffer.toString())) {
			String week = buffer.substring(0);
			NoOfWeeks = Integer.parseInt(week);
		}
		ArrayList<Integer> subjectIds = new ArrayList<Integer>();
		helper.convertSubjectMap(impl.getSubjects(Integer.parseInt(objForm
				.getClasses()), objForm.getAcademicYr()), subjectIds);

		return helper.getListOfTo(
				impl.getAllocatedHoursForSubjects(subjectIds), impl
						.getEnterehoursPerWeek(subjectIds, objForm
								.getAcademicYr()), NoOfWeeks, objForm,
				subjectIds);
	}

}
