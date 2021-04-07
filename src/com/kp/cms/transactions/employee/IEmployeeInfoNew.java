package com.kp.cms.transactions.employee;
	
	import java.util.List;
	import java.util.Map;
	
	import com.kp.cms.bo.employee.EmployeeInfoBO;
	import com.kp.cms.bo.employee.EmployeeSettings;
	import com.kp.cms.to.employee.EmpQualificationLevelTo;

	public interface IEmployeeInfoNew {

		Map<String, String> getDesignationMap()throws Exception;

		Map<String, String> getDepartmentMap()throws Exception;

		Map<String, String> getCountryMap()throws Exception;

		Map<String, String> getStateMap()throws Exception;

		Map<String, String> getNationalityMap()throws Exception;

		Map<String, String> getQualificationLevelMap()throws Exception;

		String getApplicationNumber(EmployeeSettings empSettings)throws Exception;

		Map<String, String> getSubjectAreaMap()throws Exception;

		List<EmpQualificationLevelTo> getQualificationFixedMap()throws Exception;

		boolean saveEmpResume(EmployeeInfoBO employeeInfoBO)throws Exception;

		Map<String, String> getJobType()throws Exception;

		Map<String, String> getQualificationMap()throws Exception;

	}