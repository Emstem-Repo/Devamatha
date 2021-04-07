package com.kp.cms.actions.studentfeedback;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.studentfeedback.EvaFacultyFeedBackQuestionForm;
import com.kp.cms.handlers.studentfeedback.EvaFacultyFeedBackQuestionHandler;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaFacultyFeedBackQuestionTo;

public class EvaFacultyFeedBackQuestionAction extends BaseDispatchAction
{

	private static final Log log=LogFactory.getLog(EvaFacultyFeedBackQuestionAction.class);

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initFacultyFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("end of initFacultyFeedBackQuestion method in EvaFacultyFeedBackQuestionAction cl" +"ass.");
        EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm = (EvaFacultyFeedBackQuestionForm)form;
        String formName = mapping.getName();
        request.getSession().setAttribute("formName", formName);
        setFeedBackGroupToRequest(request);
        facultyFeedBackQuestionForm.reset(mapping, request);
        setRequestedDataToForm(facultyFeedBackQuestionForm);
        log.debug("Leaving initFacultyFeedBackQuestion");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_QUESTION);
    }

    /**
     * @param request
     * @throws Exception
     */
    private void setFeedBackGroupToRequest(HttpServletRequest request)
        throws Exception {
        log.debug("inside setFeedBackGroupToRequest");
        List<EvaFacultyFeedBackGroupTo> feedBackGroupList = EvaFacultyFeedBackQuestionHandler.getInstance().getGroup();
        request.getSession().setAttribute("feedBackGroupList", feedBackGroupList);
        log.debug("leaving setFeedBackGroupToRequest");
    }

    /**
     * @param facultyFeedBackQuestionForm
     * @throws Exception
     */
    private void setRequestedDataToForm(EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm)
        throws Exception{
    	List<EvaFacultyFeedBackQuestionTo> questionList = EvaFacultyFeedBackQuestionHandler.getInstance().getFeedBackQuestionList();
        facultyFeedBackQuestionForm.setFeedBackQuestionList(questionList);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addFacultyFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of addFeedBackQuestion method in EvaFacultyFeedBackQuestionAction class.");
        EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm = (EvaFacultyFeedBackQuestionForm)form;
        setUserId(request, facultyFeedBackQuestionForm);
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = facultyFeedBackQuestionForm.validate(mapping, request);
        HttpSession session = request.getSession();
        if(errors.isEmpty())
        {
            try
            {
                boolean isAdded = false;
                boolean isDuplicate = EvaFacultyFeedBackQuestionHandler.getInstance().duplicateCheck(facultyFeedBackQuestionForm, errors, session);
                if(!isDuplicate)
                {
                    isAdded = EvaFacultyFeedBackQuestionHandler.getInstance().addFeedBackQuestion(facultyFeedBackQuestionForm, "Add");
                    if(isAdded)
                    {
                        messages.add("messages", new ActionMessage("knowledgepro.studentFeedBack.questionaddsuccess"));
                        saveMessages(request, messages);
                        setRequestedDataToForm(facultyFeedBackQuestionForm);
                        facultyFeedBackQuestionForm.reset(mapping, request);
                    } else
                    {
                        errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionaddfailure"));
                        addErrors(request, errors);
                        facultyFeedBackQuestionForm.reset(mapping, request);
                    }
                } else
                {
                    addErrors(request, errors);
                }
            }
            catch(Exception exception)
            {
                log.error("Error occured in caste Entry Action", exception);
                String msg = super.handleApplicationException(exception);
                facultyFeedBackQuestionForm.setErrorMessage(msg);
                facultyFeedBackQuestionForm.setErrorStack(exception.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else
        {
            saveErrors(request, errors);
            setRequestedDataToForm(facultyFeedBackQuestionForm);
            return mapping.findForward("facultyFeedBackQuestion");
        }
        log.info("end of addFeedBackQuestion method in EvaFacultyFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_QUESTION);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editFacultyFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	log.info("call of editFeedBackQuestion method in EvaFacultyFeedBackQuestionAction class.");
        EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm = (EvaFacultyFeedBackQuestionForm)form;
        log.debug("Entering editFeedBackQuestion ");
        try
        {
            EvaFacultyFeedBackQuestionHandler.getInstance().editFeedBackQuestion(facultyFeedBackQuestionForm);
            request.setAttribute("FeedBackQuestion", "edit");
            log.debug("Leaving editFeedBackQuestion ");
        }
        catch(Exception e)
        {
            log.error("error in editing FeedBackQuestion...", e);
            String msg = super.handleApplicationException(e);
            facultyFeedBackQuestionForm.setErrorMessage(msg);
            facultyFeedBackQuestionForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("end of editFeedBackQuestion method in EvaFacultyFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_QUESTION);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateFacultyFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	log.debug("Enter: updateFeedBackQuestion Action");
        EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm = (EvaFacultyFeedBackQuestionForm)form;
        HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = facultyFeedBackQuestionForm.validate(mapping, request);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					facultyFeedBackQuestionForm.reset(mapping, request);
			        String formName = mapping.getName();
			        request.getSession().setAttribute("formName", formName);
			        EvaFacultyFeedBackQuestionHandler.getInstance().editFeedBackQuestion(facultyFeedBackQuestionForm);
			        request.setAttribute("FeedBackQuestion", "edit");
			        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_QUESTION);
				}
				setUserId(request, facultyFeedBackQuestionForm);
				boolean isDuplicate = EvaFacultyFeedBackQuestionHandler.getInstance().duplicateCheck(facultyFeedBackQuestionForm, errors, session);
				if(!isDuplicate){
					isUpdated = EvaFacultyFeedBackQuestionHandler.getInstance().updateFeedBackQuestion(facultyFeedBackQuestionForm, "Edit");
				if (isUpdated) {
                    ActionMessage message = new ActionMessage("knowledgepro.studentFeedBack.questionupdatesuccess");
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    facultyFeedBackQuestionForm.reset(mapping, request);
                } else {
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionupdatefailure"));
                    addErrors(request, errors);
                    facultyFeedBackQuestionForm.reset(mapping, request);
                }}
				else{
	                request.setAttribute("FeedBackQuestion", "edit");
	                addErrors(request, errors);
	            }
			} catch (Exception e) {
	            log.error("Error occured in edit valuatorcharges", e);
	            String msg = super.handleApplicationException(e);
	            facultyFeedBackQuestionForm.setErrorMessage(msg);
	            facultyFeedBackQuestionForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }}else{
				saveErrors(request, errors);
				setRequestedDataToForm(facultyFeedBackQuestionForm);
		        request.setAttribute("FeedBackQuestion", "edit");
				return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_QUESTION);
			}
		 setRequestedDataToForm(facultyFeedBackQuestionForm);
        log.debug("Exit: action class updateFeedBackQuestion");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_QUESTION);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteFacultyFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.debug("Action class. Delete valuatorCharges ");
        EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm = (EvaFacultyFeedBackQuestionForm)form;
        ActionMessages messages = new ActionMessages();
        try
        {
            boolean isDeleted = EvaFacultyFeedBackQuestionHandler.getInstance().deleteFeedBackQuestion(facultyFeedBackQuestionForm);
            if(isDeleted)
            {
                ActionMessage message = new ActionMessage("knowledgepro.studentFeedBack.questiondeletesuccess");
                messages.add("messages", message);
                saveMessages(request, messages);
            } else
            {
                ActionMessage message = new ActionMessage("knowledgepro.studentFeedBack.questiondeletefailure");
                messages.add("messages", message);
                saveMessages(request, messages);
            }
            facultyFeedBackQuestionForm.reset(mapping, request);
            setRequestedDataToForm(facultyFeedBackQuestionForm);
        }
        catch(Exception e)
        {
            log.error("error submit valuatorCharges...", e);
            if(e instanceof ApplicationException)
            {
                String msg = super.handleApplicationException(e);
                facultyFeedBackQuestionForm.setErrorMessage(msg);
                facultyFeedBackQuestionForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            } else
            {
                String msg = super.handleApplicationException(e);
                facultyFeedBackQuestionForm.setErrorMessage(msg);
                facultyFeedBackQuestionForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        }
        log.debug("Action class. Delete valuatorCharges ");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_QUESTION);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reActivateFacultyFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        log.info("Entering ReactivateValuatorCharges Action");
        EvaFacultyFeedBackQuestionForm facultyFeedBackQuestionForm = (EvaFacultyFeedBackQuestionForm)form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        try
        {
            setUserId(request, facultyFeedBackQuestionForm);
            String userId = facultyFeedBackQuestionForm.getUserId();
            String duplicateId = session.getAttribute("ReactivateId").toString();
            facultyFeedBackQuestionForm.setId(Integer.parseInt(duplicateId));
            boolean isReactivate = EvaFacultyFeedBackQuestionHandler.getInstance().reActivateFeedBackQuestion(facultyFeedBackQuestionForm, userId);
            if(isReactivate)
            {
                messages.add("messages", new ActionMessage("knowledgepro.studentFeedBack.questionreactivatesuccess"));
                setRequestedDataToForm(facultyFeedBackQuestionForm);
                facultyFeedBackQuestionForm.reset(mapping, request);
                saveMessages(request, messages);
            } else
            {
                setRequestedDataToForm(facultyFeedBackQuestionForm);
                errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionreactivatefailure"));
                addErrors(request, errors);
            }
        }
        catch(Exception e)
        {
            log.error("Error occured in reactivatevaluatorCharges of ValuatorChargesAction", e);
            String msg = super.handleApplicationException(e);
            facultyFeedBackQuestionForm.setErrorMessage(msg);
            facultyFeedBackQuestionForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("Leaving into reactivatevaluatorCharges of ValuatorChargesAction");
        return mapping.findForward(CMSConstants.FACULTY_FEEDBACK_QUESTION);
    }

}
