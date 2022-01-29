package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.AttendanceStudent;

public interface IStudentAbsenceDetailsTransaction {

	public List<AttendanceStudent> getStudentAbsenceDetails(String searchCriteria) throws Exception;
}
