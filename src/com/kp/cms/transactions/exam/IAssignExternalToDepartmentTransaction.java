package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.ExternalEvaluatorsDepartment;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.exam.AssignExternalToDepartmentForm;

public interface IAssignExternalToDepartmentTransaction {
	public List<Department> getDepartment() throws Exception;
	public List<ExamValuators> getExternalDetails() throws Exception;
	public boolean saveEvaluators(List<ExternalEvaluatorsDepartment> bolist) throws Exception;
	public Map<Integer,Integer> getExternalEvaluatorsDepartmentList(AssignExternalToDepartmentForm assignExternalToDepartmentForm)throws Exception;
}
