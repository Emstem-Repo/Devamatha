package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.StudentListForSignatureForm;

public interface IStudentListForSignatureTransaction {
	public List<Student> getStudentList(StudentListForSignatureForm stForm) throws Exception;
}
