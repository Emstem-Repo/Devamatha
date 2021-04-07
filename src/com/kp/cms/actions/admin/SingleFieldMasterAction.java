package com.kp.cms.actions.admin;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.admin.SingleFieldMasterForm;
import com.kp.cms.handlers.admin.SingleFieldMasterHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;

@SuppressWarnings("deprecation")
public class SingleFieldMasterAction extends BaseDispatchAction {

	private static final Log log = LogFactory
			.getLog(SingleFieldMasterAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return set singleMasterList having SingleFieldMasterTO objects to
	 *         request, forward to singleFieldMasterEntry
	 * @throws Exception
	 */
	public ActionForward initSingleFieldMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		final String boName = (String) request.getParameter("operation");
		if(boName.equalsIgnoreCase("Roles")){
			String ipAddress = request.getHeader("X-FORWARDED-FOR");  
			   if (ipAddress == null) {  
				   ipAddress = request.getRemoteAddr();  
			   }
			   
			//IPADDRESS CHECK
			if(!CMSConstants.IPADDRESSLIST.contains(ipAddress)){
				
				//return mapping.findForward("logout");
			}
		}
		SingleFieldMasterForm singleFieldMasterForm = (SingleFieldMasterForm) form;
		HttpSession session=request.getSession(false);
		// field use for help.Don't Remove
		String field=singleFieldMasterForm.getDisplayName().trim();
		session.setAttribute("field",field);
		try {

			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setSingleFieldMastertoRequest(request, boName); // setting single
			// master list to
			// request for UI
			// display

		} catch (Exception e) {
			log.error("error submit " + boName + " page...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				singleFieldMasterForm.setErrorMessage(msg);
				singleFieldMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		singleFieldMasterForm.setOperation(boName);
		if(session.getAttribute("newEntryName")!=null)
		session.removeAttribute("newEntryName");
		if(request.getParameter("mainPageExists")==null){
			singleFieldMasterForm.setMainPage(null);
			singleFieldMasterForm.setSuperMainPage(null);
			singleFieldMasterForm.setMainPageExists(null);
		}
		else{
			if(singleFieldMasterForm.getMainPage()!=null && !singleFieldMasterForm.getMainPage().isEmpty())
			singleFieldMasterForm.setMainPageExists(singleFieldMasterForm.getMainPage());
			else singleFieldMasterForm.setMainPageExists(singleFieldMasterForm.getSuperMainPage());
		}
		log.debug("Leaving init" + boName + " ");
		if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
			return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
		}else{
			return mapping.findForward("singleFieldMasterEntry");
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will add new record according to the table definition
	 * @return to mapping singleFieldMasterEntry
	 * @throws Exception
	 */
	public ActionForward addSingleFieldMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SingleFieldMasterForm singleFieldMasterForm = (SingleFieldMasterForm) form;
		String name = singleFieldMasterForm.getName();
		String boName = singleFieldMasterForm.getOperation();
		String orderreqmessage =null;
		if(boName.equalsIgnoreCase("ExamSubjectTypeMaster")){
			String order = singleFieldMasterForm.getOrder();
			if(order==null || StringUtils.isEmpty(order.trim())){
			 orderreqmessage = "knowledgepro.admin.order.required";
			}
		}
		String existmessage = "knowledgepro.admin." + boName + ".name.exists";
		String activateMessage = "knowledgepro.admin." + boName
				+ ".addfailure.alreadyexist.reactivate";
		String successMessage = "knowledgepro.admin." + boName + ".addsuccess";
		String failureMessage = "knowledgepro.admin." + boName + ".addfailure";
		String reqmessage = "knowledgepro.admin." + boName + ".required";
		String specialmessage = "knowledgepro.admin.special";
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		if (singleFieldMasterForm.getName() == null
				|| StringUtils.isEmpty(singleFieldMasterForm.getName().trim())) {
			errors.add("error", new ActionError(reqmessage));
		}
		else if(boName.equalsIgnoreCase("EmpAllowance") || boName.equalsIgnoreCase("Stream")){
			if(isNumeric(singleFieldMasterForm.getName())){
				errors.add("error", new ActionError("knowledgepor.employee.allowance.numeric"));
			}
		}
		if((boName.equalsIgnoreCase("ExamSubjectTypeMaster")) && (singleFieldMasterForm.getOrder()==null || StringUtils.isEmpty(singleFieldMasterForm.getOrder().trim()))){
			errors.add("error", new ActionError(orderreqmessage));
		}
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setSingleFieldMastertoRequest(request, boName);
				if (singleFieldMasterForm.getName().trim().isEmpty()) {
					singleFieldMasterForm.setName(null);
				}
				if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
					return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
				}else{
					return mapping.findForward("singleFieldMasterEntry");
				}
			}

			boolean isSpcl = nameValidate(singleFieldMasterForm.getName()
					.trim());
			if (boName.equalsIgnoreCase("Income")
					|| boName.equalsIgnoreCase("Prerequisite")) {
				if (singleFieldMasterForm.getName().trim()
						.equalsIgnoreCase("'")
						|| singleFieldMasterForm.getName().trim()
								.equalsIgnoreCase("''")) {
					isSpcl = true;
				} else {
					isSpcl = false;
				}
			}
			
			if (boName.equalsIgnoreCase("_Exam_Assignment_Type_Master")
					|| boName.equalsIgnoreCase("_Exam_Internal_Exam_Type")) {
				isSpcl = splCharValidation(name, "\\-\\_");

			}

			if (boName.equalsIgnoreCase("_Exam_CourseGroupCode")) {
				isSpcl = splCharValidation(name, "\\-\\_");

			}

			if (boName.equalsIgnoreCase("_Exam_Invigilation_Duty")
					|| boName.equalsIgnoreCase("_Exam_Major_Depatment_Code")
					|| boName
							.equalsIgnoreCase("_Exam_Multiple_AnswerScript_Master")
					|| boName.equalsIgnoreCase("_Exam_Revaluation_Type")
					|| boName.equalsIgnoreCase("_Exam_Second_Language_Master")
					|| boName
							.equalsIgnoreCase("_Exam_EligibilityCriteriaMaster")
					|| boName.equalsIgnoreCase("_Exam_SubjectTypeMaster")) {
				isSpcl = splCharValidation(name, "\\-\\_\\&");

			}
			if (isSpcl) {
				errors.add("error", new ActionError(specialmessage));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setSingleFieldMastertoRequest(request, boName);
				if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
					return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
				}else{
					return mapping.findForward("singleFieldMasterEntry");
				}
			}

