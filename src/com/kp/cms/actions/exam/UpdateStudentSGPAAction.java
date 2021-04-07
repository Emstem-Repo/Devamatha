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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.AdminMarksCardForm;
import com.kp.cms.forms.exam.ExamStudentSGPAForm;
import com.kp.cms.handlers.exam.ExamUpdateProcessHandler;
import com.kp.cms.handlers.exam.UpdateStudentSGPAHandler;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class UpdateStudentSGPAAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UpdateStudentSGPAAction.class);
	public ActionForward initUpdateStudentSGPA(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initUpdateStudentSGPA input");
		ExamStudentSGPAForm examStudentSGPAForm = (ExamStudentSGPAForm) form;
		examStudentSGPAForm.resetFields();
		
		log.info("Exit initUpdateStudentSGPA input");
		
		return mapping.findForward(CMSConstants.UPDATE_STUDENT_SGPA);
	}
	public ActionForward updateStudentSGPA(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			ExamStudentSGPAForm examStudentSGPAForm = (ExamStudentSGPAForm) form;
			ActionMessages messages = new ActionMessages();
			List<String> programTypeList;
		    INewExamMarksEntryTransaction txn = NewExamMarksEntryTransactionImpl.getInstance();
			ActionErrors errors = examStudentSGPAForm.validate(mapping, request);
			boolean isAdded = false;
			try{
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_STUDENT_SGPA);
				}
				else{
					String programTypeQuery="select p.name from ProgramType p where p.id= "+examStudentSGPAForm.getProgramTypeId() ;
					programTypeList=txn.getDataForQuery(programTypeQuery);
					String programType = programTypeList.get(0);
					setUserId(request, examStudentSGPAForm);
					isAdded = UpdateStudentSGPAHandler.getInstance().updateSGPA(Integer.parseInt(examStudentSGPAForm.getCourseId()), 
							Integer.parseInt(examStudentSGPAForm.getSchemeNo()), Integer.parseInt(examStudentSGPAForm.getYear()), programType,examStudentSGPAForm.getProgramId(),examStudentSGPAForm.getUserId());
				}
			} catch (Exception e) {
				log.error("error in update SGPA...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					examStudentSGPAForm.setErrorMessage(msg);
					examStudentSGPAForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			if (isAdded) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.exam.sgpa.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				// failed
				errors.add("error", new ActionError("knowledgepro.exam.sgpa.update.failure"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_STUDENT_SGPA);
			}
			
			return mapping.findForward(CMSConstants.UPDATE_STUDENT_SGPA);
		}
	
	
	public ActionForward initUpdatePassFail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initUpdatePassFail input");
		ExamStudentSGPAForm examStudentSGPAForm = (ExamStudentSGPAForm) form;
		examStudentSGPAForm.resetFields();
		
		log.info("Exit initUpdatePassFail input");
		
		return mapping.findForward(CMSConstants.UPDATE_PASS_FAIL);
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
	public ActionForward updateStudentPassFail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			ExamStudentSGPAForm examStudentSGPAForm = (ExamStudentSGPAForm) form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = examStudentSGPAForm.validate(mapping, request);
			boolean isAdded = false;
			try{
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_PASS_FAIL);
				}
				ExamUpdateProcessHandler handler = new ExamUpdateProcessHandler();
				isAdded = handler.updatePassorFail(Integer.parseInt(examStudentSGPAForm.getCourseId()), 
						Integer.parseInt(examStudentSGPAForm.getSchemeNo()), Integer.parseInt(examStudentSGPAForm.getYear()), examStudentSGPAForm.getIsPreviousExam());
			} catch (Exception e) {
				log.error("error in updateStudentPassFail...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					examStudentSGPAForm.setErrorMessage(msg);
					examStudentSGPAForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			if (isAdded) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.exam.pass.fail.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				examStudentSGPAForm.resetFields();
			} else {
				// failed
				errors.add("error", new ActionError("knowledgepro.exam.pass.fail.update.failure"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_PASS_FAIL);
			}
			
			return mapping.findForward(CMSConstants.UPDATE_PASS_FAIL);
		}
	
	public ActionForward initCoursewiseCCPA(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initUpdateStudentSGPA input");
		ExamStudentSGPAForm examStudentSGPAForm = (ExamStudentSGPAForm) form;
		examStudentSGPAForm.resetFields();
		log.info("Exit initUpdateStudentSGPA input");
		
		return mapping.findForward(CMSConstants.UPDATE_COURSEWISE_CCPA);
	}
	public ActionForward updateStudentCCPA(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			ExamStudentSGPAForm examStudentSGPAForm = (ExamStudentSGPAForm) form;
			ActionMessages messages = new ActionMessages();
			List<String> programTypeList;
		    INewExamMarksEntryTransaction txn = NewExamMarksEntryTransactionImpl.getInstance();
			ActionErrors errors = examStudentSGPAForm.validate(mapping, request);
			boolean isAdded = false;
			String programType="";
			try{
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.UPDATE_COURSEWISE_CCPA);
				}
				else{
					 String programTypeQuery="select p.name from ProgramType p where p.id= "+examStudentSGPAForm.getProgramTypeId() ;
						programTypeList=txn.getDataForQuery(programTypeQuery);
						programType = programTypeList.get(0);
						examStudentSGPAForm.setProgramTypeName(programType);
				}
				setUserId(request, examStudentSGPAForm);
				isAdded = UpdateStudentSGPAHandler.getInstance().updateCCPA(examStudentSGPAForm,Integer.parseInt(examStudentSGPAForm.getCourseId()), 
						 Integer.parseInt(examStudentSGPAForm.getYear()), programType,examStudentSGPAForm.getProgramId(),examStudentSGPAForm.getUserId());
			} catch (Exception e) {
				log.error("error in update SGPA...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					examStudentSGPAForm.setErrorMessage(msg);
					examStudentSGPAForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			if (isAdded) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.exam.ccpa.update.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			} else {
				// failed
				errors.add("error", new ActionError("knowledgepro.exam.ccpa.update.failure"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_COURSEWISE_CCPA);
			}
			
			return mapping.findForward(CMSConstants.UPDATE_COURSEWISE_CCPA);
		}
	
	
}
