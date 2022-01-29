package com.kp.cms.actions.admin;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ProgramTypeForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.to.admin.ProgramTypeTO;

/**
 * DispatchAction to manage Add, Edit, Delete actions for Program Type.
 * 
 * @author 
 */
@SuppressWarnings("deprecation")

public class ProgramTypeAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(ProgramTypeAction.class);
	/**
	 * Performs the get Program Type action.
	 * 
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	public ActionForward getProgramType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("inside getProgramType");
		ProgramTypeForm programTypeForm = (ProgramTypeForm) form;
		try {
			setProgramTypeListToRequest(request);
			setUserId(request, programTypeForm);
		} catch (Exception e) {
			log.error("error in getProgramType...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programTypeForm.setErrorMessage(msg);
				programTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
	}

	/**
	 * Performs the add Program Type action.
	 * 
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	public ActionForward addProgramType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.debug("inside addProgramType Action");
		ProgramTypeForm programTypeForm = (ProgramTypeForm) form;
		programTypeForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = programTypeForm.validate(mapping, request);
		
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
				//space should not get added in the table
				if(programTypeForm.getProgramTypeName().trim().isEmpty()){
					programTypeForm.setProgramTypeName(null);
				}
					
				return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
			}
			boolean isSpcl=nameValidate(programTypeForm.getProgramTypeName().trim());
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
				return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
			}
			
			
			if(programTypeForm.getAgeFrom()!= null && !programTypeForm.getAgeFrom().trim().isEmpty()){
				if(programTypeForm.getAgeTo() == null || programTypeForm.getAgeTo().trim().isEmpty() || Integer.parseInt(programTypeForm.getAgeTo()) <= 0){
					errors.add("knowledgepro.program.type.age.to.mandatory",new ActionError("knowledgepro.program.type.age.to.mandatory"));					
					saveErrors(request, errors);
					setProgramTypeListToRequest(request);
					return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);					
				}
					
			}
			if(programTypeForm.getAgeFrom() == null || programTypeForm.getAgeFrom().trim().isEmpty() || Integer.parseInt(programTypeForm.getAgeFrom()) <= 0 ){
				if(programTypeForm.getAgeTo() != null && !programTypeForm.getAgeTo().trim().isEmpty()){
					errors.add("knowledgepro.program.type.age.from.mandatory",new ActionError("knowledgepro.program.type.age.from.mandatory"));					
					saveErrors(request, errors);
					setProgramTypeListToRequest(request);
					return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);					
				}
					
			}

			
			if(programTypeForm.getAgeFrom()!= null && !programTypeForm.getAgeFrom().trim().isEmpty() 
					&& programTypeForm.getAgeTo()!= null && !programTypeForm.getAgeTo().trim().isEmpty()){
				if(Integer.parseInt(programTypeForm.getAgeFrom()) > Integer.parseInt(programTypeForm.getAgeTo())){
					errors.add("knowledgepro.program.type.age.from.to.valid",new ActionError("knowledgepro.program.type.age.from.to.valid"));
					saveErrors(request, errors);
					setProgramTypeListToRequest(request);
					return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);					
				}
			}
			isAdded = ProgramTypeHandler.getInstance().addProgramType(programTypeForm, "add");
			setProgramTypeListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.programtype.name.exists", programTypeForm.getProgramTypeName()));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.PROGRAM_TYPE_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of program type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programTypeForm.setErrorMessage(msg);
				programTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.addsuccess", programTypeForm.getProgramTypeName());
			messages.add("messages", message);
			saveMessages(request, messages);
			programTypeForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.programtype.name.addfail", programTypeForm.getProgramTypeName()));
			saveErrors(request, errors);
		}
		log.debug("Leaving addProgramType Action");
		return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
	
	}

	/**
	 * Performs the edit Program Type action.
	 * 
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	public ActionForward editProgramType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.debug("inside editProgramType Action");
		ProgramTypeForm programTypeForm = (ProgramTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = programTypeForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
				//space should not get added in the table
				if(programTypeForm.getProgramTypeName().trim().isEmpty()){
					programTypeForm.setProgramTypeName(null);
				}
					
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
			}
			
			boolean isSpcl=nameValidate(programTypeForm.getProgramTypeName().trim());
			if(isSpcl)
			{
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setProgramTypeListToRequest(request);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
			}
			
			if(programTypeForm.getAgeFrom()!= null && !programTypeForm.getAgeFrom().trim().isEmpty()){
				if(programTypeForm.getAgeTo() == null || programTypeForm.getAgeTo().trim().isEmpty() || Integer.parseInt(programTypeForm.getAgeTo()) <= 0){
					errors.add("knowledgepro.program.type.age.to.mandatory",new ActionError("knowledgepro.program.type.age.to.mandatory"));					
					saveErrors(request, errors);
					setProgramTypeListToRequest(request);
					request.setAttribute("operation", "edit");
					return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);					
				}
					
			}
			if(programTypeForm.getAgeFrom() == null || programTypeForm.getAgeFrom().trim().isEmpty() || Integer.parseInt(programTypeForm.getAgeFrom()) <= 0){
				if(programTypeForm.getAgeTo() != null && !programTypeForm.getAgeTo().trim().isEmpty()){
					errors.add("knowledgepro.program.type.age.from.mandatory",new ActionError("knowledgepro.program.type.age.from.mandatory"));					
					saveErrors(request, errors);
					setProgramTypeListToRequest(request);
					request.setAttribute("operation", "edit");
					return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);					
				}
					
			}
			if(programTypeForm.getAgeFrom()!= null && !programTypeForm.getAgeFrom().trim().isEmpty() 
					&& programTypeForm.getAgeTo()!= null && !programTypeForm.getAgeTo().trim().isEmpty()){
				if(Integer.parseInt(programTypeForm.getAgeFrom()) > Integer.parseInt(programTypeForm.getAgeTo())){
					errors.add("knowledgepro.program.type.age.from.to.valid",new ActionError("knowledgepro.program.type.age.from.to.valid"));
					saveErrors(request, errors);
					setProgramTypeListToRequest(request);
					request.setAttribute("operation", "edit");
					return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);					
				}
			}
			
			isUpdated = ProgramTypeHandler.getInstance().addProgramType(programTypeForm, "edit");
			setProgramTypeListToRequest(request);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.programtype.name.exists", programTypeForm.getProgramTypeName()));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.PROGRAM_TYPE_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setProgramTypeListToRequest(request);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of program type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programTypeForm.setErrorMessage(msg);
				programTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.updatesuccess", programTypeForm.getProgramTypeName());
			messages.add("messages", message);
			saveMessages(request, messages);
			programTypeForm.reset(mapping, request);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.admin.programtype.name.updatefail", programTypeForm.getProgramTypeName()));
			saveErrors(request, errors);
		}
		request.setAttribute("operation", "add");
		log.debug("Leaving editProgramType Action");
		return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
	}

	/**
	 * Performs the delete Program Type action.
	 * 
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	public ActionForward deleteProgramType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProgramTypeForm programTypeForm = (ProgramTypeForm) form;

		int programTypeId = Integer.parseInt(programTypeForm.getProgramTypeId());
		String programTypeName=programTypeForm.getProgramTypeName();
		boolean isProgramTypeDeleted = ProgramTypeHandler.getInstance().deleteProgramType(programTypeId,programTypeForm, false);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
		setProgramTypeListToRequest(request);
		if (isProgramTypeDeleted) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.deletesuccess",	programTypeName);// Adding added message.
			messages.add("messages", message);
			saveMessages(request, messages);
			request.setAttribute("Update", "Update");
			programTypeForm.reset(mapping, request);
		} else {
			errors.add("error", new ActionError("knowledgepro.admin.programtype.name.deletefail", programTypeName));// Adding failure message
			saveErrors(request, errors);
		}
		}catch (Exception e) {
			log.error("error in final submit of Program type page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				programTypeForm.setErrorMessage(msg);
				programTypeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
	}
	/**
	 * 
	 * @param request
	 *            This method sets the programTypeList to Request used to display
	 *            
	 * @throws Exception
	 */
	public void setProgramTypeListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the remark type
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateProgramType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateProgramType");
		ProgramTypeForm programTypeForm = (ProgramTypeForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (programTypeForm.getDuplId() != 0) {
				int id = programTypeForm.getDuplId();  //setting id for activate
				isActivated = ProgramTypeHandler.getInstance().deleteProgramType(id,programTypeForm, true);
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.PROGRAM_TYPE_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setProgramTypeListToRequest(request);
		if (isActivated) {
			ActionMessage message = new ActionMessage(CMSConstants.PROGRAM_TYPE_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateProgramType");
		return mapping.findForward(CMSConstants.PROGRAM_TYPE_ENTRY);
	}

	/**
	 * special character validation
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name)
	{
		boolean result=false;
//		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \\. \\s \t]+");
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \\. \\- \\s \t]+");
        Matcher matcher = pattern.matcher(name);
        result = matcher.find();
        return result;

	}
	
}