package com.kp.cms.transactions.admission;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.StudentClassAndSubjectDetailsForm;

public interface IStudentClassAndSubjectDetailsTransaction {

	Student getStudentBO(StudentClassAndSubjectDetailsForm stuForm) throws Exception;

}
