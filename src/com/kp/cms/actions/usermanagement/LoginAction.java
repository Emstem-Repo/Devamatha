package com.kp.cms.actions.usermanagement;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.bo.employee.GuestImages;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.handlers.admin.NewsEventsHandler;
import com.kp.cms.handlers.usermanagement.CreateUserHandler;
import com.kp.cms.handlers.usermanagement.LoginHandler;
import com.kp.cms.to.admin.NewsEventsTO;
import com.kp.cms.to.usermanagement.LoginTransactionTo;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.EncryptUtil;

public class LoginAction extends BaseDispatchAction {

	
	/**
     * 
     * forwards to home pages
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	public ActionForward initLoginAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		LoginForm loginForm = (LoginForm) form;
		loginForm.setUserName(null);
		loginForm.setPassword(null);
		if(session != null && session.getAttribute("PhotoBytes")!=null){
			session.removeAttribute("PhotoBytes");
		}
		return mapping.findForward(CMSConstants.LOGIN_SUCCESS);
	}
	
	
	/**
     * 
     * Performs the Login action.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */
	public ActionForward loginAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		 ActionErrors errors = loginForm.validate(mapping, request);
		HttpSession session = request.getSession();
		//It use for Help,Don't Remove
		session.setAttribute("field",null);
		setUserId(request, loginForm);
		
		loginForm.setMobileNo(null);
		loginForm.setInvigilationDutyAllotmentDetails(null);
		if(session != null && session.getAttribute("onlineuser")!=null)
		{
			session.removeAttribute("onlineuser");
		}
			
		if(session != null && session.getAttribute("PhotoBytes")!=null){
			session.removeAttribute("PhotoBytes");
		}
		
