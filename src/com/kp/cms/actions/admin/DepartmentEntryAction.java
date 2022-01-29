package com.kp.cms.actions.admin;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.forms.employee.EmpResumeSubmissionForm;
import com.kp.cms.handlers.admin.DepartmentEntryHandler;
import com.kp.cms.to.admin.DepartmentEntryTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.utilities.CommonUtil;
public class DepartmentEntryAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(DepartmentEntryAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDepartmentEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		DepartmentEntryForm departmentEntryForm=(DepartmentEntryForm)form;
		setUserId(request, departmentEntryForm);
		departmentEntryForm.reset(mapping, request);
		try{
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setRequestedDateToForm(departmentEntryForm,request);
			
		}catch (Exception e) {
			log.error("error in initAdmittedThrough...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				departmentEntryForm.setErrorMessage(msg);
				departmentEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
	}

	/**
	 * @param departmentEntryForm
	 * @param request 
	 * @throws Exception 
	 */
	private void setRequestedDateToForm(DepartmentEntryForm departmentEntryForm, HttpServletRequest request) throws Exception {
		List<DepartmentEntryTO> department= DepartmentEntryHandler.getInstance().getDepartmentFields();
		departmentEntryForm.setDepartmentList(department);
		Map<Integer, String> mapList =DepartmentEntryHandler.getInstance().getEmpStream();
		HttpSession session = request.getSession();
		session.setAttribute("empStreamMap", mapList);
		//departmentEntryForm.setEmpStreamList(mapList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addDepartmentEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentEntryForm departmentEntryForm = (DepartmentEntryForm)form;
		setUserId(request, departmentEntryForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=departmentEntryForm.validate(mapping, request);
		validateEmail(departmentEntryForm,errors);
		boolean isAdded = false;
		try{
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setRequestedDateToForm(departmentEntryForm,request);
				//if space is entered then no need to save
				if(departmentEntryForm.getName().trim().isEmpty()){
					departmentEntryForm.setName(null);
				}
				return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
			}
			/*boolean isSpecial=nameValidate(departmentEntryForm.getName().trim()); //validation checking for special characters
			if(isSpecial)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}*/
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setRequestedDateToForm(departmentEntryForm,request);
				//if space is entered then no need to save
				if(departmentEntryForm.getName().trim().isEmpty()){
					departmentEntryForm.setName(null);
				}
				return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
			}
			isAdded=DepartmentEntryHandler.getInstance().addDepartmentEntry(departmentEntryForm,"Add");
			setRequestedDateToForm(departmentEntryForm,request);
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Department.nameOrCode.exists"));
			saveErrors(request, errors);
			setRequestedDateToForm(departmentEntryForm,request);
			return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Department.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setRequestedDateToForm(departmentEntryForm,request);
			return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of department page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				departmentEntryForm.setErrorMessage(msg);
				departmentEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		
		if(isAdded){
			ActionMessage message = new ActionError("knowledgepro.admin.Department.addsuccess",departmentEntryForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			departmentEntryForm.reset(mapping, request);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.Department.addfailure",departmentEntryForm.getName()));
			saveErrors(request, errors);
		}
		
		return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
	}
/*
	*//**
	 * validation for special characters
	 * @param name
	 * @return
	 *//*
	private boolean nameValidate(String name)
	{
		boolean result=false;
		Pattern p = Pattern.compile("[^A-Za-z0-9 \t]+");
        Matcher m = p.matcher(name);
        result = m.find();
        return result;

	}
	*/
	
	public ActionForward editDepartmentEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		DepartmentEntryForm departmentEntryForm = (DepartmentEntryForm)form;
		setUserId(request, departmentEntryForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		validateEmail(departmentEntryForm,errors);
		try {
				DepartmentEntryHandler.getInstance().editDepartmentEntry(departmentEntryForm);
				request.setAttribute("department", "edit");
			
		} catch (BusinessException businessException) {
			log.info("Exception editMenus");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			departmentEntryForm.setErrorMessage(msg);
			departmentEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//load the details for module drop down and menus list.
		setRequestedDateToForm(departmentEntryForm,request);
		return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
		}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateDepartmentEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentEntryForm departmentEntryForm = (DepartmentEntryForm)form;
		setUserId(request, departmentEntryForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=departmentEntryForm.validate(mapping, request);
		validateEmail(departmentEntryForm,errors);
		boolean isUpdate = false;
		try{
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setRequestedDateToForm(departmentEntryForm,request);
				//if space is entered then no need to save
				if(departmentEntryForm.getName().trim().isEmpty()){
					departmentEntryForm.setName(null);
				}
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
			}
			//boolean isSpecial=nameValidate(departmentEntryForm.getName().trim()); //validation checking for special characters
			//if(isSpecial)
			//{
			//	errors.add("error", new ActionError("knowledgepro.admin.special"));
			//}
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setRequestedDateToForm(departmentEntryForm,request);
				//if space is entered then no need to save
				if(departmentEntryForm.getName().trim().isEmpty()){
					departmentEntryForm.setName(null);
				}
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
			}
			isUpdate=DepartmentEntryHandler.getInstance().addDepartmentEntry(departmentEntryForm,"Edit");
			setRequestedDateToForm(departmentEntryForm,request);
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Department.nameOrCode.exists"));
			saveErrors(request, errors);
			setRequestedDateToForm(departmentEntryForm,request);
			request.setAttribute("department", "edit");
			return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.Department.addfailure.alreadyexist.reactivate"));
			saveErrors(request, errors);
			setRequestedDateToForm(departmentEntryForm,request);
			request.setAttribute("department", "edit");
			return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
		} catch (Exception e) {
			log.error("error in update admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				departmentEntryForm.setErrorMessage(msg);
				departmentEntryForm.setErrorStack(e.getMessage());
				request.setAttribute("department", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(isUpdate){
			ActionMessage actionMessage = new ActionMessage("knowledgepro.admin.Department.updatesuccess",departmentEntryForm.getName());
			messages.add("messages", actionMessage);
			saveMessages(request, messages);
			departmentEntryForm.reset(mapping, request);
		}else{
			errors.add("error", new ActionError("knowledgepro.admin.Department.updatefailure",departmentEntryForm.getName()));
			saveErrors(request, errors);
		}
		request.setAttribute("department", "add");
		return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteDepartmentEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentEntryForm departmentEntryForm = (DepartmentEntryForm)form;
		setUserId(request, departmentEntryForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isDeleted=false;
		try{
			if(departmentEntryForm.getId()!=0){
				int departmentId=departmentEntryForm.getId();
				isDeleted=DepartmentEntryHandler.getInstance().deleteDepartmentEntry(departmentId,false,departmentEntryForm);
			}
		}catch (Exception e) {
			log.error("error in delete Department page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				departmentEntryForm.setErrorMessage(msg);
				departmentEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setRequestedDateToForm(departmentEntryForm,request);
		if(isDeleted){
			ActionMessage message = new ActionMessage("knowledgepro.admin.Department.deletesuccess",departmentEntryForm.getName());
			messages.add("messages",message);
			saveMessages(request, messages);
			departmentEntryForm.reset(mapping, request);
		}else{
			errors.add("error",new ActionError("knowledgepro.admin.Department.deletefailure",departmentEntryForm.getName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateDepartmentEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DepartmentEntryForm departmentEntryForm = (DepartmentEntryForm)form;
		setUserId(request, departmentEntryForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		validateEmail(departmentEntryForm,errors);
		boolean isActivate = false;
		try{
			if(departmentEntryForm.getDupId()!=0){
				int departmentId = departmentEntryForm.getDupId();
				isActivate=DepartmentEntryHandler.getInstance().deleteDepartmentEntry(departmentId, true, departmentEntryForm);
			}
		}catch (Exception e) {
			log.error("error in delete Department page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				departmentEntryForm.setErrorMessage(msg);
				departmentEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setRequestedDateToForm(departmentEntryForm,request);
		if(isActivate){
			ActionMessage message = new ActionMessage("knowledgepro.admin.Department.activate");
			messages.add("messages",message);
			saveMessages(request, messages);
		}
		else{
			errors.add("error", new ActionError("Knowledgepro.admin.Department.activate.failure"));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.DEPARTMENT_ENTRY);
	}
	@SuppressWarnings("deprecation")
	private void validateEmail(DepartmentEntryForm departmentEntryForm,ActionErrors errors) {
		if(departmentEntryForm.getEmail()!= null && !StringUtils.isEmpty(departmentEntryForm.getEmail()) && !CommonUtil.validateEmailID(departmentEntryForm.getEmail())){
            if (errors.get(CMSConstants.EMPLOYEE_VALID_EMAIL) != null && !errors.get(CMSConstants.EMPLOYEE_VALID_EMAIL).hasNext()) {
                errors.add(CMSConstants.EMPLOYEE_VALID_EMAIL,new ActionError(CMSConstants.EMPLOYEE_VALID_EMAIL));
            }
        }
		
	}
}
