package com.kp.cms.actions.admin;

import java.util.Iterator;
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
import com.kp.cms.forms.admin.SMSTemplateForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.SMSTemplateTo;

public class SMSTemplateAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(TemplateAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward initSMSTemplate(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		
		SMSTemplateForm templateForm = (SMSTemplateForm)form;
		templateForm.clear();
		setUserId(request, templateForm);
		loadSMSTemplateList(templateForm);
		getProgramTypeList(templateForm);
		return mapping.findForward(CMSConstants.TEMPLATE_INITSMSTEMPLATE);
	}	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return save the email template to database.
	 * @throws Exception
	 */
	public ActionForward createSMSTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();
		SMSTemplateForm templateForm = (SMSTemplateForm)form;
		ActionErrors errors = templateForm.validate(mapping, request);
		validateGroupData(errors,templateForm);
		
		try {
			if(errors.isEmpty()) {
				if(SMSTemplateHandler.getInstance().saveSMSTemplate(templateForm)){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_ADDSUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadSMSTemplateList(templateForm);
					saveMessages(request, messages);
					templateForm.clear();
				}
			} else {
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TEMPLATE_ADDFAILED));
    		saveErrors(request,errors);
		}
		return mapping.findForward(CMSConstants.TEMPLATE_INITSMSTEMPLATE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return loads the template to be edited .
	 * @throws Exception
	 *        
	 */
	public ActionForward editSMSTemplate(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		
		SMSTemplateForm templateForm = (SMSTemplateForm)form;
		List<SMSTemplateTo> groupTemplateList = SMSTemplateHandler.getInstance().getSMSTemplateList(templateForm.getTemplateId());
		Iterator<SMSTemplateTo> iterator = groupTemplateList.iterator();
		SMSTemplateTo groupTemplateTO;

		while (iterator.hasNext()) {
			groupTemplateTO = (SMSTemplateTo) iterator.next();
			
			if(groupTemplateTO.getProgramTypeId() != null && !groupTemplateTO.getProgramTypeId().trim().isEmpty()){
				templateForm.setProgramTypeId(groupTemplateTO.getProgramTypeId());
			}
			if(groupTemplateTO.getProgramId() != null && !groupTemplateTO.getProgramId().trim().isEmpty()){
				templateForm.setProgramId(groupTemplateTO.getProgramId());
			}
			if(groupTemplateTO.getCourseId() != null && !groupTemplateTO.getCourseId().trim().isEmpty()){
				templateForm.setCourseId(groupTemplateTO.getCourseId());
			}
			templateForm.setTemplateName(groupTemplateTO.getTemplateName());
			templateForm.setTemplateDescription(groupTemplateTO.getTemplateDescription());
		}
		setProgramMapToRequest(request, templateForm);
		setCourseMapToRequest(request, templateForm);
		request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.TEMPLATE_INITSMSTEMPLATE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return this will update the template to database.
	 * @throws Exception
	 */
	public ActionForward updateSMSTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering updateTemplate ");
	
		ActionMessages messages = new ActionMessages();
		SMSTemplateForm templateForm = (SMSTemplateForm)form;
		ActionErrors errors = templateForm.validate(mapping, request);
		try {
			if(errors.isEmpty()) {
				if(SMSTemplateHandler.getInstance().updateSMSTemplate(templateForm)){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_UPDATESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadSMSTemplateList(templateForm);
					saveMessages(request, messages);
					templateForm.clear();
				}
			} else {
				saveErrors(request, errors);
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TEMPLATE_UPDATEFAILED));
    		saveErrors(request,errors);
		}
		log.debug("Leaving updateTemplate ");
		return mapping.findForward(CMSConstants.TEMPLATE_INITSMSTEMPLATE);
	}
	
	/**
	 * @param templateString
	 * @param errors
	 */
	public void validateGroupData(ActionErrors errors,SMSTemplateForm templateForm) throws Exception{
		int courseId = 0;
		int programId = 0;
		if(templateForm.getCourseId() != null && !templateForm.getCourseId().trim().isEmpty()){
			courseId = Integer.parseInt(templateForm.getCourseId());
		}	
		if(templateForm.getProgramId()!= null && !templateForm.getProgramId().trim().isEmpty()){
			programId = Integer.parseInt(templateForm.getProgramId());
		}
		
		if(SMSTemplateHandler.getInstance().checkDuplicate(courseId, templateForm.getTemplateName(), programId).size() >= 1){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_ALREADYEXIST));
		}
	}
	
	/**
	 * This will load the template details from database.
	 * @param templateForm
	 * @throws Exception
	 */
	public void loadSMSTemplateList(SMSTemplateForm templateForm) throws Exception{
		List<SMSTemplateTo> templateTOlist = SMSTemplateHandler.getInstance().getSMSTemplateList(0);
		templateForm.setTemplateList(templateTOlist);
	}
	
	/**
	 * Method to display selected template description in view mode
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTemplateDescription(
		    ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
		
		SMSTemplateForm templateForm = (SMSTemplateForm) form;
		List<SMSTemplateTo> groupTemplateList = SMSTemplateHandler.getInstance().getSMSTemplateList(templateForm.getTemplateId());
		Iterator<SMSTemplateTo> iterator = groupTemplateList.iterator();
		SMSTemplateTo groupTemplateTO;

		while (iterator.hasNext()) {
			groupTemplateTO = (SMSTemplateTo) iterator.next();
			templateForm.setTemplateDescription(groupTemplateTO.getTemplateDescription());
			request.setAttribute("templateDescription", groupTemplateTO.getTemplateDescription());
		}
		return mapping.findForward(CMSConstants.VIEW_TEMPLATE_DESCRIPTION);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewDefinitionForm
	 * @throws Exception
	 */
	public void getProgramTypeList(SMSTemplateForm templateForm) throws Exception{
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		templateForm.setProgramTypeList(programTypeList);
	}
	
	/**
	 * Method to set all active programs to the request object
	 * @param request
	 * @param interviewDefinitionForm
	 */
	public void setProgramMapToRequest(HttpServletRequest request,
			SMSTemplateForm templateForm) {
		if (templateForm.getProgramTypeId() != null
				&& (!templateForm.getProgramTypeId().trim().isEmpty())) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(templateForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
	}

	/**
	 * Method to set all active courses to the request object
	 * @param request
	 * @param interviewDefinitionForm
	 */
	public void setCourseMapToRequest(HttpServletRequest request,
			SMSTemplateForm templateForm) {
		if (templateForm.getProgramId() != null
				&& (!templateForm.getProgramId().trim().isEmpty())) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(templateForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return this will update the template to database.
	 * @throws Exception
	 */
	public ActionForward deleteSMSTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		ActionMessages errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		SMSTemplateForm templateForm = (SMSTemplateForm)form;
		try {
			if(errors.isEmpty()) {
				if(SMSTemplateHandler.getInstance().deleteSMSTemplate(templateForm.getTemplateId())){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_DELETESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadSMSTemplateList(templateForm);
					saveMessages(request, messages);
					templateForm.clear();
				}
			} else {
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TEMPLATE_DELETEFAILED));
    		saveErrors(request,errors);
		}
		return mapping.findForward(CMSConstants.TEMPLATE_INITSMSTEMPLATE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward helpMenu(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		
		return mapping.findForward(CMSConstants.TEMPLATE_HELPTEMPLATE);
	}	
}
