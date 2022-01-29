package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.attendance.ClassWiseAttendanceStatusForm;

public interface IClassWiseAttendanceStatusTransaction {

	CurriculumSchemeDuration checkingDatesInCoCurriculumSchemeDuration(
			ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm)throws Exception;

	String getClassName(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm)throws Exception;

	List<Attendance> getAttendanceListDetails(String searchCriteria,List<Integer> periodList)throws Exception;

	List<Integer> getPeriodsIdsUsingClassId(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm) throws Exception;

	List<String> getPeriodNamesUsingClassId(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm)throws Exception;

	List totalPeriodDetailsDayWiseList(String totalPeriodDetailsDayWise)throws Exception;

	String checkingInAttendanceDayEntry(String attendanceDate)throws Exception;

	

}
