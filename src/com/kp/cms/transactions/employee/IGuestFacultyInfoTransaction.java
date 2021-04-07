package com.kp.cms.transactions.employee;
	import java.util.List;
	import java.util.Map;

	import com.kp.cms.bo.admin.Department;
	import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmpOnlinePreviousExperience;
import com.kp.cms.bo.employee.GuestEducationalDetails;
	import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.employee.GuestPreviousExperience;
	import com.kp.cms.bo.admin.Users;
	import com.kp.cms.exceptions.ApplicationException;
	import com.kp.cms.forms.employee.GuestFacultyInfoForm;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.GuestEducationalDetailsTO;

	public interface IGuestFacultyInfoTransaction {
		
			Map<String, String> getDesignationMap()throws Exception;
			Map<String, String> getDepartmentMap()throws Exception;
			Map<String, String> getCountryMap()throws Exception;
			Map<String, String> getStateMap()throws Exception;
			Map<String, String> getNationalityMap()throws Exception;
			Map<String, String> getQualificationLevelMap()throws Exception;
			Map<String, String> getSubjectAreaMap()throws Exception;
			List<EmpQualificationLevelTo> getQualificationFixedMap()throws Exception;
			boolean saveEmployee(GuestFaculty employee, byte[] photo)throws Exception;
			Map<String, String> getJobType()throws Exception;
			Map<String, String> getQualificationMap()throws Exception;
			Map<String, String> getReligionMap()throws Exception;
			Map<String, String> getStreamMap()throws Exception;
			Map<String, String> getWorkLocationMap()throws Exception;
			Map<String, String> getTitleMap()throws Exception;
			public String getEmpId(String EmpId) throws ApplicationException;
			boolean SaveEditEmpDetails(GuestFaculty employee)throws Exception;
			public GuestFaculty GetEditEmpDetails(String empId) throws Exception ;
			public GuestFaculty GetEmpDetails(GuestFacultyInfoForm objform) throws Exception ;
			StringBuffer  getSerchedEmployeeQuery( int departmentId,
					int designationId, String Name, String Active, int streamId) throws Exception;
			List<GuestFaculty> getSerchedEmployee(StringBuffer query) throws Exception ;
			boolean checkCodeUnique(String code, String empId)throws Exception;
			boolean checkUidUnique(String Uid, String empId)throws Exception;
			List<Department> getEmployeeDepartment() throws Exception ;
			List<Designation> getEmployeeDesignation() throws Exception ;
			List getDataForQuery(String query) throws Exception;
	        public void getEmployeeId(int userId,GuestFacultyInfoForm objform)throws Exception;
			
			public Users userExists(GuestFacultyInfoForm objform)throws Exception;
			boolean updateUser(Users user)throws Exception;
			//Print functions
			GuestFaculty getDetailsForEmployee(String empId) throws Exception;
			List<GuestEducationalDetails> getEmployeeEducationDetails(String empId) throws Exception;
			List<GuestPreviousExperience> getEmployeeExperienceDetails(String empId) throws Exception;
			List<Department> getDepartmentList() throws Exception;
			List<Designation> getDesignationList() throws Exception;
			List<EmpQualificationLevel> getEmpQualificationList() throws Exception;
			public EmpOnlineResume GetGuestResumeDetails(GuestFacultyInfoForm objform) throws Exception ;
			public Map<Integer,String> getGuestFacultyMap()throws Exception;
			public GuestFaculty getGuestFacultyBo(int guestId)throws Exception;
			public boolean updateEditedGuestFacultyBankDetails(GuestFacultyInfoForm guestFacultyInfoForm)throws Exception;
		}





