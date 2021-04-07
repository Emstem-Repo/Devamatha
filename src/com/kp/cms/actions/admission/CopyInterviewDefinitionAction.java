package com.kp.cms.actions.admission;

import java.util.Calendar;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.CopyCheckListAssignmentForm;
import com.kp.cms.forms.admission.CopyInterviewDefinitionForm;
import com.kp.cms.handlers.admission.CopyCheckListAssignmentHandler;
import com.kp.cms.handlers.admission.CopyInterviewDefinitionHandler;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class CopyInterviewDefinitionAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(CopyCheckListAssignmentAction.class);
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initCopyInterviewDefinition(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside initCopyInterviewDefinition in Action");
		 CopyInterviewDefinitionForm copyIntDefForm = (CopyInterviewDefinitionForm) form;
		try{
			copyIntDefForm.clear();
			setUserId(request, copyIntDefForm);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			copyIntDefForm.setErrorMessage(msg);
			copyIntDefForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.COPY_INTERVIEW_DEFINITION);
	}
	
	
	
	/**
	 * method returns the searched interview definition for the selected fromYear
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward searchCopyInterviewDefinition(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside searchCopyCheckList in Action");
		CopyInterviewDefinitionForm copyForm = (CopyInterviewDefinitionForm) form;
		  ActionErrors errors = copyForm.validate(mapping, request);
		validateYear(errors, copyForm);
		setUserId(request, copyForm);
		List<InterviewProgramCourseTO> interviewDefinition=null;
		try{
			if(errors.isEmpty()){
			if (copyForm.getFromYear() == null) {
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				// code by hari
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}// end
				interviewDefinition = CopyInterviewDefinitionHandler.getInstance().getInterviewDefinitionByYear(currentYear,copyForm);
			} else {
				int year = Integer.parseInt(copyForm.getFromYear());
				interviewDefinition = CopyInterviewDefinitionHandler.getInstance().getInterviewDefinitionByYear(year,copyForm);
			}
			if (interviewDefinition != null) {
				copyForm.setDisplayInterviewDefinition(interviewDefinition);
			}
			else{
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				saveErrors(request, errors);
			}
			
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			copyForm.setErrorMessage(msg);
			copyForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.COPY_INTERVIEW_DEFINITION);
	}
	
	/**
	 * validates the entered year by user
	 * @param errors
	 * @param copyCheckListAssignmentForm
	 */
	public void validateYear(ActionErrors errors,CopyInterviewDefinitionForm copyForm){
		if(copyForm.getToYear()!=null && !copyForm.getToYear().isEmpty() && copyForm.getFromYear()!=null && !copyForm.getFromYear().isEmpty()){
		if(Integer.parseInt(copyForm.getToYear())< Integer.parseInt(copyForm.getFromYear())){
			errors.add("error", new ActionError("knowledgepro.admission.validate.year"));
		}
		if(copyForm.getToYear().equals(copyForm.getFromYear())){
			errors.add("error", new ActionError("knowledgepro.admission.validate.equalyear"));
		}
	}
}
	
	
	/**
	 * copying the selected interview definition 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward copyInterviewDefinition(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		log.debug("inside copyInterviewDefinition in Action");
		CopyInterviewDefinitionForm copyForm = (CopyInterviewDefinitionForm) form;
		 ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, copyForm);
		ActionMessage message = null;
		boolean isCopied=false;
		try{
			isCopied=CopyInterviewDefinitionHandler.getInstance().copyInterviewDefinition(copyForm);
			if(isCopied){
				 message = new ActionMessage("knowledgepro.admission.copyClasses.success");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				copyForm.clear();
			}else{
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.copyInterviewDefinition.failure"));
				saveErrors(request, errors);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			copyForm.setErrorMessage(msg);
			copyForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.COPY_INTERVIEW_DEFINITION);
	}
}
