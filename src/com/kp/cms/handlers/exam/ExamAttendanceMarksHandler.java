package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.exam.ExamAttendanceMarksBO;
import com.kp.cms.forms.exam.ExamAttendanceMarksForm;
import com.kp.cms.helpers.exam.ExamAttendanceMarksHelper;
import com.kp.cms.to.exam.ExamAttendanceMarksTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.transactionsimpl.exam.CourseUtilImpl;
import com.kp.cms.transactionsimpl.exam.ExamAttendanceMarksImpl;

public class ExamAttendanceMarksHandler extends ExamGenHandler{
	ExamAttendanceMarksImpl impl = new ExamAttendanceMarksImpl();
	ExamAttendanceMarksHelper helper = new ExamAttendanceMarksHelper();

	public List<ExamAttendanceMarksTO> init() throws Exception {
		ArrayList<ExamAttendanceMarksBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamAttendanceMarksBO.class));
		List<ExamAttendanceMarksTO> list = helper.convertBOtoTO(listBO);
		return list;

	}

	public void add(List<Integer> courseIdList, String startPercentage,
			String endPercentage, String marks, String theoryPractical,
			String userId) throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal marksBD = new BigDecimal(marks);
		int isTheory = 0;
		int isPractical = 0;

		for (Integer i : courseIdList) {

		/*	if (theoryPractical.contains("Theory")) {
				isTheory = 1;
			} else if (theoryPractical.contains("Practical")) {
				isPractical = 1;
			}*/
			
			if (theoryPractical.equalsIgnoreCase("Theory")) {
				isTheory = 1;
				isPractical = 0;
			} else if (theoryPractical.equalsIgnoreCase("Practical")) {
				isTheory = 0;
				isPractical = 1;
			} else {
				isTheory = 1;
				isPractical = 1;
			}

			ExamAttendanceMarksBO objBO = new ExamAttendanceMarksBO(0, i.intValue(), startPercBD,
					endPercBD, marksBD, isTheory, isPractical, theoryPractical,
					userId);

			if (!impl.isDuplicated(0, i.intValue(), startPercBD, endPercBD,
					marksBD, theoryPractical)) {
				impl.insert(objBO);
			}
			isTheory = 0;
			isPractical = 0;
		}
	}

	public void update(int id, int courseID, String startPercentage,
			String endPercentage, String marks, String theoryPractical,
			String userId) throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal marksBD = new BigDecimal(marks);
		if (!impl.isDuplicated(id, courseID, startPercBD, endPercBD, marksBD,
				theoryPractical)) {
			impl.update(id, startPercBD, endPercBD, marksBD, theoryPractical,
					userId);
		}

	}

	public ExamAttendanceMarksForm getUpdatableForm(
			ExamAttendanceMarksForm form, String mode) throws Exception {

		form = helper.createFormObjcet(form, impl.loadRoomMaster(form.getId()));

		return form;
	}

	public void deleteAttMarks(int id, String userId) throws Exception {
		impl.delete_IExamGenBO(id, userId, ExamAttendanceMarksBO.class);
	}

//	public List<ExamCourseUtilTO> getListExamCourseUtil() throws Exception {
//		CourseUtilImpl c = new CourseUtilImpl();
//		c.select_ActiveOnly();
//		return helper.convertBOtoTO_course(c.select_ActiveOnly());
//	}

	public void reactivate(int id, String userId)
			throws Exception {
		impl.reActivate_IExamGenBO(id, userId, ExamAttendanceMarksBO.class);
	}
}
