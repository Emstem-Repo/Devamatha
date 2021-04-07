package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamDefinitionUtilBO;
import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.to.exam.ExamUpdateExcludeWithheldTO;
import com.kp.cms.to.exam.KeyValueTO;

/**
 * Dec 26, 2009 Created By 9Elements Team
 */
public class ExamUpdateExcludeWithheldHelper {

	// Get Exam name list
	public ArrayList<KeyValueTO> convertBOToTO_ExamName(
			ArrayList<ExamDefinitionUtilBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamDefinitionUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		return listTO;
	}

	// Get Course for the particular exam
	public HashMap<Integer, String> convertBOToTO_course(
			List<ExamExamCourseSchemeDetailsBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamExamCourseSchemeDetailsBO bo : listBO) {
			map.put(bo.getCourseId(), bo.getExamCourseUtilBO().getCourseName());
		}
		return map;
	}

	// Get Scheme for the particular course
	public ArrayList<KeyValueTO> convertBOToTO_Scheme(
			ArrayList<ExamExamCourseSchemeDetailsBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamExamCourseSchemeDetailsBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), Integer.toString(bo
					.getSchemeNo())));

		}
		return listTO;
	}

	public List<ExamUpdateExcludeWithheldTO> convertBOToTO(Object select_student) {
		// TODO Auto-generated method stub
		return null;
	}

	// To get the students for the particular course and scheme
	public List<ExamUpdateExcludeWithheldTO> convertBOToTO_student(
			List<StudentUtilBO> studentlist,
			ArrayList<Integer> excludeFromResults, ArrayList<Integer> withHeld) {
		ArrayList<ExamUpdateExcludeWithheldTO> listTO = new ArrayList<ExamUpdateExcludeWithheldTO>();

		ExamUpdateExcludeWithheldTO uTO = null;
		Iterator<StudentUtilBO> iStu = studentlist.iterator();
		while (iStu.hasNext()) {
			uTO = new ExamUpdateExcludeWithheldTO();
			StudentUtilBO studentUtilBO = (StudentUtilBO) iStu.next();
			int studentId = studentUtilBO.getId();
			uTO.setStudentId(studentId);
			uTO.setRegNumber(studentUtilBO.getRegisterNo());
			uTO.setRollNumber(studentUtilBO.getRollNo());
			uTO.setStudentName(studentUtilBO.getAdmApplnUtilBO()
					.getPersonalDataUtilBO().getName());
			uTO.setExclude(Integer.toString(studentId));
			uTO.setWithheld(Integer.toString(studentId));
			uTO.setExcludeId(excludeFromResults.contains(studentId) ? 1 : 0);
			uTO.setWithheldId(withHeld.contains(studentId) ? 1 : 0);
			listTO.add(uTO);
		}

		return listTO;
	}

}
