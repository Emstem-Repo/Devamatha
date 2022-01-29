package com.kp.cms.transactions.reports;

import java.util.List;

public interface IStudentForUniversityTransaction {

	List getAllStudentForUniversity(String searchCriteria) throws Exception;
	public List<Integer> getDetainedOrDiscontinuedStudents() throws Exception;

}
