package com.kp.cms.actions.teacherfeedback;

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
import com.kp.cms.forms.teacherfeedback.EvaTeacherFeedBackQuestionForm;
import com.kp.cms.handlers.teacherfeedback.EvaTeacherFeedBackQuestionHandler;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackGroupTo;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;

public class EvaTeacherFeedBackQuestionAction extends BaseDispatchAction
{

	private static final Log log=LogFactory.getLog(EvaTeacherFeedBackQuestionAction.class);

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initTeacherFeedBackQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("end of initTeacherFeedBackQuestion method in EvaTeacherFeedBackQuestionAction cl" +"ass.");
        EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm = (EvaTeacherFeedBackQuestionForm)form;
        String formName = mapping.getName();
        request.getSession().setAttribute("formName", formName);
        setFeedBackGroupToRequest(request);
        teacherFeedBackQuestionForm.reset(mapping, request);
        setRequestedDataToForm(teacherFeedBackQuestionForm);
        log.debug("Leaving initTeacherFeedBackQuestion");
        return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
    }

    /**
     * @param request
     * @throws Exception
     */
    private void setFeedBackGroupToRequest(HttpServletRequest request)
        throws Exception {
        log.debug("inside setFeedBackGroupToRequest");
        List<EvaTeacherFeedBackGroupTo> feedBackGroupList = EvaTeacherFeedBackQuestionHandler.getInstance().getGroup();
        request.getSession().setAttribute("feedBackGroupList", feedBackGroupList);
        log.debug("leaving setFeedBackGroupToRequest");
    }

    /**
     * @param teacherFeedBackQuestionForm
     * @throws Exception
     */
    private void setRequestedDataToForm(EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm)
        throws Exception{
    	List<EvaTeacherFeedBackQuestionTo> questionList = EvaTeacherFeedBackQuestionHandler.getInstance().getFeedBackQuestionList();
    	teacherFeedBackQuestionForm.setFeedBackQuestionList(questionList);
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
        log.info("call of addFeedBackQuestion method in EvaTeacherFeedBackQuestionAction class.");
        EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm = (EvaTeacherFeedBackQuestionForm)form;
        setUserId(request, teacherFeedBackQuestionForm);
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = teacherFeedBackQuestionForm.validate(mapping, request);
        HttpSession session = request.getSession();
        if(errors.isEmpty())
        {
            try
            {
                boolean isAdded = false;
                boolean isDuplicate = EvaTeacherFeedBackQuestionHandler.getInstance().duplicateCheck(teacherFeedBackQuestionForm, errors, session);
                if(!isDuplicate)
                {
                    isAdded = EvaTeacherFeedBackQuestionHandler.getInstance().addFeedBackQuestion(teacherFeedBackQuestionForm);
                    if(isAdded)
                    {
                        messages.add("messages", new ActionMessage("knowledgepro.studentFeedBack.questionaddsuccess"));
                        saveMessages(request, messages);
                        setRequestedDataToForm(teacherFeedBackQuestionForm);
                        teacherFeedBackQuestionForm.reset(mapping, request);
                    } else
                    {
                        errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionaddfailure"));
                        addErrors(request, errors);
                        teacherFeedBackQuestionForm.reset(mapping, request);
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
                teacherFeedBackQuestionForm.setErrorMessage(msg);
                teacherFeedBackQuestionForm.setErrorStack(exception.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else
        {
            saveErrors(request, errors);
            setRequestedDataToForm(teacherFeedBackQuestionForm);
            return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
        }
        log.info("end of addFeedBackQuestion method in EvaTeacherFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
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
    	log.info("call of editFeedBackQuestion method in EvaTeacherFeedBackQuestionAction class.");
        EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm = (EvaTeacherFeedBackQuestionForm)form;
        log.debug("Entering editFeedBackQuestion ");
        try
        {
            EvaTeacherFeedBackQuestionHandler.getInstance().editFeedBackQuestion(teacherFeedBackQuestionForm);
            request.setAttribute("FeedBackQuestion", "edit");
            log.debug("Leaving editFeedBackQuestion ");
        }
        catch(Exception e)
        {
            log.error("error in editing FeedBackQuestion...", e);
            String msg = super.handleApplicationException(e);
            teacherFeedBackQuestionForm.setErrorMessage(msg);
            teacherFeedBackQuestionForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("end of editFeedBackQuestion method in EvaTeacherFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
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
        EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm = (EvaTeacherFeedBackQuestionForm)form;
        HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherFeedBackQuestionForm.validate(mapping, request);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					teacherFeedBackQuestionForm.reset(mapping, request);
			        String formName = mapping.getName();
			        request.getSession().setAttribute("formName", formName);
			        EvaTeacherFeedBackQuestionHandler.getInstance().editFeedBackQuestion(teacherFeedBackQuestionForm);
			        request.setAttribute("FeedBackQuestion", "edit");
			        return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
				}
				setUserId(request, teacherFeedBackQuestionForm);
				boolean isDuplicate = EvaTeacherFeedBackQuestionHandler.getInstance().duplicateCheck(teacherFeedBackQuestionForm, errors, session);
				if(!isDuplicate){
					isUpdated = EvaTeacherFeedBackQuestionHandler.getInstance().updateFeedBackQuestion(teacherFeedBackQuestionForm);
				if (isUpdated) {
                    ActionMessage message = new ActionMessage("knowledgepro.studentFeedBack.questionupdatesuccess");
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    teacherFeedBackQuestionForm.reset(mapping, request);
                } else {
                    errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionupdatefailure"));
                    addErrors(request, errors);
                    teacherFeedBackQuestionForm.reset(mapping, request);
                }}
				else{
	                request.setAttribute("FeedBackQuestion", "edit");
	                addErrors(request, errors);
	            }
			} catch (Exception e) {
	            log.error("Error occured in edit valuatorcharges", e);
	            String msg = super.handleApplicationException(e);
	            teacherFeedBackQuestionForm.setErrorMessage(msg);
	            teacherFeedBackQuestionForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }}else{
				saveErrors(request, errors);
				setRequestedDataToForm(teacherFeedBackQuestionForm);
		        request.setAttribute("FeedBackQuestion", "edit");
				return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
			}
		 setRequestedDataToForm(teacherFeedBackQuestionForm);
        log.debug("Exit: action class updateFeedBackQuestion");
        return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
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
        EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm = (EvaTeacherFeedBackQuestionForm)form;
        ActionMessages messages = new ActionMessages();
        try
        {
            boolean isDeleted = EvaTeacherFeedBackQuestionHandler.getInstance().deleteFeedBackQuestion(teacherFeedBackQuestionForm);
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
            teacherFeedBackQuestionForm.reset(mapping, request);
            setRequestedDataToForm(teacherFeedBackQuestionForm);
        }
        catch(Exception e)
        {
            log.error("error submit valuatorCharges...", e);
            if(e instanceof ApplicationException)
            {
                String msg = super.handleApplicationException(e);
                teacherFeedBackQuestionForm.setErrorMessage(msg);
                teacherFeedBackQuestionForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            } else
            {
                String msg = super.handleApplicationException(e);
                teacherFeedBackQuestionForm.setErrorMessage(msg);
                teacherFeedBackQuestionForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        }
        log.debug("Action class. Delete valuatorCharges ");
        return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
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
        EvaTeacherFeedBackQuestionForm teacherFeedBackQuestionForm = (EvaTeacherFeedBackQuestionForm)form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        try
        {
            setUserId(request, teacherFeedBackQuestionForm);
            String userId = teacherFeedBackQuestionForm.getUserId();
            String duplicateId = session.getAttribute("ReactivateId").toString();
            teacherFeedBackQuestionForm.setId(Integer.parseInt(duplicateId));
            boolean isReactivate = EvaTeacherFeedBackQuestionHandler.getInstance().reActivateFeedBackQuestion(teacherFeedBackQuestionForm, userId);
            if(isReactivate)
            {
                messages.add("messages", new ActionMessage("knowledgepro.studentFeedBack.questionreactivatesuccess"));
                setRequestedDataToForm(teacherFeedBackQuestionForm);
                teacherFeedBackQuestionForm.reset(mapping, request);
                saveMessages(request, messages);
            } else
            {
                setRequestedDataToForm(teacherFeedBackQuestionForm);
                errors.add("error", new ActionError("knowledgepro.studentFeedBack.questionreactivatefailure"));
                addErrors(request, errors);
            }
        }
        catch(Exception e)
        {
            log.error("Error occured in reactivatevaluatorCharges of ValuatorChargesAction", e);
            String msg = super.handleApplicationException(e);
            teacherFeedBackQuestionForm.setErrorMessage(msg);
            teacherFeedBackQuestionForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("Leaving into reactivatevaluatorCharges of ValuatorChargesAction");
        return mapping.findForward(CMSConstants.TEACHER_FEEDBACK_QUESTION);
    }

}
