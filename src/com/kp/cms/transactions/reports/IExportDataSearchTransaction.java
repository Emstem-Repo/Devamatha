package com.kp.cms.transactions.reports;

import java.util.List;
import com.kp.cms.bo.admin.Student;

public interface IExportDataSearchTransaction {

	public List<Student> getSearchCriteria(String search) throws Exception;
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception;
	
}
