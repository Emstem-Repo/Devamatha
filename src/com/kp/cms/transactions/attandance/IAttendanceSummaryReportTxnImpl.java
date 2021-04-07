package com.kp.cms.transactions.attandance;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceSummaryReportForm;

/**
 * @author kalyan.c
 *
 */
public interface IAttendanceSummaryReportTxnImpl {
	
	public List getStudentAttendance(String searchCriteria) throws Exception;
	public int getClassesHeld(String searchCriteria) throws ApplicationException ;
	public int getClassesAttended(String searchCriteria) throws ApplicationException ;
	public int getActivityHeld(String searchCriteria) throws ApplicationException ;
	public int getActivityAttended(String searchCriteria) throws ApplicationException ;
	public int getSelectedActivityHeld(String searchCriteria) throws ApplicationException ;
	public int getSelectedActivityAttended(String searchCriteria) throws ApplicationException ;
	public Map<Integer, Subject> getSubjectByCourseIdTermYear(int courseId,
			int year, int term)throws ApplicationException;
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception;
	public List getAttendanceType()throws Exception;
	public Date getStartDate(List classList,int year)throws Exception;
	public Map<Integer, String> getClassByYearUserId(int year, AttendanceSummaryReportForm attendanceSummaryReportForm);
	public Map<Integer, String> getSubjectByCourseTermYear(int courseId, int year, int term, AttendanceSummaryReportForm attendanceSummaryReportForm)throws ApplicationException;
	public Map<Integer, String> getSubjectByClass(Set<Integer> classesIdsSet)throws ApplicationException;
}
