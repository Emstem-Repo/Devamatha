package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.AttendanceStudent;

public interface IMonthlyBelowRequiredPercentageTransaction {

	public List<AttendanceStudent> getStudentsAttendance(String searchCriteria) throws Exception;
}
