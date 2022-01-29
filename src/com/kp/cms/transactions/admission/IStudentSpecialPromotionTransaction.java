package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.StudentSpecialPromotionForm;

public interface IStudentSpecialPromotionTransaction {
	List<Student> getStudentDetails(String query) throws Exception;
	boolean updateStudentsDetails(StudentSpecialPromotionForm studentSpecialPromotionForm) throws Exception;
	Map<Integer, String> getPromotionClasses(String classId,String corseId) throws Exception;
}
