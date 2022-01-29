package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;

public interface IAttendanceMarksEntryTransaction {

	public List<ExamCourseUtilBO> getCourseList() throws Exception;
	public boolean addAttendanceMark(List<ExamSubCoursewiseAttendanceMarksBO> attendaceBoList) throws Exception;
	public int getSchemIDforCourseAndYear(int courseId, int year,int schemeNumber) throws Exception;
	public List<Object[]> getAttendanceList() throws Exception;
	public boolean deleteExixtingData(Map<Integer, List<Integer>> deleteExistingMap) throws Exception;

}
