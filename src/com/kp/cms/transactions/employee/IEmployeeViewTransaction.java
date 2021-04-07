package com.kp.cms.transactions.employee;
	import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoViewForm;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;

	public interface IEmployeeViewTransaction {
		

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
			
			boolean saveEmployee(Employee employee)throws Exception;

			Map<String, String> getJobType()throws Exception;

			Map<String, String> getQualificationMap()throws Exception;
			Map<String, String> getReligionMap()throws Exception;
			Map<String, String> getEmpTypeMap()throws Exception;
			
			Map<String, String> getReportToMap()throws Exception;
			Map<String, String> getStreamMap()throws Exception;
			Map<String, String> getWorkLocationMap()throws Exception;
			
			Map<String, String> getPayScaleMap(EmployeeInfoViewForm objform)throws Exception;
			Map<String, String> getTitleMap()throws Exception;
			
			public String getEmpId(String EmpId) throws ApplicationException;
			public EmpType getWorkTimeListQueryByEmpTyptId(String query) throws Exception;
			boolean SaveEditEmpDetails(Employee employee)throws Exception;
			public String getScaleQueryByPayscaleId(String payScaleId) throws ApplicationException;
			

			public Employee GetEditEmpDetails(String empId) throws Exception ;

			public Employee GetEmpDetails(EmployeeInfoViewForm objform) throws Exception ;
			public Employee GetMyDetails(EmployeeInfoViewForm objform) throws Exception ;
			
			
			StringBuffer  getSerchedEmployeeQuery(String Code, String uId, String FingerPrintId, int departmentId,
					int designationId, String Name, String Active, int streamId, String teachingstaff, int empTypeId) throws Exception;
			
			List<Employee> getSerchedEmployee(StringBuffer query) throws Exception ;
			boolean checkCodeUnique(String code, String empId)throws Exception;
			boolean checkUidUnique(String Uid, String empId)throws Exception;
			boolean checkFingerPrintIdUnique(String FingerPrintId, String empId)throws Exception;
			List<Department> getEmployeeDepartment() throws Exception ;
			List<Designation> getEmployeeDesignation() throws Exception ;
			/*List<Integer> getSerchedEmployeePhotoList(String Code, String uId, String FingerPrintId, int departmentId,
					int designationId, String Name) throws Exception;*/
			public int getInitializationMonth(int empId)throws Exception;
			StringBuffer getSerDeptEmpQuery(String Code, String FingerPrintId, String Name, String userId) throws Exception;
			List<Employee> getSerchedDeptEmployee(StringBuffer query,EmployeeInfoViewForm stForm) throws Exception ;
			
			public List<String> getFingerPrintIds(int userId)throws Exception;
			
			public List<String> getEmpCodes(int userId)throws Exception;
			
			public List<String> getEmpNames(int userId)throws Exception;
			
			public String getDepartmentNameForFingerPrintId(String fingerPrintId)throws Exception;

			public String getDepartmentNameForEmpCode(String empCode)throws Exception;

			public String getDepartmentNameForEmpName(String employeeName)throws Exception;

			Users GetUsersDetails(EmployeeInfoViewForm objform)throws Exception;
		}





