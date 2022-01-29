package com.kp.cms.actions.exam;

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
import com.kp.cms.forms.admission.OfflineDetailsForm;
import com.kp.cms.forms.exam.ExamConsolidateMarksCardForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admission.OfflineDetailsHandler;
import com.kp.cms.handlers.exam.ExamConsolidateMarksCardHandler;
import com.kp.cms.to.admin.CourseTO;

public class ExamConsolidateMarksCardAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(ExamConsolidateMarksCardAction.class);
	
	public ActionForward initConsolidateMarksCard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 	log.debug("Entering initConsolidateMarksCard ");
		 	ExamConsolidateMarksCardForm examConsolidateMarksCardForm = (ExamConsolidateMarksCardForm)form;
		 	examConsolidateMarksCardForm.clear();
		 	assignListToForm(examConsolidateMarksCardForm);
		 	log.debug("Leaving initConsolidateMarksCard ");
		    return mapping.findForward(CMSConstants.EXAM_CONSOLIDATE_MARKSCARD);
	}
	
	public void assignListToForm(ActionForm form) throws Exception
	{
		log.info("Entering into assignListToForm of ExamConsolidateMarksCardAction");
		ExamConsolidateMarksCardForm examConsolidateMarksCardForm = (ExamConsolidateMarksCardForm)form;
		List<CourseTO> courseList=CourseHandler.getInstance().getActiveCourses();
		if(courseList!=null && !courseList.isEmpty()){
			examConsolidateMarksCardForm.setCourseList(courseList);
		}
	}
	
	public ActionForward saveConsolidateMarksCardDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into saveConsolidateMarksCardDetails of ExamConsolidateMarksCardAction");
		ExamConsolidateMarksCardForm examConsolidateMarksCardForm = (ExamConsolidateMarksCardForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examConsolidateMarksCardForm.validate(mapping, request);
		if(!errors.isEmpty() )
		{
		saveErrors(request, errors);
		assignListToForm(examConsolidateMarksCardForm);
		return mapping.findForward(CMSConstants.EXAM_CONSOLIDATE_MARKSCARD);
		}
		try {
			if (errors.isEmpty()) {
				setUserId(request, examConsolidateMarksCardForm);
				
				//int courseId=Integer.parseInt(examConsolidateMarksCardForm.getCourseId().trim());
				//int year = Integer.parseInt(examConsolidateMarksCardForm.getYear().trim());
				boolean isAdded;
				if(examConsolidateMarksCardForm!=null){
				isAdded = ExamConsolidateMarksCardHandler.getInstance().saveConsolidateMarksCardDetails(examConsolidateMarksCardForm);
					if (isAdded) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.KNOWLEDGEPRO_CONSOLIDATE_MARKSCARD_ADD_SUCCESS));
						examConsolidateMarksCardForm.clear();
					} else {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_CONSOLIDATE_MARKSCARD_ADD_FAILURE));
					examConsolidateMarksCardForm.clear();
					}
				}
			}
			}catch (Exception e) {
				log.info("Error occured at saveConsolidateMarksCardDetails of ExamConsolidateMarksCardAction",e);
				String msg = super.handleApplicationException(e);
				examConsolidateMarksCardForm.setErrorMessage(msg);
				examConsolidateMarksCardForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("Leaving into saveConsolidateMarksCardDetails of ExamConsolidateMarksCardAction");
			assignListToForm(examConsolidateMarksCardForm);
			saveErrors(request, errors);
			saveMessages(request, messages);
		return mapping.findForward(CMSConstants.EXAM_CONSOLIDATE_MARKSCARD);
	}
}
