package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpOnlineResumeUsers;
import com.kp.cms.forms.employee.DownloadApplicationForm;

public interface IDownloadApplicationTransaction {

	public List<Department> getDepartmentList()throws Exception;

	public List<EmpQualificationLevel> getEmpQualificationList()throws Exception;

	public List<EmpOnlineResumeUsers> getEmployeeDetails( DownloadApplicationForm downloadApplicationForm)throws Exception;

	public EmpOnlineResume getDetailsForEmployee(String empId)throws Exception;

	public List<EmpOnlineEducationalDetails> getEmployeeEducationDetails( String empId)throws Exception;

	public List<EmpOnlinePreviousExperience> getEmployeeExperienceDetails( String empId)throws Exception;

}
