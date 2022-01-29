package com.kp.cms.actions.admin;

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
import com.kp.cms.forms.admin.FreeFoodIssueForm;
import com.kp.cms.handlers.admin.FreeFoodIssueHandler;
import com.kp.cms.to.admin.FreeFoodIssueTo;

public class FreeFoodIssueAction extends BaseDispatchAction{
private static final Log log = LogFactory.getLog(FreeFoodIssueAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFreeFoodIssue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception  {
		FreeFoodIssueForm freeFoodIssueForm = (FreeFoodIssueForm) form;
		freeFoodIssueForm.reset();
		return mapping.findForward(CMSConstants.INIT_FREE_FOOD_ISSUE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStudentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered FreeFoodIssueAction - searchStudentDetails");
		FreeFoodIssueForm Form = (FreeFoodIssueForm) form;// Type casting the Action form to Required Form
		Form.reset1();
		ActionErrors errors = Form.validate(mapping, request);
		validateStudentRegOrRollNo(Form,errors);
		if (errors.isEmpty()) {
			try {
				boolean isValidStudent=FreeFoodIssueHandler.getInstance().checkValidStudentRegNo(Form.getRegisterNo());
				if(!isValidStudent){
					errors.add(CMSConstants.ERROR, new ActionError("errors.invalid","Register No"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_FREE_FOOD_ISSUE);
				}
				boolean isDuplicate=FreeFoodIssueHandler.getInstance().checkDuplicateEntry(Form);
				
				if(!isDuplicate){
					List<FreeFoodIssueTo> toList=FreeFoodIssueHandler.getInstance().checkingIsStudentEligibility(Form.getRegisterNo(),Form);
					if(toList!=null && !toList.isEmpty()){
						Form.setToList(toList);
					}
				}else{
					String regNo=Form.getRegisterNo();
					String time=Form.getTime();
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.free.food.issue.duplicate.student",regNo,time));
					saveErrors(request, errors);
					Form.reset();
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				Form.setErrorMessage(msg);
				Form.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			saveErrors(request, errors);
			addErrors(request, errors);
			log.info("Exit FreeFoodIssueAction - searchStudentDetails errors not empty ");
			return mapping.findForward(CMSConstants.INIT_FREE_FOOD_ISSUE);
		}
		
		log.info("Entered FreeFoodIssueAction - searchStudentDetails");
		return mapping.findForward(CMSConstants.INIT_FREE_FOOD_ISSUE);
	}
	
	/**
	 * @param form
	 * @param errors
	 * @throws Exception
	 */
	private void validateStudentRegOrRollNo(FreeFoodIssueForm form ,ActionErrors errors) throws Exception{
		if(form.getRegisterNo()==null || form.getRegisterNo().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.registerNo.requred"));
		}
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveStudentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered FreeFoodIssueAction - saveStudentDetails");
		FreeFoodIssueForm Form = (FreeFoodIssueForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request, Form);
		if (errors.isEmpty()) {
			try {
				
				boolean isSaved=FreeFoodIssueHandler.getInstance().saveStudentDetails(Form);
				if(isSaved){
					ActionMessage message = new ActionMessage("knowledgepro.admin.student.free.food.issue.Details.saved.successfully");
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
				}
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				Form.setErrorMessage(msg);
				Form.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} 
		Form.reset();
		log.info("Entered FreeFoodIssueAction - saveStudentDetails");
		return mapping.findForward(CMSConstants.INIT_FREE_FOOD_ISSUE);
	}

}
