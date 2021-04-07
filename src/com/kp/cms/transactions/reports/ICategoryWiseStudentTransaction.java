package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface ICategoryWiseStudentTransaction {
	//Used to get students on categorywise
	public List<Student> getStudents(String searchCriteria)throws Exception;
}
