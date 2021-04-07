package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.AssignClassForStudentForm;

public interface IAssignClassForStudentTransaction {

	Map<Integer, String> getProgramMap() throws Exception;

	List<Student> getStudentList(String query) throws Exception;

	Map<Integer, String> getClassMap(String year, String courseId) throws Exception;

	boolean assignClass(AssignClassForStudentForm assignClassForStudentForm) throws Exception;

	
}
