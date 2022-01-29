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
import com.kp.cms.forms.studentfeedback.EvaStudentFeedBackQuestionForm;
import com.kp.cms.handlers.studentfeedback.EvaStudentFeedBackQuestionHandler;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;

public class EvaStudentFeedBackQuestionAction extends BaseDispatchAction
{

	private static final Log log=LogFactory.getLog(EvaStudentFeedBackQuestionAction.class);

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initStudentFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("end of initStudentFeedBackQuestion method in EvaStudentFeedBackQuestionAction cl" +"ass.");
        EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm = (EvaStudentFeedBackQuestionForm)form;
        String formName = mapping.getName();
        request.getSession().setAttribute("formName", formName);
        setFeedBackGroupToRequest(request);
        studentFeedBackQuestionForm.reset(mapping, request);
        setRequestedDataToForm(studentFeedBackQuestionForm);
        log.debug("Leaving initStudentFeedBackQuestion");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_QUESTION);
    }

    /**
     * @param request
     * @throws Exception
     */
    private void setFeedBackGroupToRequest(HttpServletRequest request)
        throws Exception {
        log.debug("inside setFeedBackGroupToRequest");
        List<EvaStudentFeedBackGroupTo> feedBackGroupList = EvaStudentFeedBackQuestionHandler.getInstance().getGroup();
        request.getSession().setAttribute("feedBackGroupList", feedBackGroupList);
        log.debug("leaving setFeedBackGroupToRequest");
    }

    /**
     * @param studentFeedBackQuestionForm
     * @throws Exception
     */
    private void setRequestedDataToForm(EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm)
        throws Exception{
    	List<EvaStudentFeedBackQuestionTo> questionList = EvaStudentFeedBackQuestionHandler.getInstance().getFeedBackQuestionList();
        studentFeedBackQuestionForm.setFeedBackQuestionList(questionList);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm = (EvaStudentFeedBackQuestionForm)form;
        setUserId(request, studentFeedBackQuestionForm);
        ActionMessages messages = new ActionMessages();
         ActionErrors errors = studentFeedBackQuestionForm.validate(mapping, request);
        HttpSession session = request.getSession();
        if(errors.isEmpty())
        {
            try
            {
                boolean isAdded = false;
                boolean isDuplicate = EvaStudentFeedBackQuestionHandler.getInstance().duplicateCheck(studentFeedBackQuestionForm, errors, session);
                if(!isDuplicate)
                {
                    isAdded = EvaStudentFeedBackQuestionHandler.getInstance().addFeedBackQuestion(studentFeedBackQuestionForm);
                    if(isAdded)
                    {
                        messages.add("messages", new ActionMessage("knowledgepro.studentFeedBack.questionaddsuccess"));
                        saveMessages(request, messages);
                        setRequestedDataToForm(studentFeedBackQuestionForm);
                        studentFeedBackQuestionForm.reset(mapping, request);
                    } else
                    {
                        errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionaddfailure"));
                        addErrors(request, errors);
                        studentFeedBackQuestionForm.reset(mapping, request);
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
                studentFeedBackQuestionForm.setErrorMessage(msg);
                studentFeedBackQuestionForm.setErrorStack(exception.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else
        {
            saveErrors(request, errors);
            setRequestedDataToForm(studentFeedBackQuestionForm);
            return mapping.findForward("studentFeedBackQuestion");
        }
        log.info("end of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_QUESTION);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	log.info("call of editFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm = (EvaStudentFeedBackQuestionForm)form;
        log.debug("Entering editFeedBackQuestion ");
        try
        {
            EvaStudentFeedBackQuestionHandler.getInstance().editFeedBackQuestion(studentFeedBackQuestionForm);
            request.setAttribute("FeedBackQuestion", "edit");
            log.debug("Leaving editFeedBackQuestion ");
        }
        catch(Exception e)
        {
            log.error("error in editing FeedBackQuestion...", e);
            String msg = super.handleApplicationException(e);
            studentFeedBackQuestionForm.setErrorMessage(msg);
            studentFeedBackQuestionForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("end of editFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_QUESTION);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	log.debug("Enter: updateFeedBackQuestion Action");
        EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm = (EvaStudentFeedBackQuestionForm)form;
        HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = studentFeedBackQuestionForm.validate(mapping, request);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					studentFeedBackQuestionForm.reset(mapping, request);
			        String formName = mapping.getName();
			        request.getSession().setAttribute("formName", formName);
			        EvaStudentFeedBackQuestionHandler.getInstance().editFeedBackQuestion(studentFeedBackQuestionForm);
			        request.setAttribute("FeedBackQuestion", "edit");
			        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_QUESTION);
				}
				setUserId(request, studentFeedBackQuestionForm);
				boolean isDuplicate = EvaStudentFeedBackQuestionHandler.getInstance().duplicateCheck(studentFeedBackQuestionForm, errors, session);
				if(!isDuplicate){
					isUpdated = EvaStudentFeedBackQuestionHandler.getInstance().updateFeedBackQuestion(studentFeedBackQuestionForm);
				if (isUpdated) {
                    ActionMessage message = new ActionMessage("knowledgepro.studentFeedBack.questionupdatesuccess");
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    studentFeedBackQuestionForm.reset(mapping, request);
                } else {
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionupdatefailure"));
                    addErrors(request, errors);
                    studentFeedBackQuestionForm.reset(mapping, request);
                }}
				else{
	                request.setAttribute("FeedBackQuestion", "edit");
	                addErrors(request, errors);
	            }
			} catch (Exception e) {
	            log.error("Error occured in edit valuatorcharges", e);
	            String msg = super.handleApplicationException(e);
	            studentFeedBackQuestionForm.setErrorMessage(msg);
	            studentFeedBackQuestionForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }}else{
				saveErrors(request, errors);
				setRequestedDataToForm(studentFeedBackQuestionForm);
		        request.setAttribute("FeedBackQuestion", "edit");
				return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_QUESTION);
			}
		 setRequestedDataToForm(studentFeedBackQuestionForm);
        log.debug("Exit: action class updateFeedBackQuestion");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_QUESTION);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.debug("Action class. Delete valuatorCharges ");
        EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm = (EvaStudentFeedBackQuestionForm)form;
        ActionMessages messages = new ActionMessages();
        try
        {
            boolean isDeleted = EvaStudentFeedBackQuestionHandler.getInstance().deleteFeedBackQuestion(studentFeedBackQuestionForm);
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
            studentFeedBackQuestionForm.reset(mapping, request);
            setRequestedDataToForm(studentFeedBackQuestionForm);
        }
        catch(Exception e)
        {
            log.error("error submit valuatorCharges...", e);
            if(e instanceof ApplicationException)
            {
                String msg = super.handleApplicationException(e);
                studentFeedBackQuestionForm.setErrorMessage(msg);
                studentFeedBackQuestionForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            } else
            {
                String msg = super.handleApplicationException(e);
                studentFeedBackQuestionForm.setErrorMessage(msg);
                studentFeedBackQuestionForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        }
        log.debug("Action class. Delete valuatorCharges ");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_QUESTION);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reActivateFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        log.info("Entering ReactivateValuatorCharges Action");
        EvaStudentFeedBackQuestionForm studentFeedBackQuestionForm = (EvaStudentFeedBackQuestionForm)form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        try
        {
            setUserId(request, studentFeedBackQuestionForm);
            String userId = studentFeedBackQuestionForm.getUserId();
            String duplicateId = session.getAttribute("ReactivateId").toString();
            studentFeedBackQuestionForm.setId(Integer.parseInt(duplicateId));
            boolean isReactivate = EvaStudentFeedBackQuestionHandler.getInstance().reActivateFeedBackQuestion(studentFeedBackQuestionForm, userId);
            if(isReactivate)
            {
                messages.add("messages", new ActionMessage("knowledgepro.studentFeedBack.questionreactivatesuccess"));
                setRequestedDataToForm(studentFeedBackQuestionForm);
                studentFeedBackQuestionForm.reset(mapping, request);
                saveMessages(request, messages);
            } else
            {
                setRequestedDataToForm(studentFeedBackQuestionForm);
                errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionreactivatefailure"));
                addErrors(request, errors);
            }
        }
        catch(Exception e)
        {
            log.error("Error occured in reactivatevaluatorCharges of ValuatorChargesAction", e);
            String msg = super.handleApplicationException(e);
            studentFeedBackQuestionForm.setErrorMessage(msg);
            studentFeedBackQuestionForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("Leaving into reactivatevaluatorCharges of ValuatorChargesAction");
        return mapping.findForward(CMSConstants.STUDENT_FEEDBACK_QUESTION);
    }

}
