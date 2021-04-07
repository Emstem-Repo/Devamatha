package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kp.cms.bo.exam.ExamAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.forms.exam.ExamAttendanceMarksForm;
import com.kp.cms.to.exam.ExamAttendanceMarksTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;

public class ExamAttendanceMarksHelper {

	public List<ExamAttendanceMarksTO> convertBOtoTO(
			ArrayList<ExamAttendanceMarksBO> listBO) throws Exception {
		ArrayList<ExamAttendanceMarksTO> list = new ArrayList<ExamAttendanceMarksTO>();
		ExamAttendanceMarksTO to = null;

		for (ExamAttendanceMarksBO am : listBO) {
			to = new ExamAttendanceMarksTO();
			to.setId(am.getId());
			to.setProgramTypeProgramCourse(am.getCourse()
					.getPTypeProgramCourse());
			to.setFromPercentage(am.getFromPercentage().toString());
			to.setToPercentage(am.getToPercentage().toString());
			to.setMarks(am.getMarks().toString());
			to.setIsTheoryPractical(am.getTheoryPractical());
			list.add(to);
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

	public ExamAttendanceMarksTO createBOToTO(ExamAttendanceMarksBO objBO) throws Exception {
		ExamAttendanceMarksTO objTO = new ExamAttendanceMarksTO();
		objTO.setId(objBO.getId());
		objTO.setProgramTypeProgramCourse(objBO.getCourse()
				.getPTypeProgramCourse());
		objTO.setFromPercentage(objBO.getFromPercentage().toString());
		objTO.setToPercentage(objBO.getToPercentage().toString());
		objTO.setMarks(objBO.getMarks().toString());
		objTO.setIsTheoryPractical(objBO.getTheoryPractical());

		return objTO;
	}

	public ExamAttendanceMarksForm createFormObjcet(
			ExamAttendanceMarksForm form, ExamAttendanceMarksBO objBO) throws Exception {
		form.setId(objBO.getId());
		form.setSelectedCourse(Integer.toString(objBO.getCourseId()));
		form.setFromPercentage(objBO.getFromPercentage().toString());
		form.setToPercentage(objBO.getToPercentage().toString());
		form.setMarks(objBO.getMarks().toString());
		form.setTheoryPractical(objBO.getTheoryPractical());
		
		
		
		
		
		form.setOrgSelectedCourse(Integer.toString(objBO.getCourseId()));
		form.setOrgFromPercentage(objBO.getFromPercentage().toString());
		form.setOrgToPercentage(objBO.getToPercentage().toString());
		form.setOrgMarks(objBO.getMarks().toString());
		form.setOrgTheoryPractical(objBO.getTheoryPractical());
		
		
		return form;
	}

}
