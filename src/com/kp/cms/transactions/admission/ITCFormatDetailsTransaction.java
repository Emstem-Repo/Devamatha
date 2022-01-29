package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;

public interface ITCFormatDetailsTransaction {

	List<Student> getStudentDetails(String query) throws Exception;

	Student getStudentTCDetails(String query) throws Exception;

	boolean saveStudentTCDetails(StudentTCDetails bo) throws Exception;
	public boolean processUpdateTCDetails(List<StudentTCDetails> tcDetailsList) throws Exception ;
}
