package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;

public interface ITCDetailsClassWiseTransaction {

	List<Student> getStudentDetails(String query) throws Exception;

	Student getStudentTCDetails(String query) throws Exception;

	boolean saveStudentTCDetails(List<StudentTCDetails> bolist) throws Exception;

	List<CharacterAndConduct> getAllCharacterAndConduct() throws Exception;

}
