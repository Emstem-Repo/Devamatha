package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.forms.employee.EmpResPubPendApprovalForm;

public interface IEmpResPubPendApprovalTransaction {
	
	List<Employee> getSerchedEmployee(String query) throws Exception ;
	List<Department> getEmployeeDepartment() throws Exception ;
	public List<EmpResearchPublicDetails> GetResearchDetails(int selectedEmpId)throws Exception;
	public int getEmployeeIdFromUserId (EmpResPubPendApprovalForm objform)throws Exception;
	boolean saveEmpResPub(List<EmpResearchPublicDetails> empResPubDetails)throws Exception;
	public Employee getEmployeeIdFromUserIdEmp (EmpResPubPendApprovalForm empResPubForm)throws Exception;
	StringBuffer  getSerchedEmployeeQuery(String userId, String FingerPrintId, int departmentId, String Name, String Active, boolean isApprovedList, boolean isPendingList) throws Exception;
	List<Employee> getSerchedEmployee(StringBuffer query) throws Exception ;
	Map<String, String> getDepartmentMap()throws Exception;
	public Employee getEmailfromEmployeeId(int id)throws Exception;
	public Employee GetEmployee(EmpResPubPendApprovalForm objform)throws Exception;
	public List<EmpResearchPublicDetails> getEmployeeResearchDetailsApproved(int selectedEmpId)throws Exception;
	public List<EmpResearchPublicDetails> getEmployeeApprovalPendingByEmployeeId (int selectedEmpId)throws Exception;
}
