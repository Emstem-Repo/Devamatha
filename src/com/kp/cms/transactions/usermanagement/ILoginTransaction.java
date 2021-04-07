package com.kp.cms.transactions.usermanagement;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.PeersEvaluationOpenSession;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLogin;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.admission.PublishStudentEdit;
import com.kp.cms.bo.admission.PublishStudentEditClasswise;
import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.SyllabusEntryForm;
import com.kp.cms.forms.usermanagement.LoginForm;

/**
 *  An interface for Login transaction.
 */

public interface ILoginTransaction {

	/**
	 * Verify users agenest username and password.
	 * @param loginForm - 
	 *                      Represents Login form object
	 * @return          - 
	 *                      User object if user name and password presents , null otherwise.
	 * @throws ApplicationException
	 */
	public Users verifyUser(LoginForm loginForm) throws ApplicationException;

	/**
	 * Returns the list of accessable modules for username and password
	 * @param loginForm -
	 *                      Represents Login form object
	 * @return List of accessable modules for username and password
	 * @throws ApplicationException
	 */
	public List getAccessableModules(LoginForm loginForm)
			throws ApplicationException;
	
	/**
	 *  Get the non menu links for the user.
	 * @param userName Represents the user name
	 * @return List of non menu links.
	 * @throws ApplicationException
	 */
	public List getNonMenuLinksForUser(String userName)
	throws ApplicationException;
	
	/**
	 * Verify student agenest username and password.
	 * @param loginForm - 
	 *                      Represents Login form object
	 * @return          - 
	 *                      User object if user name and password presents , null otherwise.
	 * @throws ApplicationException
	 */
	public StudentLogin verifyStudentUser(LoginForm loginForm) throws ApplicationException;
	public boolean updateLastLoggedIn(int userId) throws Exception;

	public Employee getEmployeeDetails(String employeeId) throws Exception;

	public boolean getsaveMobileNo(Employee emp)throws Exception;	
	public Integer getNotificationCount(String userId)throws Exception;

	public String getMarksEnteryLinks(String userId, HttpSession session) throws Exception;

	public PeersEvaluationOpenSession getOpenSession(String departmentId)throws Exception;
	public List<Object> getResearchLinks(String userId, HttpSession session) throws Exception;
	
	public String getDepartmentByUserId(String id) throws Exception;
	
	public int getStudentIdByUserName(LoginForm loginForm) throws Exception;
	
	public boolean checkStudentIsFinalYearOrNot(int studentId) throws Exception;
	
	public int getAcademicYearByStudentRegNo(HttpSession session) throws Exception;
	
	public boolean saveConvocationRegistration(ConvocationRegistration registration) throws Exception;

	public ConvocationRegistration loadConvocationRegistration(HttpSession httpSession) throws Exception;
	
	public void setIsLoggedIn(Users users) throws Exception;
	
	public void getUserAndSetLoggedIn(int userId) throws Exception;
	public SapRegistration LoadSapRegistration(LoginForm loginForm) throws Exception;
	public boolean saveSapRegistration(SapRegistration registration) throws Exception;
	
	public List<Object[]> invigilationDutyAllotmentDetails(int userId) throws Exception;
	public Integer getDutyAllotmentDetailsSize(int userId)throws Exception;

	public boolean checkForSyllabusEntryOpen(SyllabusEntryForm syllabusEntryForm,
			Date convertStringToSQLDate)throws Exception;
	public List<Object[]> getAttendaceGrpah(String grpahQuery)throws Exception;

	public Student getStudentObj(String studentid)throws Exception;

	public boolean savePersonalData(PersonalData data)throws Exception;

	public boolean saveStudentPhoto(ApplnDoc docBO)throws Exception;

	public PublishStudentEditClasswise getClasswise(int classId)throws Exception;

	public PublishStudentEdit getStudentWise(String studentid) throws Exception;

	public boolean SaveAdmAppl(AdmAppln admappln) throws Exception;
}
