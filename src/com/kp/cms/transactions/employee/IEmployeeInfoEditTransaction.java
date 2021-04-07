package com.kp.cms.transactions.employee;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EditMyProfile;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;

public interface IEmployeeInfoEditTransaction {
	
	
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
		
		boolean saveEmployee(Employee employee, EmployeeInfoEditForm employeeInfoEditForm)throws Exception;
		Map<String, String> getJobType()throws Exception;

		Map<String, String> getQualificationMap()throws Exception;
		Map<String, String> getReligionMap()throws Exception;
		Map<String, String> getEmpTypeMap()throws Exception;
		
		Map<String, String> getReportToMap()throws Exception;
		Map<String, String> getStreamMap()throws Exception;
		Map<String, String> getWorkLocationMap()throws Exception;
		
		Map<String, String> getPayScaleMap(EmployeeInfoEditForm objform)throws Exception;
		Map<String, String> getTitleMap()throws Exception;
		
		public String getEmpId(String EmpId) throws ApplicationException;
		public EmpType getWorkTimeListQueryByEmpTyptId(String query) throws Exception;
		boolean SaveEditEmpDetails(Employee employee)throws Exception;
		public String getScaleQueryByPayscaleId(String payScaleId) throws ApplicationException;
		

		public Employee GetEditEmpDetails(String empId) throws Exception ;

		public Employee GetEmpDetails(EmployeeInfoEditForm objform) throws Exception ;
		
		StringBuffer  getSerchedEmployeeQuery(String Code, String uId, String FingerPrintId, int departmentId,
				int designationId, String Name, String Active, int streamId, String teachingstaff,int empTypeId,int eid,int rid2,int principleid,int adminid,int testid) throws Exception;
		
		List<Employee> getSerchedEmployee(StringBuffer query) throws Exception ;
		boolean checkCodeUnique(String code, String empId)throws Exception;
		boolean checkUidUnique(String Uid, String empId)throws Exception;
		boolean checkFingerPrintIdUnique(String FingerPrintId, String empId)throws Exception;
		List<Department> getEmployeeDepartment() throws Exception ;
		List<Designation> getEmployeeDesignation() throws Exception ;
		/*List<Integer> getSerchedEmployeePhotoList(String Code, String uId, String FingerPrintId, int departmentId,
				int designationId, String Name) throws Exception;*/
		public int getInitializationMonth(int empId)throws Exception;
		List getDataForQuery(String query) throws Exception;
        public void getEmployeeId(int userId,EmployeeInfoEditForm objform)throws Exception;
		
		public EditMyProfile getEditMyProfile(EmployeeInfoEditForm objform)throws Exception;
		
		public boolean saveEditEmployee(Employee employee,EmployeeInfoEditForm objform,String mode)throws Exception;
		public Users userExists(EmployeeInfoEditForm objform)throws Exception;
		boolean updateUser(Users user)throws Exception;
		
	}


