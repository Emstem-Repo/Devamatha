package com.kp.cms.actions.usermanagement;

import java.sql.Time;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.usermanagement.CreateUserForm;
import com.kp.cms.handlers.inventory.InventoryLocationHandler;
import com.kp.cms.handlers.usermanagement.CreateUserHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;

public class CreateUserAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CreateUserAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCreateUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initCreateUser input");
		CreateUserForm createUserForm = (CreateUserForm) form;
		createUserForm.resetFields();
		setRequiredDatatoForm(createUserForm);
		ActionMessages errors=new ActionMessages();
		
		/*String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }*/
		   
		
		/*if(!CMSConstants.IPADDRESSLIST.contains(ipAddress)){
		  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","UnAuthorized Access."));
				saveErrors(request, errors);
				
			return mapping.findForward("logout");
		}else{*/
			/**
			 *  OTP generation Removed
			HttpSession session = request.getSession(false);
			boolean isSent=CreateUserHandler.getInstance().initOtpGeneration(session,"UserInfo");
			if(isSent)
			{
				log.info("Exit initCreateUser input");
				return mapping.findForward(CMSConstants.INIT_ENTER_OTP);
			}
			else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Please provide mobile or emailId."));
				saveErrors(request, errors);
				log.info("Exit initCreateUser input");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			 **/
			return mapping.findForward(CMSConstants.INIT_CREATE_USER);
		//}

	}

	/**
	 * @param createUserForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(CreateUserForm createUserForm) throws Exception {
		Map<Integer, String> roles = UserInfoHandler.getInstance().getRoles();
		createUserForm.setRoles(roles);
		Map<Integer, String> employeeMap = InventoryLocationHandler.getInstance().getEmployee();
		createUserForm.setEmployeeMap(employeeMap);
		Map<Integer, String> departmentMap = UserInfoHandler.getInstance().getDepartmentMap();
		createUserForm.setDepartmentMap(departmentMap);
		Map<Integer, String> guestMap = UserInfoHandler.getInstance().getGuestMap();
		createUserForm.setGuestMap(guestMap);
		if(createUserForm.getStaffType()==null)
		createUserForm.setStaffType("employee");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered CreateUserAction - addUserInfo");
		
		CreateUserForm createUserForm = (CreateUserForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = createUserForm.validate(mapping, request);
		setUserId(request, createUserForm);
		validateEmpDept(createUserForm,errors);
		if (errors.isEmpty()) {
			try {
				String msg=CreateUserHandler.getInstance().checkUserInfo(createUserForm.getUserName(),createUserForm.getId(),createUserForm.getEmployeeId(),createUserForm.getGuestId());
				if(msg.isEmpty()){
					boolean isAdded=CreateUserHandler.getInstance().addUserInfo(createUserForm);
					if(isAdded){
						ActionMessage message = new ActionMessage(CMSConstants.USER_INFO_ADDSUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
						createUserForm.resetFields();
					}else{
						ActionError message = new ActionError("kknowledgepro.admin.addfailure"," User Info");
						errors.add("messages", message);
						saveErrors(request, errors);
					}
				}else{
					errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",msg));
					saveErrors(request, errors);
				}
			}catch(DuplicateException e1){
				errors.add("error", new ActionError(CMSConstants.USER_INFO_USEREXIST));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_CREATE_USER);
			}catch (BusinessException businessException) {
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				createUserForm.setErrorMessage(msg);
				createUserForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit Interview Batch Result - addUserInfo errors not empty ");
		}
		setRequiredDatatoForm(createUserForm);			
		log.info("Entered CreateUserAction - addUserInfo");
		return mapping.findForward(CMSConstants.INIT_CREATE_USER);
	}
	
	
	private void validateEmpDept(CreateUserForm createUserForm,ActionErrors errors) {
		log.info("enter validateEditPhone..");
		if (errors == null)
			errors = new ActionErrors();
		if(createUserForm.getStaffType().equalsIgnoreCase("employee"))
		{
				
		if ((createUserForm.getEmployeeId()== null || createUserForm.getEmployeeId().isEmpty()))  
		{
			if (errors.get(CMSConstants.EMPLOYEE_REQUIRED) != null
					&& !errors.get(CMSConstants.EMPLOYEE_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_REQUIRED);
				errors.add(CMSConstants.EMPLOYEE_REQUIRED, error);
				}
			}
		}
		
		
		
		
		if(createUserForm.getStaffType().equalsIgnoreCase("guest"))
		{
		if ((createUserForm.getGuestId()== null || createUserForm.getGuestId().isEmpty()))  
		{
			
				if (errors.get(CMSConstants.GUEST_REQUIRED) != null
					&& !errors.get(CMSConstants.GUEST_REQUIRED)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.GUEST_REQUIRED);
				errors.add(CMSConstants.GUEST_REQUIRED, error);
				}
			}
		}
		if(createUserForm.getStaffType().equalsIgnoreCase("others"))
		{
		if (createUserForm.getDepartmentId()== null || createUserForm.getDepartmentId().isEmpty()) 
		{
			if (errors.get(CMSConstants.USER_DEPARTMENT_REQUIRED) != null
				&& !errors.get(CMSConstants.USER_DEPARTMENT_REQUIRED)
						.hasNext()) {
			ActionMessage error = new ActionMessage(
					CMSConstants.USER_DEPARTMENT_REQUIRED);
			errors.add(CMSConstants.USER_DEPARTMENT_REQUIRED, error);
			}
		}
		}
		  
		log.info("exit validateEditPhone..");
		}

	@SuppressWarnings("deprecation")
	public ActionForward initCreateUserAfterOtp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initCreateUser input");
		ActionMessages errors = new ActionMessages();
		HttpSession session = request.getSession(false);
		int inCount=0;
		if(session.getAttribute("inCount")!=null){
		inCount=Integer.parseInt(session.getAttribute("inCount").toString());
		}
		inCount++;
		session.setAttribute("inCount", inCount);
		
		java.util.Calendar cal1=(java.util.Calendar) session.getAttribute("generatedTime");
		
		cal1.add(cal1.MINUTE,5);
		
		Time logoutTime=new Time(cal1.getTime().getTime());
		
		Calendar cal3 = Calendar.getInstance();  
		
		Time currentTime=new Time(cal3.getTime().getTime());
		
		
		
		if (!currentTime.before(logoutTime)) {
			
			session.removeAttribute("inCount");	
			session.removeAttribute("OTP");	
			session.removeAttribute("generatedTime");
			
			return mapping.findForward("logout");	
		}
	        
		
		CreateUserForm createUserForm = (CreateUserForm) form;
		if(createUserForm.getPassword().equalsIgnoreCase(session.getAttribute("OTP").toString())){
		createUserForm.resetFields();
		session.removeAttribute("inCount");	
		session.removeAttribute("OTP");	
		session.removeAttribute("generatedTime");
		setRequiredDatatoForm(createUserForm);
		
		return mapping.findForward(CMSConstants.INIT_CREATE_USER);
		}
		else{
			
			if(inCount>=3){
				session.removeAttribute("inCount");	
				session.removeAttribute("OTP");	
				session.removeAttribute("generatedTime");
				
				return mapping.findForward("logout");
				
			}
			
			errors.add(CMSConstants.ERROR,new ActionError("knowledgePro.error.message"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ENTER_OTP);	
			//return mapping.findForward(CMSConstants.INIT_CREATE_USER);
	}
		
		
	}
	
	

		
}
