package com.kp.cms.actions.studentfeedback;

import java.util.Calendar;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.studentfeedback.EvaluateFacultyFeedbackForm;
import com.kp.cms.forms.studentfeedback.FacultyEvaluationReportForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.studentfeedback.EvaluateFacultyFeedbackHandler;
import com.kp.cms.handlers.studentfeedback.FacultyEvaluationReportHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class EvaluateFacultyFeedbackAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(EvaluateFacultyFeedbackAction.class);

	public ActionForward initEvaluateFacultyFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm = (EvaluateFacultyFeedbackForm) form;
		evaluateFacultyFeedbackForm.resetFeilds();
		setUserId(request, evaluateFacultyFeedbackForm);
		setClassMapToRequest(request, evaluateFacultyFeedbackForm);
		return mapping.findForward(CMSConstants.EVALUATE_FACULTY_FEEDBACK);
	} 
	
	public void setClassMapToRequest(HttpServletRequest request,EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm) throws Exception {
		log.info("Entering into setClassMapToRequest of EvaluateFacultyFeedbackAction");
		try {
			if (evaluateFacultyFeedbackForm.getAcademicYear() == null
					|| evaluateFacultyFeedbackForm.getAcademicYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}		

				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesForEvaluateFacultyFeedback(evaluateFacultyFeedbackForm,currentYear);
				request.setAttribute("classMap", classMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(evaluateFacultyFeedbackForm.getAcademicYear().trim());
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesForEvaluateFacultyFeedback(evaluateFacultyFeedbackForm,year);
				request.setAttribute("classMap", classMap);
			}
		} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in  EvaluateFacultyFeedbackAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setClassMapToRequest of EvaluateFacultyFeedbackAction");
	}
	public ActionForward evaluateFacultyFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		EvaluateFacultyFeedbackForm evaluateFacultyFeedbackForm = (EvaluateFacultyFeedbackForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new  ActionErrors();
		boolean evaluate=EvaluateFacultyFeedbackHandler.getInstance().setFacultyEvaluationDetails(evaluateFacultyFeedbackForm);
		if(evaluate==true)
		{
			ActionMessage message = new ActionMessage("knowledgepro.facultyeval.evaluatefeedback.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			evaluateFacultyFeedbackForm.reset(mapping, request);
			return mapping.findForward(CMSConstants.EVALUATE_FACULTY_FEEDBACK);
		}
		else
		{			
			errors.add("error", new ActionError("knowledgepro.facultyeval.evaluatefeedback.failure"));
			return mapping.findForward(CMSConstants.EVALUATE_FACULTY_FEEDBACK);
		}
	}
}