		if (errors.isEmpty()) {
			try {
				if(request.getHeader("User-Agent").indexOf("Mobile") != -1) {
						 loginForm.setIsMobileAccess(true);
					  } else {
						    //nope, this is probably a desktop
							   loginForm.setIsMobileAccess(false);
				  }
				Users user = LoginHandler.getInstance().isValiedUser(loginForm) ;
				if (user == null) {
					message = new ActionMessage("knowledgepro.admin.validusername");
					messages.add("messages", message);
					addErrors(request, messages);
					loginForm.resetFields();
					if(CMSConstants.LINK_FOR_CJC){
						return mapping.findForward(CMSConstants.LOGIN_PAGE);
					}else{
						return mapping.findForward(CMSConstants.LOGIN_PAGE_NEW);
					}
				} else {
				   //start multiple login not allowed by user in same time done by mehaboob.
					if(loginForm.getHomePage()!=null && !loginForm.getHomePage().isEmpty())	{
						if(user.getMultipleLoginAllow() == null || !user.getMultipleLoginAllow()){
							if(user.getIsLoggedIn()!=null && user.getIsLoggedIn()){
								message = new ActionMessage(
								"knowledgepro.admin.login.already");
								messages.add("messages", message);
								addErrors(request, messages);
								if(CMSConstants.LINK_FOR_CJC){
								return mapping.findForward(CMSConstants.LOGIN_PAGE);
								}else{
									return mapping.findForward(CMSConstants.LOGIN_PAGE_NEW);
								}
							}
							if(user!=null){
								user.setIsLoggedIn(true);
								user.setLastLoggedIn(new Date());
								user.setIpAddress(session.getId());
								ILoginTransaction loginTransaction = new LoginTransactionImpl();
								loginTransaction.setIsLoggedIn(user);
							}
						}
						loginForm.setHomePage(null);
					}
					// end multiple login not allowed by user in same time done by mehaboob.
					if(user!=null && user.getRoles()!=null && user.getRoles().getId()>0){
						request.getSession().setAttribute("rid",String.valueOf(user.getRoles().getId()));
					}
					List<LoginTransactionTo> loginTransactionList = LoginHandler
							.getInstance().getAccessableModules(loginForm);
					if (loginTransactionList.isEmpty()) {
						message = new ActionMessage(
								"knowledgepro.admin.nopreveleges");
						messages.add("messages", message);
						addErrors(request, messages);
						loginForm.resetFields();
						if(CMSConstants.LINK_FOR_CJC){
						return mapping.findForward(CMSConstants.LOGIN_PAGE);
						}else{
							return mapping.findForward(CMSConstants.LOGIN_PAGE_NEW);
						}
					} else {
						loginForm.setModuleMenusList(loginTransactionList);
						LoginHandler.getInstance().updateLastLoggedIn(user.getId());  //updating last logged in
						if(user.getEmployee()!=null && user.getEmployee().getDob() !=null) {
							loginForm.setDateOfBirth(CommonUtil.getStringDate(user.getEmployee().getDob()));
						}
						else if(user.getGuest()!=null && user.getGuest().getDob() !=null) {
							loginForm.setDateOfBirth(CommonUtil.getStringDate(user.getGuest().getDob()));
						}
						session = request.getSession(false);
						String employeeId = "";
						if(user.getEmployee()!=null) {
							employeeId = String.valueOf(user.getEmployee().getId());
							session.setAttribute("employeeId", employeeId);
							if(user.getEmployee().getWorkEmail() !=null && !user.getEmployee().getWorkEmail().equalsIgnoreCase("")){
							loginForm.setContactMail(user.getEmployee().getWorkEmail());
							session.setAttribute("usermail", user.getEmployee().getWorkEmail());
							}else{
								session.setAttribute("usermail", user.getEmployee().getEmail());	
							}
							if(user.getEmployee().getCurrentAddressMobile1() !=null){
							loginForm.setMobileNo(user.getEmployee().getCurrentAddressMobile1());
							session.setAttribute("usermobile", user.getEmployee().getCurrentAddressMobile1());
							}
							if(user.getEmployee()!=null && !user.getEmployee().toString().isEmpty()){
								if(user.getEmployee().getDepartment()!=null && !user.getEmployee().getDepartment().toString().isEmpty()){
									loginForm.setDepartmentName(String.valueOf(user.getEmployee().getDepartment().getName()));
									loginForm.setDoj(CommonUtil.getStringDate(user.getEmployee().getDoj()));
								}
						}
						}
						
						String guestId = "";
						if(user.getGuest()!=null) {
							guestId = String.valueOf(user.getGuest().getId());
							session.setAttribute("guestId", guestId);
							if(user.getGuest().getWorkEmail() !=null && !user.getGuest().getWorkEmail().equalsIgnoreCase("")){
							loginForm.setContactMail(user.getGuest().getWorkEmail());
							session.setAttribute("usermail", user.getGuest().getWorkEmail());
							}else{
								session.setAttribute("usermail", user.getGuest().getEmail());	
							}
							if(user.getGuest().getCurrentAddressMobile1() !=null){
							loginForm.setMobileNo(user.getGuest().getCurrentAddressMobile1());
							session.setAttribute("usermobile", user.getGuest().getCurrentAddressMobile1());
							}
						}
						if(CMSConstants.LINK_FOR_CJC){
							byte[] photo = null;
							if(user.getEmployee()!=null && user.getEmployee().getEmpImages() !=null && !user.getEmployee().getEmpImages().isEmpty()) {
								session = request.getSession(false);
								Iterator<EmpImages> empImages=user.getEmployee().getEmpImages().iterator();
								while (empImages.hasNext()) {
									EmpImages bo=empImages .next();
									if(bo.getEmpPhoto()!=null){
										photo =bo.getEmpPhoto();
										break;
									}
								}
								
								if (session != null) {
									session.setAttribute("PhotoBytes", photo);
								}
							}
							if(user.getGuest()!=null && user.getGuest().getEmpImages() !=null && !user.getGuest().getEmpImages().isEmpty()) {
								session = request.getSession(false);
								Iterator<GuestImages> empImages=user.getGuest().getEmpImages().iterator();
								while (empImages.hasNext()) {
									GuestImages bo=empImages .next();
									if(bo.getEmpPhoto()!=null){
										photo =bo.getEmpPhoto();
										break;
									}
								}
								
								if (session != null) {
									session.setAttribute("PhotoBytes", photo);
								}
							}
						}else{
							if(user.getEmployee()!=null){
								session.setAttribute("EMP_IMAGE_HOME", "images/EmployeePhotos/E"+user.getEmployee().getId()+".jpg?"+user.getEmployee().getLastModifiedDate());
							}
							if(user.getGuest()!=null){
								session.setAttribute("EMP_IMAGE_HOME", "images/EmployeePhotos/G"+user.getGuest().getId()+".jpg?"+user.getGuest().getLastModifiedDate());
							}
						}
						
						/* --------------------------------------code added by sudhir-------------------------*/
						if(user.getEmployee()!=null && !user.getEmployee().toString().isEmpty()){
							if(user.getEmployee().getDepartment()!=null && !user.getEmployee().getDepartment().toString().isEmpty()){
								session.setAttribute("DepartmentId", String.valueOf(user.getEmployee().getDepartment().getId()));
								session.setAttribute("DepartmentName", user.getEmployee().getDepartment().getName());
							}
						}else if(user.getGuest()!=null && !user.getGuest().toString().isEmpty()){
							if(user.getGuest().getDepartment()!=null && !user.getGuest().getDepartment().toString().isEmpty()){
								session.setAttribute("DepartmentId", String.valueOf(user.getGuest().getDepartment().getId()));
								session.setAttribute("DepartmentName", user.getGuest().getDepartment().getName());
							}
						}else{
							if(user.getDepartment() != null){
								session.setAttribute("DepartmentId", String.valueOf(user.getDepartment().getId()));
								session.setAttribute("DepartmentName", user.getDepartment().getName());
							}
						}
					
					/*-------------------------------------------------------------------------------------*/
						if(user.getId()>0)
						{
						String userId= String.valueOf(user.getId());
						setNotifications(loginForm,userId,session,user);
						}
						
						// modified by sudhir 
						if(loginForm.getDescription()==null || loginForm.getDescription().isEmpty()){
							String description = "";
							NewsEventsTO eventsTO = null;
							StringBuffer buffer = new StringBuffer();
							List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance().getNewsEvents(CMSConstants.LOGIN_ADMIN);
							Iterator<NewsEventsTO> iterator = newsEventsList.iterator();
							while (iterator.hasNext()) {
								eventsTO = (NewsEventsTO ) iterator.next();
								description = buffer.append(eventsTO.getName()).append("<br></br>").toString();
							}
							loginForm.setDescription(description);
						}
						// /* code added by chandra
						Integer dutyAllotmentDetails=LoginHandler.getInstance().checkingDutyAllotmentDetails(user.getId());
						if(dutyAllotmentDetails >0){
							if(user.getIsTeachingStaff()){
								loginForm.setIsAllotmentDetails(true);
							}else{
								loginForm.setIsAllotmentDetails(false);
							}
						}
						
						// */
						
						//modified by sudhir 
						//session.getServletContext().setAttribute("DateForLogin", new Date());
						session.setAttribute("uid", String.valueOf(user.getId()));
						session.setAttribute("username", user.getUserName());
						
						if(user.getEmployee()!=null){
							session.setAttribute("empname", user.getEmployee().getFirstName());
						}else if (user.getGuest()!=null) {
							session.setAttribute("empname", user.getGuest().getFirstName());
						}else{
							session.setAttribute("empname","");
						}
					

						session.setMaxInactiveInterval(CMSConstants.MAX_SESSION_INACTIVE_TIME_FOR_EMPLOYEE);
//						if(CMSConstants.SECOND_PAGE_DISPLAY){
//							if(user.getEnableAttendanceEntry() != null && user.getEnableAttendanceEntry()){
//								if(loginForm.getLoginType() != null){
//									if(loginForm.getLoginType().equalsIgnoreCase("Normal")){
//										return mapping.findForward(CMSConstants.LOGIN_SUCCESS);
//									}
//								}
//								if(user.getIsTeachingStaff()){
//									return mapping.findForward(CMSConstants.TEACHERLOGIN_SUCCESS);
//								}
//							}
//						}
						//added by mahi start
						 loginForm.setServerDownMessage(null);
						 String maintenanceMessage =  MaintenanceAlertHandler.getInstance().getMaintenanceDetailsByDate();
						 if(maintenanceMessage!=null){
							 loginForm.setServerDownMessage(maintenanceMessage);
							 session.setAttribute("serverDownMessage", maintenanceMessage);
						 }
						 session.setAttribute("isEmployee", "1");
						 //end
						 
						if(!loginForm.isAttendanceLogin()){
							System.out.println("************** admin login");
							return mapping.findForward(CMSConstants.LOGIN_SUCCESS);
							/*boolean isSent=CreateUserHandler.getInstance().initOtpGeneration(session,"Login");
							if(isSent){
								return mapping.findForward("getOTPPage");
							}else{
								errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Please provide mobile or emailId."));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.LOGIN_PAGE_NEW);
							}*/
							
						}
						else{
							System.out.println("**************** teacher login");
							return mapping.findForward(CMSConstants.TEACHERLOGIN_SUCCESS);
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				String msg = super.handleApplicationException(e);
				loginForm.setErrorMessage(msg);
				loginForm.setErrorStack(e.getMessage());
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
				return mapping.findForward(CMSConstants.LOGIN_PAGE_NEW);
			}
		}else {
			addErrors(request, errors);	
			if(CMSConstants.LINK_FOR_CJC){
				return mapping.findForward(CMSConstants.LOGIN_PAGE);
			}else{
				return mapping.findForward(CMSConstants.LOGIN_PAGE_NEW);
			}
		}
		

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMobileNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		LoginForm loginForm = (LoginForm) form;
		setUserId(request, loginForm);
		HttpSession session = request.getSession();
		//boolean isUpdate=false;
		try{
			String mobileNo=loginForm.getMobileNo();
			String userId=loginForm.getUserId();
			String employeeId=(String) session.getAttribute("employeeId");
			//isUpdate=
					LoginHandler.getInstance().saveMobileNo(mobileNo,userId,employeeId);
			loginForm=LoginHandler.getInstance().getMobileNo(loginForm,employeeId);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			loginForm.setErrorMessage(msg);
			loginForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.LOGIN_SUCCESS);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward adminFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward(CMSConstants.FEED_BACK_INIT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward teacherFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward(CMSConstants.FEED_BACK_INIT1);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward attendanceLoginAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		if(loginForm.getLoginType() != null){
			if(loginForm.getLoginType().equalsIgnoreCase("AttendanceView") || loginForm.getLoginType().equalsIgnoreCase("AutomaticAttendanceView")){
				request.setAttribute("view",loginForm.getLoginType());
				return mapping.findForward(CMSConstants.TEACHERLOGIN_VIEW);
			}
		}
		if(!loginForm.isAttendanceLogin())
			return mapping.findForward(CMSConstants.LOGIN_SUCCESS);
		else
			return mapping.findForward(CMSConstants.TEACHERLOGIN_SUCCESS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelLoginAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginForm loginForm = (LoginForm) form;
		if(!loginForm.isAttendanceLogin())
			return mapping.findForward(CMSConstants.LOGIN_SUCCESS);
		else
			return mapping.findForward(CMSConstants.TEACHERLOGIN_SUCCESS);
	}
	
	public void setNotifications(LoginForm loginForm,String userId, HttpSession session, Users user) throws Exception {
		
		try {
			/*List<NewsEventsTO> newsEventsList = NewsEventsHandler.getInstance()
					.getNewsEvents(CMSConstants.LOGIN_STUDENT);
			Iterator<NewsEventsTO> itr = newsEventsList.iterator();
			while (itr.hasNext()) {
				eventsTO = (NewsEventsTO) itr.next();
				notifications.append(eventsTO.getName()).append("<br/><br/>");
			}*/
			
			Integer count= LoginHandler.getInstance().getNotifications(userId);
			loginForm.setNotifications(count);
			String displayExamName  = LoginHandler.getInstance().getMarksEnteryLinks(userId,session);
			loginForm.setCiaEntrys(false);
			if(displayExamName != null && !displayExamName.isEmpty()){
				loginForm.setCiaEntrys(true);
				loginForm.setDisplayLinkExamName(displayExamName);
			}
			/*-----------------------------------------------code added by sudhir---------------------------------------*/
			if(user.getIsTeachingStaff()){
				boolean isPresent = LoginHandler.getInstance().getIsPresentPeersEvaluationLink(loginForm,session);
				if(isPresent){
					loginForm.setPeerEvaluationLinkPresent(isPresent);
				}
			}else{
				loginForm.setPeerEvaluationLinkPresent(false);
			}
			/*-----------------------------------------------------------------------------------------------------------*/
				/*-----------------------------------------------code added by mary---------------------------------------*/
				List<Object> researchList = LoginHandler.getInstance().getIsResearchLink(userId,session);
				loginForm.setResearchLinkPresent(false);
				if(researchList != null && !researchList.isEmpty()){
					loginForm.setResearchLinkPresent(true);
				}
			
			/*-----------------------------------------------------------------------------------------------------------*/
	   }catch (Exception e) {
		   throw e;
	  }
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * added by mehaboob ajax call for login to checkuser is logged out properly or not 
	 */
	
	public void ajaxCallForlogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		setUserId(request, loginForm);
		HttpSession session = request.getSession();
			try {
				loginForm.setEncryptedPassword(EncryptUtil.getInstance().encryptDES(loginForm.getPassword()));
				Users user = LoginHandler.getInstance().isValiedUser(loginForm) ;
				if(user!=null){
					int count=0;
					String userId=null;
					if(user.getMultipleLoginAllow() == null || !user.getMultipleLoginAllow()){
					if(user.getIsLoggedIn()!=null && user.getIsLoggedIn()){
						   session.setAttribute("uid", String.valueOf(user.getId()));
							Calendar calendar=Calendar.getInstance();
							if(user.getLastLoggedIn()!=null)
							   calendar.setTime(user.getLastLoggedIn());
							Calendar calendar2=Calendar.getInstance();
							calendar2.setTime(new Date());
							DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							if(dateFormat.format(calendar.getTime()).compareTo(dateFormat.format(calendar2.getTime()))==0){
								long remaining=new Date().getTime()-user.getLastLoggedIn().getTime();
								long hours=remaining/(60 * 60 * 1000);
								//long minutes=remaining/(60 * 1000);
								if(hours>=2){
									userId=session.getAttribute("uid").toString();
									session.invalidate();
									count=1;
								}
							}else{
								userId=session.getAttribute("uid").toString();
								session.invalidate();
								count=1;
							}if(user.getUserName().equalsIgnoreCase(loginForm.getUserName()) && session.getId().equals(user.getIpAddress())){
								if(count==0){
									response.getWriter().write("true");	
								}else{
									session=request.getSession();
									session.setAttribute("uid", userId);
									response.getWriter().write("false");
								}	
							}else{
								session.removeAttribute("uid");
								response.getWriter().write("notAllowMultiple");
							}
					}else{
						response.getWriter().write("false");
					}
					}
				}else{
					response.getWriter().write("please Enter Valid");
				}
			}catch (Exception e) {
				   throw e;
			  }
		}
	
	public ActionForward getInvigilationDutyAllotment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginForm loginForm = (LoginForm) form;
		HttpSession session = request.getSession();
		String userId=session.getAttribute("uid").toString();
		List<LoginTransactionTo> dutyAllotmentDetails=LoginHandler.getInstance().invigilationDutyAllotment(Integer.parseInt(userId));
		loginForm.setInvigilationDutyAllotmentDetails(dutyAllotmentDetails);
		request.setAttribute("allotmentDetails", dutyAllotmentDetails);
		return mapping.findForward("invigilationDutyAllotment");
	}
	
	public ActionForward checkUserOTP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			LoginForm loginForm = (LoginForm) form;
			HttpSession session=request.getSession(false);
			String userOTP=loginForm.getUserOTP();
			try{
				if(session!=null && session.getAttribute("isEmployee")!=null && session.getAttribute("OTP").toString().equalsIgnoreCase(userOTP)){
					
					java.util.Calendar oldcalObj=(Calendar)session.getAttribute("generatedTime");
					oldcalObj.add(oldcalObj.MINUTE, 5);
					Time logoutTime=new Time(oldcalObj.getTime().getTime());
					//new time
					Time currentTime=new Time(Calendar.getInstance().getTime().getTime());
					
					if (currentTime.before(logoutTime)) {
						session.removeAttribute("OTP");
						session.removeAttribute("generatedTime");
						 List<LoginTransactionTo> loginTransactionList = LoginHandler
							.getInstance().getAccessableModules(loginForm);
						 session.setAttribute("isEmployeeOTPChecked","1");
						loginForm.setModuleMenusList(loginTransactionList);
					 	return mapping.findForward(CMSConstants.LOGIN_SUCCESS); 
					 
							
					 }else{
						 return mapping.findForward("logout");
					 }
					 
				}else{
					return mapping.findForward("logout");
				}
				
			}catch (Exception e) {
					return mapping.findForward("logout");
			}
			
		}
	
	
		
	
}
