package com.kp.cms.helpers.usermanagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AccessPrivileges;
import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Menus;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.usermanagement.LoginTransactionTo;
import com.kp.cms.utilities.CommonUtil;

/**
 * Singleton class for LoginTransactionHelper
 */
public class LoginTransactionHelper {

	/**
	 * Represents singleton object of LoginTransactionHelper
	 */
	private static volatile LoginTransactionHelper loginTransactionHelper = null;

	private LoginTransactionHelper() {

	}

	/**
	 * @return singleton object of loginTransactionHelper object
	 */
	public static LoginTransactionHelper getInstance() {
		if (loginTransactionHelper == null) {
			loginTransactionHelper = new LoginTransactionHelper();
		}
		return loginTransactionHelper;
	}

	/**
	 * Converts from buisness object to transactional objects
	 * 
	 * @param getAccessableModules
	 *            - Represents the List of buisness objects.
	 * @param loginForm 
	 * @return - List<LoginTransactionTo> Converted transactional objects.
	 */
	public List<LoginTransactionTo> getModulesFromList(List getAccessableModules, LoginForm loginForm) {
		Integer menuCount=0;
		List<LoginTransactionTo> accessableModulesList = new ArrayList<LoginTransactionTo>();

		Map<Modules, List<Menus>> moduleIdMap = new LinkedHashMap<Modules, List<Menus>>();
		if (getAccessableModules != null) {

			Iterator accessableModulesIterator = getAccessableModules
					.iterator();
			while (accessableModulesIterator.hasNext()) {
				Object[] accessableObject = (Object[]) accessableModulesIterator
						.next();
				AccessPrivileges accessPrevileges = (AccessPrivileges) accessableObject[2];

				List<Menus> menuList = moduleIdMap.get(accessPrevileges
						.getModules());
				if (menuList == null) {
					List<Menus> menusList = new ArrayList<Menus>();
					menusList.add(accessPrevileges.getMenus());
					moduleIdMap.put(accessPrevileges.getModules(), menusList);
				} else {
					menuList.add(accessPrevileges.getMenus());
					moduleIdMap.put(accessPrevileges.getModules(), menuList);
				}
			}

			Iterator it = moduleIdMap.keySet().iterator();
			while (it.hasNext()) {
				Modules module = (Modules) it.next();
				List<Menus> menuToList = moduleIdMap.get(module);
				menuCount=menuCount+menuToList.size();
				LoginTransactionTo loginTransactionTo = new LoginTransactionTo();
				loginTransactionTo.setMenuTOList(menuToList);
				loginTransactionTo.setModuleName(module.getDisplayName());
				loginTransactionTo.setModulePosition(module.getPosition());
				accessableModulesList.add(loginTransactionTo);

			}
			loginForm.setMenuCount(menuCount);
			Collections.sort(accessableModulesList);

		}

		return accessableModulesList;

	}

	/**
	 * @param employee
	 * @throws Exception
	 */
	public Employee copyFormToBO(String mobileNo, String userId,Employee employee)throws Exception {
		if(mobileNo!=null && !mobileNo.isEmpty()){
			employee.setCurrentAddressMobile1(mobileNo);
		}
		employee.setLastModifiedDate(new Date());
		employee.setModifiedBy(userId);
		return employee;
	}

	/**
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	public LoginForm copyBOToForm(Employee employee)throws Exception {
		LoginForm loginForm =new LoginForm();
		if(employee!=null){
			if(employee.getCurrentAddressMobile1()!=null && !employee.getCurrentAddressMobile1().isEmpty()){
				loginForm.setMobileNo(employee.getCurrentAddressMobile1());
			}
		}
		return loginForm;
	}

	/**
	 * @param academicYear
	 * @param loginForm
	 * @param session
	 * @return
	 */
	public ConvocationRegistration convertFormToBO(int academicYear,LoginForm loginForm,HttpSession session)
	{
		
		ConvocationRegistration registration=new ConvocationRegistration();
		if(loginForm.getConvocationId()!=0)
		{
		registration.setId(loginForm.getConvocationId());
		}
		registration.setAcademicYear(academicYear);
		if(loginForm.getGuestPassRequired()!=null)
		{
		registration.setGuestPassIsRequired(loginForm.getGuestPassRequired());
		}
		if(loginForm.getParticipatingConvocation()!=null)
		{
		registration.setParticipatingConvocation(loginForm.getParticipatingConvocation());
		}
		if(loginForm.getRelationshipWithGuest()!=null && !loginForm.getRelationshipWithGuest().isEmpty())
		{
		registration.setRelationshipWithGuest(loginForm.getRelationshipWithGuest());
		}
		registration.setCreatedBy(loginForm.getUserId());
		registration.setCreatedDate(new Date());
		registration.setModifiedBy(loginForm.getUserId());
		registration.setLastModifiedDate(new Date());
		registration.setIsActive(true);
		Student student=new Student();
		String studentId=session.getAttribute("studentIdforConvocation").toString();
		if(studentId!=null && !studentId.isEmpty())
		{
		student.setId(Integer.parseInt(studentId));
		registration.setStudent(student);
		}
		return registration;
		
	}
	
	/**
	 * @param registration
	 * @param loginForm
	 */
	public void convertBoToConvocationForm(ConvocationRegistration registration,LoginForm loginForm)
	{
	loginForm.setParticipatingConvocation(registration.isParticipatingConvocation());
	loginForm.setGuestPassRequired(registration.isGuestPassIsRequired());
   if(registration.getRelationshipWithGuest()!=null && !registration.getRelationshipWithGuest().isEmpty()){
	loginForm.setRelationshipWithGuest(registration.getRelationshipWithGuest());
   }
   loginForm.setConvocationId(registration.getId());
	}
	
	
	public SapRegistration convertFormToBOSap(LoginForm loginForm)
	{
		SapRegistration registration=new SapRegistration();
		if(loginForm.getSapId()!=0)
		{
			registration.setId(loginForm.getSapId());
		}		
		if(loginForm.getStudentId()>0)
		{
			Student sid=new Student();
			sid.setId(loginForm.getStudentId());
			registration.setStdId(sid);
		}
		registration.setRegisteredDate(new Date());
		registration.setCreatedBy(loginForm.getUserId());
		registration.setCreatedDate(new Date());
		registration.setModifiedBy(loginForm.getUserId());
		registration.setLastModifiedDate(new Date());
		registration.setIsActive(true);
		return registration;
	}
	
	public List<LoginTransactionTo> converBoToTo(List<Object[]> dutyAllotmentDetails){
		List<LoginTransactionTo> toList=new ArrayList<LoginTransactionTo>();
		Iterator itr = dutyAllotmentDetails.iterator();
		
		while (itr.hasNext()) {
			LoginTransactionTo to=new LoginTransactionTo();
			Object[] bo = (Object[]) itr.next();
			to.setRoom(bo[0].toString());
			to.setType(bo[1].toString());
			to.setExamDate((CommonUtil.formatSqlDate(bo[2].toString())));
			if(bo[3] != null)
				to.setSession(bo[3].toString());
			toList.add(to);
		}
		return toList;
	}
	
}
