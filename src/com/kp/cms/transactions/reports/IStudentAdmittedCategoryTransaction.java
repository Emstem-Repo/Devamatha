package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IStudentAdmittedCategoryTransaction {

	List<Student> getSearchedStudents(int programId, int casteId) throws Exception;

}
