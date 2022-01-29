package com.kp.cms.transactions.admin;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.admin.StudentLogin;

public interface IReGeneratePasswordTransaction {

	List<StudentLogin> getStudentLogins(ArrayList<String> registerNoList) throws Exception;

	List<String> getStudentsByRegisterNo(ArrayList<String> registerNoList) throws Exception;

	boolean checkDuplicateRegisterNo(StudentLogin login) throws Exception;

	boolean updateStudentLogin(List<StudentLogin> finalStudentLogins) throws Exception;

}
