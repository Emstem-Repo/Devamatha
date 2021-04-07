package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;

/**
 * Interface of generate password txns.
 *
 */
public interface IGeneratePasswordTransaction {

	List<Student> getSearchedStudents(int programId, int year) throws Exception;

	boolean saveCredentials(List<StudentLogin> studentlogins)throws Exception;

	StudentLogin resetPassword(String usernm, String encryptedpwd, String userID, String iserverPassword)throws Exception;

	boolean checkUserExists(String userName)throws Exception;
	List<String> getAllUserNamesPresent() throws Exception;
	public List<Student> getStudentsDetails(int year, String regNos, boolean isRollNo ) throws Exception;
	public boolean deleteStudentLogins(String studentIds)throws Exception;
}
