package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpAllowance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeApprover;
import com.kp.cms.bo.employee.EmpResearchPublicDetails;
import com.kp.cms.bo.employee.EmpResearchPublicMaster;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.employee.HodApprover;
import com.kp.cms.forms.employee.EmpAllowanceForm;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.forms.employee.EmpResPubPendApprovalForm;

public interface IEmpResPubDetailsTransaction {
	 public List<EmpResearchPublicMaster> getEmpResPublicList()throws Exception;
	   public boolean duplicateCheck(String name,HttpSession session,ActionErrors errors, EmpResPubDetailsForm empResPubForm);
	   public boolean addResPubMaster(EmpResearchPublicMaster resPub)throws Exception;
	   public EmpResearchPublicMaster getResPubMasterById(int id)throws Exception;
	   public boolean updateResPubMaster(EmpResearchPublicMaster resPub)throws Exception;
	   public boolean deleteResPubMaster(int id)throws Exception;
	   public boolean reactivateResPubMaster(EmpResPubDetailsForm empResPubForm)throws Exception;
	
	   public Map<String, String> getResPubMasterMap() throws Exception;
	   boolean saveEmpResPub(List<EmpResearchPublicDetails> empResPubDetails)throws Exception;
	   public Employee getEmployeeIdFromUserId (EmpResPubDetailsForm empResPubForm)throws Exception;
	   public List<EmpResearchPublicDetails> GetResearchDetails(EmpResPubDetailsForm empResPubForm)throws Exception;
	   public List<EmpResearchPublicDetails> GetResearchDetailsRej(EmpResPubDetailsForm empResPubForm)throws Exception;
	   public String getApproverEmailId (int empId)throws Exception;
	   public HodApprover getApproverIdFromEmpId (int empId)throws Exception;
	   StringBuffer  getSerchedEmployeeQueryAdmin(String FingerPrintId, int departmentId,
	   int designationId, String Name, String Active, int streamId, String teachingstaff,int empTypeId) throws Exception;
       List<Employee> getSerchedEmployeeAdmin(StringBuffer query) throws Exception ;
       List<Department> getEmployeeDepartment() throws Exception ;
		List<Designation> getEmployeeDesignation() throws Exception ;
		Map<String, String> getDesignationMap()throws Exception;
		Map<String, String> getDepartmentMap()throws Exception;
        Map<String, String> getEmpTypeMap()throws Exception;
		Map<String, String> getStreamMap()throws Exception;
		public List<EmpResearchPublicDetails> GetResearchDetailsAdmin(EmpResPubDetailsForm empResPubForm)throws Exception;
		public Employee GetEmployee(EmpResPubDetailsForm objform)throws Exception;
		StringBuffer  getSerchedGuestQueryAdmin(String FingerPrintId, int departmentId,
				   int designationId, String Name, String Active, int streamId, String teachingstaff,int empTypeId) throws Exception;
	    List<GuestFaculty> getSerchedGuestAdmin(StringBuffer query) throws Exception ;
	    public GuestFaculty GetGuest(EmpResPubDetailsForm objform)throws Exception;
	  
}
