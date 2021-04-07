package com.kp.cms.transactions.reports;

import java.util.List;
import com.kp.cms.bo.admin.Student;

public interface ISudentsListTransaction {

	public List<Student> getStudentsListReportDetails(String searchCriteria) throws Exception;
}
