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
import com.kp.cms.forms.admin.TemplateForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.GroupTemplateTO;
import com.kp.cms.to.admin.ProgramTypeTO;


@SuppressWarnings("deprecation")
/**
 * This is an action class for Template creation.
 */
public class TemplateAction extends BaseDispatchAction {
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
	public ActionForward initGroupTemplate(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		
		TemplateForm templateForm = (TemplateForm)form;
		templateForm.clear();
		setUserId(request, templateForm);
	//Initially The list of templates need not be loaded as onchange of program or template name the list be loaded through ajax- Smitha	
	//	loadGroupTemplateList(templateForm);
		templateForm.setTemplateList(null);
		getProgramTypeList(templateForm);
		return mapping.findForward(CMSConstants.TEMPLATE_INITGROUPTEMPLATE);
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
	public ActionForward createGroupTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateString = request.getParameter(CMSConstants.EDITOR_DEFAULT);
		ActionMessages messages = new ActionMessages();
		TemplateForm templateForm = (TemplateForm)form;
		ActionErrors errors = templateForm.validate(mapping, request);
		validateGroupData(templateString,errors,templateForm);
		
		try {
			if(errors.isEmpty()) {
				templateForm.setTemplateDescription(templateString);
				if(TemplateHandler.getInstance().saveGroupTemplate(templateForm)){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_ADDSUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadGroupTemplateList(templateForm);
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
		return mapping.findForward(CMSConstants.TEMPLATE_INITGROUPTEMPLATE);
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
	public ActionForward editGroupTemplate(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		
		TemplateForm templateForm = (TemplateForm)form;
		List<GroupTemplateTO> groupTemplateList = TemplateHandler.getInstance().getGroupTemplateList(templateForm.getTemplateId(),templateForm.getProgramTypeId(),templateForm.getProgramId(),templateForm.getTemplateName());
		Iterator<GroupTemplateTO> iterator = groupTemplateList.iterator();
		GroupTemplateTO groupTemplateTO;
        templateForm.clear();
		while (iterator.hasNext()) {
			groupTemplateTO = (GroupTemplateTO) iterator.next();
			
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
		//added by Smitha to retain the template list 
		loadGroupTemplateList(templateForm);
		request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.TEMPLATE_INITGROUPTEMPLATE);
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
	public ActionForward updateGroupTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		String templateString = request.getParameter(CMSConstants.EDITOR_DEFAULT);
		ActionMessages messages = new ActionMessages();
		TemplateForm templateForm = (TemplateForm)form;
		ActionErrors errors = templateForm.validate(mapping, request);
		if(templateString == null || templateString.length() == 0){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_CANNOTBEEMPTY));
		}
		
		try {
			if(errors.isEmpty()) {
				templateForm.setTemplateDescription(templateString);
				if(TemplateHandler.getInstance().updateGroupTemplate(templateForm)){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_UPDATESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadGroupTemplateList(templateForm);
					saveMessages(request, messages);
					templateForm.clear1();
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
		return mapping.findForward(CMSConstants.TEMPLATE_INITGROUPTEMPLATE);
	}
	
	/**
	 * @param templateString
	 * @param errors
	 */
	public void validateGroupData(String templateString,ActionErrors errors,TemplateForm templateForm) throws Exception{
		if(templateString == null || templateString.length() == 0){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_CANNOTBEEMPTY));
		}
		int courseId = 0;
		int programId = 0;
		if(templateForm.getCourseId() != null && !templateForm.getCourseId().trim().isEmpty()){
			courseId = Integer.parseInt(templateForm.getCourseId());
		}	
		if(templateForm.getProgramId()!= null && !templateForm.getProgramId().trim().isEmpty()){
			programId = Integer.parseInt(templateForm.getProgramId());
		}
		
		if(TemplateHandler.getInstance().checkDuplicate(courseId, templateForm.getTemplateName(), programId).size() >= 1){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_ALREADYEXIST));
		}
	}
	
	/**
	 * This will load the template details from database.
	 * @param templateForm
	 * @throws Exception
	 */
	public void loadGroupTemplateList(TemplateForm templateForm) throws Exception{
		List<GroupTemplateTO> templateTOlist = TemplateHandler.getInstance().getGroupTemplateList(0,templateForm.getProgramTypeId(),templateForm.getProgramId(),templateForm.getTemplateName());
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
		
		TemplateForm templateForm = (TemplateForm) form;
		List<GroupTemplateTO> groupTemplateList = TemplateHandler.getInstance().getGroupTemplateList(templateForm.getTemplateId(),templateForm.getProgramTypeId(),templateForm.getProgramId(),templateForm.getTemplateName());
		Iterator<GroupTemplateTO> iterator = groupTemplateList.iterator();
		GroupTemplateTO groupTemplateTO;

		while (iterator.hasNext()) {
			groupTemplateTO = (GroupTemplateTO) iterator.next();
			templateForm.setTemplateDescription(groupTemplateTO.getTemplateDescription());
		}
		return mapping.findForward(CMSConstants.VIEW_TEMPLATE_DESCRIPTION);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewDefinitionForm
	 * @throws Exception
	 */
	public void getProgramTypeList(TemplateForm templateForm) throws Exception{
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
	    templateForm.setProgramTypeList(programTypeList);
	}
	
	/**
	 * Method to set all active programs to the request object
	 * @param request
	 * @param interviewDefinitionForm
	 */
	public void setProgramMapToRequest(HttpServletRequest request,
			TemplateForm templateForm) {
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
			TemplateForm templateForm) {
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
	public ActionForward deleteGroupTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		TemplateForm templateForm = (TemplateForm)form;
		try {
			if(errors.isEmpty()) {
				if(TemplateHandler.getInstance().deleteGroupTemplate(templateForm.getTemplateId())){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_DELETESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadGroupTemplateList(templateForm);
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
		return mapping.findForward(CMSConstants.TEMPLATE_INITGROUPTEMPLATE);
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