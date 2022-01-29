package com.kp.cms.transactions.admin;

import java.sql.Date;
import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IBulkSMStoStudentTransaction {
	public List<Student> getStudnetForClassIds(String classID) throws Exception;
	public List<Integer> getStudentIds(Date smsDate) throws Exception;
	

}
