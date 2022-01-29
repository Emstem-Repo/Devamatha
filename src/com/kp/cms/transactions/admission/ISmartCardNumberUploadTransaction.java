package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;


public interface ISmartCardNumberUploadTransaction {

 public	Map<String, Student> getRegisterNumber() throws Exception;

public boolean addSmartCardNumber(List<Student> results,
		String user)throws Exception;

public Map<String,Student> getStudents(List<String> regNos)throws Exception;

public boolean update(List<Student> students)throws Exception;

}
