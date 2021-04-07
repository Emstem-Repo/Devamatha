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
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.forms.exam.ExamRevaluationFeeForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.employee.PayScaleDetailsHandler;
import com.kp.cms.handlers.exam.ExamRevaluationFeeHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ExamRevaluationFeeTO;

public class ExamRevaluationFeeAction extends BaseDispatchAction{
private static final Log log = LogFactory.getLog(ExamRevaluationFeeAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamRevaluationFee(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamRevaluationFeeForm examRevaluationFeeForm=(ExamRevaluationFeeForm)form;
		examRevaluationFeeForm.reset();
		setDataToForm(examRevaluationFeeForm);
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addRevaluationFee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExamRevaluationFeeForm examRevaluationFeeForm=(ExamRevaluationFeeForm)form;
		setUserId(request, examRevaluationFeeForm);
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = examRevaluationFeeForm.validate(mapping, request);
		HttpSession session=request.getSession();
		String mode="add";
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				boolean isDuplicate=ExamRevaluationFeeHandler.getInstance().duplicateCheck(examRevaluationFeeForm,errors,session);
				if(!isDuplicate){
				isAdded = ExamRevaluationFeeHandler.getInstance().addRevaluationFee(examRevaluationFeeForm, mode);
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.exam.revaluationfee.addsuccess"));
					saveMessages(request, messages);
					examRevaluationFeeForm.reset();
				} else {
					errors.add("error", new ActionError( "knowledgepro.exam.revaluationfee.addfailure"));
					addErrors(request, errors);
					examRevaluationFeeForm.reset();
				}}
				else{
					addErrors(request, errors);
				}
			}
			catch (Exception exception) {
				log.error("Error occured in ExamRevaluationFeeAction", exception);
				String msg = super.handleApplicationException(exception);
				examRevaluationFeeForm.setErrorMessage(msg);
				examRevaluationFeeForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			setDataToForm(examRevaluationFeeForm);
			return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE);
		}
		saveErrors(request, errors);
		setDataToForm(examRevaluationFeeForm);
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE);
	}
	/**
	 * @param examRevaluationFeeForm
	 * @throws Exception
	 */
	public void setDataToForm(ExamRevaluationFeeForm examRevaluationFeeForm)throws Exception{
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		examRevaluationFeeForm.setProgramTypeList(programTypeList);
		List<ExamRevaluationFeeTO> revaluationFeeTos = ExamRevaluationFeeHandler.getInstance().getRevaluationFeeList();
		examRevaluationFeeForm.setRevaluationFeeToList(revaluationFeeTos);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editRevaluationFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRevaluationFeeForm examRevaluationFeeForm=(ExamRevaluationFeeForm)form;
		log.debug("Entering editRevaluationFee ");
		try {
			ExamRevaluationFeeHandler.getInstance().editRevaluationFee(examRevaluationFeeForm);
			request.setAttribute("operation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editRevaluationFee ");
		} catch (Exception e) {
			log.error("error in editing RevaluationFee...", e);
			String msg = super.handleApplicationException(e);
			examRevaluationFeeForm.setErrorMessage(msg);
			examRevaluationFeeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateRevaluationFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Enter: updateRevaluationFee ");
		ExamRevaluationFeeForm examRevaluationFeeForm=(ExamRevaluationFeeForm)form;
		HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examRevaluationFeeForm.validate(mapping, request);
		boolean isUpdated = false;
		String mode="update";
        if(errors.isEmpty()){
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				examRevaluationFeeForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,
						formName);
				ExamRevaluationFeeHandler.getInstance().editRevaluationFee(examRevaluationFeeForm);
				request.setAttribute("operation", "edit");
				return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE);
			}
			setUserId(request, examRevaluationFeeForm); 
			boolean isDuplicate=ExamRevaluationFeeHandler.getInstance().duplicateCheck(examRevaluationFeeForm,errors,session);
			if(!isDuplicate){
			isUpdated = ExamRevaluationFeeHandler.getInstance().updateRevaluationFee(examRevaluationFeeForm, mode);
			if (isUpdated) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.revaluationfee.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				//employeeResumeForm.reset(mapping, request);
				examRevaluationFeeForm.reset();
			} else {
				errors.add("error", new ActionError(
						"knowledgepro.exam.revaluationfee.update.failed"));
				//saveErrors(request, errors);
				addErrors(request, errors);
				examRevaluationFeeForm.reset();
			}}else{
				addErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error occured in edit RevaluationFee", e);
			String msg = super.handleApplicationException(e);
			examRevaluationFeeForm.setErrorMessage(msg);
			examRevaluationFeeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}}else{
			saveErrors(request, errors);
			setDataToForm(examRevaluationFeeForm);
			request.setAttribute("operation", "edit");
			return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE);
		}
        setDataToForm(examRevaluationFeeForm);
		log.debug("Exit: action class updateRevaluationFee");
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE);

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteRevaluationFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		ExamRevaluationFeeForm examRevaluationFeeForm=(ExamRevaluationFeeForm)form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = ExamRevaluationFeeHandler.getInstance().deleteRevaluationFee(examRevaluationFeeForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.revaluationfee.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.revaluationfee.delete.failed");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
			examRevaluationFeeForm.reset();
			setDataToForm(examRevaluationFeeForm);
		} catch (Exception e) {
			log.error("error submit RevaluationFee...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				examRevaluationFeeForm.setErrorMessage(msg);
				examRevaluationFeeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				examRevaluationFeeForm.setErrorMessage(msg);
				examRevaluationFeeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.debug("Action class. Delete RevaluationFee ");
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reactivateRevaluationFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering RevaluationFee Action");
		ExamRevaluationFeeForm examRevaluationFeeForm=(ExamRevaluationFeeForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		try {
			setUserId(request, examRevaluationFeeForm);
			boolean isReactivate;
			//int olddocTypeId =Integer.parseInt(documentExamEntryForm.getDocTypeId());
			//String oldExamName = documentExamEntryForm.getExamName().trim();
			String duplicateId=session.getAttribute("ReactivateId").toString();
			examRevaluationFeeForm.setId(Integer.parseInt(duplicateId));
			isReactivate = ExamRevaluationFeeHandler.getInstance().reactivateRevaluationFee(examRevaluationFeeForm);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.revaluationfee.details.activate"));
				setDataToForm(examRevaluationFeeForm);
				examRevaluationFeeForm.reset();
				saveMessages(request, messages);
			}
			else{
				setDataToForm(examRevaluationFeeForm);
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.revaluationfee.details.activate.failed"));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reactivateRevaluationFee of ExamRevaluationFeeAction", e);
			String msg = super.handleApplicationException(e);
			examRevaluationFeeForm.setErrorMessage(msg);
			examRevaluationFeeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reactivateRevaluationFee of ExamRevaluationFeeAction");
		return mapping.findForward(CMSConstants.INIT_REVALUATION_FEE); 
	}
}
