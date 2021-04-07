package com.kp.cms.actions.exam;

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
import com.kp.cms.forms.exam.ExamInvigilationDutySettingForm;
import com.kp.cms.handlers.exam.ExamInvigilationDutySettingHandler;
import com.kp.cms.to.examallotment.ExamInvigilationDutySettingsTo;
import com.kp.cms.to.studentfeedback.BlockBoTo;

public class ExamInvigilationDutySettingAction extends BaseDispatchAction
{

	private static final Log log=LogFactory.getLog(ExamInvigilationDutySettingAction.class);

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	   public ActionForward initInvigilationDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
          throws Exception{
		  ExamInvigilationDutySettingForm invigilationForm = (ExamInvigilationDutySettingForm)form;
		  ActionErrors errors = invigilationForm.validate(mapping, request);
		  invigilationForm.resetClear();
		  setRequestedDataToForm(invigilationForm,errors);
          setEmpLocation(request);
          return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING);
        }
		/**
	     * @param request
	     * @throws Exception
	     */
	    private void setEmpLocation(HttpServletRequest request)throws Exception {
	        List<BlockBoTo> locationList = ExamInvigilationDutySettingHandler.getInstance().getEmpLocation();
	        request.getSession().setAttribute("locationList", locationList);
	    }
		/**
	     * @param invigilationForm
		 * @param errors 
		 * @param errors 
	     * @throws Exception
	     */
	    private void setRequestedDataToForm(ExamInvigilationDutySettingForm invigilationForm, ActionErrors errors)throws Exception{
	    	List<ExamInvigilationDutySettingsTo> invigilationList = ExamInvigilationDutySettingHandler.getInstance().getInvigilationList(invigilationForm);
	    	invigilationForm.setInvigilationList(invigilationList);
	    }
	    /**
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward addInvigilationDutySetting(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception{
	    	ExamInvigilationDutySettingForm invigilationForm = (ExamInvigilationDutySettingForm)form;
	        setUserId(request, invigilationForm);
	        ActionMessages messages = new ActionMessages();
	        ActionErrors errors = invigilationForm.validate(mapping, request);
	        HttpSession session=request.getSession();
	        if(errors.isEmpty())
	        {
	            try
	            {
	                boolean isAdded = false;
	                boolean isDuplicate =ExamInvigilationDutySettingHandler.getInstance().duplicateCheck(invigilationForm, errors, session);
	                if(!isDuplicate)
	                {
	                    isAdded = ExamInvigilationDutySettingHandler.getInstance().addInvigilationDuty(invigilationForm);
	                    if(isAdded)
	                    {
	                        messages.add("messages", new ActionMessage("knowledgepro.hlAdmission.entry.added.success"));
	                        saveMessages(request, messages);
	                        invigilationForm.resetClear();
	                        setRequestedDataToForm(invigilationForm,errors);
	                    } else
	                    {
	                        errors.add("error", new ActionError("knowledgepro.hlAdmission.entry.added.failure"));
	                        addErrors(request, errors);
	                        invigilationForm.resetClear();
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
	                invigilationForm.setErrorMessage(msg);
	                invigilationForm.setErrorStack(exception.getMessage());
	                return mapping.findForward(CMSConstants.ERROR_PAGE);
	            }
	        } else
	        {
	            saveErrors(request, errors);
	            return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING);
	        }
	        log.info("end of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
	        return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING);
	    }
	    /**
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward editInvigilationDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	    {
	    	ExamInvigilationDutySettingForm invigilationForm = (ExamInvigilationDutySettingForm)form;
	    	HttpSession sessions=request.getSession();
	        try
	        {
	        	ExamInvigilationDutySettingHandler.getInstance().editInvigilationDuty(invigilationForm);
	            request.setAttribute("invDuty", "edit");
	        }
	        catch(Exception e)
	        {
	            log.error("error in editing FeedBackQuestion...", e);
	            String msg = super.handleApplicationException(e);
	            invigilationForm.setErrorMessage(msg);
	            invigilationForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }
	        return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING);
	    }

	    /**
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward updateInvigilationDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	    {
	    	ExamInvigilationDutySettingForm invigilationForm = (ExamInvigilationDutySettingForm)form;
	        HttpSession session=request.getSession();
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = invigilationForm.validate(mapping, request);
			String Mode="Update";
			boolean isUpdated = false;
			if(errors.isEmpty()){
				try {
					// This condition works when reset button will click in update mode
					if (isCancelled(request)) {
						invigilationForm.resetClear();
						ExamInvigilationDutySettingHandler.getInstance().editInvigilationDuty(invigilationForm);
			            request.setAttribute("invDuty", "edit");
				        return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING);
					}
					setUserId(request, invigilationForm);
					boolean isDuplicate =ExamInvigilationDutySettingHandler.getInstance().duplicateCheck(invigilationForm, errors, session);
					if(!isDuplicate){
						isUpdated = ExamInvigilationDutySettingHandler.getInstance().updateInvigilationDuty(invigilationForm);
					if (isUpdated) {
	                    ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.update.success");
	                    messages.add("messages", message);
	                    saveMessages(request, messages);
	                    invigilationForm.resetClear();
	                } else {
	                    errors.add("error", new ActionError("knowledgepro.hlAdmission.entry.update.failure"));
	                    addErrors(request, errors);
	                    invigilationForm.resetClear();
	                }}
					else{
						request.setAttribute("invDuty", "edit");
		                addErrors(request, errors);
		            }
				} catch (Exception e) {
		            log.error("Error occured in edit valuatorcharges", e);
		            String msg = super.handleApplicationException(e);
		            invigilationForm.setErrorMessage(msg);
		            invigilationForm.setErrorStack(e.getMessage());
		            return mapping.findForward(CMSConstants.ERROR_PAGE);
		        }}else{
		        	setRequestedDataToForm(invigilationForm,errors);
					saveErrors(request, errors);
					request.setAttribute("invDuty", "edit");
					return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING);
				}
			 setRequestedDataToForm(invigilationForm,errors);
			 saveErrors(request, errors);
	        log.debug("Exit: action class updateFeedBackQuestion");
	        return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING);
	    }

	    /**
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward deleteInvigilationDuty(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception {
	    	log.debug("Action class. Delete valuatorCharges ");
	    	ExamInvigilationDutySettingForm invigilationForm = (ExamInvigilationDutySettingForm)form;
	    	HttpSession session=request.getSession();
	        ActionMessages messages = new ActionMessages();
	        ActionErrors errors = new ActionErrors();
	        try
	        {
	            boolean isDeleted = ExamInvigilationDutySettingHandler.getInstance().deleteInvigilationDuty(invigilationForm);
	            if(isDeleted)
	            {
	                ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.delete.success");
	                messages.add("messages", message);
	                saveMessages(request, messages);
	            } else
	            {
	                ActionMessage message = new ActionMessage("knowledgepro.hlAdmission.entry.delete.failure");
	                messages.add("messages", message);
	                saveMessages(request, messages);
	            }
	            invigilationForm.resetClear();
	            setRequestedDataToForm(invigilationForm,errors);
	            saveErrors(request, errors);
	        }
	        catch(Exception e)
	        {
	            log.error("error submit valuatorCharges...", e);
	            if(e instanceof ApplicationException)
	            {
	                String msg = super.handleApplicationException(e);
	                invigilationForm.setErrorMessage(msg);
	                invigilationForm.setErrorStack(e.getMessage());
	                return mapping.findForward(CMSConstants.ERROR_PAGE);
	            } else
	            {
	                String msg = super.handleApplicationException(e);
	                invigilationForm.setErrorMessage(msg);
	                invigilationForm.setErrorStack(e.getMessage());
	                return mapping.findForward(CMSConstants.ERROR_PAGE);
	            }
	        }
	        log.debug("Action class. Delete valuatorCharges ");
	        return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING);
	    }
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward reActivateInvigilationDuty(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ExamInvigilationDutySettingForm invigilationForm = (ExamInvigilationDutySettingForm)form;
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			HttpSession session=request.getSession();
			try {
				setUserId(request, invigilationForm);
				boolean isReactivate;
				String userId = invigilationForm.getUserId();
				String duplicateId=session.getAttribute("ReactivateId").toString();
				invigilationForm.setId(Integer.parseInt(duplicateId));
				isReactivate = ExamInvigilationDutySettingHandler.getInstance().reActivateInvigilationDuty(invigilationForm,userId);
				if(isReactivate){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.document.details.activate"));
					invigilationForm.resetClear();
                    setRequestedDataToForm(invigilationForm,errors);
					saveMessages(request, messages);
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.document.details.activate.failed"));
					addErrors(request, errors);
				}
				
			} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				invigilationForm.setErrorMessage(msg);
				invigilationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.EXAM_INVIGILATION_DUTY_SETTING); 
		}
	}
