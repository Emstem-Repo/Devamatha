package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IAdmissionIncompleteTransaction {

	public List<Student> getIncompleteAdmssionStudents(String selectionCriteria)throws Exception;

}
