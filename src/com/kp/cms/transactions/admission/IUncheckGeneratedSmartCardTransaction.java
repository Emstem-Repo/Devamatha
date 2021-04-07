package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.UncheckGeneratedSmartCardForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;

public interface IUncheckGeneratedSmartCardTransaction {

	List<Student> getGeneratedStudentList(UncheckGeneratedSmartCardForm uncheckGenSCForm) throws Exception;

	boolean updateGeneratedFlag(List<StudentTO> generatedStudentList,UncheckGeneratedSmartCardForm uncheckForm) throws Exception;

	List<Employee> getGeneratedEmployeeList(UncheckGeneratedSmartCardForm uncheckForm) throws Exception;

	boolean updateGeneratedEmployeeFlag(List<EmployeeTO> generatedEmployeeList,UncheckGeneratedSmartCardForm uncheckForm) throws Exception;

}