			setUserId(request, singleFieldMasterForm); // setting user is for
			// updating last changed
			// details
			isAdded = SingleFieldMasterHandler.getInstance()
					.addSingleFieldMaster(singleFieldMasterForm, "Add", boName);
			setSingleFieldMastertoRequest(request, boName);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(existmessage, name));
			saveErrors(request, errors);
			setSingleFieldMastertoRequest(request, boName);
			singleFieldMasterForm.setOperation(boName);
			if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
				return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
			}else{
				return mapping.findForward("singleFieldMasterEntry");
			}
		} catch (ReActivateException e1) {
			if (boName.equalsIgnoreCase("EmpAgeofRetirement")) {
				errors.add("error", new ActionError(
						"knowledgepro.admin.EmpAgeofRetirement.name.exists",
						name));
			} else {
				errors.add("error", new ActionError(activateMessage, name));
			}
			saveErrors(request, errors);
			setSingleFieldMastertoRequest(request, boName);
			singleFieldMasterForm.setOperation(boName);
			if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
				return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
			}else{
				return mapping.findForward("singleFieldMasterEntry");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error in final submit of " + boName + " page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				singleFieldMasterForm.setErrorMessage(msg);
				singleFieldMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			if((singleFieldMasterForm.getSuperMainPage()!=null && !singleFieldMasterForm.getSuperMainPage().isEmpty()) || (singleFieldMasterForm.getMainPage()!=null && !singleFieldMasterForm.getMainPage().isEmpty())){
				HttpSession session=request.getSession(false);
				session.setAttribute("newEntryName",singleFieldMasterForm.getName());
			}
			ActionMessage message = new ActionMessage(successMessage, name);
			messages.add("messages", message);
			saveMessages(request, messages);
			singleFieldMasterForm.setMainPage(null);
			singleFieldMasterForm.setSuperMainPage(null);
			if(boName.equalsIgnoreCase("ExamSubjectTypeMaster")){
				singleFieldMasterForm.setOrder(null);
			}
			
			singleFieldMasterForm.reset(mapping, request);
		} else {
			// failed
			errors.add("error", new ActionError(failureMessage, name));
			saveErrors(request, errors);
		}
		log.debug("Leaving " + boName + " Action");
		singleFieldMasterForm.setOperation(boName);
		if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
			return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
		}else{
			return mapping.findForward("singleFieldMasterEntry");
		}

	}
	public ActionForward editSingleFieldMasterEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		SingleFieldMasterForm singleFieldMasterForm = (SingleFieldMasterForm) form;
		assignDataToForm(request, singleFieldMasterForm);  // assigning data to form in edit option
		return mapping.findForward("singleFieldMasterEntry");
   }
	
	public void assignDataToForm(HttpServletRequest request, SingleFieldMasterForm singleFieldMasterForm) throws Exception {
		
		String boName = singleFieldMasterForm.getOperation();
		setSingleFieldMastertoRequest(request, boName);
		singleFieldMasterForm.setName(singleFieldMasterForm.getName());
		singleFieldMasterForm.setOrder(singleFieldMasterForm.getOrder());
		request.setAttribute("admOperation", "edit");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This will update the existing single field tables
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSingleFieldMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SingleFieldMasterForm singleFieldMasterForm = (SingleFieldMasterForm) form;
		ActionErrors errors = new ActionErrors();
		String boName = singleFieldMasterForm.getOperation();
		String name = singleFieldMasterForm.getName();
		String existmessage = "knowledgepro.admin." + boName + ".name.exists";
		String activateMessage = "knowledgepro.admin." + boName
				+ ".addfailure.alreadyexist.reactivate";
		String successMessage = "knowledgepro.admin." + boName
				+ ".updatesuccess";
		String failureMessage = "knowledgepro.admin." + boName
				+ ".updatefailure";
		String specialmessage = "knowledgepro.admin.special";
		String orderreqmessage =null;
		if(boName.equalsIgnoreCase("ExamSubjectTypeMaster")){
			String order = singleFieldMasterForm.getOrder();
			if(order==null || StringUtils.isEmpty(order.trim())){
			 orderreqmessage = "knowledgepro.admin.order.required";
			}
		}
		ActionMessages messages = new ActionMessages();
		String reqmessage = "knowledgepro.admin." + boName + ".required";
		if ((singleFieldMasterForm.getName() == null)
				|| (singleFieldMasterForm.getName().trim().equalsIgnoreCase(""))) {
			errors.add("error", new ActionError(reqmessage));
		}
		else if(boName.equalsIgnoreCase("EmpAllowance") || boName.equalsIgnoreCase("Stream")){
			if(isNumeric(singleFieldMasterForm.getName())){
				errors.add("error", new ActionError("knowledgepor.employee.allowance.numeric"));
			}
		}
		if((boName.equalsIgnoreCase("ExamSubjectTypeMaster")) && (singleFieldMasterForm.getOrder()==null || StringUtils.isEmpty(singleFieldMasterForm.getOrder().trim()))){
			errors.add("error", new ActionError(orderreqmessage));
		}
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setSingleFieldMastertoRequest(request, boName);
				if (singleFieldMasterForm.getName().trim().isEmpty()) {
					singleFieldMasterForm.setName(null);
				}
				request.setAttribute("admOperation", "edit");
				if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
					return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
				}else{
					return mapping.findForward("singleFieldMasterEntry");
				}
			}
			boolean isSpcl = nameValidate(singleFieldMasterForm.getName()
					.trim());
			if (boName.equalsIgnoreCase("Income")
					|| boName.equalsIgnoreCase("Prerequisite")) {
				if (singleFieldMasterForm.getName().trim()
						.equalsIgnoreCase("'")
						|| singleFieldMasterForm.getName().trim()
								.equalsIgnoreCase("''")) {
					isSpcl = true;
				} else {
					isSpcl = false;
				}
			}
			if (boName.equalsIgnoreCase("_Exam_Assignment_Type_Master")
					|| boName.equalsIgnoreCase("_Exam_Internal_Exam_Type")) {
				isSpcl = splCharValidation(name, "\\-\\_");

			}

			if (boName.equalsIgnoreCase("_Exam_CourseGroupCode")) {
				isSpcl = false;

			}
			if (boName.equalsIgnoreCase("_Exam_Invigilation_Duty")
					|| boName.equalsIgnoreCase("_Exam_Major_Depatment_Code")
					|| boName
							.equalsIgnoreCase("_Exam_Multiple_AnswerScript_Master")
					|| boName.equalsIgnoreCase("_Exam_Revaluation_Type")
					|| boName.equalsIgnoreCase("_Exam_Second_Language_Master")
					|| boName
							.equalsIgnoreCase("_Exam_EligibilityCriteriaMaster")
					|| boName.equalsIgnoreCase("_Exam_SubjectTypeMaster")) {
				isSpcl = splCharValidation(name, "\\-\\_\\&");

			}
			if (isSpcl) {
				errors.add("error", new ActionError(specialmessage));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setSingleFieldMastertoRequest(request, boName);
				request.setAttribute("admOperation", "edit");
				if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
					return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
				}else{
					return mapping.findForward("singleFieldMasterEntry");
				}
			}
			setUserId(request, singleFieldMasterForm);
			isUpdated = SingleFieldMasterHandler
					.getInstance()
					.addSingleFieldMaster(singleFieldMasterForm, "Edit", boName);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError(existmessage,
					singleFieldMasterForm.getName()));
			saveErrors(request, errors);
			setSingleFieldMastertoRequest(request, boName);
			request.setAttribute("admOperation", "edit");
			singleFieldMasterForm.setOperation(boName);
			if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
				return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
			}else{
				return mapping.findForward("singleFieldMasterEntry");
			}
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(activateMessage,
					singleFieldMasterForm.getName()));
			saveErrors(request, errors);
			setSingleFieldMastertoRequest(request, boName);
			request.setAttribute("admOperation", "edit");
			singleFieldMasterForm.setOperation(boName);
			if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
				return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
			}else{
				return mapping.findForward("singleFieldMasterEntry");
			}
		} catch (Exception e) {
			log.error("error in update " + boName + " page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("admOperation", "edit");
				singleFieldMasterForm.setOperation(boName);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				singleFieldMasterForm.setErrorMessage(msg);
				singleFieldMasterForm.setErrorStack(e.getMessage());
				request.setAttribute("admOperation", "edit");
				singleFieldMasterForm.setOperation(boName);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setSingleFieldMastertoRequest(request, boName);
		if (isUpdated) {
			// success .
			if((singleFieldMasterForm.getSuperMainPage()!=null && !singleFieldMasterForm.getSuperMainPage().isEmpty()) || (singleFieldMasterForm.getMainPage()!=null && !singleFieldMasterForm.getMainPage().isEmpty())){
				HttpSession session=request.getSession(false);
				session.setAttribute("newEntryName",singleFieldMasterForm.getName());
			}
			ActionMessage message = new ActionMessage(successMessage,
					singleFieldMasterForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			singleFieldMasterForm.reset(mapping, request);
			if(boName.equalsIgnoreCase("ExamSubjectTypeMaster")){
				singleFieldMasterForm.setOrder(null);
			}
		} else {
			// failed
			errors.add("error", new ActionError(failureMessage,
					singleFieldMasterForm.getName()));
			saveErrors(request, errors);

		}

		singleFieldMasterForm.setOperation(boName);
		if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
			return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
		}else{
			return mapping.findForward("singleFieldMasterEntry");
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            this will delete the existing single master record
	 * @return ActionForward This action method will called when particular
	 *         record need to be deleted based on the id id.
	 * @throws Exception
	 */
	public ActionForward deleteSingleFieldMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SingleFieldMasterForm singleFieldMasterForm = (SingleFieldMasterForm) form;
		String boName = singleFieldMasterForm.getOperation();
		String successMessage = "knowledgepro.admin." + boName
				+ ".deletesuccess";
		String failureMessage = "knowledgepro.admin." + boName
				+ ".deletefailure";
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		log.debug("inside Delete " + boName + " Action");
		boolean isDeleted = false;
		try {
			if (singleFieldMasterForm.getId() != 0) {
				int masterId = singleFieldMasterForm.getId();
				setUserId(request, singleFieldMasterForm);
				isDeleted = SingleFieldMasterHandler.getInstance()
						.deleteSingleFieldMaster(masterId, false, boName,
								singleFieldMasterForm);
			}
			singleFieldMasterForm.setOperation(boName);
		} catch (Exception e) {
			log.error("error in delete " + boName + " page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				singleFieldMasterForm.setErrorMessage(msg);
				singleFieldMasterForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		setSingleFieldMastertoRequest(request, boName);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage(successMessage,
					singleFieldMasterForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			singleFieldMasterForm.reset(mapping, request);
		} else {
			// failure error message.
			errors.add("error", new ActionError(failureMessage,
					singleFieldMasterForm.getName()));
			saveErrors(request, errors);
		}
		log.debug("inside Delete " + boName + "  Action");
		singleFieldMasterForm.setOperation(boName);
		if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
			return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
		}else{
			return mapping.findForward("singleFieldMasterEntry");
		}
	}

	/**
	 * setting data to request for UI display
	 * 
	 * @param request
	 * @param boName
	 * @throws Exception
	 */
	public void setSingleFieldMastertoRequest(HttpServletRequest request,
			String boName) throws Exception {

		List<SingleFieldMasterTO> singlefieldmasterlist = SingleFieldMasterHandler
				.getInstance().getsingleFieldMaster(boName);
		request.setAttribute("singlefieldmasterlist", singlefieldmasterlist);
		if (singlefieldmasterlist != null) {
			log.debug("No of " + boName + " " + singlefieldmasterlist.size());
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            This action method Reactivate the single master record.
	 * @return returns error messages based on success / failure.
	 * @throws Exception
	 */
	public ActionForward activateSingleFieldMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SingleFieldMasterForm singleFieldMasterForm = (SingleFieldMasterForm) form;
		String boName = singleFieldMasterForm.getOperation();
		String activateMessage = "knowledgepro.admin." + boName + ".activate";
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (singleFieldMasterForm.getReactivateid() != 0) {
				int masterId = singleFieldMasterForm.getReactivateid();
				setUserId(request, singleFieldMasterForm);
				isActivated = SingleFieldMasterHandler.getInstance()
						.deleteSingleFieldMaster(masterId, true, boName,
								singleFieldMasterForm);
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(
					CMSConstants.ADMITTED_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setSingleFieldMastertoRequest(request, boName);
		if (isActivated) {
			// success .
			if((singleFieldMasterForm.getSuperMainPage()!=null && !singleFieldMasterForm.getSuperMainPage().isEmpty()) || (singleFieldMasterForm.getMainPage()!=null && !singleFieldMasterForm.getMainPage().isEmpty())){
				HttpSession session=request.getSession(false);
				String reactivatedName=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(singleFieldMasterForm.getReactivateid(), boName, true, "name");
				session.setAttribute("newEntryName",reactivatedName);
			}
			ActionMessage message = new ActionMessage(activateMessage);
			messages.add("messages", message);
			saveMessages(request, messages);
			singleFieldMasterForm.reset(mapping, request);
		}
		if(boName.equalsIgnoreCase("_Exam_Second_Language_Master")){
			return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE_MASTER);
		}else{
			return mapping.findForward("singleFieldMasterEntry");
		}
	}

	/**
	 * special character validation
	 * 
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name) {
		boolean result = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9- _ &\\. \\s \t \\/ \\( \\) ]+");

		Matcher matcher = pattern.matcher(name);
		result = matcher.find();
		return result;

	}

	private boolean splCharValidation(String name, String splChar) {
		boolean haveSplChar = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9" + splChar + "]+");
		Matcher matcher = pattern.matcher(name);
		haveSplChar = matcher.find();
		return haveSplChar;

	}
	
	public static boolean isNumeric(String number){  
		 boolean isValid = false;  
		   
		 /*Number: A numeric value will have following format: 
		         ^[-+]?: Starts with an optional "+" or "-" sign. 
		     [0-9]*: May have one or more digits. 
		#     \\.? : May contain an optional "." (decimal point) character. 
		#     [0-9]+$ : ends with numeric digit. 
		# */  
		   
		 //Initialize reg ex for numeric data.  
		 String expression = "^[-+]?[0-9]*\\.?[0-9]+$";  
		 CharSequence inputStr = number;  
		 Pattern pattern = Pattern.compile(expression);  
		 Matcher matcher = pattern.matcher(inputStr);  
		 if(matcher.matches()){  
		 isValid = true;  
		 }  
		 return isValid;  
		} 

}
