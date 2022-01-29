package com.kp.cms.transactions.attandance;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.MonthlyAttendanceEntryForm;

public interface IMonthlyAttendanceTransaction {

	/**
	 * get the list of students by fromDate,toDate,classSchemeId,subjectId,teacherSet
	 * @param fromDate
	 * @param toDate
	 * @param classSchemeId
	 * @return
	 * @throws ApplicationException
	 */
	public List<Object[]> getAttendenceStudents(Date fromDate, Date toDate,
			Set<Integer> classSchemeId, MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws ApplicationException;

	/**
	 * Persists the monthly attendance.
	 * @param monthlyAttendanceEntryForm
	 * @throws ApplicationException
	 */
	public void persistMonthlyAttendance(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws ApplicationException;

	/**
	 * Cancels the monthly attendance.
	 * @param monthlyAttendanceEntryForm
	 * @throws ApplicationException
	 */
	public void cancelMonthlyAttendance(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws ApplicationException;
}
