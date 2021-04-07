package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.AssignCertificateCourse;
import com.kp.cms.forms.admission.AssignCertificateCourseForm;

public interface IAssignCertificateCourseTransaction {

	List<AssignCertificateCourse> getAllAssignCertificateCourses(int year,String semType) throws Exception;

	boolean saveAssignCertificateCourse(AssignCertificateCourse bo) throws Exception;

	boolean deleteAssignCertificateCourse(AssignCertificateCourseForm assignCertificateCourseForm,String mode);

	AssignCertificateCourse getAssignCertificateCourse(int id) throws Exception;

	AssignCertificateCourse checkDuplicateAssignCertificateCourse(String query) throws Exception;

}
