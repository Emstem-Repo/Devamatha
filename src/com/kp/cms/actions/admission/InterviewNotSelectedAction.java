package com.kp.cms.actions.admission;

import java.util.Iterator;
import java.util.List;

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
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewNotSelectedForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.InterviewNotSelectedHandler;
import com.kp.cms.to.admission.InterviewNotSelectedTO;

@SuppressWarnings("deprecation")
public class InterviewNotSelectedAction extends BaseDispatchAction{

private static final Log log = LogFactory.getLog(InterviewNotSelectedAction.class);
	
	/**
	 * Method to set the required data to the form to display it in interviewNotSelected.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCandidateSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered InterviewNotSelectedAction initStudentSearch");
		InterviewNotSelectedForm interviewNotSelectedForm = (InterviewNotSelectedForm) form;
		interviewNotSelectedForm.resetFields();
		setRequiredDatatoForm(interviewNotSelectedForm);
		log.info("Exit InterviewNotSelectedAction initStudentSearch");
		
		return mapping.findForward(CMSConstants.INTERVIEW_NOTSELECTED_INPUT);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewBatchEntryForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(InterviewNotSelectedForm interviewNotSelectedForm) throws Exception {
		if(ProgramTypeHandler.getInstance().getProgramType() != null){
			interviewNotSelectedForm.setProgramTypeList(ProgramTypeHandler.getInstance().getProgramType());
		}
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
	public ActionForward getInterviewNotSelectedCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered InterviewNotSelectedAction - getInterviewNotSelectedCandidates");
		
		InterviewNotSelectedForm interviewNotSelectedForm = (InterviewNotSelectedForm) form;
		 ActionErrors errors = interviewNotSelectedForm.validate(mapping, request);
		
		if (errors.isEmpty()) {
			try {
				List<InterviewNotSelectedTO> notSelectedCandidates = InterviewNotSelectedHandler.getInstance().notSelectedCandidates(interviewNotSelectedForm);
				
				if (notSelectedCandidates.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					log.info("Exit InterviewNotSelectedAction - getInterviewNotSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.INTERVIEW_NOTSELECTED_INPUT);
				} else {
					//save the selected candidates list to form
					interviewNotSelectedForm.setNotSelectedCandidatesList(notSelectedCandidates);
				}
			}  catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				interviewNotSelectedForm.setErrorMessage(msg);
				interviewNotSelectedForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(interviewNotSelectedForm);
			log.info("Exit InterviewNotSelectedAction - getInterviewNotSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INTERVIEW_NOTSELECTED_INPUT);
		}
		log.info("Exit InterviewNotSelectedAction - getInterviewNotSelectedCandidates");
		return mapping.findForward(CMSConstants.INTERVIEW_NOTSELECTED_UPDATE);
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
	public ActionForward sendInterviewNotSelectedMail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.debug("inside sendInterviewNotSelectedMail");
		InterviewNotSelectedForm interviewNotSelectedForm = (InterviewNotSelectedForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		ActionMessage message = null;
		// validating check boxes if user not selecting 
		List<InterviewNotSelectedTO> list = interviewNotSelectedForm.getNotSelectedCandidatesList();
		Iterator<InterviewNotSelectedTO> itr = list.iterator();	
		//Set<Integer> ids = new HashSet<Integer>();
		InterviewNotSelectedTO interviewNotSelectedTO;
		//String s="";
		int i=list.size();
		int  condition=0;
		while(itr.hasNext()) {
			interviewNotSelectedTO = itr.next();
			if(!interviewNotSelectedTO.isSendMailSelected()){
					//	interviewNotSelectedTO.setComments("");
						
						condition=condition+1;
					}
		}
		if(i==condition){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_SELECT));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INTERVIEW_NOTSELECTED_UPDATE);
		}
		
		
		try {
			List<GroupTemplate> groupTemplateList = null;
			if(interviewNotSelectedForm.getCourseId()!=null && !interviewNotSelectedForm.getCourseId().trim().isEmpty()){
				groupTemplateList = TemplateHandler.getInstance().getDuplicateCheckList(Integer.parseInt(interviewNotSelectedForm.getCourseId().trim()), CMSConstants.INTERVIEW_NOT_SELECTED);
			}
			if(groupTemplateList.isEmpty()){
				groupTemplateList = TemplateHandler.getInstance().getDuplicateCheckList(0, CMSConstants.INTERVIEW_NOT_SELECTED);
			}
			
			String templateDescription = "";
			
			Iterator<GroupTemplate> iterator = groupTemplateList.iterator();
			while (iterator.hasNext()) {
				GroupTemplate groupTemplate = (GroupTemplate) iterator.next();
				templateDescription = groupTemplate.getTemplateDescription();
			}
			interviewNotSelectedForm.setTemplateDescription(templateDescription);
									
			if(InterviewNotSelectedHandler.getInstance().sendInterviewNotSelectedMail(interviewNotSelectedForm)){
				message = new ActionMessage("knowledgepro.admin.bulkmail.sent");
				messages.add(CMSConstants.MESSAGES, message);
				addMessages(request, messages);
			}else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.bulkmail.notsent"));
				saveErrors(request, errors);
			}
			interviewNotSelectedForm.resetFields();
			setRequiredDatatoForm(interviewNotSelectedForm);
		} catch (Exception e) {
			log.error("error in sendInterviewNotSelectedMail...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				interviewNotSelectedForm.setErrorMessage(msg);
				interviewNotSelectedForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}	
		}
		log.debug("leaving sendInterviewNotSelectedMail");
		return mapping.findForward(CMSConstants.INTERVIEW_NOTSELECTED_INPUT);
	}
}
