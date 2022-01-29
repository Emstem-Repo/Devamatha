package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.EmpOnlineResumeUsers;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.forms.employee.DownloadEmployeeResumeForm;


public interface IDownloadEmployeeResumeTransaction {

	List<Department> getDepartmentList() throws Exception;
	List<Department> getDepartmentList1() throws Exception;

	List<Designation> getDesignationList() throws Exception;

	List<EmpQualificationLevel> getEmpQualificationList() throws Exception;

	List<EmpOnlineResume> getEmployeeDetails(DownloadEmployeeResumeForm downloadEmployeeResumeForm) throws Exception;

	
	EmpOnlineResume getDetailsForEmployee(String empId) throws Exception;

	List<EmpOnlineEducationalDetails> getEmployeeEducationDetails(String empId) throws Exception;

	List<EmpOnlinePreviousExperience> getEmployeeExperienceDetails(String empId) throws Exception;

    public EmpOnlineResume getEmpDetailsByAppNo(int appNo)throws Exception;
    
    public boolean saveStatus(DownloadEmployeeResumeForm downloadEmployeeResumeForm)throws Exception;
    
    public void setStatus(int appNo)throws Exception;
    
	public List<Object[]> getUsersList()throws Exception;
	public boolean saveEmpOnlineResumeUsersDetails(String[] userIds, int empOnlineResumeId, DownloadEmployeeResumeForm downloadEmployeeResumeForm)throws Exception;
	
	public List<EmpOnlineResumeUsers> getEmployeeForwardedDetails( DownloadEmployeeResumeForm downloadEmployeeResumeForm)throws Exception;
	
	public Map<Integer,String> getEmployeeSubjects() throws Exception;
}
