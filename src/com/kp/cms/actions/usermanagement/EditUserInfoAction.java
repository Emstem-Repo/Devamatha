package com.kp.cms.actions.usermanagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.usermanagement.EditUserInfoForm;
import com.kp.cms.handlers.inventory.InventoryLocationHandler;
import com.kp.cms.handlers.usermanagement.CreateUserHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.usermanagement.UserInfoTO;

public class EditUserInfoAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(EditUserInfoAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered editUserInfo input");
		EditUserInfoForm editUserInfoForm = (EditUserInfoForm) form;
		editUserInfoForm.resetFields();
		setRequiredDatatoForm(editUserInfoForm);
		log.info("Exit editUserInfo input");
		
		return mapping.findForward(CMSConstants.EDIT_USERS);
	}
	/**
	 * @param editUserInfoForm
	 */
	private void setRequiredDatatoForm(EditUserInfoForm editUserInfoForm) throws Exception {
		
		Map<Integer, String> roles = UserInfoHandler.getInstance().getRoles();
		editUserInfoForm.setRoles(roles);
		
		Map<Integer, String> employeeMap = InventoryLocationHandler.getInstance().getEmployee();
		editUserInfoForm.setEmployeeMap(employeeMap);
		
		Map<Integer, String> departmentMap = UserInfoHandler.getInstance().getDepartmentMap();
		editUserInfoForm.setDepartmentMap(departmentMap);
		
		Map<Integer, String> guestMap = UserInfoHandler.getInstance().getGuestMap();
		editUserInfoForm.setGuestMap(guestMap);
		
		if(editUserInfoForm.getStaffType()!=null && !editUserInfoForm.getStaffType().isEmpty())
		{
		editUserInfoForm.setStaffType("employee");
		}
	}
	
	public ActionForward searchUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		EditUserInfoForm editUserInfoForm = (EditUserInfoForm) form;
		editUserInfoForm.setUserInfoTOList(null);
		setUsersTotoForm(editUserInfoForm);
		return mapping.findForward(CMSConstants.EDIT_USERS);
	}
	/**
	 * @param editUserInfoForm
	 * @throws Exception
	 */
	private void setUsersTotoForm(EditUserInfoForm editUserInfoForm) throws Exception {
		List<UserInfoTO> userInfoTOList  = CreateUserHandler.getInstance().getUserDetails(editUserInfoForm.getSearchEmployeeId(),editUserInfoForm.getSearchRoleId(),editUserInfoForm.getSearchUserName(),editUserInfoForm.getSearchGuestId());
		editUserInfoForm.setUserInfoTOList(userInfoTOList);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {

		log.debug("inside deleteUserInfo Action");
		EditUserInfoForm editUserInfoForm = (EditUserInfoForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
		if (editUserInfoForm.getId() != 0) {
			int userId = editUserInfoForm.getId();
			isDeleted = UserInfoHandler.getInstance().deleteUserInfo(userId);
			setUsersTotoForm(editUserInfoForm);
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			editUserInfoForm.setErrorMessage(msg);
			editUserInfoForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.usermanagement.userinfo.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			editUserInfoForm.setId(0);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.usermanagement.userinfo.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("inside deleteUserInfo");
		return mapping.findForward(CMSConstants.EDIT_USERS);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return setting userinfo to form for edit
	 * @throws Exception
	 */
	public ActionForward editUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
								HttpServletResponse response) throws Exception {
		EditUserInfoForm editUserInfoForm = (EditUserInfoForm) form;
		//editUserInfoForm.resetFields();
		setRequiredDatatoForm(editUserInfoForm);
		List<UserInfoTO> userInfoTOList  = UserInfoHandler.getInstance().getUserDetailsById(editUserInfoForm.getId());
		CreateUserHandler.getInstance().setRequiredDateToForm(userInfoTOList, editUserInfoForm);
		request.setAttribute("userOperation", "update");
		return mapping.findForward(CMSConstants.UPDATE_USER);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered CreateUserAction - updateUserInfo");
		
		EditUserInfoForm editUserInfoForm = (EditUserInfoForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = editUserInfoForm.validate(mapping, request);
		setUserId(request, editUserInfoForm);
		validateEmpDept(editUserInfoForm,errors);
		if (errors.isEmpty()) {
			try {
				String msg=CreateUserHandler.getInstance().checkUserInfo(editUserInfoForm.getUserName(),editUserInfoForm.getId(),editUserInfoForm.getEmployeeId(),editUserInfoForm.getGuestId());
				if(msg.isEmpty()){
					boolean isAdded=CreateUserHandler.getInstance().updateUserInfo(editUserInfoForm);
					if(isAdded){
						ActionMessage message = new ActionMessage("knowledgepro.admission.userinfoupdated");
						messages.add("messages", message);
						saveMessages(request, messages);
						setUsersTotoForm(editUserInfoForm);
					}else{
						ActionError message = new ActionError("kknowledgepro.admin.addfailure"," User Info");
						errors.add("messages", message);
						saveErrors(request, errors);
					}
				}else{
					errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",msg));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_USER);
				}
			}catch(DuplicateException e1){
				errors.add("error", new ActionError(CMSConstants.USER_INFO_USEREXIST));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_USER);
			}catch (BusinessException businessException) {
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				editUserInfoForm.setErrorMessage(msg);
				editUserInfoForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit Interview Batch Result - addUserInfo errors not empty ");
			return mapping.findForward(CMSConstants.UPDATE_USER);
		}
		setRequiredDatatoForm(editUserInfoForm);			
		log.info("Entered CreateUserAction - updateUserInfo");
		return mapping.findForward(CMSConstants.EDIT_USERS);
	}
	
	private void validateEmpDept(EditUserInfoForm createUserForm,ActionErrors errors) {
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
}
