package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.AdmissionAbstractForm;


public interface IAdmissionAbstractTransaction {
	public List<Student> getAdmittedStudents(AdmissionAbstractForm admForm) throws Exception;
}
