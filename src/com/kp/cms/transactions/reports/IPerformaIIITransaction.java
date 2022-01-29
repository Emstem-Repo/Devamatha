package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IPerformaIIITransaction {

	public List<Student> getCourseIntakeDetails(String searchCriteria) throws Exception;
}
