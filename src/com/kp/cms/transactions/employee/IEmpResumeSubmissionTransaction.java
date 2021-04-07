package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.forms.employee.EmpResumeSubmissionForm;
import com.kp.cms.to.employee.EmpQualificationLevelTo;

public interface IEmpResumeSubmissionTransaction {

	Map<String, String> getDesignationMap()throws Exception;

	Map<String, String> getDepartmentMap()throws Exception;

	Map<String, String> getCountryMap()throws Exception;

	Map<String, String> getNationalityMap()throws Exception;

	Map<String, String> getQualificationLevelMap()throws Exception;

	String getApplicationNumber(String userid)throws Exception;

	Map<String, String> getSubjectAreaMap()throws Exception;

	List<EmpQualificationLevelTo> getQualificationFixedMap()throws Exception;

	boolean saveEmpResume(EmpOnlineResume empOnlineResume, EmpResumeSubmissionForm empResumeSubmissionForm)throws Exception;

	Map<String, String> getJobType()throws Exception;

	Map<String, String> getQualificationMap()throws Exception;

	Map<Integer, String> getEmployeeSubjectMap()throws Exception;
}
