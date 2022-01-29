package com.kp.cms.actions.admin;

import java.util.HashMap;
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
import com.kp.cms.bo.admin.GroupTemplateInterview;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.InterviewTemplateForm;
import com.kp.cms.handlers.admin.InterviewTemplateHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.InterviewTemplateTo;
import com.kp.cms.to.admin.ProgramTypeTO;

public class InterviewTemplateAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(InterviewTemplateAction.class);
	
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
	public ActionForward initInterviewTemplate(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		
		InterviewTemplateForm interviewTemplateForm =(InterviewTemplateForm) form;
		interviewTemplateForm.clear();
		setUserId(request, interviewTemplateForm);
		loadInterviewTemplateList(interviewTemplateForm);
		getProgramTypeList(interviewTemplateForm);
		return mapping.findForward(CMSConstants.TEMPLATE_INITINTERVIEWTEMPLATE);
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
	public ActionForward createInterviewTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering createTemplate ");
		String templateString = request.getParameter(CMSConstants.EDITOR_DEFAULT);
		ActionMessages messages = new ActionMessages();
		InterviewTemplateForm interviewTemplateForm =(InterviewTemplateForm) form;
		ActionErrors errors = interviewTemplateForm.validate(mapping, request);
		validateGroupData(templateString,errors,interviewTemplateForm);
		
		try {
			if(errors.isEmpty()) {
				interviewTemplateForm.setTemplateDescription(templateString);
				if(InterviewTemplateHandler.getInstance().saveInterviewTemplate(interviewTemplateForm)){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_ADDSUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadInterviewTemplateList(interviewTemplateForm);
					saveMessages(request, messages);
//					interviewTemplateForm.clear();
				}
			} else {
				saveErrors(request, errors);
				setCourseMapToRequest(request, interviewTemplateForm);
				setProgramMapToRequest(request, interviewTemplateForm);
				setRequestedDataToForm(request, interviewTemplateForm);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TEMPLATE_ADDFAILED));
    		saveErrors(request,errors);
		}
		log.debug("Leaving createTemplate ");
		return mapping.findForward(CMSConstants.TEMPLATE_INITINTERVIEWTEMPLATE);
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
	public ActionForward editInterviewTemplate(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		
		InterviewTemplateForm interviewTemplateForm =(InterviewTemplateForm) form;
		List<InterviewTemplateTo> groupTemplateList = InterviewTemplateHandler.getInstance().getInterviewTemplateList(interviewTemplateForm.getTemplateId());
		Iterator<InterviewTemplateTo> iterator = groupTemplateList.iterator();
		InterviewTemplateTo groupTemplateTO;

		while (iterator.hasNext()) {
			groupTemplateTO = (InterviewTemplateTo) iterator.next();
			
			if(groupTemplateTO.getProgramTypeId() != null && !groupTemplateTO.getProgramTypeId().trim().isEmpty()){
				interviewTemplateForm.setProgramTypeId(groupTemplateTO.getProgramTypeId());
			}
			if(groupTemplateTO.getProgramId() != null && !groupTemplateTO.getProgramId().trim().isEmpty()){
				interviewTemplateForm.setProgramId(groupTemplateTO.getProgramId());
			}
			if(groupTemplateTO.getCourseId() != null && !groupTemplateTO.getCourseId().trim().isEmpty()){
				interviewTemplateForm.setCourseId(groupTemplateTO.getCourseId());
			}
			if(groupTemplateTO.getInterviewId()!=null && !groupTemplateTO.getInterviewId().isEmpty()){
				interviewTemplateForm.setInterviewId(groupTemplateTO.getInterviewId());
			}
			if(groupTemplateTO.getInterviewSubRoundId()!=null && !groupTemplateTO.getInterviewSubRoundId().isEmpty()){
				interviewTemplateForm.setInterviewSubroundId(groupTemplateTO.getInterviewSubRoundId());
			}
			interviewTemplateForm.setTemplateName(groupTemplateTO.getTemplateName());
			interviewTemplateForm.setTemplateDescription(groupTemplateTO.getTemplateDescription());
			interviewTemplateForm.setAppliedYear(groupTemplateTO.getYear());
		}
		setProgramMapToRequest(request, interviewTemplateForm);
		setCourseMapToRequest(request, interviewTemplateForm);
		setRequestedDataToForm(request, interviewTemplateForm);
		request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.TEMPLATE_INITINTERVIEWTEMPLATE);
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
	public ActionForward updateInterviewTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		String templateString = request.getParameter(CMSConstants.EDITOR_DEFAULT);
		ActionMessages messages = new ActionMessages();
		InterviewTemplateForm interviewTemplateForm =(InterviewTemplateForm) form;
		ActionErrors errors = interviewTemplateForm.validate(mapping, request);
		if(templateString == null || templateString.length() == 0){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_CANNOTBEEMPTY));
		}
		validateGroupDataInUpdate(templateString, errors, interviewTemplateForm);
		try {
			if(errors.isEmpty()) {
				interviewTemplateForm.setTemplateDescription(templateString);
				if(InterviewTemplateHandler.getInstance().updateInterviewTemplate(interviewTemplateForm)){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_UPDATESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadInterviewTemplateList(interviewTemplateForm);
					saveMessages(request, messages);
					interviewTemplateForm.clear();
				}
			} else {
				saveErrors(request, errors);
				setCourseMapToRequest(request, interviewTemplateForm);
				setProgramMapToRequest(request, interviewTemplateForm);
				setRequestedDataToForm(request, interviewTemplateForm);
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TEMPLATE_UPDATEFAILED));
    		saveErrors(request,errors);
		}
		return mapping.findForward(CMSConstants.TEMPLATE_INITINTERVIEWTEMPLATE);
	}
	
	/**
	 * @param templateString
	 * @param errors
	 */
	public void validateGroupData(String templateString,ActionErrors errors,InterviewTemplateForm interviewTemplateForm) throws Exception{
		if(templateString == null || templateString.length() == 0){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_CANNOTBEEMPTY));
		}
		int courseId = 0;
		int programId = 0;
		int interviewId=0;
		int subroundId=0;
		if(interviewTemplateForm.getCourseId() != null && !interviewTemplateForm.getCourseId().trim().isEmpty()){
			courseId = Integer.parseInt(interviewTemplateForm.getCourseId());
		}	
		if(interviewTemplateForm.getProgramId()!= null && !interviewTemplateForm.getProgramId().trim().isEmpty()){
			programId = Integer.parseInt(interviewTemplateForm.getProgramId());
		}
		if(interviewTemplateForm.getInterviewId()!= null && !interviewTemplateForm.getInterviewId().trim().isEmpty()){
			interviewId = Integer.parseInt(interviewTemplateForm.getInterviewId());
		}
		if(interviewTemplateForm.getInterviewSubroundId()!= null && !interviewTemplateForm.getInterviewSubroundId().trim().isEmpty()){
			subroundId = Integer.parseInt(interviewTemplateForm.getInterviewSubroundId());
		}
		if(InterviewTemplateHandler.getInstance().checkDuplicate(courseId, interviewTemplateForm.getTemplateName(), programId,interviewId,subroundId,Integer.parseInt(interviewTemplateForm.getAppliedYear())).size() >= 1){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_ALREADYEXIST));
		}
	}
	
	/**
	 * @param templateString
	 * @param errors
	 */
	public void validateGroupDataInUpdate(String templateString,ActionErrors errors,InterviewTemplateForm interviewTemplateForm) throws Exception{
		if(templateString == null || templateString.length() == 0){
			errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_CANNOTBEEMPTY));
		}
		int courseId = 0;
		int programId = 0;
		int interviewId=0;
		int subroundId=0;
		if(interviewTemplateForm.getCourseId() != null && !interviewTemplateForm.getCourseId().trim().isEmpty()){
			courseId = Integer.parseInt(interviewTemplateForm.getCourseId());
		}	
		if(interviewTemplateForm.getProgramId()!= null && !interviewTemplateForm.getProgramId().trim().isEmpty()){
			programId = Integer.parseInt(interviewTemplateForm.getProgramId());
		}
		if(interviewTemplateForm.getInterviewId()!= null && !interviewTemplateForm.getInterviewId().trim().isEmpty()){
			interviewId = Integer.parseInt(interviewTemplateForm.getInterviewId());
		}
		if(interviewTemplateForm.getInterviewSubroundId()!= null && !interviewTemplateForm.getInterviewSubroundId().trim().isEmpty()){
			subroundId = Integer.parseInt(interviewTemplateForm.getInterviewSubroundId());
		}
		List<GroupTemplateInterview> list=InterviewTemplateHandler.getInstance().checkDuplicate(courseId, interviewTemplateForm.getTemplateName(), programId,interviewId,subroundId,Integer.parseInt(interviewTemplateForm.getAppliedYear()));
		if(list!=null && !list.isEmpty()){
			Iterator<GroupTemplateInterview> itr=list.iterator();
			while (itr.hasNext()) {
				GroupTemplateInterview groupTemplateInterview = (GroupTemplateInterview) itr
						.next();
				if(groupTemplateInterview.getId()!=interviewTemplateForm.getTemplateId()){
					errors.add(CMSConstants.ERRORS,new ActionError(CMSConstants.TEMPLATE_ALREADYEXIST));
				}
			}
		}
	}
	
	/**
	 * This will load the template details from database.
	 * @param templateForm
	 * @throws Exception
	 */
	public void loadInterviewTemplateList(InterviewTemplateForm interviewTemplateForm) throws Exception{
		List<InterviewTemplateTo> templateTOlist = InterviewTemplateHandler.getInstance().getInterviewTemplateList(0);
		interviewTemplateForm.setTemplateList(templateTOlist);
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

		InterviewTemplateForm interviewTemplateForm =(InterviewTemplateForm) form;
		List<InterviewTemplateTo> groupTemplateList = InterviewTemplateHandler.getInstance().getInterviewTemplateList(interviewTemplateForm.getTemplateId());
		Iterator<InterviewTemplateTo> iterator = groupTemplateList.iterator();
		InterviewTemplateTo groupTemplateTO;

		while (iterator.hasNext()) {
			groupTemplateTO = (InterviewTemplateTo) iterator.next();
			interviewTemplateForm.setTemplateDescription(groupTemplateTO.getTemplateDescription());
		}
		return mapping.findForward(CMSConstants.VIEW_TEMPLATE_DESCRIPTION);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewDefinitionForm
	 * @throws Exception
	 */
	public void getProgramTypeList(InterviewTemplateForm interviewTemplateForm) throws Exception{
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		interviewTemplateForm.setProgramTypeList(programTypeList);
	}
	
	/**
	 * Method to set all active programs to the request object
	 * @param request
	 * @param interviewDefinitionForm
	 */
	public void setProgramMapToRequest(HttpServletRequest request,
			InterviewTemplateForm interviewTemplateForm) {
		if (interviewTemplateForm.getProgramTypeId() != null
				&& (!interviewTemplateForm.getProgramTypeId().trim().isEmpty())) {
			Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(interviewTemplateForm.getProgramTypeId()));
			request.setAttribute("programMap", programMap);
		}
	}

	/**
	 * Method to set all active courses to the request object
	 * @param request
	 * @param interviewDefinitionForm
	 */
	public void setCourseMapToRequest(HttpServletRequest request,
			InterviewTemplateForm interviewTemplateForm) {
		if (interviewTemplateForm.getProgramId() != null
				&& (!interviewTemplateForm.getProgramId().trim().isEmpty())) {
			Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(interviewTemplateForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
	}
	
	public void setRequestedDataToForm(HttpServletRequest request,
			InterviewTemplateForm interviewTemplateForm){
		Map<Integer,String> collegeMap;
		if(interviewTemplateForm.getCourseId()!=null && interviewTemplateForm.getAppliedYear()!=null &&
				interviewTemplateForm.getCourseId().length()>0 && interviewTemplateForm.getAppliedYear().length()>0){
				collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByCourse
				(Integer.valueOf(interviewTemplateForm.getCourseId()),Integer.valueOf(interviewTemplateForm.getAppliedYear()));
				request.setAttribute("interviewMap", collegeMap);
		}
		if(interviewTemplateForm.getInterviewId()!=null && 
				interviewTemplateForm.getInterviewId().length()>0 ){
				collegeMap = CommonAjaxHandler.getInstance().getInterviewSubroundsByInterviewType(Integer.parseInt(interviewTemplateForm.getInterviewId()));
				request.setAttribute("interviewSubroundsMap", collegeMap);
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
	public ActionForward deleteInterviewTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering updateTemplate ");
	
		ActionMessages errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		InterviewTemplateForm interviewTemplateForm =(InterviewTemplateForm) form;
		try {
			if(errors.isEmpty()) {
				if(InterviewTemplateHandler.getInstance().deleteInterviewTemplate(interviewTemplateForm.getTemplateId())){
					ActionMessage message = new ActionMessage(CMSConstants.TEMPLATE_DELETESUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					loadInterviewTemplateList(interviewTemplateForm);
					saveMessages(request, messages);
					interviewTemplateForm.clear();
				}
			} else {
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.TEMPLATE_DELETEFAILED));
    		saveErrors(request,errors);
		}
		log.debug("Leaving updateTemplate ");
		return mapping.findForward(CMSConstants.TEMPLATE_INITINTERVIEWTEMPLATE);
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