package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IClasswiseStudentListTxn {
	public List<Student> getStudentList(String searchCriteria) throws Exception;
}
