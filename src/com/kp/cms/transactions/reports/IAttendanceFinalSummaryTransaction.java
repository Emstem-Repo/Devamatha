package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.exceptions.ApplicationException;

public interface IAttendanceFinalSummaryTransaction {

	/**
	 * get the List of Attendance Final Summary List.
	 * @param attendanceFinalSummaryQuery
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendanceFinalSummaryList(String attendanceFinalSummaryQuery)
			throws ApplicationException;

}
