package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.exam.ExamValidationDetailsForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.utilities.CommonUtil;

public class ExamValidationDetailsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExamValidationDetailsAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ExamMarksEntry.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamValidationEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initExamValidationEntry input");
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		if(examValidationDetailsForm.getUpdatedScripts()==null){
			examValidationDetailsForm.resetFields();
			examValidationDetailsForm.setAcademicYear(null);
		}
		try{
			setRequiredDatatoForm(examValidationDetailsForm, request);
			examValidationDetailsForm.setDate(CommonUtil.getTodayDate());
		}catch (Exception e) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initExamValidationEntry input");
		
		return mapping.findForward("initValidationDetailsEntry");
	}

	/**
	 * @param newSecuredMarksEntryForm
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void setRequiredDatatoForm(ExamValidationDetailsForm examValidationDetailsForm,HttpServletRequest request) throws Exception{
		
		Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType(examValidationDetailsForm.getExamType(),examValidationDetailsForm.getAcademicYear());
		
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		examValidationDetailsForm.setExamNameList(examNameMap);
		
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(examValidationDetailsForm.getExamType());
		if((examValidationDetailsForm.getExamId()==null || examValidationDetailsForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			examValidationDetailsForm.setExamId(currentExam);
		if(examValidationDetailsForm.getUpdatedScripts()!=null && !examValidationDetailsForm.getUpdatedScripts().isEmpty()){
			if(examValidationDetailsForm.getExamId()!=null &&!examValidationDetailsForm.getExamId().isEmpty() && 
					examValidationDetailsForm.getSubjectId()!=null && !examValidationDetailsForm.getSubjectId().isEmpty()){
				List<ExamValidationDetailsTO> validationList=ExamValidationDetailsHandler.getInstance().getExamValidationDetailsByAjax(examValidationDetailsForm);
				ExamValidationDetailsHandler.getInstance().numberOfAnswerScriptAndAlredyIssuedScript(examValidationDetailsForm);
				//added by chandra
				ExamValidationDetailsHandler.getInstance().numberOfAnswerScriptAndAlredyIssuedScriptForEvaluator2(examValidationDetailsForm);
				examValidationDetailsForm.setValidationDetails(validationList);
			}else{
				List<ExamValidationDetailsTO> validationList =  new ArrayList<ExamValidationDetailsTO>();
				examValidationDetailsForm.setValidationDetails(validationList);
			}
		    examValidationDetailsForm.setUpdatedScripts(null);	
		}else{
			List<ExamValidationDetailsTO> validationList =  new ArrayList<ExamValidationDetailsTO>();
			examValidationDetailsForm.setValidationDetails(validationList);
		}
		/*if(examValidationDetailsForm.getExamId() != null && !examValidationDetailsForm.getExamId().isEmpty()){
			List<ExamValidationDetailsTO> validationList =  ExamValidationDetailsHandler.getInstance().getExamValidationDetails(examValidationDetailsForm.getExamId(),examValidationDetailsForm.getAcademicYear());
			examValidationDetailsForm.setValidationDetails(validationList);
		}else{
			List<ExamValidationDetailsTO> validationList =  new ArrayList<ExamValidationDetailsTO>();
			examValidationDetailsForm.setValidationDetails(validationList);
		}*/
		if(examValidationDetailsForm.getExamId()!=null && !examValidationDetailsForm.getExamId().isEmpty()){
			Map<Integer, String> subjectList=ExamValidationDetailsHandler.getInstance().getSubjectCodeName(
					examValidationDetailsForm.getAcademicYear() ,examValidationDetailsForm.getDisplaySubType(),
					Integer.parseInt(examValidationDetailsForm.getExamId()),examValidationDetailsForm.getExamType());
			examValidationDetailsForm.setSubjectMap(subjectList);
		}
		
		Map<Integer, String>  evaluatorsMap = ExamValidationDetailsHandler.getInstance().getValuatorNameListBySubjectDept(examValidationDetailsForm.getSubjectId());;
		request.getSession().setAttribute("evaluatorsMap", evaluatorsMap);
		
		Map<Integer, String>  otherEvaluatorsMap = ExamValidationDetailsHandler.getInstance().getOtherEmployeeList(examValidationDetailsForm.getSubjectId());
		request.getSession().setAttribute("OtherEvaluatorsMap", otherEvaluatorsMap);
		
		/*if(examValidationDetailsForm.getExamId() == null || examValidationDetailsForm.getExamId().isEmpty()){
			if(currentExam!=null && !currentExam.isEmpty()){
				List<ExamValidationDetailsTO> validationList =  ExamValidationDetailsHandler.getInstance().getExamValidationDetails(currentExam,examValidationDetailsForm.getAcademicYear());
				examValidationDetailsForm.setValidationDetails(validationList);
			}
		}*/
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered saveDetails input");
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, examValidationDetailsForm);
			examValidationDetailsForm.validate(mapping, request);
			ActionErrors errors = examValidationDetailsForm.validate(mapping, request);
			validateForm(examValidationDetailsForm, errors, request );
			if(errors.isEmpty()){
				examValidationDetailsForm.setId(0);
				boolean save = ExamValidationDetailsHandler.getInstance().saveValidationDetails(examValidationDetailsForm);
				if(save){
					//examValidationDetailsForm.resetSomeFields();
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.validation.datails.added.success"));
					saveMessages(request, messages);
					examValidationDetailsForm.setAcademicYear(null);
				}else{
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}else{
				saveErrors(request, errors);
			}
			/*if(examValidationDetailsForm.getExamId() != null && !examValidationDetailsForm.getExamId().isEmpty()){
				List<ExamValidationDetailsTO> validationList =  ExamValidationDetailsHandler.getInstance().getExamValidationDetails(examValidationDetailsForm.getExamId(),examValidationDetailsForm.getAcademicYear());
				examValidationDetailsForm.setValidationDetails(validationList);
			}else{
				List<ExamValidationDetailsTO> validationList =  new ArrayList<ExamValidationDetailsTO>();
				examValidationDetailsForm.setValidationDetails(validationList);
			}*/
			
			/*Map<Integer, String>  evaluatorsMap = ExamValidationDetailsHandler.getInstance().getEmployeeList();
			request.getSession().setAttribute("evaluatorsMap", evaluatorsMap);*/
			
			examValidationDetailsForm.setDate(CommonUtil.getTodayDate());
			examValidationDetailsForm.setUpdatedScripts("ScriptsUpdated");
			setRequiredDatatoForm(examValidationDetailsForm, request);
			examValidationDetailsForm.setEmployeeId(null);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValidationDetailsForm.setErrorMessage(msg);
			examValidationDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit saveDetails input");
		
		return mapping.findForward("initValidationDetailsEntry");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editValidationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initSecuredMarksEntry input");
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		try{
			ExamValidationDetailsHandler.getInstance().editValidationDetails(examValidationDetailsForm);
			request.setAttribute("editType", "edit");
			setRequiredDatatoForm(examValidationDetailsForm, request);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValidationDetailsForm.setErrorMessage(msg);
			examValidationDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit editValidationDetails input");
		
		return mapping.findForward("initValidationDetailsEntry");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteValidationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered deleteValidationDetails input");
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		try{
				boolean delete = ExamValidationDetailsHandler.getInstance().deleteValidationDetails(examValidationDetailsForm);
				if(delete){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.validation.datails.deleted.success"));
					saveMessages(request, messages);
					//examValidationDetailsForm.resetFields();
					examValidationDetailsForm.setUpdatedScripts("ScriptUpdated");
					setRequiredDatatoForm(examValidationDetailsForm, request);
				}
				//ExamValidationDetailsHandler.getInstance().getAnswerScriptDetails(examValidationDetailsForm);
				//examValidationDetailsForm.setAnswerScripts(answerScripts);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValidationDetailsForm.setErrorMessage(msg);
			examValidationDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit deleteValidationDetails input");
		
		return mapping.findForward("initValidationDetailsEntry");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateValidationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered updateValidationDetails input");
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		try{
				boolean update = ExamValidationDetailsHandler.getInstance().updateValidationDetails(examValidationDetailsForm);
				if(update){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.validation.datails.update.success"));
					saveMessages(request, messages);
					examValidationDetailsForm.resetFields();
					setRequiredDatatoForm(examValidationDetailsForm, request);
				}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValidationDetailsForm.setErrorMessage(msg);
			examValidationDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit updateValidationDetails input");
		
		return mapping.findForward("initValidationDetailsEntry");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetailsForExam(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered getDetailsForExam input");
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		try{
			if(examValidationDetailsForm.getExamId() != null && !examValidationDetailsForm.getExamId().isEmpty()){
				List<ExamValidationDetailsTO> validationList =  ExamValidationDetailsHandler.getInstance().getExamValidationDetails(examValidationDetailsForm.getExamId(),examValidationDetailsForm.getAcademicYear());
				examValidationDetailsForm.setValidationDetails(validationList);
			}else{
				List<ExamValidationDetailsTO> validationList =  new ArrayList<ExamValidationDetailsTO>();
				examValidationDetailsForm.setValidationDetails(validationList);
			}
			setRequiredDatatoForm(examValidationDetailsForm, request);
			examValidationDetailsForm.setEmployeeId(null);
			examValidationDetailsForm.setOtherEmployee(null);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValidationDetailsForm.setErrorMessage(msg);
			examValidationDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit getDetailsForExam input");
		
		return mapping.findForward("initValidationDetailsEntry");
	}
	
	/**
	 * @param examValidationDetailsForm
	 * @param errors 
	 * @param request 
	 */
	@SuppressWarnings("deprecation")
	private void validateForm(ExamValidationDetailsForm examValidationDetailsForm, ActionErrors errors, HttpServletRequest request) {
		if(examValidationDetailsForm.getExamId() == null || examValidationDetailsForm.getExamId().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
			saveErrors(request, errors);
		}
		if(examValidationDetailsForm.getSubjectId() == null || examValidationDetailsForm.getSubjectId().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.subject.required"));
			saveErrors(request, errors);
		}
		
		if(examValidationDetailsForm.getEmployeeId() == null || examValidationDetailsForm.getEmployeeId().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.employee.required"));
			saveErrors(request, errors);
		}
		if(examValidationDetailsForm.getEmployeeId() != null && examValidationDetailsForm.getEmployeeId().equalsIgnoreCase("Other")){
			if(examValidationDetailsForm.getOtherEmpId() == null || examValidationDetailsForm.getOtherEmpId().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.employee.required"));
				saveErrors(request, errors);
			}
		}
		if(examValidationDetailsForm.getOtherEmpId() != null && examValidationDetailsForm.getOtherEmpId().equalsIgnoreCase("Other")){
			if(examValidationDetailsForm.getOtherEmployee() == null || examValidationDetailsForm.getOtherEmployee().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.employee.required"));
				saveErrors(request, errors);
			}
		}
		if(examValidationDetailsForm.getAnswers() == null || examValidationDetailsForm.getAnswers().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.answer.required"));
			saveErrors(request, errors);
		}
		
	}
	

	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editNumberOfAnswerScriptDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewValuationDetails input");
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		try{
			ExamValidationDetailsTO answerScripts=ExamValidationDetailsHandler.getInstance().getAnswerScriptDetails(examValidationDetailsForm);
			examValidationDetailsForm.setAnswerScripts(answerScripts);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			examValidationDetailsForm.setErrorMessage(msg);
			examValidationDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewValuationDetails input");
		
		return mapping.findForward("getValuationDetails");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * code added by mehaboob to get examvalidtionDetails by Subject through ajax call
	 */
	public ActionForward getExamValidationDetailsByAjax(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		try {
			if((examValidationDetailsForm.getExamId() != null && !examValidationDetailsForm.getExamId().isEmpty())
				&& (examValidationDetailsForm.getSubjectId()!=null && !examValidationDetailsForm.getSubjectId().isEmpty())){
				List<ExamValidationDetailsTO> examValidationList =  ExamValidationDetailsHandler.getInstance().getExamValidationDetailsByAjax(examValidationDetailsForm);
				request.setAttribute("examValidationList", examValidationList);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForValuationListBySubject");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * code added by mehaboob
	 */
	public void getNumberOfAnswrScriptAndIssuedScripts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		try {
			if((examValidationDetailsForm.getExamId() != null && !examValidationDetailsForm.getExamId().isEmpty())
				&& (examValidationDetailsForm.getSubjectId()!=null && !examValidationDetailsForm.getSubjectId().isEmpty())
				&& examValidationDetailsForm.getExamType()!=null && !examValidationDetailsForm.getExamType().isEmpty()){
				String totalScriptAndAlredyIssued =  ExamValidationDetailsHandler.getInstance().numberOfAnswerScriptAndAlredyIssuedScript(examValidationDetailsForm);
				if(totalScriptAndAlredyIssued!=null && !totalScriptAndAlredyIssued.isEmpty()){
					response.getWriter().write(totalScriptAndAlredyIssued);
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	
	// /* code added by chandra
	
	/**  for displaying Issued Scripts and Pending Scripts For Evaluator2 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getNumberOfAnswrScriptAndIssuedScriptsForValuator2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		try {
			if((examValidationDetailsForm.getExamId() != null && !examValidationDetailsForm.getExamId().isEmpty())
				&& (examValidationDetailsForm.getSubjectId()!=null && !examValidationDetailsForm.getSubjectId().isEmpty())
				&& examValidationDetailsForm.getExamType()!=null && !examValidationDetailsForm.getExamType().isEmpty()){
				String totalScriptAndAlredyIssued =  ExamValidationDetailsHandler.getInstance().numberOfAnswerScriptAndAlredyIssuedScriptForEvaluator2(examValidationDetailsForm);
				if(totalScriptAndAlredyIssued!=null && !totalScriptAndAlredyIssued.isEmpty()){
					response.getWriter().write(totalScriptAndAlredyIssued);
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}
	// */
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * code added by mehaboob
	 */
	public ActionForward updateNumberOfScriptAndValuator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		try {
				boolean isUpdated =  ExamValidationDetailsHandler.getInstance().updateTotalScriptSAndValuator(examValidationDetailsForm);
				if(isUpdated){
					 messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.validation.datails.answer.scripts.update.success"));
					saveMessages(request, messages);
					examValidationDetailsForm.setUpdatedScripts("ScriptsUpdated");
					//examValidationDetailsForm.resetFields();
					setRequiredDatatoForm(examValidationDetailsForm, request);
				}
		
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("initValidationDetailsEntry");
	}
	
	/**
	 * Method to set the required data to the form to display it in ExamMarksEntry.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAnswerScriptCount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initExamValidationEntry input");
		ExamValidationDetailsForm examValidationDetailsForm = (ExamValidationDetailsForm) form;
		try{
			examValidationDetailsForm.resetFields();
			examValidationDetailsForm.setAcademicYear(null);
			setAnswerScriptCountDatatoForm(examValidationDetailsForm, request);
			examValidationDetailsForm.setDate(CommonUtil.getTodayDate());
		}catch (Exception e) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initExamValidationEntry input");
		
		return mapping.findForward("initAnswerscriptCount");
	}
/**
 * @param examValidationDetailsForm
 * @param request
 * @throws Exception
 */
private void setAnswerScriptCountDatatoForm(ExamValidationDetailsForm examValidationDetailsForm,HttpServletRequest request) throws Exception{
		
		Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType(examValidationDetailsForm.getExamType(),examValidationDetailsForm.getAcademicYear());
		
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		examValidationDetailsForm.setExamNameList(examNameMap);
		
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(examValidationDetailsForm.getExamType());
		if((examValidationDetailsForm.getExamId()==null || examValidationDetailsForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			examValidationDetailsForm.setExamId(currentExam);
			if(examValidationDetailsForm.getExamId()!=null && !examValidationDetailsForm.getExamId().isEmpty()){
			Map<Integer, String> subjectList=ExamValidationDetailsHandler.getInstance().getSubjectCodeName(
					examValidationDetailsForm.getAcademicYear() ,examValidationDetailsForm.getDisplaySubType(),
					Integer.parseInt(examValidationDetailsForm.getExamId()),examValidationDetailsForm.getExamType());
			examValidationDetailsForm.setSubjectMap(subjectList);
		}
	}
}