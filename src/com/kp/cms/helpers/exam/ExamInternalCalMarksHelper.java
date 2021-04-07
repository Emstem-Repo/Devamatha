package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamInternalCalculationMarksBO;
import com.kp.cms.forms.exam.ExamInternalCalculationMarksForm;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamInternalCalculationMarksTO;

public class ExamInternalCalMarksHelper {

	public List<ExamInternalCalculationMarksTO> convertBOtoTO(
			ArrayList<ExamInternalCalculationMarksBO> listBO) throws Exception {
		ArrayList<ExamInternalCalculationMarksTO> list = new ArrayList<ExamInternalCalculationMarksTO>();
		ExamInternalCalculationMarksTO icmTO = null;
		for (ExamInternalCalculationMarksBO icm : listBO) {
			icmTO = new ExamInternalCalculationMarksTO();
			icmTO.setId(icm.getId());
			icmTO.setProgramTypeProgramCourse(icm.getCourse()
					.getPTypeProgramCourse());
			icmTO.setStartPercentage(icm.getStartPercentage().toString());
			icmTO.setEndPercentage(icm.getEndPercentage().toString());
			icmTO.setMarks(icm.getMarks().toString());
			list.add(icmTO);
		}
		Collections.sort(list);
		return list;
	}

	public List<ExamCourseUtilTO> convertBOtoTO_course(
			ArrayList<ExamCourseUtilBO> listBO) throws Exception {
		ArrayList<ExamCourseUtilTO> list = new ArrayList<ExamCourseUtilTO>();
		ExamCourseUtilTO to = null;
		for (ExamCourseUtilBO am : listBO) {
			to = new ExamCourseUtilTO(am.getCourseID(), am
					.getPTypeProgramCourse());
			list.add(to);
		}
		Collections.sort(list);
		return list;
	}

	public ExamInternalCalculationMarksForm createFormObjcet(
			ExamInternalCalculationMarksForm form,
			ExamInternalCalculationMarksBO objBO) throws Exception {

		form.setId(objBO.getId());
		String []sc={Integer.toString(objBO.getCourseId())};
		form.setSelectedCourse(sc);
		form.setStartPercentage(objBO.getStartPercentage().toString());
		form.setEndPercentage(objBO.getEndPercentage().toString());
		form.setMarks(objBO.getMarks().toString());
		form.setTheoryPractical(objBO.getTheoryPractical());

		form.setOrgSelectedCourse(Integer.toString(objBO.getCourseId()));
		form.setOrgStartPercentage(objBO.getStartPercentage().toString());
		form.setOrgEndPercentage(objBO.getEndPercentage().toString());
		form.setOrgMarks(objBO.getMarks().toString());
		form.setOrgTheoryPractical(objBO.getTheoryPractical());

		return form;
	}

}
