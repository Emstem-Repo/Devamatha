package com.kp.cms.handlers.exam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.exam.ExamInternalCalculationMarksBO;
import com.kp.cms.forms.exam.ExamInternalCalculationMarksForm;
import com.kp.cms.helpers.exam.ExamInternalCalMarksHelper;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamInternalCalculationMarksTO;
import com.kp.cms.transactionsimpl.exam.CourseUtilImpl;
import com.kp.cms.transactionsimpl.exam.ExamInternalCalMarksImpl;

public class ExamInternalCalMarksHandler extends ExamGenHandler{
	ExamInternalCalMarksImpl impl = new ExamInternalCalMarksImpl();
	ExamInternalCalMarksHelper helper = new ExamInternalCalMarksHelper();

	public List<ExamInternalCalculationMarksTO> init() throws Exception {
		ArrayList<ExamInternalCalculationMarksBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamInternalCalculationMarksBO.class));
		List<ExamInternalCalculationMarksTO> list = helper
				.convertBOtoTO(listBO);
		return list;

	}

	public void add(List<Integer> courseIdList, String startPercentage,
			String endPercentage, String marcks, String userId,
			String theoryPractical) throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal marksBD = new BigDecimal(marcks);
		ExamInternalCalculationMarksBO objBO = null;
		int isTheory = 0;
		int isPractical = 0;
		for (Integer i : courseIdList) {
			if (theoryPractical.contains("Theory")) {
				isTheory = 1;
			} else if (theoryPractical.contains("Practical")) {
				isPractical = 1;
			}
			objBO = new ExamInternalCalculationMarksBO(0, i.intValue(),
					startPercBD, endPercBD, marksBD, isTheory, isPractical,
					theoryPractical, userId, new Date(), userId, new Date(),
					true);
			if (!impl.isDuplicated(0, i.intValue(), startPercBD, theoryPractical)) {
				impl.insert(objBO);
			}
			isTheory = 0;
			isPractical = 0;
		}

	}

	/**
	 * Modifies Subject Selection master for a particular id
	 * @param listCourses 
	 */
	public void update(int id, int courseId, String startPercentage,
			String endPercentage, String marcks, String userId,
			String theoryPractical, List<Integer> courseIdList) throws Exception {
		BigDecimal startPercBD = new BigDecimal(startPercentage);
		BigDecimal endPercBD = new BigDecimal(endPercentage);
		BigDecimal marksBD = new BigDecimal(marcks);
		
		
		
		
		
		
		if (!impl.isDuplicated(id, courseId, startPercBD,
				theoryPractical)) {
			impl.update(id, startPercBD, endPercBD, marksBD, theoryPractical,
					userId);
		}
		
//		
//		ExamInternalCalculationMarksBO objBO = null;
//		
//		int isTheory = 0;
//		int isPractical = 0;
//		for (Integer i : courseIdList) {
//			if (theoryPractical.contains("Theory")) {
//				isTheory = 1;
//			} else if (theoryPractical.contains("Practical")) {
//				isPractical = 1;
//			}
//			objBO = new ExamInternalCalculationMarksBO(0, i.intValue(),
//					startPercBD, endPercBD, marksBD, isTheory, isPractical,
//					theoryPractical, userId, new Date(), userId, new Date(),
//					true);
//			if (!impl.isDuplicated(0, i.intValue(), startPercBD, endPercBD,
//					marksBD, theoryPractical)) {
//				impl.insert(objBO);
//			}
//			isTheory = 0;
//			isPractical = 0;
//		}

	}

	public void delete(int id, String userId) throws Exception {
		impl
				.delete_IExamGenBO(id, userId,
						ExamInternalCalculationMarksBO.class);
	}

//	public List<ExamCourseUtilTO> getListExamCourseUtil() throws Exception {
//		CourseUtilImpl c = new CourseUtilImpl();
//		c.select_ActiveOnly();
//		return helper.convertBOtoTO_course(c.select_ActiveOnly());
//	}

	public ExamInternalCalculationMarksForm getUpdatableForm(
			ExamInternalCalculationMarksForm form, String mode)
			throws Exception {
		form = helper.createFormObjcet(form,
				(ExamInternalCalculationMarksBO) impl.select_Unique(form
						.getId(), ExamInternalCalculationMarksBO.class));
		return form;
	}

	public void reactivate(int id, String userId) throws Exception {
		impl.reActivate_IExamGenBO(id, userId,
				ExamInternalCalculationMarksBO.class);

	}

}
