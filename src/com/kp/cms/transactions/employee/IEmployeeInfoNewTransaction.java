package com.kp.cms.transactions.employee;

	import java.util.List;
	import java.util.Map;
	
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
	import com.kp.cms.bo.employee.EmployeeInfoBO;
	import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;

	public interface IEmployeeInfoNewTransaction {

		Map<String, String> getDesignationMap()throws Exception;

		Map<String, String> getDepartmentMap()throws Exception;

		Map<String, String> getCountryMap()throws Exception;

		Map<String, String> getStateMap()throws Exception;

		Map<String, String> getNationalityMap()throws Exception;

		Map<String, String> getQualificationLevelMap()throws Exception;

		String getApplicationNumber(EmployeeSettings empSettings)throws Exception;

		Map<String, String> getSubjectAreaMap()throws Exception;

		List<EmpQualificationLevelTo> getQualificationFixedMap()throws Exception;
		List<EmpAllowanceTO> getPayAllowanceFixedMap()throws Exception;
		public List<EmpLeaveAllotment> getEmpLeaveListQueryByEmpTyptId(String query) throws Exception;
		
		boolean saveEmployee(Employee employee, EmployeeInfoFormNew employeeInfoFormNew)throws Exception;

		Map<String, String> getJobType()throws Exception;

		Map<String, String> getQualificationMap()throws Exception;
		Map<String, String> getReligionMap()throws Exception;
		Map<String, String> getEmpTypeMap()throws Exception;
		
		Map<String, String> getReportToMap()throws Exception;
		Map<String, String> getStreamMap()throws Exception;
		Map<String, String> getWorkLocationMap()throws Exception;
		
		Map<String, String> getPayScaleMap()throws Exception;
		Map<String, String> getTitleMap()throws Exception;
		
		public String getEmpId(String EmpId) throws ApplicationException;
		public EmpType getWorkTimeListQueryByEmpTyptId(String query) throws Exception;
		boolean SaveEditEmpDetails(Employee employee)throws Exception;
		public String getScaleQueryByPayscaleId(String payScaleId) throws ApplicationException;
		public Integer getEmpAgeRetirement() throws ApplicationException;
		
		public Employee GetEditEmpDetails(String empId) throws Exception ;
		public EmpOnlineResume GetEmpDetails(EmployeeInfoFormNew objform) throws Exception ;
		public String getLeaveInitMonthEmpTypeId(String EmpTypeId) throws ApplicationException;
		boolean checkCodeUnique(String code)throws Exception;
		boolean checkUidUnique(String Uid)throws Exception;
		boolean checkFingerPrintIdUnique(String FingerPrintId)throws Exception;
		public List<Object[]> getPayscaleTeachingStaff(String teachingStaff)throws Exception;
		public int getInitializationMonth(int empId)throws Exception;
	}