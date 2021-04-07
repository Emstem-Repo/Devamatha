package com.kp.cms.handlers.exam;

/**
 * Dec 26, 2009 Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.helpers.exam.ExamUpdateExcludeWithheldHelper;
import com.kp.cms.to.exam.ExamUpdateExcludeWithheldTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamUpdateExcludeWithheldImpl;

public class ExamUpdateExcludeWithheldHandler extends ExamGenHandler {

	ExamUpdateExcludeWithheldImpl impl = new ExamUpdateExcludeWithheldImpl();

	ExamUpdateExcludeWithheldHelper helper = new ExamUpdateExcludeWithheldHelper();

	// To get course for particular exam
	public HashMap<Integer, String> getCourse(String examId) {
		ExamUpdateExcludeWithheldHelper h = new ExamUpdateExcludeWithheldHelper();
		List<ExamExamCourseSchemeDetailsBO> listBO = new ArrayList(impl
				.select_CourseName(Integer.parseInt(examId)));
		return h.convertBOToTO_course(listBO);

	}
	
	
	
	
	// To get scheme list for particular course
	public ArrayList<KeyValueTO> getSchemeList(String courseId) {
		ArrayList<ExamExamCourseSchemeDetailsBO> lBO = new ArrayList(impl
				.select_SchemeList(courseId));
		return helper.convertBOToTO_Scheme(lBO);
	}

	public List<ExamUpdateExcludeWithheldTO> getStudent_data(int courseId,
			int examId, int schemeNo, Integer academicYear, int oldScheme) throws Exception {
			ExamUpdateExcludeWithheldHelper h = new ExamUpdateExcludeWithheldHelper();
			List<StudentUtilBO> listBO = new ArrayList(impl.select_student(
					courseId, impl.select_year(examId), schemeNo, academicYear));
			return h.convertBOToTO_student(listBO, impl
				.select_studentExcludeFromResults(examId, courseId, oldScheme),
				impl.select_studentWithheld(examId, courseId, /*schemeNo*/oldScheme));
	}

	

	// when submit is selected on first page
	public void add(ArrayList<Integer> listExcludeStudents,
			ArrayList<Integer> listWithheldStudents, int examId, int courseId,
			int schemeNo, String userId) {
		impl.update(listExcludeStudents, listWithheldStudents, examId,
				courseId, schemeNo, userId);

	}
	public Integer getAcademicYearForExam(int examId)throws Exception{
		return impl.getAcademicYearForExam(examId);
	}
	
	
}
